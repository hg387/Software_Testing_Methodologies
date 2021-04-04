import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class WhiteBoxTest {
    Integer[] inputNotSorted = new Integer[]{9,1,5,6,4,3,2,7,6,8};
    Integer[] inputNullArray = new Integer[0];
    Double[] inputDouble = new Double[]{-3.0,-2.0,-1.0,1.0,2.0,3.0};
    Double[] inputDoubleNotSorted = new Double[]{-2.0, -5.0, 7.0, 5.0};
    Integer[] inputSingleton = new Integer[]{1};
    @Test
    public void testNotSorted(){
        // non-sorted array may result in unexpected output
        BinarySearch search = new BinarySearch();
        assertEquals(search.binarySearch(inputNotSorted, 9), false); // 9 does exist in the array
        assertEquals(search.binarySearch(inputNotSorted, 2), false); // 2 does exist in the array
    }

    @Test
    public void testNullArray(){
        BinarySearch search = new BinarySearch();
        assertEquals(search.binarySearch(inputNullArray,9), false);
    }

    @Test
    public void testDoubleMix(){
        BinarySearch search = new BinarySearch();
        assertEquals(search.binarySearch(inputDouble,2.0), true);
        assertEquals(search.binarySearch(inputDouble,11.0), false);
    }

    @Test
    public void testDoubleNotSorted(){
        BinarySearch search = new BinarySearch();
        assertEquals(search.binarySearch(inputDoubleNotSorted,5.0), false); // 5 do exist
    }

    @Test
    public void testSingleton(){
        BinarySearch search = new BinarySearch();
        assertEquals(search.binarySearch(inputSingleton,1), true);
        assertEquals(search.binarySearch(inputSingleton,2), false);
    }
}
