package util;

import javafx.scene.control.CheckBox;

public class StatefulCheckBox<T> extends CheckBox {

    private final T state;

    public StatefulCheckBox(T obj){
        this.state = obj;
    }

    public T getState() {
        return state;
    }
}
