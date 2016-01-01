/*Critter World by-
 * <Rohan Tripathi>
 */

package main;

import java.util.List;

import main.Params;
import main.Critter.TestCritter;

public class Algae extends TestCritter {

	public String toString() { return "@"; }
	
	public boolean fight(String not_used) { return false; }
	
	public void doTimeStep() {
		setEnergy(getEnergy() + Params.photosynthesis_energy_amount);
	}
	
	public static String runStats(List<Critter> algae){    //Prints Algae stats
	   	 String num = ""+algae.size();
	   	 return num;
	    }


	@Override
    public javafx.scene.paint.Color viewFillColor() { 
		return javafx.scene.paint.Color.GREEN; 
	}
	
    @Override
	public javafx.scene.paint.Color viewOutlineColor() { 
		return javafx.scene.paint.Color.WHITE; 
	}
	
    @Override
	public CritterShape viewShape(){
		return Critter.CritterShape.CIRCLE;
	}
}
