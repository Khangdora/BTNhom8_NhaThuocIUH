package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import DAO.HoaDon_DAO;
import DAO.KhachHang_DAO;
import DAO.NhanVien_DAO;
import DAO.Thuoc_DAO;
import connectDB.ConnectDB;
import entity.CT_HoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.Thuoc;
import others.BottomBorder;
import others.RoundedBorder;

public class ChildTab implements ActionListener, MouseListener, KeyListener, DocumentListener, TableModelListener {
	
	private Thuoc_DAO thuoc_dao;
	private NhanVien_DAO nhanvien_dao;
	private HoaDon_DAO hoadon_dao;
	private KhachHang_DAO khachhang_dao;
	
	private JPanel listPanelHoaDon, addPanelHoaDon;
	
	private JTextField txtHoaDon, txtSDT_HD, txtmaKH_HD, txthotenKH_HD;
	
	private JLabel lblTBHD, lblTongTienHD;
	
	private JButton btnLocHoaDon, btnNextHoaDon, btnPrevHoaDon, btnLuuHoaDon, btnThanhToan;
	
	private DefaultTableModel modelHoaDon, modelChiTietHoaDon;
	private JTable tableHoaDon, tableChiTietHoaDon;
	
	private int limit = 25;
	
	private ArrayList<CT_HoaDon> listCTHoaDon;
	
	//Khach Hang
	private JPanel listPanelKhachHang, addPanelKhachHang;
	private JTextField txtmaKH_KH, txtho_KH, txtTen_KH, txtSDT_KH, txtEmail_KH, txtgioiTinh_KH, txtLocTuKhoa_KH;
	private JLabel lblTBKH, lblLocTuKhoa_KH, lblLocTen_KH;
	private JButton btnLocKH, btnNextKH, btnPrevKH, btnLuuKH, btnThemKH, btnXoaKH, btnSuaKH, btnRefresh, btnXoaKH_csdl, btnSuaKH_csdl, btnXoaTrangKH;
	private DefaultTableModel modelKH, modelKH_temp;
	private JTable tableKH, tableKH_temp;
	private JComboBox<String> comboBox_SortHoTen, comboBox_SortGioiTinh, comboBoxGioiTinh;
	private JRadioButton radSortNam, radSortNu;
	private ArrayList<KhachHang> listKH;
	
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
		hoadon_dao = new HoaDon_DAO();
		khachhang_dao = new KhachHang_DAO();
		listKH = new ArrayList<>();
		
		// ====================
	}
	
	public JPanel panelTrangChu() {
		JPanel myPanel = new JPanel();
		myPanel.setPreferredSize(new Dimension(900, 600));
		myPanel.setBackground(Color.decode("#EEEEEE"));
		myPanel.setBorder(null);
		myPanel.setLayout(null);
		
		listCTHoaDon = new ArrayList<CT_HoaDon>();
		
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
		txtHoaDon.setText(thuoc_dao.maThuocAuto());
		
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
		
		String[] sapXepStr = {"Mới-Cũ", "Cũ-Mới"};
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
		
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(JLabel.CENTER);
		tableHoaDon.getColumnModel().getColumn(0).setCellRenderer(center);
		tableHoaDon.getColumnModel().getColumn(1).setPreferredWidth(200);
		tableHoaDon.getColumnModel().getColumn(2).setCellRenderer(center);
		tableHoaDon.getColumnModel().getColumn(3).setCellRenderer(center);
		tableHoaDon.getColumnModel().getColumn(4).setCellRenderer(center);
		tableHoaDon.getColumnModel().getColumn(5).setCellRenderer(center);
		tableHoaDon.getColumnModel().getColumn(6).setCellRenderer(render);
		
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
		
		Box b1, b2, b3, b4, b5;
		JTextField txtmaHoaDon;
		
		b.add(b1 = Box.createHorizontalBox());
		b1.add(new JLabel("Thêm hóa đơn".toUpperCase()));
		b.add(Box.createVerticalStrut(10));
		b.add(b2 = Box.createHorizontalBox());
		b2.add(Box.createHorizontalStrut(5));
		b2.add(new JLabel("Mã HD"));
		b2.add(Box.createHorizontalStrut(5));
		b2.add(txtmaHoaDon = new JTextField(hoadon_dao.maHoaDonAuto(), 5));
		txtmaHoaDon.setEnabled(false);
		b2.add(Box.createHorizontalStrut(10));
		b2.add(new JLabel("Số điện thoại"));
		b2.add(Box.createHorizontalStrut(5));
		b2.add(txtSDT_HD = new JTextField(15));
		txtSDT_HD.setName("sodienthoai_hoadon");
		b2.add(Box.createHorizontalStrut(5));
		
		b.add(b3 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(5));
		b3.add(Box.createHorizontalStrut(5));
		b3.add(new JLabel("Mã KH"));
		b3.add(Box.createHorizontalStrut(5));
		b3.add(txtmaKH_HD = new JTextField(5));
		txtmaKH_HD.setEditable(false);
		b3.add(Box.createHorizontalStrut(10));
		b3.add(new JLabel("Họ tên KH"));
		b3.add(Box.createHorizontalStrut(5));
		b3.add(txthotenKH_HD = new JTextField(15));
		txthotenKH_HD.setEditable(false);
		b3.add(Box.createHorizontalStrut(5));
		
		b.add(b4 = Box.createHorizontalBox());
		b4.add(Box.createHorizontalStrut(5));	
		JComboBox<String> comboBoxDBC_HD = new JComboBox<String>(new String[] {"Kê đơn", "Không kê đơn"});
		b4.add(comboBoxDBC_HD);
		comboBoxDBC_HD.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));	
		b4.add(Box.createHorizontalStrut(5));
		
		b.add(b5 = Box.createHorizontalBox());
		b5.add(Box.createHorizontalStrut(5));
		b5.add(lblTBHD = new JLabel());
		b5.add(Box.createHorizontalStrut(5));
		
		addPanelHoaDonNorth.add(b);
		addPanelHoaDon.add(addPanelHoaDonNorth, BorderLayout.NORTH);
		
		
		JPanel addPanelHoaDonCenter = new JPanel();
//		addPanelHoaDonCenter.setPreferredSize(new Dimension(300, 400));
		
		String[] colHeaderCTHD = {"STT","Tên","Đơn giá","Số lượng","Tổng tiền"};
		
		modelChiTietHoaDon = new DefaultTableModel(colHeaderCTHD, 0) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
		        return (column == 3);
		    }
		};
		
		tableChiTietHoaDon = new JTable(modelChiTietHoaDon);
		tableChiTietHoaDon.setRowHeight(20);
		
		tableChiTietHoaDon.getColumnModel().getColumn(0).setCellRenderer(center);
		tableChiTietHoaDon.getColumnModel().getColumn(0).setPreferredWidth(30);
		tableChiTietHoaDon.getColumnModel().getColumn(1).setPreferredWidth(150);
		tableChiTietHoaDon.getColumnModel().getColumn(2).setCellRenderer(render);
		tableChiTietHoaDon.getColumnModel().getColumn(3).setCellRenderer(center);
		tableChiTietHoaDon.getColumnModel().getColumn(4).setCellRenderer(render);
		
		JScrollPane spCTHD = new JScrollPane(tableChiTietHoaDon, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		spCTHD.setPreferredSize(new Dimension(320, 350));
		
		addPanelHoaDonCenter.add(spCTHD, BorderLayout.CENTER);
		
		addPanelHoaDon.add(addPanelHoaDonCenter, BorderLayout.CENTER);
		
		
		JPanel addPanelHoaDonSouth = new JPanel();
		addPanelHoaDonSouth.setOpaque(false);		
		
		Box bSouth = Box.createVerticalBox();
		Box b1South = Box.createHorizontalBox();
		Box b2South = Box.createHorizontalBox();
		
		bSouth.add(b1South);
		b1South.add(Box.createHorizontalGlue());
		b1South.add(lblTongTienHD = new JLabel("<html>Tổng tiền: <b><font color='red'>0</font></b>₫</html>"));
		lblTongTienHD.setFont(new Font("Arial", Font.PLAIN, 17));
		b1South.add(Box.createHorizontalGlue());
		
		bSouth.add(Box.createVerticalStrut(20));
		
		bSouth.add(b2South);
		b2South.add(btnLocHoaDon = new JButton("Lưu"));
		b2South.add(Box.createHorizontalStrut(10));
		b2South.add(btnThanhToan = new JButton("Thanh toán"));
		
		addPanelHoaDonSouth.add(bSouth);
		
		addPanelHoaDon.add(addPanelHoaDonSouth, BorderLayout.SOUTH);
		
		//Set Border 0
		listPanelHoaDon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		addPanelHoaDon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		tableHoaDon.addMouseListener(this);
		txtSDT_HD.addKeyListener(this);
		txtSDT_HD.getDocument().putProperty("owner", txtSDT_HD);
		txtSDT_HD.getDocument().addDocumentListener(this);
		modelChiTietHoaDon.addTableModelListener(this);
		
		return myPanel;
	}

	public JPanel panelKhachHang() {
//		return myPanel;
		JPanel myPanel = new JPanel();
		myPanel.setPreferredSize(new Dimension(900, 600));
		myPanel.setBackground(Color.decode("#EEEEEE"));
		
		// ListPanel HoaDon
		listPanelKhachHang = new JPanel();
		myPanel.add(listPanelKhachHang, BorderLayout.CENTER);
		listPanelKhachHang.setPreferredSize(new Dimension(500, 580));//550
		listPanelKhachHang.setLayout(new BorderLayout());
		
		JPanel locPanel_KH = new JPanel();
		locPanel_KH.setPreferredSize(new Dimension(550, 50));
		
		Box box = Box.createHorizontalBox();
		
		box.add(lblLocTuKhoa_KH = new JLabel("Mã KH, SĐT, Họ tên đệm: "));
		box.add(txtLocTuKhoa_KH = new JTextField(8));
		box.add(Box.createHorizontalStrut(5));
		txtLocTuKhoa_KH.setName("timkiem_khachhang");
		
		box.add(lblLocTen_KH = new JLabel("Tên: "));
		String[] sortTen = {"Tăng", "Giảm"};
		comboBox_SortHoTen = new JComboBox<String>(sortTen);
		comboBox_SortHoTen.setPreferredSize(new Dimension(80, 25));
		box.add(comboBox_SortHoTen);
		comboBox_SortHoTen.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		box.add(Box.createHorizontalStrut(5));
		
		radSortNam = new JRadioButton("Nam");
		radSortNu = new JRadioButton("Nữ");
		ButtonGroup gr = new ButtonGroup();
		gr.add(radSortNam);gr.add(radSortNu);
		radSortNam.setSelected(true);
		box.add(radSortNam);
		box.add(radSortNu);
	
		btnLocKH = new JButton("Lọc");
		box.add(btnLocKH);
		btnLocKH.setBorder(BorderFactory.createCompoundBorder(btnLocKH.getBorder(), new BottomBorder()));
		btnLocKH.setFocusable(false);
		btnLocKH.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		locPanel_KH.add(box);
		listPanelKhachHang.add(locPanel_KH, BorderLayout.NORTH);
		
		//==== Table
		JPanel panelTable = new JPanel();
		panelTable.setLayout(new BorderLayout());
		
		String[] colHeader = {"Mã","Họ","Tên","Số điện thoại","Email","Giới tính"};
		
		modelKH = new DefaultTableModel(colHeader, 0) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		
		tableKH = new JTable(modelKH);
		tableKH.setRowHeight(20);
		
		JScrollPane sp = new JScrollPane(tableKH, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(JLabel.CENTER);
		tableKH.getColumnModel().getColumn(0).setCellRenderer(center);
		tableKH.getColumnModel().getColumn(1).setCellRenderer(center);
		tableKH.getColumnModel().getColumn(2).setCellRenderer(center);
		tableKH.getColumnModel().getColumn(3).setCellRenderer(center);
		tableKH.getColumnModel().getColumn(4).setCellRenderer(center);
		tableKH.getColumnModel().getColumn(5).setCellRenderer(center);
		
		tableKH.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		panelTable.add(sp, BorderLayout.CENTER);
		
		setDuLieuKhachHang();
		
		listPanelKhachHang.add(panelTable, BorderLayout.CENTER);
		
		JPanel panelPages = new JPanel();
		panelPages.setPreferredSize(new Dimension(550, 40));
		
		btnRefresh = new JButton("Refresh");
		btnRefresh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnXoaKH_csdl = new JButton("Xóa");
		btnXoaKH_csdl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSuaKH_csdl = new JButton("Sửa");
		btnSuaKH_csdl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//		btnNextKH = new JButton("❮");
//		btnNextKH.setFocusable(false);
//		btnNextKH.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//		
//		int total = (int) Math.ceil(thuoc_dao.totalThuoc()*1.0/25);
//		JComboBox<String> comboBoxPages = new JComboBox<String>();
//		for (int i=1; i <= total; i++) {
//			comboBoxPages.addItem(i+"");
//		}
//		comboBoxPages.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//		
//		btnPrevKH = new JButton("❯");
//		btnPrevKH.setFocusable(false);
//		btnPrevKH.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		panelPages.add(btnXoaKH_csdl);
		panelPages.add(btnSuaKH_csdl);
		panelPages.add(btnRefresh);
//		panelPages.add(btnNextKH);
//		panelPages.add(comboBoxPages);
//		panelPages.add(btnPrevKH);
		
		listPanelKhachHang.add(panelPages, BorderLayout.SOUTH);
		
		addPanelKhachHang = new JPanel();
		myPanel.add(addPanelKhachHang, BorderLayout.EAST);
		addPanelKhachHang.setPreferredSize(new Dimension(390, 570));//340
		addPanelKhachHang.setBackground(Color.decode("#DDDDDD"));
		
		JPanel addPanelKhachHangNorth = new JPanel();
		addPanelKhachHangNorth.setOpaque(false);
		Box b = Box.createVerticalBox();
		
		Box b1, b2, b3, b4, b5;
		JTextField txtmaHoaDon;
		
		b.add(b1 = Box.createHorizontalBox());
		b1.add(new JLabel("Thêm Khách Hàng".toUpperCase()));
		b.add(Box.createVerticalStrut(10));
		b.add(b2 = Box.createHorizontalBox());
		b2.add(Box.createHorizontalStrut(5));
		b2.add(new JLabel("Mã khách hàng:"));
		b2.add(Box.createHorizontalStrut(5));
		b2.add(txtmaKH_KH = new JTextField(6));
		txtmaKH_KH.setEditable(false);

		b2.add(Box.createHorizontalStrut(10));
		b2.add(new JLabel("Số điện thoại:"));
		b2.add(Box.createHorizontalStrut(5));
		b2.add(txtSDT_KH = new JTextField(8));
		b2.add(Box.createHorizontalStrut(5));
		
		b.add(b3 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(5));
		b3.add(Box.createHorizontalStrut(5));
		b3.add(new JLabel("Họ:"));
		b3.add(Box.createHorizontalStrut(5));
		b3.add(txtho_KH = new JTextField(7));
		
		b3.add(Box.createHorizontalStrut(10));
		b3.add(new JLabel("Tên:"));
		b3.add(Box.createHorizontalStrut(5));
		b3.add(txtTen_KH = new JTextField(7));
		b3.add(Box.createHorizontalStrut(5));
		
		b.add(b4 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(5));
		b4.add(Box.createHorizontalStrut(5));
		b4.add(new JLabel("Email:"));
		b4.add(Box.createHorizontalStrut(5));
		b4.add(txtEmail_KH = new JTextField(14));
		b4.add(Box.createHorizontalStrut(5));
		
		b4.add(Box.createHorizontalStrut(5));
		b4.add(new JLabel("Giới Tính: "));
		comboBoxGioiTinh = new JComboBox<String>(new String[] {"Nam", "Nữ"});
		b4.add(comboBoxGioiTinh);
		comboBoxGioiTinh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));	
		b4.add(Box.createHorizontalStrut(5));
		
		addPanelKhachHangNorth.add(b);
		addPanelKhachHang.add(addPanelKhachHangNorth, BorderLayout.NORTH);
		
		JPanel addPanelKhachHangCenter = new JPanel();
		
		String[] colHeaderThem = {"Mã","Họ","Tên","Số điện thoại","Email","Giới tính"};
		
		modelKH_temp = new DefaultTableModel(colHeaderThem, 0) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
		        return (column == 3);
		    }
		};
		
		tableKH_temp = new JTable(modelKH_temp);
		tableKH_temp.setRowHeight(20);
		
		tableKH_temp.getColumnModel().getColumn(0).setCellRenderer(center);
		tableKH_temp.getColumnModel().getColumn(1).setCellRenderer(center);
		tableKH_temp.getColumnModel().getColumn(2).setCellRenderer(center);
		tableKH_temp.getColumnModel().getColumn(3).setPreferredWidth(150);
		tableKH_temp.getColumnModel().getColumn(4).setCellRenderer(center);
		tableKH_temp.getColumnModel().getColumn(5).setCellRenderer(center);
		
		JScrollPane spTemp = new JScrollPane(tableKH_temp, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		spTemp.setPreferredSize(new Dimension(370, 390));//320
		
		addPanelKhachHangCenter.add(spTemp, BorderLayout.CENTER);
		
		addPanelKhachHang.add(addPanelKhachHangCenter, BorderLayout.CENTER);
		
		
		JPanel addPanelKhachHangSouth = new JPanel();
		addPanelKhachHangSouth.setOpaque(false);		
		
		Box bSouth = Box.createVerticalBox();
		Box b1South = Box.createHorizontalBox();
		Box b2South = Box.createHorizontalBox();
		
		bSouth.add(b1South);
		b1South.add(btnThemKH = new JButton("Thêm"));
		b1South.add(Box.createHorizontalStrut(5));
		b1South.add(btnSuaKH = new JButton("Sửa"));
		b1South.add(Box.createHorizontalStrut(5));
		b1South.add(btnXoaKH = new JButton("Xóa"));
		b1South.add(Box.createHorizontalStrut(5));
		b1South.add(btnXoaTrangKH = new JButton("Xóa rỗng"));
		b1South.add(Box.createHorizontalStrut(5));
		b1South.add(btnLuuKH = new JButton("Lưu"));
		
		addPanelKhachHangSouth.add(bSouth);
		
		addPanelKhachHang.add(addPanelKhachHangSouth, BorderLayout.SOUTH);
		
		//Set Border 0
		listPanelKhachHang.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		addPanelKhachHang.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		btnThemKH.addActionListener(this);
		btnXoaKH.addActionListener(this);
		btnSuaKH.addActionListener(this);
		btnLuuKH.addActionListener(this);
		btnLocKH.addActionListener(this);
		btnXoaTrangKH.addActionListener(this);
		btnRefresh.addActionListener(this);
		btnXoaKH_csdl.addActionListener(this);
		btnSuaKH_csdl.addActionListener(this);
		txtLocTuKhoa_KH.getDocument().putProperty("owner", txtLocTuKhoa_KH);
		txtLocTuKhoa_KH.getDocument().addDocumentListener(this);
		
		tableKH.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int r = tableKH.getSelectedRow();
				txtmaKH_KH.setText(modelKH.getValueAt(r, 0).toString());
				txtho_KH.setText(modelKH.getValueAt(r, 1).toString());
				txtTen_KH.setText(modelKH.getValueAt(r, 2).toString());
				txtSDT_KH.setText(modelKH.getValueAt(r, 3).toString());
				txtEmail_KH.setText(modelKH.getValueAt(r, 4).toString());
				
				if(modelKH.getValueAt(r, 5).toString().equalsIgnoreCase("Nam")) {
					comboBoxGioiTinh.setSelectedItem("Nam");
				}
				else {
					comboBoxGioiTinh.setSelectedItem("Nữ");
				}
			}
		});
		
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
	
	//====== Hàm thêm không phải panel ====
	
	public void setDuLieuHoaDon(int pages) {
		
		List<Thuoc> list = thuoc_dao.getPagesThuoc(pages);
		for(Thuoc thuoc : list) {
			
			DecimalFormat formatter = new DecimalFormat("#,###");
			
			modelHoaDon.addRow(new Object[] {thuoc.getMaThuoc(),thuoc.getTenThuoc(),thuoc.getDonViBan(),
					thuoc.getSoLuong(),thuoc.getXuatXu(),thuoc.getDangBaoChe(),formatter.format(thuoc.getDonGia())});
			
		}
	}
	
	public void setDuLieuChiTietHoaDon() {
		
		DefaultTableModel temp = (DefaultTableModel) tableChiTietHoaDon.getModel();
		temp.getDataVector().removeAllElements();
		
		int i = 1;
		
		for(CT_HoaDon ct : listCTHoaDon) {
			
			DecimalFormat formatter = new DecimalFormat("#,###");
			
			modelChiTietHoaDon.addRow(new Object[] {i,ct.getThuocCT().getTenThuoc(),formatter.format(ct.getDongia()),
					ct.getSoLuong(),formatter.format(ct.getTongtien())});
			
			i++;
		}
		
	}
	
	public void ThayDoiSoLuong(int row, int column) {
		
		String soLuongMoi = (String) modelChiTietHoaDon.getValueAt(row, column);
		int value = Integer.parseInt(soLuongMoi);
		
		int i = 0;
		for(CT_HoaDon ct : listCTHoaDon) {
			if(i==row)
				ct.setSoLuong(value);
			i++;
		}
		setDuLieuChiTietHoaDon();
		
	}
	
	public void lblThongBao(JLabel lbl, int x, String str) {
		lbl.setFont(new Font("Serif", Font.ITALIC, 13));
		lbl.setText(str);
		if(x==0)
			lbl.setForeground(Color.red);
		else if(x==1)
			lbl.setForeground(Color.green);
	}
	
	public String tongTienCTHD() {
		
		DecimalFormat formatter = new DecimalFormat("#,###");
		Double tongTien = (double) 0;
		for(CT_HoaDon ct : listCTHoaDon) {
			tongTien += ct.getTongtien()*ct.getSoLuong();
		}
		return "<html>Tổng tiền: <b><font color='red'>"+formatter.format(tongTien)+"0</font></b>₫</html>";
		
	}
	
	//KhachHangXuLY
	public void setDuLieuKhachHang() {
		
		List<KhachHang> dsKH = khachhang_dao.getAllKhachHang();
		String gioitinh = "";
		for(KhachHang kh : dsKH) {
			if(kh.isGioiTinh()) {
				gioitinh = "Nam";
			}else{
				gioitinh = "Nữ";
			}
			modelKH.addRow(new Object[] {kh.getMaKhachHang(),kh.getHoKhachHang(),kh.getTenKhachHang(),
					kh.getSoDienThoai(),kh.getEmailKhachHang(), gioitinh});
			
		}
	}
	
	public boolean isNumberInt(String x) {
		try {
			Integer.parseInt(x);
		}catch (Exception e){
			return false;
		}
		return true;
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
	
	DefaultTableCellRenderer render = new DefaultTableCellRenderer() {
		
		private static final long serialVersionUID = 1L;
		@Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        // Lấy đối tượng JLabel của TableCellRenderer
	        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        // Nếu là cột đầu tiên, thiết lập font in đậm
	        if (column == 6) {
	            Font boldFont = new Font(label.getFont().getName(), Font.BOLD, label.getFont().getSize());
	            label.setFont(boldFont);
	            label.setHorizontalAlignment(JLabel.RIGHT);
	        }
	        return label;
	    }
	};

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void insertUpdate(DocumentEvent e) {
		
		JTextField textField = (JTextField) e.getDocument().getProperty("owner");
		
		if (textField.getName().equals("sodienthoai_hoadon")) {
            String sodienthoai = textField.getText().trim();
			if(sodienthoai.matches("^\\d{10}$")) {
				
				KhachHang kh = khachhang_dao.getKhachHangTheoSDT(sodienthoai);
				if(kh==null) {
					lblThongBao(lblTBHD, 0, "Không có khách hàng trùng khớp.");
					txtmaKH_HD.setText("");
					txthotenKH_HD.setText("");
				}else {
					txtmaKH_HD.setText(kh.getMaKhachHang());
					txthotenKH_HD.setText(kh.getHoKhachHang()+" "+kh.getTenKhachHang());
					lblThongBao(lblTBHD, 0, "");
				}
				
			}else {
				lblThongBao(lblTBHD, 0, "Số điện thoại phải có 10 ký tự.");
				txtmaKH_HD.setText("");
				txthotenKH_HD.setText("");
			}
            
        }
	
	}
	

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	//Khach_Hàng
	@Override
	public void actionPerformed(ActionEvent e) {
		//Khách hàng
		Object o = e.getSource();
		if(o.equals(btnLocKH)) {
			String selectTen = (String)comboBox_SortHoTen.getSelectedItem();
			if(txtLocTuKhoa_KH.getName().equals("timkiem_khachhang") && selectTen.equals("Tăng") && radSortNam.isSelected()) {
				String regex = txtLocTuKhoa_KH.getText().trim();
				
				if(regex.equals("")) 
					setDuLieuKhachHang();
				else {
					
					DefaultTableModel temp = (DefaultTableModel) tableKH.getModel();
					temp.getDataVector().removeAllElements();
					
					List<KhachHang> list = khachhang_dao.filterTuKhoaKH_Tang_Nam(regex);
					for(KhachHang kh : list) {
						String gioitinh ="";	
						if(kh.isGioiTinh()) {
							gioitinh = "Nam";
						}else{
							gioitinh = "Nữ";
						}
						modelKH.addRow(new Object[] {kh.getMaKhachHang(),kh.getHoKhachHang(),kh.getTenKhachHang(),
								kh.getSoDienThoai(),kh.getEmailKhachHang(), gioitinh});
					}	
					
				}
			}
			
			if(txtLocTuKhoa_KH.getName().equals("timkiem_khachhang") && selectTen.equals("Giảm") && radSortNam.isSelected()) {
				String regex = txtLocTuKhoa_KH.getText().trim();
				
				if(regex.equals("")) 
					setDuLieuKhachHang();
				else {
					
					DefaultTableModel temp = (DefaultTableModel) tableKH.getModel();
					temp.getDataVector().removeAllElements();
					
					List<KhachHang> list = khachhang_dao.filterTuKhoaKH_Giam_Nam(regex);
					for(KhachHang kh : list) {
						String gioitinh ="";	
						if(kh.isGioiTinh()) {
							gioitinh = "Nam";
						}else{
							gioitinh = "Nữ";
						}
						modelKH.addRow(new Object[] {kh.getMaKhachHang(),kh.getHoKhachHang(),kh.getTenKhachHang(),
								kh.getSoDienThoai(),kh.getEmailKhachHang(), gioitinh});
					}	
					
				}
			}
			
			if(txtLocTuKhoa_KH.getName().equals("timkiem_khachhang") && selectTen.equals("Tăng") && radSortNu.isSelected()) {
				String regex = txtLocTuKhoa_KH.getText().trim();
				
				if(regex.equals("")) 
					setDuLieuKhachHang();
				else {
					
					DefaultTableModel temp = (DefaultTableModel) tableKH.getModel();
					temp.getDataVector().removeAllElements();
					
					List<KhachHang> list = khachhang_dao.filterTuKhoaKH_Tang_Nu(regex);
					for(KhachHang kh : list) {
						String gioitinh ="";	
						if(kh.isGioiTinh()) {
							gioitinh = "Nam";
						}else{
							gioitinh = "Nữ";
						}
						modelKH.addRow(new Object[] {kh.getMaKhachHang(),kh.getHoKhachHang(),kh.getTenKhachHang(),
								kh.getSoDienThoai(),kh.getEmailKhachHang(), gioitinh});
					}	
					
				}
			}
			
			if(txtLocTuKhoa_KH.getName().equals("timkiem_khachhang") && selectTen.equals("Giảm") && radSortNu.isSelected()) {
				String regex = txtLocTuKhoa_KH.getText().trim();
				
				if(regex.equals("")) 
					setDuLieuKhachHang();
				else {
					
					DefaultTableModel temp = (DefaultTableModel) tableKH.getModel();
					temp.getDataVector().removeAllElements();
					
					List<KhachHang> list = khachhang_dao.filterTuKhoaKH_Giam_Nu(regex);
					for(KhachHang kh : list) {
						String gioitinh ="";	
						if(kh.isGioiTinh()) {
							gioitinh = "Nam";
						}else{
							gioitinh = "Nữ";
						}
						modelKH.addRow(new Object[] {kh.getMaKhachHang(),kh.getHoKhachHang(),kh.getTenKhachHang(),
								kh.getSoDienThoai(),kh.getEmailKhachHang(), gioitinh});
					}	
					
				}
			}
			
			if(selectTen.equals("Tăng") && radSortNam.isSelected() && txtLocTuKhoa_KH.getText().trim().equals("")){
				modelKH.getDataVector().removeAllElements();
				List<KhachHang> dsKH = khachhang_dao.sortTang_Nam();
				for(KhachHang kh : dsKH) {
					String gioitinh = "";
					if(kh.isGioiTinh()) {
						gioitinh = "Nam";
					}else{
						gioitinh = "Nữ";
					}
					modelKH.addRow(new Object[] {kh.getMaKhachHang(),kh.getHoKhachHang(),kh.getTenKhachHang(),
							kh.getSoDienThoai(),kh.getEmailKhachHang(), gioitinh});	
				}
			}
			if(selectTen.equals("Giảm") && radSortNam.isSelected() && txtLocTuKhoa_KH.getText().trim().equals("")){
				modelKH.getDataVector().removeAllElements();
				List<KhachHang> dsKH = khachhang_dao.sortGiam_Nam();
				for(KhachHang kh : dsKH) {
					String gioitinh = "";
					if(kh.isGioiTinh()) {
						gioitinh = "Nam";
					}else{
						gioitinh = "Nữ";
					}
					modelKH.addRow(new Object[] {kh.getMaKhachHang(),kh.getHoKhachHang(),kh.getTenKhachHang(),
							kh.getSoDienThoai(),kh.getEmailKhachHang(), gioitinh});	
				}
			}
			if(selectTen.equalsIgnoreCase("Tăng") && radSortNu.isSelected() && txtLocTuKhoa_KH.getText().trim().equals("")) {
				modelKH.getDataVector().removeAllElements();
				List<KhachHang> dsKH = khachhang_dao.sortTang_Nu();
				for(KhachHang kh : dsKH) {
						String gioitinh = "";
					if(kh.isGioiTinh()) {
						gioitinh = "Nam";
					}else{
						gioitinh = "Nữ";
					}
					modelKH.addRow(new Object[] {kh.getMaKhachHang(),kh.getHoKhachHang(),kh.getTenKhachHang(),
							kh.getSoDienThoai(),kh.getEmailKhachHang(), gioitinh});	
				}
			}
			if(selectTen.equalsIgnoreCase("Giảm") && radSortNu.isSelected() && txtLocTuKhoa_KH.getText().trim().equals("")){
				modelKH.getDataVector().removeAllElements();
				List<KhachHang> dsKH = khachhang_dao.sortGiam_Nu();
				for(KhachHang kh : dsKH) {
					String gioitinh = "";
					if(kh.isGioiTinh()) {
						gioitinh = "Nam";
					}else{
						gioitinh = "Nữ";
					}
					modelKH.addRow(new Object[] {kh.getMaKhachHang(),kh.getHoKhachHang(),kh.getTenKhachHang(),
							kh.getSoDienThoai(),kh.getEmailKhachHang(), gioitinh});	
				}
			}
		}
		else if(o.equals(btnThemKH)) {
			KhachHang kh = revertKHFromTextfields();
			if(validKH()) {
				if(addToListKhachHang(kh)) {
					String gioitinh = "";
					if (kh.isGioiTinh()) {
						gioitinh = "Nam";
					}else {
						gioitinh = "Nữ";
					}
					JOptionPane.showMessageDialog(null, "Thêm thành công");
					String [] row = {kh.getMaKhachHang(),kh.getHoKhachHang(),kh.getTenKhachHang(),
							kh.getSoDienThoai(),kh.getEmailKhachHang(), gioitinh};
					modelKH_temp.addRow(row);
				}else {
					int i = listKH.size();
					String maMoi = null;
					String maHienTai = null;
					maHienTai = modelKH_temp.getValueAt(i-1, 0).toString();
					String kyTuCuoi = maHienTai.replaceAll("[^0-9]+", "");
					String kyTuMoi = Integer.toString(Integer.parseInt(kyTuCuoi) + 1);
					maMoi = "KH" + kyTuMoi;
					
					String ten = txtTen_KH.getText().trim();
					String ho = txtho_KH.getText().trim();
					String sdt = txtSDT_KH.getText().trim();
					String email = txtEmail_KH.getText().trim();
					String gioiTinh = (String) comboBoxGioiTinh.getSelectedItem();
					boolean phai;
					if(gioiTinh.equalsIgnoreCase("Nam")) {
						phai = true;
					}else {
						phai = false;
					}
					KhachHang khMoi = new KhachHang(maMoi, ho, ten, sdt, email, phai);
					listKH.add(kh);
					
					String gioitinh = "";
					if (khMoi.isGioiTinh()) {
						gioitinh = "Nam";
					}else {
						gioitinh = "Nữ";
					}
					JOptionPane.showMessageDialog(null, "Thêm thành công");
					String [] row = {khMoi.getMaKhachHang(),khMoi.getHoKhachHang(),khMoi.getTenKhachHang(),
							khMoi.getSoDienThoai(),khMoi.getEmailKhachHang(), gioiTinh};
					modelKH_temp.addRow(row);
				}
			}
		}
		else if(o.equals(btnXoaKH)) {
			int r = tableKH_temp.getSelectedRow();
			if(r != -1 ) {
				int tb = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa dòng này không?", "Delete", JOptionPane.NO_OPTION);
				if(tb == JOptionPane.YES_OPTION) {
					if(xoa1KhachHang(r)){
						modelKH_temp.removeRow(r);
					}
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Bạn chưa chọn dòng xóa!!!");
			}
		}
		else if(o.equals(btnSuaKH)) {
			int r = tableKH_temp.getSelectedRow();
			if(r == -1) {
				JOptionPane.showMessageDialog(null, "Bạn chưa chọn dòng");
				return;
			}
			if(tableKH_temp.getSelectedRowCount() > 1) {
				JOptionPane.showMessageDialog(null, "Chỉ được chọn 1 dòng để sửa");
				return;
			}
			
			KhachHang kh = revertKHFromTextfields();
			if(validKH()) {
				try {
					if(suaKhachHang(kh.getMaKhachHang(), kh.getHoKhachHang(), kh.getTenKhachHang(), kh.getSoDienThoai(), kh.getEmailKhachHang(), kh.isGioiTinh())) {
						modelKH_temp.removeRow(r);
						modelKH_temp.insertRow(r, kh.getObjectNV());
					}
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "Không thể sửa, hãy kiểm tra lại");
				}
			}
		}
		else if(o.equals(btnLuuKH)) {
			int r = tableKH_temp.getSelectedRow();
			if(r != -1 ) {
				int ret = JOptionPane.showConfirmDialog(null, "Lưu vào cơ sở dữ liệu ?", "Lưu", JOptionPane.NO_OPTION);
				if (ret == JOptionPane.YES_OPTION) {
					boolean gioitinh;
					if(modelKH_temp.getValueAt(r, 5).toString().equalsIgnoreCase("Nam")) {
						gioitinh = true;
					}else {
						gioitinh = false;
					}
					KhachHang kh = new KhachHang(modelKH_temp.getValueAt(r, 0).toString(), modelKH_temp.getValueAt(r, 1).toString(), modelKH_temp.getValueAt(r, 2).toString(), modelKH_temp.getValueAt(r, 3).toString(), modelKH_temp.getValueAt(r, 4).toString(), gioitinh);
						if(khachhang_dao.insertKhachHang(kh)) {
							modelKH.getDataVector().removeAllElements();
							setDuLieuKhachHang();
							JOptionPane.showMessageDialog(null, "Lưu thành công");
							if(xoa1KhachHang(r)){
								modelKH_temp.removeRow(r);
							}
						}else {
							JOptionPane.showMessageDialog(null, "Đã có lỗi");
						}
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Bạn chưa chọn dòng để lưu!!!");
			}
		}
		else if(o.equals(btnRefresh)) {
			modelKH.getDataVector().removeAllElements();
			setDuLieuKhachHang();
		}
		else if(o.equals(btnXoaTrangKH)) {
			xoaTrangFieldKhachHang();
		}
		else if(o.equals(btnXoaKH_csdl)) {
			int r = tableKH.getSelectedRow();
			if(r != -1 ) {
				int tb = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa khách hàng này không?", "Delete", JOptionPane.NO_OPTION);
				if(tb == JOptionPane.YES_OPTION) {
					String maCanXoa = modelKH.getValueAt(r, 0).toString().trim();
					if(khachhang_dao.XoaKhachHang(maCanXoa)){
						modelKH.getDataVector().removeAllElements();
						setDuLieuKhachHang();
					}
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Bạn chưa chọn dòng xóa!!!");
			}
		}
		else if(o.equals(btnSuaKH_csdl)) {
			int r = tableKH.getSelectedRow();
			if(r == -1) {
				JOptionPane.showMessageDialog(null, "Bạn chưa chọn dòng");
				return;
			}
			if(tableKH.getSelectedRowCount() > 1) {
				JOptionPane.showMessageDialog(null, "Chỉ được chọn 1 dòng để sửa");
				return;
			}
			String ma = txtmaKH_KH.getText().trim();
			String ten = txtTen_KH.getText().trim();
			String ho = txtho_KH.getText().trim();
			String sdt = txtSDT_KH.getText().trim();
			String email = txtEmail_KH.getText().trim();
			String gioiTinh = (String) comboBoxGioiTinh.getSelectedItem();
			boolean phai;
			if(gioiTinh.equalsIgnoreCase("Nam")) {
				phai = true;
			}else {
				phai = false;
			}
			KhachHang kh = new KhachHang(ma, ho, ten, sdt, email, phai);
			if(validKH()) {
				try {
					if(khachhang_dao.updateKhachHang(kh)) {
						modelKH.getDataVector().removeAllElements();
						setDuLieuKhachHang();
					}
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "Không thể sửa vào csdl, hãy kiểm tra lại");
				}
			}
		}
	}
	// Khach hang
	private boolean validKH() {
		String ma = txtmaKH_KH.getText().trim();
		String ten = txtTen_KH.getText().trim();
		String ho = txtho_KH.getText().trim();
		String sdt = txtSDT_KH.getText().trim();
		String email = txtEmail_KH.getText().trim();
		
//		if (!(ma.length() > 0 && ma.matches("(KH)\\d{4}"))) {
//			JOptionPane.showMessageDialog(null, "Error: Mã khách hàng theo mẫu: KH + 4 ký số");
//			txtmaKH_KH.requestFocus();
//			return false;
//		}

//		"^[A-Z][a-z]*(?:\\h+[A-Z][a-z]*)*$"
		if (!(ho.length() > 0 && ho.matches("^[\\p{L}]*(?:\\h+[\\p{L}]*)*$"))) {
			JOptionPane.showMessageDialog(null, "Error: Họ khách hàng không có số hay kí tự đặc biệt");
			txtho_KH.requestFocus();
			return false;
		}
		
		else if (!(ten.length() > 0 && ten.matches("^[\\p{L}]*$"))) {
			JOptionPane.showMessageDialog(null, "Error: Tên khách hàng là 1 từ, không có số, hay kí tự đặc biệt");
			txtTen_KH.requestFocus();
			return false;
		}
		
		else if(!(sdt.length() > 0 && sdt.matches("^\\d{10}$"))) {
			JOptionPane.showMessageDialog(null, "Error: Số điện thoại là 1 dãy số nguyên có 10 số");
			txtSDT_KH.requestFocus();
			return false;
		}
		
		else if(!(email.length()>=0 && email.matches("^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$"))){
			JOptionPane.showMessageDialog(null, "Sai định dạng email");
			txtEmail_KH.requestFocus();
			return false;
		}
		return true;
	}
	private KhachHang revertKHFromTextfields() {
		String ma = khachhang_dao.maKHAuto();
		String ten = txtTen_KH.getText().trim();
		String ho = txtho_KH.getText().trim();
		String sdt = txtSDT_KH.getText().trim();
		String email = txtEmail_KH.getText().trim();
		String gioiTinh = (String) comboBoxGioiTinh.getSelectedItem();
		boolean phai;
		if(gioiTinh.equalsIgnoreCase("Nam")) {
			phai = true;
		}else {
			phai = false;
		}
		return new KhachHang(ma, ho, ten, sdt, email, phai);
	}
	
	private boolean addToListKhachHang(KhachHang kh) {
		for(int i=0; i<listKH.size(); i++)
			if(listKH.get(i).getMaKhachHang().equalsIgnoreCase(kh.getMaKhachHang())) {
				return false;
			}
		listKH.add(kh);
		return true;
	}
	
	private boolean xoa1KhachHang(int x) {
		if(x >=0 && x < listKH.size()) {
			listKH.remove(x);
			return true;
		}
		else return false;
	}
	
	public boolean suaKhachHang(String ma, String ho, String ten, String sdt, String email, boolean gioitinh) {
		KhachHang kh = new KhachHang(ma, ho, ten, sdt, email, gioitinh);
		if(listKH.contains(kh)) { 
//			kh.setMaKhachHang(ma);
			kh.setHoKhachHang(ho);
			kh.setTenKhachHang(ten);
			kh.setSoDienThoai(sdt);
			kh.setEmailKhachHang(email);
			kh.setGioiTinh(gioitinh);
			return true;
		}
		else return false;
	}
	
	public void xoaTrangFieldKhachHang() {
		txtmaKH_KH.setText("");
		txtTen_KH.setText("");
		txtho_KH.setText("");
		txtSDT_KH.setText("");
		txtEmail_KH.setText("");
		txtSDT_KH.requestFocus();
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
            
            Thuoc thuoc = thuoc_dao.getThuocTheoMaThuoc(maThuoc);
            HoaDon hoaDon = new HoaDon(hoadon_dao.maHoaDonAuto());
            
            CT_HoaDon cthd = new CT_HoaDon(hoaDon, thuoc, 1);
            
            listCTHoaDon.add(cthd);            
            setDuLieuChiTietHoaDon();
            lblTongTienHD.setText(tongTienCTHD());
        }
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		
		Object o = e.getSource();
		
        int row = e.getFirstRow();
        int column = e.getColumn();
        
        if(column>0 && o.equals(modelChiTietHoaDon)) {
        	ThayDoiSoLuong(row, column);
        	lblTongTienHD.setText(tongTienCTHD());
        }
        
	}

}
