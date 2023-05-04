package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;



import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;


import DAO.NhaCungCap_DAO;
import DAO.Thuoc_DAO;
import connectDB.ConnectDB;
import entity.NhaCungCap;
import entity.Thuoc;

public class KhoThuoc extends JFrame implements ActionListener, MouseListener, DocumentListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Thuoc_DAO thuoc_dao;
	protected static NhaCungCap_DAO ncc_dao;
	protected JPanel listPanelKhoThuoc;
	protected JTextField txtKhoThuoc;
	protected JComboBox<String> comboBoxDVB;
	protected JComboBox<String> comboBoxXX;
	protected JComboBox<String> comboBoxDBC;
	protected JComboBox<String> comboBoxSX;
	protected JButton btnTaiLaiKhoThuoc;
	protected DefaultTableModel modelKhoThuoc;
	protected JTable tableKhoThuoc;
	protected JButton btnNextKhoThuoc;
	protected JComboBox<String> comboBoxPages;
	protected JButton btnPrevKhoThuoc;
	protected JPanel panelAddSanPham;
	protected JTextField txtMaSP;
	protected JTextField txtTenSP;
	protected JTextField txtSoLuong;
	protected JTextArea txtThanhPhan;
	protected JTextField txtDonGia;
	protected JTextArea txtCongDung;
	protected JComboBox<String> comboBoxSelectDVB;
	protected JComboBox<String> comboBoxSelectDBC;
	protected JButton btnThem;
	protected JButton btnXoa;
	
	protected Component main;
	
	protected String[] dangBaoCheStr = {"Dung dịch", "Viên sủi", "Bột", "Viên nén", "Viên nhộng", "Khác"};
	protected String[] donViBanStr = {"Hộp", "Chai", "Vỉ", "Tuýp"};
	protected String[] xuatXuStr = {"Việt nam", "Ý", "Hàn quốc","Pháp","Đức","Indonesia"};
	protected String[] nhaCungCapStr;
  	protected JLabel lblTBTenSP;
	protected JLabel lblTBSoLuong;
	protected JLabel lblTBDonGia;
	protected JLabel lblTBThanhPhan;
	protected JLabel lblTBCongDung;
	private JButton btnXoaTrang;
	private JButton btnSua;
	private JButton btnThemNCC;
	protected JComboBox<String> comboBoxSelectXX;
	protected static JComboBox<String> comboBoxSelectNCC;
	protected JFrame frameUpdate;
	protected JButton btnSave;
	protected JButton btnExit;
	protected JFrame frameInfoThuoc;
	protected JTextField txtHSD;

	protected JTextField txtNgayNhap;

	protected JLabel lblTBHSD;
	private DateChooser dateNgayNhap;
	protected DateChooser dateHSD;
	private JPanel p;
	public KhoThuoc() {

		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		thuoc_dao = new Thuoc_DAO();
		ncc_dao = new NhaCungCap_DAO();
		setTitle("Hệ thống Nhà thuốc IUH");
		setSize(900,700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		p = panelKhoThuoc();
		this.add(p);
	}
	
	public static void main(String[] args) {
		new KhoThuoc().setVisible(true);
	}
	
	public JPanel panelKhoThuoc() {
		JPanel myPanel = new JPanel();
		myPanel.setPreferredSize(new Dimension(900, 700));
		myPanel.setBackground(Color.decode("#EEEEEE"));
		
		JPanel panelEnter = new JPanel();
		panelEnter.setBorder(borderTitle("Thông tin sản phẩm"));
		panelEnter.setPreferredSize(new Dimension(890, 190));
		panelEnter.setBackground(Color.decode("#DDDDDD"));
		panelEnter.setLayout(null);
		
		int x = 10 , y = 17, width1 = 85, width2 = 205, height = 20;
		
		JLabel lblMaSP = new JLabel("Mã thuốc: ");
		txtMaSP = new JTextField(thuoc_dao.maThuocAuto(), 5);
		txtMaSP.setEditable(false);
		lblMaSP.setBounds(x, y, width1, height);
		txtMaSP.setBounds(width1, y, width2, height);
		
		JLabel lblTenSP = new JLabel("Tên thuốc: ");
		txtTenSP = new JTextField();
		lblTenSP.setBounds(x, y*3, width1, height);
		txtTenSP.setBounds(width1, y*3, width2, height);
		txtTenSP.setName("tbTenSP");
		lblTBTenSP = new JLabel();
		lblTBTenSP.setBounds(width1, y*4, width2, height);
		panelEnter.add(lblTBTenSP);
		
		
		JLabel lblSoLuong = new JLabel("Số lượng: ");
		txtSoLuong = new JTextField();
		txtSoLuong.setDocument(new PlainDocument());
		((PlainDocument)txtSoLuong.getDocument()).setDocumentFilter(new NumberOnlyFilter());
		lblSoLuong.setBounds(x, y*5, width1, height);
		txtSoLuong.setBounds(width1, y*5, width2, height);
		txtSoLuong.setName("tbSoLuong");
		lblTBSoLuong = new JLabel();
		lblTBSoLuong.setBounds(width1, y*6, width2, height);
		panelEnter.add(lblTBSoLuong);
		
		JLabel lblDonGia = new JLabel("Đơn giá: ");
		txtDonGia = new JTextField();
		txtDonGia.setDocument(new PlainDocument());
		((PlainDocument)txtDonGia.getDocument()).setDocumentFilter(new NumberOnlyFilter());
		lblDonGia.setBounds(x, y*7, width1, height);
		txtDonGia.setBounds(width1, y*7, width2, height);
		txtDonGia.setName("tbDonGia");
		lblTBDonGia = new JLabel();
		lblTBDonGia.setBounds(width1, y*8, width2, height);
		panelEnter.add(lblTBDonGia);
		
		JLabel lblNgayNhap = new JLabel("Ngày nhập: ");
		txtNgayNhap = new JTextField();
		dateNgayNhap = new DateChooser();
		dateNgayNhap.setTextRefernce(txtNgayNhap);
		lblNgayNhap.setBounds(x, y*9, width1, height);
		txtNgayNhap.setBounds(width1, y*9, width2, height);
		txtNgayNhap.setEnabled(false);
		
		
		JLabel lblDVB = new JLabel("Đơn vị bán: ");
		comboBoxSelectDVB = new JComboBox<String>(donViBanStr);
		lblDVB.setBounds(x + width1 + width2, y, width1, height);
		comboBoxSelectDVB.setBounds(x + width1*2 + width2, y, width2, height);
		
		JLabel lblDBC = new JLabel("Dạng bào chế: ");
		comboBoxSelectDBC = new JComboBox<String>(dangBaoCheStr);
		lblDBC.setBounds(x + width1 + width2, y*3, width1, height);
		comboBoxSelectDBC.setBounds(x + width1*2 + width2, y*3, width2, height);
		
		JLabel lblXuatXu = new JLabel("Xuất xứ: ");
		comboBoxSelectXX = new JComboBox<>(xuatXuStr);
		lblXuatXu.setBounds(x + width1 + width2, y*5, width1, height);
		comboBoxSelectXX.setBounds(x + width1*2 + width2, y*5, width2, height);
		
		JLabel lblTenNCC = new JLabel("Nhà cung cấp: ");
		comboBoxSelectNCC = new JComboBox<String>();
		docDuLieuVaoComboboxNCC();
		lblTenNCC.setBounds(x + width1 + width2, y*7, width1, height);
		comboBoxSelectNCC.setBounds(x + width1*2 + width2, y*7, width2, height);
		
		
		JLabel lblHSD = new JLabel("Hạn sử dụng: ");
		txtHSD = new JTextField();
		dateHSD = new DateChooser();
		dateHSD.setTextRefernce(txtHSD);
		lblTBHSD = new JLabel();
		txtHSD.setName("tbHSD");
		lblHSD.setBounds(x + width1 + width2, y*9, width1, height);
		txtHSD.setBounds(x + width1*2 + width2, y*9, width2, height);
		lblTBHSD.setBounds(x + width1*2 + width2, y*10, width2, height);
		
		
		
		JLabel lblThanhPhan = new JLabel("Thành phần: ");
		txtThanhPhan = new JTextArea();
		txtThanhPhan.setLineWrap(true);
		txtThanhPhan.setWrapStyleWord(true);
		txtThanhPhan.setName("tbThanhPhan");
		JScrollPane spThanhPhan = new JScrollPane(txtThanhPhan, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		lblThanhPhan.setBounds(x*2 + width1*2 + width2*2, y, width1, height);
		spThanhPhan.setBounds(x*2 + width1*3 + width2*2, y, width2, height*4);
		
		lblTBThanhPhan = new JLabel();
		lblTBThanhPhan.setBounds(x*2 + width1*3 + width2*2, y*5, width2, height);
		panelEnter.add(lblTBThanhPhan);
		
		JLabel lblCongDung = new JLabel("Công dụng: ");
		txtCongDung = new JTextArea();
		txtCongDung.setLineWrap(true);
		txtCongDung.setWrapStyleWord(true);
		txtCongDung.setName("tbCongDung");
		JScrollPane spCongDung = new JScrollPane(txtCongDung, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		lblCongDung.setBounds(x*2 + width1*2 + width2*2, y*6, width1, height);
		spCongDung.setBounds(x*2 + width1*3 + width2*2, y*6, width2, height*4);
		
		lblTBCongDung = new JLabel();
		lblTBCongDung.setBounds(x*2 + width1*3 + width2*2, y*11, width2, height);
		panelEnter.add(lblTBCongDung);
		
		panelEnter.add(lblMaSP);
		panelEnter.add(txtMaSP);
		panelEnter.add(lblTenSP);
		panelEnter.add(txtTenSP);
		panelEnter.add(lblDVB);
		panelEnter.add(comboBoxSelectDVB);
		panelEnter.add(lblSoLuong);
		panelEnter.add(txtSoLuong);
		panelEnter.add(lblDonGia);
		panelEnter.add(txtDonGia);
		panelEnter.add(lblXuatXu);
		panelEnter.add(comboBoxSelectXX);
		panelEnter.add(lblDBC);
		panelEnter.add(comboBoxSelectDBC);
		panelEnter.add(lblTenNCC);
		panelEnter.add(comboBoxSelectNCC);
		panelEnter.add(lblThanhPhan);
		panelEnter.add(spThanhPhan);
		panelEnter.add(lblCongDung);
		panelEnter.add(spCongDung);
		panelEnter.add(lblNgayNhap);
		panelEnter.add(txtNgayNhap);
		panelEnter.add(lblHSD);
		panelEnter.add(txtHSD);
		panelEnter.add(lblTBHSD);
		
		myPanel.add(panelEnter);
//----------------------------------------------------------------
		JPanel panelButton = new JPanel();
		panelButton.setBorder(borderTitle("Chức năng"));
		panelButton.setLayout(null);
		panelButton.setPreferredSize(new Dimension(890, 50));
		panelButton.setBackground(Color.decode("#DDDDDD"));
		panelButton.add(btnThem = new JButton("Thêm"));
		panelButton.add(btnXoa = new JButton("Xóa"));
		panelButton.add(btnXoaTrang = new JButton("Xóa trắng"));
		panelButton.add(btnSua = new JButton("Sửa"));
		panelButton.add(btnThemNCC = new JButton("Thêm nhà cung cấp"));
		
		btnThem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnThem.setBounds(80, 15, 100, 25);
//		btnThem.setPreferredSize(new Dimension(100, 25));
//		btnThem.setForeground(Color.decode("#ffffff"));
//		btnThem.setBackground(Color.decode("#003399"));
//		btnThem.setBorder(new RoundedBorder(new Color(0, 0, 0, 0), 10));
		btnThem.setFocusable(false);;
		
		btnXoa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnXoa.setBounds(220, 15, 100, 25);
//		btnXoa.setForeground(Color.decode("#ffffff"));
//		btnXoa.setBackground(Color.decode("#003399"));
//		btnXoa.setBorder(new RoundedBorder(new Color(0, 0, 0, 0), 10));
		btnXoa.setFocusable(false);
		
		btnXoaTrang.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnXoaTrang.setBounds(360, 15, 100, 25);
//		btnXoaTrang.setPreferredSize(new Dimension(100, 25));
//		btnXoaTrang.setForeground(Color.decode("#ffffff"));
//		btnXoaTrang.setBackground(Color.decode("#003399"));
//		btnXoaTrang.setBorder(new RoundedBorder(new Color(0, 0, 0, 0), 10));
		btnXoaTrang.setFocusable(false);
		
		btnSua.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSua.setBounds(500, 15, 100, 25);
//		btnSua.setPreferredSize(new Dimension(100, 25));
//		btnSua.setForeground(Color.decode("#ffffff"));
//		btnSua.setBackground(Color.decode("#003399"));
//		btnSua.setBorder(new RoundedBorder(new Color(0, 0, 0, 0), 10));
		btnSua.setFocusable(false);
		
		btnThemNCC.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnThemNCC.setBounds(640, 15, 150, 25);
//		btnThemNCC.setPreferredSize(new Dimension(150, 25));
//		btnThemNCC.setForeground(Color.decode("#ffffff"));
//		btnThemNCC.setBackground(Color.decode("#003399"));
//		btnThemNCC.setBorder(new RoundedBorder(new Color(0, 0, 0, 0), 10));
		btnThemNCC.setFocusable(false);
		
		myPanel.add(panelButton);	
//-----------------------------------------------------------------------------------
		
		JPanel panelList = new JPanel();
		panelList.setBorder(borderTitle("Danh sách"));
		panelList.setLayout(null);
		panelList.setPreferredSize(new Dimension(890, 330));
		panelList.setBackground(Color.decode("#DDDDDD"));
		
		JPanel filterPanel = new JPanel();
		filterPanel.setOpaque(false);
		filterPanel.setBounds(0, 10, 890, 40);
		
		JLabel lblTimTen = new JLabel("Tìm tên: ");
		txtKhoThuoc = new JTextField(10);
		txtKhoThuoc.setName("timkiem_khothuoc");
//		txtKhoThuoc.getDocument().addDocumentListener(new DocumentListener() {
//		    @Override
//		    public void insertUpdate(DocumentEvent e) {
//		        searchProduct();
//		    }
//
//		    @Override
//		    public void removeUpdate(DocumentEvent e) {
//		        searchProduct();
//		    }
//
//		    @Override
//		    public void changedUpdate(DocumentEvent e) {
//		        searchProduct();
//		    }
//
//		    private void searchProduct() {
//		    	String searchText = txtKhoThuoc.getText().trim();
//		    	TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableKhoThuoc.getModel());
//		    	tableKhoThuoc.setRowSorter(sorter);
//		    	RowFilter<TableModel, Object> filter1 = RowFilter.regexFilter("(?i)" + searchText, 1);
////		    	RowFilter<TableModel, Object> filter2 = RowFilter.regexFilter("(?i)" + searchText, 3);
////		    	RowFilter<TableModel, Object> filter = RowFilter.orFilter(Arrays.asList(filter1, filter2));
//		    	sorter.setRowFilter(filter1);
//		    }
//		});
		filterPanel.add(lblTimTen);
		filterPanel.add(txtKhoThuoc);
		
		JLabel lblTimDV = new JLabel("Tìm đơn vị: ");
		comboBoxDVB = new JComboBox<String>();
		comboBoxDVB.addItem("Tất cả");
		for(String item : donViBanStr) {
			comboBoxDVB.addItem(item);
		}
		comboBoxDVB.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		filterPanel.add(lblTimDV);
		filterPanel.add(comboBoxDVB);
		
		JLabel lblTimXX = new JLabel("Tìm xuất xứ: ");
		ArrayList<String> xuatXuStr = thuoc_dao.getXuatXu();
		comboBoxXX = new JComboBox<String>();
		comboBoxXX.addItem("Tất cả");
		for(String s : xuatXuStr) {
			comboBoxXX.addItem(s);
		}
		comboBoxXX.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		filterPanel.add(lblTimXX);
		filterPanel.add(comboBoxXX);
		
		JLabel lblTimDang = new JLabel("Tìm dạng: ");
		comboBoxDBC = new JComboBox<String>();
		comboBoxDBC.addItem("Tất cả");
		for(String item : dangBaoCheStr) {
			comboBoxDBC.addItem(item);
		}
		comboBoxDBC.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		filterPanel.add(lblTimDang);
		filterPanel.add(comboBoxDBC);
		
		JLabel lblTSX = new JLabel("Sắp xếp");
		String[] sapXepStr = {"Mới-Cũ", "Cũ-Mới"};
		comboBoxSX = new JComboBox<String>(sapXepStr);
		comboBoxSX.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		filterPanel.add(lblTSX);
		filterPanel.add(comboBoxSX);
		
		btnTaiLaiKhoThuoc = new JButton("Tải lại");
		btnTaiLaiKhoThuoc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		filterPanel.add(btnTaiLaiKhoThuoc);
		
		panelList.add(filterPanel);
//==============================table====================
		
		JPanel panelTable = new JPanel();
		panelTable.setOpaque(false);
		panelTable.setBounds(0, 40, 890, 250);
		
		String[] colHeader = {"Mã","Tên","Đơn vị bán","Số lượng","Xuất xứ","Dạng","Đơn giá"};
		
		modelKhoThuoc = new DefaultTableModel(colHeader, 0) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableKhoThuoc = new JTable(modelKhoThuoc);
		tableKhoThuoc.setRowHeight(20);
		JScrollPane sp = new JScrollPane(tableKhoThuoc, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,  JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(870, 245));
		
		DefaultTableCellRenderer right = new DefaultTableCellRenderer();
		right.setHorizontalAlignment(JLabel.RIGHT);
		tableKhoThuoc.getColumnModel().getColumn(1).setPreferredWidth(200);
		tableKhoThuoc.getColumnModel().getColumn(3).setCellRenderer(right);
		tableKhoThuoc.getColumnModel().getColumn(6).setCellRenderer(render);
		tableKhoThuoc.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		panelTable.add(sp);
		
		setDuLieuKhoThuoc(1);
		
		JPanel panelPages = new JPanel();
		panelPages.setOpaque(false);
		panelPages.setBounds(0, 290, 890, 35);
		
		btnNextKhoThuoc = new JButton("❮");
		btnNextKhoThuoc.setFocusable(false);
		btnNextKhoThuoc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		int total = (int) Math.ceil(thuoc_dao.totalThuoc()*1.0/25);
		comboBoxPages = new JComboBox<String>();
		comboBoxPages.setPreferredSize(btnNextKhoThuoc.getPreferredSize());
		comboBoxPages.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		for (int i=1; i <= total; i++) {
			comboBoxPages.addItem(i+"");
		}
		
		btnPrevKhoThuoc = new JButton("❯");
		btnPrevKhoThuoc.setFocusable(false);
		btnPrevKhoThuoc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		panelPages.add(btnNextKhoThuoc);
		panelPages.add(comboBoxPages);
		panelPages.add(btnPrevKhoThuoc);
		
		panelList.add(panelTable);	
		panelList.add(panelPages);	
		
		myPanel.add(panelList);
		
		txtKhoThuoc.getDocument().putProperty("owner", txtKhoThuoc);
		txtKhoThuoc.getDocument().addDocumentListener(this);
		
		btnTaiLaiKhoThuoc.addActionListener(this);
		comboBoxDVB.addActionListener(this); 
		comboBoxDBC.addActionListener(this);
		comboBoxXX.addActionListener(this);
		comboBoxSX.addActionListener(this); 
		btnThem.addActionListener(this);
		btnXoa.addActionListener(this);
		btnXoaTrang.addActionListener(this);
		tableKhoThuoc.addMouseListener(this);
		btnThemNCC.addActionListener(this);
		btnSua.addActionListener(this);
		
		txtTenSP.getDocument().putProperty("owner", txtTenSP);
		txtTenSP.getDocument().addDocumentListener(this);
		
		txtSoLuong.getDocument().putProperty("owner", txtSoLuong);
		txtSoLuong.getDocument().addDocumentListener(this);
		
		txtDonGia.getDocument().putProperty("owner", txtDonGia);
		txtDonGia.getDocument().addDocumentListener(this);
		
		txtCongDung.getDocument().putProperty("owners", txtCongDung);
		txtCongDung.getDocument().addDocumentListener(this);
		
		txtThanhPhan.getDocument().putProperty("owners", txtThanhPhan);
		txtThanhPhan.getDocument().addDocumentListener(this);
		
		txtHSD.getDocument().putProperty("owner", txtHSD);
		txtHSD.getDocument().addDocumentListener(this);
		
		return myPanel;
	}
	
	
	
	
	
	public void readDataIntoTxt(Thuoc thuoc) {
		txtMaSP.setText(thuoc.getMaThuoc());
		txtTenSP.setText(thuoc.getTenThuoc());
		txtSoLuong.setText(Integer.toString(thuoc.getSoLuong()));
		DecimalFormat format = new DecimalFormat("#");
		String donGia = format.format(thuoc.getDonGia());
		txtDonGia.setText(donGia);
		txtThanhPhan.setText(thuoc.getThanhPhan());
		txtCongDung.setText(thuoc.getCongDung());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		txtNgayNhap.setText(dateFormat.format(thuoc.getNgayNhapThuoc()));
		txtHSD.setText(dateFormat.format(thuoc.getHanSuDung()));
		String dvb = thuoc.getDonViBan();
		for (int i = 0; i < comboBoxSelectDVB.getItemCount(); i++) {
			if(dvb.compareTo((String) comboBoxSelectDVB.getItemAt(i)) == 0) {
				comboBoxSelectDVB.setSelectedIndex(i);
			}
		}
		String dbc = thuoc.getDangBaoChe();
		for (int i = 0; i < comboBoxSelectDBC.getItemCount(); i++) {
			if(dbc.compareTo(comboBoxSelectDBC.getItemAt(i)) == 0) {
				comboBoxSelectDBC.setSelectedIndex(i);
			}
		}
		String xx = thuoc.getXuatXu();
		for (int i = 0; i < comboBoxSelectXX.getItemCount(); i++) {
			if(xx.compareTo(comboBoxSelectXX.getItemAt(i)) == 0) {
				
				comboBoxSelectXX.setSelectedIndex(i);
			}
		}
		String tenNCC = thuoc.getNcc().getTenNCC();
		for(int i = 0; i < comboBoxSelectNCC.getItemCount(); i++) {
			if(tenNCC.compareTo(comboBoxSelectNCC.getItemAt(i)) == 0) {
				comboBoxSelectNCC.setSelectedIndex(i);
			}
		}
	}
	
	public void update() throws ParseException {
		removeTextlblTB();
		if(validData()) {
			String maSP = txtMaSP.getText();
			String tenSP = txtTenSP.getText();
			String dvb = (String) comboBoxSelectDVB.getSelectedItem();
			int soLuong = Integer.parseInt(txtSoLuong.getText());
			double donGia = Double.parseDouble(txtDonGia.getText());
			String xuatXu = (String) comboBoxSelectXX.getSelectedItem();
			String dang = (String) comboBoxSelectDBC.getSelectedItem();
			String thanhPhan = txtThanhPhan.getText();
			String congDung = txtCongDung.getText();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date ngayNhap = (Date) dateFormat.parse(txtNgayNhap.getText());
			Date HSD = dateFormat.parse(txtHSD.getText());
			String tenNCC = (String) comboBoxSelectNCC.getSelectedItem();
			NhaCungCap ncc = ncc_dao.getNCCTheoTenNCC(tenNCC);
			Thuoc thuoc = new Thuoc(maSP, tenSP, dvb, soLuong, donGia, thanhPhan, xuatXu, congDung, dang, ngayNhap, HSD, ncc);
			
			if(thuoc_dao.update(thuoc)) {
				JOptionPane.showMessageDialog(this, "Cập nhật thành công");
				reload();
			} else {
				JOptionPane.showMessageDialog(this, "Cập nhật không thành công");
			}
		}
	}
	
	public static void docDuLieuVaoComboboxNCC() {	
		comboBoxSelectNCC.removeAllItems();
		for(String tenNCC : ncc_dao.getTenNCC()) {
			comboBoxSelectNCC.addItem(tenNCC);
		}
	}
	
	public void lblThongBao(JLabel lbl, int x, String str) {
		lbl.setFont(new Font("Serif", Font.ITALIC, 13));
		lbl.setText(str);
		if(x==0)
			lbl.setForeground(Color.red);
		else if(x==1)
			lbl.setForeground(Color.decode("#008000"));
	}
	
	
	public void delete() {
		int row = tableKhoThuoc.getSelectedRow();
		if(row < 0 || row > tableKhoThuoc.getRowCount()) {
			JOptionPane.showMessageDialog(this, "Bạn chưa chọn sản phẩm để xóa");
		} else {
			String maSP = modelKhoThuoc.getValueAt(row, 0).toString();	
			Thuoc thuoc = thuoc_dao.getThuocTheoMaThuoc(maSP);
			if(JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa thuốc có tên: "  + thuoc.getTenThuoc(), "Thông báo", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				if(thuoc_dao.delete(thuoc)) {
					reload();
				}		
			}
		}	
	}
	
	public void xoaTrang() {
		txtMaSP.setText(thuoc_dao.maThuocAuto());
		txtTenSP.setText("");
		Document doc = txtSoLuong.getDocument();
		Document doc1 = txtDonGia.getDocument();
		if(doc.getLength() > 0) {
			try {
				doc.remove(0, doc.getLength());
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(doc1.getLength() > 0) {
			try {
				doc1.remove(0, doc1.getLength());
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		dateHSD.toDay();
		txtThanhPhan.setText("");
		txtCongDung.setText("");
		comboBoxSelectDBC.setSelectedIndex(0);
		comboBoxSelectDVB.setSelectedIndex(0);
		comboBoxSelectXX.setSelectedIndex(0);
		comboBoxSelectNCC.setSelectedIndex(0);
		lblTBTenSP.setText("");
		lblTBHSD.setText("");
		lblTBDonGia.setText("");
		lblTBSoLuong.setText("");
	}
	
	
	
	public void add() throws ParseException {
		removeTextlblTB();
		if(validData()) {
			String maSP = thuoc_dao.maThuocAuto();
			String tenSP = txtTenSP.getText();
			String dvb = (String) comboBoxSelectDVB.getSelectedItem();
			int soLuong = Integer.parseInt(txtSoLuong.getText());
			double donGia = Double.parseDouble(txtDonGia.getText());
			String xuatXu = (String) comboBoxSelectXX.getSelectedItem();
			String dang = (String) comboBoxSelectDBC.getSelectedItem();
			String thanhPhan = txtThanhPhan.getText();
			String congDung = txtCongDung.getText();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date ngayNhap = dateFormat.parse(txtNgayNhap.getText());
			Date HSD = dateFormat.parse(txtHSD.getText());
			String tenNCC = (String) comboBoxSelectNCC.getSelectedItem();
			NhaCungCap ncc = ncc_dao.getNCCTheoTenNCC(tenNCC);
			
			Thuoc thuoc = new Thuoc(maSP, tenSP, dvb, soLuong, donGia, thanhPhan, xuatXu, congDung, dang, ngayNhap, HSD, ncc);
			
			
			if(thuoc_dao.create(thuoc)) {
				DecimalFormat format = new DecimalFormat("#,###");
				
				modelKhoThuoc.addRow(new Object[] {thuoc.getMaThuoc(),thuoc.getTenThuoc(),thuoc.getDonViBan(),
						thuoc.getSoLuong(),thuoc.getXuatXu(),thuoc.getDangBaoChe(),format.format(thuoc.getDonGia())});
				JOptionPane.showMessageDialog(this, "Thêm thành công");
				xoaTrang();
				reload();
			}
			else {
				JOptionPane.showMessageDialog(this, "Mã sản phẩm đã tồn tại");
			}
		}		
	}
	
	public Border borderTitle(String title) {
		Border border;
		border = BorderFactory.createTitledBorder(
			    BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1), title);
		((TitledBorder) border).setTitleFont(new Font("Time New Roman", Font.ITALIC, 10));
		return border;
	}
	
	public void removeTextlblTB() {
		lblTBDonGia.setText("");
		lblTBSoLuong.setText("");
		lblTBTenSP.setText("");
		lblTBCongDung.setText("");
		lblTBThanhPhan.setText("");
	}
	
	public void setDuLieuKhoThuoc(int pages) {
		DefaultTableModel dtm = (DefaultTableModel) tableKhoThuoc.getModel();
		dtm.getDataVector().removeAllElements();
		
		List<Thuoc> list = thuoc_dao.getPagesThuoc(pages);
		for(Thuoc thuoc : list) {
			DecimalFormat format = new DecimalFormat("#,###");
			
			modelKhoThuoc.addRow(new Object[] {thuoc.getMaThuoc(),thuoc.getTenThuoc(),thuoc.getDonViBan(),
					thuoc.getSoLuong(),thuoc.getXuatXu(),thuoc.getDangBaoChe(),format.format(thuoc.getDonGia())});
		}
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
	
	
	protected void reload(){
		setDuLieuKhoThuoc(1);
		
		btnNextKhoThuoc.setEnabled(true);
		btnPrevKhoThuoc.setEnabled(true);
		comboBoxPages.setVisible(true);
		
		comboBoxDBC.setSelectedIndex(0);
		comboBoxDVB.setSelectedIndex(0);
		comboBoxXX.setSelectedIndex(0);
		comboBoxSX.setSelectedIndex(0);
	}

	private SuaThuoc sua;
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(btnSave)) {
			try {
				update();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(o.equals(btnExit)) {
			frameUpdate.dispose();
		}
		if(o.equals(btnSua)) {
			int row = tableKhoThuoc.getSelectedRow();
			
			if(row < 0 || row >= tableKhoThuoc.getRowCount()) {
				JOptionPane.showMessageDialog(this, "Bạn chưa chọn thuốc muốn sửa");
			}
			else {
				sua = new SuaThuoc();
				String maThuoc = modelKhoThuoc.getValueAt(row, 0).toString();
				Thuoc thuoc = thuoc_dao.getThuocTheoMaThuoc(maThuoc);
				sua.guiUpdate(thuoc).setVisible(true);
			}
		}
		if(o.equals(btnXoaTrang)) {
			xoaTrang();
		}
		if(o.equals(btnXoa)) {
			delete();
		}
		
		if(o.equals(btnThem)) {
			try {
				add();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if(o.equals(btnTaiLaiKhoThuoc)) {
			reload();
		}
		
		if(o.equals(btnThemNCC)) {
			new NhaCungCap_GUI().setVisible(true);
		}
		
		if(o.equals(comboBoxDBC)||o.equals(comboBoxDVB) ||o.equals(comboBoxXX)||o.equals(comboBoxSX)) {
			
			String dangBaoChe = (String) comboBoxDBC.getSelectedItem();
			String donViBan = (String) comboBoxDVB.getSelectedItem();
			String xuatXu = (String) comboBoxXX.getSelectedItem();
			String sapXep = (String) comboBoxSX.getSelectedItem();
			
			ArrayList<Thuoc> dslist = thuoc_dao.filterNangCao(donViBan, xuatXu, dangBaoChe, sapXep);
			
			if(dslist.size()==0)
				JOptionPane.showMessageDialog(main, "Không có dữ liệu.");
			
			DefaultTableModel dtm = (DefaultTableModel) tableKhoThuoc.getModel();
			dtm.getDataVector().removeAllElements();
			
			for(Thuoc thuoc : dslist) {
				
				DecimalFormat formatter = new DecimalFormat("#,###");
				
				modelKhoThuoc.addRow(new Object[] {thuoc.getMaThuoc(),thuoc.getTenThuoc(),thuoc.getDonViBan(),
						thuoc.getSoLuong(),thuoc.getXuatXu(),thuoc.getDangBaoChe(),formatter.format(thuoc.getDonGia())});
			}	
		}
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
//		int row = tableKhoThuoc.getSelectedRow();
//		String maSP = modelKhoThuoc.getValueAt(row, 0).toString();
//		Thuoc thuoc = thuoc_dao.getThuocTheoMaThuoc(maSP);
//		txtMaSP.setText(thuoc.getMaThuoc());
//		txtTenSP.setText(thuoc.getTenThuoc());
//		txtSoLuong.setText(Integer.toString(thuoc.getSoLuong()));
//		txtDonGia.setText(Double.toString(thuoc.getDonGia()));
//		txtThanhPhan.setText(thuoc.getThanhPhan());
//		txtCongDung.setText(thuoc.getCongDung());
//		snNgayNhap.setValue(thuoc.getNgayNhapThuoc());
//		snHSD.setValue(thuoc.getHanSuDung());
//		String dvb = thuoc.getDonViBan();
//		for (int i = 0; i < comboBoxSelectDVB.getItemCount(); i++) {
//			if(dvb.compareTo(comboBoxSelectDVB.getItemAt(i)) == 0) {
//				comboBoxSelectDVB.setSelectedIndex(i);
//			}
//		}
//		String dbc = thuoc.getDangBaoChe();
//		for (int i = 0; i < comboBoxSelectDBC.getItemCount(); i++) {
//			if(dbc.compareTo(comboBoxSelectDBC.getItemAt(i)) == 0) {
//				comboBoxSelectDBC.setSelectedIndex(i);
//			}
//		}
//		String xx = thuoc.getDangBaoChe();
//		for (int i = 0; i < comboBoxSelectXX.getItemCount(); i++) {
//			if(xx.compareTo(comboBoxSelectXX.getItemAt(i)) == 0) {
//				comboBoxSelectXX.setSelectedIndex(i);
//			}
//		}
	
	}
	
	private InfoThuoc infoThuoc;
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (e.getClickCount() == 2 && o.equals(tableKhoThuoc)) {
            int row = tableKhoThuoc.getSelectedRow();
            String maThuoc = (String) tableKhoThuoc.getValueAt(row, 0);
            
            Thuoc thuoc = thuoc_dao.getThuocTheoMaThuoc(maThuoc);
            infoThuoc = new InfoThuoc();
            infoThuoc.infoThuoc(thuoc).setVisible(true);
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

	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
		JTextField textField = (JTextField) e.getDocument().getProperty("owner");
		JTextArea textArea = (JTextArea) e.getDocument().getProperty("owners");
		
		if(textField != null) {
			if(textField.getName().equals("timkiem_khothuoc")) {				
				String regex = textField.getText().trim();
				
				if(regex.equals("")) 
					setDuLieuKhoThuoc(1);
				else {
					
					DefaultTableModel temp = (DefaultTableModel) tableKhoThuoc.getModel();
					temp.getDataVector().removeAllElements();
					
					List<Thuoc> list = thuoc_dao.filterThuoc(regex);
					for(Thuoc thuoc : list) {
						
						DecimalFormat formatter = new DecimalFormat("#,###");
						
						modelKhoThuoc.addRow(new Object[] {thuoc.getMaThuoc(),thuoc.getTenThuoc(),thuoc.getDonViBan(),
								thuoc.getSoLuong(),thuoc.getXuatXu(),thuoc.getDangBaoChe(),formatter.format(thuoc.getDonGia())});
					}
					
					btnNextKhoThuoc.setEnabled(false);
					btnPrevKhoThuoc.setEnabled(false);
					comboBoxPages.setVisible(false);		
					
				}
			}
			if(textField.getName().equals("tbTenSP")) {
				String tenSP = txtTenSP.getText().trim();
				if(!tenSP.matches("^[\\p{L}0-9 .]+$")) {
					lblThongBao(lblTBTenSP, 0, "Sai tên sản phẩm");
				}
				else {
					lblThongBao(lblTBTenSP, 1, "");
				}
				
			}
			if(textField.getName().equals("tbSoLuong")) {
				String soLuong = txtSoLuong.getText().trim();
				if(!soLuong.matches("^[0-9]+$")) {
					lblThongBao(lblTBSoLuong, 0, "Sai số lượng");
				}
				else {
					lblThongBao(lblTBSoLuong, 1, "");
				}
			}
			if(textField.getName().equals("tbDonGia")) {
				String donGia = txtDonGia.getText().trim();
				if(!donGia.matches("^[1-9][0-9]*$")) {
					lblThongBao(lblTBDonGia, 0, "Sai đơn giá ");
				}
				else {
					lblThongBao(lblTBDonGia, 1, "");
				}
			}
			if(textField.getName().equals("tbHSD")) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date ngayNhap = null;
				try {
					ngayNhap = dateFormat.parse(txtNgayNhap.getText());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Date HSD = null;
				try {
					HSD = dateFormat.parse(txtHSD.getText());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(ngayNhap.compareTo(HSD) > 0) {
					lblThongBao(lblTBHSD, 0, "Hạn sử dụng phải lớn hơn ngày nhập");
				}
				else {
					lblThongBao(lblTBHSD, 1, "");
				}
			}
		}
		
		if(textArea != null) {
			if(textArea.getName().equals("tbThanhPhan")) {
				String thanhPhan = txtThanhPhan.getText().trim();
				if(thanhPhan.length() <= 0) {
					lblThongBao(lblTBThanhPhan, 0, "Bạn chưa nhập thành phần");
				}
				else {
					lblThongBao(lblTBThanhPhan, 1, "");
				}
			}
			if(textArea.getName().equals("tbCongDung")) {
				String congDung = txtCongDung.getText().trim();
				if(congDung.length() <= 0) {
					lblThongBao(lblTBCongDung, 0, "Bạn chưa nhập công dụng");
				}
				else {
					lblThongBao(lblTBCongDung, 1, "");
				}
			}
		}
	}
	
	public boolean validData() {
		int sum = 0;
		String tenSP = txtTenSP.getText().trim();
		if(!tenSP.matches("^[\\p{L}0-9 .]+$")) {
			lblThongBao(lblTBTenSP, 0, "Sai tên sản phẩm");
			sum++;
		}
		
		String soLuong = txtSoLuong.getText().trim();
		if(!soLuong.matches("^[0-9]+$")) {
			lblThongBao(lblTBSoLuong, 0, "Số lượng không được rỗng");
			sum++;
		}
		String donGia = txtDonGia.getText().trim();
		if(!donGia.matches("^[1-9][0-9]*$")){
			lblThongBao(lblTBDonGia, 0, "Đơn giá không được rỗng");
			sum++;
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date ngayNhap = null;
		try {
			ngayNhap = dateFormat.parse(txtNgayNhap.getText());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Date HSD = null;
		try {
			HSD = dateFormat.parse(txtHSD.getText());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(ngayNhap.compareTo(HSD) > 0) {
			lblThongBao(lblTBHSD, 0, "Hạn sử dụng phải lớn hơn ngày nhập");
			sum++;
		}
		else {
			lblThongBao(lblTBHSD, 1, "");
		}
		
//		String thanhPhan = txtThanhPhan.getText().trim();
//		if(thanhPhan.length() <= 0) {
//			lblThongBao(lblTBThanhPhan, 0, "Bạn chưa nhập thành phần");
//			sum++;
//		}
//		
//		String congDung = txtCongDung.getText().trim();
//		if(congDung.length() <= 0) {
//			lblThongBao(lblTBCongDung, 0, "Bạn chưa nhập công dụng");
//			sum++;
//		}
		
		if(sum > 0) {
			return false;
		}
		
		return true;
	}
	
	

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public class NumberOnlyFilter extends DocumentFilter {
	    public void insertString(FilterBypass fb, int offset, String string,
	            AttributeSet attr) throws BadLocationException {

	        Document doc = fb.getDocument();
	        StringBuilder sb = new StringBuilder();
	        sb.append(doc.getText(0, doc.getLength()));
	        sb.insert(offset, string);

	        if (isNumber(sb.toString())) {
	            super.insertString(fb, offset, string, attr);
	        }
	    }
	    
	    public void replace(FilterBypass fb, int offset, int length, String text,
	            AttributeSet attrs) throws BadLocationException {

	        Document doc = fb.getDocument();
	        StringBuilder sb = new StringBuilder();
	        sb.append(doc.getText(0, doc.getLength()));
	        sb.replace(offset, offset + length, text);

	        if (isNumber(sb.toString())) {
	            super.replace(fb, offset, length, text, attrs);
	        }
	    }

	    public void remove(FilterBypass fb, int offset, int length)
	            throws BadLocationException {
	        Document doc = fb.getDocument();
	        StringBuilder sb = new StringBuilder();
	        sb.append(doc.getText(0, doc.getLength()));
	        sb.delete(offset + 1, offset + length);

	        if (isNumber(sb.toString())) {
	            super.remove(fb, offset, length);
	        }
	    }
	    
	    private boolean isNumber(String text) {
	        try {
	            Integer.parseInt(text);
	            return true;
	        } catch (NumberFormatException e) {
	            return false;
	        }
	    }
	}
}