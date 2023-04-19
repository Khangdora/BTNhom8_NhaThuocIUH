package others;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.AbstractBorder;

public class BottomBorder extends AbstractBorder {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	  public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
	    g.setColor(Color.decode("#EEEEEE"));
	    g.drawLine(x, y + height - 1, x + width, y + height - 1);
	  }

	  @Override
	  public Insets getBorderInsets(Component c) {
	    return new Insets(0, 0, 1, 0);
	  }

	  @Override
	  public Insets getBorderInsets(Component c, Insets insets) {
	    insets.bottom = 1;
	    return insets;
	  }
	  
}



