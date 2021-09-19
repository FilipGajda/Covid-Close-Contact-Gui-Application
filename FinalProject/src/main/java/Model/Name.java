//Filip Gajda r00187488

package Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class Name implements Serializable {
    @Column(name = "fName")
    String firstName;
    @Column (name = "mName")
    String middleName;
    @Column(name = "lName")
    String lastName;

    public Name(String firstName, String middleName, String lastName) {

        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public Name(){}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Name{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastname='" + lastName + '\'' +
                '}';
    }
}
