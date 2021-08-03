package br.com.projetojsf;

import java.util.Scanner;

public class tese {

	public static void main(String[] args) {
		Pessoa pessoa = new Pessoa();

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
//		
//		System.out.println("Digite o Nome completo: ");
//		pessoa.setNomeCompleto(sc.nextLine());
//
//		System.out.println("Digite o telefone: ");
//		pessoa.setTelefone(sc.nextLine());
//		
//		System.out.println("Digite o email: ");
//		pessoa.setEmail(sc.nextLine());
//
//		System.out.println("Digite o cep: ");
//		pessoa.setCep(sc.nextLine());
//		
//		System.out.println("Digite o endereco: ");
//		pessoa.setEndereco(sc.nextLine());
//		
//		System.out.println("Digite o numero: ");
//		pessoa.setNumero(sc.nextLine());
//
//		System.out.println("Digite o bairro: ");
//		pessoa.setBairro(sc.nextLine());
//		
//		System.out.println("Digite o cidade: ");
//		pessoa.setCidade(sc.nextLine());
//
//		System.out.println("Digite o estado: ");
//		pessoa.setEstado(sc.nextLine());
//		
//		System.out.println("Digite o sexo: ");
//		pessoa.setSexo(sc.nextLine());
//
//		System.out.println("Digite o data de nascimento: ");
//		pessoa.setDatacasc(sc.nextLine());
//		
//		
//				
//				pessoa.CadastrarPessoa();

//		System.out.println("Digite o cep ");
//		pessoa.setCep(sc.nextLine());
//		
//		PessoaBean pb = new PessoaBean();
////		
////		pb.pesquisaCep(null);
//
//		System.out.println(pb.pessoa.getCep());
		

		PessoaBean pb = new PessoaBean();
		
		pb.getListagemPessoas();
		
		
		
		
	}

}
