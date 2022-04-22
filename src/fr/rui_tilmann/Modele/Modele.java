package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.*;
import fr.rui_tilmann.Vue.Observable;
import fr.rui_tilmann.Vue.Vue;

import java.util.*;

public class Modele extends Observable
{

	private final Plateau plateau;
	private final List<Joueur> joueurs = new ArrayList<>();
	private final NiveauEau niveauEau;
	private final PileCartes pileCartes;

	public static int NOMBRE_JOUEURS;

	private int idJoueur = 0;
	private int nbActions = 3;

	private final HashMap<Artefact, Boolean> tresorPris = new HashMap<>(4);

	public boolean actionUtiliseePilote = false;
	public boolean actionSpeIngenieur = false;

	public Modele(Difficulte difficulte, int nbJoueurs)
	{
		NOMBRE_JOUEURS = nbJoueurs;

		plateau = new Plateau(this);

		initJoueurs();

		niveauEau = new NiveauEau(this, difficulte);
		pileCartes = new PileCartes(this);

		distribueCartesJoueurs();
		// test recup artefact

		joueurs.get(0).getCartes().clear();
		for(int i = 0; i < 5; i++)
			joueurs.get(0).getCartes().add(Carte.TERRE);

	}

	private void initJoueurs()
	{
		// tirer 4 roles aleatoires parmi les 6
		List<Role> roles = Arrays.asList(Role.values());
		Collections.shuffle(roles);

		for(int i = 0; i < NOMBRE_JOUEURS; i++)
		{
			joueurs.add(new Joueur(this, roles.get(i), plateau.caseAleatoire(Etat.SECHE, Etat.INONDEE)));
			System.out.println(joueurs.get(i));
		}
	}

	private void distribueCartesJoueurs()
	{
		new Timer().schedule(new TimerTask() {
			int i = 0;

			@Override
			public void run() {
				joueurs.get(i).piocheCartes(false);
				i++;
				if(i == NOMBRE_JOUEURS) cancel();
			}
		}, 0, 100);
	}

	public Plateau getPlateau()
	{
		return plateau;
	}

	public List<Joueur> getJoueurs()
	{
		return joueurs;
	}

	public Joueur getJoueur(int i)
	{
		return joueurs.get(i);
	}

	public Joueur getCurrentJoueur()
	{
		return joueurs.get(idJoueur);
	}
	public int getCurrentJoueurId()
	{
		return idJoueur;
	}

	public float getniveauEau()
	{
		return niveauEau.getNiveau();
	}

	public PileCartes getPileCartes()
	{
		return pileCartes;
	}

	private void resetActions()
	{
		nbActions = 3;
	}

	public boolean actionsRestantes()
	{
		return nbActions > 0;
	}

	// TODO méthode à appeler quand on pioche une Carte MONTEE_DES_EAUX
	public void monteeEau()
	{
		niveauEau.monteeEau();

		if(niveauEau.getNombreCartes() == -1)
			finDePartie(GameOver.NIVEAU_EAU_TROP_HAUT);
	}

	public void recupereArtefact(Artefact artefact) {
		tresorPris.put(artefact, true);
		enleverZones(artefact);
		useAction();
	}

	private void enleverZones(Artefact artefact)
	{
		for(int x = 0; x < Plateau.LENGTH; x++)
			for(int y = 0; y < Plateau.LENGTH; y++)
			{
				Case c = plateau.getCase(x, y);

				if(c.getType().toArtefact() == artefact)
					c.setType(Zone.NORMALE);
			}
	}

	public boolean hasArtefact(Artefact artefact)
	{
		return tresorPris.getOrDefault(artefact, false);
	}

	public void inonderCases()
	{
		for(int i = 0; i < niveauEau.getNombreCartes(); i++)
			monteeEauCase(pileCartes.caseAInonder());
	}

	private void monteeEauCase(Case c)
	{
		switch(c.getEtat())
		{
			case SECHE:
				c.setEtat(Etat.INONDEE);
				break;

			case INONDEE:
				c.setEtat(Etat.SUBMERGEE);
				pileCartes.removeCaseAInonder(c);

				// perdu si un joueur est sur la case et il n'y a pas de case adjacente pas submergee
				if(!c.getJoueurs().isEmpty())
				{
					boolean adjSub = true;

					for(Direction d : Direction.values())
						if(c.adjacente(d).getEtat() != Etat.SUBMERGEE)
							adjSub = false;

					if(adjSub)
						finDePartie(GameOver.NOYADE);

					// TODO le joueur peut se sauver si il est dans l'eau
					// si ça fonc pas pas grave car à son tour il pourra sortir mais faudra rajouter une action du coup
					/*
					int currentId = idJoueur;
					List<Joueur> joueursCase = c.getJoueurs();
					new Timer().schedule(new TimerTask()
					{
						int jId = 0;

						@Override
						public void run()
						{
							if(jId == joueursCase.size()) {cancel(); return;}

							idJoueur = joueursCase.get(jId).getId();
							for(int i = 0; i < 2; i++)
								useAction();

							if(actionsRestantes()) return;
							jId++;
						}
					}, 0, 10);
					idJoueur = currentId;
					 */
				}

				// perdu si c'est l'heliport
				if(c.getType() == Zone.HELIPORT)
					finDePartie(GameOver.HELIPORT_SUBMERGE);

				//Perdu si 2 zone du meme type tombe
				if(c.getType() != Zone.NORMALE) {
					plateau.removeZoneImportante(c);

					if(plateau.zoneImportantePasSubmergee(c.getType()) == 0 && !tresorPris.getOrDefault(c.getType().toArtefact(), false))
						finDePartie(GameOver.TRESOR_IRRECUPERABLE);
				}
				break;
		}
	}

	public void useAction()
	{
		nbActions--;
		if(nbActions == 0) finDeTour();
	}

	public void finDeTour()
	{
		Joueur joueur = joueurs.get(idJoueur);
		joueur.piocheCartes();

		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				if(joueurs.stream().allMatch(j -> j.getCartes().size() <= 5))
				{
					actionUtiliseePilote = false;
					actionSpeIngenieur = false;

					idJoueur = (idJoueur + 1) % NOMBRE_JOUEURS;
					resetActions();
					inonderCases();
					cancel();
				}
			}
		}, 2000, 10);
	}

	private void finDePartie(GameOver state)
	{
		nbActions = 0;

		switch(state)
		{
			case GAGNE:
				System.exit(1);
				break;

			case NOYADE:
				System.exit(2);
				break;

			case HELIPORT_SUBMERGE:
				System.exit(3);
				break;

			case NIVEAU_EAU_TROP_HAUT:
				System.exit(4);
				break;

			case TRESOR_IRRECUPERABLE:
				System.exit(5);
				break;
		}
	}

}
