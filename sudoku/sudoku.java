package sudoku;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.lang.Math;
import java.lang.Integer;
import java.util.Properties;
import javax.swing.table.*;
import javax.swing.border.*;

public class sudoku extends JFrame
{
  // Instance unique de ma classe
  private static sudoku s_Instance;
  private JFileChooser m_FileChooser = new JFileChooser();
  private JPanel m_PanelMenu = new JPanel();
  private JMenuBar m_MenuBar = new JMenuBar ();
  private JMenu m_MenuFile = new JMenu ();
  private JMenu m_MenuResolution = new JMenu ();
  private JMenuItem m_Recommencer = new JMenuItem();
  private JMenuItem m_Verifier = new JMenuItem();
  private JMenuItem m_ProchaineValeur = new JMenuItem();
  private JMenuItem m_Analyse = new JMenuItem();
  private JMenu m_MenuHelp = new JMenu ();
  private JMenuItem m_Regle = new JMenuItem ();
  private JMenuItem m_Environnement = new JMenuItem ();
  private JMenuItem m_APropos = new JMenuItem ();
  private JMenu m_MenuNouveau = new JMenu ();
  private JMenu m_Partie = new JMenu ();
  private JMenuItem m_TresFacile = new JMenuItem ();
  private JMenuItem m_Debutant = new JMenuItem ();
  private JMenuItem m_Moyen = new JMenuItem ();
  private JMenuItem m_Confirmes = new JMenuItem ();
  private JMenuItem m_TresDifficile = new JMenuItem ();
  private JMenuItem m_Saisie = new JMenuItem ();
  private JMenuItem m_Ouvrir = new JMenuItem();
  private JMenuItem m_Enregistrer = new JMenuItem();
  private JMenuItem m_EnregistrerSous = new JMenuItem();
  private JMenuItem m_Imprimer = new JMenuItem ();
  private JMenuItem m_ConfigurationMenu = new JMenuItem ();
  private JMenuItem m_Quitter = new JMenuItem ();
  private JPanel m_PanelTable = new JPanel();
  private JTable m_Table;
  private JPanel m_PanelAnalyse = new JPanel();
  private JPanel m_PanelButtons = new JPanel();
  private JPanel m_PanelModifierValider = new JPanel();
  private GridLayout m_GridLayoutModifierValider = new GridLayout(1,2);
  private JButton m_ButtonDebut = new JButton();
  private JButton m_ButtonFin = new JButton();
  private JButton m_ButtonSuivant = new JButton();
  private JButton m_ButtonPrecedent = new JButton();
  private JButton m_ButtonValider = new JButton();
  private JButton m_ButtonModifier = new JButton();
  private Component m_HorizontalStruct3;
  private Component m_HorizontalStruct4;
  private Component m_HorizontalStruct5;
  private MySudokuTableModel m_mySudokuTableModel;
  private GridBagLayout m_gridBagContentPane = new GridBagLayout();
  private BorderLayout m_BorderLayoutAnalyse = new BorderLayout();
  private FlowLayout m_FlowLayoutButtons = new FlowLayout();
  // Le tableau m_Tableau contient 9 objets de type Tableau. Chaque objet possède
  // 9 lignes, 9 colonnes, 9 carres (de 9 cases) et 1 bloc de 81 cases. Chaque 
  // objet de type Tableau gère un chiffre de 1 à 9!
  private ArrayList m_Tableau;
  // m_Resultats est une liste qui stocke les cases dans l'ordre de résolution.
  // C'est elle qui fournit (par exemple) la prochaine case à afficher quand
  // on clic dans le bouton m_ButtonSuivant lors de l'analyse de la solution.
  public static ArrayList m_Resultats;
  // Indique si la cellule est éditable, utilisé par MySudokuTableModel
  private boolean m_IsCellEditable = false;
  // Indique que l'on commence une étape d'analyse
  private boolean m_IsNewAnalyse = true;
  // Donne l'index courant dans m_Resultats
  private int m_IndexResultats;
  // Ce tableau contient, pour chaque case du tableau, la valeur courante
  // Le premier indice correspond à la ligne, le deuxième à la colonne, le troisième
  // est à 2 dimensions, la première donne la valeur courante, le deuxième le type 
  // d'affichage, s'il s'agit d'une valeur issue de la grille initiale, d'une erreur...
  private String[][][] m_Data;
  // Tableau de 9X9 JTextFieldData, il s'agit des composants affichés dans la JTable
  public JTextField[][] m_TextFieldData;
  // Indique si on est en mode de saisie d'une nouvelle grille
  private boolean m_ModeSaisieGrille = false;
  // Indique si la grille a été enregistrée
  private boolean m_IsSave = true;
  // Nom du fichier ou sera sauvegardé la grille
  private String m_AdresseFichier = null;
  // Nom du répertoire de sauvergarde
  private String m_Adresse = null;
  // Nom du fichier
  private String m_NomGrille = "";
  // Objet utilisé pour configurer la grille (couleur, police...)
  private Configuration m_Configuration;
  // Liste des différentes polices disponibles
  // - pour la grille initiale
  private Police m_PoliceGrilleInitiale;
  // - pour la saisie du jouer
  private Police m_PoliceSaisie;
  // - pour la prochaine valeur affichée
  private Police m_PoliceNextValue;
  // - pour les erreurs
  private Police m_PoliceErreurs;
  // Couleur de la grille
  private Color  m_CouleurGrille;
  // Couleur des diagonales
  private Color  m_CouleurDiagonale;
  // Niveau de la grille
  private int m_Niveau;
  // indique si on force une conditions sur les diagonales
  private boolean m_HasDiagonale;
  // Malgré que l'on soit en mode diagonale, on ne va toujours travailler dans ce mode
  // pour le cas d'une saisie par exemple, ou lorsqu'on a une partie en cours
  private boolean m_CurrentDiagonale;
  // indique si on est en mode diagonale ou non
  private boolean m_DiagonaleMode;
  // indique si l'on souhaite créer des grilles symétriques
  private boolean m_IsSymetrique;
  // indique le format de la grille (3X3) (4X3) ...
  private int m_SizeLine;
  private int m_SizeColumn;
  

  // Constructeur
  private sudoku()
  {
    try
    {
      init();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  /*
  La classe étant un singleton, cette méthode renvoie l'objet sudoku unique
   */
  public static synchronized sudoku getInstance()
  {
     if(s_Instance==null)
     {
         s_Instance=new sudoku();
     }
     return s_Instance;
  }

  /*
  Initialiser le composant
   */
  private void init() throws Exception
  {
    m_Niveau = -1;
    m_DiagonaleMode = false;
    m_IsSymetrique = false;
    printTitle();
    // Récupération des différentes polices dans le fichier sudoku.ini
    m_CouleurDiagonale = Color.lightGray;
    loadPolice();
    m_Tableau = null;
    m_Table = new JTable(){
      public TableCellRenderer getCellRenderer(int row, int column) {
    	  MySudokuTableCellRenderer l_TableCellRenderer = new MySudokuTableCellRenderer(row,column);
          return l_TableCellRenderer;
        }
    };
    m_Table.setRowHeight(40);
    m_Table.setShowGrid(true);
    m_Table.setGridColor(m_CouleurGrille);
    m_HorizontalStruct3 = Box.createHorizontalStrut(2);
    m_HorizontalStruct4 = Box.createHorizontalStrut(2);
    m_HorizontalStruct5 = Box.createHorizontalStrut(2);
    m_PanelAnalyse.setMinimumSize(new Dimension(390, 70));
    m_PanelAnalyse.setLayout(m_BorderLayoutAnalyse);
    m_PanelButtons.setMinimumSize(new Dimension(390, 35));
    m_PanelButtons.setLayout(m_FlowLayoutButtons);
    ImageIcon buttonDebutIcon     = getButtonIcon("images/debut.gif");
    ImageIcon buttonFinIcon       = getButtonIcon("images/fin.gif");
    ImageIcon buttonSuivantIcon   = getButtonIcon("images/suivant.gif");
    ImageIcon buttonPrecedentIcon = getButtonIcon("images/precedent.gif");
    m_ButtonDebut.setMinimumSize(new Dimension(70, 27));
    if(buttonDebutIcon == null)
    	m_ButtonDebut.setText("|<==");
    else
    	m_ButtonDebut.setIcon(buttonDebutIcon);
    m_ButtonDebut.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        if(! m_ButtonDebut.isEnabled())
          return;
        DebutMousePressed(e);
      }
    });
    m_ButtonFin.setMinimumSize(new Dimension(70, 27));
    if(buttonFinIcon == null)
        m_ButtonFin.setText("==>|");
    else
    	m_ButtonFin.setIcon(buttonFinIcon);
    m_ButtonFin.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        if(! m_ButtonFin.isEnabled())
          return;
        DernierMousePressed(e);
      }
    });
    m_ButtonSuivant.setMinimumSize(new Dimension(70, 27));
    if(buttonSuivantIcon == null)
    	m_ButtonSuivant.setText("==>");
    else
    	m_ButtonSuivant.setIcon(buttonSuivantIcon);
    m_ButtonSuivant.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        if(! m_ButtonSuivant.isEnabled())
          return;
        SuivantMousePressed(e);
      }
    });
    m_ButtonPrecedent.setMinimumSize(new Dimension(70, 27));
    if(buttonPrecedentIcon == null)
    	m_ButtonPrecedent.setText("<==");
    else
    	m_ButtonPrecedent.setIcon(buttonPrecedentIcon);
    m_ButtonPrecedent.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        if(! m_ButtonPrecedent.isEnabled())
          return;
        PrecedentMousePressed(e);
      }
    });
    m_ButtonValider.setMinimumSize(new Dimension(150, 35));
    m_ButtonValider.setText("Valider");
    m_ButtonValider.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(MouseEvent e)
      {
        if(! m_ButtonValider.isEnabled())
          return;
        ValiderSaisie();
      }
    });
    m_PanelModifierValider.setLayout(m_GridLayoutModifierValider);
    this.getContentPane().setLayout(m_gridBagContentPane);
    this.setResizable(false);
    m_PanelModifierValider.setMinimumSize(new Dimension(390, 35));
    m_ButtonModifier.setMinimumSize(new Dimension(150, 35));
    m_ButtonModifier.setText("Modifier");
    m_ButtonModifier.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        if(! m_ButtonModifier.isEnabled())
          return;
         ModifierSaisie();
      }
    });
    // création du menu
    m_MenuFile.setText("Fichier");
    m_MenuResolution.setText("Résolution");
    m_MenuHelp.setText("Aide");
    m_MenuBar.add(m_MenuFile);
    m_FileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

    m_FileChooser.setFileFilter(new Philtre());
    m_Ouvrir.setAccelerator(KeyStroke.getKeyStroke(79,java.awt.Event.CTRL_MASK));
    m_Ouvrir.setText("Ouvrir");
    m_Ouvrir.addActionListener(new java.awt.event.ActionListener()
    {
       public void actionPerformed(ActionEvent e)
       {
    	   OuvrirFichier();
       }
    });
    m_Enregistrer.setAccelerator(KeyStroke.getKeyStroke(83,java.awt.Event.CTRL_MASK));
    m_Enregistrer.setText("Enregistrer");
    m_Enregistrer.addActionListener(new java.awt.event.ActionListener()
    {
       public void actionPerformed(ActionEvent e)
       {
         EnregistrerGrille(false);
       }
    });
    m_EnregistrerSous.setText("Enregistrer sous");
    m_EnregistrerSous.addActionListener(new java.awt.event.ActionListener()
    {
       public void actionPerformed(ActionEvent e)
       {
         EnregistrerGrille(true);
       }
    });
    m_Imprimer.setAccelerator(KeyStroke.getKeyStroke(73,java.awt.Event.CTRL_MASK));
    m_Imprimer.setText("Imprimer");
    m_Imprimer.addActionListener(new java.awt.event.ActionListener()
    {
       public void actionPerformed(ActionEvent e)
       {
         Impression();
       }
    });
    JFrame l_Frame = new JFrame();
    l_Frame.setSize(450,550);
    m_Configuration = new Configuration(l_Frame,"Configuration",true);
    m_ConfigurationMenu.setAccelerator(KeyStroke.getKeyStroke(66,java.awt.Event.CTRL_MASK));
    m_ConfigurationMenu.setText("Configuration");
    m_ConfigurationMenu.addActionListener(new java.awt.event.ActionListener()
    {
       public void actionPerformed(ActionEvent e)
       {
         showConfiguration();
       }
    });
    m_Quitter.setAccelerator(KeyStroke.getKeyStroke(81,java.awt.Event.CTRL_MASK));
    m_Quitter.setText("Quitter");
    m_Quitter.addActionListener(new java.awt.event.ActionListener()
    {
       public void actionPerformed(ActionEvent e)
       {
          QuitterAction();
       }
    });
    m_MenuNouveau.setText("Nouvelle");
    m_Partie.setText("Partie");
    m_TresFacile.setText("Très facile");
    m_Debutant.setText("Facile");
    m_Moyen.setText("Moyenne");
    m_Confirmes.setText("Difficile");
    m_TresDifficile.setText("Très difficile");
    m_Saisie.setText("Saisie");
    m_Saisie.addActionListener(new java.awt.event.ActionListener()
    {
       public void actionPerformed(ActionEvent e)
       {
         VerifieIsSave();
         m_CurrentDiagonale = m_HasDiagonale;  
         m_Niveau = -1;
         m_IsSave = true;
         m_IsCellEditable = true;
         m_ModeSaisieGrille = true;
         EnableAnalyse(false);
         EnableResolution(false);
         EnableValider(true);
         EnableModifier(false);
         InitialisationTotale();
         m_Table.repaint();
       }
    });
    m_Partie.add(m_TresFacile);
    m_Partie.add(m_Debutant);
    m_Partie.add(m_Moyen);
    m_Partie.add(m_Confirmes);
    m_Partie.add(m_TresDifficile);
    m_TresFacile.addActionListener(new java.awt.event.ActionListener()
    {
       public void actionPerformed(ActionEvent e)
       {
         NouvellePartie(0);
       }
    });
    m_Debutant.addActionListener(new java.awt.event.ActionListener()
    {
       public void actionPerformed(ActionEvent e)
       {
         NouvellePartie(1);
       }
    });
    m_Moyen.addActionListener(new java.awt.event.ActionListener()
    {
       public void actionPerformed(ActionEvent e)
       {
          NouvellePartie(2);
       }
    });
    m_Confirmes.addActionListener(new java.awt.event.ActionListener()
    {
       public void actionPerformed(ActionEvent e)
       {
          NouvellePartie(3);
       }
    });
    m_TresDifficile.addActionListener(new java.awt.event.ActionListener()
    {
       public void actionPerformed(ActionEvent e)
       {
          NouvellePartie(4);
       }
    });
    m_MenuNouveau.add(m_Partie);
    m_MenuNouveau.add(m_Saisie);
    m_MenuFile.add(m_MenuNouveau);
    m_MenuFile.add(m_Ouvrir);
    m_MenuFile.add(m_Enregistrer);
    m_MenuFile.add(m_EnregistrerSous);
    m_MenuFile.addSeparator();
    m_MenuFile.add(m_Imprimer);
    m_MenuFile.addSeparator();
    m_MenuFile.add(m_ConfigurationMenu);
    m_MenuFile.addSeparator();
    m_MenuFile.add(m_Quitter);
    m_MenuBar.add(m_MenuResolution);
    m_MenuResolution.add(m_Recommencer);
    m_Recommencer.setAccelerator(KeyStroke.getKeyStroke(82,java.awt.Event.CTRL_MASK));
    m_Recommencer.setText("Recommencer");
    m_Recommencer.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        RecommencerSaisie();
      }
    });
    m_MenuResolution.add(m_Verifier);
    m_Verifier.setAccelerator(KeyStroke.getKeyStroke(70,java.awt.Event.CTRL_MASK));
    m_Verifier.setText("Vérifier Saisie");
    m_Verifier.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        VerifierSaisie(true);
      }
    });
    m_MenuResolution.add(m_ProchaineValeur);
    m_ProchaineValeur.setAccelerator(KeyStroke.getKeyStroke(80,java.awt.Event.CTRL_MASK));
    m_ProchaineValeur.setText("Prochaine Valeur");
    m_ProchaineValeur.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        ProchaineValeur();
      }
    });
    m_MenuResolution.addSeparator();
    m_MenuResolution.add(m_Analyse);
    m_Analyse.setAccelerator(KeyStroke.getKeyStroke(89,java.awt.Event.CTRL_MASK));
    m_Analyse.setText("Analyse solution");
    m_Analyse.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        StartAgain();
        m_IsNewAnalyse = true;
        m_IsCellEditable = false;
        for(int row=0;row<m_SizeLine*m_SizeColumn;row++)
        {
          for(int col=0;col<m_SizeLine*m_SizeColumn;col++)
          {
            if(! m_Data[row][col][1].equals("2"))
            {
              m_Data[row][col][0] = "";
              m_Data[row][col][1] = "0";
            }
          }
        }
        EnableAnalyse(true);
        EnableValider(false);
        EnableModifier(false);
        m_Table.repaint();
      }
    });
    m_Regle.setText("Règle du jeu");
    m_Regle.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        try
        {
          Runtime.getRuntime().exec("explorer Règle.htm");
        }
        catch(Exception exception)
        {
          System.out.println(exception);
        }
      }
    });
    m_MenuHelp.add(m_Regle);
    m_Environnement.setText("Manuel d'utilisation");
    m_Environnement.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        try
        {
          Runtime.getRuntime().exec("explorer Manuel.htm");
        }
        catch(Exception exception)
        {
          System.out.println(exception);
        }
      }
    });
    m_MenuHelp.add(m_Environnement);
    m_MenuHelp.addSeparator();
    m_APropos.setText("A propos de SU-DO-KU...");
    m_APropos.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        AfficheAPropos();
      }
    });
    m_MenuHelp.add(m_APropos);
    m_MenuBar.add(m_MenuHelp);
    m_MenuBar.setMinimumSize(new Dimension(170, 20));
    m_PanelMenu.setLayout(new FlowLayout());
    m_PanelMenu.add(m_MenuBar,FlowLayout.LEFT);
    this.getContentPane().add(m_PanelMenu,   new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.WEST, new Insets(2, 5, 2, 5), 0, 0));
    this.getContentPane().add(m_PanelTable,   new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
    m_PanelTable.add(m_Table,  BorderLayout.CENTER);
    this.getContentPane().add(m_PanelAnalyse,    new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    m_PanelAnalyse.add(m_PanelButtons, BorderLayout.NORTH);
    m_PanelButtons.add(m_ButtonDebut, null);
    m_PanelButtons.add(m_HorizontalStruct3, null);
    m_PanelButtons.add(m_ButtonPrecedent, null);
    m_PanelButtons.add(m_HorizontalStruct4, null);
    m_PanelButtons.add(m_ButtonSuivant, null);
    m_PanelButtons.add(m_HorizontalStruct5, null);
    m_PanelButtons.add(m_ButtonFin, null);
    m_PanelAnalyse.add(m_PanelModifierValider,  BorderLayout.CENTER);
    m_PanelModifierValider.add(m_ButtonValider, null);
    m_PanelModifierValider.add(m_ButtonModifier, null);
    EnableAnalyse(false);
    EnableValider(false);
    EnableModifier(false);
    EnableResolution(false);
    initNewDimensionOfTab();
  }
  
  // retourne l'icon du bouton ou null si elle l'image n'est pas trouvée
  private ImageIcon getButtonIcon(String adresse) {
      java.net.URL url = sudoku.class.getResource(adresse);
      if (url != null) {
          return new ImageIcon(url);
      } else {
          return null;
      }
  }
  
  private void initNewDimensionOfTab()
  {
	  if(m_Tableau != null)
	  {
		  for(int i=0;i<m_Tableau.size();i++)
		  {
			  Tableau tableau = (Tableau) m_Tableau.get(i);
			  tableau.clear();
			  tableau = null;
		  }
		  m_Tableau.clear();
		  m_Tableau = null;
	  }
	  if(m_Table != null)
		  m_PanelTable.remove(m_Table);
	  int length = Math.max((m_SizeLine*m_SizeColumn*40+30),360);
	  int height = m_SizeLine*m_SizeColumn*40+160;
	  m_PanelTable.setSize(m_SizeLine*m_SizeColumn*40+15,m_SizeLine*m_SizeColumn*40+15);
	  m_TextFieldData = new JTextField[m_SizeLine*m_SizeColumn][m_SizeLine*m_SizeColumn];
	  
	  for(int i=0;i<m_SizeLine*m_SizeColumn;i++)
	  {
		  for(int j=0;j<m_SizeLine*m_SizeColumn;j++)
		  {
			  m_TextFieldData[i][j] = new JTextField();
			  m_TextFieldData[i][j].setBorder(getBorder(i,j));
		  }
	  }
	  m_mySudokuTableModel = new MySudokuTableModel();
	  m_Table.setModel(m_mySudokuTableModel);
	  initColumnSize(m_Table,m_mySudokuTableModel);
	  CreateAllTab();
	  m_Table.setSize(m_SizeLine*m_SizeColumn*40+15,m_SizeLine*m_SizeColumn*40+15);
	  m_PanelTable.add(m_Table,  BorderLayout.CENTER);
	  this.setSize(length,height);
	  this.setVisible(true);
  }

  // Méthode main : accès au programme
  public static void main(String[] args)
  {
     sudoku l_MainFrame = sudoku.getInstance();
     l_MainFrame.validate();
     // Centrer la fenêtre
     Dimension l_ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
     Dimension l_FrameSize = l_MainFrame.getSize();
     if (l_FrameSize.height > l_ScreenSize.height)
     {
       l_FrameSize.height = l_ScreenSize.height;
     }
     if (l_FrameSize.width > l_ScreenSize.width)
     {
       l_FrameSize.width = l_ScreenSize.width;
     }
     l_MainFrame.setLocation((l_ScreenSize.width - l_FrameSize.width) / 2, 0);
     l_MainFrame.setVisible(true);
  }

  //**************************************************************************
  //
  // Gestion de l'affichage de la JTable
  //
  //**************************************************************************
  // Cette classe indique à la JTable quel composant afficher dans chaque case
  class MySudokuTableCellRenderer extends DefaultTableCellRenderer
  {
	   public MySudokuTableCellRenderer(int row,int column) {
	     super();
	   }
	
	   public Component getTableCellRendererComponent(JTable table,
	                                                  Object value,
	                                                  boolean isSelected,
	                                                  boolean hasFocus,
	                                                  int row,
	                                                  int column)
	   {
	     String l_string = (String) value;
	     JTextField l_Text = m_TextFieldData[row][column];
	     String type = m_Data[row][column][1];
	     if(type.equals("1"))
	     {
	       l_Text.setForeground(m_PoliceNextValue.getForeColor());
	       l_Text.setBackground(m_PoliceNextValue.getBackColor());
	       l_Text.setFont(m_PoliceNextValue.getFont());
	     }
	     else if(type.equals("2"))
	     {
	       l_Text.setForeground(m_PoliceGrilleInitiale.getForeColor());
	       l_Text.setBackground(m_PoliceGrilleInitiale.getBackColor());
	       l_Text.setFont(m_PoliceGrilleInitiale.getFont());
	     }
	     else if(type.equals("3"))
	     {
	       l_Text.setForeground(m_PoliceErreurs.getForeColor());
	       l_Text.setBackground(m_PoliceErreurs.getBackColor());
	       l_Text.setFont(m_PoliceErreurs.getFont());
	     }
	     else
	     {
	       l_Text.setForeground(m_PoliceSaisie.getForeColor());
	       l_Text.setBackground(m_PoliceSaisie.getBackColor());
	       l_Text.setFont(m_PoliceSaisie.getFont());
	     }
	     if(m_CurrentDiagonale && (row == column || row == ((m_SizeLine*m_SizeColumn)-1-column)))
	       l_Text.setBackground(m_CouleurDiagonale);
	     l_Text.setText(l_string);
	     l_Text.setHorizontalAlignment((int)JTextField.CENTER_ALIGNMENT);
	     if(hasFocus)
	       l_Text.setBackground(Color.pink);
	     return l_Text;
	   }
   }

  // Cette classe est le modèle pour la JTable, elle dit quoi afficher
  class MySudokuTableModel extends AbstractTableModel
  {
    /* Retourne le nombre de colonnes de la table */
    public int getColumnCount() {
        return m_SizeLine*m_SizeColumn;
    }

    /* Retourne le nombre de lignes de la table */
    public int getRowCount() {
      return m_SizeLine*m_SizeColumn;
    }

    /* Retourne le titre de la colonne p_col */
    public String getColumnName(int p_col) {
        String valeur = new String("");
        return valeur;
    }

    /* Retourne l'objet située dans la cellule de ligne p_row et de colonne p_col  */
    public Object getValueAt(int p_row, int p_col) {
      return m_Data[p_row][p_col][0];
    }

    public Class getColumnClass(int c) {
      String l_String = new String("");
      return l_String.getClass();
    }

    public boolean isCellEditable(int row, int col) {
      if(!m_ModeSaisieGrille && m_Data[row][col][1].equals("2"))
        return false;
      return m_IsCellEditable;
    }

    public void setValueAt(Object value, int row, int col) {
      String val = value.toString();
      if(! val.equals(""))
      {
        try
        {
          int intValue = Integer.parseInt(val.trim());
          if(intValue > 0 && intValue <10)
          {
            m_Data[row][col][0] = val;
            if(m_ModeSaisieGrille)
              m_Data[row][col][1] = "2";
            else
            {
              m_Data[row][col][1] = "0";
              EnableModifier(false);
            }
          }
          else
          {
            m_Data[row][col][0] = "";
            m_Data[row][col][1] = "0";
          }
        }
        catch(Exception e){}
      }
      else
      {
        m_Data[row][col][0] = "";
        m_Data[row][col][1] = "0";
      }
      m_IsSave = false;
    }
  }

  // indique la taille des colonnes
   private void initColumnSize(JTable tab, MySudokuTableModel model)
   {
     TableColumn column = null;
     int cellWidth;
     int l_Somme = 0;
     for(int i = 0;i < tab.getColumnCount();i++)
     {
       column = tab.getColumnModel().getColumn(i);
       cellWidth = 40;
       column.setMinWidth(cellWidth);
       column.setMaxWidth(cellWidth);
       l_Somme += cellWidth;
     }
     tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
   }

   // Méthode qui crée 9 Tableaux. 
   private void CreateAllTab()
   {
     m_Resultats = new ArrayList();
     m_Tableau = new ArrayList();
     m_Data = new String[m_SizeLine*m_SizeColumn][m_SizeLine*m_SizeColumn][2];
     // création d'un tableau par chiffre
     for(int i=0;i<m_SizeLine*m_SizeColumn;i++)
     {
       CreateTab(i+1);
     }
     InitialisationTotale();
   }

   // Méthode qui crée un Tableau composé de : 9 lignes, 9 colonnes, 9 carrés et un bloc de 81 cases
  private void CreateTab(int value)
  {
    Tableau tableau = new Tableau(m_SizeLine,m_SizeColumn);
    // création des lignes
    for(int i=0;i<m_SizeLine*m_SizeColumn;i++)
    {
      Rangee ligne = new Rangee("ligne",m_SizeLine);
      tableau.addLigne(ligne);
    }

    // création des colonnes
    for(int i=0;i<m_SizeLine*m_SizeColumn;i++)
    {
      Rangee colonne = new Rangee("colonne",m_SizeColumn);
      tableau.addColonne(colonne);
    }

    // création des carres
    /* Les carres sont numérotés comme suit (pour un tableau [3X3])
     1  2  3
     4  5  6
     7  8  9
    */
    for(int i=0;i<m_SizeLine*m_SizeColumn;i++)
    {
      Carre carre = new Carre();
      tableau.addCarre(carre);
    }

	Diagonale diagonale1 = new Diagonale();
	tableau.addDiagonale(diagonale1);
	Diagonale diagonale2 = new Diagonale();
	tableau.addDiagonale(diagonale2);
    // nombre de cases créées
    int nbCases = 0;
    // boucle sur les lignes
    for(int i=0;i<m_SizeLine*m_SizeColumn;i++)
    {
      Rangee ligne = tableau.getLigne(i);
      int k = (int)Math.floor(i/m_SizeLine);
      int p = k*m_SizeLine;
      // boucles sur les colonnes
      for(int j=0;j<m_SizeLine*m_SizeColumn;j++)
      {
        int n = (int)Math.floor(j/m_SizeColumn);
        int l = n + p;
        Diagonale diagonale1Locale = null;
        Diagonale diagonale2Locale = null;
        if(i == j)
        	diagonale1Locale = diagonale1;
        if(j == ((m_SizeLine*m_SizeColumn)-1-i))
        	diagonale2Locale = diagonale2;
        Rangee colonne = tableau.getColonne(j);
        MiniRangee miniColonne = colonne.getMiniRangee(k);
        MiniRangee miniLigne = ligne.getMiniRangee(n);
        Carre carre = tableau.getCarre(l);
        miniColonne.setCarre(carre);
        miniLigne.setCarre(carre);
        carre.addMiniColonne(miniColonne);
        carre.addMiniLigne(miniLigne);
        Case carreau = new Case(carre,ligne,miniLigne,colonne,miniColonne,diagonale1Locale,diagonale2Locale,value,i+1,j+1,m_SizeLine,m_SizeColumn);
        tableau.addCase(carreau);
        // on ajoute les liens entre les différentes cases des différents tableaux
        for(int m=0;m<value-1;m++)
        {
          Tableau currentTableau = (Tableau)m_Tableau.get(m);
          Case currentCase = (Case)currentTableau.getCase(nbCases);
          currentCase.addCases(carreau);
          carreau.addCases(currentCase);
        }
        nbCases++;
      }
    }
    m_Tableau.add(tableau);
  }

  //**************************************************************************
  //
  // Gestion des disponibilités des différents boutons
  //
  //**************************************************************************
  // Permet ou non l'accès aux boutons de l'analyse
  private void EnableAnalyse(boolean analyse)
  {
	m_ButtonDebut.setEnabled(false);
    m_ButtonPrecedent.setEnabled(false);
    if(analyse)
    {
      m_ButtonFin.setEnabled(true);
      m_ButtonSuivant.setEnabled(true);
    }
    else
    {
      m_ButtonFin.setEnabled(false);
      m_ButtonSuivant.setEnabled(false);
    }
  }

  // Permet ou non l'accès au bouton valider
  private void EnableValider(boolean enable)
  {
    m_ButtonValider.setEnabled(enable);
  }

  // Permet ou non l'accès au bouton modifier
  private void EnableModifier(boolean enable)
  {
    m_ButtonModifier.setEnabled(enable);
  }

  // Permet ou non l'accès au menu résolution
  private void EnableResolution(boolean enable)
  {
    m_Recommencer.setEnabled(enable);
    m_Verifier.setEnabled(enable);
    m_ProchaineValeur.setEnabled(enable);
    m_Analyse.setEnabled(enable);
  }

  //**************************************************************************
  //
  // Gestion des différents boutons
  //
  //**************************************************************************
  // Bouton |<- retourne à la grille initiale
  private void DebutMousePressed(MouseEvent e)
  {
    if(!m_ButtonDebut.isEnabled())
      return;
    for(int i=0;i<m_Resultats.size();i++)
    {
      Case carreau = (Case)m_Resultats.get(i);
      int row = carreau.getRow();
      int column = carreau.getColumn();
      m_Data[row][column][0] = "";
      m_Data[row][column][1] = "0";
    }
    m_IndexResultats = 0;
    m_ButtonPrecedent.setEnabled(false);
    m_ButtonSuivant.setEnabled(true);
    m_Table.repaint();
  }

  // Bouton <- efface la dernière valeur affichée
  private void PrecedentMousePressed(MouseEvent e)
  {
    if(!m_ButtonPrecedent.isEnabled())
      return;
    if(m_Resultats.size() == 0)
      return;
    m_IndexResultats--;
    Case carreau = (Case)m_Resultats.get(m_IndexResultats);
    int row = carreau.getRow();
    int column = carreau.getColumn();
    m_Data[row][column][0] = "";
    m_Data[row][column][1] = "0";
    if(m_IndexResultats>0)
    {
      carreau = (Case)m_Resultats.get(m_IndexResultats-1);
      row = carreau.getRow();
      column = carreau.getColumn();
      m_Data[row][column][0] = String.valueOf(carreau.getValue());
      m_Data[row][column][1] = "1";
    }
    else
      m_ButtonPrecedent.setEnabled(false);
    m_ButtonSuivant.setEnabled(true);
    m_Table.repaint();
  }

  // Bouton -> affiche la prochaine valeur trouvée par l'algorithme de résolution
  private void SuivantMousePressed(MouseEvent e)
  {
    if(m_IsNewAnalyse)
    {
      m_IsNewAnalyse = false;
      m_IndexResultats = 0;
      InitData();
      m_Resultats.clear();
      Start(false,-1);
    }
    if(m_Resultats.size() == 0)
      return;
    if(m_IndexResultats > 0)
    {
      Case carreau = (Case)m_Resultats.get(m_IndexResultats-1);
      int row = carreau.getRow();
      int column = carreau.getColumn();
      m_Data[row][column][1] = "0";
    }
    Case carreau = (Case)m_Resultats.get(m_IndexResultats);
    int row = carreau.getRow();
    int column = carreau.getColumn();
    m_Data[row][column][0] = String.valueOf(carreau.getValue());
    m_Data[row][column][1] = "1";
    m_IndexResultats++;
    if(m_IndexResultats == m_Resultats.size())
      m_ButtonSuivant.setEnabled(false);
    m_ButtonPrecedent.setEnabled(true);
    m_ButtonDebut.setEnabled(true);
    m_Table.repaint();
  }

  // Bouton ->| affiche la grille finale
  private void DernierMousePressed(MouseEvent e)
  {
    if(!m_ButtonFin.isEnabled())
      return;
    if(m_IsNewAnalyse)
    {
      m_IsNewAnalyse = false;
      m_IndexResultats = 0;
      InitData();
      m_Resultats.clear();
      Start(false,-1);
    }
    if(m_Resultats.size() == 0)
      return;
    for(int i=0;i<m_Resultats.size();i++)
    {
      Case carreau = (Case)m_Resultats.get(i);
      int row = carreau.getRow();
      int column = carreau.getColumn();
      m_Data[row][column][0] = String.valueOf(carreau.getValue());;
      m_Data[row][column][1] = "0";
    }
    m_IndexResultats = m_Resultats.size();
    m_ButtonDebut.setEnabled(true);
    m_ButtonPrecedent.setEnabled(true);
    m_ButtonSuivant.setEnabled(false);
    m_Table.repaint();
  }

  // Valide la grille initiale et permet de commencer la résolution
  private void ValiderSaisie()
  {
    StartAgain();
    InitData();
    boolean isCorrect = Start(false,-1);
    if(!isCorrect)
    {
      StartAgain();
      return;
    }
    ArrayList listeResult = new ArrayList();
    if(! IsFinish(listeResult))
    {
      JOptionPane.showMessageDialog(this, "Cette grille n'a pas de solution", "Erreur", JOptionPane.OK_OPTION);
      return;
    }
    m_Niveau = getVraiNiveau();
    printTitle();
    for(int i=0;i<m_SizeLine*m_SizeColumn;i++)
    {
      Tableau tab = (Tableau)m_Tableau.get(i);
      // boucle sur les cases
      for(int j=0;j<m_SizeLine*m_SizeColumn*m_SizeLine*m_SizeColumn;j++)
      {
        Case carreau = (Case) tab.getCase(j);
        carreau.switchToFinalValue();
      }
    }
    EnableValider(false);
    EnableModifier(true);
    EnableResolution(true);
    m_ModeSaisieGrille = false;
  }

  // Donne la possibilité de modifier la grille initiale avant le début de la partie
  private void ModifierSaisie()
  {
    m_ModeSaisieGrille = true;
    EnableValider(true);
    EnableModifier(false);
    EnableResolution(false);
    for(int i=0;i<m_SizeLine*m_SizeColumn;i++)
    {
      Tableau tab = (Tableau)m_Tableau.get(i);
      // boucle sur les cases
      for(int j=0;j<m_SizeLine*m_SizeColumn*m_SizeLine*m_SizeColumn;j++)
      {
        Case carreau = (Case) tab.getCase(j);
        carreau.init();
      }
    }
    for(int i=0;i<m_SizeLine*m_SizeColumn;i++)
    {
      for(int j=0;j<m_SizeLine*m_SizeColumn;j++)
      {
        String type = m_Data[i][j][1];
        if(type.equals("0"))
        {
          m_Data[i][j][0] = "";
          m_Data[i][j][1] = "0";
          for(int k=0;k<m_SizeLine*m_SizeColumn;k++)
          {
            Tableau tab = (Tableau)m_Tableau.get(k);
            int val = (j*m_SizeLine*m_SizeColumn)+i;
            Case carreau = (Case) tab.getCase(val);
            carreau.init();
          }
        }
      }
    }
    m_Resultats.clear();
    m_IndexResultats = 0;
    m_Table.repaint();
  }

  //**************************************************************************
  //
  // Méthodes pour les boutons du menu "Fichier"
  //
  //**************************************************************************
  // Méthode pour créer une nouvelle partie
  private void NouvellePartie(int niveau)
  {
	m_CurrentDiagonale = m_HasDiagonale;  
    VerifieIsSave();
    m_IsSave = true;
    EnableAnalyse(false);
    EnableResolution(true);
    m_Niveau = niveau;
    InitialisationTotale();
    // création de la grille finale
    if(!Start(false,-1))
    {
        StartAgain();
        EnableModifier(true);
        EnableValider(false);
        m_ModeSaisieGrille = false;
        m_IsCellEditable = true;
        m_Table.repaint();
        return;
    }
    /// on réinitialise Algo
    /// on stocke les valeurs des cases dans finalValue
    for(int i=0;i<m_SizeLine*m_SizeColumn;i++)
    {
      Tableau tab = (Tableau)m_Tableau.get(i);
      tab.setSymetrique(m_IsSymetrique);
      // boucle sur les cases
      for(int j=0;j<m_SizeLine*m_SizeColumn*m_SizeLine*m_SizeColumn;j++)
      {
        Case carreau = (Case) tab.getCase(j);
        carreau.switchToFinalValue();
      }
    }
    ArrayList carreauxPris = new ArrayList();
    // Création de la grille initiale
    ArrayList listeProchainsCarreaux = new ArrayList();
    NouveauCarreau(listeProchainsCarreaux);
    boolean isFinish = false;
    while(!isFinish)
    {
      StartAgain();
      for(int i=0;i<listeProchainsCarreaux.size();i++)
      {
    	  Case prochainCarreau = (Case)listeProchainsCarreaux.get(i);
	      int row = prochainCarreau.getRow();
	      int col = prochainCarreau.getColumn();
	      m_Data[row][col][0] = Integer.toString(prochainCarreau.findFinalValue());
	      m_Data[row][col][1] = "2";
	      carreauxPris.add(prochainCarreau);
      }
      InitData();
      if(!Start(true,niveau))
      {
    	StartAgain();
    	EnableModifier(true);
    	EnableValider(false);
    	m_ModeSaisieGrille = false;
    	m_IsCellEditable = true;
    	m_Table.repaint();
    	return;
      }
      isFinish = IsFinish(listeProchainsCarreaux);
    }
    IncreaseDifficulty(carreauxPris,niveau);
    if(niveau == 4)
      RemoveOneValue(carreauxPris,niveau);
    else
    {
      m_Niveau = getVraiNiveau();
      printTitle();
    }
    // on ne souhaite faire un test sur la symétrie qu'en création de tableau
    for(int i=0;i<m_SizeLine*m_SizeColumn;i++)
    {
      Tableau tab = (Tableau)m_Tableau.get(i);
      tab.setSymetrique(false);
    }
    StartAgain();
    EnableModifier(true);
    EnableValider(false);
    m_ModeSaisieGrille = false;
    m_IsCellEditable = true;
    m_Table.repaint();
  }

  // Cette méthode permet de choisir le fichier que l'on souhaite ouvrir
  private void OuvrirFichier()
  {
    VerifieIsSave();
    File l_File = null;
    if(m_Adresse != null)
    {
      l_File = new File(m_Adresse);
      m_FileChooser.setCurrentDirectory(l_File);
    }
    int returnVal = m_FileChooser.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION)
    {
      String adresse = m_FileChooser.getSelectedFile().getAbsolutePath();
      l_File = new File(adresse);
      if (l_File.getAbsolutePath().endsWith(".sud"))
      {
        m_Niveau = -1;
        InitialisationTotale();
        ScanFile(l_File);
        try{
          m_NomGrille = l_File.getName();
        }
        catch(Exception exc)
        {
          m_NomGrille = "";
        }
        printTitle();
        m_IsSave = true;
        m_AdresseFichier = adresse;
        m_Adresse = l_File.getParent();
        savePolice();
      }
      else
        JOptionPane.showMessageDialog(this, "Merci de choisir un fichier .sud", "Ouvrir", JOptionPane.ERROR_MESSAGE);
    }
  }

  // lecture des données issues du fichier pour la récupération de la grille
  private void ScanFile(File file)
  {
	String[][][] _DataMemory;
    try
    {
      BufferedReader l_Buffer = new BufferedReader(new FileReader(file));
      String l_Line = l_Buffer.readLine();
      String niveau = l_Line.substring(0,1);
      m_Niveau = Integer.parseInt(niveau.trim());
      m_Niveau--;
      try{
    	  int endDiagonale = (l_Line.length()>7?7:l_Line.length());
    	  String hasDiagonale = l_Line.substring(2,endDiagonale);
    	  hasDiagonale = hasDiagonale.trim();
    	  m_CurrentDiagonale = false;
    	  if(hasDiagonale.equals("true"))
    		  m_CurrentDiagonale = true;
      }
      catch(Exception exc)
      {
    	  System.out.println("Exception m_CurrentDiagonale");
    	  m_CurrentDiagonale = false;
      }
      if(l_Line.length() > 7)
      {
	      try{
	    	  String sizeLine = l_Line.substring(8,9);
	    	  m_SizeLine = Integer.decode(sizeLine).intValue();
	      }
	      catch(Exception exc)
	      {
	    	  System.out.println("Exception m_SizeLine");
	    	  return;
	      }
	      try{
	    	  String sizeColumn = l_Line.substring(10,11);
	    	  m_SizeColumn = Integer.decode(sizeColumn).intValue();
	      }
	      catch(Exception exc)
	      {
	    	  System.out.println("Exception m_SizeColumn");
	    	  return;
	      }
      }
      else
      {
    	  m_SizeLine = 3;
    	  m_SizeColumn = 3;
      }
      initNewDimensionOfTab();
      _DataMemory = new String[m_SizeLine*m_SizeColumn][m_SizeLine*m_SizeColumn][2];
      l_Line = l_Buffer.readLine();
      while (l_Line != null)
      {
        char[] l_LineToChar= l_Line.toCharArray();
        StringBuffer[] l_listeValue = new StringBuffer[4];
        int l_indexListeValue = 0;
        StringBuffer l_buffer = new StringBuffer();
        char val;
        for(int i=0;i<l_LineToChar.length;i++)
        {
        	val = l_LineToChar[i];
        	if(val != ' ')
        		l_buffer = l_buffer.append(val);
        	else if(l_indexListeValue < 4 && l_buffer.length() > 0)
        	{
        		l_listeValue[l_indexListeValue] = l_buffer;
        		l_buffer = new StringBuffer();
        		l_indexListeValue++;
        	}
        }
        if(l_indexListeValue < 4 && l_buffer.length() > 0)
        	l_listeValue[l_indexListeValue] = l_buffer;
        int row = Integer.parseInt(l_listeValue[0].toString());
        int col = Integer.parseInt(l_listeValue[1].toString());
        int value = Integer.parseInt(l_listeValue[2].toString());
        int type = Integer.parseInt(l_listeValue[3].toString());
        // si le fichier est tout pourri => Exception
        if(value < 0 || value >= (m_SizeLine*m_SizeColumn+1) ||
           row < 0 || row >= (m_SizeLine*m_SizeColumn) ||
           col < 0 || col >= (m_SizeLine*m_SizeColumn) ||
           type < 0 || type > 3)
          throw new Exception();
        // sinon on récupère la valeur
        if(value == 0)
        {
          _DataMemory[row][col][0] = "";
          _DataMemory[row][col][1] = "0";
          m_Data[row][col][0] = "";
          m_Data[row][col][1] = "0";
        }
        else
        {
          _DataMemory[row][col][0] = Integer.toString(value);
          _DataMemory[row][col][1] = Integer.toString(type);
          if(type == 2)
          {
        	m_Data[row][col][0] = Integer.toString(value);
        	m_Data[row][col][1] = Integer.toString(type);
          }
        }
        l_Line = l_Buffer.readLine();
      }
    }
    catch (Exception e)
    {
      m_Niveau = -1;
      InitialisationTotale();
      JOptionPane.showMessageDialog(this, "Ce fichier n'est pas au bon format !!", "Ouvrir", JOptionPane.ERROR_MESSAGE);
      m_Table.repaint();
      EnableAnalyse(false);
      EnableModifier(false);
      EnableValider(false);
      EnableResolution(true);
      m_ModeSaisieGrille = false;
      m_IsCellEditable = false;
      m_CurrentDiagonale = m_HasDiagonale;
      return;
    }
    // recherche de la solution finale (on ne travaille pas sur la saisie du joueur)
    ValiderSaisie();
    // Enfin on récupère la saisie intégrale stockée dans le fichier
    for(int i=0;i<m_SizeLine*m_SizeColumn;i++)
    {
    	for(int j=0;j<m_SizeLine*m_SizeColumn;j++)
    	{
    		for(int k=0;k<2;k++)
    		{
    			m_Data[i][j][k]=_DataMemory[i][j][k];
    		}
    	}
    }
    EnableAnalyse(false);
    EnableModifier(true);
    EnableValider(false);
    EnableResolution(true);
    m_ModeSaisieGrille = false;
    m_IsCellEditable = true;
    m_Table.repaint();
}

  // Cette méthode gère la sauvegarde de la grille dans un fichier, elle permet
  // de choisir le répertoire et le nom du fichier
  private void EnregistrerGrille(boolean enregistrerSous)
  {
    if(m_AdresseFichier == null || enregistrerSous)
    {
      if(m_Adresse != null)
      {
        File l_File1 = new File(m_Adresse);
        m_FileChooser.setCurrentDirectory(l_File1);
      }
      ////////////
      int returnVal = m_FileChooser.showSaveDialog(this);
      if (returnVal == JFileChooser.APPROVE_OPTION)
      {
        String name = m_FileChooser.getSelectedFile().getName();
        if(name.trim().length() == 0)
        {
          JOptionPane.showMessageDialog(this, "Merci de donner un nom à votre fichier", "Sauver", JOptionPane.ERROR_MESSAGE);
          return;
        }
        String path = m_FileChooser.getSelectedFile().getAbsolutePath();
        if(! path.endsWith(".sud"))
          path = path.concat(".sud");
        m_AdresseFichier = path;
        if(m_Adresse == null || m_FileChooser.getSelectedFile().getParent().equals(m_Adresse))
        {
          m_Adresse = m_FileChooser.getSelectedFile().getParent();
          savePolice();
        }
      }
      else
        return;
    }
    File l_File = new File(m_AdresseFichier);
    SaveFile(l_File);
  }

  // Sauvegarde la grille dans un fichier
  private void SaveFile(File file)
  {
    try
    {
      FileWriter writer = new FileWriter(file);
      PrintWriter printWriter = new PrintWriter(new BufferedWriter(writer));
      String context = Integer.toString((m_Niveau+1));
      context = context.concat(" ");
      context = context.concat(Boolean.toString(m_CurrentDiagonale));
      // bidouille pour la relecture, on ajoute un espace pour que true et false aient la même taille
      if(m_CurrentDiagonale)
    	  context = context.concat(" ");
      context = context.concat(" ");
      context = context.concat(Integer.toString(m_SizeLine));
      context = context.concat(" ");
      context = context.concat(Integer.toString(m_SizeColumn));
      
      printWriter.println(context);

      for(int row=0;row<m_SizeLine*m_SizeColumn;row++)
      {
        for(int col=0;col<m_SizeLine*m_SizeColumn;col++)
        {
          String data=Integer.toString(row);
          data = data.concat(" ");
          data = data.concat(Integer.toString(col));
          data = data.concat(" ");
          if(m_Data[row][col][0].equals(""))
            data = data.concat("0");
          else
            data = data.concat(m_Data[row][col][0]);
          data = data.concat(" ");
          data = data.concat(m_Data[row][col][1]);
          printWriter.println(data);
        }
      }
      printWriter.close();
      m_IsSave = true;
      try
      {
    	m_NomGrille = file.getName();
      }
      catch(Exception exc)
      {
    	m_NomGrille = "";
      }
      printTitle();
    }
    catch(Exception e)
    {
      System.out.println("Exception "+e.toString());
    }
  }

  // vérifie si la sauvegarde a été faite
  private void VerifieIsSave()
  {
    if(m_IsSave)
      return;
    if (JOptionPane.showConfirmDialog(this, "Souhaitez vous sauvegarder la grille ?", "Sauvegarde", JOptionPane.YES_NO_OPTION)== JOptionPane.NO_OPTION)
      return;
    EnregistrerGrille(false);
  }

  // Gestion de l'impression d'une grille
  private void Impression()
  {
    Properties props = new Properties();
	PrintJob pJob = getToolkit().getPrintJob(this,"Printing_Test",props);
	if(pJob != null)
	{
	  Graphics pg = pJob.getGraphics();
	  pg.translate(20,20);
	  m_Table.printAll(pg);
	  pg.dispose();
	  pJob.end();
	}
  }
  
  // Affiche la fenêtre de gestion des polices
  private void showConfiguration()
  {
    m_Configuration.init(m_PoliceGrilleInitiale,m_PoliceSaisie,m_PoliceNextValue,m_PoliceErreurs,m_CouleurGrille,m_CouleurDiagonale,m_HasDiagonale,m_IsSymetrique,m_SizeLine,m_SizeColumn);
    Point location = getLocation();
    Dimension dim = getSize();
    Dimension dimConf = m_Configuration.getSize();
    int valX = location.x+(int)(dim.width*0.5)-(int)(dimConf.width*0.5);
    int valY = location.y+(int)(dim.height*0.5)-(int)(dimConf.height*0.5);
    m_Configuration.setLocation(valX,valY);
    m_Configuration.setVisible(true);
  }

  // Demande confirmation avant de quitter l'application
  private void QuitterAction()
  {
   if (JOptionPane.showConfirmDialog(this, "Désirez vous quitter l'application ?", "Quitter", JOptionPane.YES_NO_OPTION)== JOptionPane.NO_OPTION)
   {
     return;
   }
   System.exit(0);
  }

  // Fermer l'application (lorsque l'on clique sur la croix en haut à droite)
  protected void processWindowEvent(WindowEvent e)
  {
   if (e.getID() == WindowEvent.WINDOW_CLOSING)
   {
     QuitterAction();
   }
   else
   {
     super.processWindowEvent(e);
   }
  }

  //**************************************************************************
  //
  // Méthodes pour les boutons du menu "Résolution"
  //
  //**************************************************************************
  // Cette méthode vérifie la validité de la saisie
  private boolean VerifierSaisie(boolean showDialog)
  {
    StartAgain();
    InitData();
    ArrayList incoherence = Algo.getInstance().testCoherence(m_Tableau);
    if(incoherence.size() > 0)
    {
      if(! showDialog)
        return false;
      if((JOptionPane.showConfirmDialog(this, "Il y a des erreurs basiques, souhaitez-vous les visualiser?", "Vérification", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE)) == JOptionPane.YES_OPTION)
      {
        for(int i=0;i<incoherence.size();i++)
        {
          Case carreau = (Case)incoherence.get(i);
          int row = carreau.getRow();
          int column = carreau.getColumn();
          if(m_Data[row][column][1].equals("0"))
            m_Data[row][column][1] = "3";
        }
      }
    }
    else
    {
      Start(false,-1);
      if(!IsFinish(new ArrayList()))
      {
        if(! showDialog)
          return false;
        // Si la grille de départ possède plusieures solutions et que la saisie ne permet d'atteindre aucune de ces solutions la visualisation se fait
        // arbitrairement par rapport à une de ces solutions.
        if((JOptionPane.showConfirmDialog(this, "Il y a des erreurs, souhaitez-vous les visualiser?", "Vérification", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE)) == JOptionPane.YES_OPTION)
        {
            Tableau tab = (Tableau)m_Tableau.get(0);
            // boucle sur les cases
            for(int j=0;j<m_SizeLine*m_SizeColumn*m_SizeLine*m_SizeColumn;j++)
            {
              Case carreau = (Case) tab.getCase(j);
              String value = Integer.toString(carreau.findFinalValue());
              String dataValue = m_Data[carreau.getRow()][carreau.getColumn()][0];
              if(! dataValue.equals("") && ! dataValue.equals(value))
                m_Data[carreau.getRow()][carreau.getColumn()][1] = "3";
            }
        }
      }
      else if(showDialog)
        JOptionPane.showMessageDialog(this, "Votre grille est correcte", "Vérification", JOptionPane.INFORMATION_MESSAGE);
    }
    m_Table.repaint();
    return true;
  }

  // Cette méthode affiche la prochaine valeur après vérification que la grille actuelle est correcte
  private void ProchaineValeur()
  {
    if(! VerifierSaisie(false))
    {
      JOptionPane.showMessageDialog(this, "Avant toute chose, vérifiez la validité de votre saisie", "Prochaine valeur", JOptionPane.INFORMATION_MESSAGE);
      return;
    }
    for(int i=0;i<m_SizeLine*m_SizeColumn;i++)
    {
      for(int j=0;j<m_SizeLine*m_SizeColumn;j++)
      {
        String type = m_Data[i][j][1];
        if(type.equals("1"))
          m_Data[i][j][1] = "0";
      }
    }
    StartAgain();
    InitData();
    m_Resultats.clear();
    Start(false,-1);
    if(m_Resultats.size() == 0)
      return;
    Case carreau = (Case)m_Resultats.get(0);
    int row = carreau.getRow();
    int column = carreau.getColumn();
    m_Data[row][column][0] = String.valueOf(carreau.getValue());
    m_Data[row][column][1] = "1";
    EnableModifier(false);
    m_Table.repaint();
  }

  // Méthode qui permet de recommencer la partie, après demande de confirmation
  private void RecommencerSaisie()
  {
    if (JOptionPane.showConfirmDialog(this, "Etes-vous sûr de vouloir recommencer la partie ?", "Quitter", JOptionPane.YES_NO_OPTION)== JOptionPane.NO_OPTION)
    {
      return;
    }
    StartAgain();
    m_IsCellEditable = true;
    m_ModeSaisieGrille = false;
    for(int row=0;row<m_SizeLine*m_SizeColumn;row++)
    {
      for(int col=0;col<m_SizeLine*m_SizeColumn;col++)
      {
        if(! m_Data[row][col][1].equals("2"))
        {
          m_Data[row][col][0] = "";
          m_Data[row][col][1] = "0";
        }
      }
    }
    EnableAnalyse(false);
    EnableValider(false);
    EnableModifier(true);
    m_Table.repaint();
  }

  //**************************************************************************
  //
  // Accesseurs aux différentes polices
  //
  //**************************************************************************
  public void setPoliceInitiale(Police policeInitiale)
  {
    m_PoliceGrilleInitiale = policeInitiale;
  }

  public Police getPoliceInitiale()
  {
    return m_PoliceGrilleInitiale;
  }

  public void setPoliceSaisie(Police policeSaisie)
  {
    m_PoliceSaisie = policeSaisie;
  }

  public Police getPoliceSaisie()
  {
    return m_PoliceSaisie;
  }

  public void setPoliceNextValue(Police policeNextValue)
  {
    m_PoliceNextValue = policeNextValue;
  }

  public Police getPoliceNextValue()
  {
    return m_PoliceNextValue;
  }

  public void setPoliceErreurs(Police policeErreurs)
  {
    m_PoliceErreurs = policeErreurs;
  }

  public Police getPoliceErreurs()
  {
    return m_PoliceErreurs;
  }

  // Met à jour la couleur de la grille, on remplace les anciens bords par de nouveaux
  // qui prennent en compte la nouvelle couleur
  public void setCouleurGrille(Color couleurGrille)
  {
    m_CouleurGrille = couleurGrille;
    for(int i=0;i<m_SizeLine*m_SizeColumn;i++)
    {
      for(int j=0;j<m_SizeLine*m_SizeColumn;j++)
        m_TextFieldData[i][j].setBorder(getBorder(i,j));
    }
    m_Table.setGridColor(m_CouleurGrille);
    m_Table.repaint();
  }
  
  // Retourne la couleur de la grille
  public Color getCouleurGrille()
  {
    return m_CouleurGrille;
  }
  
  public void setCouleurDiagonale(Color couleurDiagonale)
  {
	  m_CouleurDiagonale = couleurDiagonale;
  }

  public void setDiagonale(boolean hasDiagonale)
  {
	  m_HasDiagonale = hasDiagonale;
	  if(m_Niveau == -1 && ! m_IsCellEditable)
		  m_CurrentDiagonale = m_HasDiagonale;  
  }
  
  public void setSymetrie(boolean isSymetrie)
  {
	  m_IsSymetrique = isSymetrie;
  }
  

  public void setLineAndColumnSize(int line, int column)
  {
	  if(line == m_SizeLine && column == m_SizeColumn)
		  return;
	  m_SizeLine = line;
	  m_SizeColumn = column;
	  initNewDimensionOfTab(); 
	  // on nettoie la mémoire
	  System.gc();
  }


  // renvoie le bord avec sa couleur et son épaisseur en fonction de sa position dans la grille
  private Border getBorder(int i, int j)
  {
    int top = 0;
    // bord haut
    if(i == 0)
        top = 2;
    else
    {
    	for(int k=1;k<m_SizeColumn;k++)
    	{
    	    if(i == (m_SizeLine*k))
    	    {
    	        top = 1;
    	        break;
    	    }
    	}
    }
    
    // bord gauche
    int left = 0;
    if(j == 0)
        left = 2;
    else 
    {
    	for(int k=1;k<m_SizeLine;k++)
    	{
    	    if(j == (m_SizeColumn*k))
    	    {
    	    	left = 1;
    	        break;
    	    }
    	}
    }
     
    // bord bas
    int bottom = 0;
	for(int k=1;k<=m_SizeColumn;k++)
	{
	    if(i == (m_SizeLine*k-1))
	    {
	    	bottom = 1;
	        break;
	    }
	}
    
    // bord droit
    int right = 0;
	for(int k=1;k<=m_SizeLine;k++)
	{
	    if(j == (m_SizeColumn*k-1))
	    {
	    	right = 1;
	        break;
	    }
	}
    MatteBorder border = new MatteBorder(top,left,bottom,right,m_CouleurGrille);
    return border;
   }

  //**************************************************************************
  //
  // Ecriture et lecture des différentes polices
  //
  //**************************************************************************

  // Sauvegarde de la police dans Sudoku.ini
  public void savePolice()
  {
    try
    {
      File file = new File("sudoku.ini");
      FileWriter writer = new FileWriter(file);
      PrintWriter printWriter = new PrintWriter(new BufferedWriter(writer));
      printWriter.println(m_Adresse);
      String police = "\""+m_PoliceGrilleInitiale.getFont().getName()+"\""+" "+
              m_PoliceGrilleInitiale.getFont().getStyle()+" "+
              m_PoliceGrilleInitiale.getFont().getSize()+" "+
              UtilColor.getInstance().getName(m_PoliceGrilleInitiale.getBackColor())+" "+
              UtilColor.getInstance().getName(m_PoliceGrilleInitiale.getForeColor());
      printWriter.println(police);
      police = "\""+m_PoliceSaisie.getFont().getName()+"\""+" "+
              m_PoliceSaisie.getFont().getStyle()+" "+
              m_PoliceSaisie.getFont().getSize()+" "+
              UtilColor.getInstance().getName(m_PoliceSaisie.getBackColor())+" "+
              UtilColor.getInstance().getName(m_PoliceSaisie.getForeColor());
      printWriter.println(police);
      police = "\""+m_PoliceNextValue.getFont().getName()+"\""+" "+
              m_PoliceNextValue.getFont().getStyle()+" "+
              m_PoliceNextValue.getFont().getSize()+" "+
              UtilColor.getInstance().getName(m_PoliceNextValue.getBackColor())+" "+
              UtilColor.getInstance().getName(m_PoliceNextValue.getForeColor());
      printWriter.println(police);
      police = "\""+m_PoliceErreurs.getFont().getName()+"\""+" "+
              m_PoliceErreurs.getFont().getStyle()+" "+
              m_PoliceErreurs.getFont().getSize()+" "+
              UtilColor.getInstance().getName(m_PoliceErreurs.getBackColor())+" "+
              UtilColor.getInstance().getName(m_PoliceErreurs.getForeColor());
      printWriter.println(police);
      police = UtilColor.getInstance().getName(m_CouleurGrille);
      printWriter.println(police);
      police = UtilColor.getInstance().getName(m_CouleurDiagonale);
      printWriter.println(police);
      printWriter.println(Boolean.toString(m_HasDiagonale));
      printWriter.println(Boolean.toString(m_IsSymetrique));
      printWriter.println(Integer.toString(m_SizeLine));
      printWriter.println(Integer.toString(m_SizeColumn));
      printWriter.close();
    }
    catch(Exception e)
    {
      System.out.println("Exception "+e.toString());
    }
  }

  // Lecture du fichier sudoku.ini et récupération des différentes polices
  private void loadPolice()
  {
    File l_File = new File("sudoku.ini");
    BufferedReader l_Buffer = null;
    try
    {
      l_Buffer = new BufferedReader(new FileReader(l_File));
      for(int i=0;i<6;i++)
      {
        String l_Line = l_Buffer.readLine();
        if(i==0)
        {
          if(l_Line.equals("null"))
            m_Adresse = null;
          else
            m_Adresse = l_Line;
        }
        else if(i == 5)
            m_CouleurGrille = UtilColor.getInstance().getColor(l_Line);
        else
        {
          // Récupération du nom de la police
          char[] listeChar = l_Line.toCharArray();
          int j=1;
          while(listeChar[j] != '\"')
            j++;
          String name=String.copyValueOf(listeChar,1,j-1);
          int depart = j+2;
          j = 0;
          while(listeChar[depart+j] != ' ')
            j++;
          String style=String.copyValueOf(listeChar,depart,j);
          depart = depart+j+1;
          j=0;
          while(listeChar[depart+j] != ' ')
            j++;
          String taille=String.copyValueOf(listeChar,depart,j);
          depart = depart+j+1;
          j=0;
          while(listeChar[depart+j] != ' ')
            j++;
          String backColor = String.copyValueOf(listeChar,depart,j);
          depart = depart+j+1;
          String foreColor = String.copyValueOf(listeChar,depart,listeChar.length-depart);
          Color backColore = UtilColor.getInstance().getColor(backColor);
          Color foreColore = UtilColor.getInstance().getColor(foreColor);
          int styl = Integer.parseInt(style);
          int size = Integer.parseInt(taille);
          if(i==1)
            m_PoliceGrilleInitiale = new Police(new Font(name,styl,size),backColore,foreColore);
          else if(i==2)
            m_PoliceSaisie = new Police(new Font(name,styl,size),backColore,foreColore);
          else if(i==3)
            m_PoliceNextValue = new Police(new Font(name,styl,size),backColore,foreColore);
          else if(i==4)
            m_PoliceErreurs = new Police(new Font(name,styl,size),backColore,foreColore);
        }
      }
    }
    catch (Exception e)
    {
      // Si pas de fichier sudoku.ini ou s'il n'est pas au bon format
      InitPolice();
      return;
    }
    try
    {
	  for(int i=0;i<5;i++)
	  {
	    String l_Line = l_Buffer.readLine();
	    if(i == 0)
            m_CouleurDiagonale = UtilColor.getInstance().getColor(l_Line);
        else if(i == 1)
        {
        	m_HasDiagonale = false;
        	if(l_Line.equals("true"))
        		m_HasDiagonale = true;
        }
        else if(i == 2)
        {
        	m_IsSymetrique = false;
        	if(l_Line.equals("true"))
        		m_IsSymetrique = true;
        }
        else if(i == 3)
        {
        	try
        	{
        		m_SizeLine = Integer.decode(l_Line).intValue();
        	}
        	catch (Exception e)
        	{
        	     m_SizeLine = 3;
        	}
        }
        else if(i == 4)
        {
        	try
        	{
        		m_SizeColumn = Integer.decode(l_Line).intValue();
        	}
        	catch (Exception e)
        	{
        		m_SizeColumn = 3;
        	}
        }
	  }
    }
    catch (Exception e)
    {
      m_CouleurDiagonale = Color.lightGray;
      m_HasDiagonale = false;
      m_IsSymetrique = false;
      m_SizeLine = 3;
      m_SizeColumn = 3;
    }
    m_CurrentDiagonale = m_HasDiagonale;  
  }

  // Initialisation de la police avec des valeurs par défaut
  private void InitPolice()
  {
    m_PoliceGrilleInitiale = new Police(new Font("serif",Font.BOLD,20),Color.white,Color.black);
    m_PoliceSaisie = new Police(new Font("serif",Font.PLAIN,20),Color.white,Color.gray);
    m_PoliceErreurs = new Police(new Font("serif",Font.PLAIN,20),Color.white,Color.red);
    m_PoliceNextValue = new Police(new Font("serif",Font.PLAIN,20),Color.white,Color.green);
    m_CouleurGrille = Color.blue;
    m_CouleurDiagonale = Color.lightGray;
    m_HasDiagonale = false;
    m_CurrentDiagonale = false;  
    m_SizeLine = 3;
    m_SizeColumn = 3;
  }

  // Affichage de la boite de dialogue APropos
  private void AfficheAPropos()
  {
    JOptionPane.showMessageDialog(this, "Version 3.0\nDéveloppé par OscarI", "A propos...", JOptionPane.INFORMATION_MESSAGE);
  }

  // Renvoie une représentation du niveau sous forme de '*'
  private String getStringNiveau()
  {
    String niveauString = "";
    for(int i=-1;i<m_Niveau;i++)
    {
      niveauString += " *";
    }
    return niveauString;
  }
  
  // retourne le vrai niveau d'une grille
  private int getVraiNiveau()
  {
	int niveau=0;
	while(niveau<=3)
	{
      StartAgain();
      InitData();
	  Start(true,niveau);
	  if(IsFinish(new ArrayList()))
	    break;
	  else
	    niveau++;
	}
	return niveau;
  }
  
  private void printTitle()
  {
	this.setTitle("SU-DO-KU "+m_NomGrille+getStringNiveau());
  }
  //*****************************************************************************
  //
  // Méthodes de gestion de l'algorithme de résolution et de création des grilles
  //
  //*****************************************************************************
  private void InitData()
  {
    try
    {
      Tableau tab = (Tableau)m_Tableau.get(0);
      int current = -1;
      // boucle sur les rangees
      for(int j=0;j<m_SizeLine*m_SizeColumn;j++)
      {
        Rangee rangee = (Rangee) tab.getLigne(j);
        // boucle sur les cases
        for(int i=0;i<m_SizeLine*m_SizeColumn;i++)
        {
          current++;
          Case carreau = (Case) rangee.getCase(i);
          String string = (String)m_Table.getModel().getValueAt(j,i);
          try {
            int val = Integer.parseInt(string.trim());
            if(val != 0)
              carreau.setInitialValue(val);
          }
          catch (NumberFormatException e) {
          }
        }
      }
    }
    catch(Exception e)
    {
    }
  }

  // niveau = -1 => on passe dans tous les algos
  // niveau [0,3], création de la grille finale
  private boolean Start(boolean isCreation, int niveau)
  {
	if(m_DiagonaleMode != m_CurrentDiagonale)
	{
	  m_DiagonaleMode = m_CurrentDiagonale;
	  switchDiagonale();	
	}
    ArrayList incoherence = Algo.getInstance().testCoherence(m_Tableau);
    if(incoherence.size() > 0)
    {
      JOptionPane.showMessageDialog(this, "Erreur de saisie", "Erreur", JOptionPane.OK_OPTION);
      
      return false;
    }
    boolean goOn = true;
    while(goOn)
    {
      goOn = false;
      Algo.getInstance().tryAlgoDeBase(m_Tableau,m_SizeLine,m_SizeColumn);
      if(niveau == 0)
        break;
      if(!IsFinish(new ArrayList()))
      {
        int resultat = Algo.getInstance().tryAlgoOtherCase(m_Tableau, niveau,m_SizeLine,m_SizeColumn);
        goOn = (resultat != 0);
        if(!goOn && (niveau ==1 || niveau == 2))
          break;
      }
      else if(isCreation || Algo.getInstance().testCoherence(m_Tableau).size() == 0)
         break;
      if(!goOn)
      {
        int resultat = Algo.getInstance().tryAlgoMiniRangees(m_Tableau,m_SizeLine,m_SizeColumn);
        goOn = (resultat != 0);
      }
      if(! goOn)
      {
        int resultat = Algo.getInstance().tryAlgoNUplesVisibles(2,m_Tableau);
        goOn = (resultat != 0);
      }
      if(! goOn)
      {
        int resultat = Algo.getInstance().tryAlgoNUplesCaches(2,m_Tableau,m_SizeLine,m_SizeColumn);
        goOn = (resultat != 0);
      }
      if(! goOn)
      {
        int resultat = Algo.getInstance().tryAlgoNUplesVisibles(3,m_Tableau);
        goOn = (resultat != 0);
      }
      if(! goOn)
      {
        int resultat = Algo.getInstance().tryAlgoNUplesCaches(3,m_Tableau,m_SizeLine,m_SizeColumn);
        goOn = (resultat != 0);
      }
      if(! goOn && ! isCreation)
      {
        goOn = Algo.getInstance().tryAlgoAuHasard(m_Tableau,m_SizeLine,m_SizeColumn);
      }
    }
    return true;
  }

  private boolean IsFinish(ArrayList listeResult)
  {
    Tableau tab = (Tableau)m_Tableau.get(0);
    ArrayList liste = new ArrayList();
    // boucle sur les cases
    for(int j=0;j<m_SizeLine*m_SizeColumn*m_SizeLine*m_SizeColumn;j++)
    {
      Case carreau = (Case) tab.getCase(j);
      int currentNbIndetermines = carreau.getFinalResolutionValue();
      if(currentNbIndetermines < 0)
        liste.add(carreau);
    }
    if(liste.size() == 0)
      return true;
    getCaseWithSymetrique(liste,listeResult);
    return false;
  }
  
  private void InitialisationTotale()
  {
    // On crée un nouveau tableau
	m_NomGrille = "";
    printTitle();
    m_AdresseFichier = null;
    m_IndexResultats = 0;
    m_Resultats.clear();
    Algo.getInstance().init();
    for(int i=0;i<m_SizeLine*m_SizeColumn;i++)
    {
      for(int j=0;j<m_SizeLine*m_SizeColumn;j++)
      {
        m_Data[i][j][0] = "";
        m_Data[i][j][1] = "0";
      }
    }
    for(int i=0;i<m_SizeLine*m_SizeColumn;i++)
    {
      Tableau tab = (Tableau)m_Tableau.get(i);
      // boucle sur les cases
      for(int j=0;j<m_SizeLine*m_SizeColumn*m_SizeLine*m_SizeColumn;j++)
      {
        Case carreau = (Case) tab.getCase(j);
        carreau.init();
      }
    }
  }

  // on retire une valeur (voir deux si grille symétrique) de manière 
  // à ce que le nombre de chiffre à choisir au hasard soit minimal
  private void RemoveOneValue(ArrayList carreauxPris, int niveau)
  {
    int nbAuHasard = m_SizeLine*m_SizeColumn*m_SizeLine*m_SizeColumn+1;
    Case carreauFinal = null;
    Case carreauFinalSym = null;
    Tableau tab = (Tableau)m_Tableau.get(0);
    for(int i=0;i<carreauxPris.size() && nbAuHasard > 1;i++)
    {
      StartAgain();
      Case carreau = (Case) carreauxPris.get(i);
      int row = carreau.getRow();
      int col = carreau.getColumn();
      m_Data[row][col][0] = "";
      m_Data[row][col][1] = "0";
      Case carreauSym = tab.getSymetrique(carreau);
      int rowSym = 0;
      int colSym = 0;
      if(carreauSym != null)
      {
    	  rowSym = carreauSym.getRow();
          colSym = carreauSym.getColumn();
          m_Data[rowSym][colSym][0] = "";
	      m_Data[rowSym][colSym][1] = "0";
      }
      InitData();
      Start(false,-1);
      m_Data[row][col][0] = Integer.toString(carreau.findFinalValue());
      m_Data[row][col][1] = "2";
      if(carreauSym != null)
      {
          m_Data[rowSym][colSym][0] = Integer.toString(carreauSym.findFinalValue());
	      m_Data[rowSym][colSym][1] = "2";
      }
      if(Algo.getInstance().isAuHasard < nbAuHasard)
      {
        nbAuHasard = Algo.getInstance().isAuHasard;
        carreauFinal = carreau;
        carreauFinalSym = carreauSym;
      }
    }
    if(carreauFinal != null)
    {
      int row = carreauFinal.getRow();
      int col = carreauFinal.getColumn();
      m_Data[row][col][0] = "";
      m_Data[row][col][1] = "0";
    }
    if(carreauFinalSym != null)
    {
      int row = carreauFinalSym.getRow();
      int col = carreauFinalSym.getColumn();
      m_Data[row][col][0] = "";
      m_Data[row][col][1] = "0";
    }
  }

  private void StartAgain()
  {
    Algo.getInstance().init();
    /// on stocke les valeurs des cases dans finalValue
    for(int i=0;i<m_SizeLine*m_SizeColumn;i++)
    {
      Tableau tab = (Tableau)m_Tableau.get(i);
      // boucle sur les cases
      for(int j=0;j<m_SizeLine*m_SizeColumn*m_SizeLine*m_SizeColumn;j++)
      {
        Case carreau = (Case) tab.getCase(j);
        carreau.startAgain();
      }
    }
  }

  private void NouveauCarreau(ArrayList listeResult)
  {
    Tableau tab = (Tableau)m_Tableau.get(0);
    // boucle sur les cases
    double choix = Math.random()*tab.getNbCases();
    int index = (int)Math.floor(choix);
    Case prochainCarreau = (Case) tab.getCase(index);
    listeResult.add(prochainCarreau);
    Case carreauSym = tab.getSymetrique(prochainCarreau);
    if(carreauSym != null)
      listeResult.add(carreauSym);
  }

  // cette méthode essaye d'augmenter la difficulté de la grille en retirant des
  // données de m_Data et en essayant la résolution
  private void IncreaseDifficulty(ArrayList carreauxPris,int niveau)
  {
    ArrayList removableValues = new ArrayList();
    Tableau tab = (Tableau)m_Tableau.get(0);
    for(int i=0;i<carreauxPris.size();i++)
    {
      StartAgain();
      Case carreau = (Case) carreauxPris.get(i);
      int row = carreau.getRow();
      int col = carreau.getColumn();
      m_Data[row][col][0] = "";
      m_Data[row][col][1] = "0";
	  Case carreauSym = tab.getSymetrique(carreau);
	  int rowSym = 0;
	  int colSym = 0;
	  if(carreauSym != null)
	  {
		  rowSym = carreauSym.getRow();
		  colSym = carreauSym.getColumn();
	      m_Data[rowSym][colSym][0] = "";
	      m_Data[rowSym][colSym][1] = "0";
	  }
      InitData();
      Start(true,niveau);
      if(!IsFinish(new ArrayList()))
      {
          m_Data[row][col][0] = Integer.toString(carreau.findFinalValue());
          m_Data[row][col][1] = "2";
          if(carreauSym != null)
          {
	          m_Data[rowSym][colSym][0] = Integer.toString(carreauSym.findFinalValue());
	          m_Data[rowSym][colSym][1] = "2";
          }
      }
      else
      {
        removableValues.add(carreau);
        if(carreauSym != null)
            removableValues.add(carreauSym);
      }
    }
    for(int i=0;i<removableValues.size();i++)
    {
      Case carreau = (Case) removableValues.get(i);
      carreauxPris.remove(carreau);
    }
  }
  
  private void switchDiagonale()
  {
    for(int i=0;i<m_Tableau.size();i++)
    {
      Tableau currentTableau = (Tableau)m_Tableau.get(i);
      currentTableau.switchDiagonale(m_DiagonaleMode);
    }
  }
  
  private void getCaseWithSymetrique(ArrayList liste, ArrayList listeResult)
  {
	listeResult.clear();  
	Tableau tab = (Tableau)m_Tableau.get(0);  
	boolean result = true;
	ArrayList listeLocale = new ArrayList(liste);
	while(result && listeLocale.size()>0)
	{
	    double choix = Math.random();
	    choix *=listeLocale.size();
	    int index = (int)Math.floor(choix);
	    Case carreau = (Case)listeLocale.remove(index);
	    Case carreauSym = tab.getSymetrique(carreau);
	    if(carreauSym == null)
	    {
	    	listeResult.add(carreau);
	    	result = false;
	    }
	    else
	    {
	    	listeResult.add(carreau);
	    	listeResult.add(carreauSym);
	    	result = false;
	    }
	}
  }
}
