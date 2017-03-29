package Game;

public class GameOverException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public GameOverException(){
		super();
	}
	public GameOverException(String msg){
		super(msg);
	}
}
