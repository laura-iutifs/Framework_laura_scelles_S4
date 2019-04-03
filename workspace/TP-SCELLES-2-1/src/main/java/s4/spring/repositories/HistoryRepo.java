package s4.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import s4.spring.entities.History;

@Repository
public interface HistoryRepo extends JpaRepository<History, Integer>{

}
