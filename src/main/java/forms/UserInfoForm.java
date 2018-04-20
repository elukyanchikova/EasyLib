package forms;

import Core.ActionManager;
import documents.Copy;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import storage.DatabaseManager;
import users.Session;
import users.UserCard;

public class UserInfoForm {
    private Stage stage;
    private Scene scene;
    private Session session;
    private DatabaseManager databaseManager;
    private ActionManager actionManager;

    private int openUserCardID = -1;

    @FXML
    private GridPane userInfoPane;

    @FXML private ListView<UserCard> userListView;

    @FXML private Label nameLbl;
    @FXML private Label addressLbl;
    @FXML private Label phoneNumLbl;
    @FXML private Label idLbl;
    @FXML private Label typeLbl;
    @FXML private Label checkedOutLbl;
    @FXML private Label fineLbl;
    /**
     * Initialization and run new scene on the primary stage
     * @param primaryStage != null;
     * @param currentSession != null
     */
    public void startForm(Stage primaryStage, Session currentSession, DatabaseManager databaseManager, ActionManager actionManager) throws Exception{
        this.session = currentSession;
        this.stage = primaryStage;
        this.databaseManager = databaseManager;
        this.actionManager = actionManager;
        sceneInitialization();
        stage.setScene(scene);
        stage.show();
    }

    //TODO refactor after adding storage
    /**
     * Initialization scene and scene's elements
     * All elements will be initialized
     */
    private void sceneInitialization() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFiles/UserInfoForm.fxml"));
        loader.setController(this);
        GridPane root = loader.load();
        this.scene = new Scene(root,1000,700);
        elementsInitialization();

        userListView.setItems(FXCollections.observableArrayList(databaseManager.getAllUsers()));
        userListView.setCellFactory(new Callback<ListView<UserCard>, ListCell<UserCard>>() {
            public ListCell<UserCard> call(ListView<UserCard> userListView) {
                return new ListCell<UserCard>() {
                    @Override
                    protected void updateItem(UserCard user, boolean flag) {
                        super.updateItem(user, flag);
                        if (user != null) {
                            setText(user.name + " " +  user.surname);
                        }
                    }
                };
            }
        });

    }

    private void elementsInitialization(){
        userListView = (ListView<UserCard>) scene.lookup("#userListView");
        userInfoPane = (GridPane) scene.lookup("#userInfoPane");
        nameLbl = (Label) scene.lookup("#nameLbl");
        addressLbl = (Label) scene.lookup("#addressLbl");
        typeLbl = (Label) scene.lookup("#typeLbl");
        phoneNumLbl = (Label) scene.lookup("#phoneNumLbl");
        idLbl = (Label) scene.lookup("#idLbl");
        checkedOutLbl = (Label) scene.lookup("#checkedOutLbl");
        fineLbl = (Label) scene.lookup("#fineLbl");

    }

    public UserCard selectUserCard(int id){
        openUserCardID = id;
        return databaseManager.getUserCard(databaseManager.getUserCardsID()[openUserCardID]);
    }


    /**
     * Select element of Document List View Event
     */
    @FXML
    public void selectUserListView(){
        //get selected element

        if(userListView.getSelectionModel().getSelectedIndex() > -1) {
            //If no document was opened
            if(openUserCardID == -1){
                userInfoPane.setVisible(true);
            }

            //Set document info
            UserCard chosenUser = selectUserCard(userListView.getSelectionModel().getSelectedIndex());

            nameLbl.setText(chosenUser.name);
            addressLbl.setText(chosenUser.address);
            phoneNumLbl.setText(chosenUser.phoneNumb);
            idLbl.setText(Integer.toString(chosenUser.getId()));
            typeLbl.setText(chosenUser.userType.getClass().getName().replace("users.", ""));
            fineLbl.setText(Integer.toString(chosenUser.getFine(chosenUser, session,databaseManager)));

            StringBuilder stringBuilder = new StringBuilder();
            for(Copy c:chosenUser.checkedOutCopies){
                stringBuilder.append(databaseManager.getDocuments(c.getDocumentID()).title);
                stringBuilder.append("(" + c.getDueDate());
                stringBuilder.append("), ");
            }

        }
    }

    @FXML
    public void clickOnBackBtn() throws Exception {
        MainForm mainForm = new MainForm();
        mainForm.startForm(stage, session, databaseManager, actionManager);
    }


}
