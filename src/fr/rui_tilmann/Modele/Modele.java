package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.Etat;
import fr.rui_tilmann.Modele.Enums.Role;
import fr.rui_tilmann.Modele.Enums.Zone;
import fr.rui_tilmann.Vue.Observable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Modele extends Observable
{

	public static final int LENGTH = 8;
	private final Case[][] cases;
	private final Joueur[] joueurs;

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

		joueurs = new Joueur[4];

		for(int i = 0; i < 4; i++)
		{
			joueurs[i] = new Joueur(this, roles.get(i), caseAlea(Etat.SECHE, Etat.INONDEE));
			System.out.println(joueurs[i]);
		}
	}

	public Case getCase(int x, int y) {return cases[x][y];}

	public Joueur[] getJoueurs() {return joueurs;}

	private void placerZones()
	{
		for(int i = 0; i < 2; i++) {
			placerZoneAleatoire(Zone.AIR);
			placerZoneAleatoire(Zone.EAU);
			placerZoneAleatoire(Zone.FEU);
			placerZoneAleatoire(Zone.TERRE);
		}
		placerZoneAleatoire(Zone.HELIPORT);
	}

	private void placerZoneAleatoire(Zone type)
	{
		int x, y;

		do
		{
			x = new Random().nextInt(LENGTH);
			y = new Random().nextInt(LENGTH);
		}
		while(cases[x][y].getEtat() != Etat.INONDEE && cases[x][y].getType() != Zone.NORMALE);

		cases[x][y].setType(type);
	}

	private Case caseAlea(Zone... typesPossibles)
	{
		Case c;

		do c = caseAlea();
		while(!List.of(typesPossibles).contains(c.getType()));

		return c;
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

}