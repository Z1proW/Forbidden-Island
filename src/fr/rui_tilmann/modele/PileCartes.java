package fr.rui_tilmann.modele;

import fr.rui_tilmann.modele.enums.Carte;

import java.util.*;

public class PileCartes {
	private final Modele modele;

	private List<Carte> cartes = new ArrayList<>();
	private final List<Carte> defausse = new ArrayList<>();

	private final List<Case> cartesInondation;
	private final List<Case> defausseInondation = new ArrayList<>();

	PileCartes(Modele modele) {
		this.modele = modele;

		initTresors();

		cartesInondation = getCasesInondation();
		Collections.shuffle(cartesInondation);
	}

	private void initTresors() {
		for(int i = 0; i < 5; i++) {
			cartes.add(Carte.TERRE);
			cartes.add(Carte.AIR);
			cartes.add(Carte.FEU);
			cartes.add(Carte.EAU);
		}

		for(int i = 0; i < 3; i++) {
			cartes.add(Carte.HELICOPTERE);
		}

		for(int i = 0; i < 2; i++)
			cartes.add(Carte.SAC_DE_SABLE);

		for(int i = 0; i < 3; i++) {
			cartes.add(Carte.MONTEE_DES_EAUX);
		}
		Collections.shuffle(cartes);
	}

	public ArrayList<Case> getCasesInondation() {
		ArrayList<Case> cases = new ArrayList<>();
		modele.getPlateau().forEachCase(c ->
		{
			if(c.dansIle())
				cases.add(c);
		});
		return cases;
	}

	public void defausser(Carte c)
	{
		defausse.add(c);
	}

	public Carte getCarte(boolean monteeEaux) {
		Carte carte = cartes.remove(0);

		if(!monteeEaux)
		{
			while(carte == Carte.MONTEE_DES_EAUX)
			{
				cartes.add(carte);
				carte = cartes.remove(0);
			}
		}

		if(cartes.isEmpty()) reset();
		return carte;
	}

	public void reset() {
		cartes = new ArrayList<>(defausse);
		Collections.shuffle(cartes);
		defausse.clear();
	}

	public Case getCaseAInonder() {
		Case c = cartesInondation.remove(0);

		if(cartesInondation.isEmpty())
			melangerCartesInondation();

		defausseInondation.add(c);
		return c;
	}

	public void melangerCartesInondation() {
		Collections.shuffle(defausseInondation);
		cartesInondation.addAll(0 , defausseInondation);
		defausseInondation.clear();
	}

	public void enleveCaseAInonder(Case c) {
		defausseInondation.remove(c);
	}

}
