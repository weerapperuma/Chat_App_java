package lk.ijse.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ReceivedMsgLoadPaneController {

    @FXML
    private Label lblReceivedText;

    @FXML
    private Label lblSendPane;

    public void setData(String message) {
        lblReceivedText.setText(message);
    }
}
