package com.residencia.ecommerce.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.residencia.ecommerce.entities.ItemPedido;

public class RelatorioPedidoDTO {

	private Long idPedido;
	
	private Date dataPedido;
	
	private BigDecimal valorTotal;
	
	private List<ItemPedido> itemPedidos;

	public RelatorioPedidoDTO() {
		
	}

	public RelatorioPedidoDTO(Long idPedido, Date dataPedido, BigDecimal valorTotal, List<ItemPedido> itemPedidos) {
		this.idPedido = idPedido;
		this.dataPedido = dataPedido;
		this.valorTotal = valorTotal;
		this.itemPedidos = itemPedidos;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public Date getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public List<ItemPedido> getItemPedidos() {
		return itemPedidos;
	}

	public void setItemPedidos(List<ItemPedido> itemPedidos) {
		this.itemPedidos = itemPedidos;
	}
	
	
	
	
}
