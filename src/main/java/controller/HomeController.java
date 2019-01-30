package controller;

import event.EventManager;
import event.EventType;
import event.Observer;
import view.HomeView;

public class HomeController {

    private HomeView homeView;

    public HomeController(){

        homeView = new HomeView();
        //create views and models


        EventManager.attach(EventType.STUDENT_SELECTED, new Observer() {
            @Override
            public void doEvent() {
                homeView.getDetailsView().setStudent(homeView.getStudentTableView().getSelectionModel().getSelectedItem());
            }
        });
    }

    public HomeView getHomeView() {
        return homeView;
    }
}
