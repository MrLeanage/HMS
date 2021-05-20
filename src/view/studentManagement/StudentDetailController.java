package view.studentManagement;

import bean.Student;
import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import service.StudentService;
import utility.dataHandler.DataValidation;
import utility.dataHandler.UtilityMethod;
import utility.popUp.AlertPopUp;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class StudentDetailController implements Initializable {
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
    private Label nicLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private JFXButton updateButton;

    @FXML
    private JFXButton addButton;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextArea addressTextField;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private ImageView photoImageView;

    @FXML
    private Label photoLabel;

    private static Student selectedStudent;

    private static File staticFile;

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
    void addStudent(ActionEvent event) {
        ObservableList<Student> modelList = studentTable.getItems();
        ArrayList<String> studentList = new ArrayList<>();
        for (Student student : modelList) {
            studentList.add(student.getsNIC().toLowerCase());
        }

        clearLabels();
        if (studentValidation() && !UtilityMethod.checkDataAvailability(studentList, nicTextField.getText().toLowerCase())) {
            Student student = new Student();
            StudentService studentService = new StudentService();

            student.setsNIC(nicTextField.getText());
            student.setsName(nameTextField.getText());
            student.setsAddress(addressTextField.getText());
            student.setsPhone(Integer.valueOf(phoneTextField.getText()));
            student.setsImage(photoImageView);


            if (studentService.insertStudentData(student)) {
                AlertPopUp.insertSuccesfully("Student");
                loadData();
                searchTable();
                clearFields(event);
            } else
                AlertPopUp.insertionFailed("Student");

        } else
            studentValidationMessage();
    }

    @FXML
    void updateStudent(ActionEvent event) {
        clearLabels();
        if (studentValidation()) {
            Student student = new Student();
            StudentService studentService = new StudentService();

            student.setsNIC(selectedStudent.getsNIC());
            student.setsName(nameTextField.getText());
            student.setsAddress(addressTextField.getText());
            student.setsPhone(Integer.valueOf(phoneTextField.getText()));
            student.setsImage(photoImageView);

            if (studentService.updateStudentData(student)) {
                AlertPopUp.updateSuccesfully("Student");
                loadData();
                searchTable();
                clearFields(event);
            } else
                AlertPopUp.updateFailed("Student");
        } else
            studentValidationMessage();
    }

    @FXML
    private void deleteStudent(ActionEvent event) {
        if (selectedStudent != null) {
            StudentService studentService = new StudentService();
            Optional<ButtonType> action = AlertPopUp.deleteConfirmation("Student");
            if (action.get() == ButtonType.OK) {
                if (studentService.deleteStudentData((selectedStudent.getsNIC()))) {
                    AlertPopUp.deleteSuccesfull("Student");
                    loadData();
                    searchTable();
                    clearFields(event);
                } else
                    AlertPopUp.deleteFailed("Student");
            } else
                loadData();
        } else {
            AlertPopUp.selectRowToDelete("Student");
        }
    }

    @FXML
    void setSelectedStudentDataToFields(MouseEvent event) {
        try {
            clearLabels();
            addButton.setVisible(false);
            updateButton.setVisible(true);
            nicTextField.setEditable(false);
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
    void clearFields(ActionEvent event) {
        nicTextField.setText("");
        nameTextField.setText("");
        addressTextField.setText("");
        phoneTextField.setText("");
        photoImageView.setImage(null);
        resetComponents();
        clearLabels();
        selectedStudent = null;
    }

    private void clearLabels() {
        nicLabel.setText("");
        nameLabel.setText("");
        addressLabel.setText("");
        phoneLabel.setText("");
        phoneLabel.setText("");
    }

    private boolean studentValidation() {


        return DataValidation.TextFieldNotEmpty(nicTextField.getText())
                && DataValidation.TextFieldNotEmpty(nameTextField.getText())
                && DataValidation.TextFieldNotEmpty(addressTextField.getText())
                && DataValidation.TextFieldNotEmpty(phoneTextField.getText())
                && DataValidation.ImageFieldNotEmpty(photoImageView)

                && DataValidation.isValidMaximumLength(nameTextField.getText(), 45)
                && DataValidation.isValidMaximumLength(addressTextField.getText(), 400)

                && DataValidation.isValidNIC(nicTextField)
                && DataValidation.isValidPhoneNo(phoneTextField.getText());


    }

    private void studentValidationMessage() {
        ObservableList<Student> modelList = studentTable.getItems();
        ArrayList<String> studentList = new ArrayList<>();
        for (Student student : modelList) {
            studentList.add(student.getsNIC().toLowerCase());
        }

        if (!(DataValidation.TextFieldNotEmpty(nicTextField.getText())
                && DataValidation.TextFieldNotEmpty(nameTextField.getText())
                && DataValidation.TextFieldNotEmpty(addressTextField.getText())
                && DataValidation.TextFieldNotEmpty(phoneTextField.getText())
                && DataValidation.ImageFieldNotEmpty(photoImageView))) {
            DataValidation.TextFieldNotEmpty(nicTextField.getText(), nicLabel, "Student's NIC Required!");
            DataValidation.TextFieldNotEmpty(nameTextField.getText(), nameLabel, "Student's Name Required!");
            DataValidation.TextFieldNotEmpty(addressTextField.getText(), addressLabel, "Student's address Required!");
            DataValidation.TextFieldNotEmpty(phoneTextField.getText(), phoneLabel, "Student's Contact No Required!");
            DataValidation.ImageFieldNotEmpty(photoImageView, photoLabel, "Select a Image");

        }
        if (!(DataValidation.isValidMaximumLength(nameTextField.getText(), 45)
                && DataValidation.isValidMaximumLength(addressTextField.getText(), 400))) {

            DataValidation.isValidMaximumLength(nameTextField.getText(), 45, nameLabel, "Field Limit 45 Exceeded!");
            DataValidation.isValidMaximumLength(addressTextField.getText(), 400, addressLabel, "Field Limit 400 Exceeded!");
        }
        if (!(DataValidation.isValidNIC(nicTextField)
                && DataValidation.isValidPhoneNo(phoneTextField.getText()))) {
            DataValidation.isValidNIC(nicTextField, nicLabel, "Invalid NIC Number!!");
            DataValidation.isValidPhoneNo(phoneTextField.getText(), phoneLabel, "Invalid Phone Number!!");

        }
        if (!UtilityMethod.checkDataAvailability(studentList, nicTextField.getText().toLowerCase())) {
            checkStudentNICAvailability();
        }
    }

    @FXML
    private void checkStudentNICAvailability() {
        ObservableList<Student> modelList = studentTable.getItems();
        ArrayList<String> studentList = new ArrayList<>();
        for (Student student : modelList) {
            studentList.add(student.getsNIC().toLowerCase());
        }
        if (nicTextField.getText().isEmpty()) {
            nicLabel.setStyle("-fx-text-fill: #ff0000 ");
            nicLabel.setText("NIC Cannot be empty");
        } else if (UtilityMethod.checkDataAvailability(studentList, nicTextField.getText().toLowerCase()) && DataValidation.isValidNIC(nicTextField)) {
            nicLabel.setStyle("-fx-text-fill: #ff0000 ");
            nicLabel.setText("Student with NIC Already exist!!");
        } else {
            if (DataValidation.isValidNIC(nicTextField)) {
                nicLabel.setStyle("-fx-text-fill: #00B605 ");
                nicLabel.setText("NIC Available");
            } else {
                nicLabel.setStyle("-fx-text-fill: #ff0000 ");
                nicLabel.setText("Invalid NIC Address!!");
            }

        }
    }

    private void resetComponents() {
        addButton.setVisible(true);
        updateButton.setVisible(false);
        nicTextField.setEditable(true);
    }

    @FXML
    private void chooseImage(ActionEvent event){
        photoLabel.setText("");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.jpg"));
        File file = fileChooser.showOpenDialog(null);
        if(file != null){
            staticFile = file;
            Image image = new Image(file.toURI().toString());
            photoImageView.setImage(image);
        }
    }
}
