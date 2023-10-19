package com.residencia.ecommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.residencia.ecommerce.entities.Pedido;
import com.residencia.ecommerce.repositories.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	PedidoRepository pedidoRepo;

	public List<Pedido> listarPedidos() {
		return pedidoRepo.findAll();
	}

	public Pedido getPedidoPorId(Long id) {
		return pedidoRepo.findById(id).orElse(null);
	}

	public Pedido salvarPedido(Pedido pedido) {
		return pedidoRepo.save(pedido);
	}

	public Pedido atualizarPedido(Pedido pedido) {
		return pedidoRepo.save(pedido);
	}

	public boolean deletarPedido(Pedido pedido) {
		// verifica se pedido é valido
		if (pedido == null)
			return false;

		// verifica se existe no banco
		Pedido pedidoExistente = getPedidoPorId(pedido.getIdPedido());
		if (pedidoExistente == null)
			return false;

		// deleta pedido
		pedidoRepo.delete(pedido);

		// verifica se foi deletado de fato
		Pedido pedidoContinuaExistindo = getPedidoPorId(pedido.getIdPedido());
		if (pedidoContinuaExistindo == null)
			return true;
		return false;
	}

}
