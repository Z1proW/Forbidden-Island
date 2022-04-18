package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.*;

import java.util.*;

public class Joueur
{

	private final Modele modele;
	private final Role role;
	private Case position;
	private final List<Carte> cartes;
	private int count = 0;

	public Joueur(Modele modele, Role role, Case pos)
	{
		this.modele = modele;
		this.role = role;
		this.position = pos;
		this.cartes = new ArrayList<>(10);
	}

	public Role getRole() {return role;}

	public Case getPosition() {return this.position;}

	public void deplace(Case c)
	{
		if(!modele.actionsRestantes()) return;

		if(c != getPosition()
		&& c.getEtat() != Etat.SUBMERGEE)
		{
			position = c;
			modele.useAction();
			modele.notifyObservers();
		}
	}

	public void deplace(Direction d) {
		Case adjacente = getPosition().adjacente(d);

		if(adjacente.getEtat() == Etat.SUBMERGEE && getRole() == Role.PLONGEUR)
			deplace(adjacente.adjacente(d));
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

	public List<Carte> getCartes() {return cartes;}

	public void piocheCartes(boolean monteeEaux)
	{
		count = 0;
		new Timer().schedule(new TimerTask()
		{
			int i = 0;

			@Override
			public void run()
			{
				piocheCarte(monteeEaux);
				i++;
				if(i >= modele.nbCartes()) cancel();
			}
		}, 500, 500);

		if(count>1){
			modele.getPileCartes().melangerCartesInondation();
		}
	}

	public void piocheCartes()
	{
		piocheCartes(true);
	}

	private void piocheCarte(boolean monteeEaux)
	{

		Carte carte = modele.getPileCartes().getTresor(monteeEaux);
		cartes.add(carte);
		if(carte == Carte.MONTEE_DES_EAUX){
			count++;

			new Timer().schedule(new TimerTask()
			{
				@Override
				public void run()
				{
					modele.monteeEau();
					modele.getPileCartes().defausser(Carte.MONTEE_DES_EAUX);
					cartes.remove(Carte.MONTEE_DES_EAUX);
					cancel();
				}}, 500);

		}
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


	public void enleveArtefact(int n){
		modele.getPileCartes().defausser(cartes.remove(n));
		modele.finDeTour();
	}

	public String toString()
	{
		return role + " est en " + position;
	}

}
