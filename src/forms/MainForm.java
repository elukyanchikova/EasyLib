package forms;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class MainForm extends Application{

    public static void main(String[] args){
        launch(args);
    }


    @FXML private ListView<String> bookListView;
    ObservableList<String> documents = FXCollections.observableArrayList("Test", "Test2");
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("EasyLib");

        GridPane root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));

        Scene scene = new Scene(root,700,700);
        primaryStage.setScene(scene);

        bookListView = (ListView<String>) scene.lookup("#bookListView");

        bookListView.setItems(documents);

        primaryStage.show();
    }


}
