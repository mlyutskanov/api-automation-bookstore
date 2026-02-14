package com.bookstore.models;

public class Author {
    private int id;
    private int idBook;
    private String firstName;
    private String lastName;

    public Author() {}

    public Author(int id, int idBook, String firstName, String lastName) {
        this.id = id;
        this.idBook = idBook;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private int idBook;
        private String firstName;
        private String lastName;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder idBook(int idBook) {
            this.idBook = idBook;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Author build() {
            return new Author(id, idBook, firstName, lastName);
        }
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", idBook=" + idBook +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}