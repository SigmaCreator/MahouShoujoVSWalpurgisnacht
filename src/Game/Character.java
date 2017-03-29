package Game;

public abstract class Character {
	private /*@ spec_public @*/ String name;
	private /*@ spec_public @*/ int purityBar;
	private /*@ spec_public @*/ int healthBar;
	private /*@ spec_public @*/ int range;
	private /*@ spec_public @*/ boolean isMS;
	private /*@ spec_public @*/ int lin, col, code;
	
	public /*@ pure @*/ int getCode() {
		return code;
	}

	//@ requires code>0;
	//@ ensures getCode() == code;
	public void setCode(int code) {
		this.code = code;
	}

	public /*@ pure @*/ int getLin() {
		return lin;
	}

	//@ requires lin>=0 && lin<Field.TAMLC;
	//@ ensures getLin() == lin;
	public void setLin(int lin) {
		this.lin = lin;
	}

	public /*@ pure @*/ int getCol() {
		return col;
	}

	//@ requires col>=0 && col<Field.TAMLC;
	//@ ensures getCol() == col;
	public void setCol(int col) {
		this.col = col;
	}

	
	//@ requires name!=null;
	//@ requires lin>=0 && lin<Field.TAMLC;
	//@ requires col>=0 && col<Field.TAMLC;
	//@ ensures healthBar == 100;
	public Character(String name, boolean isMS, int lin, int col){
		healthBar = 100;
		this.name = name;
		this.isMS = isMS;
		this.lin = lin;
		this.col = col;
	}
	
	//@ requires i>0;
	//@ ensures \old(purityBar) < purityBar <== isMS == true;
	public void purityBarRegen(int i){
		if(isMS)purityBar+=i;
	}
	
	//@ requires i>0;
	//@ ensures getRange() == i;
	public void setRange(int i){
		range = i;
	}
	
	//@ requires i>=0;
	//@ ensures getPB() == i;
	public void setPB(int i){
		purityBar = i;
	}
	
	
	public /*@ pure @*/ String getName(){
		return name;
	}
	
	public /*@ pure @*/ boolean isAlive(){
		return healthBar>0;
	}
	
	public /*@ pure @*/ int getPB(){
		return purityBar;
	}
	
	public /*@ pure @*/ boolean isMahouShoujo(){
		return isMS;
	}
	
	public /*@ pure @*/ int getHB(){
		return healthBar;
	}
	
	public /*@ pure @*/ int getRange(){
		return range;
	}
	
	
	//@ requires h>=0;
	//@ ensures getHB()==h;
	public void setHB(int h){
		healthBar = h;
	}
	
	//@ requires i>0;
	//@ ensures \old(healthBar) > healthBar;
	public void damageTaken(int i){
		healthBar-=i;
	}

	
	
	//@ requires c!=null;
	//@ ensures \old(c.getHB()) == c.getHB()+10;
	//@ ensures getPB() == \old(getPB())-10 <== isMS ==true;
	public void normalAttack(Character c){
		c.damageTaken(10);
		if(isMahouShoujo()){
			purityBar-=10;
		}
	}
	
	
}
