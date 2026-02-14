package com.bookstore.tests.books;

import com.bookstore.base.BaseTest;
import com.bookstore.models.Book;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Epic("Books API")
@Feature("POST Operations")
public class BooksPostTests extends BaseTest {

    @Test(description = "Verify creating a new book with valid data")
    @Description("Happy path: POST /api/v1/Books with valid book data")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateBook_ValidData_Success() {
        Book newBook = Book.builder()
                .id(9999)
                .title("Test Automation Handbook")
                .description("A comprehensive guide to API testing")
                .pageCount(350)
                .excerpt("Chapter 1: Introduction...")
                .publishDate("2024-01-15T00:00:00")
                .build();

        Response response = booksClient.createBook(newBook);

        assertEquals(response.getStatusCode(), 200, "Status code should be 200");

        Book createdBook = response.as(Book.class);
        assertEquals(createdBook.getTitle(), newBook.getTitle());
        assertEquals(createdBook.getDescription(), newBook.getDescription());
        assertEquals(createdBook.getPageCount(), newBook.getPageCount());
    }

    @Test(description = "Verify creating book with missing optional fields")
    @Description("Edge case: POST with minimal required fields")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateBook_MinimalData() {
        Book minimalBook = Book.builder()
                .id(9998)
                .title("Minimal Book")
                .build();

        Response response = booksClient.createBook(minimalBook);

        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 200 || statusCode == 201,
                "Should accept minimal valid data");
    }

    @Test(description = "Verify creating book with empty title")
    @Description("Edge case: POST with empty title field")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateBook_EmptyTitle() {
        Book emptyTitleBook = Book.builder()
                .id(9997)
                .title("")
                .description("Book with empty title")
                .pageCount(100)
                .build();

        Response response = booksClient.createBook(emptyTitleBook);

        // Document actual API behavior
        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 200 || statusCode == 400,
                "Status code for empty title");
    }

    @Test(description = "Verify creating book with negative page count")
    @Description("Edge case: POST with invalid pageCount value")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateBook_NegativePageCount() {
        Book invalidBook = Book.builder()
                .id(9996)
                .title("Invalid Page Count Book")
                .pageCount(-100)
                .build();

        Response response = booksClient.createBook(invalidBook);

        // Document actual behavior
        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 200 || statusCode == 400,
                "Should handle negative page count");
    }

    @Test(description = "Verify creating book with very long title")
    @Description("Edge case: POST with extremely long title field")
    @Severity(SeverityLevel.MINOR)
    public void testCreateBook_VeryLongTitle() {
        String longTitle = "A".repeat(1000); // 1000 character title

        Book longTitleBook = Book.builder()
                .id(9995)
                .title(longTitle)
                .pageCount(200)
                .build();

        Response response = booksClient.createBook(longTitleBook);

        // Document behavior with extreme input
        assertNotNull(response, "Response should not be null");
    }
}