package fr.rui_tilmann.Controleur;

import fr.rui_tilmann.Modele.Case;
import fr.rui_tilmann.Modele.Enums.Action;
import fr.rui_tilmann.Modele.Enums.Direction;
import fr.rui_tilmann.Modele.Enums.Role;
import fr.rui_tilmann.Modele.Joueur;
import fr.rui_tilmann.Modele.Modele;
import fr.rui_tilmann.Vue.VuePlateau;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static fr.rui_tilmann.Vue.VuePlateau.P;

public class ControleurJoueur implements MouseListener, KeyListener
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

		if(0 <= x  && x <= 8*P && 0 <= y && y <= 8*P)
		{
			Case c = modele.getPlateau().getCase(x/P, y/P);
			boolean diago = modele.getJoueur().getRole() == Role.EXPLORATEUR;

			if(c.estAdjacente(modele.getJoueur().getPosition(), diago))
				modele.getJoueur().deplace(c);
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		Direction d = Direction.AUCUNE;

		switch(e.getKeyCode())
		{
			case KeyEvent.VK_UP: d = Direction.NORD; break;
			case KeyEvent.VK_RIGHT: d = Direction.EST; break;
			case KeyEvent.VK_DOWN: d = Direction.SUD; break;
			case KeyEvent.VK_LEFT: d = Direction.OUEST; break;
		}

		if(d != Direction.AUCUNE)
			modele.getJoueur().deplace(d);
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void keyPressed(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

}
