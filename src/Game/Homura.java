package Game;

public class Homura extends Character{
	//@ requires lin>=0 && lin<Field.TAMLC;
	//@ requires col>=0 && col<Field.TAMLC;
	/*@ ensures super.getName().equals("Bebe")&&
			isMahouShoujo() == true &&
	 		getLin()==lin &&
	 		getCol()==col &&
	 		getHB()==150 &&
	 		getPB()==100 &&
	 		getRange()==3 &&
	 		getCode()==2;
	 @*/
	public Homura(int lin, int col){
		super("Homura", true, lin, col);
		setHB(150);
		setPB(100);
		setRange(3);
		setCode(2);
	}
	
}
