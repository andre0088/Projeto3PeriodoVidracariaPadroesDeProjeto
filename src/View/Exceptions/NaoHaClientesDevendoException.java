package View.Exceptions;

public class NaoHaClientesDevendoException extends Exception{
	public NaoHaClientesDevendoException () {
		super("N�o h� nenhum cliente com d�vida atrasada.");
	}
}
