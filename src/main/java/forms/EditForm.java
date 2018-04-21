package forms;

import core.ActionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import storage.DatabaseManager;
import users.Session;

public class EditForm {

    private Stage stage;
    private Scene scene;
    private Session session;
    private DatabaseManager databaseManager;
    private ActionManager actionManager;

    @FXML private Button addFileBtn;
    @FXML private Button deleteFileBtn;
    @FXML private Button modifyFileBtn;

    @FXML private Button addUserBtn;
    @FXML private Button deleteUserBtn;
    @FXML private Button modifyUserBtn;

    @FXML private Button backBtn;

    /**
     * Initialization and run new scene on the primary stage
     */
    void startForm(Stage primaryStage, Session currentSession, DatabaseManager databaseManager, ActionManager actionManager) throws Exception{
        this.session = currentSession;
        this.stage = primaryStage;
        this.actionManager = actionManager;
        sceneInitialization();
        this.databaseManager = databaseManager;
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initialization scene and scene's elements
     */
    private void sceneInitialization() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFiles/EditForm.fxml"));
        loader.setController(this);
        GridPane root = loader.load();
        this.scene = new Scene(root,700,700);

        deleteFileBtn = (Button) scene.lookup("#deleteFileBtn");
        addFileBtn = (Button) scene.lookup("#addFileBtn");
        modifyFileBtn = (Button) scene.lookup("#modifyFileBtn");

        deleteUserBtn = (Button) scene.lookup("#deleteUserBtn");
        addUserBtn = (Button) scene.lookup("#addUserBtn");
        modifyUserBtn = (Button) scene.lookup("#modifyUserBtn");
        backBtn = (Button) scene.lookup("#backBtn");

    }

    /**
     * Click on button "addFile" event
     */
    @FXML
    public void addFile() throws Exception {

        addFileForm mainForm = new addFileForm();
        mainForm.startForm(stage,session, databaseManager, actionManager);

    }

    /**
     * Click on button "addUser" event
     * If the User has an access to editing(i.e. librarian) than the inf can be edited by him
     */
    @FXML
    public void addUser() throws Exception {
        addUserForm mainForm = new addUserForm();
        mainForm.startForm(stage,session, databaseManager, actionManager);
    }

    /**
     * Click on button "modifyUser" event
     */
    @FXML
    public void modifyUser() throws Exception {
        modifyUserForm mainForm = new modifyUserForm();
        mainForm.startForm(stage,session, databaseManager,actionManager);

    }

    /**
     * Click on button "modifyFile" event
     */
    @FXML
    public void modifyFile() throws Exception {
        modifyFileForm mainForm = new modifyFileForm();
        mainForm.startForm(stage,session, databaseManager, actionManager);
    }

    /**
     *  Click on button "Back" event
     *  button for coming back to the MainForm
     *  @throws Exception
     */
    @FXML public void back() throws Exception {
        MainForm mainForm = new MainForm();
        mainForm.startForm(stage, session, databaseManager, actionManager);
    }
}
