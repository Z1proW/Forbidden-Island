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
	private Case[][] cases;
	private Joueur[] joueurs;

	public Modele()
	{
		cases = new Case[LENGTH][LENGTH];

		for(int x = 0; x < LENGTH; x++)
			for(int y = 0; y < LENGTH; y++)
				cases[x][y] = new Case(this, x, y);

		formeIle();

		// inonder 6 cases
		for(int i = 0; i < 6; i++)
			inonderCaseAleatoire();

		placerArtefacts();

		// tirer 4 roles aleatoires parmi les 6
		List<Role> roles = Arrays.asList(Role.values());
		Collections.shuffle(roles);

		joueurs = new Joueur[4];

		for(int i = 0; i < 4; i++)
			joueurs[i] = new Joueur(this, roles.get(i), caseAlea());
	}

	public Case getCase(int x, int y) {return cases[x][y];}

	public Joueur[] getJoueurs() {return joueurs;}

	private void formeIle()
	{
		//Submerge autour de l'ile
		for(int x = 0; x < LENGTH; x++){
			cases[x][0].etat = Etat.SUBMERGEE;
			cases[0][x].etat = Etat.SUBMERGEE;
			cases[x][LENGTH-1].etat = Etat.SUBMERGEE;
			cases[LENGTH-1][x].etat = Etat.SUBMERGEE;
		}
		//Submerge coin en haut a gauche
		cases[1][1].etat = Etat.SUBMERGEE;
		cases[1][2].etat = Etat.SUBMERGEE;
		cases[2][1].etat = Etat.SUBMERGEE;
		//Submerge en bas a gauche
		cases[1][LENGTH-3].etat = Etat.SUBMERGEE;
		cases[1][LENGTH-2].etat = Etat.SUBMERGEE;
		cases[2][LENGTH-2].etat = Etat.SUBMERGEE;
		//Submerge en haut a droite
		cases[LENGTH-2][1].etat = Etat.SUBMERGEE;
		cases[LENGTH-2][2].etat = Etat.SUBMERGEE;
		cases[LENGTH-3][1].etat = Etat.SUBMERGEE;
		//Submerge en bas a droite
		cases[LENGTH-2][LENGTH-2].etat = Etat.SUBMERGEE;
		cases[LENGTH-2][LENGTH-3].etat = Etat.SUBMERGEE;
		cases[LENGTH-3][LENGTH-2].etat = Etat.SUBMERGEE;
	}

	private void placerArtefacts()
	{
		for(int i = 0; i < 2; i++) {
			placerZoneAleatoire(Zone.AIR);
			placerZoneAleatoire(Zone.EAU);
			placerZoneAleatoire(Zone.FEU);
			placerZoneAleatoire(Zone.TERRE);
		}
		placerZoneAleatoire(Zone.HELIPORT);
	}

	public void placerZoneAleatoire(Zone type)
	{
		int x, y;

		do
		{
			x = new Random().nextInt(LENGTH);
			y = new Random().nextInt(LENGTH);
		}
		while(cases[x][y].etat != Etat.INONDEE && cases[x][y].type != Zone.AUCUNE);

		cases[x][y].type = type;
	}

	public void inonderCaseAleatoire()
	{
		caseAlea().setEtat(Etat.INONDEE);
	}

	public Case caseAlea()
	{
		int x, y;

		do
		{
			x = new Random().nextInt(LENGTH);
			y = new Random().nextInt(LENGTH);
		}
		while(cases[x][y].getEtat() != Etat.SECHE);

		return getCase(x, y);
	}

}
