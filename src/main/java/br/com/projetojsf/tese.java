package br.com.projetojsf;

import java.util.Scanner;

public class tese {

	public static void main(String[] args) {
		LoginBean loginbean = new LoginBean();
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Digite o login: ");
		loginbean.setUsername(sc.nextLine());

		System.out.println("Digite o senha: ");
		loginbean.setPassword(sc.nextLine());
				
				loginbean.AccessLoginUser();
		
		

	}

}
