/*  Emma Shu: This program will sort one of the four input files by splitting the file at the commas and sorting it with the chosen 
 *  sort method. The four sort methods are bubble sort, selection sort, table sort, and quick sort which are described in their sorting
 *  methods below.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Scanner;

public class Sorting {

	Scanner consoleInput = new Scanner(System.in);
	String input;
	Scanner fileInput;
	int[] inputArray;
	long startTime;
	
	public Sorting() {
		// Ask user to choose an input file.
		System.out.println("Enter a number for the input file.");
		System.out.println("1: input1.txt\n2: input2.txt\n3: input3.txt\n4: input4.txt");
		input = consoleInput.nextLine();
		// In case the user inputs an input that does not translate into a valid file.
		if (input.length() != 1 || input.charAt(0) != '1' && input.charAt(0) != '2' && input.charAt(0) != '3' && input.charAt(0) != '4') {
			System.out.println("Please enter an integer between 1 and 4 inclusive.");
			while (input.length() != 1 || input.charAt(0) != '1' && input.charAt(0) != '2' && input.charAt(0) != '3' && input.charAt(0) != '4') {
				input = consoleInput.nextLine();
			}
		}
		// In case the file does not exist.
		try {
			fileInput = new Scanner(new File("input" + input.charAt(0) + ".txt"));
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
		// Retrieve file and split the numbers into a String array with the comma.
		String infile = fileInput.nextLine();
		String[] inputStringArray = infile.split(",");
		inputArray = new int[inputStringArray.length];
		// Put the String array into an integer array, parses String values into integer values.
		for (int i = 0; i < inputStringArray.length; i++) {
			inputArray[i] = Integer.parseInt(inputStringArray[i]);
		}
		// Ask user to choose a sorting method.
		System.out.println("Enter a number for sorting method.");
		System.out.println("1: Bubble Sort\n2: Selection Sort\n3: Table Sort\n4: Quick Sort");
		input = consoleInput.nextLine();
		// In case the user inputs an input that does not translate into a valid sorting method.
		if (input.length() != 1 || input.charAt(0) != '1' && input.charAt(0) != '2' && input.charAt(0) != '3' && input.charAt(0) != '4') {
			System.out.println("Please enter an integer between 1 and 4 inclusive.");
			while (input.length() != 1 || input.charAt(0) != '1' && input.charAt(0) != '2' && input.charAt(0) != '3' && input.charAt(0) != '4') {
				input = consoleInput.nextLine();
			}
		}
		startTime = System.currentTimeMillis();
		if (input.equals("1")) {
			inputArray = bubbleSort(inputArray);
		}
		else if (input.equals("2")) {
			inputArray = selectionSort(inputArray);
		}
		else if (input.equals("3")) {
			inputArray = tableSort(inputArray);
		}
		else if(input.equals("4")) {
			inputArray = quickSort(inputArray, 0, inputArray.length-1);
		}
		long totalTime = System.currentTimeMillis() - startTime;
		System.out.println("Total Time: " + totalTime);
		
		// Create output file.
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileWriter(new File("output.txt")));
			String output = "";
			for (int i = 0; i < inputArray.length; i++) {
				output += inputArray[i] + ",";
			}
			output += "\nTotal Time: " + totalTime;
			pw.write(output);
			pw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		for (int i = 0; i < inputArray.length; i++) {
			System.out.println(inputArray[i]);
		}
	}
	
	/*
	 *  Bubble Sort: Compare numbers next to each other and advance the larger number further. Keep repeating until sorted.
	 */
	int[] bubbleSort (int[] array) {
		for (int j = 0; j < array.length; j++) {
			for (int i = 0; i < array.length - 1; i++) {
				if (array[i] > array[i+1]) {
					// Sorting method with the comparing and swapping.
					int temp = array[i];
					array[i] = array[i+1];
					array[i+1] = temp;
				}
			}
		}
		return array;
	}
	
	/*
	 * Selection Sort: Select the smallest number from the set of numbers and move to the top of set. 
	 * Keep repeating excluding the already sorted portion.
	 */
	int[] selectionSort (int[] array) {
		for (int j = 0; j < array.length; j++) {
			int smallestNumber = array[j];
			int smallestIndex = j;
			for (int i = j; i < array.length; i++) {
				if (array[i] < smallestNumber) {
					smallestNumber = array[i];
					smallestIndex = i;
				}
			}
			int temp = array[smallestIndex];
			array[smallestIndex] = array[j];
			array[j] = temp;
		}
		return array;
	}
	
	/*
	 * Table Sort: Tallies instances of numbers and adds up the tallies from left to right like a Fibonacci sequence.
	 * Creates a new array, with number of indices equal to the input, based on the tallies the number has. 
	 * After placing the number, decrease its tally by 1. Repeat until sorted.
	 */
	int[] tableSort (int[] array) {
		int[] tally = new int[1001];
		for (int i = 0; i < array.length; i++) {
			tally[array[i]]++;
		}
		int count = 0;
		// Variable i is the value of the actual number
		for (int i = 0; i < tally.length; i++) {
			// Variable j is the number of occurrences of a number
			for (int j = 0; j < tally[i]; j++) {
				array[count] = i;
				count++;
			}
		}
		return array;
	}
	/*
	 * Quick Sort: Chooses rightmost element as a "pivot" for the array. The elements less than the pivot will be put to the left of
	 * the pivot, not sorted, and the elements greater than the pivot will be put to the right of the pivot, also not sorted. The pivot
	 * will then be ignored, as it is already sorted, dividing the array into two subarrays. The same process will be repeated with the 
	 * subarrays, with the partitioning with the leftmost element, until the array is sorted.
	 * Reference Code: Python Tutors
	 */
	int[] quickSort (int[] array, int low, int high) {
		if (low < high) {
			int pivotIndex = low;
			int boundary = low + 1;
			for (int a = pivotIndex + 1; a <= high; a++) {
				if(array[a] < array[pivotIndex]) {
					int temp = array[a];
					array[a] = array[boundary];
					array[boundary] = temp;
					boundary++;
				}
			}
			int temp = array[boundary-1];
			array[boundary-1] = array[pivotIndex];
			array[pivotIndex] = temp;
			pivotIndex = boundary - 1;
			quickSort(array, low, pivotIndex - 1);
			quickSort(array, pivotIndex + 1, high);
		}
		return array;
	}
	
	public static void main(String[] args) {
		new Sorting();

	}

}
