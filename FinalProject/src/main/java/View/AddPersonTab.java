//Filip Gajda r00187488

package View;

import Controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class AddPersonTab extends Tab {
    public AddPersonTab(Controller controller) {
        setText("Add Person"); //setting text for tab
        setClosable(false); //disable the x button on the tab

        //labels
        Label contactsLabel = new Label("----------Person Details----------");
        Label fNameLabel = new Label("Enter First Name");
        Label mNameLabel = new Label("Enter Middle Name");
        Label lNameLabel = new Label("Enter Last Name");
        Label phoneNoLabel = new Label("Enter Phone Number");
        Label emailLabel = new Label("Enter Email address");
        Label peopleInSystemLabel = new Label("People in system");


        //textFields
        TextField fNameInfo = new TextField();
        fNameInfo.setPromptText("Enter first name");

        TextField mNameInfo = new TextField();
        mNameInfo.setPromptText("Enter middle Name (optional)");

        TextField lNameInfo = new TextField();
        lNameInfo.setPromptText("Enter last name");

        TextField phoneNoInfo = new TextField();
        phoneNoInfo.setPromptText("Enter phone number");

        TextField emailInfo = new TextField();
        emailInfo.setPromptText("Enter email address");

        //ListView
        ListView<String> peopleInSystemList = new ListView<>();
        peopleInSystemList.setMaxSize(400, 150);

        //text
        Text addMessage = new Text();
        Text removeMessage = new Text();

        //buttons
        Button addButton = new Button("Add");
        Button removeButton = new Button("Remove");

        //layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 0, 25));

        //Adding elements to grid
        grid.add(contactsLabel, 0, 0, 2, 1);
        grid.add(fNameLabel, 0, 1);
        grid.add(fNameInfo, 1, 1);
        grid.add(mNameLabel, 0, 2);
        grid.add(mNameInfo, 1, 2);
        grid.add(lNameLabel, 0, 3);
        grid.add(lNameInfo, 1, 3);
        grid.add(phoneNoLabel, 0, 4);
        grid.add(phoneNoInfo, 1, 4);
        grid.add(emailLabel, 0, 5);
        grid.add(emailInfo, 1, 5);
        grid.add(addButton, 0, 6);
        grid.add(addMessage, 1, 6);
        grid.add(peopleInSystemLabel,0,8,2,1);
        grid.add(peopleInSystemList, 0, 9, 2, 1);
        grid.add(removeButton, 0, 10);
        grid.add(removeMessage, 1, 10);

        //Functionality
        addButton.setOnAction(e -> { //add contacts
            System.out.println("Add contact");
            controller.addPerson(fNameInfo,mNameInfo, lNameInfo, phoneNoInfo, emailInfo, addMessage);
            peopleInSystemList.getItems().clear();
            controller.showAllPeople(peopleInSystemList);
        });

        removeButton.setOnAction(e -> {
            System.out.println("remove contact");
            controller.removeContact(peopleInSystemList, removeMessage);
            peopleInSystemList.getItems().clear();
            controller.showAllPeople(peopleInSystemList);
        }); //remove a contact


        setOnSelectionChanged(e -> { //update the ListViews when tab is selected
            peopleInSystemList.getItems().clear();
            controller.showAllPeople(peopleInSystemList);
        });

        setContent(grid); // create the view
    }

}