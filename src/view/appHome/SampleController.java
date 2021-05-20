package view.appHome;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SampleController implements Initializable {

    @FXML
    private AnchorPane basePane;

    @FXML
    private Label userLabel;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void loadHomeDetail(ActionEvent event) {

    }

    @FXML
    void loadReservationDetail(ActionEvent event) {

    }

    @FXML
    void loadRoomDetail(ActionEvent event) {

    }

    @FXML
    void loadStudentDetail(ActionEvent event) {

    }

    @FXML
    void loadUserDetail(ActionEvent event) {

    }

    @FXML
    void logoutAccount(ActionEvent event) {

    }

    @FXML
    void setSelection(MouseEvent event) {

    }
}
