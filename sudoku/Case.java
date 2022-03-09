package sudoku;

import java.util.ArrayList;

public class Case implements ICase
{
  private ArrayList _casesAutresTableaux;
  private ArrayList _iCaseListArrayList;
  private Carre _carre;
  private Rangee _ligne;
  private MiniRangee _miniLigne;
  private Rangee _colonne;
  private MiniRangee _miniColonne;
  private Diagonale _diagonale1;
  private Diagonale _diagonale2;
  private int _value;
  private boolean _toUpdate;
  private boolean _lock;
  private boolean _testOtherCaseIsDone;
  private int _Nligne;
  private int _Ncolonne;
  private int _firstvalue;
  private int _isAuHasard;
  private int _finalValue;
  private int _sizeLine;
  private int _sizeColumn;

  public Case(Carre carre, Rangee ligne, MiniRangee miniLigne, Rangee colonne, MiniRangee miniColonne,Diagonale diagonale1,Diagonale diagonale2,int value,int Nligne,int Ncolonne, int sizeLine, int sizeColumn)
  {
    _sizeLine = sizeLine;  
    _sizeColumn = sizeColumn;  
    _casesAutresTableaux = new ArrayList();
    _iCaseListArrayList = new ArrayList();
    _carre = carre;
    _iCaseListArrayList.add(_carre);
    _ligne = ligne;
    _iCaseListArrayList.add(_ligne);
    _miniLigne = miniLigne;
    _colonne = colonne;
    _iCaseListArrayList.add(_colonne);
    _miniColonne = miniColonne;
    _diagonale1 = diagonale1;
    if(_diagonale1 != null)
    {
      _diagonale1.addCase(this);
      _iCaseListArrayList.add(_diagonale1);
    }
    _diagonale2 = diagonale2;
    if(_diagonale2 != null)
    {
      _diagonale2.addCase(this);
      _iCaseListArrayList.add(_diagonale2);
    }
    _carre.addCase(this);
    _ligne.addCase(this);
    _colonne.addCase(this);
    _miniLigne.addCase(this);
    _miniColonne.addCase(this);
    _firstvalue = value;
    _Nligne = Nligne;
    _Ncolonne = Ncolonne;
    init();
  }

  public void clear()
  {
	  for(int i=0;i<_casesAutresTableaux.size();i++)
	  {
		  Case carreau = (Case) _casesAutresTableaux.get(i);
		  carreau = null;
	  }	  
	  _casesAutresTableaux.clear();
	  _casesAutresTableaux = null;
	  for(int i=0;i<_iCaseListArrayList.size();i++)
	  {
		  ICaseList iCaseList = (ICaseList)_iCaseListArrayList.get(i);
		  iCaseList = null;
	  }	  
	  _iCaseListArrayList.clear();
	  _iCaseListArrayList = null;
	  _carre = null;
	  _ligne = null;
	  _colonne = null;
	  _diagonale1 = null;
	  _diagonale2 = null;
	  _miniLigne = null;
	  _miniColonne = null;
  }
  
  public void init()
  {
    _value = _firstvalue;
    _finalValue = _firstvalue;
    _lock = false;
    _toUpdate = false;
    _testOtherCaseIsDone = true;
    _isAuHasard = 0;
  }

  public void switchToFinalValue()
  {
    _finalValue = _value;
  }

  void startAgain()
  {
    _value = _firstvalue;
    _lock = false;
    _toUpdate = false;
    _testOtherCaseIsDone = true;
    _isAuHasard = 0;
  }

  public int getRow()
  {
    return _Nligne-1;
  }

  public int getColumn()
  {
    return _Ncolonne-1;
  }

  public boolean change(int value)
  {
    if(!_lock)
    {
      _value = value;
      if(_value != 0)
        sudoku.m_Resultats.add(this);
      _toUpdate = true;
      _lock = true;
      _testOtherCaseIsDone = false;
      _isAuHasard = Algo.getInstance().isAuHasard;
    }
    return _toUpdate;
  }

  public int getFinalValue()
  {
    return _finalValue;
  }

  public int findFinalValue()
  {
    if(_finalValue != 0)
      return _finalValue;
    else
    {
      for(int i=0;i<_casesAutresTableaux.size();i++)
      {
        Case currentCase = (Case) _casesAutresTableaux.get(i);
        int value = currentCase.getFinalValue();
        if(value != 0)
          return value;
      }
    }
    return 0;
  }

  public int getAuHasard()
  {
    return _isAuHasard;
  }

  public void undoAuHasard()
  {
    _lock = false;
    _testOtherCaseIsDone = true;
    if(_value != 0)
      sudoku.m_Resultats.remove(this);
    _value = _firstvalue;
    _isAuHasard = 0;
  }

  public boolean isLock()
  {
    return _lock;
  }

  public boolean update()
  {
    boolean result = false;
    if(_toUpdate)
    {
      _toUpdate = false;
      for(int i=0;i<_iCaseListArrayList.size();i++)
      {
        ICaseList iCaseList = (ICaseList)_iCaseListArrayList.get(i);
        result = (iCaseList.update(this)|| result);
      }
    }
    return result;
  }

  public int getValue()
  {
    return _value;
  }

  public int getFirstValue()
  {
    return _firstvalue;
  }

  public void addCases(Case carreau)
  {
    _casesAutresTableaux.add(carreau);
  }

  public boolean testOtherCase(int niveau)
  {
    if(_testOtherCaseIsDone)
      return false;
    _testOtherCaseIsDone = true;
    boolean result = false;
    if(_value != 0)
    {
      /// si on vient de fixer la valeur en dur != 0, c'est que toutes les
      /// autres posssibilités sont impossibles
      for(int i=0;i<_casesAutresTableaux.size();i++)
      {
        Case currentCase = (Case) _casesAutresTableaux.get(i);
        result = currentCase.change(0) || result;
      }
    }
    else if(niveau != 1)
    {
      /// si on vient d'annuler une possibilité _value == 0
      /// on regarde s'il ne reste plus qu'une possibilité
      /// et on la fixe en dur si c'est le cas
      int pos = -1;
      for(int i=0;i<_casesAutresTableaux.size();i++)
      {
        Case currentCase = (Case)_casesAutresTableaux.get(i);
        if(currentCase.getValue() != 0) //! currentCase.isLock() &&
        {
          if(pos != -1)
          {
            pos = -1;
            break;
          }
          pos = i;
        }
      }
      if(pos != -1)
      {
        Case currentCase = (Case)_casesAutresTableaux.get(pos);
        int value = currentCase.getValue();
        result = currentCase.change(value);
      }
    }
    return result;
  }

  public void setInitialValue(int value)
  {
    if(_value == value)
      change(value);
    else
      change(0);
    for(int i=0;i<_casesAutresTableaux.size();i++)
    {
      Case currentCase = (Case)_casesAutresTableaux.get(i);
      if(currentCase.getValue() == value)
        currentCase.change(value);
      else
        currentCase.change(0);
    }
  }

  /// retourne la valeur finale pour cette case si elle est déterminée sinon
  /// renvoie une valeur < 0 correspondant au nombre de valeurs indeterminées
  public int getFinalResolutionValue()
  {
    int value = _value;
    int nbIndetermines = 0;
//    if(value!=0)
  //  	System.out.println("getFinalResolutionValue ["+_Nligne+","+_Ncolonne+"]="+value);
    for(int i=0;i<_casesAutresTableaux.size();i++)
    {
      Case currentCase = (Case)_casesAutresTableaux.get(i);
      if(currentCase.getValue() != 0)
      {
    //  	System.out.println("getFinalResolutionValue ["+_Nligne+","+_Ncolonne+"]="+currentCase.getValue());
        if(value == 0)
          value = currentCase.getValue();
        else
          nbIndetermines--;
      }
    }
    if(nbIndetermines<0)
      return nbIndetermines;
    if(value == 0)
    	return -1;
    return value;
  }

  boolean removeValue(int value)
  {
    if(_firstvalue == value)
      return change(0);
    for(int i=0;i<_casesAutresTableaux.size();i++)
    {
      Case currentCase = (Case)_casesAutresTableaux.get(i);
      if(currentCase.getFirstValue() == value)
        return currentCase.change(0);
    }
    return false;
  }

  public void tryNewValue(int value)
  {
    if(_firstvalue == value)
      change(_firstvalue);
    else
      change(0);
    for(int i=0;i<_casesAutresTableaux.size();i++)
    {
      Case currentCase = (Case)_casesAutresTableaux.get(i);
      if(currentCase.getFirstValue() == value)
        currentCase.change(value);
      else
        currentCase.change(0);
    }
  }

  // retourne la liste des possibilites
  public ArrayList getPossibilites()
  {
    ArrayList possibilites = new ArrayList();
    if(_value != 0)
      possibilites.add(new Integer(_value));
    for(int i=0;i<_casesAutresTableaux.size();i++)
    {
      Case currentCase = (Case)_casesAutresTableaux.get(i);
      if(currentCase.getValue() != 0)
        possibilites.add(new Integer(currentCase.getValue()));
    }
    return possibilites;
  }

  public int getValeurDiffOfList(ArrayList values)
  {
    ArrayList possibilites = new ArrayList();
    if(_value > 0)
    {
      Integer integer = new Integer(_value);
      if(! values.contains(integer))
        possibilites.add(integer);
    }
    for(int i=0;i<_casesAutresTableaux.size();i++)
    {
      Case currentCase = (Case)_casesAutresTableaux.get(i);
      int value = currentCase.getValue();
      if(value>0)
      {
        Integer integer = new Integer(value);
        if(! values.contains(integer))
          possibilites.add(integer);
      }
    }
    if(possibilites.size() == 0)
      return -1;
    double choix = Math.random();
    choix*=possibilites.size();
    int index = (int)Math.floor(choix);
    Integer integer = (Integer) possibilites.get(index);
    return integer.intValue();
  }

  boolean hasPossibilite(int value)
  {
    if(_value == value)
      return true;
    for(int i=0;i<_casesAutresTableaux.size();i++)
    {
      Case currentCase = (Case)_casesAutresTableaux.get(i);
      if(currentCase.getValue() == value)
        return true;
    }
    return false;
  }

  public boolean tryLock()
  {
    int nbZero = 0;
    // test de ligne
    for(int i=0;i<_sizeLine*_sizeColumn;i++)
    {
      Case currentCase = (Case)_ligne.getCase(i);
      if(currentCase.getValue() == 0)
        nbZero++;
    }
    if(nbZero == (_sizeLine*_sizeColumn-1))
    {
      change(_value);
      return true;
    }

    // test de colonne
    nbZero = 0;
    for(int i=0;i<_sizeLine*_sizeColumn;i++)
    {
      Case currentCase = (Case)_colonne.getCase(i);
      if(currentCase.getValue() == 0)
        nbZero++;
    }
    if(nbZero == (_sizeLine*_sizeColumn-1))
    {
      change(_value);
      return true;
    }

    // test de carre
    nbZero = 0;
    for(int i=0;i<_sizeLine*_sizeColumn;i++)
    {
      Case currentCase = (Case)_carre.getCase(i);
      if(currentCase.getValue() == 0)
        nbZero++;
    }
    if(nbZero == (_sizeLine*_sizeColumn-1))
    {
      change(_value);
      return true;
    }

    return false;
  }
}
