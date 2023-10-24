package com.residencia.ecommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.residencia.ecommerce.entities.Cliente;
import com.residencia.ecommerce.exceptions.NoSuchElementException;
import com.residencia.ecommerce.exceptions.PropertyValueException;
import com.residencia.ecommerce.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepo;
	
	public List<Cliente> listarClientes() {
		return clienteRepo.findAll();
	}

	public Cliente getClientePorId(Long id) {
		return clienteRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Cliente", id));
	}

	public Cliente salvarCliente (Cliente cliente) {
		if (cliente.getEmail() == null || cliente.getCpf() == null || cliente.getEndereco() == null) {
			throw new PropertyValueException("Cliente");
		}
		return clienteRepo.save(cliente);
	}

	public Cliente atualizarCliente(Cliente cliente) {
		if (cliente.getEmail() == null || cliente.getCpf() == null || cliente.getEndereco() == null) {
			throw new PropertyValueException("Cliente");
		}
		return clienteRepo.save(cliente);
	}

	public boolean deletarCliente(Cliente cliente) {
		
		if (cliente == null)
			return false;

		Cliente clienteExistente = getClientePorId(cliente.getIdCliente());
		if (clienteExistente == null)
			return false;

		clienteRepo.delete(cliente);

		Cliente clienteContinuaExistindo = getClientePorId(cliente.getIdCliente());
		if (clienteContinuaExistindo == null)
			return true;
		return false;
	}

}
