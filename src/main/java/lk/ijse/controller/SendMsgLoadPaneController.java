package lk.ijse.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SendMsgLoadPaneController {

    @FXML
    private Label lblSentText;

    public void setData(String message) {
        //this.lblSentText.setText(message);
        this.lblSentText.setText(message);
        System.out.println("send:"+message);
    }
}
