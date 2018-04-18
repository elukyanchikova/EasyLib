package forms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import storage.DatabaseManager;
import users.Session;

public class LogForm {

    private Stage stage;
    private Scene scene;
    private Session session;
    private DatabaseManager databaseManager;

    @FXML
    private TextArea logTextArea;

    @FXML
    private static Button backBtn;
    @FXML
    private static Button refreshBtn;

    public void startForm(Stage primaryStage, Session currentSession, DatabaseManager databaseManager) throws Exception {
        this.session = currentSession;
        this.stage = primaryStage;
        this.databaseManager = databaseManager;
        sceneInitialization();
        stage.setScene(scene);
        stage.show();
    }

    public void setSession(Session session) {
        this.session = session;
    }

    private void sceneInitialization() throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFiles/logForm.fxml"));
        loader.setController(this);
        GridPane root = loader.load();
        this.scene = new Scene(root, 1000, 700);

        backBtn = (Button) scene.lookup("#backBtn");
        refreshBtn = (Button) scene.lookup("#refreshBtn");

        logTextArea = (TextArea) scene.lookup("#logTextArea");

    }

    @FXML
    public void back() throws Exception {
        MainForm mainForm = new MainForm();
        mainForm.startForm(stage, session, databaseManager);
    }

    public void refresh(String textToView) throws Exception {
        logTextArea.setText(textToView);
    }


}
