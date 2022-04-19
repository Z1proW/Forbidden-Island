package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.*;

import java.util.*;

public class Joueur
{

	private final Modele modele;
	private final Role role;
	private Case position;
	private final List<Carte> cartes;

	public Joueur(Modele modele, Role role, Case pos)
	{
		this.modele = modele;
		this.role = role;
		this.position = pos;
		this.cartes = new ArrayList<>(Carte.MAX);
	}

	public Role getRole() {return role;}

	public Case getPosition() {return position;}

	public void deplace(Case c)
	{
		if(!modele.actionsRestantes()) return;

		if(c != position
		&& c.getEtat() != Etat.SUBMERGEE)
		{
			position = c;
			modele.useAction();
			modele.notifyObservers();
		}
	}
	public void deplace(Case c, boolean b)
	{
		if(b)
			position = c;
		modele.notifyObservers();

	}

	public void deplace(Direction d) {
		Case adjacente = getPosition().adjacente(d);

		if(adjacente.getEtat() == Etat.SUBMERGEE && getRole() == Role.PLONGEUR)
		{
			adjacente = adjacente.adjacente(d);

			if(0 <= adjacente.getX() && adjacente.getX() < Plateau.LENGTH
			&& 0 <= adjacente.getY() && adjacente.getY() < Plateau.LENGTH)
				deplace(adjacente);
		}
		else deplace(adjacente);
	}

	public void asseche(Case c) {
		if(!modele.actionsRestantes()) return;

		if(c.getEtat() == Etat.INONDEE) {
			c.setEtat(Etat.SECHE);
			modele.useAction();
			modele.notifyObservers();
		}
	}
	public void asseche(Case c, boolean b){
		if(c.getEtat() == Etat.INONDEE && b) {
			c.setEtat(Etat.SECHE);
		}
	}

	public List<Carte> getCartes()
	{
		return cartes;
	}

	public void piocheCartes(boolean monteeEaux)
	{
		new Timer().schedule(new TimerTask()
		{
			int i = 0;
			boolean dejaEuMDE = false;

			@Override
			public void run()
			{
				dejaEuMDE = piocheCarte(monteeEaux, !dejaEuMDE);
				i++;
				//Pour débugger
				System.out.println(modele.getPileCartes().getTresors().size());
				if(i >= 5) cancel();
			}
		}, 800, 400);
	}

	public void piocheCartes()
	{
		piocheCartes(true);
	}

	private boolean piocheCarte(boolean monteeEaux, boolean melanger)
	{
		//if(cartes.size() >= 5) return;

		Carte carte = modele.getPileCartes().getTresor(monteeEaux);
		cartes.add(carte);

		if(carte == Carte.MONTEE_DES_EAUX)
		{
			modele.monteeEau();

			new Timer().schedule(new TimerTask()
			{
				@Override
				public void run()
				{
					if(melanger)
						modele.getPileCartes().melangerCartesInondation();

					modele.getPileCartes().defausser(Carte.MONTEE_DES_EAUX);
					cartes.remove(Carte.MONTEE_DES_EAUX);

					cancel();
				}}, 400);
			return true;
		}
		return false;
	}

	// TODO c'est peut être mieux de ne pas catch l'erreur car c'est pas censé arriver
	// et si ça arrive on veut le savoir
	// de toute façon on test si c'est dans les bounds 0 - 5 dans le controlleur
	public Carte utiliseCarte(int n) {
		try {
			modele.getPileCartes().defausser(cartes.get(n));
			modele.useAction();
			return cartes.remove(n);
		}
		catch(IndexOutOfBoundsException e) {
			System.out.println("Pas de carte à cet emplacement");
			return null;
		}
	}

	public void defausseCarte(int n) {
		if(0 <= n && n < cartes.size())
			modele.getPileCartes().defausser(cartes.remove(n));
	}

	public void donneCarte(int n, Joueur j) {
		Carte carte = cartes.get(n);

		if(carte.toArtefact() != null) {
			j.getCartes().add(cartes.remove(n));
			modele.useAction();
			modele.notifyObservers();
		}
	}

	public void recupereArtefact(Carte carte) {
		modele.recupereArtefact(carte.toArtefact());

		for(int i = 0; i < 4; i++)
		{
			modele.getPileCartes().defausser(carte);
			cartes.remove(carte);
		}

		modele.notifyObservers();
	}

	public String toString()
	{
		return role + " est en " + position;
	}

}
