package others;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JTextField;

public class PlaceholderTextField extends JTextField {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String placeholder;
	private boolean showingPlaceholder;
	
	public PlaceholderTextField(String placeholder) {
		  
	    this.placeholder = placeholder;
	    showingPlaceholder = true;
	    setForeground(Color.GRAY);
	    setText(placeholder);
	    setPreferredSize(new Dimension(250, 20));
	    
	    addFocusListener(new FocusAdapter() {
		    	
	      @Override
	      public void focusGained(FocusEvent e) {
		        if(showingPlaceholder) {
			          setText("");
			          setForeground(Color.BLACK);
			          showingPlaceholder = false;
		        }
	      }
	
	      @Override
	      public void focusLost(FocusEvent e) {
		        if (getText().isEmpty()) {
			          setText(placeholder);
			          setForeground(Color.GRAY);
			          showingPlaceholder = true;
		        }
	      }
	    });
	  }
	
	  @Override
	  public String getText() {
		  return showingPlaceholder ? "" : super.getText();
	  }
}
