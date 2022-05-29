package com.pomc.view;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static java.lang.Math.*;
import static javax.swing.JOptionPane.showMessageDialog;

public class SheetView {
    static JTabbedPane sheets = new JTabbedPane();
    static JLabel bar=new JLabel();
    static JMenuBar jmbar_sheet= new JMenuBar();
    static boolean changedMenu=false;

    static boolean writeChange=false;
    public static JPanel cambio() {
        sheets.removeAll();
        // el panel con barras de scroll automáticas
        JPanel frame = new JPanel(new BorderLayout());
        String[] sheets_names = PresentationController.getSheets();


        for (int i = 0; i < sheets_names.length; i++) {
            int numfil=PresentationController.getSheetRows(i,sheets_names[i]);
            int numcol= PresentationController.getSheetCols(i,sheets_names[i]);
            //TABLE
            JTable table = new JTable();
            table.putClientProperty("terminateEditOnFocusLost", true);
            table.getTableHeader().setReorderingAllowed(false);
            DefaultTableModel model=new DefaultTableModel(numfil,numcol);
            table.setModel(model);
            //table.setColumnModel(columnsModel);
            ArrayList<String> rowheaders = new ArrayList<>(numfil);
            for (int j = 1; j <= numfil; j++) {
                rowheaders.add("" + j);

            }
            ListModel l = new AbstractListModel() {
                @Override
                public int getSize() {
                    return rowheaders.size();
                }

                @Override
                public Object getElementAt(int index) {
                    return rowheaders.get(index);
                }
            };


            for (int j=0;j<numcol;j++){
                table.getColumnModel().getColumn(j).setHeaderValue(numToAlphabet(j));
            }

            JList rowHeader = new JList(l);

            rowHeader.setFixedCellWidth(50);
            rowHeader.setFixedCellHeight(table.getRowHeight());
            Color c = new Color(70, 130, 180);
            rowHeader.setBackground(c);
            rowHeader.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            rowHeader.setForeground(Color.WHITE);



            table.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.WHITE));
            table.getTableHeader().setBackground(c);
            table.getTableHeader().setForeground(Color.WHITE);
            rowHeader.setCellRenderer(getRenderer());

            model = (DefaultTableModel) (table.getModel());



            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            JScrollPane scrollPane = new JScrollPane(
                    table,
                    ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS
            );
            // pongo el botón en la ventana

            scrollPane.setRowHeaderView(rowHeader);

            bar.setBackground(Color.LIGHT_GRAY);
            bar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            bar.setText("    ");
            bar.setMinimumSize(new Dimension(table.getWidth(),25));
            bar.setPreferredSize(new Dimension(table.getWidth(),25));
            bar.setMaximumSize(new Dimension(table.getWidth(),25));
            frame.add(BorderLayout.NORTH,bar);

            sheets.addTab(sheets_names[i], null, scrollPane);



            //Cell listeners

            model.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    if (e.getType() == 0 && !writeChange) {
                        int rowChanged = e.getFirstRow();
                        int colChanged = e.getColumn();

                        if (rowChanged >= 0 && rowChanged < getCurrentTable().getRowCount() && colChanged >= 0 && colChanged < getCurrentTable().getColumnCount()) {
                           //si no son ref:
                            int colIndex = getCurrentTable().convertColumnIndexToView(colChanged);

                            TableModel tm = getCurrentTable().getModel();

                            String newValue = (String) table.getValueAt(rowChanged, colIndex);
                            PresentationController.editedCell(rowChanged, colIndex, newValue, currentSheetName());

                            boolean b=PresentationController.hasRefs(rowChanged,colIndex,currentSheetName());
                            if(b){
                                Object[] Rmodify= PresentationController.getRefsIds(rowChanged,colIndex,currentSheetName());
                                for (int i = 0; i < Rmodify.length; i+=2) {
                                    int rRow= (int) Rmodify[i];
                                    int rCol= (int) Rmodify[i+1];
                                    System.out.println("refs ids: "+rRow+" , "+rCol);
                                    writeBlock(rRow,rCol,rRow,rCol);
                                }
                            }

                        }



                    }
                    PresentationController.showTcells(currentSheetName());
                }
            });

            table.addMouseListener(new MouseAdapter() {
                int row1,col1,row2,col2;
                @Override
                public void mousePressed(MouseEvent e) {
                    table.clearSelection();
                    if(changedMenu) {
                        int count=jmbar_sheet.getMenuCount();
                        for (int j = count-2; j >= 5; j--) {
                            jmbar_sheet.remove(j);

                        }
                        jmbar_sheet.repaint();
                        jmbar_sheet.revalidate();
                        changedMenu=false;
                    }
                    int row=table.rowAtPoint(e.getPoint());
                    int col=table.columnAtPoint(e.getPoint());
                    if(row<table.getRowCount() && row>=0 && col>=0 && col<table.getColumnCount()){
                        row1=row;
                        col1=col;
                        bar.setText(PresentationController.cellInfo(row,col,currentSheetName()));
                    }

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                    int row=table.rowAtPoint(e.getPoint());
                    int col=table.columnAtPoint(e.getPoint());
                    if(row<table.getRowCount() && row>=0 && col>=0 && col<table.getColumnCount() ){
                        bar.setText(PresentationController.cellInfo(row,col,currentSheetName()));
                        col2=col;
                        row2=row;

                        PresentationController.createBlock(min(row1,row2),min(col1,col2),max(row1,row2),max(col1,col2),currentSheetName());
                        updateBlockMenu(jmbar_sheet);
                        System.out.println();
                    }
                }

            });

            table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            table.setColumnSelectionAllowed(false);
            table.setRowSelectionAllowed(false);
            table.setCellSelectionEnabled(true);
            PresentationController.createBlock(0,0,numfil-1,numcol-1,sheets_names[i]);



        }

        JButton newsheet = new JButton("+");
        newsheet.setBorderPainted(false);
        newsheet.setFocusPainted(false);
        newsheet.setContentAreaFilled(false);
        newsheet.setBackground(frame.getBackground());
        Font f= new Font("Comic Sans",Font.BOLD,10);
        newsheet.setFont(f);
        sheets.setSize(5,5);
        frame.add(BorderLayout.PAGE_END, newsheet);
        JPanel panelboton= new JPanel();
        panelboton.add(newsheet);
        sheets.add("extra",null);
        sheets.setTabComponentAt(sheets.getTabCount()-1,panelboton);
        sheets.setEnabledAt(sheets.getTabCount()-1,false);

        frame.add(BorderLayout.CENTER, sheets);


        newsheet.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean ok = false;
                while (!ok) {
                    int aux = PresentationController.getNumberofSheets() + 1;
                    JTextField newsheet_title;
                    if (existsSheetName("sheet " + aux)) ++aux;
                    newsheet_title = new JTextField("sheet " + aux);
                    JTextField nrows = new JTextField("25");
                    JTextField ncolumns = new JTextField("25");
                    Object[] fields = {
                            //"Insert a title for the new document",title,
                            "Insert a title for the new sheet", newsheet_title,
                            "Insert the number of rows in the new sheet", nrows,
                            "Insert the number of columns in the new sheet", ncolumns,
                    };

                    int newsheetpop = JOptionPane.showConfirmDialog(
                            null,
                            fields,
                            "New sheet",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE,
                            null

                    );
                    if (newsheetpop == JOptionPane.OK_OPTION && nrows.getText()!=null &&ncolumns.getText() !=null && isNumeric(nrows.getText()) && isNumeric(ncolumns.getText())) {
                        Integer numfil = Integer.parseInt(nrows.getText());
                        Integer numcol = Integer.parseInt(ncolumns.getText());
                        if (numfil > 0 && numcol > 0) {
                            String title=newsheet_title.getText();
                            if (existsSheetName(title)) title=title+"_v2";
                            sheets.insertTab(title, null, addSheet(title, numfil, numcol), null, sheets.getTabCount() - 1);
                            frame.setVisible(true);
                            sheets.setSelectedIndex(sheets.getTabCount() - 2);
                            ok=true;
                        }
                    }

                    if (newsheetpop == JOptionPane.CANCEL_OPTION || newsheetpop==JOptionPane.CLOSED_OPTION) ok = true;

                    if (!ok)
                        showMessageDialog(null, "Invalid values. Couldn't create sheet.\nTry again", "Error!", JOptionPane.ERROR_MESSAGE);

                }
            }

        });


        return frame;
    }
    public static void updateMenu(JMenuBar mb) {
        JMenu m = mb.getMenu(0);
        JMenuItem saveB = new JMenuItem("Save");
        JMenuItem saveAsB = new JMenuItem("Save As...");
        JMenuItem exportB = new JMenuItem("Export...");
        JMenuItem propertiesB = new JMenuItem("Properties");
        m.add(saveB,3);
        m.add(saveAsB,4);
        m.add(exportB,5);
        m.add(propertiesB,6);
        JMenu block= new JMenu("Block");
        JMenuItem create= new JMenuItem("Create");
        JMenuItem create_and_func= new JMenuItem("Create & Apply function");
        JMenuItem check= new JMenuItem(("Check current block"));
        block.add(create);
        block.add(create_and_func);
        block.add(check);
        JMenu sheet= new JMenu(("Sheet"));
        JMenuItem change_name= new JMenuItem("Change name");
        JMenu rows= new JMenu("Rows...");
        JMenuItem rowsInsert= new JMenuItem("Insert");
        JMenuItem rowsDelete= new JMenuItem("Delete");
        rows.add(rowsInsert);
        rows.add(rowsDelete);
        JMenu cols= new JMenu("Columns...");
        JMenuItem colsInsert= new JMenuItem("Insert");
        JMenuItem colsDelete= new JMenuItem("Delete");
        cols.add(colsInsert);
        cols.add(colsDelete);
        JMenuItem delete= new JMenuItem("Delete current sheet");
        sheet.add(change_name);
        sheet.add(rows);
        sheet.add(cols);
        sheet.add(delete);


        jmbar_sheet.add(Box.createHorizontalGlue());
        // TODO: 18/5/22 persistencia
        ImageIcon findIcon= new ImageIcon("res/find.png");
        Image fIcon=findIcon.getImage();
        Image fi=fIcon.getScaledInstance(40,40,Image.SCALE_DEFAULT);
        findIcon.setImage(fi);
        JButton find= new JButton(findIcon);
        find.setToolTipText("Find");
        Color c = new Color(70, 130, 180);
        find.setBackground(jmbar_sheet.getBackground());
        find.setBorder(BorderFactory.createLineBorder(c,1));
        jmbar_sheet.add(find);



        find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {FuncView.addFind();}
        });



        ImageIcon findRIcon= new ImageIcon("res/findR.png");
        Image fRcon=findRIcon.getImage();
        Image fr=fRcon.getScaledInstance(40,40,Image.SCALE_DEFAULT);
        findRIcon.setImage(fr);
        JButton findR= new JButton(findRIcon);
        findR.setBackground(jmbar_sheet.getBackground());
        findR.setToolTipText("Find & Replace");
        findR.setBorder(BorderFactory.createLineBorder(c,1));
        jmbar_sheet.add(BorderLayout.CENTER,findR);
        findR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {FuncView.addFindR();}
        });


        ImageIcon sort= new ImageIcon("res/iconosort.png");
        Image sortI=sort.getImage();
        Image s=sortI.getScaledInstance(40,40,Image.SCALE_DEFAULT);
        sort.setImage(s);
        JButton sortB= new JButton(sort);
        sortB.setToolTipText("Sort");
        sortB.setBackground(jmbar_sheet.getBackground());
        sortB.setBorder(BorderFactory.createLineBorder(c,1));
        jmbar_sheet.add(BorderLayout.CENTER,sortB);



        sortB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FuncView.addSort();
            }
        });


        ImageIcon MoIcon= new ImageIcon("res/iconomodify.png");
        Image moI=MoIcon.getImage();
        Image micon=moI.getScaledInstance(40,40,Image.SCALE_DEFAULT);
        MoIcon.setImage(micon);
        JButton modifyBlock = new JButton(MoIcon);
        modifyBlock.setToolTipText("Modify block");
        modifyBlock.setBackground(jmbar_sheet.getBackground());
        modifyBlock.setBorder(BorderFactory.createLineBorder(c,1));
        jmbar_sheet.add(BorderLayout.CENTER,modifyBlock);
        modifyBlock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FuncView.modifyB();
                rewriteBlock();
            }
        });


        jmbar_sheet.add(Box.createHorizontalGlue());

        JPanel mp= (JPanel) MainMenu.getCurrentFrame().getContentPane().getComponent(0);
        mp.add(BorderLayout.CENTER,jmbar_sheet);
        mb.add(sheet,1);


        saveB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {MenuViews.save();}});

        saveAsB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {MenuViews.saveAs();}});
        exportB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {MenuViews.export();}});


        change_name.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  sheets.setTitleAt(sheets.getSelectedIndex(),MenuViews.changeName());}});

        rowsInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {MenuViews.rowsInsert();}});

        colsInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {MenuViews.colsInsert();}});

        rowsDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { MenuViews.rowsDelete();}});

        colsDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { MenuViews.colsDelete();}});

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuViews.deleteSheet();
                sheets.remove(sheets.getSelectedIndex());
            }
        });

    }

    public static void updateBlockMenu(JMenuBar blockBar){
        changedMenu=true;
        Color c = new Color(70, 130, 180);

       // JPanel blockmenu= new JPanel();
        if(!emptyBlock()){
            String blockType=PresentationController.blockType(currentSheetName());
            ImageIcon moveBlockIcon= new ImageIcon("res/iconomove.png");
            Image mvbi=moveBlockIcon.getImage();
            Image mv=mvbi.getScaledInstance(40,40,Image.SCALE_DEFAULT);
            moveBlockIcon.setImage(mv);
            JButton moveBlock= new JButton(moveBlockIcon);
            moveBlock.setToolTipText("Move block");
            moveBlock.setBackground(blockBar.getBackground());
            moveBlock.setBorder(BorderFactory.createLineBorder(c,1));
            blockBar.add(moveBlock,blockBar.getMenuCount()-1);

            moveBlock.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Integer[] move_b=FuncView.moveBlock();
                    if(move_b[0]!=-1) writeBlock(move_b[0],move_b[1],move_b[2],move_b[3]);
                }
            });


            switch (blockType){
                case "N":
                    ImageIcon floorIcon= new ImageIcon("res/iconofloor.png");
                    Image flIcon=floorIcon.getImage();
                    Image fl=flIcon.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                    floorIcon.setImage(fl);
                    JButton floor= new JButton(floorIcon);
                    floor.setToolTipText("Floor");
                    floor.setBackground(blockBar.getBackground());
                    floor.setBorder(BorderFactory.createLineBorder(c,1));
                    blockBar.add(floor,blockBar.getMenuCount()-1);


                    floor.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Integer[] floored=FuncView.addSingleOp("floor");
                            if(floored[0]!=-1) {
                                if (floored[4] == -1) writeBlock(floored[0], floored[1], floored[2], floored[3]);
                                else rewriteBlock();
                            }
                        }
                    });

                    ImageIcon trimIcon = new ImageIcon("res/iconotrim.png");
                    Image trIcon = trimIcon.getImage();
                    Image tr= trIcon.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                    trimIcon.setImage(tr);
                    JButton trim = new JButton(trimIcon);
                    trim.setToolTipText("Trim");
                    trim.setBackground(blockBar.getBackground());
                    trim.setBorder(BorderFactory.createLineBorder(c,1));
                    blockBar.add(trim,blockBar.getMenuCount()-1);


                    trim.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Integer[] trimm = FuncView.addSingleOp("trim");
                            if(trimm[0]!=-1) {
                                if (trimm[4] == -1) writeBlock(trimm[0], trimm[1], trimm[2], trimm[3]);
                                else rewriteBlock();
                            }
                        }
                    });

                    ImageIcon opAllIcon = new ImageIcon("res/iconoallfinal.png");
                    Image oaIcon = opAllIcon.getImage();
                    Image opa= oaIcon.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                    opAllIcon.setImage(opa);
                    JButton opAll = new JButton(opAllIcon);
                    opAll.setToolTipText("Op All");
                    opAll.setBackground(blockBar.getBackground());
                    opAll.setBorder(BorderFactory.createLineBorder(c,1));
                    blockBar.add(opAll,blockBar.getMenuCount()-1);


                    opAll.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Integer[] opA = FuncView.addSingleOp("All Op Block");
                            if(opA[0]!=-1) {
                                if (opA[4] == -1) writeBlock(opA[0], opA[1], opA[2], opA[3]);
                                else rewriteBlock();
                            }
                        }
                    });

                    ImageIcon graficIcon = new ImageIcon("res/iconografics.png");
                    Image grIcon = graficIcon.getImage();
                    Image gr= grIcon.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                    graficIcon.setImage(gr);
                    JButton graf = new JButton(graficIcon);
                    graf.setToolTipText("Grafic");
                    graf.setBackground(blockBar.getBackground());
                    graf.setBorder(BorderFactory.createLineBorder(c,1));
                    blockBar.add(graf,blockBar.getMenuCount()-1);


                    trim.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Integer[] grafFunc = FuncView.addSingleOp("graf");
                            if(grafFunc[0]!=-1) {
                                if (grafFunc[4] == -1) writeBlock(grafFunc[0], grafFunc[1], grafFunc[2], grafFunc[3]);
                                else rewriteBlock();
                            }
                        }
                    });

                    ImageIcon ceilIcon= new ImageIcon("res/iconoceil.png");
                    Image ceIcon=ceilIcon.getImage();
                    Image ceilb=ceIcon.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                    ceilIcon.setImage(ceilb);
                    JButton ceil= new JButton(ceilIcon);
                    ceil.setToolTipText("Ceil");
                    ceil.setBackground(blockBar.getBackground());
                    ceil.setBorder(BorderFactory.createLineBorder(c,1));
                    blockBar.add(ceil,blockBar.getMenuCount()-1);
                    ceil.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Integer[] c=FuncView.addSingleOp("ceil");
                            if(c[0]!=-1) {
                                if (c[4] == -1) writeBlock(c[0], c[1], c[2], c[3]);
                                else rewriteBlock();
                            }
                        }
                    });


                    ImageIcon convertIcon= new ImageIcon("res/iconoconvert.png");
                    Image cIcon=convertIcon.getImage();
                    Image conv=cIcon.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                    convertIcon.setImage(conv);
                    JButton convert= new JButton(convertIcon);
                    convert.setToolTipText("Convert");
                    convert.setBackground(blockBar.getBackground());
                    convert.setBorder(BorderFactory.createLineBorder(c,1));
                    blockBar.add(convert,blockBar.getMenuCount()-1);
                    convert.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Integer[] conv= FuncView.addSingleOpCrit("Convert");
                            if(conv[0]!=-1) {
                                if (conv[4] == -1) writeBlock(conv[0], conv[1], conv[2], conv[3]);
                                else rewriteBlock();
                            }
                        }
                    });
                    ImageIcon AOpIcon= new ImageIcon("res/iconoaritmetic.png");
                    Image aopIcon=AOpIcon.getImage();
                    Image ao=aopIcon.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                    AOpIcon.setImage(ao);
                    JButton AritOp= new JButton(AOpIcon);
                    AritOp.setToolTipText("Arithmetic operations");
                    AritOp.setBackground(blockBar.getBackground());
                    AritOp.setBorder(BorderFactory.createLineBorder(c,1));
                    blockBar.add(AritOp,blockBar.getMenuCount()-1);

                    AritOp.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Integer[] Aop=FuncView.addAOp("arit");
                            if (Aop[0] != -1)writeBlock(Aop[0]-1,Aop[1]-1,Aop[2]-1,Aop[3]-1);
                        }
                    });



                    ImageIcon statIcon= new ImageIcon("res/iconoest.png");
                    Image stIcon=statIcon.getImage();
                    Image st=stIcon.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                    statIcon.setImage(st);
                    JButton stat= new JButton(statIcon);
                    stat.setToolTipText("Statistic operations");
                    stat.setBackground(blockBar.getBackground());
                    stat.setBorder(BorderFactory.createLineBorder(c,1));
                    blockBar.add(stat,blockBar.getMenuCount()-1);

                    stat.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Integer[] Sop=FuncView.addSOp("stat");
                            if(Sop[0]!=-1) writeBlock(Sop[0],Sop[1],Sop[0],Sop[1]);
                        }
                    });




                    ImageIcon maxIcon= new ImageIcon("res/iconomax.png");
                    Image mIcon=maxIcon.getImage();
                    Image maxb=mIcon.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                    maxIcon.setImage(maxb);
                    JButton max= new JButton(maxIcon);
                    max.setToolTipText("Max/Min");
                    max.setBackground(blockBar.getBackground());
                    max.setBorder(BorderFactory.createLineBorder(c,1));
                    blockBar.add(max,blockBar.getMenuCount()-1);
                    max.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Integer[] maxx=FuncView.addSOp("maxmin");
                            if(maxx[0]!=-1) writeBlock(maxx[0], maxx[1], maxx[0], maxx[1]);
                        }
                    });

                    ImageIcon CountiIcon= new ImageIcon("res/iconocountif.png");
                    Image ciIcon=CountiIcon.getImage();
                    Image cif=ciIcon.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                    CountiIcon.setImage(cif);
                    JButton countIf= new JButton(CountiIcon);
                    countIf.setToolTipText("Count if...");
                    countIf.setBackground(blockBar.getBackground());
                    countIf.setBorder(BorderFactory.createLineBorder(c,1));
                    blockBar.add(countIf,blockBar.getMenuCount()-1);
                    countIf.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Integer[] coif=FuncView.addSOp("countif");
                            if(coif[0]!=-1) writeBlock(coif[0], coif[1], coif[0], coif[1]);
                        }
                    });


                    ImageIcon OpBIcon= new ImageIcon("res/iconoopblock.png");
                    Image obicon=OpBIcon.getImage();
                    Image opb=obicon.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                    OpBIcon.setImage(opb);
                    JButton opBlock = new JButton(OpBIcon);
                    opBlock.setToolTipText("Unitary operations");
                    opBlock.setBackground(blockBar.getBackground());
                    opBlock.setBorder(BorderFactory.createLineBorder(c,1));
                    blockBar.add(opBlock,blockBar.getMenuCount()-1);
                    opBlock.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            FuncView.unitOp();
                            rewriteBlock();
                        }
                    });

                    break;
                case "T":
                    ImageIcon lengthIcon= new ImageIcon("res/iconolength.png");
                    Image lIcon=lengthIcon.getImage();
                    Image len=lIcon.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                    lengthIcon.setImage(len);
                    JButton length= new JButton(lengthIcon);
                    length.setToolTipText("Length");
                    length.setBackground(blockBar.getBackground());
                    length.setBorder(BorderFactory.createLineBorder(c,1));
                    blockBar.add(length,blockBar.getMenuCount()-1);

                    length.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Integer[] L=FuncView.addSingleOpCrit("Length");
                            if(L[0]!=-1){
                                if(L[4]==-1)writeBlock(L[0],L[1],L[2],L[3]);
                                else rewriteBlock();
                            }
                        }
                    });


                    ImageIcon replaceIcon= new ImageIcon("res/iconoreplace.png");
                    Image repIcon=replaceIcon.getImage();
                    Image re=repIcon.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                    replaceIcon.setImage(re);
                    JButton replace= new JButton(replaceIcon);
                    replace.setToolTipText("Replace text");
                    replace.setBackground(blockBar.getBackground());
                    replace.setBorder(BorderFactory.createLineBorder(c,1));
                    blockBar.add(replace,blockBar.getMenuCount()-1);
                    replace.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            FuncView.addReplaceWC();
                            rewriteBlock();
                        }
                    });

                    ImageIcon concatIcon= new ImageIcon("res/iconoconcat.png");
                    Image concIcon=concatIcon.getImage();
                    Image conc=concIcon.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                    concatIcon.setImage(conc);
                    JButton concat= new JButton(concatIcon);
                    concat.setToolTipText("Concatenate");
                    concat.setBackground(blockBar.getBackground());
                    concat.setBorder(BorderFactory.createLineBorder(c,1));
                    blockBar.add(concat,blockBar.getMenuCount()-1);
                    concat.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Integer[] con=FuncView.addAOp("Concatenate");
                            if(con[0]!=-1) {
                                if (con[4] == -1) writeBlock(con[0]-1, con[1]-1, con[2]-1, con[3]-1);
                                else rewriteBlock();
                            }
                        }
                    });





                    break;
                case "D":
                    ImageIcon extractIcon= new ImageIcon("res/iconoextract.png");
                    Image extracIcon=extractIcon.getImage();
                    Image exic=extracIcon.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                    extractIcon.setImage(exic);
                    JButton extract= new JButton(extractIcon);
                    extract.setToolTipText("Extract");
                    extract.setBackground(blockBar.getBackground());
                    extract.setBorder(BorderFactory.createLineBorder(c,1));
                    blockBar.add(extract,blockBar.getMenuCount()-1);

                    extract.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Integer [] ex= FuncView.addSingleOpCrit("Extract");
                            if(ex[0]!=-1) writeBlock(ex[0],ex[1],ex[2],ex[3]);
                        }
                    });

                    ImageIcon dotwIcon= new ImageIcon("res/iconodotw.png");
                    Image dotIcon=dotwIcon.getImage();
                    Image dotwi=dotIcon.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                    dotwIcon.setImage(dotwi);
                    JButton dotw= new JButton(dotwIcon);
                    dotw.setToolTipText("Day of the Week");
                    dotw.setBackground(blockBar.getBackground());
                    dotw.setBorder(BorderFactory.createLineBorder(c,1));
                    blockBar.add(dotw,blockBar.getMenuCount()-1);
                    dotw.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Integer [] dtw=FuncView.addSingleOp("Day of the week");
                            if(dtw[0]!=-1) writeBlock(dtw[0],dtw[1],dtw[2],dtw[3]);
                        }
                    });
                    break;
                default:
                    break;
            }


            blockBar.repaint();
            blockBar.revalidate();

        }
    }

    public static JScrollPane addSheet(String title, int numfil,int numcol){
        PresentationController.newSheet(title,numfil,numcol);
        String[] sheets_names = PresentationController.getSheets();

        //TABLE
        JTable table = new JTable(numfil,numcol );
        DefaultTableModel model;

        ArrayList<String> headers = new ArrayList<>(numfil);
        for (int j = 1; j <= numfil; j++) {
            headers.add("" + j);
        }
        ListModel l = new AbstractListModel() {
            @Override
            public int getSize() {
                return headers.size();
            }

            @Override
            public Object getElementAt(int index) {
                return headers.get(index);
            }
        };
        JList rowHeader = new JList(l);

        rowHeader.setFixedCellWidth(50);
        rowHeader.setFixedCellHeight(table.getRowHeight());
        Color c = new Color(70, 130, 180);
        rowHeader.setBackground(c);
        rowHeader.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        rowHeader.setForeground(Color.WHITE);

        table.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.WHITE));
        table.getTableHeader().setBackground(c);
        table.getTableHeader().setForeground(Color.WHITE);
        rowHeader.setCellRenderer(getRenderer());

        model = (DefaultTableModel) (table.getModel());


        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPane = new JScrollPane(
                table,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS
        );
        // pongo el botón en la ventana

        scrollPane.setRowHeaderView(rowHeader);


        //new sheet button
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int rowChanged = e.getFirstRow();
                    int colChanged = e.getColumn();


                    if (rowChanged >= 0 && rowChanged < getCurrentTable().getRowCount() && colChanged >= 0 && colChanged < getCurrentTable().getColumnCount()) {
//                       String colName = getCurrentTable().getModel().getColumnName(colChanged);
//
//                        int colValue= alphabetToNum(colName);
                        int colIndex = getCurrentTable().convertColumnIndexToView(colChanged);

                        TableModel tm = getCurrentTable().getModel();
                        String newValue = (String) table.getValueAt(rowChanged, colIndex);
                        PresentationController.editedCell(rowChanged, colIndex, newValue, currentSheetName());
                        boolean b=PresentationController.hasRefs(rowChanged,colIndex,currentSheetName());
                        if(b){
                            System.out.println("  LLEGA HASTA AQUÍ ;)  ");
                            Object[] Rmodify=PresentationController.getRefsIds(rowChanged,colIndex,currentSheetName());
                            for (int i = 0; i < Rmodify.length; i+=2) {
                                int rRow= (int) Rmodify[i];
                                int rCol=(int) Rmodify[i+1];
                                writeBlock(rRow,rCol,rRow,rCol);
                            }
                        }
                    }

                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            int row1,col1,row2,col2;
            @Override
            public void mousePressed(MouseEvent e) {
                table.clearSelection();
                int row=table.rowAtPoint(e.getPoint());
                int col=table.columnAtPoint(e.getPoint());
                if(row<table.getRowCount() && row>=0 && col>=0 && col<table.getColumnCount()){
                    row1=row;
                    col1=col;
                    bar.setText(PresentationController.cellInfo(row,col,currentSheetName()));
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {

                int row=table.rowAtPoint(e.getPoint());
                int col=table.columnAtPoint(e.getPoint());
                if(row<table.getRowCount() && row>=0 && col>=0 && col<table.getColumnCount() ){
                    if (row1 != row || col1 != col ) {
                        bar.setText(PresentationController.cellInfo(row,col,currentSheetName()));
                        col2=col;
                        row2=row;
                        PresentationController.createBlock(min(row1,row2),min(col1,col2),max(row1,row2),max(col1,col2),currentSheetName());
                        updateBlockMenu(jmbar_sheet);
                    }
                }
            }

        });

        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(false);
        table.setCellSelectionEnabled(true);

        return scrollPane;

    }

    private static ListCellRenderer<? super String> getRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                                                          Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel listCellRendererComponent = (JLabel) super
                        .getListCellRendererComponent(list, value, index, isSelected,
                                cellHasFocus);
                listCellRendererComponent.setBorder(BorderFactory.createMatteBorder(0,
                        0, 1, 0, Color.white));
                return listCellRendererComponent;
            }
        };
    }

    public static String currentSheetName(){
        int a= sheets.getSelectedIndex();
        return sheets.getTitleAt(a);
    }

    public static JTable getCurrentTable(){
        int a= sheets.getSelectedIndex();
        JScrollPane sp;
        if (a==0){ sp=(JScrollPane) sheets.getComponentAt(a);}
        else {sp= (JScrollPane) sheets.getComponentAt(a+1);}
        return (JTable) sp.getViewport().getComponent(0);
    }



    public static JScrollPane getCurrentScroll(){
        int a= sheets.getSelectedIndex();
        JScrollPane sp;
        if (a==0){ sp=(JScrollPane) sheets.getComponent(a);}
        else {sp= (JScrollPane) sheets.getComponent(a+1);}
        return sp;
    }
    public static void updateRowHeaders(){
        JTable current= getCurrentTable();
        DefaultTableModel tmodel= (DefaultTableModel) current.getModel();
        int numfil=tmodel.getRowCount();
        ArrayList<String> headers = new ArrayList<>(numfil);
        for (int j = 1; j <= numfil; j++) {
            headers.add("" + j);
        }
        ListModel l = new AbstractListModel() {
            @Override
            public int getSize() {
                return headers.size();
            }

            @Override
            public Object getElementAt(int index) {
                return headers.get(index);
            }
        };
        JList rowHeader = new JList(l);

        rowHeader.setFixedCellWidth(50);
        rowHeader.setFixedCellHeight(current.getRowHeight());
        Color c = new Color(70, 130, 180);
        rowHeader.setBackground(c);
        rowHeader.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        rowHeader.setForeground(Color.WHITE);

        current.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.WHITE));
        current.getTableHeader().setBackground(c);
        current.getTableHeader().setForeground(Color.WHITE);
        rowHeader.setCellRenderer(getRenderer());
        getCurrentScroll().setRowHeaderView(rowHeader);


    }

    public static void updateColHeaders(){
        int colcount=getCurrentTable().getColumnCount();
        String[] newh= new String[colcount];
        for (int j=0;j<colcount;j++){
            getCurrentTable().getColumnModel().getColumn(j).setHeaderValue(numToAlphabet(j));
            newh[j]=numToAlphabet(j);
        }
        DefaultTableModel tm= (DefaultTableModel) getCurrentTable().getModel();
        tm.setColumnIdentifiers(newh);
    }


    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public static boolean existsSheetName(String name){
        for (int i=0;i<sheets.getTabCount();i++){
            if (sheets.getTitleAt(i).equals(name)) return true;
        }
        return false;
    }

    public static String numToAlphabet(int i) {
        if( i<0 ) {
            return "-"+numToAlphabet(-i-1);
        }

        int quot = i/26;
        int rem = i%26;
        char letter = (char)((int)'A' + rem);
        if( quot == 0 ) {
            return ""+letter;
        } else {
            return numToAlphabet(quot-1) + letter;
        }
    }

    public static void rewriteModel(int col){
        writeChange=true;
        TableModel tm = getCurrentTable().getModel();
        for (int i = 0; i < tm.getRowCount(); i++) {
            for (int j = col; j <tm.getColumnCount() ; j++) {
                tm.setValueAt(PresentationController.getCellInfo(i,j,currentSheetName()),i,j);
            }
        }
        writeChange=false;

    }

    public static int alphabetToNum(String i) {
        int result = -1;
        for (int j = 0; j < i.length(); j++) {
            result += Math.pow(26,j)* (i.charAt(j) - 'A' + 1);

        }
        return result;
    }

    public static boolean emptyBlock() {
       return (getCurrentTable().getSelectedColumnCount() <= 0) && (getCurrentTable().getSelectedRowCount() <=0);
    }

    public static void rewriteBlock(){
        writeChange=true;
        TableModel tm = getCurrentTable().getModel();
        String cs = currentSheetName();
        int firstrow = PresentationController.blockFirstRow(cs);
        int firstcol = PresentationController.blockFirstCol(cs);
        for (int i = 0; i < PresentationController.blockRows(cs); i++) {
            for (int j = 0; j < PresentationController.blockCols(cs); j++) {
                  tm.setValueAt(PresentationController.getCellInfo(firstrow+i,firstcol+j,cs),firstrow+i,firstcol+j);
            }
        }
        writeChange=false;
    }

    public static void writeBlock(int ulr,int ulc,int drr,int drc){
        writeChange=true;
        TableModel tm = getCurrentTable().getModel();
        String cs = currentSheetName();
        for (int i = ulr; i <=drr; i++) {
            for (int j = ulc; j <=drc; j++) {
                String s=PresentationController.getCellInfo(i,j,cs);
                tm.setValueAt(s,i,j);
            }
        }
        writeChange=false;
    }




}
