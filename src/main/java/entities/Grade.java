package entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Grade {

    //TABLE: grade_level
    //PROPS: id, grade
    //PK: id

    private IntegerProperty id;
    private StringProperty grade;

    public Grade(){
        id = new SimpleIntegerProperty();
        grade = new SimpleStringProperty();
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

    public String getGrade() {
        return grade.get();
    }

    public StringProperty gradeProperty() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade.set(grade);
    }

    @Override
    public String toString(){
        return this.id.get() + " | " + this.grade.get();
    }
}
