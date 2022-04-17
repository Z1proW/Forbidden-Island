package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static fr.rui_tilmann.Modele.Enums.Zone.*;

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
		this.cartes = new ArrayList<>(10);
	}

	public Role getRole() {return role;}

	public Case getPosition() {return this.position;}

	public void deplace(Case c)
	{
		if(modele.actionsRestantes()) {
			if (c.getEtat() != Etat.SUBMERGEE
					|| role == Role.PLONGEUR) {
				position = c;
				modele.useAction(Action.DEPLACER);
				modele.notifyObservers();
			}
		}
	}

	public void deplace(Direction d) {
		deplace(getPosition().adjacente(d));
	}

	public void assecherCase(Case c){
		if(modele.actionsRestantes()) {
			if (c.getEtat() == Etat.INONDEE) {
				c.setEtat(Etat.SECHE);
				modele.useAction(Action.ASSECHER);
				modele.notifyObservers();
			}
		}
	}

	public void assecherCase(Direction d){
		assecherCase(getPosition().adjacente(d));
	}

	public void  assecherCase(){
		assecherCase(getPosition());
	}

	public List<Tresor> getCartes() {return cartes;}

	public void piocheTresor(ArrayList<Tresor> t)
	{
		for(Tresor tresor: t ) {
			if (cartes.size() < 10)
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

	}
	Tresor ZoneToTresor(){
		switch (getPosition().getType()){
			case AIR: return Tresor.AIR;
			case FEU: return Tresor.FEU;
			case EAU: return Tresor.EAU;
			case TERRE: return  Tresor.TERRE;
		}
		return null;
	}

	public String toString()
	{
		return role + " est en " + position;
	}

}
