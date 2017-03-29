package Game;

public class Walpurgisnacht extends Character {
	//@ requires lin>=0 && lin<Field.TAMLC;
		//@ requires col>=0 && col<Field.TAMLC;
		/*@ ensures super.getName().equals("Bebe")&&
				isMahouShoujo() == true &&
		 		getLin()==lin &&
		 		getCol()==col &&
		 		getHB()==250 &&
		 		getRange()==4 &&
		 		getCode()==8;
		 @*/
	public Walpurgisnacht(int lin, int col){
		super("Walpurgisnacht", false, lin, col);
		setHB(250);
		setRange(4);
		setCode(8);
	}
}
