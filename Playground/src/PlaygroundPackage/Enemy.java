package PlaygroundPackage;

import java.util.ArrayList;
import java.util.List;

public class Enemy 
{
	public Enemy () {
		
	}
	
	//random equation: (Math.random() * (max (500) - min (400) + 1)) + min
	private int enemyHP = (int) ((Math.random() * 101) + 400);
	private int enemySTR = 1;
	
	public String randomNameGen() 
	{
		List<String> enemyNames = new ArrayList<>();
		enemyNames.add("Snugglemuffin, the Destroyer of All Worlds");
		enemyNames.add("Mr. Tumnus, Harbringer Eternal");
		enemyNames.add("Chuckles, Reaper of the Seventh Void");
		enemyNames.add("Stanley, IT Intern from \"The Beyond\".");
		
		//randomly choose a name
	    int nameChooser = (int) (Math.random() * enemyNames.size());	
		String generatedName = enemyNames.get(nameChooser);
		
		return generatedName;
	}
	
	public int getHP()
	{
		return enemyHP;
	}
	public void setHP(int enemyHP) 
	{
		this.enemyHP = enemyHP;
	}
		
	//Roll enemy's damage from 1 to 3	
	public int enemyAttackRoll()
	{
		//formula: Math.random * ((max (3) - min (1)) + 1
		int attack = enemySTR * ((int) (Math.random() * (3) + 1));
		return attack;
	}
}
