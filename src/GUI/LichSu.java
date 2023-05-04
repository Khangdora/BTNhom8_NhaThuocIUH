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
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import DAO.CTHoaDon_DAO;
import DAO.HoaDon_DAO;
import DAO.KhachHang_DAO;
import DAO.NhaCungCap_DAO;
import DAO.NhanVien_DAO;
import DAO.Thuoc_DAO;
import connectDB.ConnectDB;
import entity.CT_HoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.Thuoc;
import others.PlaceholderTextField;

public class LichSu extends JFrame implements ActionListener, MouseListener, DocumentListener {
	
	private JPanel listPanelLichSu;
	private JComboBox<String> cbSort, cbNhanVien, cbAZ, cbKeDon, comboBoxPages;
	
	private JTextField txtLoc;
	private JButton btnTaiLai, btnNextHoaDon, btnPrevHoaDon;
	
	public static Thuoc_DAO thuoc_dao;
	public static NhaCungCap_DAO ncc_dao;
	public static NhanVien_DAO nhanvien_dao;
	public static HoaDon_DAO hoadon_dao;
	public static KhachHang_DAO khachhang_dao;
	public static CTHoaDon_DAO cthoadon_dao;
	
	public static DefaultTableModel modelLichSu;
	public static JTable tableLichSu;
	private NhanVien nvlogin;
	
	public LichSu(NhanVien nvlogin) {
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		thuoc_dao = new Thuoc_DAO();
		ncc_dao = new NhaCungCap_DAO();
		nhanvien_dao = new NhanVien_DAO();
		hoadon_dao = new HoaDon_DAO();
		khachhang_dao = new KhachHang_DAO();
		cthoadon_dao = new CTHoaDon_DAO();
		
//		setTitle("Hệ thống Nhà thuốc IUH");
//		setSize(900, 600);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		setLocationRelativeTo(null);
//		setResizable(false);
//		this.add(panelLichSu());
		
	}
	
	public JPanel panelLichSu() {
		JPanel myPanel = new JPanel();
		myPanel.setPreferredSize(new Dimension(900, 600));
		myPanel.setBackground(Color.decode("#EEEEEE"));
		
		// ListPanel Lịch Sử
		listPanelLichSu = new JPanel();
		myPanel.add(listPanelLichSu, BorderLayout.CENTER);
		listPanelLichSu.setPreferredSize(new Dimension(900, 600));
		listPanelLichSu.setLayout(new BorderLayout());
		
		JPanel locPanel = new JPanel();
		locPanel.setPreferredSize(new Dimension(550, 50));
		
		Box box = Box.createHorizontalBox();
		box.add(txtLoc = new PlaceholderTextField("Tra mã hóa đơn, SĐT/tên khách hàng"));
		box.add(Box.createHorizontalStrut(5));
		
		txtLoc.setMinimumSize(new Dimension(100, 20));
		cbNhanVien = new JComboBox<String>();
		ArrayList<NhanVien> dsNhanVien = nhanvien_dao.getallNhanVien();
		cbNhanVien.addItem("Tất cả nhân viên");
		cbNhanVien.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		for(NhanVien nv : dsNhanVien) {
			cbNhanVien.addItem(nv.getHoNhanVien() + " " + nv.getTenNhanVien());
		}
		box.add(cbNhanVien);
		box.add(Box.createHorizontalStrut(5));
		
		cbKeDon = new JComboBox<String>(new String[] {"Tất cả","Kê đơn", "Không kê đơn"});
		cbKeDon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		box.add(cbKeDon);
		box.add(Box.createHorizontalStrut(5));
		
		cbSort = new JComboBox<String>(new String[] {"Theo thứ tự", "Theo số lượng", "Theo giá thành"});
		cbSort.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		box.add(cbSort);
		box.add(Box.createHorizontalStrut(5));
		
		cbAZ = new JComboBox<String>(new String[] {"Sắp xếp A-Z","Sắp xếp Z-A"});
		cbAZ.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		box.add(cbAZ);
		box.add(Box.createHorizontalStrut(5));
		
		box.add(btnTaiLai = new JButton("Tải lại"));
		
		locPanel.add(box);
		listPanelLichSu.add(locPanel, BorderLayout.NORTH);
		
		JPanel panelTable = new JPanel();
		panelTable.setLayout(new BorderLayout());
		
		String[] colHeader = {"Mã","Khách hàng","Số lượng","Thành tiền","Tình trạng","Dạng","Nhân viên"};
		
		modelLichSu = new DefaultTableModel(colHeader, 0) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		
		tableLichSu = new JTable(modelLichSu);
		tableLichSu.setRowHeight(20);
		
		setDuLieuLichSu(1);
		
		JScrollPane sp = new JScrollPane(tableLichSu, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		panelTable.add(sp, BorderLayout.CENTER);
		
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(JLabel.CENTER);
		
		DefaultTableCellRenderer right = new DefaultTableCellRenderer();
		right.setHorizontalAlignment(JLabel.RIGHT);
		
		tableLichSu.getColumnModel().getColumn(0).setCellRenderer(center);
		tableLichSu.getColumnModel().getColumn(2).setCellRenderer(right);
		tableLichSu.getColumnModel().getColumn(3).setCellRenderer(render);
		tableLichSu.getColumnModel().getColumn(4).setCellRenderer(checkedThanhToan);
		tableLichSu.getColumnModel().getColumn(5).setCellRenderer(center);
		
		listPanelLichSu.add(panelTable, BorderLayout.CENTER);
		
		JPanel panelPages = new JPanel();
		panelPages.setPreferredSize(new Dimension(550, 40));
		
		btnNextHoaDon = new JButton("❮");
		btnNextHoaDon.setFocusable(false);
		btnNextHoaDon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		int total = (int) Math.ceil(hoadon_dao.totalHoaDon()*1.0/25);
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
		
		listPanelLichSu.add(panelPages, BorderLayout.SOUTH);
		
		txtLoc.getDocument().putProperty("owner", txtLoc);
		txtLoc.getDocument().addDocumentListener(this);
		txtLoc.setName("timkiem_hoadon");
		
		btnNextHoaDon.addActionListener(this);
		btnTaiLai.addActionListener(this);
		btnPrevHoaDon.addActionListener(this);
		tableLichSu.addMouseListener(this);
		
		cbAZ.addActionListener(this);
		cbKeDon.addActionListener(this);
		cbNhanVien.addActionListener(this);
		cbSort.addActionListener(this);
		comboBoxPages.addActionListener(this);
		
		return myPanel;
	}
	
	
	public static HoaDon loadHoaDon(HoaDon hoadon) {
		KhachHang kh = khachhang_dao.getKhachHangTheoMaKH(hoadon.getKhachHang().getMaKhachHang());
		NhanVien nv = nhanvien_dao.getNhanVienTheoMaNV(hoadon.getNhanVien().getMaNhanVien());
		
		hoadon.setKhachHang(kh);
		hoadon.setNhanVien(nv);
		
		int count = 0;
		double money = 0.0;
		
		ArrayList<CT_HoaDon> dsThuoc = cthoadon_dao.getAllCTHoaDon(hoadon.getMaHoaDon());
		for(CT_HoaDon ct : dsThuoc) {
			count+=ct.getSoLuong();
			Thuoc thuoc = thuoc_dao.getThuocTheoMaThuoc(ct.getThuocCT().getMaThuoc());
			money+=ct.getSoLuong()*thuoc.getDonGia();
		}
		
		hoadon.setTongTien(money);
		hoadon.setSoLuong(count);
		
		return hoadon;
	}
	
	//=====
	public static void setDuLieuLichSu(int pages) {
		
		DefaultTableModel temp = (DefaultTableModel) tableLichSu.getModel();
		temp.getDataVector().removeAllElements();
		
		List<HoaDon> list = hoadon_dao.getPagesHoaDon(pages);
		for(HoaDon hoadon : list) {
			
			hoadon = loadHoaDon(hoadon);
			int soLuong = cthoadon_dao.totalSoLuong(hoadon.getMaHoaDon());
			
			modelLichSu.addRow(new Object[] {hoadon.getMaHoaDon(),hoadon.getKhachHang().getHoKhachHang()+" "+hoadon.getKhachHang().getTenKhachHang(),
					soLuong, hoadon.getTongTien(), hoadon.getThanhToan()?"Đã thanh toán":"Chưa thanh toán", hoadon.isDangHoaDon()?"Kê đơn":"Không kê đơn",
							hoadon.getNhanVien().getHoNhanVien()+" "+hoadon.getNhanVien().getTenNhanVien()});
			
		}
		
	}
	
	DefaultTableCellRenderer checkedThanhToan = new DefaultTableCellRenderer() {
		
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			if(label.getText().trim().equals("Đã thanh toán"))
				label.setForeground(Color.decode("#009900"));
			else
				label.setForeground(Color.decode("#CC0000"));
			
			label.setHorizontalAlignment(JLabel.CENTER);
			
			return label;
		}
		
	};
	
	DefaultTableCellRenderer render = new DefaultTableCellRenderer() {
		
		private static final long serialVersionUID = 1L;
		@Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        // Lấy đối tượng JLabel của TableCellRenderer
	        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        
            Font boldFont = new Font(label.getFont().getName(), Font.BOLD, label.getFont().getSize());

            DecimalFormat format = new DecimalFormat("#,###");
            label.setText(format.format(Double.parseDouble(label.getText())));
            label.setFont(boldFont);
            label.setHorizontalAlignment(JLabel.RIGHT);

	        return label;
	    }
	};

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		Object o = e.getSource();
		
		if(o.equals(btnTaiLai)) {
			setDuLieuLichSu(1);
			cbNhanVien.setSelectedItem("Tất cả nhân viên");
			cbKeDon.setSelectedItem("Tất cả");
			cbAZ.setSelectedIndex(0);
			cbSort.setSelectedIndex(0);
			txtLoc.setText("");
			btnNextHoaDon.setEnabled(true);
			btnPrevHoaDon.setEnabled(true);
			comboBoxPages.setVisible(true);
		}
		
		if(o.equals(cbAZ)||o.equals(cbKeDon)||o.equals(cbNhanVien)||o.equals(cbSort)) {
			
			String AZ = (String) cbAZ.getSelectedItem();
			String keDon = (String) cbKeDon.getSelectedItem();
			String nhanVien = (String) cbNhanVien.getSelectedItem();
			String sort = (String) cbSort.getSelectedItem();
			
			NhanVien nv = nhanvien_dao.getNhanVienTheoHoTenNV(nhanVien);
			if(!(nhanVien.trim().equals("Tất cả nhân viên")))
				nhanVien = nv.getMaNhanVien();
			
			ArrayList<HoaDon> dsSort_LichSu = hoadon_dao.filterNangCao(keDon, nhanVien, sort, AZ);
			
			if(dsSort_LichSu.size()==0) {
				JOptionPane.showMessageDialog(this, "Không có dữ liệu");
				setDuLieuLichSu(1);
			}else {
				
				DefaultTableModel temp = (DefaultTableModel) tableLichSu.getModel();
				temp.getDataVector().removeAllElements();
				
				for(HoaDon hoadon : dsSort_LichSu) {
					
					hoadon = loadHoaDon(hoadon);
					int soLuong = cthoadon_dao.totalSoLuong(hoadon.getMaHoaDon());
					
					modelLichSu.addRow(new Object[] {hoadon.getMaHoaDon(),hoadon.getKhachHang().getHoKhachHang()+" "+hoadon.getKhachHang().getTenKhachHang(),
							soLuong, hoadon.getTongTien(), hoadon.getThanhToan()?"Thanh toán":"Chưa thanh toán", hoadon.isDangHoaDon()?"Kê đơn":"Không kê đơn",
									hoadon.getNhanVien().getHoNhanVien()+" "+hoadon.getNhanVien().getTenNhanVien()});
					
				}
				
				
			}
			
			
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
		Object o = e.getSource();
		
		if(e.getClickCount()==2&&o.equals(tableLichSu)) {
			
        	int row = tableLichSu.getSelectedRow();
        	
        	ArrayList<HoaDon> dsHoaDon = hoadon_dao.getallHoaDon();
        	HoaDon hoadon = new HoaDon();
        	
        	for(HoaDon hd : dsHoaDon) {
        		if(hd.getMaHoaDon().trim().equals(tableLichSu.getValueAt(row, 0)));
        			hoadon = hd;
        	}
        	
        	ArrayList<CT_HoaDon> listCTHoaDon = cthoadon_dao.getAllCTHoaDon(tableLichSu.getValueAt(row, 0).toString());
			
        	for(CT_HoaDon ct : listCTHoaDon) {
        		Thuoc thuoc = thuoc_dao.getThuocTheoMaThuoc(ct.getThuocCT().getMaThuoc());
        		ct.setThuocCT(thuoc);
        	}
        	
			new xuatHoaDon(nvlogin, hoadon, listCTHoaDon).setVisible(true);
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
		
		if(textField.getName().equals("timkiem_hoadon")) {
			
			String regex = textField.getText().trim();
			if(regex.trim().equals(""))
				setDuLieuLichSu(1);
			else {
				DefaultTableModel temp = (DefaultTableModel) tableLichSu.getModel();
				temp.getDataVector().removeAllElements();
				
				List<HoaDon> ds = hoadon_dao.filterHoaDon(regex);
				for(HoaDon hoadon : ds) {
					
					hoadon = loadHoaDon(hoadon);
					int soLuong = cthoadon_dao.totalSoLuong(hoadon.getMaHoaDon());
					
					modelLichSu.addRow(new Object[] {hoadon.getMaHoaDon(),hoadon.getKhachHang().getHoKhachHang()+" "+hoadon.getKhachHang().getTenKhachHang(),
							soLuong, hoadon.getTongTien(), hoadon.getThanhToan()?"Thanh toán":"Chưa thanh toán", hoadon.isDangHoaDon()?"Kê đơn":"Không kê đơn",
									hoadon.getNhanVien().getHoNhanVien()+" "+hoadon.getNhanVien().getTenNhanVien()});
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

}
