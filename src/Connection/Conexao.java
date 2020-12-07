package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexao {
    
	private static String url = "jdbc:postgresql://localhost:5432/Lima Vidros";
    private static String login = "postgres";
    private static String senha = "123";
    private static String driver = "postgresql.Driver";
    private volatile static Connection instancia;
	
    private Conexao() {
    }
    
    
    public static Connection getInstancia() { 
    	if (instancia == null) {
    		synchronized (Conexao.class) {
    			if (instancia==null) {
    				instancia = abrirConexao();
    			}
			}
    	}
    	return instancia;
    }

    private static Connection abrirConexao(){
        
        try {
            /// seta o driver da conexao
            System.setProperty("org.Drives", driver);
            // passa os dados e recebe a conexao
            instancia = DriverManager.getConnection(url, login, senha);
            return instancia;
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
 
    public static void fecharConexao(){
        try {
            instancia.close();
            instancia=null;
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

