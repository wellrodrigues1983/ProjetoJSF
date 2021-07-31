package br.com.projetojsf;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import com.google.gson.Gson;

import br.com.projetojsf.connection.ConnectionDB;
import br.com.projetojsf.connection.ConnectionDBException;

@ApplicationScoped
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
			URL url = new URL("https://viacep.com.br/ws/"+pessoa.getCep()+"/json/");
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
//			
//			System.out.println(jsonCep);
			
		}catch (Exception ex) {
			ex.printStackTrace();
			mostrarMsg("Erro ao consultar o CEP");
		}
	}

	public void CadastrarPessoa() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Connection conn = null;
		PreparedStatement st = null;

		try {
			conn = ConnectionDB.getConnection(); /* Criando a conexão */

			/* Criando o insert */
			st = conn.prepareStatement("INSERT INTO pessoa " + "(nomecompleto, telefone, email, cep, endereco, numeroendereco, bairro, cidade, estado, sexo, dtnascimento, complemento) " + "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");/* Place holder para ser passado como parametro depois */

			st.setString(1, pessoa.getNomeCompleto());
			st.setInt(2, pessoa.getTelefone());
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
				mostrarMsg("Cadatro Realizado com sucesso");
				
			}else {
				mostrarMsg("Cadatro não realizado");
			}
			
		} catch (SQLException e) {
			throw new ConnectionDBException(e.getMessage());
		} finally {
			ConnectionDB.closeStatement(st);
//			ConnectionDB.closeConnection();/* A conexão sempre é fechada por último */
		}
		
		
	}

	

}
