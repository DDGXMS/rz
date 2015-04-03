package org.syy.rz.util;

import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;

/**
 * Created by Administrator on 2015/4/2.
 */
public class UIUtils {

    public static void showErrorMessage(String error, Label label) {
        label.setText(error);
        label.setVisible(true);
    }
}
