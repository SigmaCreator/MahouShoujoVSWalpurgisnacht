package Game;

public class NormalWitch extends Character {
	//@ requires lin>=0 && lin<Field.TAMLC;
		//@ requires col>=0 && col<Field.TAMLC;
		/*@ ensures super.getName().equals("Bebe")&&
				isMahouShoujo() == true &&
		 		getLin()==lin &&
		 		getCol()==col &&
		 		getRange()==3 &&
		 		getCode()==7;
		 @*/
	public NormalWitch(int lin, int col){
		super("NormalWitch", false, lin, col);
		setRange(3);
		setCode(7);
	}

	
}
