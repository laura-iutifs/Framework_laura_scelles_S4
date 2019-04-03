package s4.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import s4.spring.entities.Language;

@Repository
public interface LangageRepo extends JpaRepository<Language, Integer>{
	public Language findById(int id);
}
