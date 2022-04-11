package fr.rui_tilmann.Controleur;

import fr.rui_tilmann.Modele.Case;
import fr.rui_tilmann.Modele.Enums.Etat;
import fr.rui_tilmann.Modele.Modele;
import fr.rui_tilmann.Vue.VueCartes;
import fr.rui_tilmann.Vue.VuePlateau;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static fr.rui_tilmann.Vue.VuePlateau.P;

public class ControleurClic implements MouseListener
{

	private final Modele modele;
	private final VuePlateau vuePlateau;

	public ControleurClic(Modele modele, VuePlateau vuePlateau)
	{
		this.modele = modele;
		this.vuePlateau = vuePlateau;
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		int x = (e.getX()-vuePlateau.getX()); int y = (e.getY()-vuePlateau.getY());

		if(x >= 8*P || y >= 8*P) return;

		Case c = modele.getCase(x/P, y/P);
		System.out.println("clic sur " + c);

		if(e.getButton() == MouseEvent.BUTTON3)
			c.setEtat(Etat.SUBMERGEE);
		else if(e.getButton() == MouseEvent.BUTTON1)
			c.setEtat(Etat.SECHE);

		modele.notifyObservers();
	}

	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

}
