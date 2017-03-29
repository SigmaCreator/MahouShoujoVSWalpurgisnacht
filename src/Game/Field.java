package Game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Observable;

public class Field extends Observable implements IField {
    public static /*@ spec_public @*/ int TAMLC = 20;
    private /*@ spec_public @*/ Character field[][];
    private /*@ spec_public @*/ ArrayList<Character> mahou, witch;
    private /*@ spec_public @*/static Field edj = null;
    private /*@ spec_public @*/ Incubator kyubey = Incubator.getInstance();
	private BufferedReader br;
    
    private Field(){
    	field = new Character[TAMLC][TAMLC];
    	mahou = new ArrayList<Character>();
    	witch = new ArrayList<Character>();
    }
    
  //@ ensures \old(edj) == edj <== edj!=null;
    public static Field getInstance(){
    	if (edj == null){
    		edj = new Field();
    	}
        return(edj);
    }
    
    public /*@ pure @*/ int getQuantityMahou(){
    	return mahou.size();
    }
    
    public /*@ pure @*/ int getQuantityWitch(){
    	return witch.size();
    }
    
    //@ ensures mahou.size()==0 && witch.size()==0;
    //@ ensures field !=null;
    @Override
	public void restartSimulation(int nWitches, int nMahou){
    	field = new Character[TAMLC][TAMLC];
    	mahou = new ArrayList<Character>();
    	witch = new ArrayList<Character>();
    	generateSimStart(nWitches, nMahou);
    }
    
    /*@ ensures mahou.size()>=6 && witch.size()>=1 && witch.size()<=100 && mahou.size()<=100;
    	also
    	ensures mahou.size()==nMahou && witch.size()==nWitches;
    @*/
    @Override
	public void generateSimStart(int nWitches, int nMahou){
    	if(nWitches>100) nWitches = 100;
    	if(nMahou> 100) nMahou = 100;
		field[0][0] = kyubey.createInstance(1, 0, 0);
		mahou.add(field[0][0]);
		field[1][1] = kyubey.createInstance(2, 1, 1);
		mahou.add(field[1][1]);
		field[0][1] = kyubey.createInstance(3, 0, 1);
		mahou.add(field[0][1]);
		field[1][0] = kyubey.createInstance(4, 1, 0);
		mahou.add(field[1][0]);
		field[0][2] = kyubey.createInstance(5, 0, 2);
		mahou.add(field[0][2]);
		field[2][0] = kyubey.createInstance(6, 2, 0);
		mahou.add(field[2][0]);
		field[19][19] = kyubey.createInstance(8, 19, 19);
		witch.add(field[19][19]);
		if(nMahou>6){
			sortMahou(nMahou);
		}
		int qt =1, col, row;
		while(qt<nWitches){
			col = 2 + (int)(Math.random()*100)%18;
			row = 2 + (int)(Math.random()*100)%18;
			if(!isOcupied(row, col)&&(col+row>TAMLC)){
				field[row][col] = kyubey.createInstance(7, row, col);
				witch.add(field[row][col]);
				qt++;
			}
		}
		setChanged();
		notifyObservers();
    }
   
   /*@ ensures \old(c.getLin())!=c.getLin() &&
    			\old(c.getCol())!=c.getCol();
    @*/
    @Override
	public void moveCharacter(Character c){
    	int y=0, x=0, row = c.getLin(), col= c.getCol();
    	Character d;
    	if(c instanceof Madoka && (d=enemyClose(c))!=null){
    		int p = c.getLin() - d.getLin(), q = c.getCol()-d.getCol();
    		if(p>0)x=1;
    		if(p<0)x=-1;
    		if(q>0)y=1;
    		if(q<0)y=-1;
    	}else{
	    	double r = Math.random()*100;
	    	if(r<=25){
	    		if(row+1>=TAMLC)x=-1;
	    		else x=+1;
	    	}
	    	if(r>25 && r<=50){
	    		if((row-1)<0) x=+1;
	    		else x=-1;
	    	}
	    	if(r>50 && r<=75){
	    		if(col+1>=TAMLC) y=-1;
	    		else y=1;
	    	}
	    	if(r>75){
	    		if((col-1)<0)y=+1;
	    		else y=-1;
	    	}
	    	if(!isOcupied(row+x, col+y)){
		    	field[row+x][col+y] = field[row][col];
	    		field[row+x][col+y].setCol(col+y);
	    		field[row+x][col+y].setLin(row+x);
		    	if(c.isMahouShoujo()){
		    		mahou.remove(c);
		    		mahou.add(field[row+x][col+y]);
		    	}else{
		    		witch.remove(c);
		    		witch.add(field[row+x][col+y]);
		    	}
		    	field[row][col] = null;
	    	}
    	}
    }
    
    /*@ ensures \old(c.getPB())>c.getPB() <== c.isMahouShoujo() && enemyClose(c)!=null;
        also
        ensures \old(c.getPB())<c.getPB() <== c.isMahouShoujo() && !enemyClose(c).isAlive();
    @*/
    @Override
	public boolean executeAttack(Character c){
    	int col = c.getCol();
    	int row = c.getLin();
    	if(c!=null){
			Character d = enemyClose(c);
			if(d!=null){
				c.normalAttack(d);
				if(!d.isAlive()){
					field[d.getLin()][d.getCol()] = null;
					killCharacter(d);
					System.out.println(c.getName()+" matou "+d.getName()+"!");
					c.purityBarRegen(20);
					if(d instanceof Madoka) throw new GameOverException("Fim de jogo. Madoka está morta.");
					if(d instanceof Walpurgisnacht) throw new GameOverException("Walpurgisnacht foi derrotada. Fim de jogo.");
				}
				if(c.isMahouShoujo()&&c.getPB()<=0){
					System.out.println(c.getName()+" se tornou bruxa!");
					mahou.remove(c);
					if(c instanceof Nagisa)field[row][col] = new Charlotte(row, col);
					else if(c instanceof Sayaka)field[row][col] = new Oktavia(row, col);
					else field[row][col] = new NormalWitch(row, col);
					witch.add(field[row][col]);
					if(c instanceof Madoka) throw new GameOverException("Madoka virou uma Bruxa. Fim de jogo.");
				}
				return true;
			}
	    }
    	return false;
    }
    
    //@ requires c!=null;
    //@ ensures \old(mahou.size())<mahou.size() || \old(witch.size())<witch.size();
    private /*@ spec_public@*/ void killCharacter(Character c){
    	if(c.isMahouShoujo())
    		mahou.remove(c);
    	else 
    		witch.remove(c);
    }
    
    //@ ensures \result == true <== i>=0 && i<TAMLC;
    private /*@ spec_public@*/ boolean validateIndex(int i){
    	return (i>=0 && i<TAMLC);
    }
    
    @Override
	public void forwardSimulation(int times){
    	for(int i=0; i<times; i++){
    		for(int x=0; x<mahou.size(); x++){
    			Character aux = mahou.get(x);
				if(!executeAttack(aux)||aux instanceof Madoka){
					moveCharacter(aux);
				}
    		}
    		for(int y=0; y<witch.size(); y++){
    			Character aux2 = witch.get(y);
    			if(!executeAttack(aux2)){
					moveCharacter(aux2);
				}
    		}
    	}
		setChanged();
		notifyObservers();
    }
    
    //@ requires c!=null;
    private /*@ spec_public@*/ Character enemyClose(Character c){
    	int row = c.getLin();
    	int col = c.getCol();
    	int range = c.getRange();
    	for(int i=row-range; i<row+range;i++){
    		for(int j=col-range;j<col+range; j++){
    			if(validateIndex(i)&&validateIndex(j)){
	    			Character d = field[i][j];
	    			if(d!=null && d.isMahouShoujo()^c.isMahouShoujo()) return d;
    			}
    		}
    	}
    	return null;
    }
    
    //@ requires nMahou>6;
    //@ ensures \old(mahou.size())<mahou.size();
    private /*@ spec_public@*/ void sortMahou(int nMahou){
    	int cont = 6, col, row;
		while(cont<nMahou){
			col = 2 + (int)(Math.random()*100)%18;
			row = 2 + (int)(Math.random()*100)%18;
			if(!isOcupied(row, col)&&(col+row<TAMLC)){
				int n = 2 + (int)(Math.random()*5);
		    	if(n!=7){
		    		field[row][col] = kyubey.createInstance(n, row, col);
		    		mahou.add(field[row][col]);
		    		cont++;
		    	}
			}
		}
    }
    
    //@ensures \result == true <== field[lin][col]!=null;
	@Override
	public boolean isOcupied(int lin, int col) {
		if(validateIndex(lin)&&validateIndex(col)){
			return field[lin][col] !=null;
		}
		return true;
	}

	//@ensures \result == field[lin][col];
	@Override
	public Character getChara(int lin, int col) {
		return field[lin][col];
	}
	
	/*@ public normal_behavior
	    ensures \result == true;
	  	
	  	also
	  	public exceptional_behavior 
	  	signals (IOException);
	 @*/
	@Override
	public boolean saveSimulation(String filename){
		try{
		if(filename==null)return false;
		FileWriter arq = new FileWriter(System.getProperty("user.dir")+"/saves/"+filename+".txt");
		PrintWriter print = new PrintWriter(arq);
		for(Character c: mahou){
			String code = String.valueOf(c.getCode());
			String name = c.getName();
			String row = String.valueOf(c.getLin());
			String col = String.valueOf(c.getCol());
			String hb = String.valueOf(c.getHB());
			String pb = String.valueOf(c.getPB());
			print.println(code+";"+name+";"+row+";"+col+";"+hb+";"+pb+";");
		}
		for(Character c: witch){
			String code = String.valueOf(c.getCode());
			String name = c.getName();
			String row = String.valueOf(c.getLin());
			String col = String.valueOf(c.getCol());
			String hb = String.valueOf(c.getHB());
			String pb = String.valueOf(c.getPB());
			print.println(code+";"+name+";"+row+";"+col+";"+hb+";"+pb+";");
		}
		print.close();
		arq.close();
		}catch(IOException e){
			return false;
		}
		return true;
	}

	/*@ public normal_behavior
    ensures \result == true;
  	
  	also
  	public exceptional_behavior 
  	signals (IOException);
 	@*/
	@Override
	public boolean loadSimulation(File f){
		field = new Character[TAMLC][TAMLC];
    	mahou = new ArrayList<Character>();
    	witch = new ArrayList<Character>();
    	try{
    	FileReader arq = new FileReader(f.getPath());
    	br = new BufferedReader(arq);
    	
    	String linha;
    	while((linha = br.readLine())!=null){
    		String[] data = linha.split(";");
    		int code = Integer.parseInt(data[0]);
    		int row = Integer.parseInt(data[2]);
    		int col = Integer.parseInt(data[3]);
    		int hb = Integer.parseInt(data[4]);
    		int pb = Integer.parseInt(data[5]);
    		Character c;
    		c = kyubey.createInstance(code, row, col);
    		if(c==null)throw new IOException();
    		c.setPB(pb);
    		c.setHB(hb);
    		if(c.isMahouShoujo())mahou.add(c);
    		else witch.add(c);
    		field[row][col] = c;
    	}
    	br.close();
    	arq.close();
    	
    	}catch(IOException e){
    		return false;
    	}
		setChanged();
		notifyObservers();
    	return true;
	}
	
}
