package fr.rui_tilmann.Controleur;

import fr.rui_tilmann.Modele.Case;
import fr.rui_tilmann.Modele.Enums.Carte;
import fr.rui_tilmann.Modele.Enums.Direction;
import fr.rui_tilmann.Modele.Enums.Etat;
import fr.rui_tilmann.Modele.Enums.Role;
import fr.rui_tilmann.Modele.Joueur;
import fr.rui_tilmann.Modele.Modele;
import fr.rui_tilmann.Vue.VuePlateau;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static fr.rui_tilmann.Vue.VuePlateau.P;

public class ControleurJoueur extends MouseAdapter implements KeyListener
{

	private final Modele modele;
	private final VuePlateau vuePlateau;

	public Joueur clickedJoueur;
	public int clickedCard;

	// TODO le faire marcher plusieurs personne sur la même case
	private int joueur_transporte = 0;

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

		boolean nonCarteAction = true;

		Joueur joueur = clickedJoueur;

		if(0 <= clickedCard && clickedCard < joueur.getCartes().size()) {
			switch(joueur.getCartes().get(clickedCard)) {
				case HELICOPTERE:
					if(c.getEtat() != Etat.SUBMERGEE)
					{
						modele.getJoueurs().get(joueur_transporte).deplace(c, true);
						joueur.defausseCarte(clickedCard);
					}
					break;

				case SAC_DE_SABLE:
					if(c.getEtat() == Etat.INONDEE)
					{
						joueur.asseche(c, true);
						joueur.defausseCarte(clickedCard);
					}
					break;

				default: nonCarteAction = false;

			}
		}

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

			if(occurences > 3)
				for(Carte carte : j.getCartes())
					if(carte.toArtefact() == c.getType().toArtefact()) {
						modele.getIdJoueur().recupereArtefact(carte);
						break;
					}
		}

	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		Direction d = Direction.AUCUNE;

		switch(e.getKeyCode())
		{
			case KeyEvent.VK_UP: 	d = Direction.NORD;  break;
			case KeyEvent.VK_RIGHT: d = Direction.EST; 	 break;
			case KeyEvent.VK_DOWN: 	d = Direction.SUD; 	 break;
			case KeyEvent.VK_LEFT: 	d = Direction.OUEST; break;
			//TODO trouver un meilleur moyen de déplacer la personne
			case KeyEvent.VK_SPACE:
				joueur_transporte = (joueur_transporte + 1) % 4;
				System.out.println(joueur_transporte);
				break;
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
