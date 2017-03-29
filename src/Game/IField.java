package Game;

import java.io.File;

public interface IField {
	
	//@ requires nWitches >= 0;
    //@ requires nMahou >= 0;
	void generateSimStart(int nWitches, int nMahou);
	
	//@ requires nWitches >= 0;
    //@ requires nMahou >= 0;
	void restartSimulation(int nWitches, int nMahou);
	
	//@ requires times > 0;
	void forwardSimulation(int times);
	
	//@ requires c != null;
	boolean executeAttack(Character c);
	
	//@ requires c != null;
	void moveCharacter(Character c);
	
	//@ requires lin >= 0 && lin < Field.TAMLC;
	//@ requires col >= 0 && col < Field.TAMLC;
	boolean isOcupied(int lin, int col);
	
	//@ requires lin >= 0 && lin < Field.TAMLC;
	//@ requires col >= 0 && col < Field.TAMLC;
	Character getChara( int lin, int col);
	
	//@ requires fileName != null;
	boolean saveSimulation(String fileName);
	
	//@ requires f != null;
	boolean loadSimulation(File f);
}
