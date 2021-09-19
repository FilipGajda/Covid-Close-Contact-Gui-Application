//Filip Gajda r00187488

package Model;


import java.io.Serializable;
import java.util.ArrayList;

public class ContactsList implements Serializable {
   private ArrayList<Person> personList;
   private ArrayList<CloseContact> closeContacts;


    public ContactsList() {
        personList = new ArrayList<>();
        closeContacts = new ArrayList<>();
    }

    public void addperson(Person person){personList.add(person);}
    public void addcloseContacts(CloseContact closeContact){closeContacts.add(closeContact);}

    public ArrayList<Person> getPersonList() {
        return personList;
    }

    public ArrayList<CloseContact> getCloseContacts() {
        return closeContacts;
    }
}
