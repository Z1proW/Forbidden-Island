package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.Direction;
import fr.rui_tilmann.Modele.Enums.Etat;
import fr.rui_tilmann.Modele.Enums.Zone;
import fr.rui_tilmann.Vue.Observable;

import java.util.Random;

public class Modele extends Observable
{

	public static final int LENGTH = 8;
	private Case[][] cases;

	public Modele()
	{
		cases = new Case[LENGTH][LENGTH];

		for(int x = 0; x < LENGTH; x++)
			for(int y = 0; y < LENGTH; y++)
				cases[x][y] = new Case(this, x, y);

		formeIle();
		inonderSixCases();
		placerArtefacts();
	}

	private void formeIle()
	{
		/*
		mettre les cases sur les bords en Etat.SUBMERGEE
		      [] []
		   [] [] [] []
		[] [] [] [] [] []
		[] [] [] [] [] []
		   [] [] [] []
		      [] []
		 */
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

	private void inonderSixCases()
	{
		for(int i = 0; i < 6; i++)
			inonderCaseAleatoire();
	}

	private void placerArtefacts()
	{
		for(int i = 0; i < 2; i++) {
			placerArtefactsAleatoire(Zone.AIR);
			placerArtefactsAleatoire(Zone.EAU);
			placerArtefactsAleatoire(Zone.FEU);
			placerArtefactsAleatoire(Zone.TERRE);
		}
		placerArtefactsAleatoire(Zone.HELIPORT);

	}

	public void placerArtefactsAleatoire(Zone type)
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


	public Case getCase(int x, int y) {return cases[x][y];}

	public void avance(Direction d, Joueur j)
	{
		j.deplace(d);

	}

	public void inonderCaseAleatoire()
	{
		int x, y;

		do
		{
			x = new Random().nextInt(LENGTH);
			y = new Random().nextInt(LENGTH);
		}
		while(cases[x][y].getEtat() != Etat.SECHE);

		cases[x][y].setEtat(Etat.INONDEE);
	}

}
