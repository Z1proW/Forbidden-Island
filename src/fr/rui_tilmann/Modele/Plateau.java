package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.Etat;
import fr.rui_tilmann.Modele.Enums.Zone;

import java.util.*;
import java.util.function.Consumer;

public class Plateau
{

	private final Modele modele;
	public static final int LENGTH = 8;
	private final Case[][] cases = new Case[LENGTH][LENGTH];
	private HashMap<Case, Zone> ZoneImportante = new HashMap<>();

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
		forEachCase(c ->
		{
			if((c.getX()-3.5)*(c.getX()-3.5) + (c.getY()-3.5)*(c.getY()-3.5) > 8)
				c.setEtat(Etat.SUBMERGEE);
		});
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

	public Case getCase(int x, int y)
	{
		return cases[x][y];
	}

	public void forEachCase(Consumer<Case> action) {
		Objects.requireNonNull(action);
		for(int x = 0; x < LENGTH; x++)
			for(int y = 0; y < LENGTH; y++)
				action.accept(getCase(x, y));
	}

	private void placerZoneAleatoire(Zone type)
	{
		Case c;
		do c = caseAleatoire(Etat.SECHE, Etat.INONDEE);
		while(c.getType() != Zone.NORMALE);
		c.setType(type);
		ZoneImportante.put(c, type);
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
		forEachCase(c ->
		{
			if((c.getX()-3.5)*(c.getX()-3.5) + (c.getY()-3.5)*(c.getY()-3.5) < 8)
				CarteAInonder.add(c);
		});
		return CarteAInonder;
	}

	public void removeZoneImportante(Case c) {
		ZoneImportante.remove(c);
	}

	public int zoneImportantePasSubmergee(Zone zone) {
		int nbType = 0;

		for(Zone z : ZoneImportante.values())
			if(zone == z) nbType++;
		return nbType;
	}
}
