package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChildTab {
	
	public JPanel panelTrangChu() {
		JPanel myPanel = new JPanel();
		myPanel.setPreferredSize(new Dimension(817, 600));
		myPanel.setBackground(Color.decode("#EEEEEE"));
		myPanel.setBorder(null);
		myPanel.setLayout(null);
		
		JLabel lblWelcome, brand, quydinh, imageBacSi;
		myPanel.add(lblWelcome = new JLabel("Chào mừng đến với hệ thống"));
		lblWelcome.setBounds(270, 50, 500, 27);
		lblWelcome.setFont(new Font("Arial", Font.BOLD, 20));
		myPanel.add(brand = new JLabel("IUHOSPITAL"));
		brand.setBounds(330, 77, 500, 30);
		brand.setFont(new Font("Arial", Font.BOLD, 24));
		brand.setForeground(Color.red);
		
		myPanel.add(quydinh = new JLabel("<html>Tận tâm chăm sóc<br>Nhiệt tình lắng nghe</html>"));
		quydinh.setFont(new Font("Serif", Font.ITALIC, 18));
		quydinh.setBounds(100, 270, 200, 55);
		
		myPanel.add(imageBacSi = new JLabel(new ImageIcon(getClass().getResource("/img/bacsi.png"))));
		imageBacSi.setBounds(330, 70, 500, 600);
		
		
		
		
		return myPanel;
	}
	
	public JPanel panelHoaDon() {
		JPanel myPanel = new JPanel();
		myPanel.setPreferredSize(new Dimension(817, 600));
		myPanel.setBackground(Color.decode("#EEEEEE"));
		myPanel.setBorder(null);
		
		myPanel.add(new JLabel("HoaDon"));
		
		return myPanel;
	}
	
	public JPanel panelKhachHang() {
		JPanel myPanel = new JPanel();
		myPanel.setPreferredSize(new Dimension(817, 600));
		myPanel.setBackground(Color.decode("#EEEEEE"));
		myPanel.setBorder(null);
		
		myPanel.add(new JLabel("KhachHang"));
		
		return myPanel;
	}
	
	public JPanel panelKhoThuoc() {
		JPanel myPanel = new JPanel();
		myPanel.setPreferredSize(new Dimension(817, 600));
		myPanel.setBackground(Color.decode("#EEEEEE"));
		myPanel.setBorder(null);
		
		myPanel.add(new JLabel("KhoThuoc"));
		
		return myPanel;
	}
	
	public JPanel panelLichSu() {
		JPanel myPanel = new JPanel();
		myPanel.setPreferredSize(new Dimension(817, 600));
		myPanel.setBackground(Color.decode("#EEEEEE"));
		myPanel.setBorder(null);
		
		myPanel.add(new JLabel("LichSu"));
		
		return myPanel;
	}
	
	public JPanel panelNhanVien() {
		JPanel myPanel = new JPanel();
		myPanel.setPreferredSize(new Dimension(817, 600));
		myPanel.setBackground(Color.decode("#EEEEEE"));
		myPanel.setBorder(null);
		
		myPanel.add(new JLabel("NhanVien"));
		
		return myPanel;
	}
	
	public JPanel panelThongKe() {
		JPanel myPanel = new JPanel();
		myPanel.setPreferredSize(new Dimension(817, 600));
		myPanel.setBackground(Color.decode("#EEEEEE"));
		myPanel.setBorder(null);
		
		myPanel.add(new JLabel("ThongKe"));
		
		return myPanel;
	}

}
