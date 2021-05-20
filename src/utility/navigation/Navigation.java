package utility.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import utility.dataHandler.UserAuthentication;
import utility.popUp.AlertPopUp;

import java.io.IOException;
import java.util.Optional;

public class Navigation {

    public void logoutAccount(AnchorPane basePane) {
        try {
            Optional<ButtonType> action = AlertPopUp.logoutConfirmation();
            if (action.get() == ButtonType.OK) {
                UserAuthentication.setAuthenticatedUser(null);
                AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/appHome/loginStage.fxml"));
                basePane.getChildren().setAll(pane);
            }

        } catch (IOException ex) {
            AlertPopUp.generalError(ex);
        }
    }

    public void loadHomeStage(AnchorPane basePane) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/appHome/homeStage.fxml"));
            basePane.getChildren().setAll(pane);
        } catch (IOException ex) {
            AlertPopUp.generalError(ex);
        }
    }

    public void loadHomeDetail(AnchorPane detailPane) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/appHome/homeDetail.fxml"));
            detailPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            AlertPopUp.generalError(ex);
        }
    }

    public void loadReservationDetail(AnchorPane detailPane) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/reservationManagement/reservationDetail.fxml"));
            detailPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            AlertPopUp.generalError(ex);
            ex.printStackTrace();
        }
    }

    public void loadRoomDetail(AnchorPane detailPane) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/roomManagement/roomDetail.fxml"));
            detailPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            AlertPopUp.generalError(ex);

        }
    }

    public void loadStudentDetail(AnchorPane detailPane) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/studentManagement/studentDetail.fxml"));
            detailPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
            AlertPopUp.generalError(ex);
        }
    }

    public void loadUserDetail(AnchorPane detailPane) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/userManagement/userDetail.fxml"));
            detailPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            AlertPopUp.generalError(ex);
        }
    }
}
