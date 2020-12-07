package View.Exceptions;

public class NaoHaClientesDevendoException extends Exception{
	public NaoHaClientesDevendoException () {
		super("Não há nenhum cliente com dívida atrasada.");
	}
}
