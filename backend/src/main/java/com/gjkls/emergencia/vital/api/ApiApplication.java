package com.gjkls.emergencia.vital.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gjkls.emergencia.vital.api.padroes.adapter.TipoLogging;
import com.gjkls.emergencia.vital.api.padroes.factory.LoggerFactory;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		LoggerFactory.setTipo(TipoLogging.TXT);
		SpringApplication.run(ApiApplication.class, args);
	}

}
