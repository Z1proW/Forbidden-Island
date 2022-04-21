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

	public Joueur clickedJoueur = null;
	public int clickedCard = -1;

	private Case caseHelico = null;

	private boolean actionSpeNavigateur = false;
	private boolean actionSpePlongeur = false;

	private int caseDeplace = 0;

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

		Joueur joueur = clickedJoueur;

		if(e.getButton() == MouseEvent.BUTTON3)
			caseHelico = c;

		if(0 <= clickedCard && clickedCard < joueur.getCartes().size()
		&& e.getButton() == MouseEvent.BUTTON1)
		{
			switch(joueur.getCartes().get(clickedCard))
			{
				case HELICOPTERE:
					if(c != caseHelico
					&& c.getEtat() != Etat.SUBMERGEE)
					{
						if(caseHelico == null)
							caseHelico = joueur.getPosition();

						caseHelico.getJoueurs().forEach(j -> j.deplace(c, false));
						joueur.defausseCarte(clickedCard);
						caseHelico = null;
					}
					break;

				case SAC_DE_SABLE:
					if(c.getEtat() == Etat.INONDEE)
					{
						joueur.asseche(c, true);
						joueur.defausseCarte(clickedCard);
					}
					break;
			}
			return;
		}

		if(modele.getCurrentJoueur().getRole() == Role.PILOTE
		&& !modele.actionUtiliseePilote
		&& e.getButton() == MouseEvent.BUTTON1)
		{
			modele.getCurrentJoueur().deplace(c);
			modele.actionUtiliseePilote = true;
			return;
		}

		boolean diago = modele.getCurrentJoueur().getRole() == Role.EXPLORATEUR;
		Joueur j = modele.getCurrentJoueur();

		if(c.estAdjacente(j.getPosition(), diago)) {
			switch (e.getButton())
			{
				case 1: // gauche
					if(j.getRole() == Role.INGENIEUR && modele.actionSpeIngenieur) {
						modele.actionSpeIngenieur = false;
						modele.useAction();
						if(!modele.actionsRestantes()) return;
					}
					j.deplace(c);
					break;

				case 3: // droit
					if(j.getRole() == Role.INGENIEUR && !modele.actionSpeIngenieur) {
						j.asseche(c, true);
						modele.actionSpeIngenieur = true;
					} else {
						j.asseche(c);
						modele.actionSpeIngenieur = false;
					}
			}
		}

		if(c == j.getPosition() && e.getButton() == MouseEvent.BUTTON1) {
			long occurences = j.getCartes().stream().filter(carte -> carte.toArtefact() == c.getType().toArtefact()).count();

			if(occurences > 3)
				for(Carte carte : j.getCartes())
					if(carte.toArtefact() == c.getType().toArtefact()) {
						if(j.getRole() == Role.INGENIEUR && modele.actionSpeIngenieur) {
							modele.actionSpeIngenieur = false;
							modele.useAction();
							if(!modele.actionsRestantes())return;
						}
						modele.getCurrentJoueur().recupereArtefact(carte);
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
			// déplacement
			case KeyEvent.VK_UP: 	d = Direction.NORD;  break;
			case KeyEvent.VK_RIGHT: d = Direction.EST; 	 break;
			case KeyEvent.VK_DOWN: 	d = Direction.SUD; 	 break;
			case KeyEvent.VK_LEFT: 	d = Direction.OUEST; break;

			// TODO trouver d'autre moyen pour changer
			case KeyEvent.VK_N:
				actionSpeNavigateur = !actionSpeNavigateur;
				break;

			case KeyEvent.VK_M:
				caseDeplace = (caseDeplace + 1) % 2;
				break;
		}

		// TODO à gerer les cas out of bounds afin qu'il utilise pas d'action
		if(modele.getCurrentJoueur().getRole() == Role.INGENIEUR && modele.actionSpeIngenieur) {
			modele.actionSpeIngenieur = false;
			modele.useAction();
			if(!modele.actionsRestantes()) return;
		}

		if(modele.getCurrentJoueur().getRole() == Role.NAVIGATEUR
		&& d != Direction.AUCUNE
		&& actionSpeNavigateur) {

			for(int i = 0; i <= caseDeplace; i++)
				clickedJoueur.deplace(d, actionSpeNavigateur);

			actionSpeNavigateur = false;
		}
		else modele.getCurrentJoueur().deplace(d);
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
	public void mouseDragged(MouseEvent e)
	{
		mouseMoved(e);
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
