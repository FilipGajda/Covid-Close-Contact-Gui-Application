//Filip Gajda r00187488

package Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;


/**
 * This is a Close Contact Class it is used to create a close contact even between 2 people
 */
@Entity
public class CloseContact implements Serializable {
    @Id
    @Column(name = "id", insertable = false )
    private  int ID;
    @Column(name = "date")
    private String date;
    @Column(name =  "person1")
    private int person1;
    @Column(name = "person2")
    private int person2;

    /**
     * Constructor for the close contact
     * @param person1 The id of the first person
     * @param person2 The id of the close Contact
     * @param date The date when the close contact occurred
     */
    public CloseContact( int person1, int person2, String date) {
        this.date = date;
        this.person1 = person1;
        this.person2 = person2;
    }

    /**
     * default constructor for the close contact
     */
    public CloseContact(){}

    /**
     * getter for Id
     * @return id of the close contact event
     */
    public int getID() {
        return ID;
    }

    /**
     * setter for Id
     * @param ID sets the Id to the one that was provided
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * getter for the date
     * @return the date if the close contact even
     */
    public String getDate() {
        return date;
    }

    /**
     * setter for the date
     * @param date sets the date to the one that was provided
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * getter for the id of first person
     * @return the id of the person
     */
    public int getPerson1() {
        return person1;
    }

    /**
     * setter for the id of the first person
     * @param person1 sets the id of the first person
     */
    public void setPerson1(int person1) {
        this.person1 = person1;
    }

    /**
     * getter for the id of second person (Close contact)
     * @return the id if the second person (close contact)
     */
    public int getPerson2() {
        return person2;
    }

    /**
     * setter for the id of second person (Close contact)
     * @param person2 sets the id if the second person (close contact)
     */
    public void setPerson2(int person2) {
        this.person2 = person2;
    }

    /**
     * The toString method to display the close contact event
     * @return a string with the close contact info
     */
    @Override
    public String toString() {
        return "CloseContact{" +
                "date=" + date +
                ", person1=" + person1 +
                ", person2=" + person2 +
                '}';
    }
}
