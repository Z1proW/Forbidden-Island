package fr.rui_tilmann.Controleur;

import fr.rui_tilmann.Modele.Case;
import fr.rui_tilmann.Modele.Enums.Direction;
import fr.rui_tilmann.Modele.Enums.Role;
import fr.rui_tilmann.Modele.Joueur;
import fr.rui_tilmann.Modele.Modele;
import fr.rui_tilmann.Vue.VuePlateau;

import javax.print.event.PrintJobEvent;
import java.awt.event.*;

import static fr.rui_tilmann.Vue.VuePlateau.P;

public class ControleurJoueur extends MouseAdapter implements KeyListener
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
		Case c = getCase(e);

		if(c == null) return;

		boolean diago = modele.getIdJoueur().getRole() == Role.EXPLORATEUR;

		if(c.estAdjacente(modele.getIdJoueur().getPosition(), diago))
		{
			if(e.getButton() == MouseEvent.BUTTON1)
				modele.getIdJoueur().deplace(c);
			else if(e.getButton() == MouseEvent.BUTTON3)
				modele.getIdJoueur().asseche(c);
		}

		if(c == modele.getIdJoueur().getPosition()
		&& e.getButton() == MouseEvent.BUTTON1) {
			Joueur j = modele.getIdJoueur();
			long occurences = j.getCartes().stream().filter(carte -> carte.toArtefact() == c.getType().toArtefact()).count();

			if(occurences > 3){
				for(int i=0 ; i< j.getCartes().size(); i++) {
					if (j.getCartes().get(i).toArtefact() == c.getType().toArtefact()){
						modele.getIdJoueur().recupereArtefact(j.getCartes().get(i));
						break;
					}
				}
			}
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

		modele.getIdJoueur().deplace(d);
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		Case c = getCase(e);

		if(c != null)
		{
			vuePlateau.hoveredCase = c;
			vuePlateau.repaint();
		}
		else mouseExited(e);
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		vuePlateau.hoveredCase = null;
		vuePlateau.repaint();
	}

	private Case getCase(MouseEvent e)
	{
		int x = (e.getX() - vuePlateau.getX());
		int y = (e.getY() - vuePlateau.getY());

		if(0 <= x && x < vuePlateau.getWidth()
		&& 0 <= y && y < vuePlateau.getHeight())
			return modele.getPlateau().getCase(x/P, y/P);
		return null;
	}

	public void keyPressed(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

}
