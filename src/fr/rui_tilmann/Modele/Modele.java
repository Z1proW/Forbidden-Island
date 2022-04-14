package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.*;
import fr.rui_tilmann.Vue.Observable;

import java.util.*;

public class Modele extends Observable
{

	private final Plateau plateau;
	private final List<Joueur> joueurs;
	private final NiveauEau niveauEau;
	private final PileCartes pileCartes;
	private GameState state = GameState.EN_JEU;
	private int joueur = 0;
	private int nbActions = 3;
	private HashMap<Zone,Boolean> TresorPris = new HashMap<>(4);

	public Modele()
	{
		plateau = new Plateau(this);

		// tirer 4 roles aleatoires parmi les 6
		List<Role> roles = Arrays.asList(Role.values());
		Collections.shuffle(roles);

		joueurs = new ArrayList<>();

		for(int i = 0; i < 4; i++)
		{
			joueurs.add(new Joueur(this, roles.get(i), plateau.caseAleatoire(Etat.SECHE, Etat.INONDEE)));
			System.out.println(joueurs.get(i));
		}

		niveauEau = new NiveauEau(Difficulte.NOVICE);
		pileCartes = new PileCartes(this);

		joueurs.forEach(j -> piocheCartes(j));
		pileCartes.ajoutCarteMDE();
		monteeEau();

	}

	public Plateau getPlateau()
	{
		return plateau;
	}

	public List<Joueur> getJoueurs()
	{
		return joueurs;
	}

	public int getniveauEau()
	{
		return niveauEau.getNiveau();
	}

	public PileCartes getPileCartes()
	{
		return pileCartes;
	}

	public void monteeEau()
	{
		if(niveauEau.getNombreCartes() == -1)
		{
			state = GameState.NIVEAU_EAU_TROP_HAUT;
		}

		for(int i = 0; i < niveauEau.getNombreCartes(); i++)
			monteeEauCase(pileCartes.CaseAInonder());

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
					plateau.removePlaceImportant(c);
					if(plateau.placeImportantPasSubmerge(c.getType()) == 0 && !TresorPris.getOrDefault(c.getType(), false))
						state = GameState.TRESOR_IRRECUPERABLE;
				}


				break;
		}
	}

	public Joueur getJoueur()
	{
		return joueurs.get(joueur);
	}

	public void useAction(Action action)
	{
		nbActions--;

		if(!actionsRestantes())
			finDeTour();
	}

	public boolean actionsRestantes()
	{
		return nbActions != 0;
	}

	private void resetActions()
	{
		nbActions = 3;
	}

	public void finDeTour()
	{
		joueur = (joueur + 1) % 4;
		resetActions();
		piocheCartes(joueurs.get(joueur));
		monteeEau();
	}

	public void piocheCartes(Joueur j){
		ArrayList<Tresor> CartePioche = new ArrayList<>();
		for(int a=0; a < niveauEau.getNombreCartes(); a++){
			Tresor t = pileCartes.getTresor();
			CartePioche.add(t);
		}
		int NbCartesInnodation = Collections.frequency( CartePioche, Tresor.MONTEE_DES_EAUX);
		if(NbCartesInnodation == 1){
			niveauEau.monteeEau();
		}else if(NbCartesInnodation > 1){
			niveauEau.monteeEau();
			pileCartes.grandeInondation();
		}
		for(int i = 0; i <  NbCartesInnodation; i++){
			pileCartes.defausser(Tresor.MONTEE_DES_EAUX);
		}
		CartePioche.removeIf( a -> (a == Tresor.MONTEE_DES_EAUX));
		while( j.getCartes().size() + CartePioche.size() > 5 ){
			pileCartes.defausser(CartePioche.remove(0));
		}
		j.piocheTresor(CartePioche);
	}

	void recupereArtefact(Zone Type){
		TresorPris.put(Type, true);
	}
}
