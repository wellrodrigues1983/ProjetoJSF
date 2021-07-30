package br.com.projetojsf;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import br.com.projetojsf.connection.ConnectionDBException;
public class Manipulador {

	/*Método para carregar as propriedades que estão no arquivo db.properties*/
	private static Properties loadProperties(){
		try (FileInputStream fs = new FileInputStream("db.properties")) {
				Properties props = new Properties();
				props.load(fs);
				return props;
		}
		catch (IOException e) {
			throw new ConnectionDBException(e.getMessage());
		}
	}
	
	private static Connection conn;/*Objeto de conexão do banco de dados do JDBC do import java.sql.Connection*/
	
	public static Connection getConnection() {
		
			try {
			Properties props = loadProperties();
			String login = props.getProperty("user");//Variavel que guardará o login do servidor
			String url = props.getProperty("dburl");//Variavel que guardará o host do servidor.
			String pass = props.getProperty("password");//Variável que guardará o password do usúario.
			conn = DriverManager.getConnection(url, login, pass);/*Fazendo a conexão depois de ler os dados*/
		}
		catch (SQLException e) {
			throw new ConnectionDBException(e.getMessage());
		}
			System.out.println(conn);
	
		return conn;
}
	
	
	public static void  main(String args[]) throws IOException {
	
		System.out.println("************Teste de leitura do arquivo de propriedades************");

		Properties prop = loadProperties();

		String login = prop.getProperty("user");//Variavel que guardará o login do servidor
		String url = prop.getProperty("dburl");//Variavel que guardará o host do servidor.
		String pass = prop.getProperty("password");//Variável que guardará o password do usúario.

		System.out.println("Login = " + login);
		System.out.println("Host = " + url);
		System.out.println("Password = " + pass);
		
		System.out.println(conn);
	}
}

