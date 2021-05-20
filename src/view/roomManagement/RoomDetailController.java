package view.roomManagement;

import bean.Room;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import service.RoomService;
import utility.dataHandler.DataValidation;
import utility.popUp.AlertPopUp;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class RoomDetailController implements Initializable {
    @FXML
    private TableView<Room> roomTable;

    @FXML
    private TableColumn<Room, String> noColumn;

    @FXML
    private TableColumn<Room, String> infoColumn;

    @FXML
    private TableColumn<Room, String> dimensionColumn;

    @FXML
    private TableColumn<Room, Double> priceColumn;

    @FXML
    private TableColumn<Room, String> statusColumn;

    @FXML
    private TextField searchTextField;

    @FXML
    private Label roomNoLabel;

    @FXML
    private TextField roomNoTextField;

    @FXML
    private TextField dimensionTextField;

    @FXML
    private Label dimensionLabel;

    @FXML
    private Label infoLabel;

    @FXML
    private JFXButton updateButton;

    @FXML
    private JFXButton addButton;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextArea infoTextArea;

    @FXML
    private ChoiceBox<String> statusChoiceBox;

    @FXML
    private Label priceLabel;

    private static Room selectedRoom;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList("Available", "Not Available", "Reserved");
        statusChoiceBox.setValue("Available");
        statusChoiceBox.setItems(list);
        loadData();
    }

    private void loadData() {
        RoomService roomService = new RoomService();
        ObservableList<Room> roomObservableList = roomService.loadAllRoomData();

        noColumn.setCellValueFactory(new PropertyValueFactory<>("rNo"));
        infoColumn.setCellValueFactory(new PropertyValueFactory<>("rInfo"));
        dimensionColumn.setCellValueFactory(new PropertyValueFactory<>("rDimension"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("rPrice"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("rStatus"));

        roomTable.setItems(null);
        roomTable.setItems(roomObservableList);
        searchTable();
    }

    private void searchTable() {

        RoomService roomService = new RoomService();

        SortedList<Room> sortedData = roomService.searchTable(searchTextField);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(roomTable.comparatorProperty());
        //adding sorted and filtered data to the table
        roomTable.setItems(sortedData);
    }

    @FXML
    private void addRoom(ActionEvent event) {
        clearLabels();
        if (roomValidation()) {
            Room room = new Room();
            RoomService roomService = new RoomService();

            room.setrInfo(infoTextArea.getText());
            room.setrDimension(dimensionTextField.getText());
            room.setrPrice(Double.valueOf(priceTextField.getText()));
            room.setrStatus(statusChoiceBox.getValue());


            if (roomService.insertRoomData(room)) {
                AlertPopUp.insertSuccesfully("Room Record");
                loadData();
                searchTable();
                clearFields(event);
            } else
                AlertPopUp.insertionFailed("Room Record");

        } else
            roomValidationMessage();
    }

    @FXML
    private void updateRoom(ActionEvent event) {
        clearLabels();
        if (roomValidation()) {
            if (!selectedRoom.getrStatus().equals("Reserved")) {
                Room room = new Room();
                RoomService roomService = new RoomService();

                room.setrNo(selectedRoom.getrNo());
                room.setrInfo(infoTextArea.getText());
                room.setrDimension(dimensionTextField.getText());
                room.setrPrice(Double.valueOf(priceTextField.getText()));
                room.setrStatus(statusChoiceBox.getValue());

                if (roomService.updateRoomData(room)) {
                    AlertPopUp.updateSuccesfully("Room Record");
                    loadData();
                    searchTable();
                    clearFields(event);
                } else
                    AlertPopUp.updateFailed("Room Record");
            } else
                AlertPopUp.updateFailed("Room is already reserved!!\nRoom Status");
        } else
            roomValidationMessage();
    }

    @FXML
    private void deleteRoom(ActionEvent event) {
        if (selectedRoom != null) {
            RoomService roomService = new RoomService();
            Optional<ButtonType> action = AlertPopUp.deleteConfirmation("Room Record");
            if (action.get() == ButtonType.OK) {
                if (roomService.deleteRoomData((selectedRoom.getrNo()))) {
                    AlertPopUp.deleteSuccesfull("Room Record");
                    loadData();
                    searchTable();
                    clearFields(event);
                } else
                    AlertPopUp.deleteFailed("Room Record");
            } else
                loadData();
        } else {
            AlertPopUp.selectRowToDelete("Room Record");
        }
    }

    @FXML
    void setSelectedRoomDataToFields(MouseEvent event) {
        try {
            clearLabels();
            addButton.setVisible(false);
            updateButton.setVisible(true);
            roomNoLabel.setVisible(true);
            roomNoTextField.setVisible(true);
            selectedRoom = roomTable.getSelectionModel().getSelectedItem();
            roomNoTextField.setText(selectedRoom.getrNo());
            infoTextArea.setText(selectedRoom.getrInfo());
            dimensionTextField.setText(selectedRoom.getrDimension());
            priceTextField.setText(selectedRoom.getrPrice().toString());
            statusChoiceBox.setValue(selectedRoom.getrStatus());

        } catch (NullPointerException exception) {

        }
    }

    @FXML
    void clearFields(ActionEvent event) {
        dimensionTextField.setText("");
        priceTextField.setText("");
        infoTextArea.setText("");
        statusChoiceBox.setValue("Available");
        resetComponents();
        clearLabels();
        selectedRoom = null;
    }

    private void clearLabels() {
        dimensionLabel.setText("");
        priceLabel.setText("");
        infoLabel.setText("");
    }

    private boolean roomValidation() {
        return DataValidation.TextFieldNotEmpty(dimensionTextField.getText())
                && DataValidation.TextFieldNotEmpty(priceTextField.getText())
                && DataValidation.TextFieldNotEmpty(infoTextArea.getText())

                && DataValidation.isValidMaximumLength(dimensionTextField.getText(), 30)
                && DataValidation.isValidMaximumLength(infoTextArea.getText(), 400)

                && DataValidation.isValidNumberFormat(priceTextField.getText());
    }

    private void roomValidationMessage() {
        if (!(DataValidation.TextFieldNotEmpty(dimensionTextField.getText())
                && DataValidation.TextFieldNotEmpty(priceTextField.getText())
                && DataValidation.TextFieldNotEmpty(infoTextArea.getText()))) {
            DataValidation.TextFieldNotEmpty(dimensionTextField.getText(), dimensionLabel, "Room Dimension is Required!");
            DataValidation.TextFieldNotEmpty(priceTextField.getText(), priceLabel, "Room price is Required!");
            DataValidation.TextFieldNotEmpty(infoTextArea.getText(), infoLabel, "Room Information is Required!");

        }
        if (!(DataValidation.isValidMaximumLength(dimensionTextField.getText(), 30)
                && DataValidation.isValidMaximumLength(infoTextArea.getText(), 400))) {
            DataValidation.isValidMaximumLength(dimensionTextField.getText(), 30, dimensionLabel, "Field Limit 30 Exceeded!");
            DataValidation.isValidMaximumLength(infoTextArea.getText(), 400, infoLabel, "Field Limit 400 Exceeded!");
        }
        DataValidation.isValidNumberFormat(priceTextField.getText(), priceLabel, "Price can contain only number!!");

    }

    private void resetComponents() {
        addButton.setVisible(true);
        updateButton.setVisible(false);
        roomNoLabel.setVisible(false);
        roomNoTextField.setVisible(false);
    }
}
