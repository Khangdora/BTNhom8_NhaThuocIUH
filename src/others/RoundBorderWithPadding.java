package others;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.EmptyBorder;

public class RoundBorderWithPadding extends EmptyBorder {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Color color;
    private int radius;

    public RoundBorderWithPadding(Color color, int top, int left, int bottom, int right, int radius) {
        super(top, left, bottom, right);
        this.color = color;
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(color);
        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        int padding = radius / 2;
        return new Insets(padding, padding, padding, padding);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.right = insets.bottom = insets.top = radius / 2;
        return insets;
    }
}
