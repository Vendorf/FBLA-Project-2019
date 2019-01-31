package controller;

import dao.RedemptionInfoDAO;
import entities.RedemptionInfo;
import entities.Student;
import event.Observer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.Main;
import util.StatefulCheckBox;
import view.AssignCodesView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CodeAssignController {

    private AssignCodesView view;
    private Stage window;

    public CodeAssignController(Student student){
        view = new AssignCodesView();
        newWindow();
        view.setAssignObserver(new Observer() {
            @Override
            public void doEvent() {
                System.out.println(student.toString());
                //view.getDatePicker.getText for Date
                java.sql.Date date = java.sql.Date.valueOf("2019-01-30");
                List<RedemptionInfo> redemptionInfos = new ArrayList<>();
                for(StatefulCheckBox<RedemptionInfo> checkBox : view.getCheckBoxes()){
                    if(checkBox.isSelected()){
                        redemptionInfos.add(checkBox.getState());
                    }
                }
                RedemptionInfo[] redemptionArr = new RedemptionInfo[redemptionInfos.size()];
                redemptionArr = redemptionInfos.toArray(redemptionArr);
                try {
                    RedemptionInfoDAO.assignRedemptionCodes(student, date, redemptionArr);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                window.close();
            }
        });

        view.setCancelObserver(new Observer() {
            @Override
            public void doEvent() {
                window.close();
            }
        });
    }

    private void newWindow(){
        window = new Stage();
        Scene scene = new Scene(view);
        window.setScene(scene);
        window.show();
    }

    public AssignCodesView getView() {
        return view;
    }
}
