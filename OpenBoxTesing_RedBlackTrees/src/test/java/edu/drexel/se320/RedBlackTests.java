package edu.drexel.se320;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import net.jqwik.api.*;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.Size;
import net.jqwik.api.constraints.Unique;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RedBlackTests {

    // Test to check null passed as first argument in the put()
    @Test
    public void testPutNullKey() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();
        Exception exc = assertThrows(IllegalArgumentException.class,
            () -> { tree.put(null, "Hello"); }
        );

        tree.put(1, null);
        assertEquals("first argument to put() is null", exc.getMessage());
        assertNull(tree.get(1));
    }

    // Test to check null passed as second argument in the put()
    @Test
    public void testPutNullValue() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();
        tree.put(1, null);
        assertNull(tree.get(1));
    }

    // Test to check null passed in the get()
    @Test
    public void testGetNullKey() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();
        Exception exc = assertThrows(IllegalArgumentException.class,
                () -> { tree.get(null); }
        );
        assertEquals("argument to get() is null", exc.getMessage());
    }

    // Test to check null passed in the max()
    @Test
    public void testMaxNull() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();
        Exception exc = assertThrows(NoSuchElementException.class,
                () -> { tree.max(); }
        );
        assertEquals("called max() with empty symbol table", exc.getMessage());
    }

    // Test to check null passed in the min()
    @Test
    public void testMinNull() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();
        Exception exc = assertThrows(NoSuchElementException.class,
                () -> { tree.min(); }
        );
        assertEquals("called min() with empty symbol table", exc.getMessage());
    }

    // Test to check null passed in the rank()
    @Test
    public void testRankNull() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();
        Exception exc = assertThrows(IllegalArgumentException.class,
                () -> { tree.rank(null); }
        );
        assertEquals("argument to rank() is null", exc.getMessage());
    }

    // Test to check value not in the tree passed in the rank()
    @Test
    public void testRankNoKeyFound() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();
        assertEquals(tree.rank(1), 0);
    }

    // Test to check null passed in the floor()
    @Test
    public void testFloorNullKey() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();
        Exception exc = assertThrows(IllegalArgumentException.class,
                () -> { tree.floor(null); }
        );
        assertEquals("argument to floor() is null", exc.getMessage());
    }

    // Test to check floor() with the empty tree
    @Test
    public void testFloorEmptyTree() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();
        Exception exc = assertThrows(NoSuchElementException.class,
                () -> { tree.floor(1); }
        );
        assertEquals("called floor() with empty symbol table", exc.getMessage());
    }

    // Test to check ceiling() with the empty tree
    @Test
    public void testCeilingEmptyTree() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();
        Exception exc = assertThrows(NoSuchElementException.class,
                () -> { tree.ceiling(1); }
        );
        assertEquals("called ceiling() with empty symbol table", exc.getMessage());
    }

    // Test to check DeleteMin() with the empty tree
    @Test
    public void testDeleteMinEmptyTree() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();
        Exception exc = assertThrows(NoSuchElementException.class,
                () -> { tree.deleteMin(); }
        );
        assertEquals("BST underflow", exc.getMessage());
    }

    // Test to check DeleteMax() with the empty tree
    @Test
    public void testDeleteMaxEmptyTree() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();
        Exception exc = assertThrows(NoSuchElementException.class,
                () -> { tree.deleteMax(); }
        );
        assertEquals("BST underflow", exc.getMessage());
    }

    // Test to check Delete() when null is passed
    @Test
    public void testDeleteNullPassed() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();
        Exception exc = assertThrows(IllegalArgumentException.class,
                () -> { tree.delete(null); }
        );
        assertEquals("argument to delete() is null", exc.getMessage());
    }

    // Test to check Ceiling() when null is passed
    @Test
    public void testCeilingNullKey() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();
        Exception exc = assertThrows(IllegalArgumentException.class,
                () -> { tree.ceiling(null); }
        );
        assertEquals("argument to ceiling() is null", exc.getMessage());
    }

    // Test to check Iterable() when null is passed in each argument
    @Test
    public void testIterableNull() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();
        Exception exc = assertThrows(IllegalArgumentException.class,
                () -> { tree.keys(null, 1); }
        );
        Exception exc1 = assertThrows(IllegalArgumentException.class,
                () -> { tree.keys(1, null); }
        );
        assertEquals("first argument to keys() is null", exc.getMessage());
        assertEquals("second argument to keys() is null", exc1.getMessage());
    }

    // Test to check size() when null is passed in each argument
    @Test
    public void testSizeException() {
        RedBlackBST<Integer,String> tree = new RedBlackBST<>();
        Exception exc = assertThrows(IllegalArgumentException.class,
                () -> { tree.size(null, 1); }
        );
        Exception exc1 = assertThrows(IllegalArgumentException.class,
                () -> { tree.size(1, null); }
        );
        assertEquals("first argument to size() is null", exc.getMessage());
        assertEquals("second argument to size() is null", exc1.getMessage());
    }

    // Test to size() with the randomly generated trees fo size between 0 to 50
    // and containing integers between the range [-200, 200]
    @Property(tries=100)
    public void testSizes(@ForAll @Size(min = 0, max = 50)List<@IntRange(min = -200, max = 200) @Unique Integer> list){
        Integer realSize = list.size();

        RedBlackBST<Integer, String> tree = new RedBlackBST<>();
        list.forEach(value -> tree.put(value, value.toString()));

        assertEquals(tree.size(), realSize);
    }

    // Test to size(lo, high) in-between with the randomly generated trees
    // of size between 5 to 50 and containing integers between the range [-200, 200]
    @Property(tries=50)
    public void testSizesInBetween(@ForAll @Size(min = 5, max = 50)List<@IntRange(min = -200, max = 200) @Unique Integer> list){
        RedBlackBST<Integer, String> tree = new RedBlackBST<>();
        list.forEach(value -> tree.put(value, value.toString()));

        Collections.sort(list);

        Integer  loIndex = new Random().nextInt((int) (list.size() / 2));
        Integer  lo = list.get(loIndex);
        Integer high = 0;

        double ran = Math.random();
        Integer realSize = 0;
        if (ran < 0.2 || loIndex == 0) {
            high = list.get((new Random().nextInt(list.size() - loIndex) + loIndex));
            realSize = list.indexOf(high) - list.indexOf(lo) + 1;
        }
        else if (ran > 0.2 && ran < 0.7) {
            high = list.get(new Random().nextInt(loIndex));
        }
        else {
            high = new Random().nextInt(100) + 300;
            realSize = list.size() - list.indexOf(lo);
        }

        assertThat(tree.size(lo, high), anyOf(is(realSize), is(0)));
    }

    // Test to size(lo. high) in-between lo and high keys where high not found in the tree
    // with the randomly generated trees of size between 5 to 50
    // and containing integers between the range [-200, 200]
    @Property(tries=10)
    public void testSizesInBetweenHighNotFound(@ForAll @Size(min = 5, max = 50)List<@IntRange(min = -200, max = 200) @Unique Integer> list){
        RedBlackBST<Integer, String> tree = new RedBlackBST<>();
        list.forEach(value -> tree.put(value, value.toString()));

        Collections.sort(list);

        Integer  loIndex = new Random().nextInt((int) (list.size() / 2));
        Integer  lo = list.get(loIndex);
        Integer high = new Random().nextInt(100) + 300;
        Integer realSize = list.size() - list.indexOf(lo);


        assertThat(tree.size(lo, high), is(realSize));
    }

    // Test to size(lo. high) in-between lo and high keys where low key is bigger than high key
    // with the randomly generated trees of size between 5 to 50
    // and containing integers between the range [-200, 200]
    @Property(tries=10)
    public void testSizesInBetweenBiggerLow(@ForAll @Size(min = 5, max = 50)List<@IntRange(min = -200, max = 200) @Unique Integer> list){
        RedBlackBST<Integer, String> tree = new RedBlackBST<>();
        list.forEach(value -> tree.put(value, value.toString()));

        Collections.sort(list);

        Integer  loIndex = new Random().nextInt((int) (list.size() / 2)) + 1;
        Integer  lo = list.get(loIndex);
        Integer high = list.get(new Random().nextInt(loIndex));
        Integer realSize = list.size() - list.indexOf(lo);


        assertThat(tree.size(lo, high), is(0));
    }

    // Test to rank() of randomly generated searched element between [-200,200]
    // with the randomly generated trees of size between 1 to 50
    // and containing integers between the range [-200, 200]
    @Property(tries=100)
    public void testRank(@ForAll @Size(min = 1, max = 50)List<@IntRange(min = -200, max = 200) @Unique Integer> list,
                         @ForAll @IntRange(min = -200, max = 200) @Unique Integer randomElementToSearch){

        // number of keys less than @randomElementToSearch in the tree
        Stream<Integer> keys = list.stream().filter(val -> val < randomElementToSearch);
        Integer realRank = (int) keys.count();

        RedBlackBST<Integer, String> tree = new RedBlackBST<>();
        list.forEach(value -> tree.put(value, value.toString()));

        assertThat(tree.rank(randomElementToSearch), anyOf(is(realRank), is(0)));
    }

    // Test to ceiling() of randomly generated searched element between [-200,200]
    // with the randomly generated trees of size between 1 to 50
    // and containing integers between the range [-200, 200]
    @Property(tries=100)
    public void testCeiling(@ForAll @Size(min = 1, max = 50)List<@IntRange(min = -200, max = 200) @Unique Integer> list,
                            @ForAll @IntRange(min = -200, max = 200) @Unique Integer randomElementToSearch){
        RedBlackBST<Integer, String> tree = new RedBlackBST<>();
        list.forEach(value -> tree.put(value, value.toString()));

        Integer randomOptional = list.get(new Random().nextInt(list.size()));
        Integer realKey = 0;
        // number of keys greater than @randomElementToSearch in the tree
        Stream<Integer> keys =  list.stream().filter(val -> val >= randomElementToSearch);

        if (keys.count() == 0) {
            realKey = list.stream().filter(val -> val >= randomOptional).min(Integer::compare).get();
            assertEquals(tree.ceiling(randomOptional), realKey);

        }
        else {
            realKey = list.stream().filter(val -> val >= randomElementToSearch).min(Integer::compare).get();
            assertEquals(tree.ceiling(randomElementToSearch), realKey);
        }
    }

    // Test to check Ceiling() when null is passed in each argument
    // with the randomly generated trees of size between 1 to 50
    // and containing integers between the range [-200, 200]
    @Property(tries=100)
    public void testCeilingNull(@ForAll @Size(min = 1, max = 50)List<@IntRange(min = -200, max = 200) @Unique Integer> list,
                            @ForAll @IntRange(min = 201, max = 999) @Unique Integer randomElementToSearch){
        RedBlackBST<Integer, String> tree = new RedBlackBST<>();
        list.forEach(value -> tree.put(value, value.toString()));

        assertNull(tree.ceiling(randomElementToSearch));

    }

    // Test to check Floor() when null is passed in each argument
    // with the randomly generated trees of size between 1 to 50
    // and containing integers between the range [-200, 200]
    @Property(tries=100)
    public void testFloorNull(@ForAll @Size(min = 1, max = 50)List<@IntRange(min = -200, max = 200) @Unique Integer> list,
                                @ForAll @IntRange(min = -999, max = -201) @Unique Integer randomElementToSearch){
        RedBlackBST<Integer, String> tree = new RedBlackBST<>();
        list.forEach(value -> tree.put(value, value.toString()));

        assertNull(tree.floor(randomElementToSearch));

    }

    // Test to check Floor() of randomly generated searched element between [-200,200]
    // with the randomly generated trees of size between 1 to 50
    // and containing integers between the range [-200, 200]
    @Property(tries=100)
    public void testFloor(@ForAll @Size(min = 1, max = 50)List<@IntRange(min = -200, max = 200) @Unique Integer> list,
                            @ForAll @IntRange(min = -200, max = 200) @Unique Integer randomElementToSearch){
        RedBlackBST<Integer, String> tree = new RedBlackBST<>();
        list.forEach(value -> tree.put(value, value.toString()));

        Integer randomOptional = list.get(new Random().nextInt(list.size()));
        Integer realKey = 0;
        // number of keys greater than @randomElementToSearch in the tree
        Stream<Integer> keys =  list.stream().filter(val -> val <= randomElementToSearch);

        if (keys.count() == 0) {
            realKey = list.stream().filter(val -> val <= randomOptional).max(Integer::compare).get();
            assertEquals(tree.floor(randomOptional), realKey);

        }
        else {
            realKey = list.stream().filter(val -> val <= randomElementToSearch).max(Integer::compare).get();
            assertEquals(tree.floor(randomElementToSearch), realKey);
        }
    }

    // Test to check max()
    // with the randomly generated trees of size between 1 to 50
    // and containing integers between the range [-200, 200]
    @Property(tries=100)
    public void testMax(@ForAll @Size(min = 1, max = 50)List<@IntRange(min = -200, max = 200) @Unique Integer> list){
        Integer realMax = Collections.max(list);

        RedBlackBST<Integer, String> tree = new RedBlackBST<>();
        list.forEach(value -> tree.put(value, value.toString()));

        assertEquals(tree.max(), realMax);
    }

    // Test to check min()
    // with the randomly generated trees of size between 1 to 50
    // and containing integers between the range [-200, 200]
    @Property(tries=100)
    public void testMin(@ForAll @Size(min = 1, max = 50)List<@IntRange(min = -200, max = 200) @Unique Integer> list){
        Integer realMin = Collections.min(list);

        RedBlackBST<Integer, String> tree = new RedBlackBST<>();
        list.forEach(value -> tree.put(value, value.toString()));

        assertEquals(tree.min(), realMin);
    }

    // Test to check DeleteMin()
    // with the randomly generated trees of size between 1 to 100
    // and containing integers between the range [-100, 100]
    @Property(tries=200)
    public void testDeleteMin(@ForAll @Size(min = 1, max = 100)List<@IntRange(min = -100, max = 100) @Unique Integer> list){
        Integer realMin = Collections.min(list);

        RedBlackBST<Integer, String> tree = new RedBlackBST<>();
        list.forEach(value -> tree.put(value, value.toString()));
        tree.deleteMin();
        assertNull(tree.get(realMin));
    }

    // Test to check DeleteMax()
    // with the randomly generated trees of size between 1 to 100
    // and containing integers between the range [-100, 100]
    @Property(tries=200)
    public void testDeleteMax(@ForAll @Size(min = 1, max = 100)List<@IntRange(min = -100, max = 100) @Unique Integer> list){
        Integer realMax = Collections.max(list);

        RedBlackBST<Integer, String> tree = new RedBlackBST<>();
        list.forEach(value -> tree.put(value, value.toString()));
        tree.deleteMax();
        assertNull(tree.get(realMax));
    }

    // Test to check Delete() of randomly generated searched element between [-100,100]
    // with the randomly generated trees of size between 1 to 100
    // and containing integers between the range [-100, 100]
    @Property(tries=200)
    public void testDelete(@ForAll @Size(min = 1, max = 100)List<@IntRange(min = -100, max = 100)  Integer> list,
                           @ForAll @IntRange(min = -100, max = 100)  Integer randomElementToSearch){
        RedBlackBST<Integer, String> tree = new RedBlackBST<>();
        list.forEach(value -> tree.put(value, value.toString()));
        tree.delete(randomElementToSearch);

        assertNull(tree.get(randomElementToSearch));
    }

    // Test to check Delete() of randomly generated unique searched element between [-100,100]
    // with the randomly generated trees of size between 1 to 100
    // and containing integers between the range [-100, 100]
    @Property(tries=200)
    public void testDeleteUnique(@ForAll @Size(min = 1, max = 100)List<@IntRange(min = -100, max = 100) @Unique Integer> list,
                           @ForAll @IntRange(min = -100, max = 100) @Unique Integer randomElementToSearch){
        RedBlackBST<Integer, String> tree = new RedBlackBST<>();
        list.forEach(value -> tree.put(value, value.toString()));
        randomElementToSearch = Math.random() > 0.5 ? randomElementToSearch : new Random().nextInt(list.size());
        tree.delete(randomElementToSearch);

        assertNull(tree.get(randomElementToSearch));
    }

    // Test to check get()
    // with the randomly generated trees of size between 1 to 50
    // and containing integers between the range [-200, 200]
    @Property(tries=100)
    public void testGetAndPut(@ForAll @Size(min = 1, max = 50)List<@IntRange(min = -200, max = 200)  Integer> list){
        Integer randomElementToSearch = list.get(new Random().nextInt(list.size()));

        RedBlackBST<Integer, String> tree = new RedBlackBST<>();
        list.forEach(value -> tree.put(value, value.toString()));

        assertEquals(tree.get(randomElementToSearch), randomElementToSearch.toString());
    }

    // Test to check get() of randomly generated unique searched element between [2000,3000]
    // searched elements are not in the tree
    // with the randomly generated trees of size between 1 to 50
    // and containing integers between the range [-200, 200]
    @Property(tries=100)
    public void testGetNull(@ForAll @Size(min = 1, max = 50)List<@IntRange(min = -200, max = 200) @Unique Integer> list,
                                  @ForAll @IntRange(min = 2000, max = 3000) @Unique Integer randomElementToSearch){
        RedBlackBST<Integer, String> tree = new RedBlackBST<>();
        list.forEach(value -> tree.put(value, value.toString()));

        assertNull(tree.get(randomElementToSearch));
    }

    // Test to check contains()
    // with the randomly generated trees of size between 1 to 50
    // and containing integers between the range [-200, 200]
    @Property(tries=100)
    public void testContainsTrue(@ForAll @Size(min = 1, max = 50)List<@IntRange(min = -200, max = 200) @Unique Integer> list){
        Integer randomElementToSearch = list.get(new Random().nextInt(list.size()));

        RedBlackBST<Integer, String> tree = new RedBlackBST<>();
        list.forEach(value -> tree.put(value, value.toString()));

        assertTrue(tree.contains(randomElementToSearch));
    }

    // Test to check contains() with False
    // with the randomly generated trees of size between 1 to 50
    // and containing integers between the range [-200, 200]
    @Property(tries = 100)
    public void testContainsFalse(@ForAll @Size(min = 1, max = 50)List<@IntRange(min = -200, max = 200) @Unique Integer> list){

        RedBlackBST<Integer, String> tree = new RedBlackBST<>();
        list.forEach(value -> tree.put(value, value.toString()));

        assertFalse(tree.contains(10000));
    }

    // Test to check Iterable()
    // with the randomly generated trees of size between 20 to 150
    // and containing integers between the range [-200, 200]
    @Property(tries = 100)
    public void testIterable(@ForAll @Size(min = 20, max = 150)List<@IntRange(min = -200, max = 200) @Unique Integer>  list){

        RedBlackBST<Integer, String> tree = new RedBlackBST<>();
        list.forEach(value -> tree.put(value, value.toString()));
        Iterable<Integer> generatedList = tree.keys();
        List<Integer> newList = new ArrayList<>();

        generatedList.forEach(newList::add);

        assertTrue(list.size() == newList.size() && list.containsAll(newList) && newList.containsAll(list));
    }

    // Test to check Iterable() in-between keys
    // with the randomly generated trees of size between 20 to 150
    // and containing integers between the range [-200, 200]
    @Property(tries=50)
    public void testIterableInBetween(@ForAll @Size(value = 25)List<@IntRange(min = -200, max = 200) @Unique Integer> list,
                                      @ForAll @IntRange(min = 1, max = 24) @Unique Integer randomNum) {
        RedBlackBST<Integer, String> tree = new RedBlackBST<>();
        list.forEach(value -> tree.put(value, value.toString()));

        Integer lo = list.get(randomNum);
        Integer high = list.get(new Random().nextInt(randomNum));

        Iterable<Integer> generatedList = lo < high ? tree.keys(lo, high) : tree.keys(high, lo);

        Stream<Integer> keys =  lo < high ? list.stream().filter(val -> val >= lo && val <= high) :
                list.stream().filter(val -> val <= lo && val >= high);

        List<Integer> realList = new ArrayList<>();
        List<Integer> newList = new ArrayList<>();

        generatedList.forEach(newList::add);
        keys.forEach(realList::add);
        assertTrue(realList.size() == newList.size() && realList.containsAll(newList) && newList.containsAll(realList));
    }
}
 
