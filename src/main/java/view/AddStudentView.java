package view;

import dao.GenderDAO;
import dao.GradeDAO;
import dao.StudentDAO;
import entities.Gender;
import entities.Grade;
import entities.Student;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddStudentView extends Pane {

    private TextField studentIdField;
    private TextField firstNameField;
    private TextField lastNameField;
    private DatePicker datePicker;
    private ChoiceBox<Gender> genderChoiceBox;
    private ChoiceBox<Grade> gradeChoiceBox;

    private Button addButton;
    private Button cancelButton;

    private BorderPane borderPane;
    private HBox horizontalStore;
    private HBox buttonsStore;

    private final String pattern = "yyyy-MM-dd";
    private final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern(pattern);

    public AddStudentView(){

        this.getStyleClass().add("add-student-view");

        studentIdField = new TextField();
        studentIdField.setPromptText("Student ID");
        firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");
        datePicker = new DatePicker();
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
        datePicker.setPromptText(pattern.toLowerCase());


        //set datepicker format to yyyy-MM-dd
        genderChoiceBox = new ChoiceBox<>();
        genderChoiceBox.setConverter(new StringConverter<Gender>() {
            @Override
            public String toString(Gender gender) {
                return gender.getGender();
            }

            @Override
            public Gender fromString(String s) {
                return null;
            }
        });
        try {
            genderChoiceBox.setItems(GenderDAO.searchGenders());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        gradeChoiceBox = new ChoiceBox<>();
        gradeChoiceBox.setConverter(new StringConverter<Grade>() {
            @Override
            public String toString(Grade grade) {
                return grade.getGrade();
            }

            @Override
            public Grade fromString(String s) {
                return null;
            }
        });
        try {
            gradeChoiceBox.setItems(GradeDAO.searchGrades());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        //set gender and grade choice box options

        horizontalStore = new HBox();
        horizontalStore.getChildren().setAll(studentIdField, firstNameField, lastNameField, datePicker, genderChoiceBox, gradeChoiceBox);
        horizontalStore.prefWidthProperty().bind(this.widthProperty());

        addButton = new Button("Add");
        cancelButton = new Button("Cancel");
        buttonsStore = new HBox();
        buttonsStore.getChildren().addAll(addButton, cancelButton);
        buttonsStore.prefWidthProperty().bind(this.widthProperty());

        borderPane = new BorderPane();
        borderPane.setBottom(buttonsStore);
        borderPane.setCenter(horizontalStore);
        borderPane.prefWidthProperty().bind(this.widthProperty());

        this.getChildren().setAll(borderPane);


        setDelegates();

    }

    private void setDelegates(){
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    int studentId = Integer.parseInt(studentIdField.getText());
                    String firstName = firstNameField.getText();
                    String lastName = lastNameField.getText();
                    Date dob = Date.valueOf(datePicker.getEditor().getText());
                    int genderId = genderChoiceBox.getSelectionModel().getSelectedItem().getId(); //this is optional; add null check
                    int gradeId = gradeChoiceBox.getSelectionModel().getSelectedItem().getId();

                    StudentDAO.insertStudent(studentId, firstName, lastName, dob, genderId, gradeId);
                    //DB update event

                }catch (Exception e){
                    System.out.println("no bueno");
                    //bad shit happened
                }
                //delegate to controller
                    //check if all necessary fields filled
                    //try to add student
                    //post DB_UPDATE event
                    //capture DB_UPDATE and do a refresh on all related windows
                    //perhaps have a popup that asks if user wants to refresh given new data
            }
        });

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
    }


}
