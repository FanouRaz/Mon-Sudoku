package sudoku;

import java.util.ArrayList;

public class Rangee implements ICaseList
{

  private ArrayList _rangee;
  private ArrayList _miniRangee;
  private String _type;
  private int _size;

  public Rangee(String type, int size)
  {
    _size = size;
    _rangee = new ArrayList();
    _miniRangee = new ArrayList();
    for(int i=0;i<_size;i++)
    {
      MiniRangee miniRangee = new MiniRangee();
      _miniRangee.add(miniRangee);
      miniRangee.setRangee(this);
    }
    _type = type;
  }

  public void clear()
  {
	  for(int i=0;i<_miniRangee.size();i++)
	  {
		  MiniRangee miniRangee = (MiniRangee) _miniRangee.get(i);
		  miniRangee.clear();
		  miniRangee = null;
	  }	  
	  _miniRangee.clear();
	  _miniRangee = null;
	  for(int i=0;i<_rangee.size();i++)
	  {
		  Case carreau = (Case) _rangee.get(i);
		  carreau = null;
	  }	  
	  _rangee.clear();
	  _rangee = null;
  }
  
  public void addCase(Case carreau)
  {
    _rangee.add(carreau);
  }

  public ICase getCase(int i)
  {
    return (ICase) _rangee.get(i);
  }

  public ArrayList getCases()
  {
    return _rangee;
  }

  public MiniRangee getMiniRangee(int i)
  {
    return (MiniRangee) _miniRangee.get(i);
  }

  public boolean update(ICase carreau)
  {
    boolean result = false;
    int value = carreau.getValue();
    if(value != 0)
    {
      for(int i = 0;i<_rangee.size();i++)
      {
        Case currentCarreau = (Case)_rangee.get(i);
        result = currentCarreau.change(0) || result;
      }
    }
    return result;
  }

  boolean removeValue(MiniRangee miniRangeePresent)
  {
    boolean result = false;
    for(int i=0;i<_miniRangee.size();i++)
    {
      MiniRangee miniRangee = (MiniRangee) _miniRangee.get(i);
      if(miniRangee == miniRangeePresent)
        continue;
      result = miniRangee.setValue(0) || result;
    }
    return result;
  }

  boolean verifMiniRangees()
  {
    boolean result = false;
    int nbRangees = 0;
    ICase premierCarreau = getCase(0);
    int firstValue = premierCarreau.getFirstValue();
    MiniRangee miniRangeePresent = null;
    for(int i=0;i<_miniRangee.size();i++)
    {
      MiniRangee miniRangee = (MiniRangee)_miniRangee.get(i);
      for(int j=0;j<miniRangee.getNbCases();j++)
      {
        Case carreau = (Case) miniRangee.getCase(j);
        if(carreau.getValue() == firstValue)
        {
          nbRangees++;
          miniRangeePresent = miniRangee;
          break;
        }
      }
    }
    if(nbRangees == 1)
    {
      Carre carre = miniRangeePresent.getCarre();
      if(_type.equals("colonne"))
        result = carre.removeValueColonne(miniRangeePresent) || result;
      else
        result = carre.removeValueLigne(miniRangeePresent) || result;
    }
    return result;
  }

}
