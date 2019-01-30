package view;

import dao.StudentDAO;
import entities.Student;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import util.ToggleSwitch;

import java.sql.SQLException;

public class HomeView extends Pane {

    //Have edit/view modes; when edit engaged it replaces the bottom pane with an add student one and allows editing of the table directly
    //Also have an Add... button to add a student without engaging edit mode


    private SplitPane mainBox;
    private VBox topRoot;
    private StudentDetailsView detailsView;
    private StudentTableView studentTableView;
    private HBox buttonPane;
    private AddStudentView addStudentView;


    public HomeView(){

        this.getStyleClass().add("home-view");

        mainBox = new SplitPane();
        mainBox.setOrientation(Orientation.VERTICAL);
        mainBox.setDividerPositions(0.7);
        topRoot = new VBox();

        detailsView = new StudentDetailsView();

        detailsView.prefWidthProperty().bind(mainBox.widthProperty());

        studentTableView = new StudentTableView();
        try {
            studentTableView.setData(StudentDAO.searchStudents());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



        mainBox.getItems().setAll(topRoot, detailsView);

        studentTableView.prefWidthProperty().bind(mainBox.widthProperty());



        mainBox.prefHeightProperty().bind(this.heightProperty());
        mainBox.prefWidthProperty().bind(this.widthProperty());
        this.getChildren().add(mainBox);


        Button b = new Button("Save");
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for(Student student : studentTableView.getEditedStudents()){
                    try {
                        StudentDAO.updateStudent(student);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                //stv print is incorrect as editedStudents and the current items are not actually yet updated due to no purge being done as with
                //stv.setData in the reset button call. You wouldn't want to reset if something failed, but would if everything succeeded
                //consider other edgecases before doing anything

                studentTableView.print(); //a save button would be getting items and trying to perform updates/additions/etc

            }
        });

        Button reset = new Button("Reset");
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    studentTableView.setData(StudentDAO.searchStudents()); //this will allow for a reset functionality
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        reset.setLayoutX(35);

        buttonPane = new HBox();
        final Pane spacer = new Pane();
        buttonPane.getChildren().addAll(spacer, b, reset);
        //buttonPane.setPrefHeight(30);
        buttonPane.setHgrow(spacer, Priority.ALWAYS);

        addStudentView = new AddStudentView();
        addStudentView.prefWidthProperty().bind(topRoot.widthProperty());

        topRoot.getChildren().addAll(buttonPane, addStudentView, studentTableView);
        topRoot.prefWidthProperty().bind(mainBox.widthProperty());
        topRoot.setVgrow(studentTableView, Priority.ALWAYS);


    }

    public SplitPane getMainBox() {
        return mainBox;
    }

    public StudentDetailsView getDetailsView() {
        return detailsView;
    }

    public StudentTableView getStudentTableView() {
        return studentTableView;
    }
}
