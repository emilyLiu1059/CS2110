package cs2110;

import java.util.Objects;

/**
 * A student tracked by the CMSμ course management system.
 */
public class Student {
    String firstName = ""; //First name of this Student
    String lastName = ""; //Last name of this Student
    int credits; //Number of credits student is currently enrolled in. May not be negative

    /**
     * Assert that this object satisfies its class invariants.
     */
    private void assertInv() {
        assert firstName != null;
        assert lastName != null;
        assert credits >= 0;
    }

    /**
     * Create a new Student with first name `firstName` and last name `lastName` who is not enrolled
     * for any credits.  Requires firstName and lastName are not empty.
     */
    public Student(String firstName, String lastName) {
        assert firstName != null && lastName != null;
        this.firstName = firstName;
        this.lastName = lastName;
        assertInv();
    }

    /**
     * Return the first name of this Student.  Will not be empty.
     */
    public String firstName() {
        return this.firstName;
    }

    /**
     * Return the last name of this Student.  Will not be empty.
     */
    public String lastName() {
        return this.lastName;
    }

    /**
     * Return the full name of this student, formed by joining their first and last names separated
     * by a space.
     */
    public String fullName() {
        // Observe that, by invoking methods instead of referencing this fields, this method was
        // implemented without knowing how you will name your fields.
        return firstName() + " " + lastName();
    }

    /**
     * Return the number of credits this student is currently enrolled in.  Will not be negative.
     */
    public int credits() {
        return this.credits;
    }

    /**
     * Change the number of credits this student is enrolled in by `deltaCredits`. For example, if
     * this student were enrolled in 12 credits, then `this.adjustCredits(3)` would result in their
     * credits changing to 15, whereas `this.adjustCredits(-4)` would result in their credits
     * changing to 8.  Requires that the change would not cause the student's credits to become
     * negative.
     */
    void adjustCredits(int deltaCredits) {
        assert credits >= 0;
        credits += deltaCredits;
        assertInv();
    }

    /**
     * Return the full name of this student as its string representation.
     */
    @Override
    public String toString() {
        return fullName();
    }
}
