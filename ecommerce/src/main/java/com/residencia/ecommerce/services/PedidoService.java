package com.residencia.ecommerce.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.residencia.ecommerce.dto.ItemPedidoDTO;
import com.residencia.ecommerce.dto.RelatorioPedidoDTO;
import com.residencia.ecommerce.entities.ItemPedido;
import com.residencia.ecommerce.entities.Pedido;
import com.residencia.ecommerce.entities.Produto;
import com.residencia.ecommerce.repositories.PedidoRepository;
import com.residencia.ecommerce.repositories.ProdutoRepository;

@Service
public class PedidoService {

	@Autowired
	PedidoRepository pedidoRepo;
	
	@Autowired
	ProdutoRepository produtoRepo;

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

	private RelatorioPedidoDTO convertPedidoToDTO(Pedido pedido) {

		// CRIA LISTA RELAÇÃO DE ITENS DO PEDIDO VAZIA
		List<ItemPedidoDTO> itensPedidosDTO = new ArrayList<>();

		// PREENCHE A RELAÇÃO DE ITENS COM OS ITENS DO PEDIDO
		for (ItemPedido itemPedido : pedido.getItemPedidos()) {
			
			Produto produto = produtoRepo.findById(itemPedido.getProduto().getIdProduto()).orElse(null);
			
			
			// CRIA DTO ITEM PEDIDO
			ItemPedidoDTO itemPedidoDTO = new ItemPedidoDTO(
					itemPedido.getProduto().getIdProduto(),
					produto.getNome(),
					itemPedido.getPrecoVenda(),
					itemPedido.getQuantidade(),
					itemPedido.getValorBruto(),
					itemPedido.getPercentualDesconto(), 
					itemPedido.getValorLiquido());
			
			

			// ADICIONA DTO A RELAÇÃO DE ITENS
			itensPedidosDTO.add(itemPedidoDTO);
		}

		// RETORNA UM NOVO RELATORIO CONSTRUIDO A PARTIR DOS DADOS TRATADOS
		return new RelatorioPedidoDTO(pedido.getIdPedido(), pedido.getDataPedido(), pedido.getValorTotal(),
				itensPedidosDTO);
	}

	public RelatorioPedidoDTO gerarRelatorioDTO(Pedido pedido) {
		return convertPedidoToDTO(pedido);
	}

}
