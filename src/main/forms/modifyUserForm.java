package forms;

import documents.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import users.Session;
import users.Student;
import users.UserCard;

import java.util.ArrayList;
import java.util.Arrays;

public class modifyUserForm {
    private Stage stage;
    private Scene scene;
    private Session session;

    ArrayList<UserCard> users = new ArrayList<>();
    private int openUserCardID = -1;

    @FXML
    private GridPane userInfoPane;

    @FXML
    private ListView<UserCard> userListView;

    @FXML
    private Button saveBtn;
    @FXML
    private Button deleteUserBtn;

    @FXML
    private Label labelName;
    @FXML
    private Label labelSurname;
    @FXML
    private Label labelUserType;
    @FXML
    private Label labelPhoneNumber;
    @FXML
    private Label labelAddress;
    /*@FXML
    private Label labelCheckedOutCopies;
    @FXML
    private Label labelRequestedDocs;*/

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField userTypeTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private TextField addressTextField;
   /* @FXML
    private TextField checkedOutCopiesTextField;
    @FXML
    private TextField requestedDocsTextField;*/


    /**
     * Initialization and run new scene on the primary stage
     */
    void startForm(Stage primaryStage, Session currentSession) throws Exception {
        users = Storage.getUsers();
        this.session = currentSession;
        this.stage = primaryStage;
        sceneInitialization();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initialization scene and scene's elements
     */
    private void sceneInitialization() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modifyUserForm.fxml"));
        loader.setController(this);
        GridPane root = loader.load();
        this.scene = new Scene(root, 700, 700);

        deleteUserBtn = (Button) scene.lookup("#deleteUserBtn");
        saveBtn = (Button) scene.lookup("#saveBtn");

        labelName = (Label) scene.lookup("#labelName");
        labelSurname = (Label) scene.lookup("#labelSurname");
        labelUserType = (Label) scene.lookup("#labelUserType");
        labelPhoneNumber = (Label) scene.lookup("#labelPhoneNumber");
        labelAddress = (Label) scene.lookup("#labelAddress");
        /*labelCheckedOutCopies = (Label) scene.lookup("#labelCheckedOutCopies");
        labelRequestedDocs = (Label) scene.lookup("#labelRequestedDocs");*/

        nameTextField = (TextField) scene.lookup("#nameField");
        surnameTextField = (TextField) scene.lookup("#surnameField");
        userTypeTextField = (TextField) scene.lookup("#userTypeField");
        phoneNumberTextField = (TextField) scene.lookup("#phoneNumberField");
        addressTextField = (TextField) scene.lookup("#addressField");
        /*checkedOutCopiesTextField = (TextField) scene.lookup("#checkedOutCopiesField");
        requestedDocsTextField = (TextField) scene.lookup("#requestedDocsField");*/

       userListView.setItems(FXCollections.observableArrayList(users));
        userListView.setCellFactory(new Callback<ListView<UserCard>, ListCell<UserCard>>() {
            public ListCell<UserCard> call(ListView<UserCard> userListView) {
                return new ListCell<UserCard>() {
                    @Override
                    protected void updateItem(UserCard userCard, boolean flag) {
                        super.updateItem(userCard, flag);
                        if (userCard != null) {
                            setText(userCard.name);
                        }
                    }
                };
            }
        });

    }

    /**
     * Select element of Document List View Event
     */
    @FXML
    public void selectUser() {
        if (userListView.getSelectionModel().getSelectedIndex() > -1) {
            if (openUserCardID == -1) {
                userInfoPane.setVisible(true);//If no user card was opened
            }
            //Set user info
            UserCard chosenUser = selectUser(userListView.getSelectionModel().getSelectedIndex());


        }
    }

    public UserCard selectUser(int id) {
        openUserCardID = id;
        return users.get(openUserCardID);
    }


    @FXML
    public void save() {
        //TODO add connection with database


        if (!nameTextField.getText().isEmpty()) {
            UserCard currentUser = users.get(openUserCardID);
            currentUser.name = nameTextField.getText();
        }
        if (!surnameTextField.getText().isEmpty()) {
            UserCard currentUser = users.get(openUserCardID);
            currentUser.surname = surnameTextField.getText();
        }
        if (!addressTextField.getText().isEmpty()) {
            UserCard currentUser = users.get(openUserCardID);
            currentUser.address = addressTextField.getText();

        }

        // TODO UserType setting

        if (!phoneNumberTextField.getText().isEmpty()) {
            UserCard currentUser = users.get(openUserCardID);
            currentUser.phoneNumb = phoneNumberTextField.getText();

        }

    }

    @FXML
    public void deleteFile() {
        //TODO delete file
    }
}
