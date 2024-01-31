package lk.ijse.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import lk.ijse.util.Navigation;

import java.io.IOException;

public class LoginFormController {

    @FXML
    private Label lblDash;

    @FXML
    private Pane paneDash;

    public static Pane dashBoardPane;
    public static Label dashLabel;
    public void initialize() throws IOException {
        dashBoardPane=paneDash;
        dashLabel=lblDash;
        Navigation.switchPaging(dashBoardPane,"/view/userLogingChekForm.fxml");
    }

}
