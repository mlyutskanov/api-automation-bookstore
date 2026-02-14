package com.bookstore.tests.authors;

import com.bookstore.base.BaseTest;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Epic("Authors API")
@Feature("DELETE Operations")
public class AuthorsDeleteTests extends BaseTest {

    @Test(description = "Verify deleting an existing author")
    @Description("Happy path: DELETE /api/v1/Authors/{id}")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeleteAuthor_ValidId_Success() {
        int authorId = 1;
        Response response = authorsClient.deleteAuthor(authorId);

        assertEquals(response.getStatusCode(), 200,
                "Should successfully delete author");
    }

    @Test(description = "Verify deleting non-existent author")
    @Description("Edge case: DELETE /api/v1/Authors/{invalidId}")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteAuthor_NonExistentId() {
        int invalidId = 999999;
        Response response = authorsClient.deleteAuthor(invalidId);

        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 200 || statusCode == 404,
                "Should handle non-existent ID appropriately");
    }

    @Test(description = "Verify deleting author with negative ID")
    @Description("Edge case: DELETE with negative ID")
    @Severity(SeverityLevel.MINOR)
    public void testDeleteAuthor_NegativeId() {
        Response response = authorsClient.deleteAuthor(-1);

        assertNotNull(response, "Response should not be null");
    }

    @Test(description = "Verify deleting the same author twice")
    @Description("Edge case: Double deletion (idempotency test)")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteAuthor_Twice() {
        int authorId = 5;

        // First deletion
        Response firstResponse = authorsClient.deleteAuthor(authorId);
        assertEquals(firstResponse.getStatusCode(), 200);

        // Second deletion
        Response secondResponse = authorsClient.deleteAuthor(authorId);
        int statusCode = secondResponse.getStatusCode();
        assertTrue(statusCode == 200 || statusCode == 404,
                "Second delete should be idempotent or return 404");
    }

    @Test(description = "Verify deleting author with zero ID")
    @Description("Edge case: DELETE /api/v1/Authors/0")
    @Severity(SeverityLevel.MINOR)
    public void testDeleteAuthor_ZeroId() {
        Response response = authorsClient.deleteAuthor(0);

        assertNotNull(response, "Response should not be null");
    }
}