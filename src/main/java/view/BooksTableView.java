package view;

import dao.CategoryDAO;
import entities.Book;
import entities.Category;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class BooksTableView extends TableView<Book> {


    private TableColumn<Book, String> nameColumn;
    private TableColumn<Book, String> authorColumn;
    private TableColumn<Book, Integer> yearColumn;
    private TableColumn<Book, Category> categoryColumn;

    private Set<Book> editedBooks = new HashSet<>();


    private Book currEdit = null;

    public BooksTableView(){

        //CONSIDER:
        //http://fxexperience.com
        //Use a TreeTableView and span the cells all the way across; then use the cell factory API in order to insert whatever component into the spanned cell
        //This should allow it so that when a row is clicked, the tree opens, and in that tree is the single cell with the student info

        //Check out https://www.youtube.com/watch?v=udc2iRZBF0M as well
        //You downloaded the cell spanning and it is in the project folder if you look

        this.getStyleClass().add("student-table-view");

        nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("name"));
        authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        yearColumn = new TableColumn<>("Year");
        yearColumn.setCellValueFactory(new PropertyValueFactory<Book, Integer>("publication_year"));


        categoryColumn = new TableColumn<Book, Category>("Category");
        categoryColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book, Category>, ObservableValue<Category>>() {
            @Override
            public ObservableValue<Category> call(TableColumn.CellDataFeatures<Book, Category> studentCategoryCellDataFeatures) {
                return new SimpleObjectProperty(studentCategoryCellDataFeatures.getValue().getCategory().getCategory());
            }
        });



        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        authorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        yearColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer integer) {
                return integer.toString();
            }

            @Override
            public Integer fromString(String s) {
                int val = 0;
                try{
                    val = Integer.parseInt(s);
                }catch (NumberFormatException e){
                    //LOG here
                    System.out.println("Cancelling edit");
                    //cancel edit
                    if(currEdit != null){
                        val = currEdit.getPublication_year();
                        currEdit = null;
                    }
                }
                return val;
            }
        }));

        categoryColumn.setCellFactory(new Callback<TableColumn<Book, Category>, TableCell<Book, Category>>() {
            @Override
            public TableCell<Book, Category> call(TableColumn<Book, Category> bookCategoryTableColumn) {
                return new ChoiceBoxTableCell<>() {
                    private ChoiceBox<Category> choiceBox;

                    @Override
                    public void startEdit() {
                        super.startEdit();
                        choiceBox = new ChoiceBox<>();
                        choiceBox.setConverter(new StringConverter<Category>() {
                            @Override
                            public String toString(Category category) {
                                return category.getCategory();
                            }

                            @Override
                            public Category fromString(String s) {
                                return null;
                            }
                        });
                        try {
                            choiceBox.setItems(CategoryDAO.searchCategories());
                        } catch (SQLException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        choiceBox.prefWidthProperty().bind(bookCategoryTableColumn.widthProperty());
                        setGraphic(choiceBox);

                        choiceBox.focusedProperty().addListener(new ChangeListener<Boolean>() {
                            @Override
                            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                                if (!t1) {
                                    if (currEdit != null && choiceBox.getValue() != null) {
                                        System.out.println("Committing");
                                        //currEdit.setGender(choiceBox.getSelectionModel().getSelectedItem());
                                        currEdit.setCategory(choiceBox.getValue());
                                        bookCategoryTableColumn.setVisible(false); //this is done in order to force the column to be redrawn
                                        bookCategoryTableColumn.setVisible(true);
                                        editedBooks.add(currEdit);
                                        currEdit = null;
                                    }
                                }
                            }
                        });
                    }
                };
            }});


        nameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, String> bookStringCellEditEvent) {
                if(bookStringCellEditEvent.getNewValue() != bookStringCellEditEvent.getOldValue()){
                    bookStringCellEditEvent.getTableView().getItems().get(bookStringCellEditEvent.getTablePosition().getRow()).setName(bookStringCellEditEvent.getNewValue());
                    editedBooks.add(bookStringCellEditEvent.getRowValue());
                    System.out.println(bookStringCellEditEvent.getRowValue().toString()); //can set edited flag here or something
                }
            }
        });

        authorColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, String> bookStringCellEditEvent) {
                if(bookStringCellEditEvent.getNewValue() != bookStringCellEditEvent.getOldValue()){
                    bookStringCellEditEvent.getTableView().getItems().get(bookStringCellEditEvent.getTablePosition().getRow()).setAuthor(bookStringCellEditEvent.getNewValue());
                    editedBooks.add(bookStringCellEditEvent.getRowValue());
                    System.out.println(bookStringCellEditEvent.getRowValue().toString()); //can set edited flag here or something
                }
            }
        });

        yearColumn.setOnEditStart(new EventHandler<TableColumn.CellEditEvent<Book, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, Integer> bookIntegerCellEditEvent) {
                currEdit = bookIntegerCellEditEvent.getRowValue();
            }
        });

        yearColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, Integer> bookIntegerCellEditEvent) {
                if(bookIntegerCellEditEvent.getNewValue() != bookIntegerCellEditEvent.getOldValue()){
                    bookIntegerCellEditEvent.getTableView().getItems().get(bookIntegerCellEditEvent.getTablePosition().getRow()).setPublication_year(bookIntegerCellEditEvent.getNewValue());
                    editedBooks.add(bookIntegerCellEditEvent.getRowValue());
                    System.out.println(bookIntegerCellEditEvent.getRowValue().toString());
                }
            }
        });

        categoryColumn.setOnEditStart(new EventHandler<TableColumn.CellEditEvent<Book, Category>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, Category> bookCategoryCellEditEvent) {
                currEdit = bookCategoryCellEditEvent.getRowValue();
            }
        });

        this.setEditable(true);

        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.getColumns().setAll(nameColumn, authorColumn, yearColumn, categoryColumn);

        this.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Book>() {
            @Override
            public void changed(ObservableValue<? extends Book> observableValue, Book oldBook, Book newBook) {
                if(newBook != null){
                    System.out.println(newBook.toString()); //put student info into panel
                    //EventManager.post(EventType.STUDENT_SELECTED);
                }
            }
        });

    }

    public void print(){
        for(Book book : this.getItems()){
            System.out.println(book.toString());
        }
        for(Book book : editedBooks){
            System.out.println(book.toString());
        }
    }

    public Set<Book> getEditedBooks() {
        return editedBooks;
    }

    public void setData(ObservableList<Book> books){
        this.setItems(books);
        editedBooks = new HashSet<>();
//        this.getItems().addListener(new ListChangeListener<Book>() {
//            @Override
//            public void onChanged(Change<? extends Book> change) {
//                System.out.println("I was changed"); //why is this here again?
//            }
//        });

    }

}