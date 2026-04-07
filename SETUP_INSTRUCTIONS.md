# Setup Instructions for BankTransfer Project

## Problem Fixed
The original error was: **"No suitable driver found for jdbc:mysql://localhost:3306/BankDB"**

This was caused by the missing MySQL JDBC driver in the classpath.

## Solution Implemented

### 1. MySQL JDBC Driver Added
- Downloaded: `mysql-connector-j-8.0.33.jar`
- Location: `lib/mysql-connector-j-8.0.33.jar`

### 2. Code Improvements
- Added explicit MySQL driver loading with error handling
- Replaced `printStackTrace()` with proper error logging
- The code now loads the driver at runtime before attempting database connection

### 3. Configure IntelliJ IDEA to Add JAR to Project

Follow these steps to add the MySQL driver JAR to your IntelliJ project:

1. **Open Project Structure:**
   - File → Project Structure (Ctrl + Alt + Shift + S)

2. **Navigate to Libraries:**
   - Click on "Libraries" in the left panel
   - Click the "+" button to add a new library
   - Select "Java"

3. **Add the JAR File:**
   - Browse to: `D:\OneDrive\Máy tính\IT203B\lib\mysql-connector-j-8.0.33.jar`
   - Click "OK"
   - Name it: "MySQL Connector/J"
   - Click "OK"

4. **Add Library to Module:**
   - In Project Structure, go to "Modules"
   - Select "IT203B" module
   - Go to "Dependencies" tab
   - Click "+" → "Library"
   - Select "MySQL Connector/J"
   - Click "OK" and "Apply"

### 4. Database Requirements

Make sure you have:
- **MySQL Server** running on `localhost:3306`
- **Database**: `BankDB`
- **Table**: `Accounts` with columns:
  - AccountId (VARCHAR)
  - FullName (VARCHAR)
  - Balance (DOUBLE)
- **Stored Procedure**: `sp_UpdateBalance(IN accountId VARCHAR, IN amount DOUBLE)`

### 5. Running the Program

After setup, run: `SS14.BankTransfer`

## Alternative: Using Maven (Recommended for Future Projects)

A `pom.xml` file has been created. If you install Maven, simply run:
```bash
mvn clean install
mvn exec:java -Dexec.mainClass="SS14.BankTransfer"
```

## Files Modified/Created

1. **BankTransfer.java** - Added driver loading code
2. **pom.xml** - Created for Maven dependency management
3. **lib/mysql-connector-j-8.0.33.jar** - MySQL JDBC driver

