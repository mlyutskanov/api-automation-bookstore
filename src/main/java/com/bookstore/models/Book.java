package com.bookstore.models;

public class Book {
    private int id;
    private String title;
    private String description;
    private int pageCount;
    private String excerpt;
    private String publishDate;

    public Book() {}

    public Book(int id, String title, String description, int pageCount, String excerpt, String publishDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pageCount = pageCount;
        this.excerpt = excerpt;
        this.publishDate = publishDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private String title;
        private String description;
        private int pageCount;
        private String excerpt;
        private String publishDate;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder pageCount(int pageCount) {
            this.pageCount = pageCount;
            return this;
        }

        public Builder excerpt(String excerpt) {
            this.excerpt = excerpt;
            return this;
        }

        public Builder publishDate(String publishDate) {
            this.publishDate = publishDate;
            return this;
        }

        public Book build() {
            return new Book(id, title, description, pageCount, excerpt, publishDate);
        }
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pageCount=" + pageCount +
                ", excerpt='" + excerpt + '\'' +
                ", publishDate='" + publishDate + '\'' +
                '}';
    }
}