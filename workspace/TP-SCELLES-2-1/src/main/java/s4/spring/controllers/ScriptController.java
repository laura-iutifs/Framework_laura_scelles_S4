package s4.spring.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import s4.spring.entities.History;
import s4.spring.entities.Script;
import s4.spring.entities.User;
import s4.spring.repositories.CateroryRepo;
import s4.spring.repositories.HistoryRepo;
import s4.spring.repositories.LangageRepo;
import s4.spring.repositories.ScriptRepo;

@Controller
@RequestMapping("")
public class ScriptController {
	
	@Autowired
	private ScriptRepo scriptsRepo;

	@Autowired
	private HistoryRepo historiesRepo;

	@Autowired
	private LangageRepo languagesRepo;

	@Autowired
	private CateroryRepo categoriesRepo;
	
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
				script = new Script(); //nouveau script

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
				Script script = opt.get();
				model.addAttribute("script", script);
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
