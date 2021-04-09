import java.util.Arrays;

public class SortedArrayGenerator {
    // Generate sorted array of elements including duplicate elements
    public Integer[] generate(int k) {
        Integer[] Sorted = new Integer[k];

        for (int i = 0; i < k; i++) {
            int g = (int) (Math.random() * 100);
            Sorted[i] = g;
        }
        // Using Bubble Sort:
        for (int i = 0; i < k - 1; i++) {
            for (int j = 0; j < k - i - 1; j++) {
                if (Sorted[j] > Sorted[j + 1]) {
                    int temp = Sorted[j];
                    Sorted[j] = Sorted[j + 1];
                    Sorted[j + 1] = temp;
                }
            }
        }
        return Sorted;
    }

    // Generate sorted array of unique elements
    public Integer[] generateUnique(int k){
        Integer[] Sorted = new Integer[k];

        for (int i = 0; i < k; i++) {
            int g = (int) (Math.random() * 100);
            if (Arrays.asList(Sorted).contains(g)){ i--; continue;}
            Sorted[i] = g;
        }
        // Using Bubble Sort:
        for (int i = 0; i < k - 1; i++) {
            for (int j = 0; j < k - i - 1; j++) {
                if (Sorted[j] > Sorted[j + 1]) {
                    int temp = Sorted[j];
                    Sorted[j] = Sorted[j + 1];
                    Sorted[j + 1] = temp;
                }
            }
        }
        return Sorted;
    }

}

