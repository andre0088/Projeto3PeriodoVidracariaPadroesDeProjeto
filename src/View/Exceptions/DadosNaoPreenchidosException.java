package View.Exceptions;

public class DadosNaoPreenchidosException extends Exception{
	public DadosNaoPreenchidosException() {
		super("Algum dado n�o foi preenchido corretamente.");
	}
}
