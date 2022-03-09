package sudoku;

import javax.swing.JDialog;
import javax.swing.*;
import javax.swing.JButton;
import java.util.HashMap;
import java.awt.event.*;
import java.awt.*;

public class ChoixPolice extends JDialog {


  private JLabel        m_LabelPolice     = new JLabel("Police");
  private JLabel        m_LabelTaille     = new JLabel("Taille");
  private JLabel        m_LabelItalic     = new JLabel("Style");
  private JLabel        m_LabelBackColor  = new JLabel("Couleur de fond");
  private JLabel        m_LabelForeColor  = new JLabel("Couleur du texte");
  private JComboBox     m_ComboPolice     = new JComboBox();
  private JComboBox     m_ComboTaille     = new JComboBox();
  private JComboBox     m_ComboItalic     = new JComboBox();
  private JComboBox     m_ComboBackColor  = new JComboBox();
  private JComboBox     m_ComboForeColor  = new JComboBox();
  private JButton       m_Valider         = new JButton("Valider");
  private JButton       m_Annuler         = new JButton("Annuler");
  private JPanel        m_Panel1          = new JPanel();
  private JLabel        m_Texte           = new JLabel();
  private HashMap       m_Font            = new HashMap();
  private Font          m_FontChoice;
  private Color         m_BackColor;
  private Color         m_ForeColor;
  private Configuration m_configuration = null;
  private String        m_Type = "";

  public ChoixPolice(JFrame owner, String p_Titre, boolean p_Modal, Configuration configuration){
    super(owner,p_Titre,p_Modal);
    m_configuration = configuration;
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

    Container l_pane = this.getContentPane();

    l_pane.setLayout(new GridBagLayout());

    l_pane.add(m_LabelPolice, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    l_pane.add(m_ComboPolice, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 17, 0, 0), 0, 0));
    l_pane.add(m_LabelTaille, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    l_pane.add(m_ComboTaille, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 17, 0, 0), 0, 0));
    l_pane.add(m_LabelItalic, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    l_pane.add(m_ComboItalic, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 17, 0, 0), 0, 0));
    l_pane.add(m_LabelForeColor, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    l_pane.add(m_ComboForeColor, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 17, 0, 0), 0, 0));
    l_pane.add(m_LabelBackColor, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    l_pane.add(m_ComboBackColor, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0
        ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 17, 0, 0), 0, 0));
    // couleur du texte
    UtilColor.getInstance().remplirCombo(m_ComboForeColor);
    // couleur du fond
    UtilColor.getInstance().remplirCombo(m_ComboBackColor);
    m_ComboItalic.addItem("Normal");
    m_ComboItalic.addItem("Italic");
    m_ComboItalic.addItem("Gras");
    m_ComboItalic.addItem("Gras Italic");
    m_ComboItalic.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        miseAJourTexte();
      }
    });
    for(int i=0;i<20;i++)
      m_ComboTaille.addItem(new Integer(10+i));

    m_ComboTaille.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        miseAJourTexte();
      }
    });
    m_ComboForeColor.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        miseAJourCouleurTexte();
      }
    });
    m_ComboBackColor.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        miseAJourCouleurFond();
      }
    });
    m_Panel1.add(m_Texte);
    m_Panel1.setMaximumSize(new Dimension(40,40));
    m_Panel1.setMinimumSize(new Dimension(40,40));
    m_Panel1.setPreferredSize(new Dimension(40,40));
    l_pane.add(m_Panel1, new GridBagConstraints(0, 2, 8, 1, 0.0, 0.0
        ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 17, 0, 0), 0, 0));
    JPanel l_Panel = new JPanel();
    l_Panel.add(m_Valider);
    l_Panel.add(m_Annuler);
    l_pane.add(l_Panel, new GridBagConstraints(0, 3, 8, 1, 0.0, 0.0
        ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 17, 0, 0), 0, 0));
    java.awt.Font[] l_AllFonts = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
    for(int i=0;i<l_AllFonts.length;i++)
    {
      m_Font.put(l_AllFonts[i].getFontName(),l_AllFonts[i]);
      m_ComboPolice.addItem(l_AllFonts[i].getFontName());
    }
    m_ComboPolice.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        miseAJourTexte();
      }
    });

    miseAJourTexte();
    pack();
  }

  private void miseAJourTexte()
  {
    String l_String = m_ComboPolice.getSelectedItem().toString();
    int l_Type = Font.PLAIN;
    if(m_ComboItalic.getSelectedItem().equals("Italic"))
      l_Type = Font.PLAIN | Font.ITALIC;
    else if(m_ComboItalic.getSelectedItem().equals("Gras"))
      l_Type = Font.BOLD;
    else if(m_ComboItalic.getSelectedItem().equals("Gras Italic"))
      l_Type = Font.BOLD | Font.ITALIC;
    int l_Taille = ((Integer)m_ComboTaille.getSelectedItem()).intValue();
    m_Texte.setFont(new Font(l_String,l_Type,l_Taille));
    m_Texte.setText("1");
  }

  private void miseAJourCouleurTexte()
  {
    Color color = UtilColor.getInstance().getColor((String)m_ComboForeColor.getSelectedItem());
    m_Texte.setForeground(color);
  }

  private void miseAJourCouleurFond()
  {
    Color color = UtilColor.getInstance().getColor((String)m_ComboBackColor.getSelectedItem());
    m_Panel1.setBackground(color);
  }

  public void ValiderActionPerformed(ActionEvent e)
  {
    int l_Type = Font.PLAIN;
    if(m_ComboItalic.getSelectedItem().equals("Italic"))
      l_Type = Font.PLAIN | Font.ITALIC;
    else if(m_ComboItalic.getSelectedItem().equals("Gras"))
      l_Type = Font.BOLD;
    else if(m_ComboItalic.getSelectedItem().equals("Gras Italic"))
      l_Type = Font.BOLD | Font.ITALIC;
    int l_Taille = ((Integer)m_ComboTaille.getSelectedItem()).intValue();
    m_FontChoice = new Font(m_ComboPolice.getSelectedItem().toString(),l_Type,l_Taille);
    Color backColor = UtilColor.getInstance().getColor((String)m_ComboBackColor.getSelectedItem());
    Color foreColor = UtilColor.getInstance().getColor((String)m_ComboForeColor.getSelectedItem());
    if(m_Type.equals("la grille initiale"))
      m_configuration.setPoliceInitiale(m_FontChoice,backColor,foreColor);
    else if(m_Type.equals("la saisie"))
      m_configuration.setPoliceSaisie(m_FontChoice,backColor,foreColor);
    else if(m_Type.equals("la prochaine valeur"))
      m_configuration.setPoliceValeur(m_FontChoice,backColor,foreColor);
    else if(m_Type.equals("les erreurs"))
      m_configuration.setPoliceErreur(m_FontChoice,backColor,foreColor);

    setVisible(false);
  }

  /* Retourne le point de l'écran où sera placée la fenêtre de dialogue */
  public Point getCentreAboutBox(int p_x, int p_y, Dimension p_Dim)
  {
    int l_x = p_x;
    int l_y = p_y;
    Dimension l_Dim = p_Dim;
    Double l_DeltaX = new Double(l_Dim.getWidth()*0.5);
    Double l_DeltaY = new Double(l_Dim.getHeight()*0.5);
    l_x = l_x + l_DeltaX.intValue();
    l_y = l_y + l_DeltaY.intValue();
    l_Dim = getSize();
    l_DeltaX = new Double(l_Dim.getWidth()*0.5);
    l_DeltaY = new Double(l_Dim.getHeight()*0.5);
    l_x = l_x - l_DeltaX.intValue();
    l_y = l_y - l_DeltaY.intValue();
    l_Dim = null;
    l_DeltaX = null;
    l_DeltaY = null;
    return new Point(l_x,l_y);
  }

  public Font getFont()
  {
    return m_FontChoice;
  }

  public Color getBackColor()
  {
    return m_BackColor;
  }

  public Color getForeColor()
  {
    return m_ForeColor;
  }

  public void init(Police police, String type)
  {
    String titre = "Police pour "+type;
    setTitle(titre);
    m_Type = type;
    m_ComboBackColor.setSelectedItem(UtilColor.getInstance().getName(police.getBackColor()));
    m_ComboForeColor.setSelectedItem(UtilColor.getInstance().getName(police.getForeColor()));
    Font font = police.getFont();
    m_ComboPolice.setSelectedItem(font.getName());
    int taille = font.getSize();
    if(taille < 10)
      taille = 10;
    if(taille > 39)
      taille = 39;
    m_ComboTaille.setSelectedItem(new Integer(taille));
    if(font.getStyle() == (Font.PLAIN | Font.ITALIC))
      m_ComboItalic.setSelectedItem("Italic");
    else if(font.getStyle() == Font.BOLD)
      m_ComboItalic.setSelectedItem("Gras");
    else if(font.getStyle() == (Font.BOLD | Font.ITALIC))
      m_ComboItalic.setSelectedItem("Gras Italic");
    else
      m_ComboItalic.setSelectedItem("Normal");

    miseAJourCouleurFond();
    miseAJourCouleurTexte();
    miseAJourTexte();
  }

}
