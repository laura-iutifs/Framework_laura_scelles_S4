package s4.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import s4.spring.entities.Category;

@Repository
public interface CateroryRepo extends JpaRepository<Category, Integer>{
	public Category findById(int id);
}
