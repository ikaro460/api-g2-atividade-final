package com.residencia.ecommerce.dto;

public class ViaCepResponse {

	/*
	 * código e nome do produto, preço de venda, quantidade, valor bruto, percentual
	 * de desconto e valor líquido.
	 */

	private String cep;
	private String logradouro;
	private String bairro;
	private String localidade;
	private String complemento;
	private String uf;

	// CONSTRUTORES

	public ViaCepResponse() {
	}

	public ViaCepResponse(String cep, String logradouro, String bairro, String localidade,
			String complemento, String uf) {
		this.cep = cep;
		this.logradouro = logradouro;
		this.bairro = bairro;
		this.localidade = localidade;
		this.complemento = complemento;
		this.uf = uf;
	}
	
	// GETTERS E SETTERS

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}
	
	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

}
