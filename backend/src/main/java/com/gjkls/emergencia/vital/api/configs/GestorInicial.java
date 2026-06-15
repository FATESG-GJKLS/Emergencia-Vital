package com.gjkls.emergencia.vital.api.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gjkls.emergencia.vital.api.models.funcionario.Funcionario;
import com.gjkls.emergencia.vital.api.models.funcionario.TipoFuncionario;
import com.gjkls.emergencia.vital.api.padroes.adapter.ILogging;
import com.gjkls.emergencia.vital.api.padroes.factory.LoggerFactory;
import com.gjkls.emergencia.vital.api.repository.FuncionarioRepository;

@Configuration
public class GestorInicial {
	private ILogging logger = LoggerFactory.getLogger();

    @Bean
    CommandLineRunner criarGestorPadrao(FuncionarioRepository repo, PasswordEncoder encoder) {
		return args -> {
			logger.printInfo("Procurando Gestor Padrão...");
			if (repo.findByCPFAndAtivo("241.796.780-47", true).isEmpty()) {
				logger.printInfo("Não foi encontrado, inicializando");
				
				Funcionario gestor = new Funcionario();
				gestor.setNome("Gestor Padrão");
				gestor.setCPF("241.796.780-47");
                gestor.setEmail("uwu@gmail.com");
				gestor.setSenha(encoder.encode("123456"));
				gestor.setTipoFuncionario(TipoFuncionario.GESTOR);
				repo.save(gestor);
			}
			logger.printInfo("Finalizado a inicialização do Gestor Padrão.");
		};
	}
}
