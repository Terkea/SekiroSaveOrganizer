package app.controller;


import app.model.PropertiesFile;
import app.model.Savefile;
import app.model.SavefileJSON;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import jdk.jfr.events.ExceptionThrownEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Controller {

    public final static String CURRENT_PATH = System.getProperty("user.dir");
    public final static String SAVES_PATH = CURRENT_PATH + "\\saves";

    @FXML
    private TableView<Savefile> savesTableView;

    @FXML
    private TableColumn<Savefile, String> nameColumn;

    @FXML
    private TableColumn<Savefile, String> dataColumn;

    @FXML
    private Button saveButton;

    @FXML
    private Button loadButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField saveFileNameText;

    private static void copyFile(Path sourceDirectory, Path targetDirectory) throws IOException {
        //copy source to target using Files Class
        Files.copy(sourceDirectory, targetDirectory, StandardCopyOption.REPLACE_EXISTING);
    }


    @FXML
    private void initialize(){

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        dataColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        ObservableList<Savefile> saveFiles = SavefileJSON.getAllSavefiles();
        populateServerfiles(saveFiles);

    }


    @FXML
    private void populateServerfiles(ObservableList<Savefile> savefilesData){
        savesTableView.setItems(savefilesData);
    }


    @FXML
    public void createSaveFile(ActionEvent event) {

        boolean notEmpty = false;
        boolean notDuplicate = false;

        if (saveFileNameText.getText().equals("") || saveFileNameText.getText()==null){
            saveFileNameText.setStyle("-fx-border-color: #ff0000;" +
                    "-fx-border-radius: 5");
            saveFileNameText.setPromptText("Name field required");
        }else{
            notEmpty = true;
        }
        SavefileJSON sfj = new SavefileJSON();
        if (sfj.savefileUnique(saveFileNameText.getText()) == false){
            saveFileNameText.getText();
            saveFileNameText.setStyle("-fx-text-fill: red" +
                    "-fx-border-color: #ff0000;" +
                    "-fx-border-radius: 5");
            errorLabel.setText("The name has to be unique");
        }else{
            notDuplicate = true;
        }

        if (notEmpty == true && notDuplicate == true){
            String location = SAVES_PATH + "\\" + saveFileNameText.getText();
            File newSaveFileDirectory = new File(location);
            boolean result = false;

            //CREATING THE DIR
            try{
                newSaveFileDirectory.mkdir();
                SavefileJSON sfJ = new SavefileJSON();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM HH:mm:ss");
                Date date = new Date();
                Savefile sf = new Savefile();
                sf.setName(saveFileNameText.getText().toString());
                sf.setDate(dateFormat.format(date).toString());
                sfJ.addSavefile(sf);

                //COPY THE ACTUAL SAVE FILE TO THE DIR
                PropertiesFile pf = new PropertiesFile();
                try{
                    copyFile(Paths.get(pf.getProp("sekiroLocation")), Paths.get(SAVES_PATH + "\\" +(saveFileNameText.getText())+ "\\S0000.sl2"));
                    initialize();
                }catch(IOException e){
                    System.out.println(e);
                    System.out.println("Copy sf failed");
                }

                //check if the name its unique

                result = true;
            }catch(SecurityException se){
                System.out.println(se);
            }
            if (result){
                System.out.println("Save file directory created");
                saveFileNameText.setText("");
                errorLabel.setText("Savefile succesfully created");
                errorLabel.setStyle("-fx-text-fill: green");
            }
        }

    }

    boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    @FXML
    void deleteSavefile(ActionEvent event)throws IOException {
        SavefileJSON sfj = new SavefileJSON();
        String selectedSf = savesTableView.getSelectionModel().getSelectedItem().getName();
        sfj.deleteSavefile(selectedSf);
        File directory = new File(SAVES_PATH + "\\" +savesTableView.getSelectionModel().getSelectedItem().getName());
        deleteDirectory(directory);
        errorLabel.setStyle("-fx-text-fill: green");
        errorLabel.setText("Savefile deleted");
        initialize();
    }

    @FXML
    void loadSavefile(ActionEvent event) {
        PropertiesFile pf = new PropertiesFile();
        String selectedSf = savesTableView.getSelectionModel().getSelectedItem().getName();
        try {
            copyFile(Paths.get(SAVES_PATH + "\\" + selectedSf + "\\S0000.sl2"), Paths.get(pf.getProp("sekiroLocation")));
            errorLabel.setStyle("-fx-text-fill: green");
            errorLabel.setText("Savefile successfully loaded");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
