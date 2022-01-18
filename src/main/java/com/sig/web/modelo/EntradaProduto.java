package com.sig.web.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class EntradaProduto implements Serializable{

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long codigo;
	
	@Column(nullable = true)
	private String 	notaFical;
	
	@Column(nullable = false)

	private String dataCompra;
	
	@Column(nullable = false)

	private String dataEntrega;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp = new Date();
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario Usuario;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Fornecedor fornecedor;
	
	
	@Column(nullable = true)
	private String 	observacao;


	public long getCodigo() {
		return codigo;
	}


	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}


	public String getNotaFical() {
		return notaFical;
	}


	public void setNotaFical(String notaFical) {
		this.notaFical = notaFical;
	}


	public String getDataCompra() {
		return dataCompra;
	}


	public void setDataCompra(String dataCompra) {
		this.dataCompra = dataCompra;
	}


	public String getDataEntrega() {
		return dataEntrega;
	}


	public void setDataEntrega(String dataEntrega) {
		this.dataEntrega = dataEntrega;
	}


	public Date getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}


	public Usuario getUsuario() {
		return Usuario;
	}


	public void setUsuario(Usuario usuario) {
		Usuario = usuario;
	}


	public Fornecedor getFornecedor() {
		return fornecedor;
	}


	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}


	public String getObservacao() {
		return observacao;
	}


	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
}
