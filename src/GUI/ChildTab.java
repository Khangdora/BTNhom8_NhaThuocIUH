package GUI;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChildTab {
	
	public JPanel panelTrangChu() {
		JPanel myPanel = new JPanel();
		myPanel.setPreferredSize(new Dimension(830, 580));
		
		myPanel.add(new JLabel("TrangChu"));
		
		return myPanel;
	}
	
	public JPanel panelHoaDon() {
		JPanel myPanel = new JPanel();
		myPanel.setPreferredSize(new Dimension(830, 580));
		
		myPanel.add(new JLabel("HoaDon"));
		
		return myPanel;
	}
	
	public JPanel panelKhachHang() {
		JPanel myPanel = new JPanel();
		myPanel.setPreferredSize(new Dimension(830, 580));
		
		myPanel.add(new JLabel("KhachHang"));
		
		return myPanel;
	}
	
	public JPanel panelKhoThuoc() {
		JPanel myPanel = new JPanel();
		myPanel.setPreferredSize(new Dimension(830, 580));
		
		myPanel.add(new JLabel("KhoThuoc"));
		
		return myPanel;
	}
	
	public JPanel panelLichSu() {
		JPanel myPanel = new JPanel();
		myPanel.setPreferredSize(new Dimension(830, 580));
		
		myPanel.add(new JLabel("LichSu"));
		
		return myPanel;
	}
	
	public JPanel panelNhanVien() {
		JPanel myPanel = new JPanel();
		myPanel.setPreferredSize(new Dimension(830, 580));
		
		myPanel.add(new JLabel("NhanVien"));
		
		return myPanel;
	}
	
	public JPanel panelThongKe() {
		JPanel myPanel = new JPanel();
		myPanel.setPreferredSize(new Dimension(830, 580));
		
		myPanel.add(new JLabel("ThongKe"));
		
		return myPanel;
	}

}
