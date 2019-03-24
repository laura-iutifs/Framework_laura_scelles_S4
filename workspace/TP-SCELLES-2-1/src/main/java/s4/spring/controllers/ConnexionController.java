package s4.spring.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import s4.spring.entities.User;
import s4.spring.repositories.UsersRepo;

@Controller
@RequestMapping("/accueil/")
public class ConnexionController {

	@Autowired
	private UsersRepo usersRepo;

	@PostMapping("login")
	public RedirectView creerUser(Model model, User utilisateur, HttpSession session) {
		// String login, String password, String email, String identity
		User user = usersRepo.findByLogin(utilisateur.getLogin());
		
		if (user != null) {
			session.setAttribute("UtilisateurConnected", user);
			return new RedirectView("/index");
		}
		//si il n'existe pas alors on demande reconnexion
		return new RedirectView("/connexion");
	}
	
	@GetMapping("login")
	public String loginView(Model model) {
		model.addAttribute("utilisateur", new User());
		return "connexion";
	}
	
	
	
}
