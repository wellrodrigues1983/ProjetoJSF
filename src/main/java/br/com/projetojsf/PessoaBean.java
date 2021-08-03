package br.com.projetojsf;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;

import com.google.gson.Gson;

import br.com.projetojsf.connection.ConnectionDB;
import br.com.projetojsf.connection.ConnectionDBException;

@ViewScoped
@ManagedBean(name = "pessoaBean")
public class PessoaBean {

	Pessoa pessoa = new Pessoa();

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	private void mostrarMsg(String msg) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(msg);
		context.addMessage(null, message);
	}

	public void pesquisaCep(AjaxBehaviorEvent event) {

		try {
			URL url = new URL("https://viacep.com.br/ws/" + pessoa.getCep() + "/json/");
			URLConnection connection = url.openConnection();
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			String cep = "";
			StringBuilder jsonCep = new StringBuilder();

			while ((cep = br.readLine()) != null) {
				jsonCep.append(cep);
			}

			Pessoa gsonAux = new Gson().fromJson(jsonCep.toString(), pessoa.getClass());

			pessoa.setCep(gsonAux.getCep());
			pessoa.setNomeCompleto(gsonAux.getNomeCompleto());
			pessoa.setLogradouro(gsonAux.getLogradouro());
			pessoa.setComplemento(gsonAux.getComplemento());
			pessoa.setBairro(gsonAux.getBairro());
			pessoa.setLocalidade(gsonAux.getLocalidade());
			pessoa.setUf(gsonAux.getUf());

			System.out.println(pessoa.getCep());
			System.out.println(pessoa.getLogradouro());
			System.out.println(pessoa.getComplemento());
			System.out.println(pessoa.getBairro());
			System.out.println(pessoa.getLocalidade());
			System.out.println(pessoa.getUf());


		} catch (Exception ex) {
			ex.printStackTrace();
			mostrarMsg("Erro ao consultar o CEP");

		}

	}
	
	
	public void novoCadastro() {
		pessoa = new Pessoa();
	}
	
	

	public void CadastrarPessoa() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Connection conn = null;
		PreparedStatement st = null;

		try {
			conn = ConnectionDB.getConnection(); /* Criando a conexão */

			/* Criando o insert */
			st = conn.prepareStatement("INSERT INTO pessoa "
					+ "(nomecompleto, telefone, email, cep, endereco, numeroendereco, bairro, cidade, estado, sexo, dtnascimento, complemento) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");/* Place holder para ser passado como parametro depois */

			st.setString(1, pessoa.getNomeCompleto());
			st.setString(2, pessoa.getTelefone());
			st.setString(3, pessoa.getEmail());
			st.setString(4, pessoa.getCep());
			st.setString(5, pessoa.getLogradouro());
			st.setInt(6, pessoa.getNumero());
			st.setString(7, pessoa.getBairro());
			st.setString(8, pessoa.getBairro());
			st.setString(9, pessoa.getUf());
			st.setString(10, pessoa.getSexo());
			try {
				st.setDate(11, new java.sql.Date(sdf.parse(pessoa.getDatanasc()).getTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			st.setString(12, pessoa.getComplemento());

			int resultado = st.executeUpdate();

			if (resultado > 0) {
				novoCadastro();
				
				
			} else {
				mostrarMsg("Cadatro não realizado");
			}

		} catch (SQLException e) {
			throw new ConnectionDBException(e.getMessage());
		} finally {
			ConnectionDB.closeStatement(st);
//			ConnectionDB.closeConnection();/* A conexão sempre é fechada por último */
		}

	}

	public String SelectAllUser() {

		Connection conn = null;/* Conecta ao banco */
		Statement st = null;/* Prepara uma consulta ao banco */
		ResultSet rs = null;/* Resultado da consulta */

		try {
			conn = ConnectionDB.getConnection();/* Abrindo uma conexão */

			st = conn.createStatement();/* Instancia de um objeto statment */

			rs = st.executeQuery("select * from pessoa");/* fazendo uma consulta a uma tabela no banco */

			while (rs.next()) {
				System.out.println(rs.getInt("id") + "," + rs.getString("nomecompleto") + "," + rs.getString("telefone")
						+ "," + rs.getString("email") + "," + rs.getString("cep") + "," + rs.getString("endereco") + ","
						+ rs.getString("numeroendereco") + "," + rs.getString("bairro") + "," + rs.getString("cidade")
						+ "," + rs.getString("estado") + "," + rs.getString("sexo") + "," + rs.getString("dtnascimento")
						+ "," + rs.getString("complemento"));

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

//	private List<Pessoa> listagemPessoas = new ArrayList<>();

	
	public List<Pessoa> getListagemPessoas() {

		ArrayList<Pessoa> pessoalista = new ArrayList<Pessoa>();

		Connection conn = null;/* Conecta ao banco */
		Statement st = null;/* Prepara uma consulta ao banco */
		ResultSet rs = null;/* Resultado da consulta */

		try {
			conn = ConnectionDB.getConnection();/* Abrindo uma conexão */

			st = conn.createStatement();/* Instancia de um objeto statment */

			rs = st.executeQuery("select * from pessoa");/* fazendo uma consulta a uma tabela no banco */

			while (rs.next()) {
				
				Pessoa pessoa = new Pessoa();
				pessoa.setId(rs.getString("id"));
				pessoa.setNomeCompleto(rs.getString("nomecompleto"));
				pessoa.setTelefone(rs.getString("telefone"));
				pessoa.setEmail(rs.getString("email"));
				pessoa.setCep(rs.getString("cep"));
				pessoa.setLogradouro(rs.getString("endereco"));
				pessoa.setNumero(rs.getInt("numeroendereco"));
				pessoa.setBairro(rs.getString("bairro"));
				pessoa.setLocalidade(rs.getString("cidade"));
				pessoa.setUf(rs.getString("estado"));
				pessoa.setSexo(rs.getString("sexo"));
				pessoa.setDatanasc(rs.getString("dtnascimento"));
				pessoa.setComplemento(rs.getString("complemento"));
				
				pessoalista.add(pessoa);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionDB.closeResultSet(rs);
			ConnectionDB.closeStatement(st);
//			ConnectionDB.closeConnection();
		}
		return pessoalista;
	}

	

}
