package forms;


import documents.Book;
import documents.Document;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;


public class MainForm extends Application{

    public static void main(String[] args){
        launch(args);
    }

    ArrayList<Document> documents = new ArrayList<>();

    @FXML private ListView<Document> bookListView;
    @FXML private Label titleLbl;

    ObservableList<Document> data;
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("EasyLib");

        GridPane root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));

        Scene scene = new Scene(root,700,700);
        primaryStage.setScene(scene);

        titleLbl = (Label) scene.lookup("#titleLbl");

        Book book1 = new Book();
        Book book2 = new Book();
        book1.title = "Test1";
        book2.title = "Test2";
        documents.add(book1);
        documents.add(book2);
        data = FXCollections.observableArrayList(documents);
        bookListView = (ListView<Document>) scene.lookup("#bookListView");
        bookListView.setItems(data);
        bookListView.setCellFactory(new Callback<ListView<Document>, ListCell<Document>>(){

            public ListCell<Document> call(ListView<Document> documentListView) {

                ListCell<Document> cell = new ListCell<Document>(){

                    @Override
                    protected void updateItem(Document document, boolean flag) {
                        super.updateItem(document, flag);
                        if (document != null) {
                            setText(document.title);
                        }
                    }

                };
                return cell;
            }
        });


        bookListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Document chosenDocument = documents.get(bookListView.getSelectionModel().getSelectedIndex());
                titleLbl.setText(chosenDocument.title);

            }
        });

        primaryStage.show();
    }


}
