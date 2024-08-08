-- Enable server output
SET SERVEROUTPUT ON;

-- Create tables
CREATE TABLE Customers (
    CustomerID INT PRIMARY KEY,
    Name VARCHAR2(100),
    DOB DATE,
    Balance INT,
    LastModified DATE,
    IsVIP CHAR(1)
);

CREATE TABLE Accounts (
    AccountID INT PRIMARY KEY,
    CustomerID INT,
    AccountType VARCHAR2(20),
    Balance INT,
    LastModified DATE,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

CREATE TABLE Transactions (
    TransactionID INT PRIMARY KEY,
    AccountID INT,
    TransactionDate DATE,
    Amount INT,
    TransactionType VARCHAR2(10),
    FOREIGN KEY (AccountID) REFERENCES Accounts(AccountID)
);

CREATE TABLE Loans (
    LoanID INT PRIMARY KEY,
    CustomerID INT,
    LoanAmount INT,
    InterestRate INT,
    StartDate DATE,
    EndDate DATE,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

CREATE TABLE Employees (
    EmployeeID INT PRIMARY KEY,
    Name VARCHAR2(100),
    Position VARCHAR2(50),
    Salary INT,
    Department VARCHAR2(50),
    DepartmentID INT,
    HireDate DATE
);

-- Create ErrorLogs table for logging errors
CREATE TABLE ErrorLogs (
    ErrorID INT PRIMARY KEY,
    ErrorMessage VARCHAR2(255),
    ErrorDate DATE
);

-- Scripts for Sample Data Insertion

INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
VALUES (1, 'John Doe', TO_DATE('1963-05-15', 'YYYY-MM-DD'), 1000, SYSDATE);

INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
VALUES (2, 'Jane Smith', TO_DATE('1990-07-20', 'YYYY-MM-DD'), 1500, SYSDATE);

INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified)
VALUES (1, 1, 'Savings', 1000, SYSDATE);

INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified)
VALUES (2, 2, 'Checking', 1500, SYSDATE);

INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
VALUES (1, 1, SYSDATE, 200, 'Deposit');

INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
VALUES (2, 2, SYSDATE, 300, 'Withdrawal');

INSERT INTO Loans (LoanID, CustomerID, LoanAmount, InterestRate, StartDate, EndDate)
VALUES (1, 1, 5000, 5, SYSDATE, ADD_MONTHS(SYSDATE, 60));

INSERT INTO Loans (LoanID, CustomerID, LoanAmount, InterestRate, StartDate, EndDate)
VALUES (2, 2, 10000, 5, SYSDATE, SYSDATE + 25);

INSERT INTO Employees (EmployeeID, Name, Position, Salary, Department, DepartmentID, HireDate)
VALUES (1, 'Alice Johnson', 'Manager', 70000, 'HR', 1, TO_DATE('2015-06-15', 'YYYY-MM-DD'));

INSERT INTO Employees (EmployeeID, Name, Position, Salary, Department, DepartmentID, HireDate)
VALUES (2, 'Bob Brown', 'Developer', 60000, 'IT', 2, TO_DATE('2017-03-20', 'YYYY-MM-DD'));

-- Function to Calculate Age of Customers
CREATE OR REPLACE FUNCTION CalculateAge(dob DATE)
RETURN NUMBER
IS
    age NUMBER;
BEGIN
    -- Calculate age based on the date of birth
    SELECT FLOOR((SYSDATE - dob) / 365)
    INTO age
    FROM dual;

    RETURN age;
END CalculateAge;
/
SHOW ERRORS FUNCTION CalculateAge;

-- Function to Compute Monthly Installment for a Loan
CREATE OR REPLACE FUNCTION CalculateMonthlyInstallment(
    loanAmount NUMBER,
    annualInterestRate NUMBER,
    loanDurationYears NUMBER
)
RETURN NUMBER
IS
    monthlyRate NUMBER;
    numPayments NUMBER;
    monthlyInstallment NUMBER;
BEGIN
    -- Calculate monthly interest rate
    monthlyRate := annualInterestRate / 100 / 12;

    -- Calculate total number of payments
    numPayments := loanDurationYears * 12;

    -- Calculate the monthly installment using the formula
    monthlyInstallment := loanAmount * (monthlyRate * POWER(1 + monthlyRate, numPayments)) / (POWER(1 + monthlyRate, numPayments) - 1);

    RETURN monthlyInstallment;
END CalculateMonthlyInstallment;
/
SHOW ERRORS FUNCTION CalculateMonthlyInstallment;

-- Function to Check if Customer Has Sufficient Balance
CREATE OR REPLACE FUNCTION HasSufficientBalance(
    accountID NUMBER,
    amount NUMBER
)
RETURN BOOLEAN
IS
    balance NUMBER;
BEGIN
    -- Get the current balance of the account
    SELECT Balance INTO balance
    FROM Accounts
    WHERE AccountID = accountID AND ROWNUM = 1;

    -- Check if the balance is sufficient
    IF balance >= amount THEN
        RETURN TRUE;
    ELSE
        RETURN FALSE;
    END IF;
END HasSufficientBalance;
/
SHOW ERRORS FUNCTION HasSufficientBalance;

-- Anonymous block to test the functions
DECLARE
    v_age NUMBER;
    v_monthlyInstallment NUMBER;
    v_hasSufficientBalance BOOLEAN;
BEGIN
    -- Test CalculateAge function
    v_age := CalculateAge(TO_DATE('1980-01-01', 'YYYY-MM-DD'));
    DBMS_OUTPUT.PUT_LINE('Age: ' || v_age);

    -- Test CalculateMonthlyInstallment function
    v_monthlyInstallment := CalculateMonthlyInstallment(10000, 5, 10);
    DBMS_OUTPUT.PUT_LINE('Monthly Installment: ' || v_monthlyInstallment);

    -- Test HasSufficientBalance function
    v_hasSufficientBalance := HasSufficientBalance(1, 500);
    IF v_hasSufficientBalance THEN
        DBMS_OUTPUT.PUT_LINE('Sufficient Balance: TRUE');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Sufficient Balance: FALSE');
    END IF;
END;
/
