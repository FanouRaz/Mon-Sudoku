package sudoku;

import java.awt.*;

public class Police {

  private Font  _Font;
  private Color _BackColor;
  private Color _ForeColor;

  public Police(Font font, Color backColor, Color foreColor)
  {
    _Font = new Font(font.getName(),font.getStyle(),font.getSize());
    _BackColor = backColor;
    _ForeColor = foreColor;
  }

  public void setFont(Font font)
  {
    _Font = font;
  }

  public Font getFont()
  {
    return _Font;
  }

  public void setBackColor(Color backColor)
  {
    _BackColor = backColor;
  }

  public Color getBackColor()
  {
    return _BackColor;
  }

  public void setForeColor(Color foreColor)
  {
    _ForeColor = foreColor;
  }

  public Color getForeColor()
  {
    return _ForeColor;
  }
}