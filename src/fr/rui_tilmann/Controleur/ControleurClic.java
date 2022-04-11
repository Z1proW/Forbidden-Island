package fr.rui_tilmann.Controleur;

import fr.rui_tilmann.Modele.Case;
import fr.rui_tilmann.Modele.Enums.Etat;
import fr.rui_tilmann.Modele.Modele;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static fr.rui_tilmann.Vue.VuePlateau.P;

public class ControleurClic implements MouseListener
{

	private final Modele modele;
	private JFrame f;

	public ControleurClic(Modele modele)
	{
		this.modele = modele;
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		int x = (e.getX()-8); int y = (e.getY()-31);

		if(x >= 8*P || y >= 8*P) return;

		Case c = modele.getCase(x/P, y/P);
		System.out.println("clic sur " + c);

		if(e.getButton() == MouseEvent.BUTTON3)
			c.setEtat(Etat.SUBMERGEE);
		else if(e.getButton() == MouseEvent.BUTTON1)
			c.setEtat(Etat.SECHE);

		modele.notifyObservers();
	}

	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}

}
