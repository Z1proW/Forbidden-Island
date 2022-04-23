package fr.rui_tilmann.modele;

import fr.rui_tilmann.modele.enums.*;

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

	public Role getRole()
	{
		return role;
	}

	public Case getPosition()
	{
		return position;
	}

	public int getId()
	{
		for(int i = 0; i < Modele.NOMBRE_JOUEURS; i++)
			if(modele.getJoueur(i) == this)
				return i;
		return -1;
	}

	public void deplace(Case c, boolean useAction)
	{
		if(modele.actionsRestantes()
		&& c != position
		&& c.getEtat() != Etat.SUBMERGEE)
		{
			position = c;
			if(useAction) modele.useAction();
			modele.notifyObservers();
		}
	}

	public void deplace(Case c)
	{
		deplace(c, true);
	}

	public void deplace(Direction d, boolean useAction) {
		Case adjacente = getPosition().adjacente(d);

		if(adjacente.getEtat() == Etat.SUBMERGEE && getRole() == Role.PLONGEUR)
		{
			adjacente = adjacente.adjacente(d);

			if(adjacente != null)
				deplace(adjacente, useAction);
		}
		else deplace(adjacente, useAction);
	}

	public void deplace(Direction d){
		deplace(d, true);
	}

	public void asseche(Case c) {
		if(modele.actionsRestantes()
		&& c.getEtat() == Etat.INONDEE) {
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
				if(i >= 2) cancel();
			}
		}, 500, 400);
	}

	public void piocheCartes()
	{
		piocheCartes(true);
	}

	private boolean piocheCarte(boolean monteeEaux, boolean melanger)
	{
		Carte carte = modele.getPileCartes().getTresor(monteeEaux);
		cartes.add(carte);

		if(monteeEaux) Son.CARTE.jouerSon();

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
				}}, 500);
			return true;
		}
		return false;
	}

	public Carte utiliseCarte(int n) {
		modele.getPileCartes().defausser(cartes.get(n));
		modele.useAction();
		return cartes.remove(n);
	}

	public void defausseCarte(int n) {
		if(0 <= n && n < cartes.size())
			modele.getPileCartes().defausser(cartes.remove(n));
	}

	public void donneCarte(int n, Joueur j) {
		if(!modele.actionsRestantes()) return;

		Carte carte = cartes.get(n);

		if(carte.toArtefact() != null) {
			j.getCartes().add(cartes.remove(n));
			modele.useAction();
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
