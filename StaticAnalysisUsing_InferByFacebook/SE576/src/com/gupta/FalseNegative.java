package com.gupta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FalseNegative {

    public static void main(String[] args) {

        HashMap<String, List<Integer>> maps = new HashMap<>();
        maps.put("lists", null);

        HashMap<String, Integer> map = new HashMap<>();
        map.put("list", null);

        // Infer might not able to analyze the actual values inside of a hasmap
        // I have stored null values inside of my hashmaps which essentially have Wrapper classes in it.
        // From my experience, I believe infer is just checking the wrapper class type not the actual value
        // This produces errors as null is de-referenced
        // We can see the program fail as we run it because of null de-referencing but Infer produced Negative for Errors
        // Thus, this is false negative situation for Infer.

        // run execution of Infer on this program:
        /*
            infer -- javac FalseNegative.java
            Capturing in javac mode...
            Found 1 source file to analyze in /Users/rambhawan/Desktop/untitled folder 2/SE576/src/com/gupta/infer-out


            Analysis finished in 1.627ss

            No issues found
         */
        int counter = map.get("list"); // expecting Error because primitive types can't be null
        int counters = maps.get("lists").size(); // expecting Error because Object passed is null, .size() does not exists for that.


    }
}
