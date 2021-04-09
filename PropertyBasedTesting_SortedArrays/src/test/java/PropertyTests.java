import net.jqwik.api.*;
import net.jqwik.api.statistics.Statistics;
import static org.assertj.core.api.Assertions.*;
import java.util.List;


public class PropertyTests {

    @Provide("0 to 100")
    Arbitrary<Integer> numbers(){
        return Arbitraries.integers().between(0,100); // giving arbitrary number between 0 to 100
    }

    @Provide("0 to 30")
    Arbitrary<Integer> sizes(){
        return Arbitraries.integers().between(0,30); // // giving arbitrary number between 0 to 30
    }


    @Provide("unsortedArrays")
    Arbitrary<List<Integer>> unsortedArrays(){
        // Giving unsorted Array of random size upto 30
        int size = (int) (Math.random()*30);
        Arbitrary<Integer> integers = Arbitraries.integers().between(1, 100);
        Arbitrary<List<Integer>> collected = integers.collect(list -> list.size() >= size);

        return collected;
    }


    @Property
    boolean sortedArrayTest(@ForAll("0 to 30") int size){
        // testing if element exists in the sorted array using binary search
        // expected to return true in majority
        // generating sorted array with random size
        SortedArrayGenerator sortedArrayGenerator = new SortedArrayGenerator();
        Integer[] randomSortedArray = sortedArrayGenerator.generate(size);

        // this makes sure that element exists in the array
        int index = (int) (Math.random()*(randomSortedArray.length)); // to get the random index in the array
        Assume.that(randomSortedArray.length > 0);

        // calling binary search
        BinarySearch search = new BinarySearch();
        boolean result  = search.binarySearch(randomSortedArray, randomSortedArray[index]);
        return result;
    }

    @Property
    boolean sortedUniqueElementsArrayTest(@ForAll("0 to 30") int size){
        // testing if element exists in the sorted array of unique elements using binary search
        // expected to return true in majority
        // generating sorted array with random size
        SortedArrayGenerator sortedArrayGenerator = new SortedArrayGenerator();
        Integer[] randomSortedArray = sortedArrayGenerator.generateUnique(size);

        // this makes sure that element exists in the array
        int index = (int) (Math.random()*(randomSortedArray.length)); // to get the random index in the array
        Assume.that(randomSortedArray.length > 0);

        // calling binary search
        BinarySearch search = new BinarySearch();
        boolean result  = search.binarySearch(randomSortedArray, randomSortedArray[index]);
        return result;
    }

    @Property
    void randomNumberSortedArrayTest(@ForAll("0 to 30") int size, @ForAll("0 to 100") int value){
        // testing search of random number (value) between 0 to 100 in the sorted array
        // Non-deterministic behavior
        // generating sorted array with random size
        SortedArrayGenerator sortedArrayGenerator = new SortedArrayGenerator();
        Integer[] randomSortedArray = sortedArrayGenerator.generate(size);

        Assume.that(randomSortedArray.length > 0);

        // calling binary search
        BinarySearch search = new BinarySearch();
        boolean result  = search.binarySearch(randomSortedArray, value);
        Statistics.collect(result ? "randomNumber in Sorted Array Test true" : "randomNumber in Sorted Array Test false");
    }

    @Property
    void randomNumberUnsortedArrayTest(@ForAll("unsortedArrays") List<Integer> list, @ForAll("0 to 100") int value){
        // testing search of random number (value) between 0 to 100 using binary search in an unsorted array
        // expected to return false in majority
        // generating unsorted array with random size
        Integer[] randomUnsortedArray = list.stream().toArray(Integer[]::new);

        Assume.that(randomUnsortedArray.length > 0);

        // calling binary search
        BinarySearch search = new BinarySearch();
        boolean result  = search.binarySearch(randomUnsortedArray, value);
        Statistics.collect(result ? "randomNumber in Unsorted Array Test true" : "randomNumber in Unsorted Array Test false");
    }

    @Property
    boolean firstHalfIndexSortedArrayTest(@ForAll("0 to 30") int size){
        // testing element in the sorted array which exists in the first half
        // expected to return true in majority
        // generating sorted array with random size
        SortedArrayGenerator sortedArrayGenerator = new SortedArrayGenerator();
        Integer[] randomSortedArray = sortedArrayGenerator.generate(size);

        Assume.that(randomSortedArray.length > 0);
        int value = ((int) (Math.random() * (randomSortedArray.length))) / 2; // index always in the first half

        // calling the binary search
        BinarySearch search = new BinarySearch();
        boolean result  = search.binarySearch(randomSortedArray, randomSortedArray[value]);
        return result;
    }

    @Property
    boolean middleIndexSortedArrayTest(@ForAll("0 to 30") int size){
        // testing element in the sorted array which exists in the middle
        // expected to return true in majority
        // generating sorted array with random size
        SortedArrayGenerator sortedArrayGenerator = new SortedArrayGenerator();
        Integer[] randomSortedArray = sortedArrayGenerator.generate(size);

        Assume.that(randomSortedArray.length > 0);
        int value = (randomSortedArray.length/ 2) ; // index always in the  half

        BinarySearch search = new BinarySearch();
        boolean result  = search.binarySearch(randomSortedArray, randomSortedArray[value]);
        return result;
    }

    @Property
    boolean lastHalfIndexSortedArrayTest(@ForAll("0 to 30") int size){
        // testing element in the sorted array which exists in the last half
        // expected to return true in majority
        // generating sorted array with random size
        SortedArrayGenerator sortedArrayGenerator = new SortedArrayGenerator();
        Integer[] randomSortedArray = sortedArrayGenerator.generate(size);

        Assume.that(randomSortedArray.length > 0);
        // index always in the last half
        int value = (((int) (Math.random() * (randomSortedArray.length))) / 2) + (randomSortedArray.length/2);

        // calling the binary search
        BinarySearch search = new BinarySearch();
        boolean result  = search.binarySearch(randomSortedArray, randomSortedArray[value]);
        return result;
    }

    @Property
    void firstHalfIndexUnsortedArrayTest(@ForAll("unsortedArrays") List<Integer> list){
        // testing search of number using binary search in an unsorted array which exits in first half
        // expected to return false in majority
        // generating unsorted array with random size
        Integer[] randomUnsortedArray = list.stream().toArray(Integer[]::new);

        //Assume.that(Arrays.asList(randomSortedArray).contains(value));
        Assume.that(randomUnsortedArray.length > 0);
        int value = ((int) (Math.random() * (randomUnsortedArray.length))) / 2; // index always in the first half

        // calling the binary search
        BinarySearch search = new BinarySearch();
        boolean result  = search.binarySearch(randomUnsortedArray, randomUnsortedArray[value]);
        Statistics.collect(result ? "lastHalfIndexUnsortedArrayTest true" : "lastHalfIndexUnsortedArrayTest false");
    }

    @Property
    void middleIndexUnsortedArrayTest(@ForAll("unsortedArrays") List<Integer> list){
        // testing search of number using binary search in an unsorted array which exits in middle
        // expected to return false in majority, sometimes results in 100% false, makes our test to fail
        // generating unsorted array with random size
        Integer[] randomUnsortedArray = list.stream().toArray(Integer[]::new);

        //Assume.that(Arrays.asList(randomSortedArray).contains(value));
        Assume.that(randomUnsortedArray.length > 0);
        int value = (randomUnsortedArray.length) / 2; // index always in the middle

        // calling the binary search
        BinarySearch search = new BinarySearch();
        boolean result  = search.binarySearch(randomUnsortedArray, randomUnsortedArray[value]);
        Statistics.collect(result ? "middleIndexUnsortedArrayTest true" : "middleIndexUnsortedArrayTest false");
    }

    @Property
    void lastHalfIndexUnsortedArrayTest(@ForAll("unsortedArrays") List<Integer> list){
        // testing search of number using binary search in an unsorted array which exits in the last half
        // expected to return false in majority
        // generating unsorted array with random size
        Integer[] randomUnsortedArray = list.stream().toArray(Integer[]::new);

        Assume.that(randomUnsortedArray.length > 0);
        int value = (((int) (Math.random() * (randomUnsortedArray.length))) / 2) + (randomUnsortedArray.length/2); // index always in the last half
        BinarySearch search = new BinarySearch(); // calling the binary search
        boolean result  = search.binarySearch(randomUnsortedArray, randomUnsortedArray[value]);
        Statistics.collect(result ? "lastHalfIndexUnsortedArrayTest true" : "lastHalfIndexUnsortedArrayTest false");
    }

    @Property
    void unsortedArrayTest(@ForAll("unsortedArrays") List<Integer> list){
        // testing search of number using binary search in an unsorted array
        // expected to return false in majority
        // generating unsorted array with random size
        Integer[] randomUnsortedArray = list.stream().toArray(Integer[]::new);

        int index = (int) (Math.random()*(randomUnsortedArray.length)); // this makes sure that element exists in the array

        BinarySearch search = new BinarySearch(); // calling binary search

        boolean result = search.binarySearch(randomUnsortedArray, randomUnsortedArray[index]);

        Statistics.collect(result ? "Unsorted Array Test true" : "Unsorted Array Test false");
    }

    @Property
    boolean nullArrayTest(@ForAll("0 to 100") int searchValue){
        // testing null array with binary search
        // expected to return false

        Integer[] nullArray = new SortedArrayGenerator().generate(0);

        BinarySearch search = new BinarySearch();
        return !(search.binarySearch(nullArray, searchValue));
    }


}
