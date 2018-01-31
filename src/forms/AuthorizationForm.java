package forms;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.swing.text.html.ListView;

public class AuthorizationForm extends Application{

    Button button;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("EasyLib");
        button = new Button("Sign in as Guest");
        StackPane layout = new StackPane();
        layout.getChildren().add(button);
        Scene scene = new Scene(layout,300,300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
