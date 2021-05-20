package view.appHome;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/appHome/loginStage.fxml"));
        primaryStage.setTitle("H O S T E L   M A N A G E M E N T   S Y S T E M");
        primaryStage.setScene(new Scene(root, 1000, 700));
        primaryStage.getIcons().add(new Image("/lib/images/favicon.png"));
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
