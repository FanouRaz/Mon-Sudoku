package sudoku;

import java.util.ArrayList;

public class Diagonale implements ICaseList
{
  private ArrayList _rangee;
  // cette rangée est utilisée quand on ne teste pas la diagonale dans l'algo
  // de résolution. Elle n'est utilisée qu'en mode création quand le joueur a
  // selectionné cette condition supplémentaire
  private ArrayList _rangeeVide;
  // Rangee utilisée
  private ArrayList _rangeecourante;

  public Diagonale()
  {
    _rangee = new ArrayList();
    _rangeeVide = new ArrayList();
    _rangeecourante = _rangeeVide;
  }

  public void clear()
  {
	  for(int i=0;i<_rangee.size();i++)
	  {
		  Case carreau = (Case) _rangee.get(i);
		  carreau = null;
	  }	  
	  _rangee.clear();
	  _rangee = null;
	  for(int i=0;i<_rangeeVide.size();i++)
	  {
		  Case carreau = (Case) _rangeeVide.get(i);
		  carreau = null;
	  }	  
	  _rangeeVide.clear();
	  _rangeeVide = null;
	  _rangeecourante.clear();
	  _rangeecourante = null;
  }

  public void addCase(Case carreau)
  {
    _rangee.add(carreau);
  }

  public ICase getCase(int i)
  {
    return (ICase) _rangeecourante.get(i);
  }

  public ArrayList getCases()
  {
    return _rangeecourante;
  }

  public void switchRangee(boolean isDiagonale)
  {
	if(isDiagonale)
	  _rangeecourante = _rangee;
	else
	  _rangeecourante = _rangeeVide;
  }
  
  public boolean update(ICase carreau)
  {
    boolean result = false;
    int value = carreau.getValue();
    if(value != 0)
    {
      for(int i = 0;i<_rangeecourante.size();i++)
      {
        Case currentCarreau = (Case)_rangeecourante.get(i);
        result = currentCarreau.change(0) || result;
      }
    }
    return result;
  }
}