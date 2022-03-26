package fr.rui_tilmann.Modele;

import fr.rui_tilmann.Modele.Enums.Direction;
import fr.rui_tilmann.Modele.Enums.Etat;

public class Joueur
{

	private final Modele modele;
	private Case pos;

	public Joueur(Modele modele, Case pos)
	{
		this.modele = modele;
		this.pos = pos;
	}
	public Case Jget_pos(){
		return this.pos;
	}
	public void deplace(Direction d){
		Case newpos = Jget_pos().adjacente(d);
		if( newpos.etat != Etat.SUBMERGEE){
			pos = newpos;
		}
	}

}
