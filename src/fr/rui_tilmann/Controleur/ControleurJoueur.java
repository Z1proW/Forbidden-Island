package fr.rui_tilmann.Controleur;

import fr.rui_tilmann.Modele.Case;
import fr.rui_tilmann.Modele.Enums.Action;
import fr.rui_tilmann.Modele.Modele;
import fr.rui_tilmann.Vue.VuePlateau;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static fr.rui_tilmann.Vue.VuePlateau.P;

public class ControleurJoueur implements MouseListener
{

	private final Modele modele;
	private final VuePlateau vuePlateau;

	public ControleurJoueur(Modele modele, VuePlateau vuePlateau)
	{
		this.modele = modele;
		this.vuePlateau = vuePlateau;
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		int x = (e.getX() - vuePlateau.getX());
		int y = (e.getY() - vuePlateau.getY());

		if(x >= 8*P || y >= 8*P) return;

		Case c = modele.getPlateau().getCase(x/P, y/P);
		System.out.println("clic sur " + c);

		if(modele.getJoueur().deplace(c))
		{
			modele.useAction(Action.DEPLACER);
			modele.notifyObservers();
		}
	}

	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

}
