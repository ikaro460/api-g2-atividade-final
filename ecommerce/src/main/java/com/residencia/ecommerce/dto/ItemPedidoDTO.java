package com.residencia.ecommerce.dto;

import java.math.BigDecimal;

public class ItemPedidoDTO {

	/*
	 * código e nome do produto, preço de venda, quantidade, valor bruto, percentual
	 * de desconto e valor líquido.
	 */

	private Long idItemPedido;

	private String nomeProduto;

	private BigDecimal precoVenda;

	private Integer quantidade;

	private BigDecimal valorBruto;

	private BigDecimal percentualDesconto;

	private BigDecimal valorLiquido;

	// CONSTRUTORES

	public ItemPedidoDTO() {

	}

	public ItemPedidoDTO(Long idItemPedido, String nomeProduto, BigDecimal precoVenda, Integer quantidade,
			BigDecimal valorBruto, BigDecimal percentualDesconto, BigDecimal valorLiquido) {

		this.idItemPedido = idItemPedido;
		this.nomeProduto = nomeProduto; // BUSCA O NOME DO PRODUTO PELO ID
										// PASSADO NO PARAMETRO
		this.precoVenda = precoVenda;
		this.quantidade = quantidade;
		this.valorBruto = valorBruto;
		this.percentualDesconto = percentualDesconto;
		this.valorLiquido = valorLiquido;
	}

	// GETTERS E SETTERS

	public Long getIdItemPedido() {
		return idItemPedido;
	}

	public void setIdItemPedido(Long idItemPedido) {
		this.idItemPedido = idItemPedido;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public BigDecimal getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(BigDecimal precoVenda) {
		this.precoVenda = precoVenda;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorBruto() {
		return valorBruto;
	}

	public void setValorBruto(BigDecimal valorBruto) {
		this.valorBruto = valorBruto;
	}

	public BigDecimal getPercentualDesconto() {
		return percentualDesconto;
	}

	public void setPercentualDesconto(BigDecimal percentualDesconto) {
		this.percentualDesconto = percentualDesconto;
	}

	public BigDecimal getValorLiquido() {
		return valorLiquido;
	}

	public void setValorLiquido(BigDecimal valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

}
