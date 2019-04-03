package s4.spring.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Language {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String name;

	@OneToMany
	private List<Script> scripts;
	
	public Language() {
		super();
	}
	

	public List<Script> getScripts() {
		return scripts;
	}

	public void setScripts(List<Script> scripts) {
		this.scripts = scripts;
	}

	private int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}

	private String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

}
