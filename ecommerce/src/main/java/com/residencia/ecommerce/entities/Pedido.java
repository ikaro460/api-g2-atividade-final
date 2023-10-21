package com.residencia.ecommerce.entities;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "idPedido", scope=Pedido.class)
@Entity
@Table(name = "pedido")
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pedido")
	private Long idPedido;
	
	@Column(name = "data_pedido")
	private Date dataPedido;
	
	@Column(name = "data_envio")
	private Date dataEnvio;
	
	@Column(name = "data_entrega")
	private Date dataEntrega;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "valor_total", nullable = false)
	private BigDecimal valorTotal;
	
	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido> itemPedidos;
	
	@ManyToOne
	@JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
	private Cliente cliente;

	// construtores
	
	public Pedido() {
		//data do pedido é gerada automaticamente na hora do computador
		dataPedido = new Date();
		
		//data de envio é calculada dois dias depois do pedido
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(dataPedido);
	    calendar.add(Calendar.DAY_OF_MONTH, 2);
	    dataEnvio = calendar.getTime();
	    
	    //data de entrega é calculada tres dias depois do envio
	    Calendar calendar2 = Calendar.getInstance();
	    calendar2.setTime(dataEnvio);
	    calendar2.add(Calendar.DAY_OF_MONTH, 3);
	    dataEntrega = calendar2.getTime();
	    
	}
	
	// gets e sets
	
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
	
	public Date getDataEntrega() {
		return dataEntrega;
	}
	
	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
	
	public Date getDataEnvio() {
		return dataEnvio;
	}
	
	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemPedido> getItemPedidos() {
		return itemPedidos;
	}

	public void setItemPedidos(List<ItemPedido> itemPedidos) {
		this.itemPedidos = itemPedidos;
	}


	

}
