/*package PlaygroundPackage;

import java.util.Scanner;

public class Player 
{
	//formula: (Math.random() * (max(15) - min (10) + 1) + min)
	int HP = (int) (Math.random() * (6)) + 10;
	int STR = (int) 100;
	
	private static Scanner scanner = new Scanner(System.in);
	
	public static String scanPlayerName()
	{
		System.out.println("Player name: ");
		String playerName = scanner.next();
		scanner.nextLine();
		return playerName;
	}	
	
	public static int attackPhase()
	{
		String[] attackList = 
		{
			"Drop Spirit Bomb",
			"Do a Barrel Roll",
			"Insult Mother",
			"Shoot Fireball"
		};
		
		int attackChooser = (int) (Math.random() * (attackList.length));
		String chosenAttack = attackList[attackChooser];
		

		System.out.println("Type the word to attack! Faster = more damage\n");
		System.out.println("Type: " + chosenAttack + "\nType here: ");
		
		long startTime = System.currentTimeMillis();
		String playerInput = scanner.nextLine();
		
		long endTime = System.currentTimeMillis();
		
		int finalDamageValue = 0;
		if (playerInput.equals(chosenAttack))
		{
			finalDamageValue = (int) ((endTime - startTime) / 100);
			System.out.println("Success!");
			return finalDamageValue;
		}
		
		else
		{
			System.out.println("Failure! ");
			return finalDamageValue;
		}	
	}
	
	public static void waitPhase()
	{
		System.out.println("You cautiously assess the situation. Press Enter to continue.");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		scanner.close();
	}
	
	public int getHP()
	{
		return HP;
	}
	
	public void setHP(int HP)
	{
		this.HP = HP;
	}
	
	public int getSTR()
	{
		return STR;
	}
	public static void scannerClose()
	{
		scanner.close();
	}

}

*/

package PlaygroundPackage;

import java.util.Scanner;

public class Player 
{
	//Default Constructor
	public Player() {
	}
	
	//formula: (Math.random() * (max(15) - min (10) + 1) + min)
	int HP = (int) (Math.random() * (6)) + 10;
	int STR = 100;
	
	public static Scanner scanner = new Scanner(System.in);
	
	public static String scanPlayerName()
	{
		System.out.println("Player name: ");
		String playerName = scanner.next();
		scanner.nextLine();
		return playerName;
	}	
	
	public static int attackPhase()
	{
		String[] attackList = 
		{
			"Drop Spirit Bomb",
			"Do a Barrel Roll",
			"Insult Mother",
			"Shoot Fireball"
		};
		
		//takes random 0-1 number and multiplies by array length, and then truncates the decimal when converting to int.
		int attackChooser = (int) ((Math.random() * (attackList.length)));		
		String chosenAttack = attackList[attackChooser];
		

		System.out.println("Type the word to attack! Faster = more damage\n");
		System.out.println("Type: " + chosenAttack + "\nType here: ");
		
		//begin timer, start scan, end timer
		long startTime = System.currentTimeMillis();
		String playerInput = scanner.nextLine();
		
		long endTime = System.currentTimeMillis();
		
		//initialize finalDamageValue each time
		int finalDamageValue = 0;
		
		//type accuracy check
		if (playerInput.equals(chosenAttack))
		{
			finalDamageValue = (int) ((endTime - startTime) / 100);
			System.out.println("Success!");
			return finalDamageValue;
		}
		
		else
		{
			System.out.println("Failure! ");
			return finalDamageValue;
		}	
	}
	
	//Give players a breather before continuing
	public static void waitPhase()
	{
		System.out.println("You cautiously assess the situation. Press Enter to continue.");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
	}
	
	public int getHP()
	{
		return HP;
	}
	
	public void setHP(int HP)
	{
		this.HP = HP;
	}
	
	public int getSTR()
	{
		return STR;
	}
	public static void scannerClose()
	{
		scanner.close();
	}

}
