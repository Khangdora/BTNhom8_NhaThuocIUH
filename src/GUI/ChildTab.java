package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.nio.file.attribute.AclEntry;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import DAO.NhanVien_DAO;
import DAO.Thuoc_DAO;
import connectDB.ConnectDB;

public class ChildTab {
	
	private Thuoc_DAO thuoc_dao;
	private NhanVien_DAO nhanvien_dao;
	private JPanel listPanelHoaDon, addPanelHoaDon;
	private JTextField txtHoaDon;
	
	public ChildTab() {
		// ====================
		
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		nhanvien_dao = new NhanVien_DAO();
		thuoc_dao = new Thuoc_DAO();
		
		// ====================
	}
	
	public JPanel panelTrangChu() {
		JPanel myPanel = new JPanel();
		myPanel.setPreferredSize(new Dimension(900, 600));
		myPanel.setBackground(Color.decode("#EEEEEE"));
		myPanel.setBorder(null);
		myPanel.setLayout(null);
		
		JLabel lblWelcome, brand, slogan, address, imageBacSi;
		myPanel.add(lblWelcome = new JLabel("Chào mừng đến với hệ thống"));
		lblWelcome.setBounds(320, 50, 500, 27);
		lblWelcome.setFont(new Font("Arial", Font.BOLD, 20));
		myPanel.add(brand = new JLabel("IUHOSPITAL"));
		brand.setBounds(380, 77, 500, 30);
		brand.setFont(new Font("Arial", Font.BOLD, 24));
		brand.setForeground(Color.red);
		
		myPanel.add(slogan = new JLabel("<html>Tận tâm chăm sóc<br>&ensp;&ensp;&ensp;&ensp;Nhiệt tình lắng nghe</html>"));
		slogan.setFont(new Font("Serif", Font.ITALIC, 18));
		slogan.setBounds(150, 170, 200, 55);
		
		myPanel.add(address = new JLabel("<html>NHÓM 8<br>Chủ tiệm thuốc: Trần Thị Ánh Thi"+
				"<br>Võ Trường Khang<br>Phạm Lê Thanh Nhiệt<br>Nguyễn Thế Lực<br>Nguyễn Đức Thắng</html>"));
		address.setFont(new Font("Serif", Font.PLAIN, 16));
		address.setBounds(90, 300, 250, 200);
		
		myPanel.add(imageBacSi = new JLabel(new ImageIcon(getClass().getResource("/img/bacsi.png"))));
		imageBacSi.setBounds(440, 60, 500, 600);
		
		return myPanel;
	}
	
	public JPanel panelHoaDon() {
		JPanel myPanel = new JPanel();
		myPanel.setPreferredSize(new Dimension(900, 600));
		myPanel.setBackground(Color.decode("#EEEEEE"));
		myPanel.setBorder(null);
		
		// ListPanel HoaDon
		listPanelHoaDon = new JPanel();
		myPanel.add(listPanelHoaDon, BorderLayout.CENTER);
		listPanelHoaDon.setPreferredSize(new Dimension(550, 600));
		listPanelHoaDon.setLayout(new BorderLayout());
		
		JPanel locPanel = new JPanel();
		locPanel.setPreferredSize(new Dimension(600,50));
		
		Box box = Box.createHorizontalBox();
		box.add(txtHoaDon = new JTextField(20));
		String[] donViBanStr = {"Hộp", "Chai", "Vỉ", "Tuýp"};
		JComboBox<String> comboBoxDVB = new JComboBox<String>(donViBanStr);
		comboBoxDVB.setPreferredSize(new Dimension(70, 25));
		box.add(comboBoxDVB);
		
		locPanel.add(box);
		listPanelHoaDon.add(locPanel, BorderLayout.NORTH);
		
		// ThemPanel HoaDon
		addPanelHoaDon = new JPanel();
		myPanel.add(addPanelHoaDon, BorderLayout.EAST);
		addPanelHoaDon.setPreferredSize(new Dimension(330, 600));
		
		addPanelHoaDon.add(new JLabel("Thêm Hóa Đơn"));
		
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
