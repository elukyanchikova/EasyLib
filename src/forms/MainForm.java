package forms;


import documents.Document;
import documents.DocumentStorage;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import users.Guest;
import users.Session;
import users.Student;

import java.util.ArrayList;


public class MainForm extends Application implements EventHandler<MouseEvent>{

    public static void main(String[] args){
        launch(args);
    }

    Session currentSession;
    ArrayList<Document> documents = new ArrayList<>();

    private int openDocumentID = -1;

    @FXML private ListView<Document> documentListView;
    @FXML private GridPane documentInfoPane;
    @FXML private Label titleLbl;
    @FXML private Label authorsLbl;
    @FXML private Label documentTypeLbl;
    @FXML private Label priceLbl;
    @FXML private Label keywordsLbl;
    @FXML private Label requestLbl;
    @FXML private static Button checkoutButton;

    @FXML private Button loginAsStudentBtn;
    //TODO: refactor this class. Divide the functions by forms
    ObservableList<Document> data;

    static Stage primaryStage;
    static Scene scene;
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("EasyLib");

        GridPane root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        GridPane authorization = FXMLLoader.load(getClass().getResource("AuthorizationForm.fxml"));


        this.primaryStage = primaryStage;
        Scene authorizationScene = new Scene(authorization,350,300);
        this.scene = new Scene(root,700,700);
        primaryStage.setScene(authorizationScene);

        initializationAuthorizationScene(authorizationScene);
        initializationMainScene(scene);

        documents = DocumentStorage.getDocuments();

        data = FXCollections.observableArrayList(documents);
        documentListView.setItems(data);
        documentListView.setCellFactory(new Callback<ListView<Document>, ListCell<Document>>(){
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

        documentListView.setOnMouseClicked(this);

        primaryStage.show();
    }

    private void initializationMainScene(Scene scene){

        documentListView = (ListView<Document>) scene.lookup("#documentListView");
        documentInfoPane = (GridPane) scene.lookup("#documentInfoPane");
        titleLbl = (Label) scene.lookup("#titleLbl");
        authorsLbl = (Label) scene.lookup("#authorsLbl");
        documentTypeLbl = (Label) scene.lookup("#documentTypeLbl");
        priceLbl = (Label) scene.lookup("#priceLbl");
        keywordsLbl = (Label) scene.lookup("#keywordsLbl");
        requestLbl = (Label) scene.lookup("#requestLbl");
        checkoutButton = (Button) scene.lookup("#checkoutButton");


    }

    private void initializationAuthorizationScene(Scene scene){

        loginAsStudentBtn =(Button) scene.lookup("#loginAsStudentBtn");
        loginAsStudentBtn.requestFocus();
    }

    @Override
    public void handle(MouseEvent event) {
        if(openDocumentID == -1){
            documentInfoPane.setVisible(true);
        }
        openDocumentID = documentListView.getSelectionModel().getSelectedIndex();
        Document chosenDocument = documents.get(openDocumentID);
        titleLbl.setText(chosenDocument.title);
        authorsLbl.setText(chosenDocument.title);
        documentTypeLbl.setText(chosenDocument.title);
        priceLbl.setText(chosenDocument.title);
        keywordsLbl.setText(chosenDocument.title);
        requestLbl.setText(String.valueOf(tempRequests));
    }


    private int tempRequests = 0;
    private boolean requested = false;
    @FXML
    public void checkOut(){
        if(requested) {
            tempRequests--;
            checkoutButton.setText("Request");
            requested = false;
        } else{
            tempRequests++;
            requestLbl.setText(String.valueOf(tempRequests));
            checkoutButton.setText("Cancel request");
            requested = true;
        }
        requestLbl.setText(String.valueOf(tempRequests));
    }

    @FXML
    public void loginAsStudent(){
        currentSession = new Session(new Student());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    public void loginAsGuest(){
        currentSession = new Session(new Guest());
        primaryStage.setScene(scene);
        checkoutButton.setVisible(false);
        primaryStage.show();
    }
}
