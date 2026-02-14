# API Automation Testing - Online Bookstore

Comprehensive API test automation framework for the FakeRestAPI Bookstore using Java 21, RestAssured, TestNG, and Allure Reports.

## Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Setup Instructions](#setup-instructions)
- [Running Tests](#running-tests)
- [Viewing Reports](#viewing-reports)
- [CI/CD Pipeline](#cicd-pipeline)
- [Test Coverage](#test-coverage)
- [Code Quality](#code-quality)
- [Troubleshooting](#troubleshooting)

## Overview

This project automates testing for the FakeRestAPI Bookstore, covering both Books and Authors endpoints. The framework is designed with maintainability, scalability, and clean code principles in mind.

**API Base URL:** `https://fakerestapi.azurewebsites.net`

**Tested Endpoints:**
- Books API: GET, POST, PUT, DELETE operations
- Authors API: GET, POST, PUT, DELETE operations

## Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 21      | Programming language |
| Maven | 3.9+    | Build and dependency management |
| RestAssured | 5.4.0   | API testing library |
| TestNG | 7.9.0   | Test execution framework |
| Allure | 2.25.0  | Test reporting |
| Gson | 2.10.1  | JSON serialization/deserialization |
| SLF4J | 2.0.12  | Logging framework |
| GitHub Actions | -       | CI/CD pipeline |

## Project Structure
```
api-automation-bookstore/
├── src/
│   ├── main/java/com/bookstore/
│   │   ├── clients/         # API client classes (BooksClient, AuthorsClient)
│   │   │   ├── BooksClient.java
│   │   │   └── AuthorsClient.java
│   │   ├── config/          # Configuration (URLs, endpoints)
│   │   │   └── Config.java
│   │   └── models/          # POJO classes (Book, Author)
│   │       ├── Book.java
│   │       └── Author.java
│   └── test/java/com/bookstore/
│       ├── base/            # Base test class
│       │   └── BaseTest.java
│       └── tests/
│           ├── books/       # Books API tests
│           │   ├── BooksGetTests.java
│           │   ├── BooksPostTests.java
│           │   ├── BooksPutTests.java
│           │   └── BooksDeleteTests.java
│           └── authors/     # Authors API tests
│               ├── AuthorsGetTests.java
│               ├── AuthorsPostTests.java
│               ├── AuthorsPutTests.java
│               └── AuthorsDeleteTests.java
├── .github/workflows/       # CI/CD configuration
│   └── api-tests.yml
├── pom.xml                  # Maven dependencies
├── testng.xml              # TestNG suite configuration
├── .gitignore
└── README.md
```

## Prerequisites

- **Java JDK 21** or higher
- **Maven 3.9+**
- **Git**
- **IDE** (IntelliJ IDEA recommended)

### Verify installations:
```bash
java -version    # Should show Java 21
mvn -version     # Should show Maven 3.9+
git --version
```

## Setup Instructions

### 1. Clone the repository
```bash
git clone https://github.com/mlyutskanov/api-automation-bookstore.git
cd api-automation-bookstore
```

### 2. Install dependencies
```bash
mvn clean install -DskipTests
```

This will:
- Download all Maven dependencies
- Compile the project
- Skip test execution

### 3. Verify setup

Run a single test to verify everything works:
```bash
mvn clean test -Dtest=BooksGetTests#testGetAllBooks_Success
```

Expected output:
```
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## Running Tests

### Run all tests
```bash
mvn clean test
```

Expected output:
```
Tests run: 40, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
Time: ~10 seconds
```

### Run specific test suite
```bash
# Books tests only
mvn clean test -Dtest=com.bookstore.tests.books.*

# Authors tests only
mvn clean test -Dtest=com.bookstore.tests.authors.*
```

### Run specific test class
```bash
mvn clean test -Dtest=BooksGetTests
```

### Run specific test method
```bash
mvn clean test -Dtest=BooksGetTests#testGetAllBooks_Success
```

### Run with parallel execution

TestNG is configured to run test classes in parallel (2 threads):
```bash
mvn clean test
```

To change thread count, edit `testng.xml`:
```xml
<suite name="..." thread-count="4">
```

### Run with detailed logging
```bash
mvn clean test -X
```

## Viewing Reports

### Allure Report (Recommended)

**Generate and serve report:**
```bash
mvn allure:serve
```

This will:
1. Generate the Allure report
2. Start a local web server
3. Open the report in your default browser

**Or generate static report:**
```bash
# Generate report
mvn allure:report

# Report will be in: target/site/allure-maven-plugin/index.html
```

**Allure Report Features:**
- Dashboard with test statistics
- Trend graphs (after multiple runs)
-  Detailed test steps with request/response logs
- Timeline view of test execution
- Test categorization by Epic/Feature/Severity

### TestNG HTML Report

After running tests, open:
```
target/surefire-reports/index.html
```

Or in PowerShell:
```powershell
start target\surefire-reports\index.html
```

## CI/CD Pipeline

### GitHub Actions

The pipeline automatically runs on:
- ✅ Every push to `main` or `develop` branches
- ✅ Every pull request to `main`
- ✅ Daily at 2 AM UTC (scheduled)
- ✅ Manual trigger via GitHub UI

**Workflow Configuration:** `.github/workflows/api-tests.yml`

**What it does:**
1. Checks out code
2. Sets up Java 21
3. Caches Maven dependencies
4. Runs all tests
5. Generates Allure report
6. Uploads test results as artifacts
7. Deploys report to GitHub Pages (on main branch)

### View Pipeline Results

1. Go to repository on GitHub
2. Click the **Actions** tab
3. Select the latest workflow run from main branch
4. Follow the link from Test Result section

### GitHub Pages Deployment

Reports are automatically deployed to:
```
https://github.com/mlyutskanov/api-automation-bookstore
```

**To enable GitHub Pages:**
1. Go to repository **Settings** → **Pages**
2. Source: Select `gh-pages` branch and choose `root`
3. Save

### Manual Pipeline Trigger

1. Go to **Actions** tab in GitHub
2. Select **API Automation Tests** workflow
3. Click **Run workflow** button
4. Choose `main` branch and click **Run workflow**

## Test Coverage

### Books API Tests

| Endpoint | Operation | Happy Path | Edge Cases | Total Tests |
|----------|-----------|------------|------------|-------------|
| /api/v1/Books | GET All | ✅ | ✅ | 1 |
| /api/v1/Books/{id} | GET by ID | ✅ | ✅ (invalid, zero, negative ID) | 4 |
| /api/v1/Books | POST | ✅ | ✅ (empty fields, invalid data, long strings) | 5 |
| /api/v1/Books/{id} | PUT | ✅ | ✅ (ID mismatch, non-existent, partial update) | 4 |
| /api/v1/Books/{id} | DELETE | ✅ | ✅ (non-existent, negative ID, idempotency) | 4 |

**Subtotal: 18 tests**

### Authors API Tests

| Endpoint | Operation | Happy Path | Edge Cases | Total Tests |
|----------|-----------|------------|------------|-------------|
| /api/v1/Authors | GET All | ✅ | ✅ | 1 |
| /api/v1/Authors/{id} | GET by ID | ✅ | ✅ (invalid, zero, negative ID, data integrity) | 5 |
| /api/v1/Authors | POST | ✅ | ✅ (empty names, invalid refs, special chars, long names) | 6 |
| /api/v1/Authors/{id} | PUT | ✅ | ✅ (ID mismatch, non-existent, ref change, partial) | 5 |
| /api/v1/Authors/{id} | DELETE | ✅ | ✅ (non-existent, negative, zero ID, idempotency) | 5 |

**Subtotal: 22 tests**

### Total Test Count: **40 Tests**

### Test Severity Distribution

- **Critical** (Core functionality): 8 tests
- **Normal** (Important validations): 22 tests
- **Minor** (Edge cases): 10 tests

### Test Categories Covered

✅ **Happy Path Testing**
- All CRUD operations with valid data
- Expected successful responses

✅ **Boundary Testing**
- Zero IDs, negative IDs
- Empty strings, null values
- Very long strings (1000+ characters)

✅ **Negative Testing**
- Non-existent resource IDs
- Invalid data types
- Malformed requests

✅ **Data Integrity**
- ID mismatches
- Foreign key references
- Special characters handling

✅ **Idempotency Testing**
- Multiple DELETE operations
- Duplicate requests


## Code Quality

✅ **SOLID Principles**
- **Single Responsibility**: Each class has one clear purpose
    - `BooksClient` - handles Books API calls only
    - `Book` model - represents Book data only
    - Test classes - one feature per class
- **Open/Closed**: Easy to extend (add new endpoints) without modifying existing code
- **Dependency Inversion**: Tests depend on abstractions (Client interfaces)

✅ **Clean Code**
- Meaningful names: `testGetBookById_ValidId_Success()`
- Small, focused methods
- Clear test descriptions with `@Description`
- Proper package structure

✅ **DRY**
- `BaseTest` class for common setup
- Reusable client classes
- Centralized configuration in `Config.java`
- Builder pattern for object creation

### Test Design

✅ **Comprehensive Coverage**
- Happy paths (expected behavior)
- Edge cases (boundary conditions)
- Negative scenarios (error handling)

✅ **Maintainability**
- Client Pattern (like Page Object Model)
- Separation of test data and test logic
- Configurable test suites
- Clear test naming convention

✅ **Reporting**
- Allure annotations (`@Epic`, `@Feature`, `@Step`)
- Detailed logging with RestAssured
- Clear assertion messages
- Test categorization by severity

✅ **Performance**
- Parallel test execution
- Efficient API calls
- No unnecessary waits

## Troubleshooting

### Common Issues

**Issue:** Tests fail with connection timeout
```bash
# Solution: Check internet connection and API availability
curl https://fakerestapi.azurewebsites.net/api/v1/Books
```

**Issue:** Compilation errors
```bash
# Solution: Clean and rebuild
mvn clean compile
```

**Issue:** Allure command not found
```bash
# Solution: Install Allure

# macOS
brew install allure

# Windows (using Scoop)
scoop install allure

# Linux
wget https://github.com/allure-framework/allure2/releases/download/2.25.0/allure-2.25.0.tgz
tar -zxvf allure-2.25.0.tgz
sudo mv allure-2.25.0 /opt/allure
export PATH=$PATH:/opt/allure/bin
```

**Issue:** Java version mismatch
```bash
# Verify Java version
java -version

# Should show Java 21
# If not, update JAVA_HOME environment variable
```


## Contact

Momchil Lyutskanov - mlyutskanov84@gmail.com

Project Link: [https://github.com/mlyutskanov/api-automation-bookstore](https://github.com/mlyutskanov/api-automation-bookstore)

## License

This project is created for assessment purposes.
