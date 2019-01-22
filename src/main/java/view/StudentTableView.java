package view;

import entities.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.DateCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import util.DatePickerCell;

import java.util.Date;

public class StudentTableView extends TableView<Student> {

    //OP A
    //factory does not directly apply to the column
    //instead, it is used in order to populate the true factories which are Student, Textbox



    private TableColumn<Student, Integer> idColumn;
    private TableColumn<Student, Integer> studentIdColumn;
    private TableColumn nameColumn;
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
        nameColumn = new TableColumn("Name");
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

        idColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

//straight garbage class; needs rewrite
//        dobColumn.setCellFactory(new Callback<TableColumn<Student, String>, TableCell<Student, String>>() {
//            @Override
//            public TableCell<Student, String> call(TableColumn<Student, String> studentStringTableColumn) {
//                DatePickerCell datePick = new DatePickerCell();
//                return datePick;
//            }
//        });

        this.setEditable(true);

        /*
        * .setCellValueFactory(new Callback<CellDataFeatures<Enseignant,String>,ObservableValue<String>>(){

                @Override
                public ObservableValue<String> call(CellDataFeatures<Enseignant, String> param) {
                    return new SimpleStringProperty(param.getValue().getMatiere().getIntitule());
                }
            });
        * */

        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        nameColumn.getColumns().setAll(firstNameColumn, lastNameColumn);
        this.getColumns().setAll(idColumn, studentIdColumn, nameColumn, dobColumn, genderColumn, gradeColumn);



    }

    public void setData(ObservableList<Student> students){
        this.setItems(students);
    }

}
