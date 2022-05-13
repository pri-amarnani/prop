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
    static boolean menuUpdated=false;
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
        JLabel intro = new JLabel("<html>BIENVENIDO A POMC WORKSHEETS<br/><br/><br/>Para empezar crea un nuevo archivo</html>");
        intro.setFont(new Font("Serif",Font.BOLD,25));
        intro.setAlignmentX(Component.CENTER_ALIGNMENT);
        top.add(intro,BorderLayout.CENTER);
        centro.add(top,BorderLayout.CENTER);
        // Agregar componentes al marco.
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, centro);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        newDocB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            JTextField title= new JTextField("untitled_doc");
            JTextField newsheet_title= new JTextField("sheet1");
            JTextField nrows= new JTextField("25");
            JTextField ncolumns= new JTextField("25");
            Object[] fields= {
                    "Insert a title for the new document",title,
                    "Insert a title for the new sheet", newsheet_title,
                    "Insert the number of rows in the new sheet", nrows,
                    "Insert the number of columns in the new sheet",ncolumns,
            };
                int result=  JOptionPane.showConfirmDialog(
                        null,
                        fields,
                        "New document",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null

                );
              /*  String result2= (String) JOptionPane.showInputDialog(
                        frame,
                        "New Document",
                        "Insert a title for the new document",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "untitled_doc"

                );
                 int result3=  JOptionPane.showConfirmDialog(
                        null,
                        fields,
                        "New sheet",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null

                );*/
                if(result==JOptionPane.OK_OPTION) {
                    PresentationController.newDoc(title.getText());
                    frame.getContentPane().remove(centro);
                    frame.repaint();
                    int numfil=Integer.parseInt(nrows.getText());
                    int numcol=Integer.parseInt(ncolumns.getText());;
                    frame.getContentPane().add(SheetView.cambio(numfil,numcol));
                    if(!menuUpdated){
                        menuUpdated=true;
                        SheetView.updateMenu(mb);
                    }
                    frame.setTitle(title.getText() + " - POMC WORKSHEETS");
                    frame.setVisible(true);

               }


            }
        });


    }
}
