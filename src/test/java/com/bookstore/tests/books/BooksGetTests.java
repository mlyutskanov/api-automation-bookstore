package com.bookstore.tests.books;

import com.bookstore.base.BaseTest;
import com.bookstore.models.Book;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

@Epic("Books API")
@Feature("GET Operations")
public class BooksGetTests extends BaseTest {

    @Test(description = "Verify getting all books returns 200 and non-empty list")
    @Description("Happy path: GET /api/v1/Books should return list of books")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAllBooks_Success() {
        Response response = booksClient.getAllBooks();

        // Assertions
        assertEquals(response.getStatusCode(), 200, "Status code should be 200");

        Book[] books = response.as(Book[].class);
        assertTrue(books.length > 0, "Books list should not be empty");

        // Validate response body structure
        response.then()
                .body("$", not(empty()))
                .body("[0].id", notNullValue())
                .body("[0].title", notNullValue());
    }

    @Test(description = "Verify getting a specific book by valid ID")
    @Description("Happy path: GET /api/v1/Books/{id} with valid ID")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetBookById_ValidId_Success() {
        int bookId = 1;
        Response response = booksClient.getBookById(bookId);

        assertEquals(response.getStatusCode(), 200);

        Book book = response.as(Book.class);
        assertEquals(book.getId(), bookId, "Book ID should match requested ID");
        assertNotNull(book.getTitle(), "Book title should not be null");
    }

    @Test(description = "Verify getting book with non-existent ID returns 404")
    @Description("Edge case: GET /api/v1/Books/{id} with invalid ID")
    @Severity(SeverityLevel.NORMAL)
    public void testGetBookById_InvalidId_ReturnsNotFound() {
        int invalidId = 999999;
        Response response = booksClient.getBookById(invalidId);

        assertEquals(response.getStatusCode(), 404,
                "Should return 404 for non-existent book");
    }

    @Test(description = "Verify getting book with ID zero")
    @Description("Edge case: GET /api/v1/Books/0")
    @Severity(SeverityLevel.MINOR)
    public void testGetBookById_ZeroId() {
        Response response = booksClient.getBookById(0);

        // Document actual behavior
        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 200 || statusCode == 404,
                "Status should be either 200 or 404 for ID zero");
    }

    @Test(description = "Verify getting book with negative ID")
    @Description("Edge case: GET /api/v1/Books/{negativeId}")
    @Severity(SeverityLevel.MINOR)
    public void testGetBookById_NegativeId() {
        Response response = booksClient.getBookById(-1);

        // Document actual behavior
        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 400 || statusCode == 404,
                "Should handle negative ID appropriately");
    }
}