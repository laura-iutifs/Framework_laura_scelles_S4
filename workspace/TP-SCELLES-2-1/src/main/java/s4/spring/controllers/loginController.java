package s4.spring.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import s4.spring.entities.User;
import s4.spring.repositories.ScriptRepo;
import s4.spring.repositories.UsersRepo;

@Controller
@RequestMapping("")
public class loginController {

	@Autowired
	private UsersRepo usersRepo;

	@Autowired
	private ScriptRepo scriptsRepo;

	// ---------------------------------------

	@RequestMapping("new")
	@ResponseBody
	public String newUtilisateur() {
		User user = new User();
		user.setLogin("laura");
		user.setPassword("toor");
		user.setIdentity("Laura SCELLES");
		user.setEmail("laurascelles18@gmail.com");

		usersRepo.save(user);
		return user.getIdentity() + " a ete ajoute a la base de donnee";
	}
	
	@GetMapping("login")
	public String Vuelogin(Model model) {
		model.addAttribute("utilisateur", new User());
		return "login";
	}
	
	@PostMapping("login")
	public RedirectView creerUser(Model model, User utilisateur, HttpSession session) {
		// String login, String password, String email, String identity
		User user = usersRepo.findByLogin(utilisateur.getLogin());

		if (user != null) {
			session.setAttribute("UtilisateurConnected", user);
			return new RedirectView("/index");
		}
		// si il n'existe pas alors on demande relogin
		return new RedirectView("/login");
	}

	@GetMapping("delogin")
	public RedirectView delogin(Model model, HttpSession session) {
		session.removeAttribute("UtilisateurConnected");
		return new RedirectView("/login");
	}

	@GetMapping("index")
	public String index(Model model, HttpSession session) {
		if (isConnected(session)) {
			model.addAttribute("user", session.getAttribute("connectedUser"));
			model.addAttribute("scripts", scriptsRepo.findAll());
			return "index";
		}else {
			return "index";
		}
	}
	
	// si utilisateur connecte => true
	public boolean isConnected(HttpSession session) {
		if (session.getAttribute("connectedUser") != null) {
			return true;
		} else {
			return false;
		}
	}
}
