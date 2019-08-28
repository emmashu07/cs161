import java.util.Scanner;

public class GuessingGame {
	
	public GuessingGame()
	{
		boolean stillPlaying = true;
		
		Scanner scanner = new Scanner (System.in);
		while (stillPlaying) // Every iteration is a new game.
		{
			int number = (int)(Math.random() * 51); // 51 because Math.random() will be always less than one and integer casting will truncate the number.
			System.out.println("Welcome to the Guessing Game. To begin, please enter a number between 0 and 50: ");
			int guess = -1; // Number will never be equal to -1.
			int count = 0;
			while (number != guess)
			{
				String input = scanner.nextLine();
				guess = Integer.parseInt(input); // Converting input string into integer.
				if (number > guess)
				{
					System.out.println("Your guess is too low, please try again: ");
				}
				else if (number < guess)
				{
					System.out.println("Your guess is too high, please try again: ");
				}
				count++; // Add one to count every guess.
			}
			System.out.println("You are correct!");
			System.out.println("It took you " + count + " guesses.");
			System.out.println("Continue playing? (y or n): ");		
			String yesno = scanner.nextLine();
			if (yesno.equals("n")) // stillPlaying will already be true, so if keep playing, they don't need to change.
			{
				stillPlaying = false;
			}
		}
		scanner.close();
	}

	public static void main(String[] args) {
		new GuessingGame();
	}

}