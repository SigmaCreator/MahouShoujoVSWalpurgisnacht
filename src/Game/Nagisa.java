package Game;

public class Nagisa extends Character {
	
	//@ requires lin>=0 && lin<Field.TAMLC;
	//@ requires col>=0 && col<Field.TAMLC;
	/*@ ensures super.getName().equals("Bebe")&&
			isMahouShoujo() == true &&
	 		getLin()==lin &&
	 		getCol()==col &&
	 		getPB()==90 &&
	 		getRange()==3 &&
	 		getCode()==4;
	 @*/
	
	public Nagisa(int lin, int col){
		super("Bebe", true, lin, col);
		setPB(90);
		setRange(3);
		setCode(4);
	}
}
