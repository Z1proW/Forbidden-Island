package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.Carte;

import java.util.*;

public class PileCartes {
	// todo il y a un problème car on peut avoir plus de 5 clés du même artefact dans les inventaires
	private final Modele modele;

	private List<Carte> tresors = new ArrayList<>();
	private List<Carte> defausse = new ArrayList<>();

	private List<Case> cartesInondation;
	private List<Case> cartesInondees = new ArrayList<>();

	PileCartes(Modele modele) {
		this.modele = modele;

		initTresors();

		cartesInondation = this.modele.getPlateau().getCartesCase();
		Collections.shuffle(cartesInondation);
		Collections.shuffle(tresors);
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
				Collections.shuffle(tresors);
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
