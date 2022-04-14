package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.Etat;
import fr.rui_tilmann.Modele.Enums.Zone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Plateau
{

	private final Modele modele;
	public static final int LENGTH = 8;
	private final Case[][] cases = new Case[LENGTH][LENGTH];
	private HashMap<Case, Zone> PlaceImportant = new HashMap<>();

	public Plateau(Modele modele)
	{
		this.modele = modele;

		initCases();
		ileCirculaire();
		inonderSixCases();
		placerZones();
	}

	private void initCases()
	{
		for(int x = 0; x < LENGTH; x++)
			for(int y = 0; y < LENGTH; y++)
				cases[x][y] = new Case(modele, x, y);
	}

	private void ileCirculaire()
	{
		for(int x = 0; x < LENGTH; x++)
			for(int y = 0; y < LENGTH; y++)
				if((x-3.5)*(x-3.5) + (y-3.5)*(y-3.5) > 8)
					cases[x][y].setEtat(Etat.SUBMERGEE);
	}

	private void inonderSixCases()
	{
		for(int i = 0; i < 6; i++)
			caseAleatoire(Etat.SECHE).setEtat(Etat.INONDEE);
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

	public Case getCase(int x, int y) {return cases[x][y];}

	private void placerZoneAleatoire(Zone type)
	{
		Case c;
		do c = caseAleatoire(Etat.SECHE, Etat.INONDEE);
		while(c.getType() != Zone.NORMALE);
		c.setType(type);
		PlaceImportant.put(c, type);
	}

	protected Case caseAleatoire(Etat... etatsPossibles)
	{
		Case c;
		do c = caseAleatoire();
		while(!List.of(etatsPossibles).contains(c.getEtat()));
		return c;
	}

	public Case caseAleatoire()
	{
		return getCase(new Random().nextInt(LENGTH), new Random().nextInt(LENGTH));
	}

	public ArrayList<Case> getCartesCase(){
		ArrayList<Case> CarteAInonder = new ArrayList<>();
		for(int x = 0; x < LENGTH; x++)
			for(int y = 0; y < LENGTH; y++)
				if((x-3.5)*(x-3.5) + (y-3.5)*(y-3.5) < 8)
					CarteAInonder.add(getCase(x, y));
		return CarteAInonder;
	}
	public void removePlaceImportant(Case c){
		PlaceImportant.remove(c);
	}
	public int placeImportantPasSubmerge(Zone Type){
		int nbType = 0;
		for(Zone z:PlaceImportant.values()){
			if(Type == z)nbType++;
		}
		return  nbType;
	}
}
