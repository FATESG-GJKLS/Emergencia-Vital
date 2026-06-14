package com.gjkls.emergencia.vital.api.padroes.factory;

import com.gjkls.emergencia.vital.api.padroes.adapter.ILogging;
import com.gjkls.emergencia.vital.api.padroes.adapter.Log4jAdapter;
import com.gjkls.emergencia.vital.api.padroes.adapter.TipoLogging;
import com.gjkls.emergencia.vital.api.padroes.adapter.TxtLogging;

public class LoggerFactory {
    private static TipoLogging tipo;
    private static ILogging loggerSingleton;

    public static ILogging getLogger() {
        switch (tipo) {
            case TXT:
                if (loggerSingleton == null) {
                    loggerSingleton = new TxtLogging();
                }
                return loggerSingleton;
            case LOG4J:
                if (loggerSingleton == null) {
                    loggerSingleton = new Log4jAdapter();
                }
                return loggerSingleton;
            default:
                throw new IllegalArgumentException("Tipo de logging não suportado: " + tipo);
        }
    }

    public static void setTipo(TipoLogging tipo) {
        LoggerFactory.tipo = tipo;
    }
}
