package app;

import app.model.PropertiesFile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        PropertiesFile pf = new PropertiesFile();
        String property = pf.getProp("sekiroLocation");
        if (property != null && property != "" && property.contains("S0000.sl2")){
            Parent root = FXMLLoader.load(getClass().getResource("view/app.fxml"));
            primaryStage.setTitle("Sekiro Save Organizer");
            primaryStage.setScene(new Scene(root, 400, 500));
            primaryStage.show();
            primaryStage.setResizable(false);
        }else{
            Parent root = FXMLLoader.load(getClass().getResource("view/SelectLocation.fxml"));
            primaryStage.setTitle("Sekiro Save Organizer");
            primaryStage.setScene(new Scene(root, 500, 200));
            primaryStage.show();
            primaryStage.setResizable(false);
        }
    }


    public static void main(String[] args) throws IOException {
        launch(args);
    }
}
