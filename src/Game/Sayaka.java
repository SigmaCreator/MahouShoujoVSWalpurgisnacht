package Game;

public class Sayaka extends Character {
	//@ requires lin>=0 && lin<Field.TAMLC;
		//@ requires col>=0 && col<Field.TAMLC;
		/*@ ensures super.getName().equals("Bebe")&&
				isMahouShoujo() == true &&
		 		getLin()==lin &&
		 		getCol()==col &&
		 		getHB()==200 &&
		 		getPB()==100 &&
		 		getRange()==1 &&
		 		getCode()==3;
		 @*/
	public Sayaka(int lin, int col){
		super("Sayaka", true, lin, col);
		setHB(200);
		setPB(100);
		setRange(1);
		setCode(3);
	}
}
