package DAO;

public interface CrudMethods {
	
	public void cadastrar(Object dto) throws Exception;
	
	public void editar(Object dto) throws Exception;
	
	public void excluir(Object dto);
	
	public Object listar();
	
}
