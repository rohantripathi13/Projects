/* CRITTERS Critter4.java
 *Critter World by-
 * <Rohan Tripathi>
 */


/* Critter4 is a Critter subclass represented by the symbol '#' and will walk away from all critters except algae.
 * If it's energy is greater than 180, then it will attempt to reproduce.
 * It will not move unless it encounters another critter that is not an algae.*/

package main;

import java.util.List;

public class Critter4 extends Critter {
	private int dir;

	@Override
	public String toString() { return "#"; }	//Returns string map representation of Critter4

	@Override
	public void doTimeStep() {	//doTimeStep method for Critter4
		if (getEnergy() > 135) {	//Reproduce if energy levels are greater than 180
			Critter4 child4 = new Critter4();
			reproduce(child4, Critter.getRandomInt(8));
		}
	}

	@Override
	public boolean fight(String oponent) {	//Critter4 will run away from all fights but stay for Algae
   	 String lookString = look(dir, true);
   	 for (int n = 0; n < 8; n+=2) { // Run away for all fights
   		 if (lookString != null) {
   			 dir = Critter.getRandomInt(8); // Set direction
   		 }
   		 else {
   			 break;
   		 }
   		 lookString = look(dir, true);
   	 }
  	  if(oponent.equals("@")){    //Stay for Algae
  		  return false;
  	  }
  	  run(dir);
  	  return false;
	}
	
	public static String runStats(List<Critter> critters){   //Prints Critter4 stats
	   	 String num = ""+critters.size();
	   	 return num;
	    }

	@Override
    public javafx.scene.paint.Color viewFillColor() { 
		return javafx.scene.paint.Color.PINK; 
	}
	
    @Override
	public javafx.scene.paint.Color viewOutlineColor() { 
		return javafx.scene.paint.Color.MAROON; 
		}
	
    @Override
	public CritterShape viewShape(){
		return Critter.CritterShape.DIAMOND;
	}
}
