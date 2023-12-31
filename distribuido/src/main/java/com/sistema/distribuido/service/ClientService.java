package com.sistema.distribuido.service;
import com.sistema.distribuido.controller.Arquivo;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.stereotype.Service;


import java.io.*;

import java.net.Socket;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    private static final String FileCpath = "C:\\client-1\\"; // substitua pelo caminho dos arquivos a serem enviados/salvos.
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    private static final int clientPort = 22345;


    private static final int CHUNK_SIZE = 10;

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


    public void uploadFileName(List<String> fileNames) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

            out.writeUTF("STORE_FILE_INFO"); // Comando para o servidor executar o upload.

            out.writeInt(fileNames.size()); // Envia o tamanho da lista de nomes de arquivos primeiro.

            for (String fileName : fileNames) {
                out.writeUTF(fileName); // Envia cada nome de arquivo para o servidor.
            }

            out.writeShort(clientPort);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

/*    public void downloadFile(String fileName,String ipAddress, int port) {
        try (Socket socket = new Socket(ipAddress, port);
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
    }*/

    public void downloadFile(String fileName, String ipAddress, int port, long startByte, int chunkSize) {
        try (Socket socket = new Socket(ipAddress, port);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream())) {

            out.writeUTF("DOWNLOAD");
            out.writeUTF(fileName);
            out.writeLong(startByte);
            out.writeInt(chunkSize);
            boolean fileExists = in.readBoolean();
            long fileSize = in.readLong();
            if (fileExists) {
                try (RandomAccessFile file = new RandomAccessFile(FileCpath + fileName, "rw")) {
                    long remainingBytes = fileSize - startByte;

                    while (remainingBytes > 0) {
                        long bytesToRead = Math.min(chunkSize, remainingBytes); // Certifique-se de que não leia mais do que o necessário
                        byte[] buffer = new byte[(int) bytesToRead];
                        int bytesRead = in.read(buffer);
                        if (bytesRead == -1) {
                            break;
                        }
                        file.seek(startByte); // Define a posição de gravação no arquivo
                        file.write(buffer, 0, bytesRead);
                        remainingBytes = 0;
                    }
                }
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

    public List<Arquivo> listarArquivos() {
        List<Arquivo> arquivos = new ArrayList<>();
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream())) {
            out.writeUTF("REQUEST_FILE_INFO"); //Envia comando para o servidor listar os itens na pasta.
            boolean fileExists = in.readBoolean();
            if (fileExists) {
                int fileSize = in.readInt();
                byte[] fileData = new byte[fileSize];
                in.readFully(fileData);
                String xmlContent = new String(fileData);

                // Ler o conteúdo XML
                SAXBuilder saxBuilder = new SAXBuilder();
                Document document = saxBuilder.build(new StringReader(xmlContent));
                Element root = document.getRootElement();

                List<Element> fileList = root.getChildren("file");
                for (Element fileElement : fileList) {
                    String fileName = fileElement.getChildText("fileName");
                    String ipAddress = fileElement.getChildText("ipAddress");
                    String port = fileElement.getChildText("port");
                    Arquivo arquivo = new Arquivo(fileName, ipAddress, Integer.parseInt(port));
                    arquivos.add(arquivo);
                }
            } else {
                System.out.println("Arquivo não encontrado no servidor.");
            }
        } catch (IOException | JDOMException e) {
            e.printStackTrace();
        }
        return arquivos;
    }





}