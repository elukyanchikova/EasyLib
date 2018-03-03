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

public class AuthorizationForm {


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
    /*private void sceneInitialization() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AuthorizationForm.fxml"));
        loader.setController(this);
        GridPane root = loader.load();
        this.scene = new Scene(root,700,700);

        emailTextField = (TextField) scene.lookup("#emailTextField");
        loginAsStudentBtn = (Button) scene.lookup("#loginAsStudentBtn");
        loginAsGuestBtn = (Button) scene.lookup("#loginAsGuestBtn");
    }*/

    private void sceneInitialization() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditForm.fxml"));
        loader.setController(this);
        GridPane root = loader.load();
        this.scene = new Scene(root,700,700);

        addFileBtn = (Button) scene.lookup("#addFileBtn");
        addUserBtn = (Button) scene.lookup("#addUserBtn");
        deleteFileBtn = (Button) scene.lookup("#deleteFileBtn");
        deleteUserBtn = (Button) scene.lookup("#deleteUserBtn");
        modifyFileBtn = (Button) scene.lookup("#modifyFileBtn");
        modifyUserBtn = (Button) scene.lookup("#modifyUserBtn");

    }
    /**
     * Click on button "loginAsStudent" event
     * If textField has right format(will be stronger filter soon) then the student log in(temp: from one account)
   /*  *//*
    @FXML
    public void loginAsStudent() throws Exception{

        //TODO: get UserCard from storage
        if(emailTextField.getText().toLowerCase().contains("@innopolis.ru") &&
                emailTextField.getText().toLowerCase().replace("@innopolis.ru", "").replace(" ", "").length() != 0)
        {
            MainForm mainForm = new MainForm();
            Session session = new Session(new Student());
            session.userCard = Storage.getUsers().get(0);
            mainForm.startForm(stage, session);
        }
    }*/


     /**
     /* Click on button "loginAsGuest" event
     */
    /*@FXML
    public void loginAsGuest()throws Exception{
        MainForm mainForm = new MainForm();
        mainForm.startForm(stage,new Session(new Guest()));
    }
*/
     @FXML
     public void loginAsGuest()throws Exception{
         EditForm mainForm = new EditForm();
         mainForm.startForm(stage,new Session(new Guest()));
     }




}
