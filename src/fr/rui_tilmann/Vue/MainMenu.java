package fr.rui_tilmann.Vue;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel
{
    private ImageIcon bg = new ImageIcon("src/fr/rui_tilmann/images/menu/background.png");

    public MainMenu()
    {
        setPreferredSize(new Dimension(bg.getIconWidth(), bg.getIconHeight()));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.repaint();

        g.drawImage(bg.getImage(),0,0,null);
        g.setColor(new Color(255,200,100));
        g.setFont(new Font("SegoeUI", Font.BOLD, 50));
        g.drawString("L'ILE INTERDITE", getWidth()/2 - 200, 60);
    }
}
