package servidorCliente.ucsal;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

import java.util.Scanner;

public class Cliente {
	public static void main(String[] args) throws Exception {
		entrada();
	}

	public static void entrada() throws Exception {
		Scanner enderecoClienteScanner = new Scanner(System.in);
		String enderecoCliente;
		Scanner portaClienteScanner = new Scanner(System.in);
		Integer portaCliente;

		System.out.println("Endereco : ");
		enderecoCliente = enderecoClienteScanner.nextLine();
		System.out.println("Porta : ");
		portaCliente = portaClienteScanner.nextInt();

		conexao(enderecoCliente, portaCliente);

		enderecoClienteScanner.close();
		portaClienteScanner.close();
	}

	public static void conexao(String enderecoCliente, Integer portaCliente) throws Exception {

		System.out.println("Iniciando conexao... ");
		System.out.println(" ");
		Socket socket = new Socket(enderecoCliente, portaCliente);
		System.out.println("Conexao estabelecida. ");

		comunicacaoServidor(socket);

	}

	public static void comunicacaoServidor(Socket socket) throws Exception {
		String mensagem;
		Boolean status = true;
		InputStream inputCliente = socket.getInputStream();
		OutputStream outputCliente = socket.getOutputStream();

		BufferedReader readerCliente = new BufferedReader(new InputStreamReader(inputCliente));
		PrintStream printCliente = new PrintStream(outputCliente);

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);

		while (status) {
			System.out.println("Comando : ");
			mensagem = sc.nextLine();
			printCliente.println(mensagem);
			if (mensagem.equalsIgnoreCase("exit")) {
				System.out.println("Conexao encerrada com o servidor. ");
				status = false;
				System.exit(0);
			}
			if (!mensagem.equalsIgnoreCase(null)) {
				mensagem = readerCliente.readLine();
				System.out.println("Enviado servidor : " + mensagem);

				comunicacaoServidor(socket);
			}
			break;
		}
	}
}
