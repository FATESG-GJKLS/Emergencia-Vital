package com.gjkls.emergencia.vital.api.padroes.adapter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;

public class TxtLogging implements ILogging {
    private static final String DEFAULT_FILE_PATH = "data/logs.txt";
    private BufferedWriter arquivo;

    public TxtLogging() {
        try {
            this.arquivo = new BufferedWriter(new FileWriter(DEFAULT_FILE_PATH, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printInfo(String msg) {
        try {
            arquivo.write(LocalDateTime.now() + " INFO: " + msg);
            arquivo.newLine();
            arquivo.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printError(String msg, Throwable err) {
        try {
            arquivo.write(LocalDateTime.now() + " ERROR: " + msg + " - " + err.getMessage());
            arquivo.newLine();
            arquivo.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
