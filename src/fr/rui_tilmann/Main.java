package fr.rui_tilmann;

import fr.rui_tilmann.vue.menu.VueMenu;

import java.awt.*;

public class Main
{

    public static void main(String[] args)
    {
        EventQueue.invokeLater(VueMenu::new);
    }

}
