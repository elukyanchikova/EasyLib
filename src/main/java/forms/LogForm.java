package forms;

import core.ActionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import storage.DatabaseManager;
import users.Session;

public class LogForm {

    private Stage stage;
    private Scene scene;
    private Session session;
    private DatabaseManager databaseManager;
    private ActionManager actionManager;

    @FXML
    private TextArea logTextArea;

    @FXML
    private static Button backBtn;

    public void startForm(Stage primaryStage, Session currentSession, DatabaseManager databaseManager, ActionManager actionManager) throws Exception {
        this.session = currentSession;
        this.stage = primaryStage;
        this.databaseManager = databaseManager;
        this.actionManager = actionManager;
        sceneInitialization();
        stage.setScene(scene);
        stage.show();
    }

    public void setSession(Session session) {
        this.session = session;
    }

    private void sceneInitialization() throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFiles/LogForm.fxml"));
        loader.setController(this);
        AnchorPane root = loader.load();
        this.scene = new Scene(root, 600, 700);


        backBtn = (Button) scene.lookup("#backBtn");

        logTextArea = (TextArea) scene.lookup("#logTextArea");

        refresh("owowowowow \nowowowowoow");

    }

    @FXML
    public void back() throws Exception {
        MainForm mainForm = new MainForm();
        mainForm.startForm(stage, session, databaseManager, actionManager);
    }

    public void refresh(String toTextArea){
        logTextArea.setText(actionManager.getLog());
    }


}
