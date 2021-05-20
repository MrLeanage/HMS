package view.appHome;

import bean.User;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import utility.dataHandler.UserAuthentication;
import utility.navigation.MenuBarHandler;
import utility.navigation.Navigation;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeStageController implements Initializable {

    @FXML
    private AnchorPane basePane;

    @FXML
    private JFXButton homeButton;

    @FXML
    private JFXButton roomButton;

    @FXML
    private JFXButton studentButton;

    @FXML
    private JFXButton reserveButton;

    @FXML
    private JFXButton userButton;

    @FXML
    private AnchorPane detailPane;

    @FXML
    private Label userLabel;

    private Navigation navigation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = UserAuthentication.getAuthenticatedUser();
        userLabel.setText(user.getuFName() + " " +user.getuLName());
        navigation = new Navigation();
        loadHomeDetail();
    }
    private void loadHomeDetail() {
        navigation.loadHomeDetail(detailPane);
    }

    @FXML
    private void loadHomeDetail(ActionEvent actionEvent) {
        navigation.loadHomeDetail(detailPane);
    }

    @FXML
    private void logoutAccount(ActionEvent actionEvent) {
        navigation.logoutAccount(basePane);
    }

    @FXML
    private void loadRoomDetail(ActionEvent actionEvent) {
        navigation.loadRoomDetail(detailPane);
    }

    @FXML
    private void loadStudentDetail(ActionEvent actionEvent) {
        navigation.loadStudentDetail(detailPane);
    }

    @FXML
    private void loadReservationDetail(ActionEvent actionEvent) {
        navigation.loadReservationDetail(detailPane);
    }

    @FXML
    private void loadUserDetail(ActionEvent actionEvent) {
        navigation.loadUserDetail(detailPane);
    }

    @FXML
    private void setSelection() {
        if (homeButton.isPressed())
            MenuBarHandler.setMenuNumber(0);
        else if (roomButton.isPressed())
            MenuBarHandler.setMenuNumber(1);
        else if (studentButton.isPressed())
            MenuBarHandler.setMenuNumber(2);
        else if (reserveButton.isPressed())
            MenuBarHandler.setMenuNumber(3);
        else if (userButton.isPressed())
            MenuBarHandler.setMenuNumber(4);
        else
            MenuBarHandler.setMenuNumber(0);
        setStyle();
    }

    public void setStyle() {
        String selectionColor = "-fx-background-color:   #01579B;";
        resetButtons();
        switch (MenuBarHandler.getMenuNumber()) {
            case 0:
                homeButton.setStyle(selectionColor);
                break;
            case 1:
                roomButton.setStyle(selectionColor);
                break;
            case 2:
                studentButton.setStyle(selectionColor);
                break;
            case 3:
                reserveButton.setStyle(selectionColor);
                break;
            case 4:
                userButton.setStyle(selectionColor);
                break;
            default:
                homeButton.setStyle(selectionColor);

        }
    }

    private void resetButtons() {
        String defaultColor = "-fx-background-color:   #141937;  ";
        homeButton.setStyle(defaultColor);
        roomButton.setStyle(defaultColor);
        studentButton.setStyle(defaultColor);
        reserveButton.setStyle(defaultColor);
        userButton.setStyle(defaultColor);
    }
}
