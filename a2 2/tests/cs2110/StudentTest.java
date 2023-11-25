//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cs2110;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StudentTest {
    @Test
    void testConstructorAndObservers() {
        Student s = new Student("first", "last");
        Assertions.assertEquals("first", s.firstName());
        Assertions.assertEquals("last", s.lastName());
        Assertions.assertEquals(0, s.credits());
        s = new Student("f", "l");
        Assertions.assertEquals("f", s.firstName());
        Assertions.assertEquals("l", s.lastName());
    }

    @Test
    void testFullName() {
        Student example = new Student("John", "Doe");
        Assertions.assertEquals("John Doe", example.toString());
    }

    @Test
    void testAdjustCredits() {
        Student s = new Student("first", "last");
        s.adjustCredits(3);
        Assertions.assertEquals(3, s.credits());
        s.adjustCredits(4);
        Assertions.assertEquals(7, s.credits());
        s.adjustCredits(-3);
        Assertions.assertEquals(4, s.credits());
        s.adjustCredits(-4);
        Assertions.assertEquals(0, s.credits());
    }
}
