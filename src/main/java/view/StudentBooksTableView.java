package view;

import com.sun.source.tree.Tree;
import dao.BookCatalogDAO;
import entities.Book;
import entities.RedemptionInfo;
import entities.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Callback;

import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StudentBooksTableView extends TreeTableView<RedemptionInfo> {

    private TreeTableColumn<RedemptionInfo, String> bookColumn;
    private TreeTableColumn<RedemptionInfo, String> redemptionCodeColumn;
    private TreeTableColumn<RedemptionInfo, String> assignDateColumn;

    public StudentBooksTableView(){

        bookColumn = new TreeTableColumn<>("Book");
        redemptionCodeColumn = new TreeTableColumn<>("Code");
        assignDateColumn = new TreeTableColumn<>("Assign Date");

       bookColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<RedemptionInfo, String>, ObservableValue<String>>() {
           @Override
           public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<RedemptionInfo, String> redemptionInfoStringCellDataFeatures) {
               if(!(redemptionInfoStringCellDataFeatures.getValue().getValue().getBook() == null)){
                return new SimpleStringProperty(redemptionInfoStringCellDataFeatures.getValue().getValue().getBook().getName());
               }
               return new SimpleStringProperty();
           }
       });
       redemptionCodeColumn.setCellValueFactory(new TreeItemPropertyValueFactory<RedemptionInfo, String>("redemption_code"));
       assignDateColumn.setCellValueFactory(new TreeItemPropertyValueFactory<RedemptionInfo, String>("student_id_assign_date"));


        this.getColumns().setAll(bookColumn, redemptionCodeColumn, assignDateColumn);
        this.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);


//        //Dummy data
//        TreeItem root = new TreeItem();
//        for(int j = 0; j < 5; j++) {
//            TreeItem<RedemptionInfo> treeItem;
//            Book b = new Book();
//            b.setId(1);
//            b.setAuthor("Stewart");
//            b.setCategory_id(1);
//            b.setName("Calculus: Early Transcendentals" + j);
//            b.setPublication_year(2014);
//            RedemptionInfo bookInfo = new RedemptionInfo();
//            bookInfo.setBook(b);
//            //redemptionInfo.setStudent_id_assign_date(Date.valueOf("2019-24-1"));
//            treeItem = new TreeItem<>(bookInfo);
//            for (int i = 0; i < 3; i++) {
//                RedemptionInfo redemptionInfo = new RedemptionInfo();
//                redemptionInfo.setId(1);
//                redemptionInfo.setStudent_id(8340256);
//                redemptionInfo.setBook_id(1);
//                redemptionInfo.setRedemption_code("aksdjf-lk-asjdf" + i);
//                redemptionInfo.setStudent_id_assign_date(Date.valueOf(String.format("201%d-01-24", i)));
//                treeItem.getChildren().add(new TreeItem<>(redemptionInfo));
//            }
//            root.getChildren().add(treeItem);
//        }
//        this.setRoot(root);
//        this.setShowRoot(false);



    }



    //THIS IS CURRENTLY A MASSIVE HACK

    //Need a good way to organize data. Don't want every redemption code to show the book, but don't want to display
    //with redemption code

    //Maybe can do a TreeTableColumn<Book, String> and set the root
    //Or have some sort of wrapper that holds RedemptionInfo and Books
        //Ex: StudentRedemptionCodesWrapper
    //Or have student own everything ever and use that for everything
    public void setData(ObservableList<RedemptionInfo> studentCodes){

        Map<Integer, List<RedemptionInfo>> bookIdRedemptionMap = new HashMap<>();
        for(RedemptionInfo redemptionInfo : studentCodes){
            if(bookIdRedemptionMap.get(redemptionInfo.getBook_id()) == null){
                bookIdRedemptionMap.put(redemptionInfo.getBook_id(), new ArrayList<>());
            }
            bookIdRedemptionMap.get(redemptionInfo.getBook_id()).add(redemptionInfo);
        }

        TreeItem root = new TreeItem();
        for(Map.Entry<Integer, List<RedemptionInfo>> entry : bookIdRedemptionMap.entrySet()){
            RedemptionInfo bookHack = new RedemptionInfo();
            Book book = null;
            try {
                 book = BookCatalogDAO.searchBookCatalog(entry.getKey() + "");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            bookHack.setBook(book);
            TreeItem<RedemptionInfo> bookInfo = new TreeItem<>(bookHack);
            for(RedemptionInfo redemptionInfo : entry.getValue()){
                bookInfo.getChildren().add(new TreeItem<>(redemptionInfo));
            }
            root.getChildren().add(bookInfo);
        }
        this.setRoot(root);
        this.setShowRoot(false);

//        for(RedemptionInfo redemptionInfo : studentCodes){
//            //booksDAO -> search -> set book to the result
//        }
//
//        //map: BookId, List<RedemptionInfo>
//        //map: BookId, Book
    }
}
