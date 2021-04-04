import java.util.Arrays;

public class BinarySearch {
    public  <T extends Comparable<T>> boolean binarySearch(T[] array, T element){
        if (array.length == 0) return false;

        int midIndex = (array.length-1) / 2;
        int comp = element.compareTo(array[midIndex]);

        if (comp == 0)
            return true;
        else if (comp > 0 && array.length > 1){
            T[] newArray = Arrays.copyOfRange(array, midIndex+1, array.length);
            return binarySearch(newArray, element);
        }
        else if (comp < 0 && array.length > 1){
            T[] newArray = Arrays.copyOfRange(array, 0, midIndex);
            return binarySearch(newArray, element);
        }
        else{
            return false;
        }
    }
}
