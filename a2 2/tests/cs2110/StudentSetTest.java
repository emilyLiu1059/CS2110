//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cs2110;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StudentSetTest {
    @Test
    void testConstructorAndSize() {
        StudentSet students = new StudentSet();
        Assertions.assertEquals(0, students.size());
        Student example = new Student("Emily", "Liu");
        students.add(example);
        Assertions.assertEquals(1, students.size());
        Assertions.assertTrue(students.contains(example));
        students.remove(example);
        Assertions.assertEquals(0, students.size());
        Assertions.assertEquals("{}", students.toString());

        for(int i = 1; i < 32; ++i) {
            Student a = new Student("Emily" + i, "Liu" + i);
            students.add(a);
            Assertions.assertEquals(i, students.size());
        }

    }
}
