package com.sistema.distribuido.service;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.net.Socket;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ClientService {

    private static final String FileCpath = "C:\\test\\"; // substitua pelo caminho dos arquivos a serem enviados/salvos.
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    private Socket socket;
    static DataOutputStream out;
    static DataInputStream in;


    public void uploadFile(String fileName) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream())) {
            // Adicione a lógica para enviar arquivos ao servidor aqui
            out.writeUTF("UPLOAD");// comando para o servidor executar o upload.

            // passando apenas o nome do arquivo para o servidor.
            out.writeUTF(fileName);

            // abre o caminho + o nome do arquivo.
            try (FileInputStream fileInputStream = new FileInputStream(FileCpath + fileName)) {
                // abra o arquivo e o envie por partes para o servidor.
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void downloadFile(String fileName) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream())) {

            out.writeUTF("DOWNLOAD"); // comando para o servidor executar o download.
            // passa apenas o nome dos arquivos.
            out.writeUTF(fileName);

            boolean fileExists = in.readBoolean();
            if (fileExists) {
                int fileSize = in.readInt();
                byte[] fileData = new byte[fileSize];
                in.readFully(fileData);
                saveFile(fileName, fileData);
            } else {
                System.out.println("Arquivo não encontrado no servidor.");
            }

        } catch (IOException ex) {
           ex.printStackTrace();
        }
    }

    private void saveFile(String fileName, byte[] fileData) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(FileCpath + fileName)) {
            fileOutputStream.write(fileData);
        }
    }

    public void listarArquivos() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream())) {
            out.writeUTF("LISTAR"); //Envia comando para o servidor listar os itens na pasta.
            String response = in.readUTF();
            System.out.println(response); // Exibir a lista de arquivos no console
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}