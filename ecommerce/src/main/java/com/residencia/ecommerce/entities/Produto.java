package com.residencia.ecommerce.entities;

import java.math.BigDecimal;
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

/*
	CONSTRAINT produtos_pkey PRIMARY KEY (id_produto)
*/

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idProduto", scope = Produto.class)
@Entity
@Table(name = "produto")

public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_produto")
	private Long idProduto;

	@Column(name = "nome")
	private String nome;

	@Column(name = "descricao", unique = true, nullable = false)
	private String descricao;

	@Column(name = "qtd_estoque", nullable = false)
	private Integer qtdEstoque;

	@Column(name = "data_cadastro")
	private Date dataCadastro = new Date(); // DEFINE DATA NA CRIACAO DO PRODUTO

	@Column(name = "valor_unitario", nullable = false)
	private BigDecimal valorUnitario;

	@Column(name = "imagem")
	private byte[] imagem;

	/* RELACIONAMENTOS */

	// PRODUTO > ITEM_PEDIDO

	@OneToMany(mappedBy = "produto")
	private List<ItemPedido> itemPedidos;

	// PRODUTO > CATEGORIA
	@ManyToOne
	@JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria", nullable = false)
	private Categoria categoria;

	public Long getIdProduto() {
		return idProduto;
	}

	// CONSTRUTORES
	public Produto() {
	}

	public Produto(Long idProduto, String nome, String descricao, Integer qtdEstoque, BigDecimal valorUnitario,
			byte[] imagem, Categoria categoria) {
		this.idProduto = idProduto;
		this.nome = nome;
		this.descricao = descricao;
		this.qtdEstoque = qtdEstoque;
		this.valorUnitario = valorUnitario;
		this.imagem = imagem;
		this.categoria = categoria;
	}

	// GETTERS E SETTERS

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getQtdEstoque() {
		return qtdEstoque;
	}

	public void setQtdEstoque(Integer qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<ItemPedido> getItemPedidos() {
		return itemPedidos;
	}

	public void setItemPedidos(List<ItemPedido> itemPedidos) {
		this.itemPedidos = itemPedidos;
	}

}
