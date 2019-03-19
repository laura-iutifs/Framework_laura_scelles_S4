package s4.spring.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Script {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String title;
	private String description;
	private String content;
	private String creationDate;
	
	// ---------------------------------------------------------

	@ManyToOne
	private Language language;

	@ManyToOne
	private User user;

	@OneToMany
	private List<History> history;

	@ManyToOne
	private Category categorie;

	public int getId() {
		return id;
	}
	// ---------------------------------------------------------

	public Script() {
		history = new ArrayList<>();
		//penser a garder une trace des differentes versions ecrites
	}
	
	// --------------------- Getters / Setters ------------------
	
	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

}
