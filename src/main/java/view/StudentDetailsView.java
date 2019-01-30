package view;

import dao.RedemptionInfoDAO;
import entities.RedemptionInfo;
import entities.Student;
import javafx.scene.layout.Pane;

import java.sql.SQLException;

public class StudentDetailsView extends Pane {

    private Student student = null;
    private StudentBooksTableView sbtv;

    public StudentDetailsView(){
        sbtv = new StudentBooksTableView();
        sbtv.prefHeightProperty().bind(this.heightProperty());
        sbtv.prefWidthProperty().bind(this.widthProperty());
        this.getChildren().add(sbtv);
    }


    public void setStudent(Student student){
        this.student = student;
        try {
            this.sbtv.setData(RedemptionInfoDAO.searchRedemptionInfoByStudentId("" + student.getStudent_id()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //redraw
    }

}
