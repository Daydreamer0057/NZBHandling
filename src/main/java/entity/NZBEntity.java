package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ARTICLES")
public class NZBEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_nzb;

	@Column(name = "Title")
	private String title;

	@Column(name = "Name")
	private String name;

	@Column(name = "Body")
	private String body;

	public NZBEntity() {
		// TODO Auto-generated constructor stub
	}

	public NZBEntity(String title, String name) {
		this.title = title;
		this.name = name;
	}

	public Long getId_nzb() {
		return id_nzb;
	}

	public void setId_nzb(Long id_nzb) {
		this.id_nzb = id_nzb;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	// Getter and Setter methods
}