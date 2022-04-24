package fr.rui_tilmann.modele;

import fr.rui_tilmann.modele.enums.*;
import fr.rui_tilmann.vue.Observable;
import fr.rui_tilmann.vue.VueGameOver;
import fr.rui_tilmann.vue.jeu.Vue;

import javax.swing.*;
import java.util.Timer;
import java.util.*;

public class Modele extends Observable
{
	private final Plateau plateau;
	private final List<Joueur> joueurs = new ArrayList<>();
	private final NiveauEau niveauEau;
	private final PileCartes pileCartes;

	public static int NOMBRE_JOUEURS;
	private final Vue gameFrame;

	private int idJoueur = 0;
	private int nbActions = 3;

	private final HashMap<Artefact, Boolean> tresorPris = new HashMap<>(4);

	public boolean actionUtiliseePilote = false;
	public boolean actionSpeIngenieur = false;
	public boolean finDeTourPossible = true;

	public Modele(Difficulte difficulte, int nbJoueurs, Vue gameFrame)
	{
		NOMBRE_JOUEURS = nbJoueurs;
		this.gameFrame = gameFrame;

		plateau = new Plateau(this);

		initJoueurs();

		niveauEau = new NiveauEau(this, difficulte);
		pileCartes = new PileCartes(this);

		distribueCartesJoueurs();
		initInondation();
		// test recup artefact
		/*
		joueurs.get(0).getCartes().clear();
		for(int i = 0; i < 5; i++)
			joueurs.get(0).getCartes().add(Carte.TERRE);
		*/
		/*
		tresorPris.put(Artefact.AIR,true);
		tresorPris.put(Artefact.FEU,true);
		tresorPris.put(Artefact.TERRE,true);
		tresorPris.put(Artefact.EAU,true);
		 */

	}

	private void initInondation()
	{
		for(int i = 0; i < 6 ; i++)
			inonderCase(pileCartes.getCaseAInonder());
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

	public int getNbActions()
	{
		return nbActions;
	}

	private void resetActions()
	{
		nbActions = 3;
	}

	public boolean actionsRestantes()
	{
		return nbActions > 0;
	}

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
		plateau.forEachCase(c ->
		{
			if(c.getZone().toArtefact() == artefact)
				c.setZone(Zone.NORMALE);
		});
	}

	public boolean hasArtefact(Artefact artefact)
	{
		return tresorPris.getOrDefault(artefact, false);
	}

	public void inonderCases()
	{
		for(int i = 0; i < niveauEau.getNombreCartes(); i++)
			inonderCase(pileCartes.getCaseAInonder());
	}

	private void inonderCase(Case c)
	{
		switch(c.getEtat())
		{
			case SECHE:
				c.setEtat(Etat.INONDEE);
				break;

			case INONDEE:
				c.setEtat(Etat.SUBMERGEE);
				c.setZone(Zone.NORMALE);
				pileCartes.enleveCaseAInonder(c);

				// perdu si un joueur est sur une case submergee et pas de cases autour
				joueurs.forEach(joueur ->
				{
					if(joueur.getPosition().getEtat() == Etat.SUBMERGEE
					&& joueur.getPosition().casesAdjacentes(joueur.getRole() == Role.EXPLORATEUR)
					.stream().allMatch(c2 -> c2.getEtat() == Etat.SUBMERGEE))
						finDePartie(GameOver.NOYADE);
				});

				// perdu si c'est l'heliport
				if(c.getZone() == Zone.HELIPORT)
					finDePartie(GameOver.HELIPORT_SUBMERGE);

				//Perdu si 2 zone du meme type tombe
				if(c.getZone().toArtefact() != null
				&& plateau.compte(c.getZone()) == 0
				&& !tresorPris.getOrDefault(c.getZone().toArtefact(), false))
					finDePartie(GameOver.TRESOR_IRRECUPERABLE);
				break;
		}
	}

	public void useAction()
	{
		nbActions--;
		if(nbActions == 0) finDeTour();
		if(gagnePartie())
			finDePartie(GameOver.GAGNE);
	}

	public void finDeTour()
	{
		if(!finDeTourPossible) return;

		finDeTourPossible = false;
		Joueur joueur = getCurrentJoueur();
		joueur.piocheCartes();

		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				if(joueurs.stream().allMatch(j -> j.getCartes().size() <= 5)) {
					actionUtiliseePilote = false;
					actionSpeIngenieur = false;

					idJoueur = (idJoueur + 1) % NOMBRE_JOUEURS;
					resetActions();
					inonderCases();
					finDeTourPossible = true;
					cancel();
				}
			}
		}, 2000, 10);
	}

	private void aGagne() {
		finDePartie(GameOver.GAGNE);
	}

	private boolean gagnePartie() {
		for(int i = 0; i < NOMBRE_JOUEURS; i++)
			if(getJoueur(i).getPosition().getZone() != Zone.HELIPORT)
				return false;

		return tresorPris.values().stream().allMatch(e -> e)
		&& joueurs.stream().anyMatch(e ->
				e.getCartes().stream().anyMatch(c ->
						c == Carte.HELICOPTERE));
	}

	private void finDePartie(GameOver state)
	{
		JPanel vueGameOver = new VueGameOver(state);
		gameFrame.setGlassPane(vueGameOver);
		vueGameOver.setVisible(true);

		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				if(gameFrame.isActive())
					JOptionPane.showMessageDialog(null, "Voulez vous retourner au menu ?","Retour au menu", JOptionPane.QUESTION_MESSAGE);
				gameFrame.dispose();
			}
		}, 6000);
	}

}
