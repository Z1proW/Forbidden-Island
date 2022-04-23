package fr.rui_tilmann;

import fr.rui_tilmann.vue.Vue;

import java.awt.*;

public class Main
{

    public static void main(String[] args)
    {
        EventQueue.invokeLater(Vue::new);
    }

}
