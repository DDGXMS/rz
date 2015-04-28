package org.syy.rz.for51.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.syy.rz.for51.entity.Position;
import org.syy.rz.for51.service.PositionService;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2015/4/2.
 */
public class MainFrame {

    private PositionService positionService;
    private Scene scene = null;
    private Stage stage;

    public MainFrame() {
        positionService = new PositionService();
        this.show();
    }

    public void show() {
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
        List<Position> firsts = positionService.loadPosition(1);
    }

    public void close() {
        if (null != stage) {
            stage.close();
        }
    }
}
