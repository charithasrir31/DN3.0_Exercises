package com.example.BookstoreAPI.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private int id;
    private String name;
    private String email;
    private String password;
}
// Example jsons for posting into customers
/* 
 {
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "password": "password123"
 }
  {
  "id": 2,
  "name": "Jane Smith",
  "email": "jane.smith@example.com",
  "password": "securePass456"
}
{
  "id": 3,
  "name": "Alice Johnson",
  "email": "alice.johnson@example.com",
  "password": "alicePassword789"
}
{
  "id": 4,
  "name": "Bob Brown",
  "email": "bob.brown@example.com",
  "password": "bobPassword101"
}
{
  "id": 5,
  "name": "Charlie Davis",
  "email": "charlie.davis@example.com",
  "password": "charlieSecret202"
}


 */