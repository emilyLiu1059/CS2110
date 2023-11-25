package cs2110;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedSeqTest {

    // Helper functions for creating lists used by multiple tests.  By constructing strings with
    // `new`, more likely to catch inadvertent use of `==` instead of `.equals()`.

    /**
     * Creates [].
     */
    static Seq<String> makeList0() {
        return new LinkedSeq<>();
    }

    /**
     * Creates ["A"].  Only uses prepend.
     */
    static Seq<String> makeList1() {
        Seq<String> ans = new LinkedSeq<>();
        ans.prepend(new String("A"));
        return ans;
    }

    /**
     * Creates ["A", "B"].  Only uses prepend.
     */
    static Seq<String> makeList2() {
        Seq<String> ans = new LinkedSeq<>();
        ans.prepend(new String("B"));
        ans.prepend(new String("A"));
        return ans;
    }

    /**
     * Creates ["A", "B", "C"].  Only uses prepend.
     */
    static Seq<String> makeList3() {
        Seq<String> ans = new LinkedSeq<>();
        ans.prepend(new String("C"));
        ans.prepend(new String("B"));
        ans.prepend(new String("A"));
        return ans;
    }

    /**
     * Creates a list containing the same elements (in the same order) as array `elements`.  Only
     * uses prepend.
     */
    static <T> Seq<T> makeList(T[] elements) {
        Seq<T> ans = new LinkedSeq<>();
        for (int i = elements.length; i > 0; i--) {
            ans.prepend(elements[i - 1]);
        }
        return ans;
    }

    @Test
    void testConstructorSize() {
        Seq<String> list = new LinkedSeq<>();
        assertEquals(0, list.size());
    }

    @Test
    void testPrependSize() {
        // List creation helper functions use prepend.
        Seq<String> list;

        list = makeList1();
        assertEquals(1, list.size());

        list = makeList2();
        assertEquals(2, list.size());

        list = makeList3();
        assertEquals(3, list.size());
    }

    @Test
    void testToString() {
        Seq<String> list;

        list = makeList0();
        assertEquals("[]", list.toString());

        list = makeList1();
        assertEquals("[A]", list.toString());

        list = makeList2();
        assertEquals("[A, B]", list.toString());

        list = makeList3();
        assertEquals("[A, B, C]", list.toString());
    }

    @Test
    void testLinkedSeq() {
        LinkedSeq<String> ans = new LinkedSeq<>();
        ans.prepend("A");
        assertTrue(ans.contains("A"));
        assertFalse(ans.contains("B"));
        assertEquals("A", ans.get(0));
        ans.append("B");
        assertEquals("B", ans.get(1));
        ans.insertBefore("C", "B");
        assertEquals("C", ans.get(1));
        assertTrue(ans.remove("B"));
        assertEquals("[A, C]", ans.toString());

        LinkedSeq<String> ans2 = new LinkedSeq<>();
        ans2.prepend("A");
        ans2.append("C");
        assertTrue(ans.equals(ans2));
        ans2.remove("A");
        assertEquals("[C]", ans2.toString());
        ans2.append("B");
        assertFalse(ans.equals(ans2));
        ans2.insertBefore("D", "C");
        assertEquals("D", ans2.get(0));

        LinkedSeq<String> ans3 = new LinkedSeq<>();
        ans3.append("D");
        ans3.append("C");
        ans3.append("B");
        assertTrue(ans3.equals(ans2));
        ans3.append("F");
        assertFalse(ans3.equals(ans));
    }

    /*
     * There is no need to read the remainder of this file for the purpose of completing the
     * assignment.  We have not yet covered `hashCode()` or `assertThrows()` in class.
     */

    @Test
    void testHashCode() {
        assertEquals(makeList0().hashCode(), makeList0().hashCode());

        assertEquals(makeList1().hashCode(), makeList1().hashCode());

        assertEquals(makeList2().hashCode(), makeList2().hashCode());

        assertEquals(makeList3().hashCode(), makeList3().hashCode());
    }

    @Test
    void testIterator() {
        Seq<String> list;
        Iterator<String> it;

        list = makeList0();
        it = list.iterator();
        assertFalse(it.hasNext());
        Iterator<String> itAlias = it;
        assertThrows(NoSuchElementException.class, () -> itAlias.next());

        list = makeList1();
        it = list.iterator();
        assertTrue(it.hasNext());
        assertEquals("A", it.next());
        assertFalse(it.hasNext());

        list = makeList2();
        it = list.iterator();
        assertTrue(it.hasNext());
        assertEquals("A", it.next());
        assertTrue(it.hasNext());
        assertEquals("B", it.next());
        assertFalse(it.hasNext());
    }
}
