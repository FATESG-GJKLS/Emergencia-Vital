package com.gjkls.emergencia.vital.api.padroes.adapter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


// É necessário o adapter pois o Log4j tem uma interface diferente da nossa ILogging.
public class Log4jAdapter implements ILogging {
    private final Logger logger = LogManager.getLogger(Log4jAdapter.class);

    @Override
    public void printInfo(String msg) {
        logger.info(msg);
    }

    @Override
    public void printError(String msg, Throwable err) {
        logger.error(msg + err.getMessage());
    }
}
