package view;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class CoursesView extends Pane {

    private Button button;

    public CoursesView(){
        button = new Button("CoursesView");
        button.prefWidthProperty().bind(this.widthProperty());
        button.prefHeightProperty().bind(this.heightProperty());
        this.getChildren().add(button);
    }

}
