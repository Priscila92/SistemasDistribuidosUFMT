package com.sistema.distribuido.controller;

import lombok.Data;

@Data
public class Arquivo {
    private String fileName;
    private String ipAddress;
    private int port;

    public Arquivo(String fileName, String ipAddress, int port) {
        this.fileName = fileName;
        this.ipAddress = ipAddress;
        this.port = port;
    }
}
