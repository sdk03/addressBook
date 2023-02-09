package sadiq.code.addressbook;
//Import Statements
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import sadiq.code.addressbook.model.data;

import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import javax.swing.*;
import javax.swing.JFileChooser;
import java.io.File;
import java.util.List;


//Inititalize Class
public class Controller {

    //FILENAME VARIABLE DECLARATION
    String filename = "data2.txt";

    //CHOOSE FILE FROM USER
    public void filechoose() {
        FileDialog fd = new FileDialog((Frame) null, "Choose a database file", FileDialog.LOAD);
        fd.setDirectory("C:\\");
        fd.setFile("*.txt");
        fd.setVisible(true);
        filename = fd.getFile();
        if (filename == null)
            JOptionPane.showMessageDialog(null, "No File Selected");
        else
            JOptionPane.showMessageDialog(null, "You chose " + filename);

        initialize();
    }

    //DECLARE AN INTERNAL LIST OF TYPE DATA - THIS IS TO USE FOR SAVING NUMBER AND PHONE AT SAME INDEX
    List<data> internalList = new ArrayList<>();

    //SCENE & STAGE IMPORTS
    private Stage stage;
    private Scene scene;
    private Parent root;

    //FXML IMPORTS
    @FXML
    private TextField name;
    @FXML
    private TextField number;
    @FXML
    private TextArea display;
    @FXML
    private ListView<data> list;
    @FXML
    private Label records;
    @FXML
    private TextField search;
    @FXML
    private BorderPane b1;

    //DECLARING VARIABLES FOR FXML TO JAVA TRANSITION
    private String inputName;
    private String inputNumber;
    String output = "";
    String Jrecords = "";

    //DECLARE ALERTS
    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Record Added");
    Alert about = new Alert(Alert.AlertType.INFORMATION, "This is an directory made by \nSDK SOFTWARES");

    //METHOD TO DISPLAY DATA(PHONE NUMBER) IN TEXT AREA
    public void showList() {
        data selection = list.getSelectionModel().getSelectedItem();
        StringBuilder sb = new StringBuilder(selection.getPhone());
        display.setText(sb.toString());

        name.setText(selection.getName());
        number.setText(selection.getPhone());
    }

    public void showRecordNo() {
        Jrecords = String.valueOf(internalList.size());
        records.setText(String.format("%s records found", Jrecords));
    }

    //BELOW CODE RUNS ON STARTUP

    public void initialize() {

        //READ FILENAME
        Path pathRead = Paths.get(filename);
        try {
            BufferedReader br = Files.newBufferedReader(pathRead); //INITIALIZE BUFFERED READER
            //READ EVERY LINE FROM FILE UNTIL NO LINE REMAINS
            while ((output = br.readLine()) != null) {
                String[] separated = output.split("!!!"); //SEPARATING DATA AT !!! SEPARATOR
                data item = new data(separated[0], separated[1]); //CREATE OBJECT item TO OF TYPE data
                internalList.add(item); //ADD IT TO OUR INTERNAL ARRAY LIST
                list.getItems().add(item);// ADD IT TO FXML LIST
            }
            br.close();// CLOSE BUFFERED READER

        } catch (IOException e) {
            e.printStackTrace();
        }

        //EVENT LISTENER FOR FXML LIST
        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<data>() {
            @Override
            public void changed(ObservableValue<? extends data> observableValue, data data, data t1) {
                showList();
            }
        });

        //NUMBER OF RECORDS FOUND METHOD
        showRecordNo();

        //EVENT LISTENER FOR SEARCH
        search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String searchText = search.getText();

            }
        });
    }

    //METHOD TO ADD TO FILE
    public void addtoFile() throws IOException {

        Path path = Paths.get(filename);//GET FILE PATH
        BufferedWriter bw = Files.newBufferedWriter(path);//INITIALIZE BUFFERED WRITER

        try {
            Iterator<data> it = internalList.iterator();//CREATE ITERATOR TO ITERATE THROUGH INTERNAL LIST

            //READ EVERY INDEX IN ARRAY LIST AND SAVE DATA INTO FILE ACCORDING TO A SPECIFIC FORMAT
            while (it.hasNext()) {
                data items = it.next();
                bw.write(String.format("%s!!!%s", items.getName(), items.getPhone()));
                bw.newLine();

            }
        } finally {
            bw.close(); // CLOSE BUFFERED WRITER
        }
    }

    //METHOD TO ADD DATA TO FXML LIST, INTERNAL LIST & STORE DATA TO FILE
    @FXML
    public void go() throws IOException, InterruptedException {
        inputName = name.getText(); //FXML TO JAVA VARIABLE
        inputNumber = number.getText();//FXML TO JAVA VARIABLE

        data item = new data(inputName, inputNumber); // NEW OBJECT CREATED OF TYPE data
        internalList.add(item); // ADD CREATED OBJECT OT INTERNAL ARRAY LIST
        showRecordNo();//NUMBER OF RECORDS UPDATED AFTER ADDING RECORD

        list.getItems().add(item);// ADD OBJECT TO FXML LIST
        addtoFile();
        alert.show();
        //JOptionPane.showMessageDialog(null, "Record Added");

    }

    //METHOD TO DELETE FXML LIST, INTERNAL LIST AND REMOVE DATA FROM FILE
    @FXML
    public void delete() throws IOException {
        Path path = Paths.get(filename);//GET FILE PATH
        BufferedWriter bw = Files.newBufferedWriter(path);//INITIALIZE BUFFERED WRITER

        int indexRemove = list.getSelectionModel().getSelectedIndex();
        bw.append("");
        list.getItems().remove(indexRemove);
        internalList.remove(indexRemove);
        addtoFile();
        showRecordNo();
        JOptionPane.showMessageDialog(null, "Record Deleted");
    }


}

