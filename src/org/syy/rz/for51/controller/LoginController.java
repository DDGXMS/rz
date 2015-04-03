package org.syy.rz.for51.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.syy.rz.for51.bus.SystemBus;
import org.syy.rz.for51.crawl.HttpClientFor51Job;
import org.syy.rz.for51.entity.User;
import org.syy.rz.util.UIUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Administrator on 2015/4/2.
 */
public class LoginController{

    @FXML
    private Button loginButton;
    @FXML
    private TextField memberNameTextField;
    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    @FXML
    public void loginAction(ActionEvent event) {
        String memberName = memberNameTextField.getText();
        if (StringUtils.isBlank(memberName)) {
            UIUtils.showErrorMessage("请输入会员名", errorLabel);
        }

        String userUserName = userNameTextField.getText();
        if (StringUtils.isBlank(userUserName)) {
            UIUtils.showErrorMessage("请输入用户名", errorLabel);
        }

        String password = passwordField.getText();
        if (StringUtils.isBlank(password)) {
            UIUtils.showErrorMessage("请输入密码", errorLabel);
        }

        User user = new User(memberName, userUserName, password);
        try {
            HttpClient loginHttpClient = HttpClientFor51Job.login(user);
            SystemBus.instance().setLoginUser(user);
            SystemBus.instance().setLoginClient(loginHttpClient);


        } catch (IOException e) {
            e.printStackTrace();
            UIUtils.showErrorMessage("失败："+e.getMessage(), errorLabel);
        }
    }
}
