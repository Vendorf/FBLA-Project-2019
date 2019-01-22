package main;

import dao.StudentDAO;
import entities.Student;
import event.EventManager;
import event.EventType;
import event.Observer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.CoursesView;
import view.HomeView;
import view.MenuView;
import view.NavigatorView;

public class Main extends Application {

    private Pane mainView;
    private HomeView hv;
    private CoursesView cv;
    private HBox hContainer;
    private VBox vContainer;

    @Override
    public void start(Stage stage) throws Exception {

        Group root = new Group();
        vContainer = new VBox();
        hContainer = new HBox();
        NavigatorView nv = new NavigatorView();
        hv = new HomeView();
        cv = new CoursesView();

//

        MenuView mv = new MenuView();
        vContainer.getChildren().addAll(mv, hContainer);
        vContainer.setVgrow(hContainer, Priority.ALWAYS);


        mainView = hv;

        hContainer.getChildren().addAll(nv, mainView);
        hContainer.setHgrow(mainView, Priority.ALWAYS);
        root.getChildren().add(vContainer);

        Scene scene = new Scene(root, 1000 ,750);
        vContainer.prefWidthProperty().bind(scene.widthProperty());
        vContainer.prefHeightProperty().bind(scene.heightProperty());
        nv.prefHeightProperty().bind(hContainer.heightProperty());

        mainView.prefHeightProperty().bind(hContainer.heightProperty());


//Option B: menu does not go over navigator

//        mainView = hv;
//        MenuView mv = new MenuView();
//        vContainer.getChildren().addAll(mv, mainView);
//        vContainer.setVgrow(mainView, Priority.ALWAYS);
//
//        hContainer.getChildren().addAll(nv, vContainer);
//        hContainer.setHgrow(vContainer, Priority.ALWAYS);
//        root.getChildren().add(hContainer);
//
//        Scene scene = new Scene(root, 1000 ,750);
//        hContainer.prefWidthProperty().bind(scene.widthProperty());
//        hContainer.prefHeightProperty().bind(scene.heightProperty());
//        nv.prefHeightProperty().bind(hContainer.heightProperty());

        String path = "application.css";
        System.out.println(path + "     " + (getClass().getResource(path) == null ? "NULL" : "working"));
        //Logger.getGlobal().log(Level.ALL, path + "     " + (getClass().getResource(path) == null ? "NULL" : "working"));
        scene.getStylesheets().add(getClass().getResource(path).toExternalForm());
        stage.setScene(scene);
        stage.show();

        EventManager.attach(EventType.HOME_CLICKED, new Observer() {
            @Override
            public void doEvent() {
                hContainer.getChildren().removeAll(mainView);
                mainView = hv;
                hContainer.setHgrow(mainView, Priority.ALWAYS);
                mainView.prefHeightProperty().bind(hContainer.heightProperty());
                hContainer.getChildren().add(mainView);
            }
        });

        EventManager.attach(EventType.COURSES_CLICKED, new Observer() {
            @Override
            public void doEvent() {
                hContainer.getChildren().removeAll(mainView);
                mainView = cv;
                hContainer.setHgrow(mainView, Priority.ALWAYS);
                mainView.prefHeightProperty().bind(hContainer.heightProperty());
                hContainer.getChildren().add(mainView);
            }
        });



        for(Student student : StudentDAO.searchStudents()){
            System.out.println(student.toString());
        }
        System.out.println();
        System.out.println(StudentDAO.searchStudents("8340256").toString());

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
