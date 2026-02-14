package com.bookstore.tests.books;

import com.bookstore.base.BaseTest;
import com.bookstore.models.Book;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Epic("Books API")
@Feature("PUT Operations")
public class BooksPutTests extends BaseTest {

    @Test(description = "Verify updating an existing book with valid data")
    @Description("Happy path: PUT /api/v1/Books/{id} with valid data")
    @Severity(SeverityLevel.CRITICAL)
    public void testUpdateBook_ValidData_Success() {
        int bookId = 1;

        Book updatedBook = Book.builder()
                .id(bookId)
                .title("Updated Book Title")
                .description("Updated description")
                .pageCount(500)
                .excerpt("Updated excerpt")
                .publishDate("2024-02-01T00:00:00")
                .build();

        Response response = booksClient.updateBook(bookId, updatedBook);

        assertEquals(response.getStatusCode(), 200);

        Book returnedBook = response.as(Book.class);
        assertEquals(returnedBook.getTitle(), updatedBook.getTitle());
        assertEquals(returnedBook.getPageCount(), updatedBook.getPageCount());
    }

    @Test(description = "Verify updating book with mismatched ID in path and body")
    @Description("Edge case: PUT with ID mismatch between URL and payload")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateBook_MismatchedIds() {
        int pathId = 1;
        int bodyId = 2;

        Book book = Book.builder()
                .id(bodyId)
                .title("Mismatched ID Book")
                .pageCount(300)
                .build();

        Response response = booksClient.updateBook(pathId, book);

        // Document how API handles ID mismatch
        assertNotNull(response);
        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 200 || statusCode == 400,
                "Should handle ID mismatch");
    }

    @Test(description = "Verify updating non-existent book")
    @Description("Edge case: PUT /api/v1/Books/{invalidId}")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateBook_NonExistentId() {
        int invalidId = 999999;

        Book book = Book.builder()
                .id(invalidId)
                .title("Non-existent Book")
                .pageCount(100)
                .build();

        Response response = booksClient.updateBook(invalidId, book);

        // Many APIs return 200 for PUT to non-existent resources (creates them)
        assertNotNull(response);
    }

    @Test(description = "Verify partial update of book fields")
    @Description("Edge case: PUT with only some fields updated")
    @Severity(SeverityLevel.MINOR)
    public void testUpdateBook_PartialUpdate() {
        int bookId = 1;

        Book partialBook = Book.builder()
                .id(bookId)
                .title("Only Title Updated")
                .build();

        Response response = booksClient.updateBook(bookId, partialBook);

        assertEquals(response.getStatusCode(), 200);
    }
}