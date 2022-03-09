package sudoku;

import java.util.ArrayList;

public interface ICaseList {
	
	public abstract boolean update(ICase carreau);
	
    public abstract ArrayList getCases();
    
    public ICase getCase(int i);

}
