//Filip Gajda r00187488

package View;

import Controller.Controller;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.time.LocalDate;

public class RecordCloseContactTab extends Tab {
    public RecordCloseContactTab(Controller controller){

        Label contactLabel = new Label("Select Contact:");
        Label closeContactLabel = new Label("Select Close Contact:");
        Label contactDateLabel = new Label("Date of Contact:");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(10);

        ListView<String> contacts = new ListView<>();
        contacts.setMaxSize(200, 350);

        ListView<String> closeContacts = new ListView<>();
        closeContacts.setMaxSize(200, 350);

        DatePicker contactDate = new DatePicker(); //create a day picker
        contactDate.setEditable(false); //set it so that the user is unable to type
        contactDate.setValue(LocalDate.now()); // set todays date
        contactDate.setDayCellFactory(datePicker -> new DateCell(){ // prevent the user form choosing a date in the future
            public void updateItem(LocalDate date, boolean empty){ //prevent the user form picking a date in the future
                super.updateItem(date, empty);
                if (date.isAfter(LocalDate.now())){
                    setDisable(true); //prevent choice
                    setStyle("-fx-background-color: #ffc0cb;"); //set color of restricted days red
                }
            }
        });

        Button confirmContactButton = new Button("Confirm Close Contact");

        //Hbox
        HBox confirmContactHbox = new HBox();//Hbox to move button to the center
        confirmContactHbox.getChildren().add(confirmContactButton);
        confirmContactHbox.setAlignment(Pos.CENTER);

        //Vbox
        VBox dateOfContactVbox = new VBox();
        dateOfContactVbox.getChildren().addAll(contactDateLabel,contactDate);
        dateOfContactVbox.setAlignment(Pos.CENTER);



        Text infoMessage = new Text();


        gridPane.add(contactLabel,0,0);
        gridPane.add(contacts,0,1);
        gridPane.add(closeContactLabel,1,0);
        gridPane.add(closeContacts,1,1);
        gridPane.add(dateOfContactVbox,0,2,2,1);
        gridPane.add(confirmContactHbox,0,3,2,1);
        gridPane.add(infoMessage,0,4,2,1);

        confirmContactButton.setOnAction(e ->{ //create the contact when the button is pressed
          controller.createCloseContact(contacts,closeContacts,contactDate, infoMessage);
        });

        setOnSelectionChanged(e -> { //update the ListViews when tab is selected
            contacts.getItems().clear();
            closeContacts.getItems().clear();
            controller.populateListView(contacts);
            controller.populateListView(closeContacts);
        });

        setContent(gridPane);
        setClosable(false);
        setText("Record Close Contact");

    }
}




