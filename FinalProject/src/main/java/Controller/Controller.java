//Filip Gajda r00187488

package Controller;

import Model.CloseContact;
import Model.Person;
import Model.ContactsList;

import ExitDialogBox.ExitConfirmation;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Controller {
    public Controller(){
    }

    EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );
    EntityManager entitymanager = emfactory.createEntityManager( );

    public void addPerson(TextField fName, TextField mName, TextField lName, TextField phoneNo, TextField email, Text message) {
        if(fName.getText().isEmpty() || lName.getText().isEmpty() || phoneNo.getText().isEmpty() || email.getText().isEmpty()){
            message.setFill(Color.FIREBRICK);
            message.setText("Insert failed, Please populate all required fields");
        }else if(!isNumeric(phoneNo.getText())){
            message.setFill(Color.FIREBRICK);
            message.setText("Insert failed, Phone number must be numeric!");
            phoneNo.clear();
        }else if(!email.getText().contains("@")){
            message.setFill(Color.FIREBRICK);
            message.setText("Insert failed, Enter valid email!");
            email.clear();
        }else {
            entitymanager.clear();
            entitymanager.getTransaction().begin();
            Person p = new Person(fName.getText(), mName.getText(), lName.getText(), phoneNo.getText(), email.getText());
            entitymanager.persist(p);
            entitymanager.getTransaction().commit();
            message.setFill(Color.GREEN);
            message.setText("Insert Successful, " + fName.getText() + " was inserted into database!");
            fName.clear();
            mName.clear();
            lName.clear();
            phoneNo.clear();
            email.clear();
        }
    }

    public void removeContact(ListView<String> people,Text message ){
        String personString = people.getSelectionModel().getSelectedItem(); //get the selected string from the contacts list
        if (personString != null) {
            int id = getId(personString);
            entitymanager.clear();
            entitymanager.getTransaction().begin();
            Person person = entitymanager.find(Person.class, id); //remove person from database
            entitymanager.remove(person);
            entitymanager.getTransaction().commit();
            List<CloseContact> closeContacts = entitymanager.createNativeQuery("SELECT * FROM CloseContact WHERE person1 = " + id + " OR person2 = " + id, CloseContact.class).getResultList();
            for (CloseContact cc : closeContacts) {  //remove all close contact events of the removed person
                entitymanager.clear();
                entitymanager.getTransaction().begin();
                cc = entitymanager.merge(cc);
                entitymanager.remove(cc);
                entitymanager.getTransaction().commit();
            }
            message.setFill(Color.GREEN);
            message.setText("Remove Successful, " +person.getFirstName() + " was removed successfully!");
        }else{
            message.setFill(Color.FIREBRICK);
            message.setText("Please select an option from the list above!");
        }
    }

    public void createCloseContact(ListView<String> contacts, ListView<String> closeContacts, DatePicker contactDate,Text message){
        if (contacts.getSelectionModel().getSelectedItem() == null || closeContacts.getSelectionModel().getSelectedItem() == null){
        message.setFill(Color.FIREBRICK);
        message.setText("Please select contacts from the lists above!");
        }else if(getId(contacts.getSelectionModel().getSelectedItem()) == getId(closeContacts.getSelectionModel().getSelectedItem())){
            message.setFill(Color.FIREBRICK);
            message.setText("A person cannot be its own close contacts");
        }else {
            entitymanager.clear();
            System.out.println("people with ids " + getId(contacts.getSelectionModel().getSelectedItem()) + " " + getId(closeContacts.getSelectionModel().getSelectedItem()) + " are close contacts");
            entitymanager.getTransaction().begin();
            CloseContact cc = new CloseContact(getId(contacts.getSelectionModel().getSelectedItem()), getId(closeContacts.getSelectionModel().getSelectedItem()), contactDate.getValue().toString());
            entitymanager.persist(cc);
            entitymanager.getTransaction().commit();
            message.setFill(Color.GREEN);
            message.setText("Close contact between " + getId(contacts.getSelectionModel().getSelectedItem()) + " and " +getId(closeContacts.getSelectionModel().getSelectedItem()) + " was created");
            contacts.getSelectionModel().clearSelection();
            closeContacts.getSelectionModel().clearSelection();
            contactDate.getEditor().clear();
            contactDate.setValue(LocalDate.now());
        }
    }


    public void closeApp()  {// terminate the application
        int save = ExitConfirmation.exitConfirmationAlertBox();

       switch (save){
           case 0:
               System.out.println("cancel");
               break;
           case 1:
               System.out.println("save and exit");
               try {
                   writeContacts();
               } catch (IOException e) {
                   e.printStackTrace();
               }
               entitymanager.close( );
               emfactory.close( );
               Platform.exit();
               break;
           case 2:
               System.out.println("exit without saving");
               entitymanager.close( );
               emfactory.close( );
               Platform.exit();
               break;

       }

    }


    public void writeContacts() throws IOException{//Save all Classes as serializable file
        ContactsList contactsList = new ContactsList();
        entitymanager.clear();
        List<Person> personList = entitymanager.createNativeQuery("SELECT * FROM Person", Person.class).getResultList();
        List<CloseContact> closeContactList = entitymanager.createNativeQuery("SELECT * FROM closeContact", CloseContact.class).getResultList();
        for (Person p: personList) { //add people to an arraylist
            System.out.println(p.toString());
            contactsList.addperson(p);
        }
        for (CloseContact cc: closeContactList) { //add closecontacts to an arraylist
            System.out.println(cc.toString());
            contactsList.addcloseContacts(cc);
        }
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("contactsSerialized.ser"));
        {
            os.writeObject(contactsList);
        }
        os.close();
        System.out.println("printed to file ");
    }

    public void readContacts() throws IOException { //Load Classes from serializable file
        ContactsList contactsList = new ContactsList();
        ObjectInputStream is = new ObjectInputStream(new FileInputStream("contactsSerialized.ser"));
        try {
            contactsList = (ContactsList) is.readObject();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        is.close();
        for (Person p:contactsList.getPersonList()) {
            System.out.println(p.toString());
            entitymanager.clear();
            entitymanager.getTransaction().begin();
            entitymanager.persist(p);
            entitymanager.getTransaction().commit();
        }
        for (CloseContact cc:contactsList.getCloseContacts()) {
            System.out.println(cc.toString());
            entitymanager.clear();
            entitymanager.getTransaction().begin();
            entitymanager.persist(cc);
            entitymanager.getTransaction().commit();
        }

    }



    private boolean isNumeric (String s){ // function to check if a number is numeric or not
        try{
            Integer.parseInt(s);
            return true; //returns true if the string is a number
        }catch (NumberFormatException e){
        return false; //returns false if the string is not a number
        }
    }

    private int getId(String listItem){ //get the id of the selected string.
        String[] splitString = listItem.split(" ");
        return Integer.parseInt(splitString[0]);
    }

    public void selectContact(ListView<String> people, TextArea textArea){ //select a contact using listview
        int id;
        if (people.getSelectionModel().getSelectedItem() != null) {
            id = getId(people.getSelectionModel().getSelectedItem());
            textArea.setText(returnPersonDetailsById(id));
        }else{
            textArea.setText("Please select an option from the list above!");
        }
        people.getSelectionModel().clearSelection();
    }

public String returnPersonDetailsById(int id){ //return the details of a person
    String output;
    entitymanager.clear();
    Person person = entitymanager.find(Person.class, id);
    output = person.toString() + " has the following contacts:\n\t";
    entitymanager.clear();
    List<CloseContact> result = entitymanager.createNativeQuery("SELECT * FROM closeContact WHERE person1 =" + id, CloseContact.class).getResultList();
    System.out.println(result);
    for (CloseContact cc: result) {
        entitymanager.clear();
        person = entitymanager.find(Person.class,cc.getPerson2());
        output += person.toString() + " on the " + cc.getDate() + "\n\t";
    }
    return output;
}

public void memoryException(){ //create a memory dump
        while (true) {
            ArrayList<Person> people = new ArrayList<Person>();
            people.add(new Person("Filip", "p", "Gajda", "123465789", "filip.gajda@mycit.ie"));
        }
}

public void displayCloseContactsSinceDate(DatePicker datePicker,TextField textField, TextArea textArea) {
    entitymanager.clear();
    String output = "";
    List<CloseContact> closeContacts = null;
    if(datePicker.getValue() == null){
        textArea.setText("Please select a date");
    }else {
        if (!textField.getText().isEmpty()) {
            int id = Integer.parseInt(textField.getText());
            closeContacts = entitymanager.createNativeQuery("SELECT * FROM closeContact WHERE date >= '" + datePicker.getValue().toString() + "' AND person1 = " + id + " OR person2 = " + id, CloseContact.class).getResultList();
        } else {
            closeContacts = entitymanager.createNativeQuery("SELECT * FROM closeContact WHERE date >= '" + datePicker.getValue().toString() + "'", CloseContact.class).getResultList();
        }
        System.out.println(closeContacts);
        for (CloseContact cc : closeContacts) {
            entitymanager.clear();
            Person p1 = entitymanager.find(Person.class, cc.getPerson1()); //get person with id of person1
            entitymanager.clear();
            Person p2 = entitymanager.find(Person.class, cc.getPerson2()); //get person with id of person1

            output += p1.toString() + " had close contact with:\n\t" + p2.toString() + "  on " + cc.getDate() + "\n";
        }
        textArea.setText(output);

        textField.clear();
        datePicker.getEditor().clear();
        datePicker.setValue(null);
    }
    }

    public void populateListView(ListView<String> listView){ //insert contacts into the list views
        entitymanager.clear();
        List<Person> result = entitymanager.createNativeQuery("SELECT * FROM Person", Person.class).getResultList();
        System.out.println(result);
        for (Person p: result) {
            listView.getItems().add(p.personFormattedForList());
        }
    }

    public void showAllPeople(ListView<String> listView){ //add all people to a listview
        entitymanager.clear();
        List<Person> result = entitymanager.createNativeQuery("SELECT * FROM Person", Person.class).getResultList();
        System.out.println(result);
        for (Person p: result) {
            listView.getItems().add(p.toString());
        }
       }

       public void displayCloseContacts(TextArea textArea){ //display all contacts
        String output = "";
           entitymanager.clear();
           List<CloseContact> result = entitymanager.createNativeQuery("SELECT * FROM closeContact", CloseContact.class).getResultList();
           System.out.println(result);
           for (CloseContact cc: result) {
               entitymanager.clear();
               Person p1 = entitymanager.find(Person.class,cc.getPerson1()); //get person with id of person1
               entitymanager.clear();
               Person p2 = entitymanager.find(Person.class,cc.getPerson2()); //get person with id of person1

               output +=  p1.toString() + " had close contact with:\n\t" + p2.toString() + "  on " + cc.getDate() + "\n";
           }
        textArea.setText(output);
       }

}

