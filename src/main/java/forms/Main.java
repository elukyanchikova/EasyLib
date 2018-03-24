package forms;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("EasyLib");

        AuthorizationForm authorizationForm = new AuthorizationForm();
        authorizationForm.startForm(primaryStage);
    }

}
