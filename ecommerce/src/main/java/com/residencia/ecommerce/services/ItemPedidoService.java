package com.residencia.ecommerce.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.residencia.ecommerce.dto.ItemPedidoDTO;
import com.residencia.ecommerce.dto.RelatorioPedidoDTO;
import com.residencia.ecommerce.entities.ItemPedido;
import com.residencia.ecommerce.entities.Pedido;
import com.residencia.ecommerce.entities.Produto;
import com.residencia.ecommerce.repositories.ItemPedidoRepository;
import com.residencia.ecommerce.repositories.PedidoRepository;
import com.residencia.ecommerce.repositories.ProdutoRepository;

@Service
public class ItemPedidoService {

	@Autowired
	ItemPedidoRepository itemPedidoRepo;

	@Autowired
	ProdutoRepository produtoRepo;

	@Autowired
	PedidoRepository pedidoRepo;

	@Autowired
	EmailService emailService;

	public List<ItemPedido> listarItemPedidos() {
		return itemPedidoRepo.findAll();
	}

	public ItemPedido getItemPedidoPorId(Long id) {
		return itemPedidoRepo.findById(id).orElse(null);
	}

	public ItemPedido salvarItemPedido(ItemPedido itemPedido) {
		BigDecimal obterValorBruto = gerarValorBruto(itemPedido.getPrecoVenda(), itemPedido.getQuantidade());
		itemPedido.setValorBruto(obterValorBruto);

		BigDecimal obterValorLiquido = gerarValorLiquido(itemPedido.getValorBruto(),
				itemPedido.getPercentualDesconto());
		itemPedido.setValorLiquido(obterValorLiquido);

		ItemPedido itemSalvo = itemPedidoRepo.save(itemPedido);
		RelatorioPedidoDTO relatorio = gerarRelatorioDTO(itemSalvo);
		emailService.enviarEmail("Teste@gmail.com", "Assunto entrará aqui.", relatorio.toString());

		return itemSalvo;
	}

	public ItemPedido atualizarItemPedido(ItemPedido itemPedido) {
		BigDecimal obterValorBruto = gerarValorBruto(itemPedido.getPrecoVenda(), itemPedido.getQuantidade());
		itemPedido.setValorBruto(obterValorBruto);

		BigDecimal obterValorLiquido = gerarValorLiquido(itemPedido.getValorBruto(),
				itemPedido.getPercentualDesconto());
		itemPedido.setValorLiquido(obterValorLiquido);
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

//	private RelatorioPedidoDTO convertPedidoToDTO(ItemPedido itemPedido) {
//
//		// CRIA LISTA RELAÇÃO DE ITENS DO PEDIDO VAZIA
//		List<ItemPedidoDTO> itensPedidosDTO = new ArrayList<>();
//		
//		RelatorioPedidoDTO relatorio = new RelatorioPedidoDTO();
//		
//		Pedido pedido = pedidoRepo.findById(itemPedido.getPedido().getIdPedido()).orElse(null);
//		
//		// PREENCHE A RELAÇÃO DE ITENS COM OS ITENS DO PEDIDO
//		for (ItemPedidoDTO item : relatorio.getItemPedidos()) {
//			
//			Produto produto = produtoRepo.findById(itemPedido.getProduto().getIdProduto()).orElse(null);
//			
//			
//			// CRIA DTO ITEM PEDIDO
//			ItemPedidoDTO itemPedidoDTO = new ItemPedidoDTO(
//					itemPedido.getProduto().getIdProduto(),
//					produto.getNome(),
//					itemPedido.getPrecoVenda(),
//					itemPedido.getQuantidade(),
//					itemPedido.getValorBruto(),
//					itemPedido.getPercentualDesconto(), 
//					itemPedido.getValorLiquido());
//			
//			
//
//			// ADICIONA DTO A RELAÇÃO DE ITENS
//			itensPedidosDTO.add(itemPedidoDTO);
//		}
//
//		// RETORNA UM NOVO RELATORIO CONSTRUIDO A PARTIR DOS DADOS TRATADOS
//		return new RelatorioPedidoDTO(pedido.getIdPedido(), pedido.getDataPedido(), pedido.getValorTotal(),
//				itensPedidosDTO);
//	}

	private RelatorioPedidoDTO convertPedidoToDTO(ItemPedido itemPedido) {
		
		Pedido pedido = itemPedido.getPedido();
		if (itemPedido == null || itemPedido.getPedido() == null || itemPedido.getProduto() == null
				|| pedido.getItemPedidos() == null) {
			// Se o itemPedido ou suas associações forem nulas, retorne um
			// RelatorioPedidoDTO vazio ou null, dependendo do que desejar.
			return new RelatorioPedidoDTO();
		}

		List<ItemPedidoDTO> itensPedidosDTO = new ArrayList<>();

		for (ItemPedido item : pedido.getItemPedidos()) {
			Produto produto = item.getProduto();

			ItemPedidoDTO itemPedidoDTO = new ItemPedidoDTO(produto.getIdProduto(), produto.getNome(),
					item.getPrecoVenda(), item.getQuantidade(), item.getValorBruto(), item.getPercentualDesconto(),
					item.getValorLiquido());

			itensPedidosDTO.add(itemPedidoDTO);
		}

		return new RelatorioPedidoDTO(pedido.getIdPedido(), pedido.getDataPedido(), pedido.getValorTotal(),
				itensPedidosDTO);
	}

	public RelatorioPedidoDTO gerarRelatorioDTO(ItemPedido itemPedido) {
		return convertPedidoToDTO(itemPedido);
	}

	// métodos valores bruto e liquido

	public BigDecimal gerarValorBruto(BigDecimal precoVenda, Integer quantidade) {
		BigDecimal valorBruto = precoVenda.multiply(BigDecimal.valueOf(quantidade));
		return valorBruto;
//			valor bruto = precovenda * quantidade
	}

	public BigDecimal gerarValorLiquido(BigDecimal valorBruto, BigDecimal percentual) {
		BigDecimal valorLiquido = valorBruto.subtract(valorBruto.multiply(percentual));
		return valorLiquido;
//			valor  liq = valor bruto - (valor bruto * percentual)		
	}

}
