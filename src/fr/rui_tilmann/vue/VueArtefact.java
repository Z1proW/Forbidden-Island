package fr.rui_tilmann.vue;

import fr.rui_tilmann.modele.enums.Artefact;
import fr.rui_tilmann.modele.Modele;
import fr.rui_tilmann.vue.menu.Bouton;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class VueArtefact extends JPanel implements Observer
{

	private final Modele modele;
	public Bouton boutonFinTour;
	public Bouton[] boutonJoueur = bJrs();
	public Bouton boutonActionSpe = new Bouton("", 0, 0, 0, 0);

	public VueArtefact(Modele modele)
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
		helicoText.setForeground(Color.WHITE);
		helicoText.setHorizontalAlignment(JLabel.CENTER);
		add(helicoText);

		JLabel actionsRestantes = new JLabel();
		actionsRestantes.setBounds(x, y + height, width, 30);
		actionsRestantes.setForeground(Color.WHITE);
		actionsRestantes.setFont(new Font("", Font.PLAIN, 16));
		actionsRestantes.setHorizontalAlignment(JLabel.CENTER);

		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				actionsRestantes.setText("Actions restantes: " + modele.getNbActions());
			}
		}, 0, 10);

		add(actionsRestantes);

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

		g.setColor(Color.BLACK);
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
