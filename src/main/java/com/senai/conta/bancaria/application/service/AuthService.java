package com.senai.conta.bancaria.application.service;

import com.senai.conta.bancaria.application.dto.AuthDTO;
import com.senai.conta.bancaria.domain.entity.Usuario;
import com.senai.conta.bancaria.domain.exceptions.EntidadeNaoEncontradaException;
import com.senai.conta.bancaria.domain.repository.UsuarioRepository;
import com.senai.conta.bancaria.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarios;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public String login(AuthDTO.LoginRequest req) {
        Usuario usuario = usuarios.findByCpfAndAtivoTrue(req.cpf())
                .orElseThrow(() ->  new EntidadeNaoEncontradaException("Usuário"));

        if (!encoder.matches(req.senha(), usuario.getSenha())) {
            throw new BadCredentialsException("Credenciais inválidas");
        }

        return jwt.generateToken(usuario.getCpf(), usuario.getTipo().name());
    }
}
