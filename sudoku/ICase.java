package sudoku;

import java.util.ArrayList;

public interface ICase {
	
	public abstract int getValue();

	public abstract int getFirstValue();
	
	public abstract ArrayList getPossibilites();
	
	public abstract int getRow();
	
	public abstract int getColumn();
}
