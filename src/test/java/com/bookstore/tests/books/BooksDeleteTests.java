package com.bookstore.tests.books;

import com.bookstore.base.BaseTest;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Epic("Books API")
@Feature("DELETE Operations")
public class BooksDeleteTests extends BaseTest {

    @Test(description = "Verify deleting an existing book")
    @Description("Happy path: DELETE /api/v1/Books/{id}")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeleteBook_ValidId_Success() {
        int bookId = 1;
        Response response = booksClient.deleteBook(bookId);

        assertEquals(response.getStatusCode(), 200,
                "Should successfully delete book");
    }

    @Test(description = "Verify deleting non-existent book")
    @Description("Edge case: DELETE /api/v1/Books/{invalidId}")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteBook_NonExistentId() {
        int invalidId = 999999;
        Response response = booksClient.deleteBook(invalidId);

        // Some APIs return 200, others 404 for deleting non-existent resources
        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 200 || statusCode == 404,
                "Should handle non-existent ID appropriately");
    }

    @Test(description = "Verify deleting book with negative ID")
    @Description("Edge case: DELETE with negative ID")
    @Severity(SeverityLevel.MINOR)
    public void testDeleteBook_NegativeId() {
        Response response = booksClient.deleteBook(-1);

        assertNotNull(response, "Response should not be null");
    }

    @Test(description = "Verify deleting the same book twice")
    @Description("Edge case: Double deletion (idempotency test)")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteBook_Twice() {
        int bookId = 5;

        // First deletion
        Response firstResponse = booksClient.deleteBook(bookId);
        assertEquals(firstResponse.getStatusCode(), 200);

        // Second deletion (should be idempotent or return 404)
        Response secondResponse = booksClient.deleteBook(bookId);
        int statusCode = secondResponse.getStatusCode();
        assertTrue(statusCode == 200 || statusCode == 404,
                "Second delete should be idempotent or return 404");
    }
}