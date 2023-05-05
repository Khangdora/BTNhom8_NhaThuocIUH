package others;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MyTableCellRenderer extends DefaultTableCellRenderer {
   @Override
   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      
      // Kiểm tra hàng chẵn hoặc lẻ để xác định màu nền
      if (row % 2 == 0) {
         c.setBackground(Color.LIGHT_GRAY);
      } else {
         c.setBackground(Color.WHITE);
      }
      
      return c;
   }
}