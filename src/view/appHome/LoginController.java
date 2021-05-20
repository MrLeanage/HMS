package view.appHome;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import utility.dataHandler.DataValidation;
import utility.dataHandler.UserAuthentication;
import utility.navigation.Navigation;

public class LoginController {
    @FXML
    private AnchorPane basePane;

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Label validationLabel;


    @FXML
    void login(ActionEvent event) {
        if (fieldValidation()) {
            String authenticateMessage = UserAuthentication.authenticateUser(emailTextField.getText(), passwordTextField.getText());

            if (authenticateMessage.equals("true")){
                Navigation navigationn = new Navigation();
                navigationn.loadHomeStage(basePane);
            }else
                validationLabel.setText(authenticateMessage);
        } else
            fieldValidationMessage();
    }
    private boolean fieldValidation() {
        return DataValidation.TextFieldNotEmpty(emailTextField.getText())
                && DataValidation.TextFieldNotEmpty(passwordTextField.getText())
                && DataValidation.isValidEmail(emailTextField.getText());
    }

    private void fieldValidationMessage() {
        if (!(DataValidation.TextFieldNotEmpty(emailTextField.getText())
                && DataValidation.TextFieldNotEmpty(passwordTextField.getText()))) {
            DataValidation.TextFieldNotEmpty(emailTextField.getText(), validationLabel, "User Name/Password Fields Cannot be Empty");
            DataValidation.TextFieldNotEmpty(passwordTextField.getText(), validationLabel, "User Name/Password Fields Cannot be Empty");
            DataValidation.isValidEmail(emailTextField.getText(), validationLabel, "Invalid Email Address ");
        }
    }

    private void clearLabel() {
        validationLabel.setText("");
    }

    @FXML
    private void clearFields(ActionEvent event) {
        emailTextField.setText("");
        passwordTextField.setText("");
        clearLabel();
    }
}
