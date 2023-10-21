package com.sistema.distribuido.controller;

import com.sistema.distribuido.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

    @GetMapping("/download")
    public String downloadFile(@RequestParam String fileName) {
        clientService.downloadFile(fileName);
        return "Arquivo " + fileName + " foi baixado com sucesso.";
    }

    @GetMapping("/list")
    public String listFiles() {
        clientService.listarArquivos();
        return "Listando arquivos disponíveis no servidor.";
    }
}
