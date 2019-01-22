package view;

import entities.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class StudentTableView extends TableView<Student> {

    private TableColumn<Student, Integer> idColumn;
    private TableColumn<Student, Integer> studentIdColumn;
    private TableColumn<Student, String> firstNameColumn;
    private TableColumn<Student, String> lastNameColumn;
    private TableColumn<Student, String> dobColumn;

    private TableColumn<Student, String> genderColumn;
    private TableColumn<Student, String> gradeColumn;

    //private TableColumn<Student, S>

    public StudentTableView(){
        idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));
        studentIdColumn = new TableColumn<>("Student ID");
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<Student, Integer>("student_id"));
        firstNameColumn = new TableColumn<>("First");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("first_name"));
        lastNameColumn = new TableColumn<>("Last");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("last_name"));
        dobColumn = new TableColumn<>("DOB");
        dobColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("dob"));

        genderColumn = new TableColumn<>("Gender");
        genderColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student, String> studentStringCellDataFeatures) {
                return new SimpleStringProperty(studentStringCellDataFeatures.getValue().getGender().getGender());
            }
        });
        gradeColumn = new TableColumn<>("Year");
        gradeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student, String> studentStringCellDataFeatures) {
                return new SimpleStringProperty(studentStringCellDataFeatures.getValue().getGrade().getGrade());
            }
        });
        /*
        * .setCellValueFactory(new Callback<CellDataFeatures<Enseignant,String>,ObservableValue<String>>(){

                @Override
                public ObservableValue<String> call(CellDataFeatures<Enseignant, String> param) {
                    return new SimpleStringProperty(param.getValue().getMatiere().getIntitule());
                }
            });
        * */

        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.getColumns().setAll(idColumn, studentIdColumn, firstNameColumn, lastNameColumn, dobColumn, genderColumn, gradeColumn);
    }

    public void setData(ObservableList<Student> students){
        this.setItems(students);
    }

}
