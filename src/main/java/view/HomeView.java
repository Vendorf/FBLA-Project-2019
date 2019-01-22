package view;

import dao.StudentDAO;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.sql.SQLException;

public class HomeView extends Pane {

    private Button button;

    public HomeView(){
//        button = new Button("HomeView");
//        button.prefWidthProperty().bind(this.widthProperty());
//        button.prefHeightProperty().bind(this.heightProperty());
//        this.getChildren().add(button);

        StudentTableView stv = new StudentTableView();
        try {
            stv.setItems(StudentDAO.searchStudents());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        stv.prefHeightProperty().bind(this.heightProperty());
        stv.prefWidthProperty().bind(this.widthProperty());
        this.getChildren().add(stv);
    }

}
