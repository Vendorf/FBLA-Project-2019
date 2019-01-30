package entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Gender {
    //TABLE: genders
    //PROPS: id, gender
    //PK: id

    private IntegerProperty id;
    private StringProperty gender;

    public Gender(){
        id = new SimpleIntegerProperty();
        gender = new SimpleStringProperty();
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

    public String getGender() {
        return gender.get();
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    @Override
    public String toString(){
        return this.id.get() + " | " + this.gender.get();
    }
}
