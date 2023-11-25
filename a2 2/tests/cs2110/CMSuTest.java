//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cs2110;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CMSuTest {

    @Test
    void testHasConflict() {
        CMSu manage = new CMSu();
        Student student1 = new Student("Emily", "Liu");
        Student student2 = new Student("Jane", "Wu");
        Student student3 = new Student("John", "Doe");
        manage.addStudent(student1);
        manage.addStudent(student2);
        manage.addStudent(student3);
        Course first = new Course("Computer Science", 3, "Muhlberger", "Bailey Hall", 10, 10, 75);
        Course second = new Course("Math", 4, "Townsend", "Olin Hall", 11, 25, 50);
        Course third = new Course("Chem", 4, "Musser", "Baker Lab", 11, 24, 50);
        Course fourth = new Course("Intro to ORIE", 3, "Schalekamp", "Thurston Hall", 12, 13, 50);
        manage.addCourse(first);
        manage.addCourse(second);
        manage.addCourse(third);
        manage.addCourse(fourth);
        first.enrollStudent(student1);
        second.enrollStudent(student1);
        third.enrollStudent(student2);
        fourth.enrollStudent(student2);
        first.enrollStudent(student3);
        second.enrollStudent(student3);
        fourth.enrollStudent(student3);
        Assertions.assertFalse(manage.hasConflict(student1));
        Assertions.assertTrue(manage.hasConflict(student2));
        Assertions.assertTrue(manage.hasConflict(student3));
    }

    @Test
    void testAuditCredit() {
        CMSu manage = new CMSu();
        Student student1 = new Student("Emily", "Liu");
        Student student2 = new Student("Jane", "Wu");
        Student student3 = new Student("John", "Doe");
        manage.addStudent(student1);
        manage.addStudent(student2);
        manage.addStudent(student3);
        Course first = new Course("Computer Science", 3, "Muhlberger", "Bailey Hall", 10, 10, 75);
        Course second = new Course("Math", 4, "Townsend", "Olin Hall", 11, 25, 50);
        Course third = new Course("Chem", 4, "Musser", "Baker Lab", 11, 24, 50);
        Course fourth = new Course("Intro to ORIE", 3, "Schalekamp", "Thurston Hall", 12, 13, 50);
        manage.addCourse(first);
        manage.addCourse(second);
        manage.addCourse(third);
        manage.addCourse(fourth);
        first.enrollStudent(student1);
        second.enrollStudent(student1);
        third.enrollStudent(student2);
        fourth.enrollStudent(student2);
        first.enrollStudent(student3);
        second.enrollStudent(student3);
        fourth.enrollStudent(student3);
        Assertions.assertTrue(manage.auditCredits(1).contains(student1) && manage.auditCredits(1).contains(student2) && manage.auditCredits(1).contains(student3));
        Assertions.assertFalse(manage.auditCredits(8).contains(student1) && manage.auditCredits(8).contains(student2));
    }

    @Test
    void testCheckCreditConsistency() {
        CMSu manage = new CMSu();
        Student student1 = new Student("Emily", "Liu");
        manage.addStudent(student1);
        Course first = new Course("Computer Science", 3, "Muhlberger", "Bailey Hall", 10, 10, 75);
        Course second = new Course("Math", 4, "Townsend", "Olin Hall", 11, 25, 50);
        Course third = new Course("Chem", 4, "Musser", "Baker Lab", 11, 24, 50);
        Course fourth = new Course("Intro to ORIE", 3, "Schalekamp", "Thurston Hall", 12, 13, 50);
        manage.addCourse(first);
        manage.addCourse(second);
        manage.addCourse(third);
        manage.addCourse(fourth);
        first.enrollStudent(student1);
        second.enrollStudent(student1);
        Assertions.assertTrue(manage.checkCreditConsistency());
        first.dropStudent(student1);
        third.enrollStudent(student1);
        student1.adjustCredits(-3);
        fourth.enrollStudent(student1);
        Assertions.assertFalse(manage.checkCreditConsistency());
    }
}
