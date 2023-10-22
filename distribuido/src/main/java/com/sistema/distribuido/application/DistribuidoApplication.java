package com.sistema.distribuido.application;


import com.sistema.distribuido.config.Config;
import com.sistema.distribuido.controller.ClientController;
import com.sistema.distribuido.service.ClientService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
@ComponentScan(basePackageClasses = {ClientController.class, ClientService.class,Config.class})
public class DistribuidoApplication {

	private static final int PORT = 22345; // escolha a porta de operação/escuta do servidor.
	// Local da pasta onde o servidor salvará os arquivos.

	private static final String FILE_STORAGE_PATH = "C:\\test/"; //subistitua o local para o servidor salvar os arquivos.

	public static void main(String[] args) {
		SpringApplication.run(DistribuidoApplication.class, args);

		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			System.out.println("Servidor em execução na porta " + PORT);

			File storageDir = new File(FILE_STORAGE_PATH);
			if (!storageDir.exists()) {
				storageDir.mkdirs();
			}

			while (true) {
				Socket clientSocket = serverSocket.accept();
				new ClientHandler(clientSocket).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	static class ClientHandler extends Thread {
		private final Socket clientSocket;

		public ClientHandler(Socket socket) {
			this.clientSocket = socket;
		}

		@Override
		public void run() {
			try (DataInputStream in = new DataInputStream(clientSocket.getInputStream());
				 DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())) {

				String request = in.readUTF();

			 if (request.equals("DOWNLOAD")) {
					// Cliente está solicitando um arquivo
					String fileName = in.readUTF();
					sendFile(fileName, out);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void sendFile(String fileName, DataOutputStream out) throws IOException {
		Path filePath = Path.of(FILE_STORAGE_PATH + fileName);
		if (Files.exists(filePath)) {
			out.writeBoolean(true); // Indica que o arquivo existe
			byte[] fileData = Files.readAllBytes(filePath);
			out.writeInt(fileData.length); // Tamanho do arquivo
			out.write(fileData);
			System.out.println("Enviado arquivo " + fileName + " para o cliente.");
		} else {
			out.writeBoolean(false); // Indica que o arquivo não existe
		}
	}
}
