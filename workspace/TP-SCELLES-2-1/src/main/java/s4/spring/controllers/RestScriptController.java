package s4.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import s4.spring.entities.Script;
import s4.spring.repositories.ScriptRepo;

@RestController
@RequestMapping("/search")
public class RestScriptController {

	@Autowired
	private ScriptRepo scriptsRepo;

	@ResponseBody
	@GetMapping("")
	public List<Script> get(String titre) {
		List<Script> scripts = scriptsRepo.findAll();
		List<Script> resultatS = null;
		// liste des script qui correspondent

		for (Script sr : scripts) {
			if (sr.getTitle() == titre)
				resultatS.add(sr);
		}
		return resultatS; // renvoie le script trouvé
	}
}
