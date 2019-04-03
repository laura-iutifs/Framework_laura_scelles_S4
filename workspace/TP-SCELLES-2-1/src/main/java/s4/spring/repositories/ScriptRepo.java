package s4.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import s4.spring.entities.Script;
import s4.spring.entities.User;

@Repository
public interface ScriptRepo extends JpaRepository<Script, Integer>{
	
	List<Script> findByUser(User user);
	Script findOneById(int id);

}
