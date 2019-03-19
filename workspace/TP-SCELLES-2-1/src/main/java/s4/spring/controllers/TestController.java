package s4.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import s4.spring.entities.User;
import s4.spring.repositories.UsersRepo;

@Controller
@RequestMapping("/accueil/")
public class TestController {

	@Autowired
	private UsersRepo usersRepo;

	@GetMapping("")
	public String creerUser() {
		// String login, String password, String email, String identity
		User u1 = new User("u1", "u1bfbrf", "u1@unicaen.fr", "Laura Scelles");
		User u2 = new User("u2", "u2grgze", "u2@unicaen.fr", "James Bond");
		User u3 = new User("u3", "u3tjhet", "u3@unicaen.fr", "Hello World");

		usersRepo.save(u1);
		usersRepo.save(u2);
		usersRepo.save(u3);

		return "index";
	}
	
	@GetMapping("login")
	public String afficherUsers(Model model) {
		List<User> users = usersRepo.findAll();
		model.addAttribute("users", users);
		return "index";
	}
	
}
