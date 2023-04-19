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
	
	private int limit = 25;
	
	private ArrayList<CT_HoaDon> listCTHoaDon;
	
	private Component main;
	
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
		
		nvlogin = nv;
		
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
		btnTaiLaiHoaDon.setBorder(BorderFactory.createCompoundBorder(btnTaiLaiHoaDon.getBorder(), new BottomBorder()));
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
		comboBoxPages = new JComboBox<String>();
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
	
	//====== Hàm thêm không phải panel ====
	
	public void setDuLieuHoaDon(int pages) {
		
		DefaultTableModel temp = (DefaultTableModel) tableHoaDon.getModel();
		temp.getDataVector().removeAllElements();
		
		List<Thuoc> list = thuoc_dao.getPagesThuoc(pages);
		for(Thuoc thuoc : list) {
			
			DecimalFormat formatter = new DecimalFormat("#,###");
			
			modelHoaDon.addRow(new Object[] {thuoc.getMaThuoc(),thuoc.getTenThuoc(),thuoc.getDonViBan(),
					thuoc.getSoLuong(),thuoc.getXuatXu(),thuoc.getDangBaoChe(),formatter.format(thuoc.getDonGia())});
			
		}
	}
	
	public void TayTrongHoaDon() {
		txtmaHoaDon.setText(hoadon_dao.maHoaDonAuto());
		txtSDT_HD.setText("");
		txthotenKH_HD.setText("");
		txtmaKH_HD.setText("");
		listCTHoaDon.clear();
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

}
