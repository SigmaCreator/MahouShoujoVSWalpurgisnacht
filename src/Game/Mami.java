package Game;

public class Mami extends Character{
	//@ requires lin>=0 && lin<Field.TAMLC;
		//@ requires col>=0 && col<Field.TAMLC;
		/*@ ensures super.getName().equals("Bebe")&&
				isMahouShoujo() == true &&
		 		getLin()==lin &&
		 		getCol()==col &&
		 		getPB()==100 &&
		 		getRange()==4 &&
		 		getCode()==6;
		 @*/
	public Mami(int lin, int col){
		super("Mami", true, lin, col);
		setPB(100);
		setRange(4);
		setCode(6);
	}
	
	
	
}
