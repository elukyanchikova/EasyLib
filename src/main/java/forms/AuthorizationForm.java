package forms;

import core.ActionManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import storage.DatabaseManager;
import users.*;
import users.userTypes.Guest;
import users.userTypes.UserType;

import java.net.URL;

public class AuthorizationForm {

    private Stage stage;
    private Scene scene;
    private DatabaseManager databaseManager;

    @FXML private Button loginAsStudentBtn;
    @FXML private Button loginAsGuestBtn;
    @FXML private ListView<UserCard> usersListView;
    @FXML private TextField dateField;
    /**
     * Initialization and run new scene on the primary stage
     */
    public void startForm(Stage primaryStage) throws Exception{
        this.stage = primaryStage;
        UserType.load();
        databaseManager = new DatabaseManager("library");
        sceneInitialization();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initialization scene and scene's elements
     */
    private void sceneInitialization() throws Exception {
        URL url = getClass().getResource("FXMLFiles/AuthorizationForm.fxml");
        System.err.println(url);
        FXMLLoader loader = new FXMLLoader(url);
        loader.setController(this);
        GridPane root = loader.load();
        this.scene = new Scene(root,700,700);

        loginAsStudentBtn = (Button) scene.lookup("#loginAsStudentBtn");
        loginAsGuestBtn = (Button) scene.lookup("#loginAsGuestBtn");

        usersListView = (ListView<UserCard>) scene.lookup("#usersListView");
        dateField = (TextField) scene.lookup("#dateField");
        dateField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("(3[0-1]|[12][0-9]|0[1-9])\\/(1[0-2]|0[1-9])")){
                    loginAsStudentBtn.setDisable(true);
                    loginAsGuestBtn.setDisable(true);
                }else {
                    loginAsStudentBtn.setDisable(false);
                    loginAsGuestBtn.setDisable(false);
                }
            }
        });

        usersListView.setItems(FXCollections.observableArrayList(databaseManager.getAllUsers()));
        usersListView.setCellFactory(new Callback<ListView<UserCard>, ListCell<UserCard>>() {
            public ListCell<UserCard> call(ListView<UserCard> userCardListView) {
                return new ListCell<UserCard>() {
                    @Override
                    protected void updateItem(UserCard user, boolean flag) {
                        super.updateItem(user, flag);
                        if (user != null) {
                            setText(user.userType.getClass().getName().replace("users.userTypes.", "") + " " + user.name + " " + user.surname);
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

        int day = 1;
        int month = 1;
        if(dateField.getText() != null){
            String str = dateField.getText();
            if(str.length() == 5 &&
                    str.charAt(0) >= '0' && str.charAt(0) <= '9' &&
                    str.charAt(1) >= '0' && str.charAt(1) <= '9' &&
                    str.charAt(3) >= '0' && str.charAt(3) <= '9' &&
                    str.charAt(4) >= '0' && str.charAt(4) <= '9'){
                day = Integer.parseInt(str.substring(0,2));
                month = Integer.parseInt(str.substring(3,5));
                if(month > 12 || month < 1) month = 12;
                if(day > 31 || day<1) day = 28;
            }
        }
        if(selectedUser != null ){
            MainForm mainForm = new MainForm();
            Session session = new Session(selectedUser.userType, day,month);
            session.userCard = selectedUser;
            mainForm.startForm(stage, session, databaseManager, databaseManager.actionManager);

        }
    }

    private UserCard selectedUser;
    @FXML
    public void selectUserOnListView() throws Exception{
        int i = usersListView.getSelectionModel().getSelectedIndex();
        if( i > -1){
            selectedUser = databaseManager.getUserCard(databaseManager.getUserCardsID()[i]);
        }
    }


     /**
     /* Click on button "loginAsGuest" event
     */
    @FXML
    public void loginAsGuest()throws Exception{
        int day = 1;
        int month = 1;
        if(dateField.getText() != null){
            String str = dateField.getText();
            if(str.length() == 5 &&
                    str.charAt(0) >= '0' && str.charAt(0) <= '9' &&
                    str.charAt(1) >= '0' && str.charAt(1) <= '9' &&
                    str.charAt(3) >= '0' && str.charAt(3) <= '9' &&
                    str.charAt(4) >= '0' && str.charAt(4) <= '9'){
                day = Integer.parseInt(str.substring(0,2));
                month = Integer.parseInt(str.substring(3,5));
                if(month > 12 || month < 1) month = 1;
                if(day > 31 || day<1) day = 1;
            }
        }
        MainForm mainForm = new MainForm();
        mainForm.startForm(stage,new Session(new Guest(), day,month), databaseManager, databaseManager.actionManager);
    }
}
