package s4.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import s4.spring.entities.Script;

@Repository
public interface ScriptRepo extends JpaRepository<Script, Integer>{

}
