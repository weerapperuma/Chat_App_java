package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lk.ijse.util.Navigation;

import java.io.IOException;

public class UserLogingChekFormController {

    @FXML
    private TextField txtNameFx;
    public static String name="";

    @FXML
    void chatOnAction(ActionEvent event) throws IOException {
        chatGo();
    }

    @FXML
    void txtNameOnAction(ActionEvent event) throws IOException {
        chatGo();
    }
    public void chatGo() throws IOException {
        if (txtNameFx.getLength()!=0){
            name=txtNameFx.getText();
            LoginFormController.dashLabel.setText("Welcome "+name+",");
            Navigation.switchPaging(LoginFormController.dashBoardPane,"/view/chatInterfaceForm.fxml");

        }
    }

}
