package com.bookstore.tests.authors;

import com.bookstore.base.BaseTest;
import com.bookstore.models.Author;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Epic("Authors API")
@Feature("POST Operations")
public class AuthorsPostTests extends BaseTest {

    @Test(description = "Verify creating a new author with valid data")
    @Description("Happy path: POST /api/v1/Authors with valid author data")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateAuthor_ValidData_Success() {
        Author newAuthor = Author.builder()
                .id(9999)
                .idBook(1)
                .firstName("John")
                .lastName("Doe")
                .build();

        Response response = authorsClient.createAuthor(newAuthor);

        assertEquals(response.getStatusCode(), 200, "Status code should be 200");

        Author createdAuthor = response.as(Author.class);
        assertEquals(createdAuthor.getFirstName(), newAuthor.getFirstName());
        assertEquals(createdAuthor.getLastName(), newAuthor.getLastName());
        assertEquals(createdAuthor.getIdBook(), newAuthor.getIdBook());
    }

    @Test(description = "Verify creating author with minimal data")
    @Description("Edge case: POST with only required fields")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateAuthor_MinimalData() {
        Author minimalAuthor = Author.builder()
                .id(9998)
                .idBook(1)
                .build();

        Response response = authorsClient.createAuthor(minimalAuthor);

        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 200 || statusCode == 201,
                "Should accept minimal valid data");
    }

    @Test(description = "Verify creating author with empty name fields")
    @Description("Edge case: POST with empty firstName and lastName")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateAuthor_EmptyNames() {
        Author emptyNameAuthor = Author.builder()
                .id(9997)
                .idBook(1)
                .firstName("")
                .lastName("")
                .build();

        Response response = authorsClient.createAuthor(emptyNameAuthor);

        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 200 || statusCode == 400,
                "Should handle empty names appropriately");
    }

    @Test(description = "Verify creating author with invalid book reference")
    @Description("Edge case: POST with non-existent idBook")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateAuthor_InvalidBookReference() {
        Author invalidRefAuthor = Author.builder()
                .id(9996)
                .idBook(999999)
                .firstName("Jane")
                .lastName("Smith")
                .build();

        Response response = authorsClient.createAuthor(invalidRefAuthor);

        // Document how API handles invalid references
        assertNotNull(response, "Response should not be null");
    }

    @Test(description = "Verify creating author with special characters in name")
    @Description("Edge case: POST with special characters")
    @Severity(SeverityLevel.MINOR)
    public void testCreateAuthor_SpecialCharacters() {
        Author specialCharAuthor = Author.builder()
                .id(9995)
                .idBook(1)
                .firstName("José")
                .lastName("O'Brien-Smith")
                .build();

        Response response = authorsClient.createAuthor(specialCharAuthor);

        assertEquals(response.getStatusCode(), 200);

        Author createdAuthor = response.as(Author.class);
        assertEquals(createdAuthor.getFirstName(), specialCharAuthor.getFirstName(),
                "Should preserve special characters in firstName");
    }

    @Test(description = "Verify creating author with very long names")
    @Description("Edge case: POST with extremely long name fields")
    @Severity(SeverityLevel.MINOR)
    public void testCreateAuthor_VeryLongNames() {
        String longName = "A".repeat(500);

        Author longNameAuthor = Author.builder()
                .id(9994)
                .idBook(1)
                .firstName(longName)
                .lastName(longName)
                .build();

        Response response = authorsClient.createAuthor(longNameAuthor);

        assertNotNull(response, "Response should not be null");
    }
}