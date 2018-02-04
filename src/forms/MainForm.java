package forms;

import documents.Document;
import documents.DocumentStorage;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import users.Guest;
import users.Session;

import java.util.ArrayList;

public class MainForm {

    private Stage stage;
    private Scene scene;
    private Session session;

    ArrayList<Document> documents = new ArrayList<>();
    private int openDocumentID = -1;

    @FXML private GridPane documentInfoPane;

    @FXML private ListView<Document> documentListView;

    @FXML private javafx.scene.control.Label titleLbl;
    @FXML private javafx.scene.control.Label authorsLbl;
    @FXML private javafx.scene.control.Label documentTypeLbl;
    @FXML private javafx.scene.control.Label priceLbl;
    @FXML private javafx.scene.control.Label keywordsLbl;
    @FXML private javafx.scene.control.Label requestLbl;
    @FXML private static Button checkoutButton;


    public void startForm(Stage primaryStage, Session currentSession) throws Exception{
        this.session = currentSession;
        this.stage = primaryStage;
        documents = DocumentStorage.getDocuments();
        sceneInitialization();
        stage.setScene(scene);
        stage.show();
    }

    private void sceneInitialization() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainForm.fxml"));
        loader.setController(this);
        GridPane root = loader.load();
        this.scene = new Scene(root,700,700);

        documentListView = (ListView<Document>) scene.lookup("#documentListView");
        documentInfoPane = (GridPane) scene.lookup("#documentInfoPane");
        titleLbl = (javafx.scene.control.Label) scene.lookup("#titleLbl");
        authorsLbl = (javafx.scene.control.Label) scene.lookup("#authorsLbl");
        documentTypeLbl = (javafx.scene.control.Label) scene.lookup("#documentTypeLbl");
        priceLbl = (javafx.scene.control.Label) scene.lookup("#priceLbl");
        keywordsLbl = (javafx.scene.control.Label) scene.lookup("#keywordsLbl");
        requestLbl = (javafx.scene.control.Label) scene.lookup("#requestLbl");
        checkoutButton = (javafx.scene.control.Button) scene.lookup("#checkoutButton");

        if(session.getUser().getClass().equals(Guest.class)) checkoutButton.setVisible(false);

        documentListView.setItems(FXCollections.observableArrayList(documents));
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
    }

    @FXML
    public void selectDocument(){
        if(documentListView.getSelectionModel().getSelectedIndex() > -1) {
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
}
