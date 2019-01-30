package entities;

import javafx.beans.property.*;

import java.sql.Date;

public class RedemptionInfo {
    //from redemption_codes

    private IntegerProperty id;
    private IntegerProperty book_id;
    private StringProperty redemption_code;
    private IntegerProperty student_id;
    private ObjectProperty<Date> student_id_assign_date;

    private Book book; //should probably have books own redemption info, not the other way around
    //or link it all through students and so not have either know, but have student know both

    public RedemptionInfo(){
        this.id = new SimpleIntegerProperty();
        this.book_id = new SimpleIntegerProperty();
        this.redemption_code = new SimpleStringProperty();
        this.student_id = new SimpleIntegerProperty();
        this.student_id_assign_date = new SimpleObjectProperty<>();
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

    public int getBook_id() {
        return book_id.get();
    }

    public IntegerProperty book_idProperty() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id.set(book_id);
    }

    public String getRedemption_code() {
        return redemption_code.get();
    }

    public StringProperty redemption_codeProperty() {
        return redemption_code;
    }

    public void setRedemption_code(String redemption_code) {
        this.redemption_code.set(redemption_code);
    }

    public int getStudent_id() {
        return student_id.get();
    }

    public IntegerProperty student_idProperty() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id.set(student_id);
    }

    public Date getStudent_id_assign_date() {
        return student_id_assign_date.get();
    }

    public ObjectProperty<Date> student_id_assign_dateProperty() {
        return student_id_assign_date;
    }

    public void setStudent_id_assign_date(Date student_id_assign_date) {
        this.student_id_assign_date.set(student_id_assign_date);
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
