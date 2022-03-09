package sudoku;

import java.util.ArrayList;

public class Carre implements ICaseList
{
  private ArrayList _bloc;
  private ArrayList _miniLignes;
  private ArrayList _miniColonnes;

  public Carre()
  {
    _bloc = new ArrayList();
    _miniLignes = new ArrayList();
    _miniColonnes = new ArrayList();
  }

  public void clear()
  {
	  for(int i=0;i<_bloc.size();i++)
	  {
		  Case carreau = (Case) _bloc.get(i);
		  carreau = null;
	  }	  
	  _bloc.clear();
	  _bloc = null;
	  for(int i=0;i<_miniLignes.size();i++)
	  {
		  MiniRangee miniRangee = (MiniRangee) _miniLignes.get(i);
		  miniRangee = null;
	  }	  
	  _miniLignes.clear();
	  _miniLignes = null;
	  for(int i=0;i<_miniColonnes.size();i++)
	  {
		  MiniRangee miniRangee = (MiniRangee) _miniColonnes.get(i);
		  miniRangee = null;
	  }	  
	  _miniColonnes.clear();
	  _miniColonnes = null;
  }

  public void addCase(Case carreau)
  {
    _bloc.add(carreau);
  }

  public ICase getCase(int pos)
  {
    return (ICase)_bloc.get(pos);
  }

  public ArrayList getCases()
  {
    return _bloc;
  }

  public void addMiniLigne(MiniRangee ligne)
  {
    if(!_miniLignes.contains(ligne))
      _miniLignes.add(ligne);
  }

  public void addMiniColonne(MiniRangee colonne)
  {
    if(!_miniColonnes.contains(colonne))
      _miniColonnes.add(colonne);
  }

  public MiniRangee getMiniLigne(int i)
  {
    return (MiniRangee)_miniLignes.get(i);
  }

  public MiniRangee getMiniColonne(int i)
  {
    return (MiniRangee) _miniColonnes.get(i);
  }

  public boolean update(ICase carreau)
  {
    boolean result = false;
    int value = carreau.getValue();
    if(value != 0)
    {
      for(int i = 0;i<_bloc.size();i++)
      {
        Case currentCarreau = (Case)_bloc.get(i);
        result = currentCarreau.change(0) || result;
      }
    }
    return result;
  }

  boolean verifMiniLignes()
  {
    boolean result = false;
    ICase premierCarreau = getCase(0);
    int firstValue = premierCarreau.getFirstValue();
    result = verifMiniRangees(_miniLignes,firstValue);
    return result;
  }

  boolean verifMiniColonnes()
  {
    boolean result = false;
    ICase premierCarreau = getCase(0);
    int firstValue = premierCarreau.getFirstValue();
    result = verifMiniRangees(_miniColonnes,firstValue);
    return result;
  }

  boolean verifMiniRangees(ArrayList miniRangees,int value)
  {
    boolean result = false;
    int nbRangees = 0;
    MiniRangee miniRangeePresent = null;
    for(int i=0;i<miniRangees.size();i++)
    {
      MiniRangee miniRangee = (MiniRangee)miniRangees.get(i);
      for(int j=0;j<miniRangee.getNbCases();j++)
      {
        Case carreau = (Case) miniRangee.getCase(j);
        if(carreau.getValue() == value)
        {
          nbRangees++;
          miniRangeePresent = miniRangee;
          break;
        }
      }
    }
    if(nbRangees == 1)
    {
      Rangee rangee = miniRangeePresent.getRangee();
      result = rangee.removeValue(miniRangeePresent) || result;
    }
    return result;
  }

  boolean removeValueColonne(MiniRangee miniRangeePresent)
  {
    return removeValue(_miniColonnes,miniRangeePresent);
  }

  boolean removeValueLigne(MiniRangee miniRangeePresent)
  {
    return removeValue(_miniLignes,miniRangeePresent);
  }

  boolean removeValue(ArrayList miniRangees, MiniRangee miniRangeePresent)
  {
    boolean result = false;
    for(int i=0;i<miniRangees.size();i++)
    {
      MiniRangee miniRangee = (MiniRangee) miniRangees.get(i);
      if(miniRangee == miniRangeePresent)
        continue;
      result = miniRangee.setValue(0) || result;
    }
    return result;
  }


}
