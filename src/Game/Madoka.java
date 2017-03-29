package Game;

public class Madoka extends Character{
	//@ requires lin>=0 && lin<Field.TAMLC;
		//@ requires col>=0 && col<Field.TAMLC;
		/*@ ensures super.getName().equals("Bebe")&&
				isMahouShoujo() == true &&
		 		getLin()==lin &&
		 		getCol()==col &&
		 		getPB()==200 &&
		 		getRange()==5 &&
		 		getCode()==1;
		 @*/
	public Madoka(int lin, int col){
		super("Madoka", true, lin, col);
		setPB(200);
		setRange(5);
		setCode(1);
	}
	
	
}
