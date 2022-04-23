package fr.rui_tilmann.modele;

import fr.rui_tilmann.modele.enums.Carte;

import java.util.*;

public class PileCartes {
	private final Modele modele;

	private List<Carte> tresors = new ArrayList<>();
	private final List<Carte> defausse = new ArrayList<>();

	private final List<Case> cartesInondation;
	private final List<Case> cartesInondees = new ArrayList<>();

	PileCartes(Modele modele) {
		this.modele = modele;

		initTresors();

		cartesInondation = getCasesInondation();
		Collections.shuffle(cartesInondation);
	}

	private void initTresors() {
		for(int i = 0; i < 5; i++) {
			tresors.add(Carte.TERRE);
			tresors.add(Carte.AIR);
			tresors.add(Carte.FEU);
			tresors.add(Carte.EAU);
		}

		for(int i = 0; i < 3; i++) {
			tresors.add(Carte.HELICOPTERE);
		}

		for(int i = 0; i < 2; i++)
			tresors.add(Carte.SAC_DE_SABLE);

		for(int i = 0; i < 3; i++) {
			tresors.add(Carte.MONTEE_DES_EAUX);
		}
		Collections.shuffle(tresors);
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

	public void defausser(Carte t)
	{
		defausse.add(t);
	}

	public Carte getTresor(boolean monteeEaux) {
		Carte tresor = tresors.remove(0);

		if(!monteeEaux)
		{
			while(tresor == Carte.MONTEE_DES_EAUX)
			{
				tresors.add(tresor);
				tresor = tresors.remove(0);
			}
		}

		if(tresors.isEmpty()) reset();
		return tresor;
	}

	public void reset() {
		tresors = new ArrayList<>(defausse);
		Collections.shuffle(tresors);
		defausse.clear();
	}

	public Case caseAInonder() {
		Case c = cartesInondation.remove(0);

		if(cartesInondation.isEmpty())
			melangerCartesInondation();

		cartesInondees.add(c);
		return c;
	}

	public void melangerCartesInondation() {
		Collections.shuffle(cartesInondees);
		cartesInondation.addAll(0 , cartesInondees);
		cartesInondees.clear();
	}

	public void removeCaseAInonder(Case c) {
		cartesInondees.remove(c);
	}

}
