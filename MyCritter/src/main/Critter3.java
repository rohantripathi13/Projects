/* CRITTERS Critter3.java
 *Critter World by-
 * <Rohan Tripathi>
 */

/* Critter3 is a Critter subclass represented by the symbol '$' and will fight any critter that does not look like it.
 * If it's energy is greater than 140, then it will attempt to reproduce.*/


package main;

import java.util.List;

public class Critter3 extends Critter.TestCritter {
	private int dir;
	
	@Override
	public String toString() { return "$"; }	//Returns the string map representation of Critter3

	@Override
	public void doTimeStep() {	//doTimeStep method for Critter3
   	 String lookString = look(dir, true);
   	 for (int n = 0; n < 8; n+=2) { // Look for algae
   		 if (lookString != null) {
   			 if (!(lookString.equals("@"))) {
   				 dir = Critter.getRandomInt(8); // Set direction
   			 }
   			 else {
   				 break;
   			 }
   		 }
   		 dir = Critter.getRandomInt(8); // Set direction
   		 lookString = look(dir, false);
   	 }
  	  run(dir);	//Critter3 runs
  	  if (getEnergy() > 140) {	//Attempt to reproduce if energy levels are greater than 140
  		  Critter3 child3 = new Critter3();
  		  reproduce(child3, Critter.getRandomInt(8));
  	  }
	}

	@Override
	public boolean fight(String oponent) {	//Critter3 fights any critter that does not look like it
		if((oponent.equals("$"))){
			return false;
		}
		else{
			return true;
		}
	}
	
	public static String runStats(List<Critter> critters){    //Prints Critter3 stats
	   	 String num = ""+critters.size();
	   	 return num;
	    }

	
	@Override
    public javafx.scene.paint.Color viewFillColor() { 
		return javafx.scene.paint.Color.YELLOW; 
	}
	
    @Override
	public javafx.scene.paint.Color viewOutlineColor() { 
		return javafx.scene.paint.Color.DARKOLIVEGREEN; 
	}
	
    @Override
	public CritterShape viewShape(){
		return Critter.CritterShape.DIAMOND;
	}
}
