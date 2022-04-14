package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.*;
import fr.rui_tilmann.Vue.Observable;

import java.util.*;

public class Modele extends Observable
{

	public static final int LENGTH = 8;
	private final Case[][] cases;
	private final List<Joueur> joueurs;
	private final NiveauEau niveauEau;
	private final PileCartes pileCartes;
	private GameState state = GameState.EN_JEU;
	private int joueur = 0;
	private int nbActions = 3;

	public Modele()
	{
		cases = new Case[LENGTH][LENGTH];

		for(int x = 0; x < LENGTH; x++)
			for(int y = 0; y < LENGTH; y++)
			{
				cases[x][y] = new Case(this, x, y);

				// rend l'ile circulaire
				if((x-3.5)*(x-3.5) + (y-3.5)*(y-3.5) > 8)
					cases[x][y].setEtat(Etat.SUBMERGEE);
			}

		// inonder 6 cases aleatoires
		for(int i = 0; i < 6; i++)
			caseAleatoire(Etat.SECHE).setEtat(Etat.INONDEE);

		placerZones();

		// tirer 4 roles aleatoires parmi les 6
		List<Role> roles = Arrays.asList(Role.values());
		Collections.shuffle(roles);

		joueurs = new ArrayList<>();

		for(int i = 0; i < 4; i++)
		{
			joueurs.add(new Joueur(this, roles.get(i), caseAleatoire(Etat.SECHE, Etat.INONDEE)));
			System.out.println(joueurs.get(i));
		}

		niveauEau = new NiveauEau(Difficulte.NOVICE);
		pileCartes = new PileCartes();

		for(int i = 0; i < 2; i++)
			joueurs.forEach(j -> j.piocheTresor());
	}

	public Case getCase(int x, int y) {return cases[x][y];}

	public List<Joueur> getJoueurs() {return joueurs;}

	public int getniveauEau() {return niveauEau.getNiveau();}

	public PileCartes getPileCartes() {return pileCartes;}

	private void placerZones()
	{
		for(int i = 0; i < 2; i++)
		{
			placerZoneAleatoire(Zone.AIR);
			placerZoneAleatoire(Zone.EAU);
			placerZoneAleatoire(Zone.FEU);
			placerZoneAleatoire(Zone.TERRE);
		}
		placerZoneAleatoire(Zone.HELIPORT);
	}

	private void placerZoneAleatoire(Zone type)
	{
		Case c;
		do c = caseAleatoire(Etat.SECHE, Etat.INONDEE);
		while(c.getType() != Zone.NORMALE);
		c.setType(type);
	}

	private Case caseAleatoire(Etat... etatsPossibles)
	{
		Case c;
		do c = caseAleatoire();
		while(!List.of(etatsPossibles).contains(c.getEtat()));
		return c;
	}

	private Case caseAleatoire()
	{
		return getCase(new Random().nextInt(LENGTH), new Random().nextInt(LENGTH));
	}

	public void monteeEau()
	{
		niveauEau.monteeEau();

		if(niveauEau.getNombre() == -1)
		{
			state = GameState.NIVEAU_EAU_TROP_HAUT;
		}

		for(int i = 0; i < niveauEau.getNombre(); i++)
			monteeEauCase(caseAleatoire(Etat.SECHE, Etat.INONDEE));
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

				// TODO perdu si il les 2 tuiles d'un tresor sont submergees sauf si le tresor est deja pris
				state = GameState.TRESOR_IRRECUPERABLE;

				break;
		}
	}

	public void finDeTour()
	{
		joueur = (joueur + 1) % 4;
		nbActions = 3;
	}

	public Joueur getJoueur()
	{
		return joueurs.get(joueur);
	}

	public boolean actionsRestantes()
	{
		return nbActions != 0;
	}

	public void useAction()
	{
		nbActions--;
	}

}
