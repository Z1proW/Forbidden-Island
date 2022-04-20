package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.*;
import fr.rui_tilmann.Vue.Observable;

import java.util.*;

public class Modele extends Observable
{

	private final Plateau plateau;
	private final List<Joueur> joueurs = new ArrayList<>();
	private final NiveauEau niveauEau;
	private final PileCartes pileCartes;

	private GameState state = GameState.EN_JEU;

	private int idJoueur = 0;
	private int nbActions = 3;

	private final HashMap<Artefact, Boolean> tresorPris = new HashMap<>(4);

	public Modele()
	{
		plateau = new Plateau(this);

		initJoueurs();

		niveauEau = new NiveauEau(this, Difficulte.NOVICE);
		pileCartes = new PileCartes(this);

		distribueCartesJoueurs();
		// test recup artefact
		/*
		joueurs.get(0).getCartes().clear();
		for(int i = 0; i < 5; i++)
			joueurs.get(2).getCartes().add(Carte.TERRE);
		*/
	}

	private void initJoueurs()
	{
		// tirer 4 roles aleatoires parmi les 6
		List<Role> roles = Arrays.asList(Role.values());
		Collections.shuffle(roles);

		for(int i = 0; i < 4; i++)
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
				if(i == 4) cancel();
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
			state = GameState.NIVEAU_EAU_TROP_HAUT;

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

					if(adjSub) state = GameState.NOYADE;
				}

				// perdu si c'est l'heliport
				if(c.getType() == Zone.HELIPORT)
					state = GameState.HELIPORT_SUBMERGE;

				//Perdu si 2 zone du meme type tombe
				if(c.getType() != Zone.NORMALE){
					plateau.removeZoneImportante(c);
					if(plateau.zoneImportantePasSubmergee(c.getType()) == 0 && !tresorPris.getOrDefault(c.getType().toArtefact(), false))
						state = GameState.TRESOR_IRRECUPERABLE;
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
					idJoueur = (idJoueur + 1) % 4;
					resetActions();
					inonderCases();
					cancel();
				}
			}
		}, 2000, 10);
	}

	public void recupereArtefact(Artefact artefact) {
		tresorPris.put(artefact, true);
		useAction();
	}

	public boolean hasArtefact(Artefact artefact)
	{
		return tresorPris.getOrDefault(artefact, false);
	}

	public int positionJoueur(Joueur j){
		for(int i=0; i < joueurs.size(); i++){
			if(joueurs.get(i) ==j)
				return i;
		}
		return -1;
	}

}
