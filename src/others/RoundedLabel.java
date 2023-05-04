package others;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JLabel;
import javax.swing.border.AbstractBorder;

public class RoundedLabel extends JLabel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RoundedLabel(String text) {
        super(text);
        setOpaque(true);
        setBorder(new RoundBorder(15));
    }

    private static class RoundBorder extends AbstractBorder {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final int radius;

        public RoundBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(c.getBackground());
            g2d.fill(new RoundRectangle2D.Float(x, y, width - 1, height - 1, radius, radius));
            g2d.setColor(c.getForeground());
            g2d.draw(new RoundRectangle2D.Float(x, y, width - 1, height - 1, radius, radius));
            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius / 2, this.radius / 2, this.radius / 2, this.radius / 2);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = this.radius / 2;
            insets.top = insets.bottom = this.radius / 2;
            return insets;
        }
    }

}