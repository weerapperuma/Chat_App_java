package lk.ijse.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ReceivedMsgLoadPaneController {

    @FXML
    private Label lblReceivedText;

    public void setData(String message) {
        this.lblReceivedText.setText(message);
    }
}
