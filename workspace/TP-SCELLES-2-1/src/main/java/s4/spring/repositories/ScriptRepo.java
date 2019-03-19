package s4.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import s4.spring.entities.Script;

public interface ScriptRepo extends JpaRepository<Script, Integer>{

}
