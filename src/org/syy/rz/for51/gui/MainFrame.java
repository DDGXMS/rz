package org.syy.rz.for51.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Administrator on 2015/4/2.
 */
public class MainFrame {

    private Stage stage;

    public void show() {
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(getClass().getResource("fxml/main.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void loadPositions() {

    }

    public void close() {
        if (null != stage) {
            stage.close();
        }
    }
}
