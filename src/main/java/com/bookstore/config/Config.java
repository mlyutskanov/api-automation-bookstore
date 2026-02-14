package com.bookstore.config;

public class Config {
    // Base URL for the API
    public static final String BASE_URL = "https://fakerestapi.azurewebsites.net";

    // API version
    public static final String API_VERSION = "/api/v1";

    // Endpoints
    public static final String BOOKS_ENDPOINT = API_VERSION + "/Books";
    public static final String AUTHORS_ENDPOINT = API_VERSION + "/Authors";

    // Timeouts
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int SOCKET_TIMEOUT = 10000;
}