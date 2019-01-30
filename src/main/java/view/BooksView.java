package view;

import dao.StudentDAO;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.sql.SQLException;

public class BooksView extends Pane {

    private Button button;

    public BooksView(){
//        button = new Button("BooksView");
//        button.prefWidthProperty().bind(this.widthProperty());
//        button.prefHeightProperty().bind(this.heightProperty());
//        this.getChildren().add(button);

//        StudentTreeTableView stv = new StudentTreeTableView();
//        try {
//            stv.setData(StudentDAO.searchStudents());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

//        StudentDetailsView stv = new StudentDetailsView();
//
//        stv.prefHeightProperty().bind(this.heightProperty());
//        stv.prefWidthProperty().bind(this.widthProperty());
//        this.getChildren().addAll(stv);
//
//        try {
//            System.out.println("PASSING THIS: " + StudentDAO.searchStudents("8350001").toString());
//
//            stv.setStudent(StudentDAO.searchStudents("8350001"));
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        AddBookView addBookView = new AddBookView();
        addBookView.prefWidthProperty().bind(this.widthProperty());
//        HBox hBox = new HBox();
//        hBox.prefWidthProperty().bind(this.widthProperty());
//        hBox.getChildren().add(addStudentView);
        this.getChildren().add(addBookView);

    }

}
