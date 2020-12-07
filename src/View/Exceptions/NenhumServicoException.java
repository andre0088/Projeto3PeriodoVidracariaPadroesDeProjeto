package View.Exceptions;

public class NenhumServicoException extends Exception{
	public NenhumServicoException() {
		super("Nenhum serviço foi realizado esse mês.");
	}
}
