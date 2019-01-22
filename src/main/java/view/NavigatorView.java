package view;

import event.EventManager;
import event.EventType;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class NavigatorView extends Pane {

    private static PseudoClass LAST_SELECTED_PSEUDO_CLASS = PseudoClass.getPseudoClass("last-selected");

    private ImageView logoView;

    private VBox mainRoot;
    private Button homeButton;
    private Button coursesButton;
    private Button studentsButton;
    private Button eItemsButton;


    private final int BUTTON_HEIGHT = 66;

    private Button lastSelectedButton;

    public NavigatorView() {
        this.getStyleClass().add("navigator");

        mainRoot = new VBox();
        mainRoot.getStyleClass().add("main");

        initButtons();
        initLogo();


        mainRoot.getChildren().addAll(logoView, homeButton, coursesButton, studentsButton, eItemsButton);
        mainRoot.prefWidthProperty().bind(this.widthProperty());
        mainRoot.prefHeightProperty().bind(this.heightProperty());

        this.getChildren().add(mainRoot);

        this.setPrefWidth(84);
        this.setMinWidth(84);



    }

    private void initButtons() {
        homeButton = new Button("Home");
        coursesButton = new Button("Courses");
        studentsButton = new Button("Students");
        eItemsButton = new Button("E-Items");

        lastSelectedButton = homeButton;

        homeButton.setPrefHeight(BUTTON_HEIGHT);
        coursesButton.setPrefHeight(BUTTON_HEIGHT);
        studentsButton.setPrefHeight(BUTTON_HEIGHT);
        eItemsButton.setPrefHeight(BUTTON_HEIGHT);

        homeButton.prefWidthProperty().bind(mainRoot.widthProperty());
        coursesButton.prefWidthProperty().bind(mainRoot.widthProperty());
        studentsButton.prefWidthProperty().bind(mainRoot.widthProperty());
        eItemsButton.prefWidthProperty().bind(mainRoot.widthProperty());

        homeButton.pseudoClassStateChanged(LAST_SELECTED_PSEUDO_CLASS, true);


        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                setActive(homeButton);
                EventManager.post(EventType.HOME_CLICKED);
            }
        });

        coursesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                setActive(coursesButton);
                EventManager.post(EventType.COURSES_CLICKED);
            }
        });

        studentsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                setActive(studentsButton);
            }
        });

        eItemsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                setActive(eItemsButton);
            }
        });
    }

    private void setActive(Button btn){
        lastSelectedButton.pseudoClassStateChanged(LAST_SELECTED_PSEUDO_CLASS, false);
        btn.pseudoClassStateChanged(LAST_SELECTED_PSEUDO_CLASS, true);
        lastSelectedButton = btn;
    }

    private void initLogo(){
        try {
            Image logoImage = new Image(this.getClass().getResourceAsStream("logo.png"));
            logoView = new ImageView(logoImage);
        } catch (NullPointerException e) {
            logoView = new ImageView();
            //e.printStackTrace();
        }
        logoView.setPreserveRatio(true);
        logoView.setFitHeight(84);
        logoView.fitWidthProperty().bind(mainRoot.widthProperty());

    }

}
