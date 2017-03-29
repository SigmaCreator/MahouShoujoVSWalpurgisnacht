package Game;

public class Oktavia extends Character {
	//@ requires lin>=0 && lin<Field.TAMLC;
		//@ requires col>=0 && col<Field.TAMLC;
		/*@ ensures super.getName().equals("Bebe")&&
				isMahouShoujo() == true &&
		 		getLin()==lin &&
		 		getCol()==col &&
		 		getHB()==100 &&
		 		getRange()==5 &&
		 		getCode()==4;
		 @*/
	public Oktavia(int lin, int col){
		super("SayakaWitch", false, lin, col);
		setHB(100);
		setRange(5);
		setCode(4);
	}
}
