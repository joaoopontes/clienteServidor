package servidorCliente.ucsal;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Servidor {
	public static void main(String[] args) throws Exception {
		entrada();
	}

	public static void entrada() {
		Scanner scportaServidor = new Scanner(System.in);
		Integer portaEntradaServidor;

		System.out.println("Iniciando servidor");
		System.out.println("Digite a porta que deseja ser utilizada pelo servidor : ");
		portaEntradaServidor = scportaServidor.nextInt();
		try {
			conexaoCliente(portaEntradaServidor);
		} catch (Exception e) {
			System.out.println("Erro ao conectar");
			System.out.println("Excpetion :" + e.getMessage());
			scportaServidor.close();
		}
	}

	public static void conexaoCliente(Integer portaEntradaServidor) throws Exception {
		ServerSocket servidor = new ServerSocket(portaEntradaServidor);
		System.out.println("Aguardando estabelecimento de conexao... ");
		Socket socketServidor = servidor.accept();
		InputStream inputServidor = socketServidor.getInputStream();
		OutputStream outputServidor = socketServidor.getOutputStream();
		try {
			comunicacaoCliente(inputServidor, outputServidor, socketServidor);
		} catch (Exception e) {
			servidor.close();
		}

	}

	public static void comunicacaoCliente(InputStream inputServidor, OutputStream outputServidor,
			Socket socketServidor) {
		Boolean status = true;
		BufferedReader readerServidor = new BufferedReader(new InputStreamReader(inputServidor));
		PrintStream printServidor = new PrintStream(outputServidor);
		try {
			while (status) {
				String mensagem = readerServidor.readLine();

				System.out.println(
						"Cliente :  " + " " + "Ip Cliente :" + socketServidor.getInetAddress() + " " + mensagem);
				if (mensagem.equalsIgnoreCase("exit")) {
					System.out.println("Conexao encerrada.");
					break;
				}
				printServidor.println(mensagem);
			}

		} catch (Exception e) {
			try {
				readerServidor.close();
				printServidor.close();
				socketServidor.close();
			} catch (Exception erroEncerrar) {
				System.out.println("Erro ao encerrar ");
				System.out.println("Exception  : " + erroEncerrar.getMessage());
			}
		}

	}
}
