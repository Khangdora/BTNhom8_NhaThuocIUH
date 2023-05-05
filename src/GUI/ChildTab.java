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
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import DAO.CaTruc_DAO;
import DAO.HoaDon_DAO;
import DAO.KhachHang_DAO;
import DAO.NhanVien_DAO;
import DAO.Thuoc_DAO;
import connectDB.ConnectDB;
import entity.CT_HoaDon;
import entity.CaTruc;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.Thuoc;
import others.BottomBorder;
import others.RoundedBorder;

public class ChildTab implements ActionListener, MouseListener, KeyListener, DocumentListener, TableModelListener {
	
	//Gán DAO
	private Thuoc_DAO thuoc_dao;
	private NhanVien_DAO nhanvien_dao;
	private HoaDon_DAO hoadon_dao;
	private KhachHang_DAO khachhang_dao;
	private NhanVien nvlogin;
	protected static CaTruc_DAO catruc_dao;
	
	//Gán phần tử
	private JPanel listPanelHoaDon, addPanelHoaDon;
	
	private JTextField txtHoaDon, txtSDT_HD, txtmaKH_HD, txthotenKH_HD, txtmaHoaDon;
	
	private JLabel lblTBHD, lblTongTienHD;
	
	private JButton btnTaiLaiHoaDon, btnNextHoaDon, btnPrevHoaDon, 
		btnLuuHoaDon, btnThanhToan, btnTayTrongHoaDon;
	
	private DefaultTableModel modelHoaDon, modelChiTietHoaDon;
	private JTable tableHoaDon, tableChiTietHoaDon;
	
	private JComboBox<String> comboBoxPages, comboBoxDangHD, 
		comboBoxDVB, comboBoxXX, comboBoxDBC, comboBoxSX;
	
	public static int pages = 1;
	
	private int limit = 25;
	
	private ArrayList<CT_HoaDon> listCTHoaDon;
	
	//NHAN VIEN
	private JPanel listPanelNhanVien;
	private JTextField txtMaNV_NV, txtHo_NV, txtTen_NV, txtSDT_NV, txtEmail_NV, txtGioiTinh_NV, txtCaTruc_NV, txtChucVu_NV, txtTimMa_NV, txtTienLuong_NV;
	private JLabel lblTimMa_NV, lblLocCaTruc_NV, lblLocGioiTinh_NV, lblLocChucVu_NV, lblMa_NV, lblHo_NV, lblSDT_NV, lblEmail_NV;
	private JButton btnLoc_NV, btnNext_NV, btnPrev_NV, btnThem_NV, btnXoa_NV, btnSua_NV, btnTaiLai_NV, btnXoaRong_NV;
	protected static DefaultTableModel modelNV;
	protected static JTable tableNV;
	private JComboBox<String> comboBoxGioiTinh_NV, comboBoxChucVu_NV, comboBoxCaTruc_NV, comboBoxSortGioiTinh_NV, comboBoxSortChucVu_NV, comboBoxSortCaTruc_NV;
	private JRadioButton radNam, radNu;
	protected static ArrayList<NhanVien> listNV;
	private ArrayList<CaTruc> listCaTruc;
	
	private Component main;
	
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
	
	public ChildTab(NhanVien nv) {
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
		
		nvlogin = nhanvien_dao.getNhanVienTheoMaNV(nv.getMaNhanVien());;
		listNV = nhanvien_dao.getallNhanVien();
		catruc_dao = new CaTruc_DAO();
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
		txtHoaDon.setName("timkiem_hoadon");
		
		String[] donViBanStr = {"Tất cả", "Hộp", "Chai", "Vỉ", "Tuýp"};
		comboBoxDVB = new JComboBox<String>(donViBanStr);
		comboBoxDVB.setPreferredSize(new Dimension(65, 25));
		box.add(comboBoxDVB);
		comboBoxDVB.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		box.add(Box.createHorizontalStrut(5));
		
		ArrayList<String> xuatXuStr = thuoc_dao.getXuatXu();
		comboBoxXX = new JComboBox<String>();
		comboBoxXX.addItem("Tất cả");
		for(String s : xuatXuStr)
			comboBoxXX.addItem(s);
		box.add(comboBoxXX);
		comboBoxXX.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		box.add(Box.createHorizontalStrut(5));
		
		String[] dangBaoCheStr = {"Tất cả", "Dung dịch", "Viên sủi", "Bột", "Viên nén", "Viên nhộng", "Khác"};
		comboBoxDBC = new JComboBox<String>(dangBaoCheStr);
		box.add(comboBoxDBC);
		comboBoxDBC.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		box.add(Box.createHorizontalStrut(5));
		
		String[] sapXepStr = {"Mới-Cũ", "Cũ-Mới"};
		comboBoxSX = new JComboBox<String>(sapXepStr);
		box.add(comboBoxSX);
		comboBoxSX.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		box.add(Box.createHorizontalStrut(5));
		
		btnTaiLaiHoaDon = new JButton("Tải lại");
		box.add(btnTaiLaiHoaDon);
		btnTaiLaiHoaDon.setFocusable(false);
		btnTaiLaiHoaDon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
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
		
		JScrollPane sp = new JScrollPane(tableHoaDon, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(JLabel.CENTER);
		tableHoaDon.getTableHeader().setDefaultRenderer(headerRenderer);
		tableHoaDon.getColumnModel().getColumn(0).setCellRenderer(center);
		tableHoaDon.getColumnModel().getColumn(1).setPreferredWidth(200);
		tableHoaDon.getColumnModel().getColumn(2).setCellRenderer(center);
		tableHoaDon.getColumnModel().getColumn(3).setCellRenderer(center);
		tableHoaDon.getColumnModel().getColumn(4).setCellRenderer(center);
		tableHoaDon.getColumnModel().getColumn(5).setCellRenderer(center);
		tableHoaDon.getColumnModel().getColumn(6).setCellRenderer(render);
		
		tableHoaDon.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		panelTable.add(sp, BorderLayout.CENTER);
		
		listPanelHoaDon.add(panelTable, BorderLayout.CENTER);
		
		JPanel panelPages = new JPanel();
		panelPages.setPreferredSize(new Dimension(550, 40));
		
		btnPrevHoaDon = new JButton("❮");
		btnPrevHoaDon.setFocusable(false);
		btnPrevHoaDon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		int total = (int) Math.ceil(thuoc_dao.totalThuoc()*1.0/25);
		comboBoxPages = new JComboBox<String>();
		for (int i=1; i <= total; i++) {
			comboBoxPages.addItem(i+"");
		}
		comboBoxPages.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		btnNextHoaDon = new JButton("❯");
		btnNextHoaDon.setFocusable(false);
		btnNextHoaDon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		panelPages.add(btnPrevHoaDon);
		panelPages.add(comboBoxPages);
		panelPages.add(btnNextHoaDon);
		
		listPanelHoaDon.add(panelPages, BorderLayout.SOUTH);
		
		setDuLieuHoaDon(pages);
		
		// ThemPanel HoaDon
		addPanelHoaDon = new JPanel();
		myPanel.add(addPanelHoaDon, BorderLayout.EAST);
		addPanelHoaDon.setPreferredSize(new Dimension(340, 570));
		addPanelHoaDon.setBackground(Color.decode("#DDDDDD"));
		
		JPanel addPanelHoaDonNorth = new JPanel();
		addPanelHoaDonNorth.setOpaque(false);
		Box b = Box.createVerticalBox();
		
		Box b1, b2, b3, b4, b5;
		
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
		txtmaKH_HD.setPreferredSize(txtmaHoaDon.getPreferredSize());
		txtmaKH_HD.setEditable(false);
		b3.add(Box.createHorizontalStrut(10));
		b3.add(new JLabel("Họ tên KH"));
		b3.add(Box.createHorizontalStrut(5));
		b3.add(txthotenKH_HD = new JTextField(15));
		txthotenKH_HD.setEditable(false);
		b3.add(Box.createHorizontalStrut(5));
		
		b.add(b4 = Box.createHorizontalBox());
		b4.add(Box.createHorizontalStrut(5));	
		comboBoxDangHD = new JComboBox<String>(new String[] {"Kê đơn", "Không kê đơn"});
		b4.add(comboBoxDangHD);
		comboBoxDangHD.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));	
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
		
		JScrollPane spCTHD = new JScrollPane(tableChiTietHoaDon, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
		b2South.add(btnLuuHoaDon = new JButton("Lưu"));
		b2South.add(Box.createHorizontalStrut(10));
		b2South.add(btnThanhToan = new JButton("Thanh toán"));
		b2South.add(Box.createHorizontalStrut(10));
		b2South.add(btnTayTrongHoaDon = new JButton("Tẩy trống"));
		
		addPanelHoaDonSouth.add(bSouth);
		
		addPanelHoaDon.add(addPanelHoaDonSouth, BorderLayout.SOUTH);
		
		//Set Border 0
		listPanelHoaDon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		addPanelHoaDon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		tableHoaDon.addMouseListener(this);
		tableChiTietHoaDon.addMouseListener(this);
		tableChiTietHoaDon.getTableHeader().setDefaultRenderer(headerRenderer);
		
		txtSDT_HD.getDocument().putProperty("owner", txtSDT_HD);
		txtSDT_HD.getDocument().addDocumentListener(this);
		
		txtHoaDon.getDocument().putProperty("owner", txtHoaDon);
		txtHoaDon.getDocument().addDocumentListener(this);
		
		modelChiTietHoaDon.addTableModelListener(this);
		
		btnTaiLaiHoaDon.addActionListener(this);
		comboBoxDVB.addActionListener(this); //Đơn vị bán
		comboBoxDBC.addActionListener(this); //Dạng bào chế
		comboBoxXX.addActionListener(this); //Xuất xứ
		comboBoxSX.addActionListener(this); //Sắp xếp
		
		btnLuuHoaDon.addActionListener(this); //Lưu hóa đơn
		btnThanhToan.addActionListener(this); //Thanh toán hóa đơn
		btnTayTrongHoaDon.addActionListener(this); //Tẩy trống hóa đơn
		
		return myPanel;
	}

	public JPanel panelKhachHang() {
		JPanel myPanel = new JPanel();
		myPanel.setPreferredSize(new Dimension(900, 600));
		myPanel.setBackground(Color.decode("#EEEEEE"));
		
		// ListPanel HoaDon
		listPanelKhachHang = new JPanel();
		myPanel.add(listPanelKhachHang, BorderLayout.CENTER);
		listPanelKhachHang.setPreferredSize(new Dimension(500, 580));//550
		listPanelKhachHang.setLayout(new BorderLayout());
		
		listKH= new ArrayList<KhachHang>();
		
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
		
		JScrollPane sp = new JScrollPane(tableKH, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
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
		
		JScrollPane spTemp = new JScrollPane(tableKH_temp, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
		
		tableKH.getTableHeader().setDefaultRenderer(headerRenderer);
		tableKH_temp.getTableHeader().setDefaultRenderer(headerRenderer);
		
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
	
	// -------------------------------------------------------------------
		JTextField txtFilter;

	public JPanel panelNhanVien() {
		JPanel myPanel = new JPanel();
		myPanel.setPreferredSize(new Dimension(900, 600));
		myPanel.setBackground(Color.decode("#EEEEEE"));
		lblEmail_NV = new JLabel("Email:");
		
		NhanVien_DAO nhanvien_dao;
		CaTruc_DAO catruc_dao;
		
		//panel nhap thong tin nhan vien
		JPanel pnlNhapThongTin = new JPanel(); 
		pnlNhapThongTin.add(Box.createVerticalStrut(25));
		myPanel.add(pnlNhapThongTin);
		pnlNhapThongTin.setPreferredSize(new Dimension(590,240));
		pnlNhapThongTin.setBorder(BorderFactory.createTitledBorder("Nhập và thay đổi thông tin"));
		pnlNhapThongTin.setLayout(new BoxLayout(pnlNhapThongTin, BoxLayout.Y_AXIS));
		
		// box nhap thong tin nhap vien
		Box bRight1, bRight2, bRight3, bRight4, bRight5;
		
		bRight1 = Box.createHorizontalBox();
		lblMa_NV = new JLabel("Mã:");
		lblMa_NV.setPreferredSize(lblEmail_NV.getPreferredSize());
		bRight1.add(lblMa_NV);
		txtMaNV_NV = new JTextField(10);
		txtMaNV_NV.setEditable(false);
		bRight1.add(txtMaNV_NV);
		bRight1.add(Box.createHorizontalStrut(10));
		pnlNhapThongTin.add(bRight1);
		pnlNhapThongTin.add(Box.createVerticalStrut(15));
		
		
		bRight2 = Box.createHorizontalBox();
		lblHo_NV = new JLabel("Họ:");
		lblHo_NV.setPreferredSize(lblEmail_NV.getPreferredSize());
		bRight2.add(lblHo_NV);
		txtHo_NV = new JTextField(10);
		bRight2.add(txtHo_NV);
		bRight2.add(Box.createHorizontalStrut(10));
		bRight2.add(new JLabel("Tên:"));
		txtTen_NV = new JTextField();
		bRight2.add(txtTen_NV);
		bRight2.add(Box.createHorizontalStrut(10));
		bRight2.add(new JLabel("Giới tính:"));
		radNam = new JRadioButton("Nam",true);
		radNam.setEnabled(true);
		radNu = new JRadioButton("Nữ");
		ButtonGroup group = new ButtonGroup();
		group.add(radNam);
		group.add(radNu);
		bRight2.add(radNam);
		bRight2.add(radNu);
		pnlNhapThongTin.add(bRight2);
		pnlNhapThongTin.add(Box.createVerticalStrut(15));
		
		
		bRight3 = Box.createHorizontalBox();
		lblSDT_NV = new JLabel("SĐT: ");
		lblSDT_NV.setPreferredSize(lblEmail_NV.getPreferredSize());
		bRight3.add(lblSDT_NV);
		txtSDT_NV = new JTextField();
		bRight3.add(txtSDT_NV);
		bRight3.add(Box.createHorizontalStrut(10));
		bRight3.add(new JLabel("Ca trực: "));
		comboBoxCaTruc_NV = new JComboBox<String>(new String[] {"Ca sáng", "Ca chiều"});
		bRight3.add(comboBoxCaTruc_NV);
		comboBoxCaTruc_NV.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bRight3.add(Box.createHorizontalStrut(10));
		bRight3.add(new JLabel("Chức vụ: "));
		
		comboBoxChucVu_NV = new JComboBox<String>(new String[] {"Nhân viên", "Quản trị viên"});		
		bRight3.add(comboBoxChucVu_NV);
		comboBoxChucVu_NV.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		pnlNhapThongTin.add(bRight3);
		pnlNhapThongTin.add(Box.createVerticalStrut(15));
	
		bRight4 = Box.createHorizontalBox();
		bRight4.add(lblEmail_NV);
		txtEmail_NV = new JTextField();
		bRight4.add(txtEmail_NV);
		bRight4.add(Box.createHorizontalStrut(10));
		bRight4.add(new JLabel("Tiền lương: "));
		txtTienLuong_NV = new JTextField();
		bRight4.add(txtTienLuong_NV);
		pnlNhapThongTin.add(bRight4);
		pnlNhapThongTin.add(Box.createVerticalStrut(25));
		
		bRight5 = Box.createHorizontalBox();
		bRight5.add(Box.createHorizontalStrut(50));
		btnThem_NV = new JButton("Thêm nhân viên");
		bRight5.add(btnThem_NV);
		bRight5.add(Box.createHorizontalStrut(10));
		btnXoaRong_NV = new JButton("Xóa rỗng");
		bRight5.add(btnXoaRong_NV);
		pnlNhapThongTin.add(bRight5);
		pnlNhapThongTin.add(Box.createVerticalStrut(10));
		
		
		//// PANEL THONG TIN NHAN VIEN
		/// lay thong tin cua nhan vien dang login
		nhanvien_dao = new NhanVien_DAO();
		NhanVien nhanVien = nhanvien_dao.getNhanVienTheoMaNV(nvlogin.getMaNhanVien());
		
		JTextField txtMa,txtHo, txtTen, txtSoDienThoai, txtEmail, txtCaTruc, txtGioiTinh, txtChucVu, txtTienLuong;
		JComboBox<String> cbCaTruc, cbGioiTinh, cbChucVu;
		
		JPanel pnlThongTin = new JPanel();
		JLabel avatar;
		myPanel.add(pnlThongTin);
		pnlThongTin.setPreferredSize(new Dimension(300, 240));
		pnlThongTin.setBorder(BorderFactory.createTitledBorder("Thông tin cá nhân"));
		
		// box thông tin nhân viên
		Box bLeft1, bLeft2, bLeft3, bLeft4;
		pnlThongTin.setLayout(new BoxLayout(pnlThongTin, BoxLayout.Y_AXIS));
		pnlThongTin.add(Box.createVerticalStrut(20));
		
		bLeft1 = Box.createHorizontalBox();
		bLeft1.add(Box.createHorizontalStrut(105));
		bLeft1.add(avatar = new JLabel(new ImageIcon(getClass().getResource("/img/avatar.png"))));
		bLeft1.add(Box.createVerticalStrut(15));
		pnlThongTin.add(bLeft1);
		
		pnlThongTin.add(Box.createVerticalStrut(10));
		bLeft2 = Box.createHorizontalBox();
		bLeft2.add(new JLabel("Mã nhân viên:"));
		txtMa = new JTextField(nhanVien.getMaNhanVien());
		txtMa.setEditable(false);
		bLeft2.add(txtMa);
		pnlThongTin.add(bLeft2);
		
		pnlThongTin.add(Box.createVerticalStrut(10));
		bLeft3 = Box.createHorizontalBox();
		bLeft3.add(new JLabel("Họ:"));
		txtHo = new JTextField(nhanVien.getHoNhanVien());
		txtHo.setEditable(false);
		bLeft3.add(txtHo);
		bLeft3.add(new JLabel("Tên:"));
		txtTen = new JTextField(nhanVien.getTenNhanVien());
		txtTen.setEditable(false);
		bLeft3.add(txtTen);
		bLeft3.add(new JLabel("Phái:"));
		String phai;
		if (nhanVien.isGioiTinh() == true) {
			phai ="Nam";
		}
		else
			phai = "Nữ";
		txtGioiTinh = new JTextField(phai);
		txtGioiTinh.setEditable(false);
		bLeft3.add(txtGioiTinh);
		pnlThongTin.add(bLeft3);
		
		pnlThongTin.add(Box.createVerticalStrut(10));
		bLeft4 = Box.createHorizontalBox();
		bLeft4.add(new JLabel("Ca:"));
		txtCaTruc = new JTextField(nhanVien.getCaTruc().getMaCaTruc());
		txtCaTruc.setEditable(false);
		bLeft4.add(txtCaTruc);
		bLeft4.add(new JLabel("Chức vụ:"));
		txtChucVu = new JTextField(nhanVien.getChucVu());
		txtChucVu.setEditable(false);
		bLeft4.add(txtChucVu);
		pnlThongTin.add(bLeft4);

		////////////// PANEL LOC NHAN VIEN
		JPanel pnlLoc = new JPanel();
		pnlLoc.setPreferredSize(new Dimension(900, 35));
		myPanel.add(pnlLoc);
		Box boxLocNV = Box.createHorizontalBox();
		boxLocNV.add(lblTimMa_NV = new JLabel("Tìm theo mã và nhân viên:"));
		boxLocNV.add(Box.createHorizontalStrut(5));
		boxLocNV.add(txtTimMa_NV = new JTextField(5));
		boxLocNV.add(Box.createHorizontalStrut(5));
		txtTimMa_NV.setName("timKiemMa_NV");
		btnLoc_NV = new JButton("Tìm");
		boxLocNV.add(btnLoc_NV);
		boxLocNV.add(Box.createHorizontalStrut(5));
		
		boxLocNV.add(Box.createHorizontalStrut(5));
		boxLocNV.add(lblLocGioiTinh_NV = new JLabel("Giới tính"));
		boxLocNV.add(Box.createHorizontalStrut(5));
		String[] gioiTinh_NV = {"Tất cả","Nam", "Nữ"};
		comboBoxSortGioiTinh_NV = new JComboBox<String>(gioiTinh_NV);
		boxLocNV.add(comboBoxSortGioiTinh_NV);
		comboBoxSortGioiTinh_NV.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		boxLocNV.add(Box.createHorizontalStrut(5));
		
		boxLocNV.add(Box.createHorizontalStrut(5));
		boxLocNV.add(lblLocCaTruc_NV = new JLabel("Ca trực"));
		boxLocNV.add(Box.createHorizontalStrut(5));
		String[] caTruc_NV = {"Tất cả","Ca sáng","Ca chiều"};
		comboBoxSortCaTruc_NV = new JComboBox<String>(caTruc_NV);
		boxLocNV.add(comboBoxSortCaTruc_NV);
		
		boxLocNV.add(Box.createHorizontalStrut(5));
		boxLocNV.add(lblLocCaTruc_NV = new JLabel("Chức vụ"));
		boxLocNV.add(Box.createHorizontalStrut(5));
		String[] chucVu_NV = {"Tất cả","Nhân viên","Quản trị viên"};
		comboBoxSortChucVu_NV = new JComboBox<String>(chucVu_NV);
		boxLocNV.add(comboBoxSortChucVu_NV);
		
		boxLocNV.add(Box.createHorizontalStrut(10));
		btnXoa_NV = new JButton("Xóa");
		boxLocNV.add(Box.createHorizontalStrut(5));
		boxLocNV.add(btnXoa_NV);
		btnTaiLai_NV = new JButton("Tải lại");
		boxLocNV.add(Box.createHorizontalStrut(5));
		boxLocNV.add(btnTaiLai_NV);
		
		pnlLoc.add(boxLocNV);
		Border cnBorder = BorderFactory.createLoweredBevelBorder();
		pnlLoc.setBorder(cnBorder);
		
		////// PANEL TABLE THONG TIN NHAN VIEN
		JPanel panelTable = new JPanel();
		panelTable.setLayout(new BorderLayout());
		panelTable.setBorder(BorderFactory.createTitledBorder("Danh sách nhân viên"));
		String[] colHeader = {"Mã","Họ","Tên","Số điện thoại","Email","Giới tính","Ca trực","Chức vụ","Tiền lương"};
		
		modelNV = new DefaultTableModel(colHeader, 0) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		
		tableNV = new JTable(modelNV);
		tableNV.setRowHeight(20);
		tableNV.getTableHeader().setDefaultRenderer(headerRenderer);
		
		JScrollPane sp = new JScrollPane(tableNV, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(890,290));
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(JLabel.CENTER);
		DefaultTableCellRenderer right = new DefaultTableCellRenderer();
		right.setHorizontalAlignment(JLabel.RIGHT);
		tableNV.getColumnModel().getColumn(0).setCellRenderer(center);
		tableNV.getColumnModel().getColumn(3).setCellRenderer(center);
		tableNV.getColumnModel().getColumn(4).setCellRenderer(center);
		tableNV.getColumnModel().getColumn(5).setCellRenderer(center);
		tableNV.getColumnModel().getColumn(6).setCellRenderer(center);
		tableNV.getColumnModel().getColumn(7).setCellRenderer(center);
		tableNV.getColumnModel().getColumn(8).setCellRenderer(right);
		
		tableNV.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		panelTable.add(sp, BorderLayout.CENTER);
		setDuLieuNhanVien();
		myPanel.add(panelTable);
		
		txtTimMa_NV.getDocument().putProperty("owner", txtTimMa_NV);
		txtTimMa_NV.getDocument().addDocumentListener(this);
		
		btnThem_NV.addActionListener(this);
		btnXoa_NV.addActionListener(this);
		btnLoc_NV.addActionListener(this);
		btnTaiLai_NV.addActionListener(this);
		btnXoaRong_NV.addActionListener(this);
		comboBoxSortCaTruc_NV.addActionListener(this);
		comboBoxSortChucVu_NV.addActionListener(this);
		comboBoxSortGioiTinh_NV.addActionListener(this);
		tableNV.addMouseListener(this);
		txtMaNV_NV.setText(nhanvien_dao.maNVAuto());
		if(!nvlogin.kiemTraQuyen()) {
			btnXoa_NV.setEnabled(false);
			comboBoxChucVu_NV.removeItemAt(1);
		}
		
		return myPanel;
	}
	
	//====== Hàm thêm không phải panel ====
	
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
	
	public void setDuLieuHoaDon(int pages) {
		
		DefaultTableModel temp = (DefaultTableModel) tableHoaDon.getModel();
		temp.getDataVector().removeAllElements();
		
		List<Thuoc> list = thuoc_dao.getPagesThuoc(pages);
		for(Thuoc thuoc : list) {
			
			DecimalFormat formatter = new DecimalFormat("#,###");
			
			modelHoaDon.addRow(new Object[] {thuoc.getMaThuoc(),thuoc.getTenThuoc(),thuoc.getDonViBan(),
					thuoc.getSoLuong(),thuoc.getXuatXu(),thuoc.getDangBaoChe(),formatter.format(thuoc.getDonGia())});
			
		}
		if(pages==1)
			btnPrevHoaDon.setEnabled(false);
		else
			btnPrevHoaDon.setEnabled(true);
		
		int total = comboBoxPages.getItemCount();
		if(pages==total)
			btnNextHoaDon.setEnabled(false);
		else
			btnNextHoaDon.setEnabled(true);
		
	}
	
	public void TayTrongHoaDon() {
		txtmaHoaDon.setText(hoadon_dao.maHoaDonAuto());
		txtSDT_HD.setText("");
		txthotenKH_HD.setText("");
		txtmaKH_HD.setText("");
		listCTHoaDon.clear();
		lblTongTienHD.setText("<html>Tổng tiền: <b><font color='red'>0</font></b>₫</html>");
		DefaultTableModel temp = (DefaultTableModel) tableChiTietHoaDon.getModel();
		temp.setRowCount(0);
		temp.getDataVector().removeAllElements();
	}
	
	public void setDuLieuChiTietHoaDon() {
		
		DefaultTableModel temp = (DefaultTableModel) tableChiTietHoaDon.getModel();
		temp.getDataVector().removeAllElements();
		
		if(listCTHoaDon.isEmpty())
			return;
		
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
			if(i==row) {
				if(value>ct.getThuocCT().getSoLuong()) {
					JOptionPane.showMessageDialog(main, "Giá trị quá số lượng thuốc.");
				}else {
					ct.setSoLuong(value);
				}
			}
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
			lbl.setForeground(Color.decode("#008000"));
	}
	
	public String tongTienCTHD() {
		
		DecimalFormat formatter = new DecimalFormat("#,###");
		Double tongTien = (double) 0;
		for(CT_HoaDon ct : listCTHoaDon) {
			tongTien += ct.getTongtien()*ct.getSoLuong();
		}
		return "<html>Tổng tiền: <b><font color='red'>"+formatter.format(tongTien)+"</font></b>₫</html>";
		
	}

	////////////////////////XU LY NHAN VIEN///////////////////////////
	public static String removeCharAt(String s, int pos) { 
		return s.substring(0, pos) + s.substring(pos + 1); 
	}

	public void setDuLieuLocNhanVien() {
		modelNV.getDataVector().removeAllElements();
		setDuLieuNhanVien();
		comboBoxSortGioiTinh_NV.setSelectedIndex(0);
		comboBoxSortCaTruc_NV.setSelectedIndex(0);
		comboBoxSortChucVu_NV.setSelectedIndex(0);
		
	}
	
	private boolean xoa1NhanVien(int x) {
		if(x >=0 && x < listNV.size()) {
			listNV.remove(x);
			return true;
		}
		else return false;
	}
	
	public boolean suaNhanVien(String ma, String ho, String ten, String sdt, String email, CaTruc caTruc, boolean gioitinh, String chucVu, Double tienLuong) {
		NhanVien nv = new NhanVien(ma, ho, ten, sdt, email, caTruc, gioitinh ,chucVu,tienLuong);
		if(listNV.contains(nv)) { 
			listNV.set(listNV.indexOf(nv), nv);
			nv.setHoNhanVien(ho);
			nv.setTenNhanVien(ten);;
			nv.setSoDienThoai(sdt);
			nv.setEmailNhanVien(email);
			nv.setCaTruc(caTruc);
			nv.setGioiTinh(gioitinh);
			nv.setChucVu(chucVu);
			nv.setTienLuong(tienLuong);
			return true;
		}
		else return false;
	}
	
	public static void setDuLieuNhanVien() {
		modelNV.getDataVector().removeAllElements();
		DecimalFormat formatter = new DecimalFormat("#,###");
		catruc_dao = new CaTruc_DAO();
		String gioitinh = "";
		for(NhanVien nv : listNV) {
			if(nv.isGioiTinh()) 
				gioitinh = "Nam";
				else
					gioitinh = "Nữ";
			String maCaTruc = nv.getCaTruc().getMaCaTruc();
			nv.setCaTruc(catruc_dao.getCaTrucTheoMaCT(maCaTruc));
			
			modelNV.addRow(new Object[] {nv.getMaNhanVien(),nv.getHoNhanVien(),nv.getTenNhanVien(),
					nv.getSoDienThoai(),nv.getEmailNhanVien(), gioitinh, nv.getCaTruc().getTenCaTruc(),
					nv.getChucVu(), formatter.format(nv.getTienLuong())});
		}
	}
	
	private NhanVien revertNVFromTextfields(boolean flag) {
		nhanvien_dao = new NhanVien_DAO();
		String ma;
		if (flag == false) {
			ma = nhanvien_dao.maNVAuto();
		}
		else
			ma = txtMaNV_NV.getText().trim();		
		String ten = txtTen_NV.getText().trim();
		String ho = txtHo_NV.getText().trim();
		String sdt = txtSDT_NV.getText().trim();
		String email = txtEmail_NV.getText().trim();
		boolean gioiTinh = radNam.isSelected();
		String ca = (String) comboBoxCaTruc_NV.getSelectedItem();
		catruc_dao = new CaTruc_DAO();
		listCaTruc = catruc_dao.getAllCaTruc();
		CaTruc caTruc = new CaTruc(ca);
		if (ca.equalsIgnoreCase("Ca sáng"))
			caTruc = listCaTruc.get(0);
		else
			caTruc = listCaTruc.get(1);
		
		String chucVu = (String) comboBoxChucVu_NV.getSelectedItem();
		Double tienLuong = Double.parseDouble(txtTienLuong_NV.getText());
		return new NhanVien(ma, ho, ten, sdt, email,  caTruc, gioiTinh,chucVu,tienLuong);
	}
	
	public void xoaRongFieldNhanVien() {
		txtMaNV_NV.setText(nhanvien_dao.maNVAuto());
		txtTen_NV.setText("");
		txtHo_NV.setText("");
		txtSDT_NV.setText("");
		txtEmail_NV.setText("");
		txtTienLuong_NV.setText("");
		radNam.setSelected(true);
		comboBoxCaTruc_NV.setSelectedIndex(0);
		comboBoxChucVu_NV.setSelectedIndex(0);
		txtHo_NV.requestFocus();
		tableNV.clearSelection();
	}
	
	private boolean validNV() {
		//String ma = txtma_KH.getText().trim();
		String ten = txtTen_NV.getText().trim();
		String ho = txtHo_NV.getText().trim();
		String sdt = txtSDT_NV.getText().trim();
		String email = txtEmail_NV.getText().trim();
		String tienLuong = txtTienLuong_NV.getText().trim();
		
		if (!(ho.length() > 0 && ho.matches("^[\\p{L}]*$"))) {
			JOptionPane.showMessageDialog(null, "Error: Họ nhân viên không có số hay kí tự đặc biệt");
			txtHo_NV.requestFocus();
			return false;
		}
		
		else if (!(ten.length() > 0 && ten.matches("^[\\p{L}]*(?:\\h+[\\p{L}]*)*$"))) {
			JOptionPane.showMessageDialog(null, "Error: Tên nhân viên là 1 từ, không có số, hay kí tự đặc biệt");
			txtTen_NV.requestFocus();
			return false;
		}
		
		else if(!(sdt.length() > 0 && sdt.matches("^\\d{10}$"))) {
			JOptionPane.showMessageDialog(null, "Error: Số điện thoại là 1 dãy số nguyên có 10 số");
			txtSDT_NV.requestFocus();
			return false;
		}
		
		else if(!(email.length()>=0 && email.matches("^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$"))){
			JOptionPane.showMessageDialog(null, "Sai định dạng email");
			txtEmail_NV.requestFocus();
			return false;
		}
		
		else if(!(tienLuong.length() > 0 && tienLuong.matches("^\\d+$"))) {
			JOptionPane.showMessageDialog(null, "Error: Tiền lương là 1 dãy số nguyên");
			txtSDT_NV.requestFocus();
			return false;
		}
		return true;
	}
	
	private boolean addToListNhanVien(NhanVien nv) {
		if (listNV.contains(nv)){
				return false;
			}
		listNV.add(nv);
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
		
		if(textField.getName().equals("timkiem_hoadon")) {
			String regex = textField.getText().trim();
			
			if(regex.equals("")) 
				setDuLieuHoaDon(1);
			else {
				
				DefaultTableModel temp = (DefaultTableModel) tableHoaDon.getModel();
				temp.getDataVector().removeAllElements();
				
				List<Thuoc> list = thuoc_dao.filterThuoc(regex);
				for(Thuoc thuoc : list) {
					
					DecimalFormat formatter = new DecimalFormat("#,###");
					
					modelHoaDon.addRow(new Object[] {thuoc.getMaThuoc(),thuoc.getTenThuoc(),thuoc.getDonViBan(),
							thuoc.getSoLuong(),thuoc.getXuatXu(),thuoc.getDangBaoChe(),formatter.format(thuoc.getDonGia())});
				}
				
				btnNextHoaDon.setEnabled(false);
				btnPrevHoaDon.setEnabled(false);
				comboBoxPages.setVisible(false);		
				
			}
		}
		
		if(textField.getName().equals("timKiemMa_NV")) {
			String regex = textField.getText().trim();
			
			if(regex.equals("")) 
				setDuLieuNhanVien();
			else {
				modelNV.getDataVector().removeAllElements();
				
				List<NhanVien> list = nhanvien_dao.filterNhanVien(regex);
				for(NhanVien nv : list) {
					
					DecimalFormat formatter = new DecimalFormat("#,###");
					String gioitinh = "";
					if (nv.isGioiTinh()) {
						gioitinh = "Nam";
					}else {
						gioitinh = "Nữ";
					}
					modelNV.addRow(new Object[] {nv.getMaNhanVien(),nv.getHoNhanVien(),nv.getTenNhanVien(),
							nv.getSoDienThoai(),nv.getEmailNhanVien(), gioitinh, nv.getCaTruc().getTenCaTruc(),
							nv.getChucVu(), formatter.format(nv.getTienLuong())});
				}
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
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		Object o = e.getSource();
		/////////////SU KIEN NHAN VIEN/////////////////////////////
		if(o.equals(btnThem_NV)) {
			if (tableNV.getSelectedRow() != -1) {
				JOptionPane.showMessageDialog(null, "Error: Không thể thêm khi đang chọn 1 dòng! (click chuột 2 lần để bỏ chọn)");
			} else {
			DecimalFormat formatter = new DecimalFormat("#,###");

			if(validNV()) {
				NhanVien nv = revertNVFromTextfields(false);
				if(addToListNhanVien(nv)) {
					String gioitinh = "";
					if (nv.isGioiTinh()) {
						gioitinh = "Nam";
					}else {
						gioitinh = "Nữ";
					}
					JOptionPane.showMessageDialog(null, "Thêm thành công");
					modelNV.addRow(new Object[] {nv.getMaNhanVien(),nv.getHoNhanVien(),nv.getTenNhanVien(),
							nv.getSoDienThoai(),nv.getEmailNhanVien(), gioitinh, nv.getCaTruc().getTenCaTruc(),
							nv.getChucVu(), formatter.format(nv.getTienLuong())});
					if (nhanvien_dao.insertNhanVien(nv)) {
						JOptionPane.showMessageDialog(null, "Đã cập nhật lại dữ liệu!");
					}
						
					else
						JOptionPane.showMessageDialog(null, "Error: Lỗi cập nhật dữ liệu!");
				} else
					JOptionPane.showMessageDialog(null,"Error: Dữ liệu bị trùng!");
				
			}
			}
		}
		if(o.equals(btnXoa_NV)) {
			int r = tableNV.getSelectedRow();
			if(r != -1) {
				int tb = JOptionPane.showConfirmDialog(main, "Bạn có muốn xóa dòng này không?", "Delete", JOptionPane.NO_OPTION);
				if(nvlogin.kiemTraQuyen()) {
					if(tb == JOptionPane.YES_OPTION) {
						String maCanXoa = modelNV.getValueAt(r, 0).toString().trim();
						if(nhanvien_dao.xoaNhanVien(maCanXoa)){
							listNV.remove(r);
							modelNV.removeRow(r);	
							JOptionPane.showMessageDialog(main, "Xóa thành công");
							JOptionPane.showMessageDialog(main, "Đã cập nhật lại dữ liệu!");	
							}else
								JOptionPane.showMessageDialog(main, "Error: Lỗi cập nhật dữ liệu!");
					}
				}else 
					JOptionPane.showMessageDialog(main, "Bạn không có quyền xóa.");
			}
			else {
				JOptionPane.showMessageDialog(main, "Bạn chưa chọn dòng xóa!");
			}
		}
		
		if(o.equals(btnXoaRong_NV))
			xoaRongFieldNhanVien();
		
		if(o.equals(comboBoxSortCaTruc_NV)||o.equals(comboBoxSortChucVu_NV)
				||o.equals(comboBoxSortGioiTinh_NV)){
			String gioiTinh = (String) comboBoxSortGioiTinh_NV.getSelectedItem();
			String chucVu = (String) comboBoxSortChucVu_NV.getSelectedItem();
			String caTruc = (String) comboBoxSortCaTruc_NV.getSelectedItem();
			
			ArrayList<NhanVien> dsSort_NV = nhanvien_dao.filterNangCao(gioiTinh, caTruc, chucVu);
			
			if(dsSort_NV.size()==0) {
				JOptionPane.showMessageDialog(main, "Không có dữ liệu.");
				setDuLieuLocNhanVien();
				
			}
			else {
				DefaultTableModel temp = (DefaultTableModel) tableNV.getModel();
				temp.getDataVector().removeAllElements();
				catruc_dao = new CaTruc_DAO();
				for(NhanVien nv : dsSort_NV) {
					
					DecimalFormat formatter = new DecimalFormat("#,###");
					
					String gt; 
					if (nv.isGioiTinh()) {
						gt = "Nam";
					} else
						gt = "Nữ";
				
					String maCaTruc = nv.getCaTruc().getMaCaTruc();
					nv.setCaTruc(catruc_dao.getCaTrucTheoMaCT(maCaTruc));
					modelNV.addRow(new Object[] {nv.getMaNhanVien(),nv.getHoNhanVien(),nv.getTenNhanVien(),nv.getSoDienThoai(),
							nv.getEmailNhanVien(),gt,nv.getCaTruc().getTenCaTruc(),nv.getChucVu(),formatter.format(nv.getTienLuong())});
			
				}
			}
		}
		
		if(o.equals(btnTaiLai_NV)) {
			setDuLieuLocNhanVien();
			xoaRongFieldNhanVien();
			
		}
		
		/////////////SU KIEN HOA DON///////////////////////
		if(o.equals(btnTaiLaiHoaDon)) {
			setDuLieuHoaDon(1);
			
			btnNextHoaDon.setEnabled(true);
			btnPrevHoaDon.setEnabled(true);
			comboBoxPages.setVisible(true);
			
			comboBoxDBC.setSelectedIndex(0);
			comboBoxDVB.setSelectedIndex(0);
			comboBoxXX.setSelectedIndex(0);
			comboBoxSX.setSelectedIndex(0);
			
		}
		
		if(o.equals(comboBoxDBC)||o.equals(comboBoxDVB)
				||o.equals(comboBoxXX)||o.equals(comboBoxSX)) {
			
			String dangBaoChe = (String) comboBoxDBC.getSelectedItem();
			String donViBan = (String) comboBoxDVB.getSelectedItem();
			String xuatXu = (String) comboBoxXX.getSelectedItem();
			String sapXep = (String) comboBoxSX.getSelectedItem();
			
			ArrayList<Thuoc> dslist = thuoc_dao.filterNangCao(donViBan, xuatXu, dangBaoChe, sapXep);
			
			if(dslist.size()==0)
				JOptionPane.showMessageDialog(main, "Không có dữ liệu.");
			
			DefaultTableModel temp = (DefaultTableModel) tableHoaDon.getModel();
			temp.getDataVector().removeAllElements();
			
			for(Thuoc thuoc : dslist) {
				
				DecimalFormat formatter = new DecimalFormat("#,###");
				
				modelHoaDon.addRow(new Object[] {thuoc.getMaThuoc(),thuoc.getTenThuoc(),thuoc.getDonViBan(),
						thuoc.getSoLuong(),thuoc.getXuatXu(),thuoc.getDangBaoChe(),formatter.format(thuoc.getDonGia())});
			}
			
		}
		
		if(o.equals(btnLuuHoaDon)) {
			
			String maKhachHang = txtmaKH_HD.getText().trim();
			
			if(maKhachHang.isBlank()) {
				lblThongBao(lblTBHD, 0, "Số điện thoại đang rỗng hoặc chưa đúng.");
			}else {
				
				String mahoadon = hoadon_dao.maHoaDonAuto();
				Date now = new Date();
				String dangHoaDonStr = (String) comboBoxDangHD.getSelectedItem();
				boolean dangHoaDon = dangHoaDonStr.trim().equals("Kê đơn") ? true : false;				
				
				if(listCTHoaDon.size()==0)
					lblThongBao(lblTBHD, 0, "Chưa có sản phẩm trong hóa đơn");
				else {
					HoaDon hd = new HoaDon(mahoadon, now, new KhachHang(maKhachHang), nvlogin, dangHoaDon, false, 0.0);
					if(hoadon_dao.create(hd, listCTHoaDon)) {
						lblThongBao(lblTBHD, 1, "Lưu hóa đơn thành công!");
						setDuLieuHoaDon(pages);
					} else
						lblThongBao(lblTBHD, 0, "Lưu hóa đơn không thành công!");
				}
				
			}
			
		}
		
		if(o.equals(btnTayTrongHoaDon)) 
			TayTrongHoaDon();
		
		if(o.equals(btnThanhToan)) {
			
			String maKhachHang = txtmaKH_HD.getText().trim();
			if(maKhachHang.isBlank()) { 
				lblThongBao(lblTBHD, 0, "Số điện thoại đang rỗng hoặc chưa đúng.");
			}else {
			
				String mahoadon = hoadon_dao.maHoaDonAuto();
				Date now = new Date();
				String dangHoaDonStr = (String) comboBoxDangHD.getSelectedItem();
				boolean dangHoaDon = dangHoaDonStr.trim().equals("Kê đơn") ? true : false;
				
				HoaDon hd = new HoaDon(mahoadon, now, new KhachHang(maKhachHang), nvlogin, dangHoaDon, false, 0.0);
				
				if(listCTHoaDon.size()==0)
					lblThongBao(lblTBHD, 0, "Danh sách thuốc đang rỗng");
				else
					new xuatHoaDon(nvlogin, hd, listCTHoaDon).setVisible(true);
			
			}
		}
		
		///SỰ KIỆN KHÁCH HÀNG/////
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
		if(o.equals(btnPrevHoaDon)) {
			setDuLieuHoaDon(pages-1);
			pages--;
		}
		if(o.equals(btnNextHoaDon)) {
			setDuLieuHoaDon(pages+1);
			pages++;
		}
		if(o.equals(comboBoxPages)) {
			int item = comboBoxPages.getSelectedIndex()+1;
			setDuLieuHoaDon(item);
			pages=item;
		}
		
		
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
        
        if(e.getClickCount() == 2 && o.equals(tableNV)) {
        	
        	int row = tableNV.getSelectedRow();
        	
        	String maNV = (String) tableNV.getValueAt(row, 0);
        	NhanVien nv = nhanvien_dao.getNhanVienTheoMaNV(maNV.trim());
        	
        	new InfoUser(nvlogin, nv).setVisible(true);
        	
        	tableNV.clearSelection();
        	
        }
        
        if(e.getClickCount() == 2 && o.equals(tableChiTietHoaDon)) {
        	
        	int row = tableChiTietHoaDon.getSelectedRow();
        	int column = tableChiTietHoaDon.getSelectedColumn();
        	
        	tableChiTietHoaDon.clearSelection();
        	
        	if(column == 3)
        		return;

    		if(listCTHoaDon.size()==1)
    			listCTHoaDon.clear();
    		else
    			listCTHoaDon.remove(row);
    		
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
	
		// Khach hang
		private boolean validKH() {
			String ma = txtmaKH_KH.getText().trim();
			String ten = txtTen_KH.getText().trim();
			String ho = txtho_KH.getText().trim();
			String sdt = txtSDT_KH.getText().trim();
			String email = txtEmail_KH.getText().trim();
			
//			if (!(ma.length() > 0 && ma.matches("(KH)\\d{4}"))) {
//				JOptionPane.showMessageDialog(null, "Error: Mã khách hàng theo mẫu: KH + 4 ký số");
//				txtmaKH_KH.requestFocus();
//				return false;
//			}

//			"^[A-Z][a-z]*(?:\\h+[A-Z][a-z]*)*$"
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
//				kh.setMaKhachHang(ma);
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
		
		DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
		    @Override
		    public Component getTableCellRendererComponent(JTable table, Object value,
		            boolean isSelected, boolean hasFocus, int row, int column) {
		        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		        // Thiết lập màu nền cho header
		        setBackground(Color.decode("#3366CC"));
		        setForeground(Color.white);
		        return this;
		    }
		};
		
	public boolean checkSoLuong(int oldSL, int newSL) {
		return oldSL>=newSL;		
	}


}
