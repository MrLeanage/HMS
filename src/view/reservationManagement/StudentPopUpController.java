package view.reservationManagement;

import bean.Room;
import bean.Student;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.StudentService;
import utility.dataHandler.DataValidation;
import utility.dataHandler.UtilityMethod;
import utility.popUp.AlertPopUp;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class StudentPopUpController implements Initializable {
    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student, ImageView> imageColumn;

    @FXML
    private TableColumn<Student, String> nicColumn;

    @FXML
    private TableColumn<Student, String> nameColumn;

    @FXML
    private TableColumn<Student, String> addressColumn;

    @FXML
    private TableColumn<Student, Integer> phoneColumn;

    @FXML
    private TextField searchTextField;

    @FXML
    private TextField nicTextField;

    @FXML
    private TextField nameTextField;


    @FXML
    private TextField phoneTextField;

    @FXML
    private TextArea addressTextField;

    @FXML
    private ImageView photoImageView;

    @FXML
    private JFXButton cancelButton;


    private static Student selectedStudent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }
    private void loadData() {
        StudentService studentService = new StudentService();
        ObservableList<Student> studentObservableList = studentService.loadAllStudentData();

        imageColumn.setCellValueFactory(new PropertyValueFactory<>("sImage"));
        nicColumn.setCellValueFactory(new PropertyValueFactory<>("sNIC"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("sName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("sAddress"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("sPhone"));
        imageColumn.setCellFactory(param -> {
            //Set up the ImageView
            final ImageView imageview = new ImageView();
            imageview.setFitHeight(80);
            imageview.setFitWidth(80);

            //Set up the Table
            TableCell<Student, ImageView> cell = new TableCell<Student, ImageView>() {
                public void updateItem(ImageView item, boolean empty) {
                    if(item != null){
                        if(!empty){
                            imageview.setImage(item.getImage());
                        }
                    }
                }
            };
            // Attach the imageview to the cell
            cell.setGraphic(null);
            cell.setGraphic(imageview);
            return cell;
        });

        studentTable.setItems(null);
        studentTable.setItems(studentObservableList);
        searchTable();
    }

    public void searchTable() {

        StudentService studentService = new StudentService();

        SortedList<Student> sortedData = studentService.searchTable(searchTextField);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(studentTable.comparatorProperty());
        //adding sorted and filtered data to the table

        studentTable.setItems(sortedData);
    }

    @FXML
    void setSelectedStudentDataToFields(MouseEvent event) {
        try {
            selectedStudent = studentTable.getSelectionModel().getSelectedItem();
            nicTextField.setText(selectedStudent.getsNIC());
            nameTextField.setText(selectedStudent.getsName());
            addressTextField.setText(selectedStudent.getsAddress());
            phoneTextField.setText("0" + selectedStudent.getsPhone().toString());
            photoImageView.setImage(selectedStudent.getsImage().getImage());
        } catch (NullPointerException exception) {

        }
    }

    @FXML
    void addSelectedStudent(ActionEvent event) {
        if (selectedStudent != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("reservationDetail.fxml"));
            try {
                Parent root = (Parent) loader.load();
                ReservationDetailController orderDetailController = loader.getController();

                orderDetailController.setStudent(selectedStudent);
                selectedStudent = null;
                closeStage();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } else
            AlertPopUp.selectRow("Student to Add");
    }

    private void closeStage(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void closeStage(ActionEvent actionEvent){
        // get a handle to the stage
        closeStage();
    }

}
