package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.Direction;
import fr.rui_tilmann.Modele.Enums.Etat;
import fr.rui_tilmann.Modele.Enums.Role;
import fr.rui_tilmann.Modele.Enums.Tresor;

import java.util.ArrayList;

public class Joueur
{

	private final Modele modele;
	private final Role role;
	private Case pos;
	private ArrayList<Tresor> tresors;

	public Joueur(Modele modele, Role role, Case pos)
	{
		this.modele = modele;
		this.role = role;
		this.pos = pos;
		this.tresors = new ArrayList<>();
	}

	public Role getRole() {return role;}

	public Case getPosition() {return this.pos;}

	public void deplace(Direction d) {
		Case newPos = getPosition().adjacente(d);
		if(newPos.getEtat() != Etat.SUBMERGEE || this.getRole() == Role.PLONGEUR) {
			pos = newPos;
		}
	}

	public void piocheTresor(){
		Tresor tresor = modele.getPileCartes().getTresor();
		if(tresor != Tresor.MONTEEDESEAUX){
			tresors.add(tresor);
		}
	}

	public Tresor utiliseTresor(int n){
		try{
			return tresors.remove(n);
		}catch (Exception e) {
			System.out.println("Pas de carte à cette emplacement");
			return null;
		}
	}

	public String toString()
	{
		return role + " est en " + pos;
	}

}
