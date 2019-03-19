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
		User u1 = new User();
		usersRepo.save(u1);
		
		return "index";
	}
	
	@GetMapping("login")
	public String afficherUsers(Model model) {
		model.addAttribute("utilisateur", new User() );
		return "index";
	}
	
	
	
}
