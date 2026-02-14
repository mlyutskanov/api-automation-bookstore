package com.bookstore.tests.authors;

import com.bookstore.base.BaseTest;
import com.bookstore.models.Author;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Epic("Authors API")
@Feature("PUT Operations")
public class AuthorsPutTests extends BaseTest {

    @Test(description = "Verify updating an existing author with valid data")
    @Description("Happy path: PUT /api/v1/Authors/{id} with valid data")
    @Severity(SeverityLevel.CRITICAL)
    public void testUpdateAuthor_ValidData_Success() {
        int authorId = 1;

        Author updatedAuthor = Author.builder()
                .id(authorId)
                .idBook(2)
                .firstName("UpdatedFirstName")
                .lastName("UpdatedLastName")
                .build();

        Response response = authorsClient.updateAuthor(authorId, updatedAuthor);

        assertEquals(response.getStatusCode(), 200);

        Author returnedAuthor = response.as(Author.class);
        assertEquals(returnedAuthor.getFirstName(), updatedAuthor.getFirstName());
        assertEquals(returnedAuthor.getLastName(), updatedAuthor.getLastName());
    }

    @Test(description = "Verify updating author with mismatched ID in path and body")
    @Description("Edge case: PUT with ID mismatch between URL and payload")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateAuthor_MismatchedIds() {
        int pathId = 1;
        int bodyId = 2;

        Author author = Author.builder()
                .id(bodyId)
                .idBook(1)
                .firstName("Mismatched")
                .lastName("Author")
                .build();

        Response response = authorsClient.updateAuthor(pathId, author);

        assertNotNull(response);
        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 200 || statusCode == 400,
                "Should handle ID mismatch");
    }

    @Test(description = "Verify updating non-existent author")
    @Description("Edge case: PUT /api/v1/Authors/{invalidId}")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateAuthor_NonExistentId() {
        int invalidId = 999999;

        Author author = Author.builder()
                .id(invalidId)
                .idBook(1)
                .firstName("Non")
                .lastName("Existent")
                .build();

        Response response = authorsClient.updateAuthor(invalidId, author);

        assertNotNull(response);
    }

    @Test(description = "Verify updating author's book reference")
    @Description("Happy path: Change author's associated book")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateAuthor_ChangeBookReference() {
        int authorId = 1;

        Author author = Author.builder()
                .id(authorId)
                .idBook(5)
                .firstName("Same")
                .lastName("Author")
                .build();

        Response response = authorsClient.updateAuthor(authorId, author);

        assertEquals(response.getStatusCode(), 200);

        Author returnedAuthor = response.as(Author.class);
        assertEquals(returnedAuthor.getIdBook(), 5,
                "Book reference should be updated");
    }

    @Test(description = "Verify partial update of author fields")
    @Description("Edge case: PUT with only some fields updated")
    @Severity(SeverityLevel.MINOR)
    public void testUpdateAuthor_PartialUpdate() {
        int authorId = 1;

        Author partialAuthor = Author.builder()
                .id(authorId)
                .firstName("OnlyFirstNameUpdated")
                .build();

        Response response = authorsClient.updateAuthor(authorId, partialAuthor);

        assertEquals(response.getStatusCode(), 200);
    }
}