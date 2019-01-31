package view;

import dao.BookCatalogDAO;
import dao.RedemptionInfoDAO;
import entities.Book;
import entities.RedemptionInfo;
import event.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import util.StatefulCheckBox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssignCodesView extends Pane {

    private TreeView<String> codesView;
    private TreeItem root;
    private Button assignButton; //action delegated to controller
    private Button cancelButton;

    private Observer assignObserver;
    private Observer cancelObserver;

    private List<StatefulCheckBox<RedemptionInfo>> checkBoxes = new ArrayList<>();

    public AssignCodesView(){
        //RedemptionDAO - only get unnassigned
        root = new TreeItem();
        codesView = new TreeView<>();

        codesView.setRoot(root);

        ObservableList<RedemptionInfo> unassignedCodes = FXCollections.observableArrayList();
        try {
            unassignedCodes = RedemptionInfoDAO.getUnnassignedCodes();
        } catch (SQLException e) {
            System.out.println("problems");
        }

        for(RedemptionInfo info : unassignedCodes){
            System.out.println(info.toString());
        }



//        Map<Integer, List<RedemptionInfo>> bookIdRedemptionMap = new HashMap<>();
//        for(RedemptionInfo redemptionInfo : unassignedCodes){
//            if(bookIdRedemptionMap.get(redemptionInfo.getBook_id()) == null){
//                bookIdRedemptionMap.put(redemptionInfo.getBook_id(), new ArrayList<>());
//            }
//            bookIdRedemptionMap.get(redemptionInfo.getBook_id()).add(redemptionInfo);
//        }
//
//        for(Map.Entry<Integer, List<RedemptionInfo>> entry : bookIdRedemptionMap.entrySet()){
//            RedemptionInfo bookHack = new RedemptionInfo();
//            Book book = null;
//            try {
//                book = BookCatalogDAO.searchBookCatalog(entry.getKey() + "");
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            bookHack.setBook(book);
//            TreeItem<String> bookInfo = new TreeItem<>(bookHack.getBook().getName());
//            for(RedemptionInfo redemptionInfo : entry.getValue()){
//                bookInfo.getChildren().add(new TreeItem<>(redemptionInfo.getRedemption_code(), new CheckBox("aaa")));
//            }
//            root.getChildren().add(bookInfo);
//        }
        Map<Integer, List<RedemptionInfo>> bookIdRedemptionMap = new HashMap<>();
        for(RedemptionInfo redemptionInfo : unassignedCodes){
            if(bookIdRedemptionMap.get(redemptionInfo.getBook_id()) == null){
                bookIdRedemptionMap.put(redemptionInfo.getBook_id(), new ArrayList<>());
            }
            bookIdRedemptionMap.get(redemptionInfo.getBook_id()).add(redemptionInfo);
        }

        for(Map.Entry<Integer, List<RedemptionInfo>> entry : bookIdRedemptionMap.entrySet()){
            Book book = null;
            try {
                book = BookCatalogDAO.searchBookCatalog(entry.getKey() + "");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            TreeItem<String> bookInfo = new TreeItem<>(book != null ? book.getName() : "");
            for(RedemptionInfo redemptionInfo : entry.getValue()){
                StatefulCheckBox<RedemptionInfo> checkBox = new StatefulCheckBox<>(redemptionInfo);
                checkBoxes.add(checkBox);
                bookInfo.getChildren().add(new TreeItem<>(redemptionInfo.getRedemption_code(), checkBox));
            }
            root.getChildren().add(bookInfo);
        }
        codesView.setShowRoot(false);


        assignButton = new Button("Assign");
        assignButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(assignObserver != null) assignObserver.doEvent();

            }
        });
        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(cancelObserver != null) cancelObserver.doEvent();
            }
        });

        VBox main = new VBox();
        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(assignButton, cancelButton);
        main.getChildren().addAll(codesView, buttonBox);
        main.setVgrow(codesView, Priority.ALWAYS);

        this.getChildren().add(main);


    }

    public void setAssignObserver(Observer observer){
        this.assignObserver = observer;
    }

    public void setCancelObserver(Observer observer){
        this.cancelObserver = observer;
    }

    public List<StatefulCheckBox<RedemptionInfo>> getCheckBoxes() {
        return checkBoxes;
    }
}
