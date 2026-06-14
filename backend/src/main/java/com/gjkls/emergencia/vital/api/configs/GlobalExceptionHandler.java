package com.gjkls.emergencia.vital.api.configs;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gjkls.emergencia.vital.api.dtos.ErroDTO;
import com.gjkls.emergencia.vital.api.padroes.adapter.ILogging;
import com.gjkls.emergencia.vital.api.padroes.factory.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private ILogging logger = LoggerFactory.getLogger();

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroDTO> handle(Exception ex) {

        logger.printError(
                "Algo deu errado em uma requisição: ",
                ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErroDTO(
                        "Erro interno"));
    }
}
