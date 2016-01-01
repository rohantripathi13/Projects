/* CRITTERS Critter1.java
 *Critter World by-
 * <Rohan Tripathi>
 */

/* Critter1 is a Critter subclass representing another kind of critter. This critter is represented by the alphabet 'A' and chooses to walk
 * in a random direction. This critter will reproduce asexually if its current energy level is greater than 170.
 * This critter chooses to fight all the other Critters including its own type of critters. */

package main;

import java.util.List;

public class Critter1 extends Critter{
    private int dir;
    
    @Override
    public String toString() { return "A"; }	// method to return the String representing Critter1
    
    @Override
    public void doTimeStep() {	// perform time step
        dir=Critter.getRandomInt(8);	//assign a random value of direction, between 0(inclusive) and 8 to dir
        walk(dir);	// walk one step in the direction
        
        if (getEnergy() > 170) {	// Check if the energy of Critter is greater than 170
            Critter1 child1 = new Critter1();	// create a child object for Critter1 if its energy is greater than 170
            reproduce(child1, Critter.getRandomInt(8));	// reproduce method called using the child object and assigning a random direction to it.
        }
    }

    @Override
    public boolean fight(String not_used) {	//method to determine if the Critter will fight other critters on encounter
        return true;
    }
    
    public static String runStats(List<Critter> critters){    //Prints Critter1 stats
      	 String num = ""+critters.size();
      	 return num;
       }

    
    @Override
    public javafx.scene.paint.Color viewFillColor() { 
		return javafx.scene.paint.Color.LIGHTGREEN; 
	}
	
    @Override
	public javafx.scene.paint.Color viewOutlineColor() { 
		return javafx.scene.paint.Color.DARKBLUE; 
		}
	
    @Override
	public CritterShape viewShape(){
		return Critter.CritterShape.STAR;
	}
}
