package com.pomc.view;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.swing.JOptionPane.showMessageDialog;
public class MainMenu {
    static JFrame frame = new JFrame("POMC WORKSHEETS");
    public static void main(String args[]) throws IOException {
        // MARCO DE TO DO
        frame.repaint();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        //MENU DE ARRIBA
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("File");
        JMenu m2 = new JMenu("HELP");
        mb.add(m1);
        mb.add(m2);
        JMenuItem newDocB = new JMenuItem("New");
        JMenuItem openB = new JMenuItem("Open...");
        JMenuItem exitB = new JMenuItem("Exit POMC WORKSHEETS");
        JMenuItem validFormatB = new JMenuItem("Valid formats");
        JMenuItem userGuideB = new JMenuItem("Users Guide");
        JMenuItem aboutB = new JMenuItem("About");
        m1.add(newDocB);
        m1.add(openB);
        m1.add(exitB);
        m2.add(validFormatB);
        m2.add(userGuideB);
        m2.add(aboutB);

        //LISTENERS



        exitB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        aboutB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showMessageDialog(null, "Hello People", "About", JOptionPane.PLAIN_MESSAGE);
            }
        });

        validFormatB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showMessageDialog(null, "DATE : \n YYYY-MM-DD ", "Valid Formats", JOptionPane.PLAIN_MESSAGE);
            }
        });


        // Área de texto en el centro
        JPanel centro = new JPanel(new BorderLayout());
        JPanel top = new JPanel(new BorderLayout());
        JLabel intro = new JLabel("<html>BIENVENIDO A POMC WORKSHEETS<br/><br/><br/>Para empezar crea un nuevo archivaisdnbvuianovibnewaoi</html>");
        intro.setAlignmentX(Component.LEFT_ALIGNMENT);
        top.add(intro,BorderLayout.WEST);
        centro.add(top,BorderLayout.NORTH);
        // Agregar componentes al marco.
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, centro);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        newDocB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().remove(centro);
                frame.repaint();
                frame.getContentPane().add(SheetView.cambio());
                SheetView.updateMenu(mb);
                frame.setVisible(true);
            }
        });
    }
}
