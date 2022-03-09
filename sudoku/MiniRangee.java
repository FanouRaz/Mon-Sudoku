package sudoku;

import java.util.ArrayList;

/// rangée de trois cases
public class MiniRangee
{
  private Rangee _rangee;
  private Carre _carre;
  ArrayList _cases;

  public MiniRangee()
  {
    _cases = new ArrayList();
  }

  public void clear()
  {
	  _rangee = null;
	  _carre = null;
	  for(int i=0;i<_cases.size();i++)
	  {
		  Case carreau = (Case) _cases.get(i);
		  carreau = null;
	  }	 
	  _cases.clear();
  }
  
  void setCarre(Carre carre)
  {
    _carre = carre;
  }

  Carre getCarre()
  {
    return _carre;
  }

  void setRangee(Rangee rangee)
  {
    _rangee = rangee;
  }

  Rangee getRangee()
  {
    return _rangee;
  }

  void addCase(Case carreau)
  {
    _cases.add(carreau);
  }

  int getNbCases()
  {
    return _cases.size();
  }

  Case getCase(int i)
  {
    return (Case) _cases.get(i);
  }

  boolean setValue(int value)
  {
    boolean result = false;
    for(int i=0;i<_cases.size();i++)
    {
      Case carreau = (Case) _cases.get(i);
      result = carreau.change(value) || result;
    }
    return result;
  }
}
