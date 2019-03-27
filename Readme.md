Framework Web
=============

Bienvenu sur mon repertoire qui regroupe les différents TD du module **Framework Web**
de l'option Web du DUT Informatique de l'Université de Caen, Ifs Campus 3.
Ils sont animés par **Jean Christophe HERON**.


Langages et framework utilisés
------------------------------

* HTML
* CSS
* JAVA SCRIPT
* Vue.Js - [Vue](https://vuejs.org/v2/guide/)

* Mustache - doc [Mustache](https://mustache.github.io/mustache.5.html)
* Spring Boot - [Spring](https://spring.io/)

--> Features Spring Boot - [click here](https://spring.io/projects/spring-boot)

* SEMANTIC UI 2.4.2 - [Semantic](https://semantic-ui.com/)
* VuetifyJs - [Vuetify](https://vuetifyjs.com/en/getting-started/quick-start)

Notions abordees
----------------

* Routes
* Controllers
* Models / Entities
* Composants
* Service Rest
* SPA (Single Page Apllication)
* JPA / Hibernate

Installer l'application Web
---------------------------

A partir de [Spring Initializr :](https://start.spring.io/)

	Groupe s4.spring
	 
	Artifact TD5
	 
	Packaging War
	 
	Description TD SpringBoot
	 
	Selectionner les dependances suivantes :
	 
	* Web
	* DevTools
	* Mustache
	* H2
	* JPA
	 
	Dezipper le projet telecharge
	 
	Importer le projet dans Eclipse (File/Import/Maven/Existing Maven Projects)

Configurer le projet dans application.properties

	spring.datasource.url=jdbc:h2:file:./data/messagerie;DB_CLOSE_ON_EXIT=FALSE
	 
	spring.datasource.username=sa
	 
	spring.datasource.password=
	 
	spring.datasource.driverClassName=org.h2.Driver
	 
	spring.jpa.hibernate.ddl-auto=update
	 
	spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
	 
	spring.h2.console.enabled=true
	 
	spring.h2.console.path=/h2-console
		
	server.servlet.context-path=/
	 
	# Mustache Template engine
	 
	spring.mustache.prefix=classpath:/templates/
	 
	spring.mustache.suffix=.html

Ou bien a partir d'un nouveau projet Spring Boot (File/New/Spring Starter Project)

Utile
-----
	Les annotations @RequestMapping permettent de définir le routage
		GET: /persons/1 ⇒ Affiche la personne d'id 1
		POST: /persons ⇒ Ajoute la personne passée en paramètre
	@GetMapping("/{id}")
	@PostMapping
	@ResponseBody => la methode retourne une valeur
	ModelAttribute => envoie des donnees de la vue au controller
	
	Comment appeller une vue : 
		@RequestMapping
		public String index() {
			return "main/index";
		}
	


Base de donnees
--------------
	Lien: http://localhost:4749/h2-console/
	Setting Name : messagerie
	JDBC URL : jdbc:h2:./data/messagerie


Etudiant
--------

Ces TDs sont realises par Laura SCELLES, etudiante en deuxieme annee de DUT Informatique
a l'universite de Caen, Ifs, Campus 3.