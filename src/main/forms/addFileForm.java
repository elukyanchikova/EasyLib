package forms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import users.Session;

public class addFileForm {
    private Stage stage;
    private Scene scene;
    private Session session;

    @FXML private TextField titleTextField;
    @FXML private TextField authorsTextField;
    @FXML private TextField keywordsTextField;
    @FXML private TextField docTypeTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField numbOfCopiesTextField;

    /**
     * Initialization and run new scene on the primary stage
     */
    void startForm(Stage primaryStage, Session currentSession) throws Exception{
        this.stage = primaryStage;
        this.session = currentSession;
        sceneInitialization();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initialization scene and scene's elements
     */
    private void sceneInitialization() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addFileForm.fxml"));
        loader.setController(this);
        GridPane root = loader.load();
        this.scene = new Scene(root,700,700);

        titleTextField = (TextField) scene.lookup("#titleField");
        authorsTextField = (TextField) scene.lookup("#authorsTextField");
        docTypeTextField = (TextField) scene.lookup("#docTypeField");
        keywordsTextField = (TextField) scene.lookup("#keywordsTextField");
        priceTextField = (TextField) scene.lookup("#priceTextField");
        numbOfCopiesTextField = (TextField) scene.lookup("#numbOfCopiesField");


    }


}
