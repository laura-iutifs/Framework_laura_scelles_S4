package s4.spring.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import s4.spring.entities.History;
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
			// on ajoute au model l utilisateur connecte et ses scripts
			model.addAttribute("user", session.getAttribute("UtilisateurConnecte"));
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
	@GetMapping("logout")
	public String deconnexion(Model model, HttpSession session) {
		session.removeAttribute("UtilisateurConnecte");
		// return new RedirectView("/logout");
		return "logout";
	}

	// -----------------SCRIPT----------------------------
	@GetMapping("script")
	public String afficherScripts(Model model, HttpSession session) {
		// afficher liste des scripts
		User user = (User) session.getAttribute("UtilisateurConnecte");
		model.addAttribute("scripts", user.getScripts());
		model.addAttribute("langages", languagesRepo.findAll());
		model.addAttribute("categories", categoriesRepo.findAll());
		return "script";
	}

	@RequestMapping("creerScript")
	@ResponseBody
	public String createScript(HttpSession session) {
		Script s1 = new Script();

		s1.setTitle("Script echo");
		s1.setCreationDate("24/03/2019");
		s1.setDescription("comment afficher quelque chose (.bat)");
		s1.setContent("echo Hello World!");
		s1.setUser((User) session.getAttribute("UtilisateurConnecte"));

		Script s2 = new Script();
		s2.setTitle("$variable");
		s2.setCreationDate("02/04/2019");
		s2.setDescription("afficher contenu d'une variable");
		s2.setContent("echo \"Bienvenue $prenom\";");
		s2.setUser((User) session.getAttribute("UtilisateurConnecte"));

		System.out.println(s1);
		System.out.println(s2);
		scriptsRepo.save(s1);
		scriptsRepo.save(s2);
		return "s1 et s2 ont ete ajoutes a la base de donnees";
	}

	// ajouter un script

	@GetMapping("script/new")
	public String ajouterScript(Model model) {
		model.addAttribute("script", new Script());
		model.addAttribute("languages", languagesRepo.findAll());
		model.addAttribute("categories", categoriesRepo.findAll());
		return "script/new";
	}

	@PostMapping("script/submit")
	public RedirectView ajouterScript(@ModelAttribute("nouveauScript") Script nouveauScript, Model model,
			HttpSession session) {
		System.out.println(nouveauScript); // affichage pour debug
		Optional<Script> opt = scriptsRepo.findById(nouveauScript.getId());

		Script script;
		if (opt.isPresent()) {
			System.out.println("script existant ");
			script = opt.get(); // creation version a sauvegarder
			History history = new History();
			history.setScripts(script);
			history.setContent(script.getContent());
			history.setDate(getDate());
			historiesRepo.save(history);
		} else {
			script = new Script();

			script.setId(nouveauScript.getId());
			script.setTitle(nouveauScript.getTitle());
			script.setUser( (User) session.getAttribute("UtilisateurConnecte"));
			script.setDescription(nouveauScript.getDescription());
			script.setContent(nouveauScript.getContent());
			script.setLanguage(nouveauScript.getLanguage());
			script.setCategory(nouveauScript.getCategory());
			script.setCreationDate(getDate());

			System.out.println("nouveau script");
		}
		scriptsRepo.save(script);
		return new RedirectView("index");
	}

	@GetMapping("script/{id}")
	public String modifier(@PathVariable int id, Model model) {

		Optional<Script> opt = scriptsRepo.findById(id);
		if (opt.isPresent()) {
			Script nouveauScript = opt.get();
			model.addAttribute("nouveauScript", nouveauScript);
		}
		model.addAttribute("languages", languagesRepo.findAll());
		model.addAttribute("categories", categoriesRepo.findAll());
		return "script/new";
	}

	//supprimer script
	
	@GetMapping("script/delete/{id}")
	public RedirectView delete(@PathVariable int id, Script script) {
		scriptsRepo.delete(script);
		return new RedirectView("/index");
		//TODO trouver pourquoi il faut se deconnecter pour actualiser liste des scripts
	}

	// ----------------fonction transformation date---------------------------
	
	private String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
