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

	@Autowired
	PedidoService pedidoService;

	public List<ItemPedido> listarItemPedidos() {
		return itemPedidoRepo.findAll();
	}

	public ItemPedido getItemPedidoPorId(Long id) {
		return itemPedidoRepo.findById(id).orElse(null);
	}

	public ItemPedido salvarItemPedido(ItemPedido itemPedido) {

		Produto produto = produtoRepo.findById(itemPedido.getProduto().getIdProduto()).orElse(null);

		if (produto != null) {
			itemPedido.setPrecoVenda(produto.getValorUnitario());
			if (produto.getQtdEstoque() >= itemPedido.getQuantidade()) {
				Integer qtd = produto.getQtdEstoque() - itemPedido.getQuantidade();
				produto.setQtdEstoque(qtd);
			} else {
				System.out.println("nao tem estoque suficiente");
				return null;
			}
		}

		// CRIA ITEM USANDO CONSTRUTOR
		ItemPedido itemSalvo = new ItemPedido(itemPedido.getIdItemPedido(), itemPedido.getQuantidade(),
				itemPedido.getPrecoVenda(), itemPedido.getPercentualDesconto(), itemPedido.getPedido(),
				itemPedido.getProduto());

		// SALVA O ITEM
		itemPedidoRepo.save(itemSalvo);

		// ATUALIZA O VALOR TOTAL DO PEDIDO REFERENTE AO ITEM
		pedidoService.gerarValorTotal(pedidoRepo.findById(itemPedido.getPedido().getIdPedido()).orElse(null));

		// GERA RELATORIO
		RelatorioPedidoDTO pedidoDTO = gerarRelatorioDTO(
				pedidoRepo.findById(itemPedido.getPedido().getIdPedido()).orElse(null));

		// ENVIA EMAIL

		emailService.enviarEmail("ikaro.gaspar1@gmail.com", "Assunto entrará aqui.", pedidoDTO.toString());

		// ENVIA O ITEM SALVO COMO RESPOSTA
		return itemSalvo;
	}

	public ItemPedido atualizarItemPedido(ItemPedido itemPedido) {
		// CRIA ITEM USANDO CONSTRUTOR
		ItemPedido itemSalvo = new ItemPedido(itemPedido.getIdItemPedido(), itemPedido.getQuantidade(),
				itemPedido.getPrecoVenda(), itemPedido.getPercentualDesconto(), itemPedido.getPedido(),
				itemPedido.getProduto());

		return itemPedidoRepo.save(itemSalvo);
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

		// verifica se item foi deletado de fato
		ItemPedido itemPedidoContinuaExistindo = getItemPedidoPorId(itemPedido.getIdItemPedido());
		if (itemPedidoContinuaExistindo == null) {
			// ATUALIZA O VALOR TOTAL DO PEDIDO REFERENTE AO ITEM
			pedidoService.gerarValorTotal(pedidoRepo.findById(itemPedido.getPedido().getIdPedido()).orElse(null));
			return true;
		}
		return false;
	}

	// métodos auxiliares

	private RelatorioPedidoDTO convertPedidoToDTO(Pedido pedido) {

		// CRIA LISTA RELAÇÃO DE ITENS DO PEDIDO VAZIA
		List<ItemPedidoDTO> itensPedidosDTO = new ArrayList<>();

		// VERIFICA SE PEDIDO CONTÉM ITENS
		if (pedido.getItemPedidos() != null && pedido.getItemPedidos().size() > 0) {

			// PREENCHE A RELAÇÃO DE ITENS COM OS ITENS DO PEDIDO
			for (ItemPedido itemPedido : pedido.getItemPedidos()) {

				Produto produto = produtoRepo.findById(itemPedido.getProduto().getIdProduto()).orElse(null);

				// CRIA DTO ITEM PEDIDO
				ItemPedidoDTO itemPedidoDTO = new ItemPedidoDTO(itemPedido.getProduto().getIdProduto(),
						produto.getNome(), itemPedido.getPrecoVenda(), itemPedido.getQuantidade(),
						itemPedido.getValorBruto(), itemPedido.getPercentualDesconto(), itemPedido.getValorLiquido());

				// ADICIONA DTO A RELAÇÃO DE ITENS
				itensPedidosDTO.add(itemPedidoDTO);
			}
		}

		// RETORNA UM NOVO RELATORIO CONSTRUIDO A PARTIR DOS DADOS TRATADOS
		RelatorioPedidoDTO relatorioFinalizado = new RelatorioPedidoDTO(pedido.getIdPedido(), pedido.getDataPedido(),
				pedido.getValorTotal(), itensPedidosDTO);

		System.out.println(relatorioFinalizado);

		return relatorioFinalizado;
	}

	public RelatorioPedidoDTO gerarRelatorioDTO(Pedido pedido) {
		return convertPedidoToDTO(pedido);
	}

}
