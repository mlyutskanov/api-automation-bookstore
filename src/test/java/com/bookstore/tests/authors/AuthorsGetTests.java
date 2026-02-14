package com.bookstore.tests.authors;

import com.bookstore.base.BaseTest;
import com.bookstore.models.Author;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

@Epic("Authors API")
@Feature("GET Operations")
public class AuthorsGetTests extends BaseTest {

    @Test(description = "Verify getting all authors returns 200 and non-empty list")
    @Description("Happy path: GET /api/v1/Authors should return list of authors")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAllAuthors_Success() {
        Response response = authorsClient.getAllAuthors();

        assertEquals(response.getStatusCode(), 200, "Status code should be 200");

        Author[] authors = response.as(Author[].class);
        assertTrue(authors.length > 0, "Authors list should not be empty");

        // Validate response structure
        response.then()
                .body("$", not(empty()))
                .body("[0].id", notNullValue())
                .body("[0].firstName", notNullValue());
    }

    @Test(description = "Verify getting a specific author by valid ID")
    @Description("Happy path: GET /api/v1/Authors/{id} with valid ID")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAuthorById_ValidId_Success() {
        int authorId = 1;
        Response response = authorsClient.getAuthorById(authorId);

        assertEquals(response.getStatusCode(), 200);

        Author author = response.as(Author.class);
        assertEquals(author.getId(), authorId, "Author ID should match requested ID");
        assertNotNull(author.getFirstName(), "First name should not be null");
    }

    @Test(description = "Verify getting author with non-existent ID returns 404")
    @Description("Edge case: GET /api/v1/Authors/{id} with invalid ID")
    @Severity(SeverityLevel.NORMAL)
    public void testGetAuthorById_InvalidId_ReturnsNotFound() {
        int invalidId = 999999;
        Response response = authorsClient.getAuthorById(invalidId);

        assertEquals(response.getStatusCode(), 404,
                "Should return 404 for non-existent author");
    }

    @Test(description = "Verify getting author with ID zero")
    @Description("Edge case: GET /api/v1/Authors/0")
    @Severity(SeverityLevel.MINOR)
    public void testGetAuthorById_ZeroId() {
        Response response = authorsClient.getAuthorById(0);

        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 200 || statusCode == 404,
                "Status should be either 200 or 404 for ID zero");
    }

    @Test(description = "Verify getting author with negative ID")
    @Description("Edge case: GET /api/v1/Authors/{negativeId}")
    @Severity(SeverityLevel.MINOR)
    public void testGetAuthorById_NegativeId() {
        Response response = authorsClient.getAuthorById(-1);

        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 400 || statusCode == 404,
                "Should handle negative ID appropriately");
    }

    @Test(description = "Verify author data contains valid book reference")
    @Description("Data integrity: Check idBook field is valid")
    @Severity(SeverityLevel.NORMAL)
    public void testGetAuthorById_ValidBookReference() {
        int authorId = 1;
        Response response = authorsClient.getAuthorById(authorId);

        assertEquals(response.getStatusCode(), 200);

        Author author = response.as(Author.class);
        assertTrue(author.getIdBook() >= 0,
                "Book reference ID should be non-negative");
    }
}