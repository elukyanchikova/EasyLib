package forms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import storage.Database;
import users.Guest;
import users.Session;

public class EditForm {

    private Stage stage;
    private Scene scene;
    private Session session;
    private Database database;

    @FXML private Button addFileBtn;
    @FXML private Button deleteFileBtn;
    @FXML private Button modifyFileBtn;

    @FXML private Button addUserBtn;
    @FXML private Button deleteUserBtn;
    @FXML private Button modifyUserBtn;

    /**
     * Initialization and run new scene on the primary stage
     */
    void startForm(Stage primaryStage, Session currentSession, Database database) throws Exception{
        this.session = currentSession;
        this.stage = primaryStage;
        sceneInitialization();
        this.database = database;
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initialization scene and scene's elements
     */
    private void sceneInitialization() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditForm.fxml"));
        loader.setController(this);
        GridPane root = loader.load();
        this.scene = new Scene(root,700,700);

        deleteFileBtn = (Button) scene.lookup("#deleteFileBtn");
        addFileBtn = (Button) scene.lookup("#addFileBtn");
        modifyFileBtn = (Button) scene.lookup("#modifyFileBtn");

        deleteUserBtn = (Button) scene.lookup("#deleteUserBtn");
        addUserBtn = (Button) scene.lookup("#addUserBtn");
        modifyUserBtn = (Button) scene.lookup("#modifyUserBtn");

    }

    /**
     * Click on button "addUser" event
     * If the User has an access to editing(i.e. librian) than the inf can be edited by him
     */

    @FXML
    public void addFile() throws Exception {

        addFileForm mainForm = new addFileForm();
        mainForm.startForm(stage,new Session(new Guest()));

    }
    @FXML
    public void addUser() throws Exception {
        addUserForm mainForm = new addUserForm();
        mainForm.startForm(stage,new Session(new Guest()));
    }
    @FXML
    public void modifyUser(){
        // add deleting

    }
    @FXML
    public void modifyFile() throws Exception {
        modifyFileForm mainForm = new modifyFileForm();
        mainForm.startForm(stage,new Session(new Guest()));
    }
}
