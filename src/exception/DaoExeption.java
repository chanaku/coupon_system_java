package exception;

public class DaoExeption extends Exception{
	public DaoExeption(String massage) {
		super("error while trying to get or set data from DB. "+massage);
	}
}
