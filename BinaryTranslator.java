import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class BinaryTranslator {
	
	public BinaryTranslator()
	{
		Scanner scanner = new Scanner (System.in);
		System.out.println("Please enter \"file\" if entering a file or \"input\" if using the console: "); // Backslash as an escape key.
		String input = scanner.nextLine();
		String numberInput = "";
		
		if (input.equals("file"))
		{
			try // To try the following code to find whether the file exists.
			{
				System.out.println("Please enter your file name: ");
				input = scanner.nextLine();
				Scanner fileScanner = new Scanner(new File(input)); 
				numberInput = fileScanner.nextLine();
				fileScanner.close();
			} 
			catch (IOException ex) // If file name is not found, display the following line.
			{
				System.out.println("File not found.");
				scanner.close();
				System.exit(1);
			}
		}
		else if (input.equals("input")) // For console input.
		{
			System.out.println("Please enter your number: ");
			numberInput = scanner.nextLine();
			int beginIndex = 0;
			for (int i = 0; numberInput.charAt(i) == '0'; i++)
			{
				beginIndex = i;
			}
			numberInput = numberInput.substring(beginIndex, numberInput.length());
		}
		System.out.println("If you are translating from decimal to binary, enter \"dtb\" below.");
		System.out.println("If you are translating from binary to decimal, enter \"btd\" below.");
		input = scanner.nextLine();
		if (input.equals("dtb"))
		{
			String answer = "";
			int number = Integer.parseInt(numberInput);
			while (number > 0)
			{
				if (number % 2 == 1)
				{
					answer += "1"; 
				}
				else
				{
					answer += "0";
				}
				number /= 2;
			}
			System.out.println("Your binary number is: " + new StringBuilder(answer).reverse().toString());
		}
		else if (input.equals("btd"))
		{
			int answer = 0;
			for (int a = numberInput.length() - 1; a >= 0; a--) // Subtract 1 from length because it is zero base indexing.
			{
				if (numberInput.charAt(a) == '1')
				{
					answer += (int)(Math.pow(2, numberInput.length() - 1 - a)); // Reverse reading indices.
				}
			}
			System.out.println("Your decimal number is: " + answer);
		}
		scanner.close();
	}

	public static void main(String[] args) { 
		
		new BinaryTranslator();

	}

}