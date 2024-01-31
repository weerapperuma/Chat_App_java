package lk.ijse.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigation {
    private static Parent rootNode;
    private static Scene scene;
    private static Stage stage;

    public static void switchNavigation(String path, javafx.event.ActionEvent event) throws IOException {
        rootNode= FXMLLoader.load(Navigation.class.getResource(path));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();

        scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    public static void switchPaging(Pane pane, String path) throws IOException {
        pane.getChildren().clear();
        FXMLLoader loader=new FXMLLoader(Navigation.class.getResource(path));

        Parent root=loader.load();
        pane.getChildren().add(root);
    }

    public static void switchNavigation2(String path, javafx.event.ActionEvent event) throws IOException {
        Parent newNode = FXMLLoader.load(Navigation.class.getResource(path));

        Stage newStage = new Stage();
        newStage.setScene(new Scene(newNode));
        newStage.centerOnScreen();
        newStage.show();
    }

}
