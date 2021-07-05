package com.gupta;

import java.util.ArrayList;
import java.util.List;

public class FalsePositive {

    public static void main(String[] args){


        List<Integer> list = null;

        for (int i=0; i<256; i++){
            if (i == 128){
                list = new ArrayList<Integer>(){{
                    add(1);
                    add(2);
                    add(3);
                }};
            }
        }

        int count  = list.size();
        /* Run execution of infer on this program:
            infer -- javac FalsePositive.java
            Capturing in javac mode...
            Found 1 source file to analyze in /Users/rambhawan/Desktop/untitled folder 2/SE576/src/com/gupta/infer-out


            Analysis finished in 1.752ss

            Found 1 issue

            FalsePositive.java:23: error: NULL_DEREFERENCE
              object `list` last assigned on line 11 could be null and is dereferenced at line 23.
              21.           }
              22.
              23. >         int count  = list.size();
              24.       }
              25.   }


            Summary of the reports

              NULL_DEREFERENCE: 1
        */
    }
}
