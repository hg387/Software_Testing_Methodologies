SE 576 HW2 Himanshu Gupta

README:

1)  for runnning the test, use this command: mvn clean test

    This command will compile and run all the tests.

2)  In the case of search of elements exist only in the middle of the
    unsorted array, sometimes it always return false so run the test
    couple or more times to get better results.

3)  In the PropertyTest.java, I have described all the properties in the
    comments saying what this test do, and what we expect out of it.

4)  In the result, all the tests are marked with their names.

5)  For more info look into Reflection.pdf

TASKS:

1)  Your binary search only works properly on sorted arrays. So most of
    the properties you’ll specify for your search should be stated for
    sorted arrays. As discussed in lecture, the odds of generating an
    array of some random length that happens to be sorted, without some
    additional work, are astronomically low. So you need to write a
    generator that produces only sorted arrays. Write a generator in the
    relevant framework, which produces only sorted arrays. You may
    choose a modest upper bound on length (e.g., arrays of some random
    length up to size 30). You may also generate arrays of integers to
    keep things a bit simpler, and you may restrict the array elements
    to a certain reasonable range (e.g., 0-100).

For easy reference, here are links to the relevant parts of the jqwik
documentation and the hypothesis documentation. Note that you can either
generate the array structure yourself, or generate a random list (e.g.,
hypothesis.strategies.lists or Arbitrary.list) and convert it to an
array.

2)  Write a file PropertyTest.jaba containing property based tests for
    your binary search. Some of your tests should use a default
    generator for arrays. while most should probably use custom
    generator abpve. Write atleast 10 meaningful different properties
    using jqwick framework.

