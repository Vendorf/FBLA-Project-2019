package entities;

import javafx.beans.property.*;

import java.sql.Date;

public class Student {
    //TABLE: students
    //PROPS: id, student_id, first_name, last_name, dob, gender_id, grade_id
    //PK: id
    //FK: gender_id, grade_id

    private IntegerProperty id;
    private IntegerProperty student_id;
    private StringProperty first_name;
    private StringProperty last_name;
    private ObjectProperty<Date> dob;
    private IntegerProperty gender_id;
    private IntegerProperty grade_id;

    private Gender gender;
    private Grade grade;

    public Student() {
        id = new SimpleIntegerProperty();
        student_id = new SimpleIntegerProperty();
        first_name = new SimpleStringProperty();
        last_name = new SimpleStringProperty();
        dob = new SimpleObjectProperty<>();
        gender_id = new SimpleIntegerProperty();
        grade_id = new SimpleIntegerProperty();

        gender = new Gender();
        grade = new Grade();
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

    public int getStudent_id() {
        return student_id.get();
    }

    public IntegerProperty student_idProperty() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id.set(student_id);
    }

    public String getFirst_name() {
        return first_name.get();
    }

    public StringProperty first_nameProperty() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name.set(first_name);
    }

    public String getLast_name() {
        return last_name.get();
    }

    public StringProperty last_nameProperty() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name.set(last_name);
    }

    public Date getDob() {
        return dob.get();
    }

    public ObjectProperty<Date> dobProperty() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob.set(dob);
    }

    public int getGender_id() {
        return gender_id.get();
    }

    public IntegerProperty gender_idProperty() {
        return gender_id;
    }

    public void setGender_id(int gender_id) {
        this.gender_id.set(gender_id);
    }

    public int getGrade_id() {
        return grade_id.get();
    }

    public IntegerProperty grade_idProperty() {
        return grade_id;
    }

    public void setGrade_id(int grade_id) {
        this.grade_id.set(grade_id);
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    @Override
    public String toString(){
        return this.id.get() + " | " + this.student_id.get() + " | " + this.first_name.get() + " | " + this.last_name.get() + " | " + this.dob.get() + " | " + this.gender_id.get() + " | " + this.grade_id.get();
    }
}
