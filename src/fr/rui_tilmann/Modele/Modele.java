package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.Difficulte;
import fr.rui_tilmann.Modele.Enums.Etat;
import fr.rui_tilmann.Modele.Enums.Role;
import fr.rui_tilmann.Modele.Enums.Zone;
import fr.rui_tilmann.Vue.Observable;

import java.util.*;

public class Modele extends Observable
{

	public static final int LENGTH = 8;
	private final Case[][] cases;
	private final List<Joueur> joueurs;
	private NiveauEau niveauEau;

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
			caseAlea(Etat.SECHE).setEtat(Etat.INONDEE);

		placerZones();

		// tirer 4 roles aleatoires parmi les 6
		List<Role> roles = Arrays.asList(Role.values());
		Collections.shuffle(roles);

		joueurs = new ArrayList<>();

		for(int i = 0; i < 4; i++)
		{
			joueurs.add(new Joueur(this, roles.get(i), caseAlea(Etat.SECHE, Etat.INONDEE)));
			System.out.println(joueurs.get(i));
		}

		/*
		for(int i = 0; i < 3; i++)
			joueurs.add(new Joueur(this, roles.get(i), cases[4][4]));
		joueurs.add(new Joueur(this, roles.get(4), cases[3][4]));
		 */
		niveauEau = new NiveauEau(Difficulte.NOVICE);
	}

	public Case getCase(int x, int y) {return cases[x][y];}

	public List<Joueur> getJoueurs() {return joueurs;}

	public int getniveauEau(){
		return this.niveauEau.getCurrent();
	}

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
		do c = caseAlea(Etat.SECHE, Etat.INONDEE);
		while(c.getType() != Zone.NORMALE);
		c.setType(type);
	}

	private Case caseAlea(Etat... etatsPossibles)
	{
		Case c;
		do c = caseAlea();
		while(!List.of(etatsPossibles).contains(c.getEtat()));
		return c;
	}

	private Case caseAlea()
	{
		return getCase(new Random().nextInt(LENGTH), new Random().nextInt(LENGTH));
	}

	public void innodationIle(int n){
		Case c;
		for(int i=0; i < n; i++){
			c = caseAlea(Etat.SECHE, Etat.INONDEE);
			if(c.getEtat() == Etat.SECHE){
				c.setEtat(Etat.INONDEE);
			}else{
				c.setEtat(Etat.SECHE);
			}
		}
	}

}
