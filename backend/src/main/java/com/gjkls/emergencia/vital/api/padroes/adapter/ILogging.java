package com.gjkls.emergencia.vital.api.padroes.adapter;

public interface ILogging {
    void printInfo(String msg);
    void printError(String msg, Throwable err);
}
