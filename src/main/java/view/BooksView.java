package view;

import dao.BookCatalogDAO;
import dao.StudentDAO;
import entities.Book;
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

import java.sql.SQLException;

public class BooksView extends Pane {

    private Button button;

    private SplitPane mainBox;
    private VBox topRoot;
    //private StudentDetailsView detailsView;
    private BooksTableView booksTableView;
    private HBox buttonPane;
    private AddBookView addBookView;

    public BooksView(){

        this.getStyleClass().add("home-view");

        mainBox = new SplitPane();
        mainBox.setOrientation(Orientation.VERTICAL);
        mainBox.setDividerPositions(0.7);
        topRoot = new VBox();

        //detailsView = new StudentDetailsView();

        //detailsView.prefWidthProperty().bind(mainBox.widthProperty());

        booksTableView = new BooksTableView();
        try {
            booksTableView.setData(BookCatalogDAO.searchBookCatalog());
        } catch (SQLException e) {
            e.printStackTrace();
        }



        mainBox.getItems().setAll(topRoot/*, detailsView*/);

        booksTableView.prefWidthProperty().bind(mainBox.widthProperty());



        mainBox.prefHeightProperty().bind(this.heightProperty());
        mainBox.prefWidthProperty().bind(this.widthProperty());
        this.getChildren().add(mainBox);


        Button b = new Button("Save");
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for(Book book : booksTableView.getEditedBooks()){
                    try {
                        BookCatalogDAO.updateBook(book);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                //stv print is incorrect as editedStudents and the current items are not actually yet updated due to no purge being done as with
                //stv.setData in the reset button call. You wouldn't want to reset if something failed, but would if everything succeeded
                //consider other edgecases before doing anything

                booksTableView.print(); //a save button would be getting items and trying to perform updates/additions/etc

            }
        });

        Button reset = new Button("Reset");
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    booksTableView.setData(BookCatalogDAO.searchBookCatalog()); //this will allow for a reset functionality
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonPane = new HBox();
        final Pane spacer = new Pane();
        buttonPane.getChildren().addAll(spacer, b, reset);
        buttonPane.setHgrow(spacer, Priority.ALWAYS);

        addBookView = new AddBookView();
        addBookView.prefWidthProperty().bind(topRoot.widthProperty());

        topRoot.getChildren().addAll(buttonPane, addBookView, booksTableView);
        topRoot.prefWidthProperty().bind(mainBox.widthProperty());
        topRoot.setVgrow(booksTableView, Priority.ALWAYS);


    }

}
