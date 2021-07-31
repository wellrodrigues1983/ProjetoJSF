package br.com.projetojsf;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.Properties;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.projetojsf.connection.ConnectionDB;
import br.com.projetojsf.connection.ConnectionDBException;


@ManagedBean
@RequestScoped
public class LoginBean {

	private String username;

	private String password;

	private String dataCadastro;

	public void setUsername(String name) {
		this.username = name;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public static Properties getProp() throws IOException {
		Properties props = new Properties();
		FileInputStream file = new FileInputStream(
				"db.properties");
		props.load(file);
		return props;

	}


//	public void login() {
//		if ("BootsFaces".equalsIgnoreCase(username) && "rocks!".equalsIgnoreCase(password)) {
//			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
//					"Congratulations! You've successfully logged in.");
//			FacesContext.getCurrentInstance().addMessage("loginForm:password", msg);
//
//		} else {
//			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
//					"That's the wrong password. Hint: BootsFaces rocks!");
//			FacesContext.getCurrentInstance().addMessage("loginForm:password", msg);
//		}
//	}
//
//	public void forgotPassword() {
//		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Default user name: BootsFaces");
//		FacesContext.getCurrentInstance().addMessage("loginForm:username", msg);
//		msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Default password: rocks!");
//		FacesContext.getCurrentInstance().addMessage("loginForm:password", msg);
//	}

	@Override
	public int hashCode() {
		return Objects.hash(password, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginBean other = (LoginBean) obj;
		return Objects.equals(password, other.password) && Objects.equals(username, other.username);
	}

	public void CadastrarUsuarios() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Connection conn = null;
		PreparedStatement st = null;

		try {
			conn = ConnectionDB.getConnection(); /* Criando a conexão */

			/* Criando o insert */
			st = conn.prepareStatement("INSERT INTO login_user " + "(login, senha, dtcadastro) " + "VALUES "
					+ "(?, ?, ?)");/* Place holder para ser passado como parametro depois */

			st.setString(1, username);
			st.setString(2, password);
			st.setDate(3, new java.sql.Date(
					sdf.parse(dataCadastro).getTime()));/* Passar uma data pelo padrão sql.date para inserir no banco */

			int rowsAffected = st.executeUpdate(); /* Pegar a quantidade de registros afetados */

			System.out.println("Cadastrado! Linhas Afetadas: " + rowsAffected);
		} catch (SQLException e) {
			throw new ConnectionDBException(e.getMessage());
		} catch (ParseException e) { /* Necessário tratamento para o sdf.parse */
			e.printStackTrace();
		} finally {
			ConnectionDB.closeStatement(st);
			ConnectionDB.closeConnection();/* A conexão sempre é fechada por último */
		}
	}

	public void SelectAllUser() {

		Connection conn = null;/* Conecta ao banco */
		Statement st = null;/* Prepara uma consulta ao banco */
		ResultSet rs = null;/* Resultado da consulta */

		try {
			conn = ConnectionDB.getConnection();/* Abrindo uma conexão */

			st = conn.createStatement();/* Instancia de um objeto statment */

			rs = st.executeQuery("select * from login_user");/* fazendo uma consulta a uma tabela no banco */

			while (rs.next()) {
				System.out.println(rs.getInt("id") + "," + rs.getString("username") + "," + rs.getString("password")
						+ "," + rs.getString("dataCadastro"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionDB.closeResultSet(rs);
			ConnectionDB.closeStatement(st);
			ConnectionDB.closeConnection();
		}

	}

	public String AccessLoginUser() {
		
		Connection conn = null;/* Conecta ao banco */
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM login_user WHERE username=? and password=?";
		
		try {
			
			conn = ConnectionDB.getConnection();			
			st = conn.prepareStatement(sql);
			
			st.setString(1, getUsername());
			st.setString(2, getPassword());

			rs = st.executeQuery();
			
			if(rs.next()){
				return "/cadastropessoa.xhtml";
			}else {
				System.out.println("Usuario Não cadastrado");
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionDB.closeResultSet(rs);
			ConnectionDB.closeStatement(st);
//			ConnectionDB.closeConnection();
		}
		return null;

	}

}