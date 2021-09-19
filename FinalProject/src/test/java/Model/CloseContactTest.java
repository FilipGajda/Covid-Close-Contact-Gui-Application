//Filip Gajda r00187488
package Model;

import org.junit.jupiter.api.*;

class CloseContactTest {
 private CloseContact closeContact;
    @BeforeEach
    void setUp() {
       closeContact = new CloseContact(1,23,"2021-06-05");
    }

    @Test
    void testSetDate() {
       closeContact.setDate("2021-05-05");
       Assertions.assertEquals("2021-05-05", closeContact.getDate());
    }

   @Test
   void failTestGetDate(){
      Assertions.assertEquals("2021-05-05",closeContact.getDate());
   }

   @Test
    void testGetDate(){
       Assertions.assertEquals("2021-06-05",closeContact.getDate());
    }

    @Test
    void testToString(){
       Assertions.assertEquals("CloseContact{" +
               "date=" + "2021-06-05" +
               ", person1=" + "1" +
               ", person2=" + "23" +
               '}', closeContact.toString());
    }

}