package PlaygroundPackage;
import java.util.Random.*;
import java.util.*;
import java.lang.*;

public class RPG {
	
	public static void main(String[] args) {
		
		//greeting
		System.out.println("Welcome to Typing Hero RPG. \nYou are a typing hero with a quick trigger reflex, summoned from another world to save it from utter destruction.\n");
		

		
		//explain rules
		System.out.println("You will fight a random boss.\nType the given attack quickly to do as much damage as possible!\n");
		
		//create instance of class
		Player player = new Player();		
		
		//stuff player name into variable
		String name = Player.scanPlayerName();
		System.out.println("Player name: " + name);
		System.out.println("Here is your HP stat: ");
		
		//get HP value
		int playerHP = player.getHP();
		//get STR value for turn loop
		int playerSTR = player.getSTR();
		System.out.println("HP: " + playerHP);
		
		//get random enemy
		Enemy enemy = new Enemy();
		System.out.print("You are facing: ");
		String enemyName = enemy.randomNameGen();
		System.out.println(enemyName + "\n");
		
		int enemyHP = enemy.getHP();
		System.out.println("Enemy HP: " + enemyHP + "\n");
		
		//loop turns
		do 
		{
			//wait phase for each turn
			Player.waitPhase();
			int damageMult = Player.attackPhase();
			int damage;
			
			//do 0 damage if attack misses
			if (damageMult == 0)
			{
				System.out.println("XXXXXXXXXXXXXX\nXXXXXXXXXXXXXX\n");
				damage = 0;
			}
			else
			{
				System.out.println("**************\n**************\n");
				damage = playerSTR - damageMult;
				
				//commentator flavor reactions
				if (damage < 30)
				{
					System.out.println("Glancing hit! You'll have to be faster next time.");
				}
				else if ((damage > 30) && (damage < 60))
				{
					System.out.println("Solid blow!");
				}
				else
				{
					System.out.println("Looks like that one hurt!");
				}
			}
			
			//subtract damage from enemyHP
			enemyHP -= damage;
			
			//if enemyHP drops below 0, set to 0
			if (enemyHP < 0)
			{
				enemyHP = 0;
			}
			enemy.setHP(enemyHP);
			System.out.println("You did " + damage + "DMG\nEnemy has " + enemy.getHP() + " HP left.\n");
			
			//if statement for when enemy is defeated
			if (enemyHP == 0)
			{
				break;
			}
			
			//Enemy attack commence
			if (enemyHP > 0)
			{
				int enemyAttack = enemy.enemyAttackRoll();
				System.out.println("Enemy hits for " + enemyAttack + ".");
				playerHP -= enemyAttack;
				//if playerHP drops below 0, set to 0
				if (playerHP < 0)
				{
					playerHP = 0;
				}
				player.setHP(playerHP);
				System.out.println("You have " + playerHP + " HP left.\n");
				
				
				//commence end prompt if player is defeated
				if (playerHP == 0)
				{
					break;
				}
				
				//Warn player if critically injured
				if (playerHP < 4)
				{
					System.out.println("Things are not looking good...");
				}
			}
		}
		
		while (playerHP != 0 || enemyHP != 0);
		
		//win checker
		Player.scannerClose();
		if (playerHP == 0)
		{
			System.out.println("You have been vanquished and the world has been vaporized.");
		}
		if (enemyHP == 0)
		{
			System.out.println("You have vanquished your foe. Huzzah!");
		}
	}
	
}
