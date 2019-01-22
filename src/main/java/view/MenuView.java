package view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuView extends MenuBar {

    private Menu fileMenu;
    private MenuItem item0;
    private MenuItem item1;
    private MenuItem item2;

    private Menu editMenu;
    private MenuItem editItem0;
    private MenuItem editItem1;
    private MenuItem editItem2;


    public MenuView(){

        fileMenu = new Menu("File");
        item0 = new MenuItem("Item 0");
        item1 = new MenuItem("Item 1");
        item2 = new MenuItem("Item 2");
        fileMenu.getItems().addAll(item0, item1, item2);

        editMenu = new Menu("Edit");
        editItem0 = new MenuItem("Item 0");
        editItem1 = new MenuItem("Item 1");
        editItem2 = new MenuItem("Item 2");
        editMenu.getItems().addAll(editItem0, editItem1, editItem2);

        this.getMenus().addAll(fileMenu, editMenu);
    }

}
