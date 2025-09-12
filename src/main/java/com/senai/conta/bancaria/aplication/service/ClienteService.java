package com.senai.conta.bancaria.aplication.service;

import com.senai.conta.bancaria.domain.entity.Cliente;
import com.senai.conta.bancaria.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    public Cliente criarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }
    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }
    public void deletarCliente(String cpf) {
        clienteRepository.deleteByCpf(cpf);
    }

}