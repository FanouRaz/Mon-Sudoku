package sudoku;

import javax.swing.filechooser.*;
import java.io.File;

public class Philtre extends FileFilter{

  public Philtre() {
  }

  public boolean accept(File f)
  {
    if(f.getAbsolutePath().endsWith(".sud"))
      return true;
    else if(f.isDirectory())
      return true;
    return false;
  }
  public String getDescription()
  {
    return ".sud";
  }
}