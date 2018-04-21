package forms;

import core.ActionManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import storage.DatabaseManager;
import users.Session;
import users.UserCard;
import users.userTypes.Librarian;
import users.userTypes.UserType;

public class ModifyUserForm {
    private Stage stage;
    private Scene scene;
    private Session session;
    private DatabaseManager databaseManager;
    private ActionManager actionManager;

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
    private Button backBtn;

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

   @FXML
   private CheckBox checkBoxPriv1;
    @FXML
    private CheckBox checkBoxPriv2;
    @FXML
    private CheckBox checkBoxPriv3;
    public UserCard chosenUserSuper;

    /**
     * Initialization and run new scene on the primary stage
     */
    void startForm(Stage primaryStage, Session currentSession, DatabaseManager databaseManager, ActionManager actionManager) throws Exception {
        this.session = currentSession;
        this.databaseManager = databaseManager;
        this.stage = primaryStage;
        this.actionManager = actionManager;
        sceneInitialization();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initialization scene and scene's elements
     */
    private void sceneInitialization() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFiles/modifyUserForm.fxml"));
        loader.setController(this);
        GridPane root = loader.load();
        this.scene = new Scene(root, 700, 700);

        deleteUserBtn = (Button) scene.lookup("#deleteUserBtn");
        saveBtn = (Button) scene.lookup("#saveBtn");
        backBtn = (Button) scene.lookup("#backBtn");

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
        checkBoxPriv1 = (CheckBox) scene.lookup("#checkBoxPriv1") ;
        checkBoxPriv2 = (CheckBox) scene.lookup("#checkBoxPriv2") ;
        checkBoxPriv3 = (CheckBox) scene.lookup("#checkBoxPriv3") ;


        userListView.setItems(FXCollections.observableArrayList(databaseManager.getAllUsers()));
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
     * Load special permission buttons' state
     */
    private void loadHighPermissionInterface(UserCard user) {
        boolean a =session.getUser().isHasEditingLibrarianPerm();
        String h =String.valueOf(user.userType);
                boolean b =String.valueOf(user.userType).contains("Librarian")  ;
                        boolean c = a&&b;
   /*     boolean b =(session.getUser().isHasEditingLibrarianPerm()
               &&  Librarian.class.isAssignableFrom(user.getClass()));*/
        checkBoxPriv1.setVisible(b);
        checkBoxPriv2.setVisible(b);
        checkBoxPriv3.setVisible(b);
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
            UserCard chosenUser = selectUser(userListView.getSelectionModel().getSelectedIndex());
            chosenUserSuper = chosenUser;
            loadHighPermissionInterface(chosenUser);
            nameTextField.setText(chosenUser.name);
            surnameTextField.setText(chosenUser.surname);
            addressTextField.setText(chosenUser.address);
            phoneNumberTextField.setText(chosenUser.phoneNumb);
            userTypeTextField.setText(chosenUser.userType.getClass().getName());
        }
    }

    /**
     * choose a user for starting new session
     * @param id - id of the UserCard
     * @return UserCard with the particular id
     */
    public UserCard selectUser(int id) {
        openUserCardID = id;
        return  databaseManager.getUserCard(databaseManager.getUserCardsID()[openUserCardID]);
    }


    /**
     * Click on button "Save" event
     * collecting information from textFields(if the textField is not empty) and rewriting the information about the particular doc in the database
     */
    @FXML
    public void save() {
        //TODO add connection with databaseManager


        if (!nameTextField.getText().isEmpty()) {
            UserCard currentUser = databaseManager.getUserCard(databaseManager.getUserCardsID()[openUserCardID]);
            currentUser.name = nameTextField.getText();
            databaseManager.saveUserCard(currentUser);

        }
        if (!surnameTextField.getText().isEmpty()) {
            UserCard currentUser = databaseManager.getUserCard(databaseManager.getUserCardsID()[openUserCardID]);
            currentUser.surname = surnameTextField.getText();
            databaseManager.saveUserCard(currentUser);

        }
        if (!addressTextField.getText().isEmpty()) {

            UserCard currentUser = databaseManager.getUserCard(databaseManager.getUserCardsID()[openUserCardID]);
            currentUser.address = addressTextField.getText();
            databaseManager.saveUserCard(currentUser);
        }


        if(!userTypeTextField.getText().isEmpty()){
            UserCard currentUser = databaseManager.getUserCard(databaseManager.getUserCardsID()[openUserCardID]);
            UserType u = UserType.userTypes.get(userTypeTextField.getText());
            if(u != null){
                currentUser.userType = u;
            }
            databaseManager.saveUserCard(currentUser);
        }


        if (!phoneNumberTextField.getText().isEmpty()) {
            UserCard currentUser = databaseManager.getUserCard(databaseManager.getUserCardsID()[openUserCardID]);
            currentUser.phoneNumb = phoneNumberTextField.getText();
            databaseManager.saveUserCard(currentUser);
        }

    }

    /**
     * Clock on button "Back" event
     * come back for the EditForm
     * @throws Exception
     */
    @FXML
    void back() throws Exception {
        EditForm mainForm = new EditForm();
        mainForm.startForm(stage, session, databaseManager, actionManager);
    }

    /**
     * Click on button "Delete" event
     * remove the UserCard from the database
     */
    @FXML
    public void deleteUser() {
        UserCard currentUser = databaseManager.getUserCard(databaseManager.getUserCardsID()[openUserCardID]);
        databaseManager.removeUserCard(currentUser);
    }

    @FXML
    public void setPrivilege1(){
        UserCard us = databaseManager.getUserCard(databaseManager.getUserCardsID()[openUserCardID]);
           if ( Librarian.class.isAssignableFrom(databaseManager.getUserCard(databaseManager.getUserCardsID()[openUserCardID]).getClass())){
               ((Librarian)us.userType).setPriv1();
           }
        }

    @FXML
    public void setPrivilege2(){
        UserCard us = databaseManager.getUserCard(databaseManager.getUserCardsID()[openUserCardID]);
        if ( Librarian.class.isAssignableFrom(databaseManager.getUserCard(databaseManager.getUserCardsID()[openUserCardID]).getClass())){
            ((Librarian)us.userType).setPriv2();
        }
    }

    @FXML
    public void setPrivilege3(){
        UserCard us = databaseManager.getUserCard(databaseManager.getUserCardsID()[openUserCardID]);
        if ( Librarian.class.isAssignableFrom(databaseManager.getUserCard(databaseManager.getUserCardsID()[openUserCardID]).getClass())){
            ((Librarian)us.userType).setPriv3();
        }
    }
}
