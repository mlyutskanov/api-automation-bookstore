package com.bookstore.clients;

import com.bookstore.config.Config;
import com.bookstore.models.Author;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthorsClient {

    @Step("Get all authors")
    public Response getAllAuthors() {
        return given()
                .baseUri(Config.BASE_URL)
                .basePath(Config.AUTHORS_ENDPOINT)
                .log().all()
                .when()
                .get()
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Get author by ID: {id}")
    public Response getAuthorById(int id) {
        return given()
                .baseUri(Config.BASE_URL)
                .basePath(Config.AUTHORS_ENDPOINT + "/" + id)
                .log().all()
                .when()
                .get()
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Create new author")
    public Response createAuthor(Author author) {
        return given()
                .baseUri(Config.BASE_URL)
                .basePath(Config.AUTHORS_ENDPOINT)
                .header("Content-Type", "application/json")
                .body(author)
                .log().all()
                .when()
                .post()
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Update author with ID: {id}")
    public Response updateAuthor(int id, Author author) {
        return given()
                .baseUri(Config.BASE_URL)
                .basePath(Config.AUTHORS_ENDPOINT + "/" + id)
                .header("Content-Type", "application/json")
                .body(author)
                .log().all()
                .when()
                .put()
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Delete author with ID: {id}")
    public Response deleteAuthor(int id) {
        return given()
                .baseUri(Config.BASE_URL)
                .basePath(Config.AUTHORS_ENDPOINT + "/" + id)
                .log().all()
                .when()
                .delete()
                .then()
                .log().all()
                .extract().response();
    }
}