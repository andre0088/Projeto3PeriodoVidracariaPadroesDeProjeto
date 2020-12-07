package View.Exceptions;

public class FaltaDeDinheiroException extends Exception{
	public FaltaDeDinheiroException() {
		super("Nenhum dinheiro foi ganho nesse mês.");
	}
}
