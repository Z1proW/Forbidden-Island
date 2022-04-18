package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.Carte;
import fr.rui_tilmann.Modele.Enums.Direction;
import fr.rui_tilmann.Modele.Enums.Etat;
import fr.rui_tilmann.Modele.Enums.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
		this.cartes = new ArrayList<>(5);
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

	public void piocheCartes(boolean firstTime)
	{
		new Timer().schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				piocheCarte(firstTime);

				new Timer().schedule(new TimerTask()
				{
					@Override
					public void run()
					{
						piocheCarte(firstTime);
						cancel();
					}
				}, 500);
				cancel();
			}
		}, 500);
	}

	private void piocheCarte(boolean firstTime)
	{
		if(cartes.size() >= 5) return;

		Carte carte;

		if(firstTime)
		{
			do {carte = modele.getPileCartes().getTresor();}
			while(carte == Carte.MONTEE_DES_EAUX);

			cartes.add(carte);
		}
		else
		{
			carte = modele.getPileCartes().getTresor();
			cartes.add(carte);

			if(carte == Carte.MONTEE_DES_EAUX)
			{
				new Timer().schedule(new TimerTask()
				{
					@Override
					public void run()
					{
						modele.getPileCartes().defausser(Carte.MONTEE_DES_EAUX);
						cartes.remove(Carte.MONTEE_DES_EAUX);
						modele.monteeEau();
						cancel();
					}
				}, 500);
			}
		}
	}

	/*
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

	public void discardTresor(int n){
		if(n >= 0 && n < cartes.size())
			modele.getPileCartes().defausser(cartes.remove(n));
		if(!modele.actionsRestantes())
			modele.finDeTour();

	}

	public void giveCarte(int n, Joueur j){
		if(getCartes().get(n) != Tresor.HELICOPTERE || getCartes().get(n) != Tresor.SAC_DE_SABLE) {
			j.getCartes().add(getCartes().remove(n));
			modele.useAction(Action.DONNER_CARTE);
			modele.notifyObservers();
		}
	}

	public void gainTresor(){
		int occurences = Collections.frequency(cartes, ZoneToTresor());
		if( occurences > 3){
			modele.recupereArtefact(getPosition().getType());
			for(int i =0 ; i < occurences; i++){
				cartes.remove(ZoneToTresor());
				modele.getPileCartes().defausser(ZoneToTresor());
			}
			modele.useAction(Action.GAGNER_TRESOR);
			modele.notifyObservers();
		}

	}*/

	public String toString()
	{
		return role + " est en " + position;
	}

}
