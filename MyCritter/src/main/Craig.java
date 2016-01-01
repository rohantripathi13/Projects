/*Critter World by-
 * <Rohan Tripathi>
 */

package main;

import main.Craig;
import main.Critter;

public class Craig extends Critter {
	
	@Override
	public String toString() { return "C"; }
	
	private static final int GENE_TOTAL = 24;
	private int[] genes = new int[8];
	private int dir;
	
	public Craig() {
		for (int k = 0; k < 8; k += 1) {
			genes[k] = GENE_TOTAL / 8;
		}
		dir = Critter.getRandomInt(8);
	}
	
	public boolean fight(String not_used) { return true; }

	@Override
	public void doTimeStep() {
		/* take one step forward */
		walk(dir);
		
		if (getEnergy() > 150) {
			Craig child = new Craig();
			for (int k = 0; k < 8; k += 1) {
				child.genes[k] = this.genes[k];
			}
			int g = Critter.getRandomInt(8);
			while (child.genes[g] == 0) {
				g = Critter.getRandomInt(8);
			}
			child.genes[g] -= 1;
			g = Critter.getRandomInt(8);
			child.genes[g] += 1;
			reproduce(child, Critter.getRandomInt(8));
		}
		
		/* pick a new direction based on our genes */
		int roll = Critter.getRandomInt(GENE_TOTAL);
		int turn = 0;
		while (genes[turn] <= roll) {
			roll = roll - genes[turn];
			turn = turn + 1;
		}
		assert(turn < 8);
		
		dir = (dir + turn) % 8;
	}

	public static String runStats(java.util.List<Critter> craigs) {
		   	 String num = ""+craigs.size();
		   	 return num;
		    }

	@Override
    public javafx.scene.paint.Color viewFillColor() { 
		return javafx.scene.paint.Color.AQUA; 
	}
	
    @Override
	public javafx.scene.paint.Color viewOutlineColor() { 
		return javafx.scene.paint.Color.VIOLET; 
		}
	
    @Override
	public CritterShape viewShape(){
		return Critter.CritterShape.TRIANGLE;
	}
}
