/* CRITTERS Critter2.java
 *Critter World by-
 * <Rohan Tripathi>
 */

/* Critter2 is a Critter subclass representing another kind of critter. This critter is represented by the alphabet 'B' and chooses to walk
 * in a random direction. This critter will reproduce asexually if its current energy level is greater than 150.
 * This critter chooses to not fight any of the Critters */

package main;

import java.util.List;

public class Critter2 extends Critter {
    
    @Override
    public String toString() { return "B"; }	// method to return the String representing Critter2
    private int dir;

    @Override
    public void doTimeStep() {	// perform time step
        dir=Critter.getRandomInt(8);	//assign a random value of direction, between 0(inclusive) and 8 to dir
        walk(dir);	// walk one step in the direction
            if (getEnergy() > 150) {	// Check if the energy of Critter is greater than 150
                Critter2 child2 = new Critter2();	// create a child object for Critter1 if its energy is greater than 150
                reproduce(child2, Critter.getRandomInt(8));	// reproduce method called using the child object and assigning a random direction to it.
            }
        }

    @Override
    public boolean fight(String not_used) {	//method to determine if the Critter will fight other critters on encounter
        return true;
    }
    
    public static String runStats(List<Critter> critters){    //Prints Critter2 stats
      	 String num = ""+critters.size();
      	 return num;
       }

    
    @Override
    public javafx.scene.paint.Color viewFillColor() { 
		return javafx.scene.paint.Color.RED; 
	}
	
    @Override
	public javafx.scene.paint.Color viewOutlineColor() { 
		return javafx.scene.paint.Color.BLACK; 
		}
	
    @Override
	public CritterShape viewShape(){
		return Critter.CritterShape.SQUARE;
	}
}
