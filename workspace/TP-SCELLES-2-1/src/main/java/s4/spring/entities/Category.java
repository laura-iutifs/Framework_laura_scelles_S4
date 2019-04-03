package s4.spring.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String name;

	@OneToMany
	private List<Script> script;
	
	public Category() {
		super();
	}

	// -----------------------------
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// -----------------------------
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// -----------------------------
	public List<Script> getScript() {
		return script;
	}

	public void setScript(List<Script> script) {
		this.script = script;
	}
}
