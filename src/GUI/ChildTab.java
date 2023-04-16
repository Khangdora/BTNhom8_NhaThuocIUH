package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Console;
import java.nio.file.attribute.AclEntry;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import DAO.NhanVien_DAO;
import DAO.Thuoc_DAO;
import connectDB.ConnectDB;
import entity.ThanhToan;
import entity.Thuoc;
import entity.ThuocMua;
import others.BottomBorder;
import others.RoundedBorder;

public class ChildTab implements ActionListener, MouseListener {
	
	private Thuoc_DAO thuoc_dao;
	private NhanVien_DAO nhanvien_dao;
	private JPanel listPanelHoaDon, addPanelHoaDon;
	private JTextField txtHoaDon, txtSoDienThoaiHD;
	private JButton btnLocHoaDon, btnNextHoaDon, btnPrevHoaDon;
	private DefaultTableModel modelHoaDon;
	private JTable tableHoaDon;
	private int limit = 25;
	
	private ArrayList<ThuocMua> dsthuocClick;
	private Component main;
	
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
		
		// ListPanel HoaDon
		listPanelHoaDon = new JPanel();
		myPanel.add(listPanelHoaDon, BorderLayout.CENTER);
		listPanelHoaDon.setPreferredSize(new Dimension(550, 580));
		listPanelHoaDon.setLayout(new BorderLayout());
		
		JPanel locPanel = new JPanel();
		locPanel.setPreferredSize(new Dimension(550, 50));
		
		Box box = Box.createHorizontalBox();
		box.add(txtHoaDon = new JTextField(10));
		box.add(Box.createHorizontalStrut(5));
		
		String[] donViBanStr = {"Tất cả", "Hộp", "Chai", "Vỉ", "Tuýp"};
		JComboBox<String> comboBoxDVB = new JComboBox<String>(donViBanStr);
		comboBoxDVB.setPreferredSize(new Dimension(65, 25));
		box.add(comboBoxDVB);
		comboBoxDVB.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		box.add(Box.createHorizontalStrut(5));
		
		ArrayList<String> xuatXuStr = thuoc_dao.getXuatXu();
		JComboBox<String> comboBoxXX = new JComboBox<String>();
		comboBoxXX.addItem("Tất cả");
		for(String s : xuatXuStr)
			comboBoxXX.addItem(s);
		box.add(comboBoxXX);
		comboBoxXX.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		box.add(Box.createHorizontalStrut(5));
		
		String[] dangBaoCheStr = {"Tất cả", "Dung dịch", "Viên sủi", "Bột", "Viên nén", "Viên nhộng", "Khác"};
		JComboBox<String> comboBoxDBC = new JComboBox<String>(dangBaoCheStr);
		box.add(comboBoxDBC);
		comboBoxDBC.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		box.add(Box.createHorizontalStrut(5));
		
		String[] sapXepStr = {"A-Z", "Z-A", "Mới-Cũ", "Cũ-Mới"};
		JComboBox<String> comboBoxSX = new JComboBox<String>(sapXepStr);
		box.add(comboBoxSX);
		comboBoxSX.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		box.add(Box.createHorizontalStrut(5));
		
		btnLocHoaDon = new JButton("Lọc");
		box.add(btnLocHoaDon);
		btnLocHoaDon.setBorder(BorderFactory.createCompoundBorder(btnLocHoaDon.getBorder(), new BottomBorder()));
		btnLocHoaDon.setFocusable(false);
		btnLocHoaDon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		locPanel.add(box);
		listPanelHoaDon.add(locPanel, BorderLayout.NORTH);
		
		//==== Table
		JPanel panelTable = new JPanel();
		panelTable.setLayout(new BorderLayout());
		
		String[] colHeader = {"Mã","Tên","Đơn vị bán","Số lượng","Xuất xứ","Dạng","Đơn giá"};
		
		modelHoaDon = new DefaultTableModel(colHeader, 0) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		
		tableHoaDon = new JTable(modelHoaDon);
		tableHoaDon.setRowHeight(20);
		
		JScrollPane sp = new JScrollPane(tableHoaDon, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		DefaultTableCellRenderer right = new DefaultTableCellRenderer();
		right.setHorizontalAlignment(JLabel.RIGHT);
		tableHoaDon.getColumnModel().getColumn(6).setCellRenderer(right);
		
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(JLabel.CENTER);
		tableHoaDon.getColumnModel().getColumn(0).setCellRenderer(center);
		tableHoaDon.getColumnModel().getColumn(2).setCellRenderer(center);
		tableHoaDon.getColumnModel().getColumn(3).setCellRenderer(center);
		tableHoaDon.getColumnModel().getColumn(4).setCellRenderer(center);
		tableHoaDon.getColumnModel().getColumn(5).setCellRenderer(center);
		
		tableHoaDon.getColumnModel().getColumn(1).setPreferredWidth(200);
		
		tableHoaDon.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		panelTable.add(sp, BorderLayout.CENTER);
		
		setDuLieuHoaDon(1);
		
		listPanelHoaDon.add(panelTable, BorderLayout.CENTER);
		
		JPanel panelPages = new JPanel();
		panelPages.setPreferredSize(new Dimension(550, 40));
		
		btnNextHoaDon = new JButton("❮");
		btnNextHoaDon.setFocusable(false);
		btnNextHoaDon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		int total = (int) Math.ceil(thuoc_dao.totalThuoc()*1.0/25);
		JComboBox<String> comboBoxPages = new JComboBox<String>();
		for (int i=1; i <= total; i++) {
			comboBoxPages.addItem(i+"");
		}
		comboBoxPages.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		btnPrevHoaDon = new JButton("❯");
		btnPrevHoaDon.setFocusable(false);
		btnPrevHoaDon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		panelPages.add(btnNextHoaDon);
		panelPages.add(comboBoxPages);
		panelPages.add(btnPrevHoaDon);
		
		
		listPanelHoaDon.add(panelPages, BorderLayout.SOUTH);
		
		// ThemPanel HoaDon
		addPanelHoaDon = new JPanel();
		myPanel.add(addPanelHoaDon, BorderLayout.EAST);
		addPanelHoaDon.setPreferredSize(new Dimension(340, 570));
		addPanelHoaDon.setBackground(Color.decode("#DDDDDD"));
		
		JPanel addPanelHoaDonNorth = new JPanel();
		addPanelHoaDonNorth.setOpaque(false);
		Box b = Box.createVerticalBox();
		
		Box b1, b2, b3;
		b.add(b1 = Box.createHorizontalBox());
		b1.add(new JLabel("Thêm hóa đơn".toUpperCase()));
		b.add(Box.createVerticalStrut(10));
		b.add(b2 = Box.createHorizontalBox());
		b2.add(new JLabel("Số điện thoại"));
		b2.add(Box.createHorizontalStrut(10));
		b2.add(txtSoDienThoaiHD = new JTextField(15));
		
		addPanelHoaDonNorth.add(b);
		addPanelHoaDon.add(addPanelHoaDonNorth, BorderLayout.NORTH);
		
//		private String maHoaDon;
//		private Date ngayMua;
//		private String maKhachHang;
//		private String maNhanVien;
//		private boolean dangHoaDon;
//		private ThanhToan thanhToan;
//		private ArrayList<ThuocMua> thuocMua;
		
		
		//Set Border 0
		listPanelHoaDon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		addPanelHoaDon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		tableHoaDon.addMouseListener(this);
		
		return myPanel;
	}
	
	public void setDuLieuHoaDon(int pages) {
		
		List<Thuoc> list = thuoc_dao.getPagesThuoc(pages);
		for(Thuoc thuoc : list) {
			
			DecimalFormat formatter = new DecimalFormat("#,###");
			
			modelHoaDon.addRow(new Object[] {thuoc.getMaThuoc(),thuoc.getTenThuoc(),thuoc.getDonViBan(),
					thuoc.getSoLuong(),thuoc.getXuatXu(),thuoc.getDangBaoChe(),formatter.format(thuoc.getDonGia())});
			
		}
		
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		Object o = e.getSource();
		
		// TODO Auto-generated method stub
        if (e.getClickCount() == 2 && o.equals(tableHoaDon)) {
            int row = tableHoaDon.getSelectedRow();
            String maThuoc = (String) tableHoaDon.getValueAt(row, 0);
            JOptionPane.showMessageDialog(main, maThuoc);
        }
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
