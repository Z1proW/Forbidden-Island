package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.*;

import java.util.ArrayList;
import java.util.List;

public class Joueur
{

	private final Modele modele;
	private final Role role;
	private Case position;
	private List<Tresor> cartes;

	public Joueur(Modele modele, Role role, Case pos)
	{
		this.modele = modele;
		this.role = role;
		this.position = pos;
		this.cartes = new ArrayList<>(5);
	}

	public Role getRole() {return role;}

	public Case getPosition() {return this.position;}

	public void deplace(Case c)
	{
		if(c.getEtat() != Etat.SUBMERGEE
		|| role == Role.PLONGEUR)
		{
			position = c;
			modele.useAction(Action.DEPLACER);
			modele.notifyObservers();
		}
	}

	public void deplace(Direction d) {
		deplace(getPosition().adjacente(d));
	}

	public List<Tresor> getCartes() {return cartes;}

	// TODO gérer 5 cartes max
	public void piocheTresor(ArrayList<Tresor> t)
	{
		for(Tresor tresor: t ) {
			if (cartes.size() < 5)
				cartes.add(tresor);

		}
	}

	public Tresor utiliseTresor(int n) {
		try {
			modele.getPileCartes().defausser(cartes.get(n));
			return cartes.remove(n);
		}
		catch(Exception e) {
			System.out.println("Pas de carte à cette emplacement");
			return null;
		}
	}

	public String toString()
	{
		return role + " est en " + position;
	}

}
