package de.tbh.exampleImplementations.sorts;
public class Sorts {

    // selection sort
    public static void selectionSort(int[] arr){  
        for (int i = 0; i < arr.length - 1; i++)  {
            int index = i;  
            for (int j = i + 1; j < arr.length; j++){  
                if (arr[j] < arr[index]){  
                    index = j;
                }  
            }  
            int smallerNumber = arr[index];   arr[index] = arr[i];  arr[i] = smallerNumber;  
        }  
    }  
    //insertion sort

    public static void insertionSort(int[] arr) {
        int n = arr.length;
        for (int j = 1; j < n; j++) {
            int key = arr[j];
            int i = j - 1;
            while ((i > -1) && (arr[i] > key)) {
                arr[i + 1] = arr[i];
                i--;
            }
            arr[i + 1] = key;
        }
    }

    // quick sort
    int partition(int intArray[], int low, int high) {
        int pi = intArray[high];
        int i = (low - 1); // smaller element index
        for (int j = low; j < high; j++) {
            // check if current element is less than or equal to pi
            if (intArray[j] <= pi) {
                i++;
                // swap intArray[i] and intArray[j]
                int temp = intArray[i];
                intArray[i] = intArray[j];
                intArray[j] = temp;
            }
        }

        // swap intArray[i+1] and intArray[high] (or pi)
        int temp = intArray[i + 1];
        intArray[i + 1] = intArray[high];
        intArray[high] = temp;

        return i + 1;
    }

    void quickSort(int[] intArray, int low, int high) {
        if (low < high) {
            int pi = partition(intArray, low, high);

            quickSort(intArray, low, pi - 1);
            quickSort(intArray, pi + 1, high);
        }
    }
}
