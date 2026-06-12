package com.gjkls.emergencia.vital.api.services;

import com.gjkls.emergencia.vital.api.repository.FuncionarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutorizacaoService implements UserDetailsService {

    private FuncionarioRepository funcionarioRepository;
    public AutorizacaoService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDetails> userDetails = funcionarioRepository.findByCPFAndAtivo(username, true);
        if (userDetails.isPresent()) {
            return userDetails.get();
        }
        throw new UsernameNotFoundException(username);
    }
}
