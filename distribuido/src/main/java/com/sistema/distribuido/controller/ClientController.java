package com.sistema.distribuido.controller;

import com.sistema.distribuido.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private ClientService clientService;
    @PostMapping("/upload")
    public String uploadFile(@RequestParam String fileName) {
        clientService.uploadFile(fileName);
        return "Arquivo " + fileName + " foi enviado com sucesso.";
    }

    @PostMapping("/uploadFileName")
    public ResponseEntity<List<String>> uploadFileName(@RequestParam List<String> fileNames) {
        clientService.uploadFileName(fileNames);
        System.out.println("Arquivos foram enviados com sucesso.");
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadFile(@RequestParam String fileName,@RequestParam String ipAddress,@RequestParam int port,
                               @RequestParam long startByte,@RequestParam int chunkSize) {
        clientService.downloadFile(fileName,ipAddress,port,startByte,chunkSize);
        System.out.println("Arquivo " + fileName + " foi baixado com sucesso.");
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Arquivo>> listFiles() {
        List<Arquivo> arquivos = clientService.listarArquivos();
        return new ResponseEntity<>(arquivos, HttpStatus.OK);
    }
}
