package s4.spring.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import s4.spring.entities.Category;
import s4.spring.entities.History;
import s4.spring.entities.Language;
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
	public String afficherScripts(ModelMap model, HttpSession session) {
		// afficher liste des scripts
		User user = (User) session.getAttribute("UtilisateurConnecte");
		if (user != null) {
			model.addAttribute("scripts", user.getScripts());
			model.addAttribute("langages", languagesRepo.findAll());
			model.addAttribute("categories", categoriesRepo.findAll());
			return "script";
		}
		return "login";
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
	public String ajouterScript(ModelMap model, HttpSession session) {
		User user = (User) session.getAttribute("UtilisateurConnecte");

		if (user != null) {
			model.addAttribute("script", new Script());
			model.addAttribute("languages", languagesRepo.findAll());
			model.addAttribute("categories", categoriesRepo.findAll());
			return "script/new";
		} else
			return "login";
	}

	@PostMapping("script/submit")
	public RedirectView ajouterScript(
			@RequestParam("id") int id, 
			@RequestParam("title") String title,
			@RequestParam("description") String description, 
			@RequestParam("content") String content,
			@RequestParam("category") int categorie, 
			@RequestParam("language") int language, 
			HttpSession session) {

		User user = (User) session.getAttribute("UtilisateurConnecte");
		Optional<Script> opt = scriptsRepo.findById(id);

		Script nScript;
		if (opt.isPresent()) {//on le cherche

			nScript = scriptsRepo.findOneById(id);
			System.out.println("debug ajouterScript __ " + opt);// debug

			// ajout historique
			History hist = new History();
			hist.setScripts(nScript);
			hist.setContent(nScript.getContent());
			hist.setDate(getDate());
			historiesRepo.save(hist);

		} else {//on le creer
			nScript = new Script();

			// creation nouveau script
			nScript.setUser(user);
			nScript.setCreationDate(getDate());
			nScript.setTitle(title);
			nScript.setDescription(description);
			nScript.setContent(content);
			nScript.setCategory(categoriesRepo.findOneById(categorie));
			nScript.setLanguage(languagesRepo.findOneById(language));
			nScript.setUser((User) session.getAttribute("UtilisateurConnecte"));
		}
		scriptsRepo.save(nScript); //enregistrer
		return new RedirectView("/script");
	}

	@GetMapping("script/edit/{id}")
	public String modifier(@PathVariable int id, ModelMap model) {

		Optional<Script> opt = scriptsRepo.findById(id);
		if (opt.isPresent()) {
			// debug
			// System.out.println(sc.toString());
			Script newS = opt.get();
			model.addAttribute("script", newS);

			List<Category> categories = categoriesRepo.findAll();
			List<Language> languages = languagesRepo.findAll();

			Category cateSelected = newS.getCategory();
			categories.remove(cateSelected);
			model.addAttribute("cateSelected", cateSelected);

			Language langselected = newS.getLanguage();
			languages.remove(langselected);
			model.addAttribute("langselected", langselected);

			model.addAttribute("categories", categories);
			model.addAttribute("languages", languages);

			return "script/edit";
		}
		return "index";

	}

	@PostMapping("script/edit/{id}")
	public String modifier(@ModelAttribute("script") Script script, @PathVariable int id, @RequestParam int categoryId,
			@RequestParam int languageId, @RequestParam String comment, ModelMap model) {

		Optional<Script> opt = scriptsRepo.findById(id);
		if (opt.isPresent()) {
			Script sc = opt.get();
			model.addAttribute("script", sc);

			History historique = new History();
			historique.setComment(comment);
			historique.setContent(script.getContent());
			historique.setDate(getDate());
			historique.setScripts(script);
			// ajouter au repository
			historiesRepo.save(historique);

			// sauvegarder copy script
			sc.setContent(script.getContent());
			sc.setLanguage(languagesRepo.findOneById(languageId));
			sc.setCategory(categoriesRepo.findOneById(categoryId));
			sc.setTitle(script.getTitle());
			sc.setDescription(script.getDescription());

			scriptsRepo.save(sc);
		}
		return "script/edit";
	}

	// supprimer script

	@GetMapping("script/delete/{id}")
	public RedirectView delete(@PathVariable int id) {
		Script script = scriptsRepo.findOneById(id);
		scriptsRepo.delete(script);
		return new RedirectView("/index");
	}

	// ----------------fonction transformation date---------------------------

	private String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
