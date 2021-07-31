package br.com.projetojsf.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDB {
	
//	/*Método para carregar as propriedades que estão no arquivo db.properties*/
//	private static Properties loadProperties(){
//		try (FileInputStream fs = new FileInputStream("db.properties")) {
//				Properties props = new Properties();
//				props.load(fs);
//				return props;
//		}
//		catch (IOException e) {
//			throw new ConnectionDBException(e.getMessage());
//		}
//	}
//	
	
	private static Connection conn = null;/*Objeto de conexão do banco de dados do JDBC do import java.sql.Connection*/
	
	/**/
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (conn == null) {
			
			try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/cadastro?" +
                    "user=root&password=123qweasd");/*Fazendo a conexão depois de ler os dados*/
		}
		catch (SQLException e) {
			throw new ConnectionDBException(e.getMessage());
		}
	}
		return conn;
}
	public static void closeConnection() {/*fechando a conexão*/
		if (conn != null) {/*verificando se a conexão esta nula*/
			try {
				conn.close();
			} catch (SQLException e) {
				throw new ConnectionDBException(e.getMessage());
			}
		}
	}
	


	public static void closeStatement(Statement st) {/*Criando metodo para fechar o Statement com try e catch, para que não seja exigido toda vez o tratamento de exceção*/
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new ConnectionDBException(e.getMessage());
				
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {/*Criando metodo para fechar o ResultSet com try e catch, para que não seja exigido toda vez o tratamento de exceção*/
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new ConnectionDBException(e.getMessage());
				
			}
		}
	}
}
