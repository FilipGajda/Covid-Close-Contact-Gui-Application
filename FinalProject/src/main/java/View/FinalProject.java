//Filip Gajda r00187488

package View;


import Controller.Controller;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FinalProject extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Controller controller = new Controller();
        BorderPane borderPane = new BorderPane();

        Button exitButton = new Button("Exit");

        HBox exitHBox = new HBox();

        exitHBox.getChildren().add(exitButton);
        exitHBox.setAlignment(Pos.CENTER_RIGHT);
        exitHBox.setPadding(new Insets(0,20,20,0));

        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(new AddPersonTab(controller));
        tabPane.getTabs().add(new RecordCloseContactTab(controller));
        tabPane.getTabs().add(new FindCloseContactsTab(controller));
        tabPane.getTabs().add(new AdminTab(controller));


        borderPane.setTop(tabPane);
        borderPane.setBottom(exitHBox);

        exitButton.setOnAction(e -> {
            System.out.println("exit button");
            controller.closeApp();
        }); //exit the application

        stage.setOnCloseRequest(e -> {
            System.out.println("x button");
            e.consume();
            controller.closeApp();
        });


        stage.setTitle("Covid Close Contacts");
        stage.setScene(new Scene(borderPane, 500, 600));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }



}
