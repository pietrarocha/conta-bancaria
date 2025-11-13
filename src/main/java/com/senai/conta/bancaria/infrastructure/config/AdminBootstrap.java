package com.senai.conta.bancaria.infrastructure.config;

import com.senai.conta.bancaria.domain.entity.Gerente;
import com.senai.conta.bancaria.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminBootstrap implements CommandLineRunner {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${sistema.admin.cpf}")
    private String adminCpf;

    @Value("${sistema.admin.senha}")
    private String adminSenha;

    @Override
    public void run(String... args) {
        usuarioRepository.findByCpfAndAtivoTrue(adminCpf).ifPresentOrElse(
                gerente -> {
                    if (!gerente.isAtivo()) {
                        gerente.setAtivo(true);
                        usuarioRepository.save(gerente);
                    }
                },
                () -> {
                    Gerente admin = Gerente.builder()
                            .nome("Administrador Provisório")
                            .cpf("00000000000")
                            .senha(passwordEncoder.encode(adminSenha))
                            .ativo(true)
                            .build();
                    usuarioRepository.save(admin);
                    System.out.println("⚡ Gerente provisório criado: " + adminCpf);
                }
        );
    }
}
