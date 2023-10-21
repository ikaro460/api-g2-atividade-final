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
	
	@Autowired
	PedidoService pedidoService;

	public List<ItemPedido> listarItemPedidos() {
		return itemPedidoRepo.findAll();
	}

	public ItemPedido getItemPedidoPorId(Long id) {
		return itemPedidoRepo.findById(id).orElse(null);
	}

	public ItemPedido salvarItemPedido(ItemPedido itemPedido) {

		// CALCULA VALOR BRUTO
		BigDecimal obterValorBruto = gerarValorBruto(itemPedido.getPrecoVenda(), itemPedido.getQuantidade());
		itemPedido.setValorBruto(obterValorBruto);

		// CALCULA VALOR LIQUIDO
		BigDecimal obterValorLiquido = gerarValorLiquido(itemPedido.getValorBruto(),
				itemPedido.getPercentualDesconto());
		itemPedido.setValorLiquido(obterValorLiquido);
		

		// SALVA O ITEM
		ItemPedido itemSalvo = itemPedidoRepo.save(itemPedido);
		
		pedidoService.gerarValorTotal(pedidoRepo.findById(itemPedido.getPedido().getIdPedido()).get());
		
		// GERA RELATORIO
		RelatorioPedidoDTO pedidoDTO = gerarRelatorioDTO(
				pedidoRepo.findById(itemPedido.getPedido().getIdPedido()).get());

		// ENVIA EMAIL
		emailService.enviarEmail("ikaro.gaspar1@gmail.com", "Assunto entrará aqui.",
				("Mensagem: " + pedidoDTO.toString()));

		// ENVIA O ITEM SALVO COMO RESPOSTA
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

	// métodos valores bruto e liquido

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
		return new RelatorioPedidoDTO(pedido.getIdPedido(), pedido.getDataPedido(), pedido.getValorTotal(),
				itensPedidosDTO);
	}

	public RelatorioPedidoDTO gerarRelatorioDTO(Pedido pedido) {
		return convertPedidoToDTO(pedido);
	}

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
