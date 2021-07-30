package br.com.projetojsf.connection;

public class ConnectionDBException extends RuntimeException{

		private static final long serialVersionUID = 1L;
		
		/*classe para carregar excecões na conexão com o banco*/
		public ConnectionDBException(String msg) {
			super(msg);
		}

	}
