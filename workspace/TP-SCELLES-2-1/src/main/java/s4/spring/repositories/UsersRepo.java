package s4.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import s4.spring.entities.User;

@Repository
public interface UsersRepo extends JpaRepository<User, Integer> {
	public User findByLogin(String login);

}
