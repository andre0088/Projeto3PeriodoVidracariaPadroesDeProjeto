package View.Exceptions;

public class DadosNaoPreenchidosException extends Exception{
	public DadosNaoPreenchidosException() {
		super("Algum dado não foi preenchido corretamente.");
	}
}
