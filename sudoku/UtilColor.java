package sudoku;

import java.awt.Color;
import javax.swing.JComboBox;

public class UtilColor {

  static UtilColor _UtilColor = null;

  private UtilColor()
  {
  }

  static public UtilColor getInstance()
  {
    if(_UtilColor == null)
      _UtilColor = new UtilColor();
    return _UtilColor;
  }

  public Color getColor(String couleur)
  {
    if(couleur.equals("noir"))
      return Color.black;
    else if(couleur.equals("bleu"))
      return Color.blue;
    else if(couleur.equals("cyan"))
      return Color.cyan;
    else if(couleur.equals("gris foncé"))
      return Color.darkGray;
    else if(couleur.equals("gris"))
      return Color.gray;
    else if(couleur.equals("vert"))
      return Color.green;
    else if(couleur.equals("gris clair"))
      return Color.lightGray;
    else if(couleur.equals("magenta"))
      return Color.magenta;
    else if(couleur.equals("orange"))
      return Color.orange;
    else if(couleur.equals("rose"))
      return Color.pink;
    else if(couleur.equals("rouge"))
      return Color.red;
    else if(couleur.equals("blanc"))
      return Color.white;
    else if(couleur.equals("jaune"))
      return Color.yellow;
    return Color.black;
  }

  public String getName(Color couleur)
  {
    if(couleur == Color.black)
      return "noir";
    else if(couleur == Color.blue)
      return "bleu";
    else if(couleur == Color.cyan)
      return "cyan";
    else if(couleur == Color.darkGray)
      return "gris foncé";
    else if(couleur == Color.gray)
      return "gris";
    else if(couleur == Color.green)
      return "vert";
    else if(couleur == Color.lightGray)
      return "gris clair";
    else if(couleur == Color.magenta)
      return "magenta";
    else if(couleur == Color.orange)
      return "orange";
    else if(couleur == Color.pink)
      return "rose";
    else if(couleur == Color.red)
      return "rouge";
    else if(couleur == Color.white)
      return "blanc";
    else if(couleur == Color.yellow)
      return "jaune";
    return "noir";
  }

  public void remplirCombo(JComboBox combo)
  {
    combo.addItem("noir");
    combo.addItem("gris foncé");
    combo.addItem("gris");
    combo.addItem("gris clair");
    combo.addItem("bleu");
    combo.addItem("cyan");
    combo.addItem("vert");
    combo.addItem("jaune");
    combo.addItem("rouge");
    combo.addItem("magenta");
    combo.addItem("orange");
    combo.addItem("rose");
    combo.addItem("blanc");
  }

}