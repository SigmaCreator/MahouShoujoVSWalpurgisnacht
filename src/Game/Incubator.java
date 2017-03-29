package Game;

public class Incubator {
private /*@ spec_public @*/static Incubator sm = null;
	
	private Incubator(){
	}
	
	//@ ensures \old(sm) == sm <== sm!=null;
	public static Incubator getInstance(){
		if (sm == null){
			sm = new Incubator();
		}
		return(sm);
	}
	
	/*@ requires n>=1 && n<=10 &&
	  	row<=0 && row<Field.TAMLC &&
	  	col<=0 && row<Field.TAMLC; @*/
    public Character createInstance(int n, int row, int col){
    	switch(n){
    	    case 1: return new Madoka(row, col);
    	    case 2: return new Homura(row, col);
    	    case 3: return new Sayaka(row, col);
    	    case 4: return new Nagisa(row, col);
    	    case 5: return new Kyoko(row, col);
    	    case 6: return new Mami(row, col);
    	    case 7: return new NormalWitch(row, col);
    	    case 8: return new Walpurgisnacht(row, col);
    	    case 9: return new Oktavia(row, col);
    	    case 10: return new Charlotte(row, col);
    	}
    	return null;
    }
}
