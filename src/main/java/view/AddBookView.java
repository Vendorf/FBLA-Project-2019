package view;

import dao.BookCatalogDAO;
import dao.CategoryDAO;
import entities.Book;
import entities.Category;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

import java.sql.SQLException;

public class AddBookView extends Pane {

    private TextField bookNameField;
    private TextField authorField;
    private TextField yearField;
    private ChoiceBox<Category> categoryChoiceBox;

    private Button addButton;
    private Button cancelButton;

    private BorderPane borderPane;
    private HBox horizontalStore;
    private HBox buttonsStore;

    public AddBookView(){

        this.getStyleClass().add("add-student-view");


        bookNameField = new TextField();
        bookNameField.setPromptText("Book Name");
        authorField = new TextField();
        authorField.setPromptText("Author");

        yearField = new TextField();
        yearField.setPromptText("Publication Year");


        categoryChoiceBox = new ChoiceBox<>();
        categoryChoiceBox.setConverter(new StringConverter<Category>() {
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
            categoryChoiceBox.setItems(CategoryDAO.searchCategories());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        horizontalStore = new HBox();
        horizontalStore.getChildren().setAll(bookNameField, authorField, yearField, categoryChoiceBox);
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
                    String name = bookNameField.getText();
                    String author = authorField.getText();
                    int year = Integer.parseInt(yearField.getText());
                    int categoryId = categoryChoiceBox.getSelectionModel().getSelectedItem().getId();

                    Book book = new Book();
                    book.setName(name);
                    book.setAuthor(author);
                    book.setPublication_year(year);
                    book.setCategory_id(categoryId);

                    BookCatalogDAO.insertBook(book);
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
