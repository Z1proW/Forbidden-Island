package fr.rui_tilmann.vue.jeu;

import fr.rui_tilmann.modele.enums.Artefact;
import fr.rui_tilmann.modele.Modele;
import fr.rui_tilmann.modele.enums.Role;
import fr.rui_tilmann.vue.Observer;
import fr.rui_tilmann.controleur.Bouton;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class VueBoutons extends JPanel implements Observer
{

	private final Modele modele;
	public Bouton boutonFinTour;
	public Bouton[] boutonJoueur = bJrs();
	public Bouton boutonActionSpe = new Bouton("", 0, 0, 0, 0);
	public Bouton MessagerJoueurAction;
	public Bouton MessagerDeplaceCase;

	public VueBoutons(Modele modele)
	{
		this.modele = modele;
		modele.addObserver(this);

		this.setPreferredSize(new Dimension(5*VueCartes.WIDTH, 8*VuePlateau.P - 4*VueCartes.HEIGHT));

		initBoutons();
	}

	private Bouton[] bJrs()
	{
		Bouton[] bts = new Bouton[Modele.NOMBRE_JOUEURS];
		for(int i = 0; i < bts.length; i++)
			bts[i] = new Bouton("", 0, 0, 0, 0);
		return bts;
	}

	private void initBoutons() {
		int width = 192;
		int height = 50;
		int x = 32;
		int y = 80;

		JLabel helicoText = new JLabel("Helico");
		helicoText.setBounds(x, y - 30, width, 30);
		helicoText.setForeground(Color.BLACK);
		helicoText.setHorizontalAlignment(JLabel.CENTER);
		add(helicoText);

		JLabel actionsRestantes = new JLabel();
		actionsRestantes.setBounds(x, y + height, width, 30);
		actionsRestantes.setForeground(Color.BLACK);
		actionsRestantes.setFont(new Font("", Font.BOLD, 16));
		actionsRestantes.setHorizontalAlignment(JLabel.CENTER);
		add(actionsRestantes);

		JLabel JoueurActuel = new JLabel();
		JoueurActuel.setBounds(x + width, 30 , width, 30);
		add(JoueurActuel);
		if(modele.getJoueurs().stream().anyMatch(e -> e.getRole() == Role.MESSAGER)) {
			MessagerJoueurAction = new Bouton("Navigateur Joueur Action : " + 0, x + width, y + height, width, height);
			MessagerDeplaceCase = new Bouton("Navigateur Deplace : j" + 1, x + 2 * width, y + height, width, height);
			add(MessagerJoueurAction);
			add(MessagerDeplaceCase);
		}

		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				actionsRestantes.setText("Actions restantes: " + modele.getNbActions());
			}
		}, 0, 10);

		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				String s = "";
				switch(modele.getCurrentJoueur().getRole()) {
					case INGENIEUR: s = "<font color='red'>";break;
					case MESSAGER: s = "<font color='silver'>";break;
					case PLONGEUR: s = "<font color='blue'>";break;
					case EXPLORATEUR: s = "<font color='green'>";break;
					case PILOTE: s = "<font color='black'>";break;
					case NAVIGATEUR: s = "<font color='yellow'>";break;
				}
				JoueurActuel.setText("<html>Joueur actuel: " + s +  modele.getCurrentJoueur().getRole() + "</font></html>");
			}
		}, 0, 100);

		boutonActionSpe = new Bouton("Action Speciale", x + width, y, width, height);
		boutonActionSpe.setBackground(Color.RED);
		add(boutonActionSpe);

		boutonFinTour = new Bouton("Fin de tour", x + 2*width, y, width, height);
		boutonFinTour.addActionListener(e -> modele.finDeTour());
		add(boutonFinTour);

		for(int i = 0; i < Modele.NOMBRE_JOUEURS; i++)
		{
			int dx = width/Modele.NOMBRE_JOUEURS;
			boutonJoueur[i] = new Bouton(String.valueOf(i + 1), x, y, dx, height);
			boutonJoueur[i].setBackground(Color.RED);
			add(boutonJoueur[i]);
			x += dx;
		}

		setLayout(new BorderLayout());
	}

	@Override
	public void update() {repaint();}

	public void paintComponent(Graphics g)
	{
		super.repaint();

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		Artefact[] artefacts = Artefact.values();

		for(int i = 0; i < 4; i++)
		{
			Artefact artefact = artefacts[i];

			if(modele.hasArtefact(artefact))
			{
				Image img = artefact.getImage();
				g.drawImage(img, i*(img.getWidth(null) + 5) + 5, 5, null);
			}
			else
			{
				Image img = artefact.getContour();
				g.drawImage(img, i*(img.getWidth(null) + 5) + 5, 5, null);
			}
		}
	}

}
