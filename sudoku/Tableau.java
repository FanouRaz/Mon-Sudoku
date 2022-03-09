package sudoku;

import java.util.ArrayList;

public class Tableau
{
  private ArrayList _bloc;
  private ArrayList _carres;
  private ArrayList _lignes;
  private ArrayList _colonnes;
  private ArrayList _diagonales;
  private boolean   _isSymetrique; // pour la création de grilles symétriques
  private int _sizeLine;
  private int _sizeColumn;
  
  public Tableau(int sizeLine, int sizeColumn)
  {
    _sizeLine = sizeLine;  
    _sizeColumn = sizeColumn;  
    _isSymetrique = false;  
    _bloc = new ArrayList();
    _lignes = new ArrayList();
    _colonnes = new ArrayList();
    _carres = new ArrayList();
    _diagonales = new ArrayList();
  }

  public void clear()
  {
	  for(int i=0;i<_bloc.size();i++)
	  {
		  Case carreau = (Case) _bloc.get(i);
		  carreau.clear();
		  carreau = null;
	  }	  
	  _bloc.clear();
	  _bloc = null;
	  for(int i=0;i<_carres.size();i++)
	  {
		  Carre carre = (Carre) _carres.get(i);
		  carre.clear();
		  carre = null;
	  }	  
	  _carres.clear();
	  _carres = null;
	  for(int i=0;i<_lignes.size();i++)
	  {
		  Rangee ligne = (Rangee) _lignes.get(i);
		  ligne.clear();
		  ligne = null;
	  }	  
	  _lignes.clear();
	  _lignes = null;
	  for(int i=0;i<_colonnes.size();i++)
	  {
		  Rangee colonne = (Rangee) _colonnes.get(i);
		  colonne.clear();
		  colonne = null;
	  }	  
	  _colonnes.clear();
	  _colonnes = null;
	  for(int i=0;i<_diagonales.size();i++)
	  {
		  Diagonale diagonale = (Diagonale) _diagonales.get(i);
		  diagonale.clear();
		  diagonale = null;
	  }	  
	  _diagonales.clear();
	  _diagonales = null;
  }
  
  public void addCase(Case carreau)
  {
    _bloc.add(carreau);
  }

  public void addCarre(Carre carre)
  {
    _carres.add(carre);
  }

  public void addLigne(Rangee ligne)
  {
    _lignes.add(ligne);
  }

  public void addColonne(Rangee colonne)
  {
    _colonnes.add(colonne);
  }
  
  public void addDiagonale(Diagonale diagonale)
  {
	_diagonales.add(diagonale);
  }
  
  public Case getCase(int i)
  {
    return (Case)_bloc.get(i);
  }

  public int getNbLignes()
  {
    return _lignes.size();
  }

  public int getNbColonnes()
  {
    return _colonnes.size();
  }

  public int getNbCarres()
  {
    return _carres.size();
  }

  public int getNbCases()
  {
    return _bloc.size();
  }

  public int getNbDiagonales()
  {
    return _diagonales.size();
  }

  public Rangee getLigne(int i)
  {
    return (Rangee)_lignes.get(i);
  }

  public Rangee getColonne(int i)
  {
    return (Rangee)_colonnes.get(i);
  }

  public Carre getCarre(int i)
  {
    return (Carre)_carres.get(i);
  }

  public Diagonale getDiagonale(int i)
  {
    return (Diagonale)_diagonales.get(i);
  }
  
  public void switchDiagonale(boolean hasDiagonale)
  {
	for(int i=0;i<_diagonales.size();i++)
	{
	  Diagonale diagonale = (Diagonale)_diagonales.get(i);
	  diagonale.switchRangee(hasDiagonale);
	}
  }
  
  public void setSymetrique(boolean isSymetrique)
  {
	_isSymetrique = isSymetrique;
  }
  
  public Case getSymetrique(Case carreau)
  {
	int row = (_sizeLine*_sizeColumn-1)-carreau.getRow();
	int column = (_sizeLine*_sizeColumn-1)-carreau.getColumn();
	int index = row*(_sizeLine*_sizeColumn)+column;
	Case symetrique = (Case)_bloc.get(index);
	// dans le cas du carreau central, il n'y a pas de symétrique
	if(carreau == symetrique || ! _isSymetrique)
		symetrique = null;
	return symetrique;
  }
}
