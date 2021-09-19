//Filip Gajda r00187488

package View;

import Controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class FindCloseContactsTab extends Tab {
    public FindCloseContactsTab(Controller controller){

        Label peopleLabel = new Label("select a person from the list:");
        Label dateLabel1 = new Label("Show");
        Label dateLabel2 = new Label("since:");

        Button selectpersonButton = new Button("Select");
        Button showAllButton = new Button("Show All Contacts");
        Button selectDateButton = new Button("Select");

        TextArea closeContactsList = new TextArea();
        closeContactsList.setEditable(false);
        closeContactsList.setMaxHeight(200);

        ListView<String> peopleInTheSystem = new ListView<>();
        peopleInTheSystem.setMaxSize(400, 200);
        peopleInTheSystem.setEditable(false);


        TextField searchByIDInfo = new TextField();
        searchByIDInfo.setPromptText("search by id");

        DatePicker searchDate = new DatePicker(); //create a day picker
        searchDate.setEditable(false); //set it so that the user is unable to type
        searchDate.setDayCellFactory(datePicker -> new DateCell(){ // prevent the user form choosing a date in the future
            public void updateItem(LocalDate date, boolean empty){ //prevent the user form picking a date in the future
                super.updateItem(date, empty);
                if (date.isAfter(LocalDate.now())){
                    setDisable(true); //prevent choice
                    setStyle("-fx-background-color: #ffc0cb;"); //set color of restricted days red
                }
            }
        });

        HBox contactSelectionButtonsHbox = new HBox();
        contactSelectionButtonsHbox.setSpacing(10);
        contactSelectionButtonsHbox.setAlignment(Pos.CENTER);
        contactSelectionButtonsHbox.getChildren().addAll(selectpersonButton, showAllButton);

        HBox dateSelectionHbox = new HBox();
        dateSelectionHbox.setSpacing(10);
        dateSelectionHbox.setAlignment(Pos.CENTER);
        dateSelectionHbox.getChildren().addAll(dateLabel1,searchByIDInfo,dateLabel2,searchDate,selectDateButton);


        VBox contactVbox = new VBox();
        contactVbox.getChildren().addAll(peopleLabel,peopleInTheSystem, contactSelectionButtonsHbox);
        contactVbox.setSpacing(10);
        contactVbox.setAlignment(Pos.CENTER);


        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20,0,0,0));
        gridPane.setHgap(20);
        gridPane.setVgap(10);

        gridPane.add(contactVbox,0,0);
        gridPane.add(dateSelectionHbox,0,1);
        gridPane.add(closeContactsList,0,2);

        selectpersonButton.setOnAction(e ->{ //find and display required contacts
           controller.selectContact(peopleInTheSystem,closeContactsList);
        });

        showAllButton.setOnAction(e ->{
           controller.displayCloseContacts(closeContactsList);
           // contacts.getSelectionModel().clearSelection();//clear the selection to allow for another search
        });

        selectDateButton.setOnAction(e -> {
            controller.displayCloseContactsSinceDate(searchDate,searchByIDInfo, closeContactsList);
        });

        setOnSelectionChanged(e -> { //when tab is changed
            peopleInTheSystem.getItems().clear();
            controller.populateListView(peopleInTheSystem); //update the ListViews when tab is selected
            controller.displayCloseContacts(closeContactsList); //show the recorded contacts
        });

        setText("Find Close Contacts");
        setClosable(false);
        setContent(gridPane);
    }
}
