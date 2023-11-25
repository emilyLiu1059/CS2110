//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cs2110;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CourseTest {
    @Test
    void testConstructor() {
        Course test = new Course("Computer Science", 3, "Muhlberger", "Bailey Hall", 10, 10, 75);
        Assertions.assertEquals("Professor Muhlberger", test.instructor());
        Assertions.assertEquals("10:10 AM", test.formatStartTime());
        Course potentialOverlap = new Course("Chemistry", 4, "Musser", "Baker Lab", 11, 25, 50);
        Course potentialOverlap2 = new Course("Chemistry", 4, "Musser", "Baker Lab", 11, 24, 50);
        Assertions.assertFalse(test.overlaps(potentialOverlap));
        Assertions.assertTrue(test.overlaps(potentialOverlap2));
    }

    @Test
    void testHasStudent() {
        Course test = new Course("Computer Science", 3, "Muhlberger", "Bailey Hall", 10, 10, 75);
        Student example = null;

        for(int i = 0; i < 18; ++i) {
            Student a;
            if (i == 0) {
                a = new Student("Elaine", "Wu");
                example = a;
            } else {
                a = new Student("Elaine" + i, "Wu" + i);
            }

            test.enrollStudent(a);
        }

        Assertions.assertTrue(test.hasStudent(example));
        Student example2 = new Student("Joe", "Bob");
        Assertions.assertFalse(test.hasStudent(example2));
    }

    @Test
    void testEnrollStudent() {
        Course test = new Course("Computer Science", 3, "Muhlberger", "Bailey Hall", 10, 10, 75);
        Student a = new Student("Emily", "Liu");
        Assertions.assertTrue(test.enrollStudent(a));
        Assertions.assertFalse(test.enrollStudent(a));
    }

    @Test
    void testDropStudent() {
        Course test = new Course("Computer Science", 3, "Muhlberger", "Bailey Hall", 10, 10, 75);
        Student a = new Student("Emily", "Liu");
        test.enrollStudent(a);
        Assertions.assertTrue(test.dropStudent(a));
        Assertions.assertFalse(test.dropStudent(a));
    }
}
