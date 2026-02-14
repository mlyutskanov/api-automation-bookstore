package com.bookstore.base;

import com.bookstore.clients.AuthorsClient;
import com.bookstore.clients.BooksClient;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected BooksClient booksClient;
    protected AuthorsClient authorsClient;

    @BeforeClass
    public void setup() {
        booksClient = new BooksClient();
        authorsClient = new AuthorsClient();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}