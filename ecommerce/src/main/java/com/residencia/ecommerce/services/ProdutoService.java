package com.residencia.ecommerce.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.residencia.ecommerce.entities.Produto;
import com.residencia.ecommerce.repositories.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	ProdutoRepository produtoRepo;

	public List<Produto> listarProdutos() {
		return produtoRepo.findAll();
	}

	public Produto getProdutoPorId(Long id) {
		return produtoRepo.findById(id).orElse(null);
	}

	public Produto salvarProduto(Produto produto) {
		return produtoRepo.save(produto);
	}
	
	public Produto salvarProduto(String strProduto, MultipartFile arqImg) throws IOException {
		Produto produto = new Produto();
		
		try {
			ObjectMapper objMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			 
			//editora.setImagem(arqImg.getBytes());
			produto = objMapper.readValue(strProduto, Produto.class);
		} catch(IOException e) {
			System.out.println("Erro ao converter a string produto: " + e.toString());
		}
		produto.setImagem(arqImg.getBytes());
		// fazer o @Lob com um array de bytes
		
		return produtoRepo.save(produto);
	}

	public Produto atualizarProduto(Produto produto) {
		return produtoRepo.save(produto);
	}

	public boolean deletarProduto(Produto produto) {
		// verifica se produto é valido
		if (produto == null)
			return false;

		// verifica se existe no banco
		Produto produtoExistente = getProdutoPorId(produto.getIdProduto());
		if (produtoExistente == null)
			return false;

		// deleta produto
		produtoRepo.delete(produto);

		// verifica se foi deletado de fato
		Produto produtoContinuaExistindo = getProdutoPorId(produto.getIdProduto());
		if (produtoContinuaExistindo == null)
			return true;
		return false;
	}

}
