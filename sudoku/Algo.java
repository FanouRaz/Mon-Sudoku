package sudoku;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;

public class Algo {

  public class Tentative
  {
    public Case _carreau;
    public ArrayList _values;
    public int _value;
    public Tentative(Case carreau,int value)
    {
      _carreau = carreau;
      _values = new ArrayList();
      _values.add(new Integer(value));
      _value = value;
    }

    public String toString()
    {
      return new String("Tentative carreau : "+_carreau.getRow()+" "+_carreau.getColumn()+" valeur = "+_value);
    }
  }

  ArrayList _listeTentative;
  int isAuHasard;

  static Algo _algo = null;
  private Algo()
  {
    _listeTentative = new ArrayList();
  }

  public void init()
  {
    isAuHasard = 0;
    _listeTentative.clear();
  }

  static public Algo getInstance()
  {
    if(_algo == null)
      _algo = new Algo();
    return _algo;
  }

  int tryAlgoOtherCase(ArrayList tableau, int niveau, int sizeLine, int sizeColumn)
  {
    int result = 0;
    ArrayList choix = new ArrayList();
    for(int k=0;k<sizeLine*sizeColumn;k++)
    {
      Integer valeur = new Integer(k);
      choix.add(valeur);
    }
    // boucle sur les tableaux
    while(choix.size() > 0)
    {
      double random = Math.random();
      random*=choix.size();
      int index = (int)Math.floor(random);
      Integer newValue = (Integer)choix.remove(index);
      Tableau tab = (Tableau)tableau.get(newValue.intValue());
      // boucle sur les cases
      for(int j=0;j<sizeLine*sizeColumn*sizeLine*sizeColumn;j++)
      {
        Case carreau = (Case) tab.getCase(j);
        if(carreau.testOtherCase(niveau))
          result++;
      }
    }
    return result;
  }

  int tryAlgoDeBase(ArrayList tableau, int sizeLine, int sizeColumn)
  {
    boolean result = true;
    int boucle = 0;
    ArrayList choix = new ArrayList();
    while(result)
    {
      boucle++;
      result = false;
      for(int k=0;k<sizeLine*sizeColumn;k++)
      {
        Integer valeur = new Integer(k);
        choix.add(valeur);
      }
      // boucle sur les tableaux
      while(choix.size() > 0)
      {
        double random = Math.random();
        random*=choix.size();
        int index = (int)Math.floor(random);
        Integer newValue = (Integer)choix.remove(index);
        Tableau tab = (Tableau)tableau.get(newValue.intValue());
        // boucle sur les cases
        for(int j=0;j<sizeLine*sizeColumn*sizeLine*sizeColumn;j++)
        {
          Case carreau = (Case) tab.getCase(j);
          result = carreau.update() || result;
        }
      }
      result = verifGlobale(tableau,sizeLine,sizeColumn) || result;
    }
    return boucle;
  }

  private boolean verifGlobale(ArrayList tableau, int sizeLine, int sizeColumn)
  {
    boolean result = false;
    // boucle sur les tableaux
    for(int i=0;i<sizeLine*sizeColumn;i++)
    {
      Tableau tab = (Tableau)tableau.get(i);
      // boucle sur les cases
      for(int j=0;j<sizeLine*sizeColumn*sizeLine*sizeColumn;j++)
      {
        Case carreau = (Case) tab.getCase(j);
        if(! carreau.isLock())
          result = carreau.tryLock() || result;
      }
    }
    return result;
  }

  int tryAlgoMiniRangees(ArrayList tableau, int sizeLine, int sizeColumn)
  {
    int result = 0;
    // boucle sur les tableaux
    ArrayList choix = new ArrayList();
    for(int k=0;k<sizeLine*sizeColumn;k++)
    {
      Integer valeur = new Integer(k);
      choix.add(valeur);
    }
    // boucle sur les tableaux
    while(choix.size() > 0)
    {
      double random = Math.random();
      random*=choix.size();
      int index = (int)Math.floor(random);
      Integer newValue = (Integer)choix.remove(index);
      Tableau tab = (Tableau)tableau.get(newValue.intValue());
      // boucle sur les lignes
      for(int j=0;j<tab.getNbLignes();j++)
      {
        Rangee ligne = (Rangee) tab.getLigne(j);
        if(ligne.verifMiniRangees())
          result++;
      }
      // boucle sur les colonnes
      for(int j=0;j<tab.getNbColonnes();j++)
      {
        Rangee colonne = (Rangee) tab.getColonne(j);
        if(colonne.verifMiniRangees())
          result++;
      }
      // boucle sur les carres
      for(int j=0;j<tab.getNbCarres();j++)
      {
        Carre carre = (Carre) tab.getCarre(j);
        if(carre.verifMiniLignes())
          result++;
        if(carre.verifMiniColonnes())
          result++;
      }
    }
    return result;
  }

  int tryAlgoNUplesVisibles(int uples, ArrayList tableau)
  {
    int result = 0;
    // récupération du 1er tableau
    Tableau tab = (Tableau)tableau.get(0);
    // boucle sur les lignes
    for(int j=0;j<tab.getNbLignes();j++)
    {
      Rangee ligne = tab.getLigne(j);
      if(verifNUplesVisibles(ligne.getCases(),uples))
        result++;
    }
    // boucle sur les colonnes
    for(int j=0;j<tab.getNbColonnes();j++)
    {
      Rangee colonne = tab.getColonne(j);
      if(verifNUplesVisibles(colonne.getCases(),uples))
        result++;
    }
    // boucle sur les carres
    for(int j=0;j<tab.getNbCarres();j++)
    {
      Carre carre = tab.getCarre(j);
      if(verifNUplesVisibles(carre.getCases(),uples))
        result++;
    }
    // boucle sur les diagonales
    for(int j=0;j<tab.getNbDiagonales();j++)
    {
      Diagonale diagonale = tab.getDiagonale(j);
      if(verifNUplesVisibles(diagonale.getCases(),uples))
        result++;
    }
    return result;
  }

  int tryAlgoNUplesCaches(int uples, ArrayList tableau, int sizeLine, int sizeColumn)
  {
    int result = 0;
    // récupération du 1er tableau
    Tableau tab = (Tableau)tableau.get(0);
    // boucle sur les lignes
    for(int j=0;j<tab.getNbLignes();j++)
    {
      Rangee ligne = tab.getLigne(j);
      if(verifNUplesCaches(ligne.getCases(),uples,sizeLine,sizeColumn))
        result++;
    }
    // boucle sur les colonnes
    for(int j=0;j<tab.getNbColonnes();j++)
    {
      Rangee colonne = tab.getColonne(j);
      if(verifNUplesCaches(colonne.getCases(),uples,sizeLine,sizeColumn))
        result++;
    }
    // boucle sur les carres
    for(int j=0;j<tab.getNbCarres();j++)
    {
      Carre carre = tab.getCarre(j);
      if(verifNUplesCaches(carre.getCases(),uples,sizeLine,sizeColumn))
        result++;
    }
    // boucle sur les diagonales
    for(int j=0;j<tab.getNbDiagonales();j++)
    {
      Diagonale diagonale = tab.getDiagonale(j);
      if(verifNUplesCaches(diagonale.getCases(),uples,sizeLine,sizeColumn))
        result++;
    }
    return result;
  }

  private boolean verifNUplesVisibles(ArrayList cases, int uples)
  {
    boolean result = false;
    // map stocke pour chaque case la liste des chiffres qu'il possède
    HashMap map = new HashMap();
    // map qui dit pour un Integer donné à quelles cases il appartient
    HashMap modifyMap = new HashMap();
    // liste des cases qui ont les mêmes entiers
    ArrayList correspondants = new ArrayList();
    // on remplit map
    for(int i=0;i<cases.size();i++)
    {
      Case carreau = (Case) cases.get(i);
      ArrayList possibilites = carreau.getPossibilites();
      if(possibilites.size() == uples)
        map.put(carreau,possibilites);
    }
    while(map.size() != 0)
    {
      Iterator iter = map.keySet().iterator();
      // récupération de la case
      Case carreau = (Case)iter.next();
      // récupération des possibilités
      ArrayList possibilites = (ArrayList)map.get(carreau);
      // on retire cette case de la map (puisqu'elle va être traitée)
      map.remove(carreau);
      // on vide correspondants
      correspondants.clear();
      // on ajoute carreau à correspondant
      correspondants.add(carreau);
      // on crée une map intermédiaire
      HashMap mapIntermediaire = new HashMap(map);
      // boucle sur map
      Iterator iter1 = mapIntermediaire.keySet().iterator();
      while(iter1.hasNext())
      {
        // récupération de la case
        Case carreauRestant = (Case)iter1.next();
        // récupération des possibilités
        ArrayList possibilitesRestantes = (ArrayList)map.get(carreauRestant);
        if(possibilitesRestantes.containsAll(possibilites))
        {
          correspondants.add(carreauRestant);
          // on retire cette case de la map (puisqu'elle va être traitée)
          map.remove(carreauRestant);
        }
      }
      if(correspondants.size() == uples)
      {
        for(int i=0;i<possibilites.size();i++)
        {
          Integer valeur = (Integer) possibilites.get(i);
          modifyMap.put(valeur,new ArrayList(correspondants));
        }
      }
    }
    // boucle sur ModifyMap
    Iterator iter = modifyMap.keySet().iterator();
    while(iter.hasNext())
    {
      // récupération de la valeur
      Integer valeur = (Integer)iter.next();
      correspondants = (ArrayList) modifyMap.get(valeur);
      // on fait une boucle sur les cases passées en paramètre
      for(int i=0;i<cases.size();i++)
      {
        Case carreau = (Case) cases.get(i);
        if(! correspondants.contains(carreau))
          result = result || carreau.removeValue(valeur.intValue());
      }
    }
    return result;
  }

  private boolean verifNUplesCaches(ArrayList cases, int uples, int sizeLine, int sizeColumn)
  {
    boolean result = false;
    // map stocke pour chaque chiffre la liste des cases qui le possèdent
    HashMap map = new HashMap();
    // map qui dit pour un Integer donné à quelles cases il appartient
    HashMap modifyMap = new HashMap();
    // liste des cases qui ont les mêmes entiers
    ArrayList correspondants = new ArrayList();
    for(int i=1;i<(sizeLine*sizeColumn+1);i++)
    {
      Integer integer = new Integer(i);
      ArrayList listeDeCases = new ArrayList();
      // on remplit map
      for(int j=0;j<cases.size();j++)
      {
        Case carreau = (Case) cases.get(j);
        if(carreau.hasPossibilite(i))
          listeDeCases.add(carreau);
      }
      if(listeDeCases.size() == uples)
        map.put(integer,listeDeCases);
    }

    while(map.size() != 0)
    {
      Iterator iter = map.keySet().iterator();
      // récupération de l'entier
      Integer integer = (Integer)iter.next();
      // récupération des possibilités
      ArrayList listeDeCases = (ArrayList)map.get(integer);
      // on retire cette case de la map (puisqu'elle va être traitée)
      map.remove(integer);
      // on vide correspondants
      correspondants.clear();
      // on ajoute carreau à correspondant
      correspondants.add(integer);
      // on crée une map intermédiaire
      HashMap mapIntermediaire = new HashMap(map);
      // boucle sur map
      Iterator iter1 = mapIntermediaire.keySet().iterator();
      while(iter1.hasNext())
      {
        // récupération de la case
        Integer integerRestant = (Integer)iter1.next();
        // récupération des possibilités
        ArrayList listeDeCasesRestantes = (ArrayList)map.get(integerRestant);
        if(listeDeCasesRestantes.containsAll(listeDeCases))
        {
          correspondants.add(integerRestant);
          // on retire cette case de la map (puisqu'elle va être traitée)
          map.remove(integerRestant);
        }
      }
      if(correspondants.size() == uples)
      {
        for(int i=0;i<correspondants.size();i++)
        {
          Integer valeur = (Integer) correspondants.get(i);
          modifyMap.put(valeur,new ArrayList(listeDeCases));
        }
      }
    }
    // boucle sur ModifyMap
    Iterator iter = modifyMap.keySet().iterator();
    while(iter.hasNext())
    {
      // récupération de la valeur
      Integer valeur = (Integer)iter.next();
      correspondants = (ArrayList) modifyMap.get(valeur);
      // on fait une boucle sur les cases passées en paramètre
      for(int i=0;i<cases.size();i++)
      {
        Case carreau = (Case) cases.get(i);
        if(! correspondants.contains(carreau))
          result = result || carreau.removeValue(valeur.intValue());
        // si la case possède bien cette valeur
        else
        {
          ArrayList listeInteger = (ArrayList) map.get(carreau);
          if(listeInteger == null)
            listeInteger = new ArrayList();
          listeInteger.add(valeur);
          map.put(carreau,listeInteger);
        }
      }
    }
    // boucle sur ModifyMap
    iter = map.keySet().iterator();
    while(iter.hasNext())
    {
      // récupération de la case
      Case carreau = (Case)iter.next();
      // récupération des possibilités
      ArrayList possibilitesRestantes = (ArrayList)map.get(carreau);
      for(int i=1;i<(sizeLine*sizeColumn+1);i++)
      {
        Integer integerRestant = new Integer(i);
        if(! possibilitesRestantes.contains(integerRestant))
          carreau.removeValue(i);
      }
    }
    return result;
  }

  boolean tryAlgoAuHasard(ArrayList tableau, int sizeLine, int sizeColumn)
  {
    /// si on a déjà fait une hypothèse et qu'elle est merdique il faut tout re-nettoyer
    if(isAuHasard > 0 && testCoherence(tableau).size() > 0)
    {
      impasseDemiTour(tableau,sizeLine,sizeColumn);
      boolean isFind = false;
      while(!isFind && isAuHasard > 0)
      {
        Tentative tentative = (Tentative)_listeTentative.get(isAuHasard-1);
        Case carreau = tentative._carreau;
        _listeTentative.remove(tentative);
        int newValue = carreau.getValeurDiffOfList(tentative._values);
        if(newValue != -1)
        {
          carreau.tryNewValue(newValue);
          tentative._value = newValue;
          tentative._values.add(new Integer(newValue));
          _listeTentative.add(tentative);
          isFind = true;
          return true;
        }
        else
        {
          isAuHasard--;
          if(isAuHasard > 0)
          {
            impasseDemiTour(tableau,sizeLine,sizeColumn);
          }
        }
      }
      if(isAuHasard == 0)
        return false;
    }
    isAuHasard++;
    /// sinon on fait une hypothèse sur une nouvelle case
    Tableau tab = (Tableau)tableau.get(0);
    boolean isNouvelleTentative = false;
    boolean find = false;
    int nbMinimal = 1;
    while(!find)
    {
      find = true;
      nbMinimal++;
      for(int i=0;i<tab.getNbCases() && ! isNouvelleTentative;i++)
      {
        Case carreau = (Case) tab.getCase(i);
        ArrayList possibilites = carreau.getPossibilites();
        if(possibilites.size() == nbMinimal)
        {
          boolean isOn = false;
          for(int j=0;j<_listeTentative.size();j++)
          {
            Tentative ancienneTentative = (Tentative)_listeTentative.get(j);
            if(carreau == ancienneTentative._carreau)
            {
              isOn = true;
              break;
            }
          }
          if(! isOn)
          {
            double choix = Math.random();
            choix*=possibilites.size();
            int index = (int)Math.floor(choix);
            Integer newValue = (Integer)possibilites.get(index);
            carreau.tryNewValue(newValue.intValue());
            Tentative nouvelleTentative = new Tentative(carreau,newValue.intValue());
            _listeTentative.add(nouvelleTentative);
            isNouvelleTentative = true;
          }
        }
        else
          if( possibilites.size() > nbMinimal)
            find = false;
      }
    }
    return isNouvelleTentative;
  }

  private void impasseDemiTour(ArrayList tableau, int sizeLine, int sizeColumn)
  {
    for(int j=0;j<sizeLine*sizeColumn;j++)
    {
      Tableau tab = (Tableau)tableau.get(j);
      for(int i=0;i<tab.getNbCases();i++)
      {
        Case carreau = (Case) tab.getCase(i);
        // Notre hypothèse de départ était fausse
        if(carreau.getAuHasard() == isAuHasard)
          carreau.undoAuHasard();
      }
    }
  }

  public ArrayList testCoherence(ArrayList tableau)
  {
    ArrayList incoherence = new ArrayList();
    // récupération du 1er tableau
    Tableau tab = (Tableau) tableau.get(0);
    HashMap mapDefinitive = new HashMap();
    // vérifie que dans une ligne il n'y a pas la 2 fois même valeur
    for(int j=0;j<tab.getNbLignes();j++)
    {
      ICaseList rangee = (ICaseList)tab.getLigne(j);
      testCoherenceICaseList(rangee,incoherence,mapDefinitive);
    }
    // vérifie que dans une colonne il n'y a pas la 2 fois même valeur
    for(int j=0;j<tab.getNbColonnes();j++)
    {
      ICaseList rangee = (ICaseList)tab.getColonne(j);
      testCoherenceICaseList(rangee,incoherence,mapDefinitive);
    }
    // vérifie que dans un carré il n'y a pas la 2 fois même valeur
    for(int j=0;j<tab.getNbCarres();j++)
    {
      ICaseList carre = (ICaseList)tab.getCarre(j);
      testCoherenceICaseList(carre,incoherence,mapDefinitive);
    }
    // vérifie que dans une diagonale il n'y a pas deux fois la même valeur
    for(int j=0;j<tab.getNbDiagonales();j++)
    {
      ICaseList diagonale = (ICaseList)tab.getDiagonale(j);
      testCoherenceICaseList(diagonale,incoherence,mapDefinitive);
    }
    for(int i=0;i<tab.getNbCases();i++)
    {
      Case carreau = (Case) tab.getCase(i);
      // Notre hypothèse de départ était fausse
      if(carreau.getPossibilites().size() == 0)
        incoherence.add(carreau);
    }
    return incoherence;
  }
  
  private void testCoherenceICaseList(ICaseList iCaseList,ArrayList incoherence, HashMap mapDefinitive)
  {
	  mapDefinitive.clear();
      for(int k=0;k<iCaseList.getCases().size();k++)
      {
        ICase carreau = (ICase) iCaseList.getCase(k);
        ArrayList liste = carreau.getPossibilites();
        if(liste.size() == 1)
        {
          Integer integer = (Integer) liste.get(0);
          if(mapDefinitive.containsKey(integer))
          {
            Case carreauOld = (Case) mapDefinitive.get(integer);
            if(!incoherence.contains(carreauOld))
              incoherence.add(carreauOld);
            incoherence.add(carreau);
          }
          else
        	mapDefinitive.put(integer,carreau);
        }
      }
  }
}
