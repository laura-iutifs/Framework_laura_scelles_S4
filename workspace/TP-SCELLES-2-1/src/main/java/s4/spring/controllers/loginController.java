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

import s4.spring.entities.Language;
import s4.spring.entities.Script;
import s4.spring.entities.User;
import s4.spring.repositories.CateroryRepo;
import s4.spring.repositories.HistoryRepo;
import s4.spring.repositories.LangageRepo;
import s4.spring.repositories.ScriptRepo;
import s4.spring.repositories.UsersRepo;

@Controller
@RequestMapping("")
public class loginController {

	@Autowired
	private UsersRepo usersRepo;

	@Autowired
	private ScriptRepo scriptsRepo;
	
	@Autowired
	private HistoryRepo historiesRepo; 
	
	@Autowired
	private LangageRepo languagesRepo; 
	
	@Autowired
	private CateroryRepo categoriesRepo; 

	// ---------------USER------------------------

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
	public String connexion(Model model) {
		model.addAttribute("utilisateur", new User());
		return "login";
	}

	@PostMapping("login")
	public RedirectView connexion(Model model, User utilisateur, HttpSession session) {
		// String login, String password, String email, String identity
		User user = usersRepo.findByLogin(utilisateur.getLogin());

		if (user != null) {
			System.err.println("user existe");
			session.setAttribute("UtilisateurConnecte", user);
			return new RedirectView("/index");
		}
		System.out.println("user existe pas !");
		// si il n'existe pas alors on demande reconnexion
		return new RedirectView("/login");
	}

	@GetMapping("index")
	public String index(Model model, HttpSession session) {
		if (isConnected(session)) {
			//on ajoute au model l utilisateur connecte et ses scripts
			model.addAttribute("user", session.getAttribute("UtilisateurConnecte"));
			model.addAttribute("scripts", scriptsRepo.findAll());
			return "index";
		} else {
			return "login";
		}
	}

	// si utilisateur connecte => true
	public boolean isConnected(HttpSession session) {
		if (session.getAttribute("UtilisateurConnecte") != null) {
			return true;
		} else {
			return false;
		}
	}
	
	// revenir sur la page connexion si utilisateur se deconnecte
	@GetMapping("deconnexion")
	public RedirectView deconnexion(Model model, HttpSession session) {
		session.removeAttribute("UtilisateurConnecte");
		return new RedirectView("/login");
	}

	//-----------------SCRIPT----------------------------
	@GetMapping("indexS")
	public String afficherScripts(Model model) {
		//afficher liste des scripts
		model.addAttribute("script", new Script());
		model.addAttribute("langages", languagesRepo.findAll());
		model.addAttribute("categories", categoriesRepo.findAll());
		return "indexS";
	}
	
	@RequestMapping("creerScript")
	@ResponseBody
	public String createScript() {
		Script script = new Script();
		script.setTitle("Mon premier script");
		script.setDescription("c'est un script d'essai");
		script.setContent("oESHDVPZOuhf ncefhnZPEF?CzhfcpiZNFHCIz fhZIZPZGRNCHFFHU");

		scriptsRepo.save(script);
		return script + " a ete ajoute a la base de donnees.";
	}
	
	
	
	
	
	
}
