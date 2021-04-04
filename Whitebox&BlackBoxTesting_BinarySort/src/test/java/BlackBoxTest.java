import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BlackBoxTest {
    // input space divided into equal partitions based on the types of data
    /* Why this covers the input domain:
     * First, input domain for this assignment is valid for all the pre-defined comparable data types sorted arrays.
     * The following Integers, Double, and String arrays covers the comparable data types making
     * our input domain.
     * Now, the partitioning is done on the basis of their data types
     * Further partitioning can be done for integers and double for negative and non-negative numbers
     * Also, partitioning of String can be done with mix of Uppercase and lowercase strings - invalid class*/

    Integer[] inputInt = new Integer[]{1,2,3,4,5,6,7,8,9,10};
    Integer[] inputIntNegative = new Integer[]{-9,-8,-7,-6,-5,-4,-3,-2,-1};
    Double[] inputDouble = new Double[]{1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0};
    Double[] inputDoubleNegative = new Double[]{-9.0,-8.0,-7.0,-6.0,-5.0,-4.0,-3.0,-2.0,-1.0};
    String[] inputString = new String[]{"a","b","c","d","e","f","g","h","i","j"};
    String[] inputStringInvalid = new String[]{"a","B","C","D","E","F"};

    @Test
    public void testIntegers(){
        BinarySearch search = new BinarySearch();
        assertEquals(search.binarySearch(inputInt, 4), true);
        assertEquals(search.binarySearch(inputInt, 11), false);
    }

    @Test
    public void testIntegersNegative(){
        BinarySearch search = new BinarySearch();
        assertEquals(search.binarySearch(inputIntNegative, -4), true);
        assertEquals(search.binarySearch(inputIntNegative, -11), false);
    }

    @Test
    public void testDouble(){
        BinarySearch search = new BinarySearch();
        assertEquals(search.binarySearch(inputDouble, 4.0), true);
        assertEquals(search.binarySearch(inputDouble, 11.0), false);
    }

    @Test
    public void testDoubleNegative(){
        BinarySearch search = new BinarySearch();
        assertEquals(search.binarySearch(inputDoubleNegative, -4.0), true);
        assertEquals(search.binarySearch(inputDoubleNegative, -11.0), false);
    }

    @Test
    public void testString(){
        BinarySearch search = new BinarySearch();
        assertEquals(search.binarySearch(inputString, "a"), true);
        assertEquals(search.binarySearch(inputString, "abc"), false);
    }

    @Test
    public void testStringMix() {
        BinarySearch search = new BinarySearch();
        assertEquals(search.binarySearch(inputStringInvalid, "F"), true);
        assertEquals(search.binarySearch(inputStringInvalid, "a"), false);
    }
}
