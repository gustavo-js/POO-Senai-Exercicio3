package conexao.com.br;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Conexao {
	private String url;
	private String usuario;
	private String senha;
	private Connection conn;
	
	public Conexao() {
		this.url = "jdbc:postgresql://127.0.0.1:5432/postgres";
		this.usuario = "postgres";
		this.senha = "123456";
		
		try {
			Class.forName("org.postgresql.Driver");
			this.conn = DriverManager.getConnection(url, usuario, senha);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int executeQuery(String sql) {
		try {
			Statement stm = conn.createStatement();
			int res = stm.executeUpdate(sql);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public ResultSet executeSearch(String sql) {
		try {
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
