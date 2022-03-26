package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.Direction;
import fr.rui_tilmann.Modele.Enums.Etat;
import fr.rui_tilmann.Modele.Enums.Role;

public class Joueur
{

	private final Modele modele;
	private final Role role;
	private Case pos;

	public Joueur(Modele modele, Role role, Case pos)
	{
		this.modele = modele;
		this.role = role;
		this.pos = pos;
	}

	public Role getRole() {return role;}

	public Case getPosition() {return this.pos;}

	public void deplace(Direction d) {
		Case newPos = getPosition().adjacente(d);
		if(newPos.etat != Etat.SUBMERGEE) {
			pos = newPos;
		}
	}

}
