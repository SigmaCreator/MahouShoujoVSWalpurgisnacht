package Game;

public class Kyoko extends Character {
	//@ requires lin>=0 && lin<Field.TAMLC;
		//@ requires col>=0 && col<Field.TAMLC;
		/*@ ensures super.getName().equals("Bebe")&&
				isMahouShoujo() == true &&
		 		getLin()==lin &&
		 		getCol()==col &&
		 		getHB()==150 &&
		 		getPB()==100 &&
		 		getRange()==1 &&
		 		getCode()==5;
		 @*/
	public Kyoko(int lin, int col){
		super("Kyoko", true, lin, col);
		setHB(150);
		setPB(100);
		setRange(2);
		setCode(5);
	}
	

}
