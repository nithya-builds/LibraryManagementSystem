# Library Management System

**Course:** 23CSR306 Java Programming - Assignment II (OOP Mini Project), 2026-2027
**Specification:** 2 - Library Management System
**Name:** YOUR NAME HERE
**Roll No:** YOUR ROLL NUMBER HERE

## Problem Statement
Design and implement a Java console application for a Library Management System that manages books, members, borrowing, and returns. The system should follow object-oriented principles and ensure proper tracking of availability.

**Class Requirements:** Book, Author, Member, PremiumMember, BorrowTransaction, ReturnTransaction, Library

**Business Rules**
1. Books can only be borrowed if they are available in stock.
2. When borrowed, stock reduces; when returned, stock updates.
3. Premium members may borrow more books and for longer duration.
4. Fines are applied for late returns.
5. Borrowing and returning transactions must link directly to books.

**Menu Options:** Add Book / Add Member / Borrow Book / Return Book / Display Available Books / Exit

**Expected Output:** borrow/return receipt, updated stock after each transaction, fines (if applicable).

## How to Compile and Run (JDK 8 or later)
```bash
mkdir -p bin
javac -d bin $(find src -name "*.java")
java -cp bin main.LibraryApp
```
In Eclipse: File > Import > Existing Projects into Workspace, open `src/main/LibraryApp.java`, then Run As > Java Application.

## Project Structure
- `model/` - Book, Author, Member, PremiumMember, BorrowTransaction, ReturnTransaction
- `service/` - Library
- `util/` - Validator, InputReader
- `exception/` - LibraryException and its subclasses
- `main/` - LibraryApp (menu)

## Rules Implemented
| | Regular | Premium |
|---|---|---|
| Max books | 3 | 6 |
| Loan period | 14 days | 30 days |
| Fine per late day | Rs. 5 | Rs. 2 |
