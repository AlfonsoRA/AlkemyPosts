package com.alfonso.alkemy.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * @author Futuro
 *
 */
@Entity 
@Table(name = "posts")
@SQLDelete(sql= "UPDATE posts SET eliminado = true WHERE id = ?")
@Where(clause = "eliminado = false")
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Size(min=3, max = 15, message="El titulo del Post debe contener entre 3 a 15 caracteres")
	private String titulo;
	@NotEmpty(message="El campo contenido no puede estar vacio!!")
	private String contenido;
	private String imagen;
	private String categoria;
	private Boolean eliminado;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date create_at;
	
	@PrePersist
	private void prePersist() {
		this.create_at = new Date();
		this.eliminado = false;
	}	

	public Post() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Date getCreate_at() {
		return create_at;
	}

	public void setCreate_at(Date create_at) {
		this.create_at = create_at;
	}

	public Boolean getEliminado() {
		return eliminado;
	}

	public void setEliminado(Boolean eliminado) {
		this.eliminado = eliminado;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", titulo=" + titulo + ", contenido=" + contenido + ", imagen=" + imagen
				+ ", categoria=" + categoria + ", eliminado=" + eliminado +", create_at=" + create_at + "]";
	}
}
