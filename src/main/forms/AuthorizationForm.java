package forms;

import documents.Document;
import documents.Storage;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import storage.Database;
import users.*;

import javax.xml.crypto.Data;

public class AuthorizationForm {

    private Stage stage;
    private Scene scene;
    private Session session;
    private Database database;

    @FXML private TextField emailTextField;
    @FXML private Button loginAsStudentBtn;
    @FXML private Button loginAsGuestBtn;
    @FXML private ListView<UserCard> usersListView;

    /**
     * Initialization and run new scene on the primary stage
     */
    void startForm(Stage primaryStage) throws Exception{
        this.stage = primaryStage;
        UserType.load();
        database = new Database();
        sceneInitialization();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initialization scene and scene's elements
     */
    private void sceneInitialization() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AuthorizationForm.fxml"));
        loader.setController(this);
        GridPane root = loader.load();
        this.scene = new Scene(root,700,700);

        emailTextField = (TextField) scene.lookup("#emailTextField");
        loginAsStudentBtn = (Button) scene.lookup("#loginAsStudentBtn");
        loginAsGuestBtn = (Button) scene.lookup("#loginAsGuestBtn");
        usersListView = (ListView<UserCard>) scene.lookup("#usersListView");

        usersListView.setItems(FXCollections.observableArrayList(database.getAllUsers()));
        usersListView.setCellFactory(new Callback<ListView<UserCard>, ListCell<UserCard>>() {
            public ListCell<UserCard> call(ListView<UserCard> userCardListView) {
                return new ListCell<UserCard>() {
                    @Override
                    protected void updateItem(UserCard user, boolean flag) {
                        super.updateItem(user, flag);
                        if (user != null) {
                            setText(user.userType.getClass().getName() + " " + user.name + " " + user.surname);
                        }
                    }
                };
            }
        });
    }


    /**
     * Click on button "loginAsStudent" event
     * If textField has right format(will be stronger filter soon) then the student log in(temp: from one account)
   /*  */
    @FXML
    public void loginAsStudent() throws Exception{

        /*if(emailTextField.getText().toLowerCase().contains("@innopolis.ru") &&
                emailTextField.getText().toLowerCase().replace("@innopolis.ru", "").replace(" ", "").length() != 0)
        */
        if(selectedUser != null ){
            MainForm mainForm = new MainForm();
            Session session = new Session(selectedUser.userType);
            session.userCard = selectedUser;
            mainForm.startForm(stage, session,database);
        }
    }

    private UserCard selectedUser;
    @FXML
    public void selectUserOnListView() throws Exception{
        int i = usersListView.getSelectionModel().getSelectedIndex();
        if( i > -1){
            selectedUser = database.getUserCard(database.getUsercardsID()[i]);
        }
    }


     /**
     /* Click on button "loginAsGuest" event
     */
    @FXML
    public void loginAsGuest()throws Exception{
        MainForm mainForm = new MainForm();
        mainForm.startForm(stage,new Session(new Guest()),database);
    }
}
