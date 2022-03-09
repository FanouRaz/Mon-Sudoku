package sudoku;

import javax.swing.JDialog;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JButton;
import java.awt.event.*;
import java.awt.*;

public class Configuration  extends JDialog {

  private JLabel       m_CouleurGrille   = new JLabel("Couleur de grille");
  private JComboBox    m_ComboGrille     = new JComboBox();
  private JLabel       m_CouleurDiagonale= new JLabel("Diagonales : couleur");
  private JComboBox    m_ComboDiagonale  = new JComboBox();
  private JLabel       m_AvecDiagonale   = new JLabel("Avec :");
  private JCheckBox    m_CheckBoxDiag    = new JCheckBox();
  private JLabel       m_Symetrie        = new JLabel("Symetrie centrale :");
  private JCheckBox    m_CheckBoxSym     = new JCheckBox();
  private JLabel       m_LabelGrille     = new JLabel("Grille initiale");
  private JLabel       m_LabelSaisie     = new JLabel("Saisie");
  private JLabel       m_LabelValeur     = new JLabel("Prochaine valeur");
  private JLabel       m_LabelErreur     = new JLabel("Erreurs");
  private JLabel       m_LabelGrilleEx   = new JLabel("1");
  private JLabel       m_LabelSaisieEx   = new JLabel("1");
  private JLabel       m_LabelValeurEx   = new JLabel("1");
  private JLabel       m_LabelErreurEx   = new JLabel("1");
  private JPanel       m_PanelGrilleEx   = new JPanel();
  private JPanel       m_PanelSaisieEx   = new JPanel();
  private JPanel       m_PanelValeurEx   = new JPanel();
  private JPanel       m_PanelErreurEx   = new JPanel();
  private JButton      m_ButtonGrille    = new JButton("Modifier");
  private JButton      m_ButtonSaisie    = new JButton("Modifier");
  private JButton      m_ButtonValeur    = new JButton("Modifier");
  private JButton      m_ButtonErreur    = new JButton("Modifier");
  private JButton      m_Valider         = new JButton("Valider");
  private JButton      m_Annuler         = new JButton("Annuler");
  private ChoixPolice  m_Dialog;
  private Police       m_PoliceGrilleInitiale;
  private Police       m_PoliceSaisie;
  private Police       m_PoliceValeur;
  private Police       m_PoliceErreur;
  private JLabel       m_Lignes          = new JLabel("Lignes :");
  private JComboBox    m_ComboLine       = new JComboBox();
  private JLabel       m_Colonnes        = new JLabel("Colonnes :");
  private JComboBox    m_ComboColumn     = new JComboBox();

  public Configuration(JFrame owner, String p_Titre, boolean p_Modal){
    super(owner,p_Titre,p_Modal);
    m_Valider.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ValiderActionPerformed(e);
      }
    });

    m_Annuler.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setVisible(false);
      }
    });

    JFrame l_Frame = new JFrame();
    l_Frame.setSize(450,550);
    m_Dialog = new ChoixPolice(l_Frame,"Police",true,this);

    // couleur de la grille
    UtilColor.getInstance().remplirCombo(m_ComboGrille);
    m_ComboGrille.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        miseAJourCouleurGrille();
      }
    });

    // couleur de la grille
    UtilColor.getInstance().remplirCombo(m_ComboDiagonale);

    m_ButtonGrille.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        showChoixDialog(m_PoliceGrilleInitiale,"la grille initiale");
      }
    });

    m_ButtonSaisie.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        showChoixDialog(m_PoliceSaisie,"la saisie");
      }
    });

    m_ButtonValeur.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        showChoixDialog(m_PoliceValeur,"la prochaine valeur");
      }
    });

    m_ButtonErreur.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        showChoixDialog(m_PoliceErreur,"les erreurs");
      }
    });

    Container l_pane = this.getContentPane();

    l_pane.setLayout(new GridBagLayout());

    l_pane.add(m_LabelGrille, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    m_PanelGrilleEx.add(m_LabelGrilleEx);
    m_PanelGrilleEx.setMaximumSize(new Dimension(40,40));
    m_PanelGrilleEx.setMinimumSize(new Dimension(40,40));
    m_PanelGrilleEx.setPreferredSize(new Dimension(40,40));
    l_pane.add(m_PanelGrilleEx, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 17, 0, 0), 0, 0));
    l_pane.add(m_ButtonGrille, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));

    l_pane.add(m_LabelSaisie, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    m_PanelSaisieEx.add(m_LabelSaisieEx);
    m_PanelSaisieEx.setMaximumSize(new Dimension(40,40));
    m_PanelSaisieEx.setMinimumSize(new Dimension(40,40));
    m_PanelSaisieEx.setPreferredSize(new Dimension(40,40));
    l_pane.add(m_PanelSaisieEx, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 17, 0, 0), 0, 0));
    l_pane.add(m_ButtonSaisie, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));

    l_pane.add(m_LabelValeur, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    m_PanelValeurEx.add(m_LabelValeurEx);
    m_PanelValeurEx.setMaximumSize(new Dimension(40,40));
    m_PanelValeurEx.setMinimumSize(new Dimension(40,40));
    m_PanelValeurEx.setPreferredSize(new Dimension(40,40));
    l_pane.add(m_PanelValeurEx, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 17, 0, 0), 0, 0));
    l_pane.add(m_ButtonValeur, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));

    l_pane.add(m_LabelErreur, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    m_PanelErreurEx.add(m_LabelErreurEx);
    m_PanelErreurEx.setMaximumSize(new Dimension(40,40));
    m_PanelErreurEx.setMinimumSize(new Dimension(40,40));
    m_PanelErreurEx.setPreferredSize(new Dimension(40,40));
    l_pane.add(m_PanelErreurEx, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 17, 0, 0), 0, 0));
    l_pane.add(m_ButtonErreur, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));

    l_pane.add(m_CouleurGrille, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    l_pane.add(m_ComboGrille, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 17, 0, 0), 0, 0));

    l_pane.add(m_CouleurDiagonale, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    l_pane.add(m_ComboDiagonale, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 17, 0, 0), 0, 0));
    JPanel panelDiagonal = new JPanel();
    panelDiagonal.add(m_AvecDiagonale);
    panelDiagonal.add(m_CheckBoxDiag);
    l_pane.add(panelDiagonal, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 17, 0, 0), 0, 0));

    
    l_pane.add(m_Symetrie, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    l_pane.add(m_CheckBoxSym, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 17, 0, 0), 0, 0));

    m_ComboLine.addItem("2");
    m_ComboLine.addItem("3");
    m_ComboLine.addItem("4");
    m_ComboLine.addItem("5");
    l_pane.add(m_Lignes, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    l_pane.add(m_ComboLine, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 17, 0, 0), 0, 0));

    m_ComboColumn.addItem("2");
    m_ComboColumn.addItem("3");
    m_ComboColumn.addItem("4");
    m_ComboColumn.addItem("5");
    l_pane.add(m_Colonnes, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    l_pane.add(m_ComboColumn, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 17, 0, 0), 0, 0));
    JPanel l_Panel = new JPanel();
    l_Panel.add(m_Valider);
    l_Panel.add(m_Annuler);
    l_pane.add(l_Panel, new GridBagConstraints(0, 9, 8, 1, 0.0, 0.0
        ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 17, 0, 0), 0, 0));
    pack();
  }

  public void ValiderActionPerformed(ActionEvent e)
  {
	setVisible(false);
	String couleurGrille = (String)m_ComboGrille.getSelectedItem();
    sudoku.getInstance().setCouleurGrille(UtilColor.getInstance().getColor(couleurGrille));
	String couleurDiagonale = (String)m_ComboDiagonale.getSelectedItem();
	sudoku.getInstance().setDiagonale(m_CheckBoxDiag.isSelected());
	sudoku.getInstance().setSymetrie(m_CheckBoxSym.isSelected());
    sudoku.getInstance().setCouleurDiagonale(UtilColor.getInstance().getColor(couleurDiagonale));
    sudoku.getInstance().setPoliceInitiale(m_PoliceGrilleInitiale);
    sudoku.getInstance().setPoliceSaisie(m_PoliceSaisie);
    sudoku.getInstance().setPoliceNextValue(m_PoliceValeur);
    sudoku.getInstance().setPoliceErreurs(m_PoliceErreur);
    String lineString = (String)m_ComboLine.getSelectedItem();
    System.out.println(lineString);
    int line = Integer.decode(lineString).intValue();
    String columnString = (String)m_ComboColumn.getSelectedItem();
    int column = Integer.decode(columnString).intValue();
    sudoku.getInstance().setLineAndColumnSize(line,column);    
    sudoku.getInstance().savePolice();
  }

  private void showChoixDialog(Police police, String type)
  {
    m_Dialog.init(police, type);
    Point location = getLocation();
    Dimension dim = getSize();
    Dimension dimConf = m_Dialog.getSize();
    int valX = location.x+(int)(dim.width*0.5)-(int)(dimConf.width*0.5);
    int valY = location.y+(int)(dim.height*0.5)-(int)(dimConf.height*0.5);
    m_Dialog.setLocation(valX,valY);
    m_Dialog.setVisible(true);
  }

  private void miseAJourCouleurGrille()
  {
    Color color = UtilColor.getInstance().getColor((String)m_ComboGrille.getSelectedItem());
    m_PanelGrilleEx.setBorder(new MatteBorder(1,1,1,1,color));
    m_PanelErreurEx.setBorder(new MatteBorder(1,1,1,1,color));
    m_PanelSaisieEx.setBorder(new MatteBorder(1,1,1,1,color));
    m_PanelValeurEx.setBorder(new MatteBorder(1,1,1,1,color));
  }

  public void init(Police policeGrilleInitiale, Police policeSaisie, Police policeValeur, Police policeErreurs, Color couleurGrille, Color couleurDiagonale, boolean hasDiagonale, boolean isSymetrie, int lines, int columns)
  {
    if(m_PoliceGrilleInitiale == null)
      m_PoliceGrilleInitiale = new Police(policeGrilleInitiale.getFont(),policeGrilleInitiale.getBackColor(),policeGrilleInitiale.getForeColor());
    else
    {
      m_PoliceGrilleInitiale.setFont(policeGrilleInitiale.getFont());
      m_PoliceGrilleInitiale.setBackColor(policeGrilleInitiale.getBackColor());
      m_PoliceGrilleInitiale.setForeColor(policeGrilleInitiale.getForeColor());
    }
    m_LabelGrilleEx.setFont(policeGrilleInitiale.getFont());
    m_PanelGrilleEx.setBackground(policeGrilleInitiale.getBackColor());
    m_LabelGrilleEx.setForeground(policeGrilleInitiale.getForeColor());

    if(m_PoliceSaisie == null)
      m_PoliceSaisie = new Police(policeSaisie.getFont(),policeSaisie.getBackColor(),policeSaisie.getForeColor());
    else
    {
      m_PoliceSaisie.setFont(policeSaisie.getFont());
      m_PoliceSaisie.setBackColor(policeSaisie.getBackColor());
      m_PoliceSaisie.setForeColor(policeSaisie.getForeColor());
    }
    m_LabelSaisieEx.setFont(policeSaisie.getFont());
    m_PanelSaisieEx.setBackground(policeSaisie.getBackColor());
    m_LabelSaisieEx.setForeground(policeSaisie.getForeColor());

    if(m_PoliceValeur == null)
      m_PoliceValeur = new Police(policeValeur.getFont(),policeValeur.getBackColor(),policeValeur.getForeColor());
    else
    {
      m_PoliceValeur.setFont(policeValeur.getFont());
      m_PoliceValeur.setBackColor(policeValeur.getBackColor());
      m_PoliceValeur.setForeColor(policeValeur.getForeColor());
    }
    m_LabelValeurEx.setFont(policeValeur.getFont());
    m_PanelValeurEx.setBackground(policeValeur.getBackColor());
    m_LabelValeurEx.setForeground(policeValeur.getForeColor());

    if(m_PoliceErreur == null)
      m_PoliceErreur = new Police(policeErreurs.getFont(),policeErreurs.getBackColor(),policeErreurs.getForeColor());
    else
    {
      m_PoliceErreur.setFont(policeErreurs.getFont());
      m_PoliceErreur.setBackColor(policeErreurs.getBackColor());
      m_PoliceErreur.setForeColor(policeErreurs.getForeColor());
    }
    m_LabelErreurEx.setFont(policeErreurs.getFont());
    m_PanelErreurEx.setBackground(policeErreurs.getBackColor());
    m_LabelErreurEx.setForeground(policeErreurs.getForeColor());

    m_ComboGrille.setSelectedItem(UtilColor.getInstance().getName(couleurGrille));
    
    m_ComboDiagonale.setSelectedItem(UtilColor.getInstance().getName(couleurDiagonale));
    
    m_CheckBoxDiag.setSelected(hasDiagonale);
    
    m_CheckBoxSym.setSelected(isSymetrie);
    
    m_ComboLine.setSelectedItem(String.valueOf(lines));
    
    m_ComboColumn.setSelectedItem(String.valueOf(columns));
  }

  public void setPoliceInitiale(Font font, Color backColor, Color foreColor)
  {
    m_PoliceGrilleInitiale.setFont(font);
    m_PoliceGrilleInitiale.setBackColor(backColor);
    m_PoliceGrilleInitiale.setForeColor(foreColor);
    m_LabelGrilleEx.setFont(font);
    m_PanelGrilleEx.setBackground(backColor);
    m_LabelGrilleEx.setForeground(foreColor);
  }

  public void setPoliceSaisie(Font font, Color backColor, Color foreColor)
  {
    m_PoliceSaisie.setFont(font);
    m_PoliceSaisie.setBackColor(backColor);
    m_PoliceSaisie.setForeColor(foreColor);
    m_LabelSaisieEx.setFont(font);
    m_PanelSaisieEx.setBackground(backColor);
    m_LabelSaisieEx.setForeground(foreColor);
  }

  public void setPoliceValeur(Font font, Color backColor, Color foreColor)
  {
    m_PoliceValeur.setFont(font);
    m_PoliceValeur.setBackColor(backColor);
    m_PoliceValeur.setForeColor(foreColor);
    m_LabelValeurEx.setFont(font);
    m_PanelValeurEx.setBackground(backColor);
    m_LabelValeurEx.setForeground(foreColor);
  }

  public void setPoliceErreur(Font font, Color backColor, Color foreColor)
  {
    m_PoliceErreur.setFont(font);
    m_PoliceErreur.setBackColor(backColor);
    m_PoliceErreur.setForeColor(foreColor);
    m_LabelErreurEx.setFont(font);
    m_PanelErreurEx.setBackground(backColor);
    m_LabelErreurEx.setForeground(foreColor);
  }
}
