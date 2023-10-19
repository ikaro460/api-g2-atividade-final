package com.residencia.ecommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.residencia.ecommerce.entities.ItemPedido;
import com.residencia.ecommerce.repositories.ItemPedidoRepository;

@Service
public class ItemPedidoService {

	@Autowired
	ItemPedidoRepository itemPedidoRepo;

	public List<ItemPedido> listarItemPedidos() {
		return itemPedidoRepo.findAll();
	}

	public ItemPedido getItemPedidoPorId(Long id) {
		return itemPedidoRepo.findById(id).orElse(null);
	}

	public ItemPedido salvarItemPedido(ItemPedido itemPedido) {
		return itemPedidoRepo.save(itemPedido);
	}

	public ItemPedido atualizarItemPedido(ItemPedido itemPedido) {
		return itemPedidoRepo.save(itemPedido);
	}

	public boolean deletarItemPedido(ItemPedido itemPedido) {
		// verifica se itemPedido é valido
		if (itemPedido == null)
			return false;

		// verifica se existe no banco
		ItemPedido itemPedidoExistente = getItemPedidoPorId(itemPedido.getIdItemPedido());
		if (itemPedidoExistente == null)
			return false;

		// deleta itemPedido
		itemPedidoRepo.delete(itemPedido);

		// verifica se foi deletado de fato
		ItemPedido itemPedidoContinuaExistindo = getItemPedidoPorId(itemPedido.getIdItemPedido());
		if (itemPedidoContinuaExistindo == null)
			return true;
		return false;
	}

}
