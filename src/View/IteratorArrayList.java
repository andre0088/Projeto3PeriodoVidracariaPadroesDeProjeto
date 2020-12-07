package View;

import java.util.ArrayList;

public class IteratorArrayList<T> implements Iterator{
	
	private ArrayList<T> lista;
	private int indice=0;
	
	public IteratorArrayList(ArrayList<T> lista) {
		this.lista=lista;
	}
	
	@Override
	public boolean hasNext() {
		if(lista.size()<=indice || lista.get(indice)==null) {
			return false;
		}
		return true;
	}

	@Override
	public Object next() {
		return lista.get(indice++);
	}
	
}
