package Game;

public class Charlotte extends Character {
	//@ requires lin>=0 && lin<Field.TAMLC;
		//@ requires col>=0 && col<Field.TAMLC;
		/*@ ensures super.getName().equals("Bebe")&&
				isMahouShoujo() == true &&
		 		getLin()==lin &&
		 		getCol()==col &&
		 		getPB()==150 &&
		 		getRange()==3 &&
		 		getCode()==10;
		 @*/
	public Charlotte(int lin, int col){
		super("BebeWitch", false, lin, col);
		setHB(150);
		setRange(3);
		setCode(10);
	}
}
