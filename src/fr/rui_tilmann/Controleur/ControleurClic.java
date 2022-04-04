package fr.rui_tilmann.Controleur;

import fr.rui_tilmann.Modele.Case;
import fr.rui_tilmann.Modele.Modele;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static fr.rui_tilmann.Vue.VuePlateau.P;

public class ControleurClic implements MouseListener
{

	private final Modele modele;

	public ControleurClic(Modele modele) {this.modele = modele;}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		System.out.println("clic");
		Case c = modele.getCase(e.getX()/P, e.getY()/P);
		// TODO fix modele.notifyObservers();
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}
