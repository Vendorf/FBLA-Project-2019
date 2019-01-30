package view;

import dao.StudentDAO;
import entities.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.control.skin.TreeTableViewSkin;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import util.DatePickerCell;

import java.sql.SQLException;
import java.util.Date;

public class StudentTreeTableView extends TreeTableView<Student> {

    //No longer needed (right?)
    // OP A
    //factory does not directly apply to the column
    //instead, it is used in order to populate the true factories which are Student, Textbox



    private TreeTableColumn<Student, Integer> idColumn;
    private TreeTableColumn<Student, Integer> studentIdColumn;
    private TreeTableColumn nameColumn;
    private TreeTableColumn<Student, String> firstNameColumn;
    private TreeTableColumn<Student, String> lastNameColumn;
    private TreeTableColumn<Student, String> dobColumn;

    private TreeTableColumn<Student, String> genderColumn;
    private TreeTableColumn<Student, String> gradeColumn;

    private TreeItem<Student> subTreeStudentViewRoot;

    //private TableColumn<Student, S>

    public StudentTreeTableView(){

        //CONSIDER:
        //http://fxexperience.com
        //Use a TreeTableView and span the cells all the way across; then use the cell factory API in order to insert whatever component into the spanned cell
        //This should allow it so that when a row is clicked, the tree opens, and in that tree is the single cell with the student info

        //Check out https://www.youtube.com/watch?v=udc2iRZBF0M as well
        //You downloaded the cell spanning and it is in the project folder if you look

        this.getStyleClass().add("student-table-view");

        idColumn = new TreeTableColumn<>("ID");
        idColumn.setCellValueFactory(new TreeItemPropertyValueFactory<Student, Integer>("id"));

        studentIdColumn = new TreeTableColumn<>("Student ID");
        studentIdColumn.setCellValueFactory(new TreeItemPropertyValueFactory<Student, Integer>("student_id"));
        nameColumn = new TreeTableColumn("Name");
        firstNameColumn = new TreeTableColumn<>("First");
        firstNameColumn.setCellValueFactory(new TreeItemPropertyValueFactory<Student, String>("first_name"));
        lastNameColumn = new TreeTableColumn<>("Last");
        lastNameColumn.setCellValueFactory(new TreeItemPropertyValueFactory<Student, String>("last_name"));
        dobColumn = new TreeTableColumn<>("DOB");
        dobColumn.setCellValueFactory(new TreeItemPropertyValueFactory<Student, String>("dob"));

        genderColumn = new TreeTableColumn<>("Gender");
        genderColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Student, String> studentStringCellDataFeatures) {
                return null;//new SimpleStringProperty(studentStringCellDataFeatures.getValue().getValue().getGender().getGender());
            }
        });

        gradeColumn = new TreeTableColumn<>("Year");
        gradeColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
                                            @Override
                                            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Student, String> studentStringCellDataFeatures) {
                                                return null;//new SimpleStringProperty(studentStringCellDataFeatures.getValue().getValue().getGrade().getGrade());
                                            }
                                        });

        idColumn.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new IntegerStringConverter()));
        firstNameColumn.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        lastNameColumn.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

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


//        idColumn.setCellFactory(new Callback<TreeTableColumn<Student, Integer>, TreeTableCell<Student, Integer>>() {
//            @Override
//            public TreeTableCell<Student, Integer> call(TreeTableColumn<Student, Integer> studentIntegerTreeTableColumn) {
//                return new TreeTableCell<Student, Integer>() {
//                    final Button button = new Button(); {
//                        button.setMinWidth(130);
//                        button.setMinHeight(500);
//                        button.toFront();
//                    }
//
//                    @Override public void updateItem(final Integer integer, boolean empty) {
//                        super.updateItem(integer, empty);
//                        setGraphic(button);
//                        button.toFront();
//                    }
//                };
//            }
//        });

//        studentIdColumn.setCellFactory(new Callback<TreeTableColumn<Student, Integer>, TreeTableCell<Student, Integer>>() {
//            @Override
//            public TreeTableCell<Student, Integer> call(TreeTableColumn<Student, Integer> studentStringTreeTableColumn) {
//                return new TreeTableCell<>(){
//                    @Override
//                    public void updateItem(Integer t, boolean b){
//                        super.updateItem(t, b);
//                        maxWidth(0);
//                    }
//                };
//            }
//        });

        this.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
        nameColumn.getColumns().setAll(firstNameColumn, lastNameColumn);
        this.getColumns().setAll(idColumn, studentIdColumn, nameColumn, dobColumn, genderColumn, gradeColumn);

        subTreeStudentViewRoot = new TreeItem<>();




    }

    public void setData(ObservableList<Student> students){
        TreeItem<Student> studentTreeItems = new TreeItem<>();
        studentTreeItems.setExpanded(true);

        //can take advantage of this; span cells together in the subtreeitem and make its factory be some Node you create
        //make a custom TreeTableCell that wraps that node
        //When it is displayed, node gets the information of the parent TreeItem, getting the Student from that and using that to retrieve more student info (books, etc)
        //That node can store a table with books, show an image, etc; its a Pane
        TreeItem<Student> subTreeItem = null;
        try {
            subTreeItem = new TreeItem<>(StudentDAO.searchStudents("8340256"));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for(Student each : students){
            TreeItem<Student> treeItem = new TreeItem<>(each);
            treeItem.getChildren().add(subTreeItem);
            studentTreeItems.getChildren().add(treeItem);
        }
        this.setRoot(studentTreeItems);
    }

}
