//Filip Gajda r00187488

package Model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

@Entity
public class Person extends Name implements Serializable {
    @Id
    @Column(name = "id", insertable = false)
    private int id;
    @Column(name = "phoneNo")
    private String phoneNo;
    @Column(name = "email")
    private String email;


    public Person(String fName, String mName, String lName, String phoneNo, String email){ //constructor
        super(fName,mName,lName);
        this.id = id;
        this.phoneNo = phoneNo;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Person(){
        super();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String personFormattedForList(){
        return id + " " + firstName + " " + middleName + " " + lastName;
    }

    @Override
    public String toString(){
        return id + " " + firstName + " " + middleName + " " + lastName + " " + phoneNo + " " + email;
    }

}
