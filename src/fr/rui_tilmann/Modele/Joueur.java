package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.Direction;
import fr.rui_tilmann.Modele.Enums.Etat;
import fr.rui_tilmann.Modele.Enums.Role;
import fr.rui_tilmann.Modele.Enums.Tresor;

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

	public  void  deplace(Case c){
		position = c;
	}

	public void deplace(Direction d) {
		Case newPos = getPosition().adjacente(d);
		if(newPos.getEtat() != Etat.SUBMERGEE || this.getRole() == Role.PLONGEUR) {
			position = newPos;
		}
	}

	public List<Tresor> getCartes() {return cartes;}

	// TODO gérer 5 cartes max
	public void piocheTresor()
	{
		Tresor tresor = modele.getPileCartes().getTresor();

		if(tresor == Tresor.MONTEE_DES_EAUX)
			modele.monteeEau();
		else cartes.add(tresor);
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
