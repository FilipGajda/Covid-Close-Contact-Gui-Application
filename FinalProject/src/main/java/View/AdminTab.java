//Filip Gajda r00187488

package View;

import Controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;

public class AdminTab extends Tab{

        public AdminTab(Controller controller) {
            setText("Admin"); //setting text for tab
            setClosable(false); //disable the x button on the tab

            //labels
            Label adminLabel = new Label("----------Administration----------");
            Label saveLabel = new Label("Save to file:");
            Label loadLabel = new Label("Load to database:");
            Label memExceptionLabel = new Label("Memory exception:");

            //buttons
            Button saveButton = new Button("Save");
            Button loadButton = new Button("Load");
            Button memExceptionButton = new Button("Run");

            //layout
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 0, 25));

            //text
            Text message = new Text();


            //Adding elements to grid
            grid.add(adminLabel, 0, 0, 2, 1);
            grid.add(saveLabel, 0, 1);
            grid.add(saveButton, 1, 1);
            grid.add(loadLabel, 0, 2);
            grid.add(loadButton, 1, 2);
            grid.add(memExceptionLabel, 0, 3);
            grid.add(memExceptionButton, 1, 3);
            grid.add(message,0,4);

            //Functionality
        saveButton.setOnAction(e -> { //save to file
            System.out.println("save contacts");
            try {
                controller.writeContacts();
                message.setFill(Color.GREEN);
                message.setText("Save Successful");
            } catch (IOException ioException) {
                ioException.printStackTrace();
                message.setFill(Color.FIREBRICK);
                message.setText("Save Failed");
            }
        });

        loadButton.setOnAction(e ->{ //load from file
            try {
                controller.readContacts();
                message.setFill(Color.GREEN);
                message.setText("Load Successful");
            } catch (IOException ioException) {
                ioException.printStackTrace();
                message.setFill(Color.FIREBRICK);
                message.setText("Load Failed");
            }
        });

        memExceptionButton.setOnAction(e ->{
            message.setFill(Color.FIREBRICK);
            message.setText("Creating memory exception");
            controller.memoryException();
        });

            setContent(grid); // create the view
        }

    }

