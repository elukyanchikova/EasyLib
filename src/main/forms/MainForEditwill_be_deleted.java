package forms;
package forms;

import documents.Storage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import users.Guest;
import users.Session;
import users.Student;

public class MainForEditwill_be_deleted  {


    private Stage stage;
    private Scene scene;
    private Session session;

    @FXML private Button addFileBtn;
    @FXML private Button deleteFileBtn;
    @FXML private Button modifyFileBtn;

    @FXML private Button addUserBtn;
    @FXML private Button deleteUserBtn;
    @FXML private Button modifyUserBtn;

    /**
     * Initialization and run new scene on the primary stage
     */
    void startForm(Stage primaryStage) throws Exception{
        this.stage = primaryStage;
        sceneInitialization();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initialization scene and scene's elements
     */
    private void sceneInitialization() throws Exception {

        deleteFileBtn = (Button) scene.lookup("#deleteFileBtn");
        addFileBtn = (Button) scene.lookup("#addFileBtn");
        modifyFileBtn = (Button) scene.lookup("#modifyFileBtn");

        deleteUserBtn = (Button) scene.lookup("#deleteUserBtn");
        addUserBtn = (Button) scene.lookup("#addUserBtn");
        modifyUserBtn = (Button) scene.lookup("#modifyUserBtn");
    }


    /**
     * Click on button "loginAsStudent" event
     * If textField has right format(will be stronger filter soon) then the student log in(temp: from one account)
     */
    @FXML
    public void loginAsStudent() throws Exception{

        //TODO: get UserCard from storage

        EditForm mainForm = new EditForm();
        Session session = new Session(new Student());
        session.userCard = Storage.getUsers().get(0);
        mainForm.startForm(stage, session);

    }


    /**
     * Click on button "loginAsGuest" event
     */
    @FXML
    public void loginAsGuest()throws Exception{
        EditForm mainForm = new EditForm();
        mainForm.startForm(stage, new Session(new Guest()));
    }

}

