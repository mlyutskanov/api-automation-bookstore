package com.bookstore.clients;

import com.bookstore.config.Config;
import com.bookstore.models.Book;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BooksClient {

    @Step("Get all books")
    public Response getAllBooks() {
        return given()
                .baseUri(Config.BASE_URL)
                .basePath(Config.BOOKS_ENDPOINT)
                .log().all()
                .when()
                .get()
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Get book by ID: {id}")
    public Response getBookById(int id) {
        return given()
                .baseUri(Config.BASE_URL)
                .basePath(Config.BOOKS_ENDPOINT + "/" + id)
                .log().all()
                .when()
                .get()
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Create new book")
    public Response createBook(Book book) {
        return given()
                .baseUri(Config.BASE_URL)
                .basePath(Config.BOOKS_ENDPOINT)
                .header("Content-Type", "application/json")
                .body(book)
                .log().all()
                .when()
                .post()
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Update book with ID: {id}")
    public Response updateBook(int id, Book book) {
        return given()
                .baseUri(Config.BASE_URL)
                .basePath(Config.BOOKS_ENDPOINT + "/" + id)
                .header("Content-Type", "application/json")
                .body(book)
                .log().all()
                .when()
                .put()
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Delete book with ID: {id}")
    public Response deleteBook(int id) {
        return given()
                .baseUri(Config.BASE_URL)
                .basePath(Config.BOOKS_ENDPOINT + "/" + id)
                .log().all()
                .when()
                .delete()
                .then()
                .log().all()
                .extract().response();
    }
}