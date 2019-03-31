package app.controller;

import app.model.PropertiesFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;

public class DefaultLocation {

    @FXML
    private Button browseButton;

    @FXML
    private TextField locationTextField;

    @FXML
    private Button carryOnButton;


    @FXML
    private void initialize() throws Exception{


    }

    @FXML
    void selectFile(ActionEvent event) {
        FileChooser file = new FileChooser();
        file.setInitialDirectory(new File (System.getProperty("user.home")+ "\\AppData\\Roaming\\Sekiro"));
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("SEKIRO savefiles (*.sl2)", "*.sl2");
        file.getExtensionFilters().add(extFilter);

        File selectedFile = file.showOpenDialog(null);


        if (selectedFile != null){
            locationTextField.setText(selectedFile.getAbsolutePath());
            carryOnButton.setDisable(false);

            PropertiesFile pf = new PropertiesFile();
            String test = selectedFile.getPath();
            pf.saveProperty("sekiroLocation", test);
//            System.out.println(selectedFile.getAbsolutePath());

        }else{

        }
    }


    @FXML
    void changeStage(ActionEvent event) throws IOException {
        Parent registerParent = FXMLLoader.load(getClass().getResource("../view/app.fxml"));
        Scene registerScene = new Scene(registerParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.hide();
        window.setScene(registerScene);
        window.show();
    }

}
