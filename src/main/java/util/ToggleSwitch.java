package util;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


public class ToggleSwitch extends HBox {

    private static PseudoClass TOGGLE_STATE_PSEUDO_CLASS = PseudoClass.getPseudoClass("right-toggle");

    private final Label label = new Label();
    private final Button button = new Button();


    private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(false);
    public SimpleBooleanProperty switchOnProperty() { return switchedOn; }

    private void init() {
        this.getStyleClass().add("toggle-switch");

        label.setText("View");

        getChildren().addAll(label, button);
        button.setOnAction((e) -> {
            switchedOn.set(!switchedOn.get());
        });
        label.setOnMouseClicked((e) -> {
            switchedOn.set(!switchedOn.get());
        });
        setStyle();
        bindProperties();
    }

    private void setStyle() {
        //Default Width
        setWidth(80);
        label.setAlignment(Pos.CENTER);
        setAlignment(Pos.CENTER_LEFT);
    }

    private void bindProperties() {
        label.prefWidthProperty().bind(widthProperty().divide(2));
        label.prefHeightProperty().bind(heightProperty());
        button.prefWidthProperty().bind(widthProperty().divide(2));
        button.prefHeightProperty().bind(heightProperty());
    }

    public ToggleSwitch() {
        init();
        switchedOn.addListener((a,b,c) -> {
            if (c) {
                label.setText("Edit");
                label.pseudoClassStateChanged(TOGGLE_STATE_PSEUDO_CLASS, true);
                label.toFront();
            }
            else {
                label.setText("View");
                label.pseudoClassStateChanged(TOGGLE_STATE_PSEUDO_CLASS, true);
                button.toFront();
            }
        });
    }
}