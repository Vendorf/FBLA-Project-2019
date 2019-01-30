package entities;

import javafx.beans.property.*;

public class Book {


    private IntegerProperty id;
    private IntegerProperty category_id;
    private StringProperty name;
    private StringProperty author;
    private IntegerProperty publication_year;

    public Book(){
        this.id = new SimpleIntegerProperty();
        this.category_id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.author = new SimpleStringProperty();
        this.publication_year = new SimpleIntegerProperty();
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getCategory_id() {
        return category_id.get();
    }

    public IntegerProperty category_idProperty() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id.set(category_id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAuthor() {
        return author.get();
    }

    public StringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public int getPublication_year() {
        return publication_year.get();
    }

    public IntegerProperty publication_yearProperty() {
        return publication_year;
    }

    public void setPublication_year(int publication_year) {
        this.publication_year.set(publication_year);
    }
}
