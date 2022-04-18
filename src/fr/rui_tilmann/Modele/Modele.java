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

	private int joueur = 0;
	private int nbActions = 3;

	private final HashMap<Artefact, Boolean> tresorPris = new HashMap<>(4);
	private boolean endTour = true;

	public Modele()
	{
		plateau = new Plateau(this);

		initJoueurs();

		niveauEau = new NiveauEau(Difficulte.NOVICE);
		pileCartes = new PileCartes(this);

		joueurs.forEach(this::piocheCartes);

		joueurs.get(0).getCartes().clear();
		for(int i = 0; i < 5; i++)
			joueurs.get(0).getCartes().add(Carte.TERRE);

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

	public Plateau getPlateau()
	{
		return plateau;
	}

	public List<Joueur> getJoueurs()
	{
		return joueurs;
	}

	public Joueur getJoueur()
	{
		return joueurs.get(joueur);
	}

	public int getniveauEau()
	{
		return niveauEau.getNiveau();
	}

	public PileCartes getPileCartes()
	{
		return pileCartes;
	}

	public int getNbActions() {
		return nbActions;
	}

	private void resetActions()
	{
		nbActions = 3;
	}

	// TODO méthode à appeler quand on pioche une Carte MONTEE_DES_EAUX
	public void monteeEau()
	{
		niveauEau.monteeEau();

		if(niveauEau.getNombreCartes() == -1)
			state = GameState.NIVEAU_EAU_TROP_HAUT;

		getPileCartes().melangerCartesInondation();
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
		if(endTour) {
			piocheCartes(getJoueur());
			endTour = !endTour;
		}
		if(joueurs.stream().allMatch(j -> j.getCartes().size() <= 5)) {
			joueur = (joueur + 1) % 4;
			resetActions();
			inonderCases();
			endTour = true;
		}
	}

	// TODO je ne crois pas que ça soit bon
	public void piocheCartes(Joueur j) {
		ArrayList<Carte> cartePioche = new ArrayList<>();

		for(int a = 0; a < niveauEau.getNombreCartes(); a++) {
			Carte t = pileCartes.getTresor();
			cartePioche.add(t);
		}

		int nbCartesInondation = Collections.frequency(cartePioche, Carte.MONTEE_DES_EAUX);

		if(nbCartesInondation == 1)
			niveauEau.monteeEau();
		else if(nbCartesInondation > 1) {
			niveauEau.monteeEau();
			pileCartes.melangerCartesInondation();
		}

		for(int i = 0; i <  nbCartesInondation; i++)
			pileCartes.defausser(Carte.MONTEE_DES_EAUX);

		cartePioche.removeIf(a -> (a == Carte.MONTEE_DES_EAUX));

		while(j.getCartes().size() + cartePioche.size() > 10)
			pileCartes.defausser(cartePioche.remove(0));

		j.piocheCarte(cartePioche);
	}

	public void recupereArtefact(Artefact artefact) {
		tresorPris.put(artefact, true);
		useAction();
	}

	public boolean hasArtefact(Artefact artefact)
	{
		return tresorPris.getOrDefault(artefact, false);
	}

}
