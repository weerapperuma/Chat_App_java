package lk.ijse.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lk.ijse.util.Navigation;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LoginFormController {

    @FXML
    private Label lblDash;

    @FXML
    private Pane paneDash;
    @FXML
    private Label lblTime;

    public static Pane dashBoardPane;
    public static Label dashLabel;
    public void initialize() throws IOException {
        dashBoardPane=paneDash;
        dashLabel=lblDash;

        updateTime();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateTime()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Navigation.switchPaging(dashBoardPane,"/view/userLogingChekForm.fxml");
    }

    private void updateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        lblTime.setText(LocalTime.now().format(formatter));
    }

}
