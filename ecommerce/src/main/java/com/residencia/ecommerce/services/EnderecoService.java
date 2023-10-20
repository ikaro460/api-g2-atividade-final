package com.residencia.ecommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.residencia.ecommerce.entities.Endereco;
import com.residencia.ecommerce.repositories.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	EnderecoRepository enderecoRepo;

	public List<Endereco> listarEnderecos() {
		return enderecoRepo.findAll();
	}

	public Endereco getEnderecoPorId(Long id) {
		return enderecoRepo.findById(id).orElse(null);
	}

	public Endereco salvarEndereco(Endereco endereco) {
		return enderecoRepo.save(endereco);
	}

	public Endereco atualizarEndereco(Endereco endereco) {
		return enderecoRepo.save(endereco);
	}

	public boolean deletarEndereco(Endereco endereco) {
		// verifica se endereco é valido
		if (endereco == null)
			return false;

		// verifica se existe no banco
		Endereco enderecoExistente = getEnderecoPorId(endereco.getIdEndereco());
		if (enderecoExistente == null)
			return false;

		// deleta endereco
		enderecoRepo.delete(endereco);

		// verifica se foi deletado de fato
		Endereco enderecoContinuaExistindo = getEnderecoPorId(endereco.getIdEndereco());
		if (enderecoContinuaExistindo == null)
			return true;
		return false;
	}

}
