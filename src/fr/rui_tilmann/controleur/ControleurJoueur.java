package fr.rui_tilmann.controleur;

import fr.rui_tilmann.modele.Case;
import fr.rui_tilmann.modele.enums.Carte;
import fr.rui_tilmann.modele.enums.Direction;
import fr.rui_tilmann.modele.enums.Etat;
import fr.rui_tilmann.modele.enums.Role;
import fr.rui_tilmann.modele.Joueur;
import fr.rui_tilmann.modele.Modele;
import fr.rui_tilmann.vue.VuePlateau;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static fr.rui_tilmann.vue.VuePlateau.P;
import static fr.rui_tilmann.vue.VueArtefact.boutonJoueur1;
import static fr.rui_tilmann.vue.VueArtefact.boutonJoueur2;
import static fr.rui_tilmann.vue.VueArtefact.boutonJoueur3;
import static fr.rui_tilmann.vue.VueArtefact.boutonJoueur4;
import static fr.rui_tilmann.vue.VueArtefact.boutonActionSpe;

public class ControleurJoueur extends MouseAdapter implements KeyListener
{

	private final Modele modele;
	private final VuePlateau vuePlateau;

	public Joueur clickedJoueur = null;
	public int clickedCard = -1;

	private Case caseHelico = null;

	private boolean actionSpeNavigateurOuPilote = false;
	private boolean actionSpePlongeur = false;
	private boolean j1, j2, j3, j4 = false;
	private int joueurDeplace = 0;

	private int caseDeplace = 0;

	public ControleurJoueur(Modele modele, VuePlateau vuePlateau)
	{
		this.modele = modele;
		this.vuePlateau = vuePlateau;

		boutonJoueur1.addActionListener(e -> {
			AbstractButton button = (AbstractButton) e.getSource();
			Color color = button.getBackground();
			if(color == Color.RED) {
				boutonJoueur1.setBackground(Color.GREEN);
				j1 = true;
			}
			else {
				boutonJoueur1.setBackground(Color.RED);
				j1 = false;
			}
		});
		boutonJoueur2.addActionListener(e -> {
			AbstractButton button = (AbstractButton) e.getSource();
			Color color = button.getBackground();
			if(color == Color.RED) {
				boutonJoueur2.setBackground(Color.GREEN);
				j2 = true;
			}
			else {
				boutonJoueur2.setBackground(Color.RED);
				j2 = false;
			}
		});
		boutonJoueur3.addActionListener(e -> {
			AbstractButton button = (AbstractButton) e.getSource();
			Color color = button.getBackground();
			if(color == Color.RED) {
				boutonJoueur3.setBackground(Color.GREEN);
				j3 = true;
			}
			else {
				boutonJoueur3.setBackground(Color.RED);
				j3 = false;
			}
		});
		boutonJoueur4.addActionListener(e -> {
			AbstractButton button = (AbstractButton) e.getSource();
			Color color = button.getBackground();
			if(color == Color.RED) {
				boutonJoueur4.setBackground(Color.GREEN);
				j4 = true;
			}
			else {
				boutonJoueur4.setBackground(Color.RED);
				j4 = false;
			}
		});
		boutonActionSpe.addActionListener(e -> {
			if(!actionSpeNavigateurOuPilote) {
				boutonActionSpe.setBackground(Color.GREEN);
				actionSpeNavigateurOuPilote = true;
			}
			else {
				boutonActionSpe.setBackground(Color.RED);
				actionSpeNavigateurOuPilote = false;
			}
		});
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		vuePlateau.setFocusable(true);
		Case c = getCase(e);
		if(c == null) return;

		Joueur joueur = clickedJoueur;

		if(e.getButton() == MouseEvent.BUTTON3)
			caseHelico = c;

		if(0 <= clickedCard && clickedCard < clickedJoueur.getCartes().size()
		&& e.getButton() == MouseEvent.BUTTON1)
		{
			switch(clickedJoueur.getCartes().get(clickedCard))
			{
				case HELICOPTERE:
					if(c != caseHelico
					&& c.getEtat() != Etat.SUBMERGEE)
					{
						if(caseHelico == null)
							caseHelico = clickedJoueur.getPosition();

						for(Joueur j : caseHelico.getJoueurs()) {
							if(j == modele.getJoueur(0) && j1)
								j.deplace(c, false);
							else if(j == modele.getJoueur(1) && j2)
								j.deplace(c, false);
							else if(j == modele.getJoueur(2) && j3)
								j.deplace(c, false);
							else if(j == modele.getJoueur(3) && j4)
								j.deplace(c, false);
						}
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
		&& !modele.actionUtiliseePilote && actionSpeNavigateurOuPilote
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
				actionSpeNavigateurOuPilote = !actionSpeNavigateurOuPilote;
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
		&& actionSpeNavigateurOuPilote) {

			//TODO Avec joueurClicked ca ne marche pas
			for(int i = 0; i <= caseDeplace; i++) {
				modele.getJoueur(joueurDeplace).deplace(d, actionSpeNavigateurOuPilote);
			}
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
