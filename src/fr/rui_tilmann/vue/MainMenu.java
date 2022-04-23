package fr.rui_tilmann.vue;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel
{
    private final ImageIcon bg = new ImageIcon("src/fr/rui_tilmann/images/menu/background.png");

    MainMenu()
    {
        setPreferredSize(new Dimension(bg.getIconWidth(), bg.getIconHeight()));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.repaint();

        g.drawImage(bg.getImage(),0,0,null);

        g.setFont(new Font("", Font.BOLD, 50));

        // ombre
        g.setColor(Color.BLACK);
        g.drawString("L'île interdite".toUpperCase(), getWidth()/2 - 200 + 5, 60 + 5);

        g.setColor(new Color(255,200,100));
        g.drawString("L'île interdite".toUpperCase(), getWidth()/2 - 200, 60);
    }
}
