package com.pomc.view;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static javax.swing.JOptionPane.showMessageDialog;
public class MainMenu {
    static boolean menuUpdated=false;
    static JFrame frame = new JFrame("POMC WORKSHEETS");

    public static void main(String args[]) throws IOException {
        // MARCO DE TO DO
        frame.repaint();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 900);

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


        userGuideB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    Desktop.getDesktop().browse(new URI("https://www.google.com/"));
                }
                catch (URISyntaxException | IOException e2){
                    e2.printStackTrace();
                }

                };

        });


        openB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        });


        // Área de texto en el centro
        JPanel centro = new JPanel(new BorderLayout());
        JPanel top = new JPanel(new BorderLayout());
        //JLabel intro = new JLabel("<html>BIENVENIDO A POMC WORKSHEETS<br/><br/><br/>Para empezar crea un nuevo archivo</html>");
        //intro.setFont(new Font("Serif",Font.BOLD,25));
        //intro.setAlignmentX(Component.CENTER_ALIGNMENT);
        BufferedImage logo= ImageIO.read(new File("res/LOGO.png")); //TODO Persistencia
        JLabel logoLabel= new JLabel(new ImageIcon(logo));
        top.setBackground(Color.white);
        top.add(logoLabel,BorderLayout.CENTER);
        centro.add(top,BorderLayout.CENTER);
        // Agregar componentes al marco.
        JPanel menuPanel= new JPanel(new BorderLayout());
        menuPanel.add(BorderLayout.NORTH,mb);
        frame.getContentPane().add(BorderLayout.NORTH, menuPanel);
        //Color cfons= new Color(0x4D4D97);
        frame.getContentPane().add(BorderLayout.CENTER, centro);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

        newDocB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                boolean ok = false;
                while (!ok) {

                    JTextField newsheet_title = new JTextField("sheet 1");
                    JTextField nrows = new JTextField("25");
                    JTextField ncolumns = new JTextField("25");
                    Object[] fields = {
                            "Insert a title for the new sheet", newsheet_title,
                            "Insert the number of rows in the new sheet", nrows,
                            "Insert the number of columns in the new sheet", ncolumns,
                    };

                    String result2 = (String) JOptionPane.showInputDialog(
                            frame,
                            "New Document",
                            "Insert a title for the new document",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            null,
                            "untitled_doc"

                    );
                    if (result2==null) ok=true;
                    else {
                        int result3 = JOptionPane.showConfirmDialog(
                                null,
                                fields,
                                "New sheet",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.PLAIN_MESSAGE,
                                null


                        );
                        if (nrows.getText()!= null&&ncolumns.getText()!=null && isNumeric(nrows.getText()) && isNumeric(ncolumns.getText())) {
                            if (result3 == JOptionPane.OK_OPTION) {
                                Integer numfil = Integer.parseInt(nrows.getText());
                                Integer numcol = Integer.parseInt(ncolumns.getText());
                                if (numfil > 0 && numcol > 0) {
                                    PresentationController.newDoc(result2);
                                    BorderLayout blayout = (BorderLayout) frame.getContentPane().getLayout();
                                    frame.getContentPane().remove(blayout.getLayoutComponent(BorderLayout.CENTER));
                                    frame.repaint();
                                        ok = true;
                                        PresentationController.newSheet(newsheet_title.getText(), numfil, numcol);
                                        frame.getContentPane().add(BorderLayout.CENTER, SheetView.cambio(true));

                                        if (!menuUpdated) {
                                            menuUpdated = true;
                                            SheetView.updateMenu(mb);
                                        }
                                        frame.setTitle(result2 + " - POMC WORKSHEETS");
                                        frame.setVisible(true);
                                }


                            }
                        }
                        if (result3 == JOptionPane.CANCEL_OPTION|| result3==JOptionPane.CLOSED_OPTION) ok = true;
                    }
                    if (!ok) showMessageDialog(null,"Invalid values. Couldn't create document.\nTry again","Error!",JOptionPane.ERROR_MESSAGE);

                }
            }
        });


    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public static JFrame getCurrentFrame(){
        return frame;
    }



}

