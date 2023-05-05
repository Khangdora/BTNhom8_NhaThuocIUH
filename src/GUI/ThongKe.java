package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import DAO.CTHoaDon_DAO;
import DAO.CaTruc_DAO;
import DAO.HoaDon_DAO;
import DAO.KhachHang_DAO;
import DAO.NhaCungCap_DAO;
import DAO.NhanVien_DAO;
import DAO.Thuoc_DAO;
import connectDB.ConnectDB;
import entity.KhachHang;
import entity.NhanVien;

public class ThongKe extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Thuoc_DAO thuoc_dao;
	public static NhaCungCap_DAO ncc_dao;
	public static NhanVien_DAO nhanvien_dao;
	public static HoaDon_DAO hoadon_dao;
	public static KhachHang_DAO khachhang_dao;
	public static CTHoaDon_DAO cthoadon_dao;
	public CaTruc_DAO catruc_dao;
	
	private JPanel panelEast, panelCenter;
	
	private NhanVien nvlogin;
	
	private DefaultTableModel modelLeft, modelRight;
	private JTable tableLeft, tableRight;
	private Box panelCenterSouthLeft, panelCenterSouthRight, boxKhachHang, boxNhanVien;
	
	private JPanel panelTable;
	private JComboBox<String> cboNV, cboKH;
	
	public ThongKe(NhanVien nvlogin) {
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
		catruc_dao = new CaTruc_DAO();
		
		this.nvlogin = nhanvien_dao.getNhanVienTheoMaNV(nvlogin.getMaNhanVien());
		
		add(panelThongKe(),BorderLayout.CENTER);
	}
	
	public JPanel panelThongKe() {
		JPanel myPanel = new JPanel();
		myPanel.setPreferredSize(new Dimension(900, 600));
		
		DecimalFormat format = new DecimalFormat("#,###");
		
		panelCenter = new JPanel();
		panelCenter.setPreferredSize(new Dimension(700, 700));
		
		Box panelBox = Box.createVerticalBox();
		
		Box panelCenterNorth = Box.createHorizontalBox();
		panelCenterNorth.setPreferredSize(new Dimension(650, 50));
		
		JLabel lblCN1, lblCN2, lblCN3, lblCN4;
		
		panelCenterNorth.add(lblCN1 = new JLabel("<html><center><span style='font-size:18px'>" + format.format(thuoc_dao.totalThuoc()) 
        + "</span><br><span style='font-size:12px'>thuốc</span></center></html>"));
		lblCN1.setBackground(Color.decode("#66CCFF"));
		lblCN1.setOpaque(true);
		lblCN1.setPreferredSize(new Dimension(150, 100));
		lblCN1.setHorizontalAlignment(JLabel.CENTER);
		panelCenterNorth.add(Box.createHorizontalStrut(10));
		
		panelCenterNorth.add(lblCN2 = new JLabel("<html><center><span style='font-size:18px'>" + format.format(hoadon_dao.totalHoaDon()) 
        + "</span><br><span style='font-size:12px'>hóa đơn</span></center></html>"));
		lblCN2.setBackground(Color.decode("#66CCFF"));
		lblCN2.setOpaque(true);
		lblCN2.setPreferredSize(new Dimension(150, 100));
		lblCN2.setHorizontalAlignment(JLabel.CENTER);
		panelCenterNorth.add(Box.createHorizontalStrut(10));
		
		panelCenterNorth.add(lblCN3 = new JLabel("<html><center><span style='font-size:18px'>" + format.format(nhanvien_dao.totalNhanVien()) 
        + "</span><br><span style='font-size:12px'>nhân viên</span></center></html>"));
		lblCN3.setBackground(Color.decode("#66CCFF"));
		lblCN3.setOpaque(true);
		lblCN3.setPreferredSize(new Dimension(150, 100));
		lblCN3.setHorizontalAlignment(JLabel.CENTER);
		panelCenterNorth.add(Box.createHorizontalStrut(10));
		
		panelCenterNorth.add(lblCN4 = new JLabel("<html><center><span style='font-size:18px'>" + format.format(khachhang_dao.totalKhachHang()) 
        + "</span><br><span style='font-size:12px'>khách hàng</span></center></html>"));
		lblCN4.setBackground(Color.decode("#66CCFF"));
		lblCN4.setOpaque(true);
		lblCN4.setPreferredSize(new Dimension(150, 100));
		lblCN4.setHorizontalAlignment(JLabel.CENTER);
		panelCenterNorth.add(Box.createHorizontalStrut(10));
		
		panelBox.add(panelCenterNorth);
		panelBox.add(Box.createVerticalStrut(10));
		
		Box panelCenterCenter = Box.createHorizontalBox();
		panelCenterCenter.setPreferredSize(new Dimension(650, 50));
		JLabel lblPrevMonth, lblMonth;
		
		panelCenterCenter.add(lblPrevMonth = new JLabel("<html><center><span style='font-size:12px'>tháng trước</span><br><span style='font-size:18px'>" 
				+ format.format(hoadon_dao.getDoanhThuTheoThang(LocalDate.now().getMonthValue()-1, LocalDate.now().getYear())) 
				+ "₫</span></center></html>"));
		lblPrevMonth.setBackground(Color.decode("#FF3366"));
		lblPrevMonth.setOpaque(true);
		lblPrevMonth.setPreferredSize(new Dimension(150, 100));
		lblPrevMonth.setHorizontalAlignment(JLabel.CENTER);
		panelCenterCenter.add(Box.createHorizontalStrut(10));
		
		panelCenterCenter.add(lblMonth = new JLabel("<html><center><span style='font-size:12px'>tháng này</span><br><span style='font-size:18px'>" 
				+ format.format(hoadon_dao.getDoanhThuTheoThang(LocalDate.now().getMonthValue(), LocalDate.now().getYear())) 
				+ "₫</span></center></html>"));
		lblMonth.setBackground(Color.decode("#FF3366"));
		lblMonth.setOpaque(true);
		lblMonth.setPreferredSize(new Dimension(150, 100));
		lblMonth.setHorizontalAlignment(JLabel.CENTER);
		panelCenterCenter.add(Box.createHorizontalStrut(10));
		
		panelBox.add(panelCenterCenter);
		
		Box panelCenterSouth = Box.createHorizontalBox();
		panelCenterSouthLeft = Box.createVerticalBox();
		panelCenterSouthRight = Box.createVerticalBox();
		
		panelCenterSouth.add(panelCenterSouthLeft);
		panelCenterSouth.add(Box.createHorizontalStrut(10));
		panelCenterSouth.add(panelCenterSouthRight);
		
		panelCenterSouthLeft.setPreferredSize(new Dimension(300, 450));
		panelCenterSouthRight.setPreferredSize(new Dimension(300, 450));
		
		panelBox.add(Box.createVerticalStrut(10));
		
		//DS KH
		JLabel lblKH, lblNV;
		Box titleLeft = Box.createHorizontalBox();
		titleLeft.add(lblKH = new JLabel("Khách hàng:"));
		titleLeft.add(Box.createHorizontalStrut(10));
		lblKH.setHorizontalAlignment(JLabel.LEFT);
		cboKH = new JComboBox<String>(new String[] {"Theo tổng giá", "Theo số lượng", "Theo lượt mua"});
		cboKH.setPreferredSize(new Dimension(200, 20));
		titleLeft.add(cboKH);
		
		panelCenterSouthLeft.setBorder(borderTitle("Khách hàng"));

		panelCenterSouthLeft.add(titleLeft);
		panelCenterSouthLeft.add(Box.createVerticalStrut(10));
		
		boxKhachHang = loadKhachHang(1);
		panelCenterSouthLeft.add(boxKhachHang);
		
		//DSNV
		Box titleRight = Box.createHorizontalBox();
		titleRight.add(lblNV = new JLabel("Nhân viên:"));
		titleRight.add(Box.createHorizontalStrut(10));
		lblNV.setHorizontalAlignment(JLabel.LEFT);
		cboNV = new JComboBox<String>(new String[] {"Theo doanh số", "Theo số đơn", "Theo tiền lương"});
		cboNV.setPreferredSize(new Dimension(200, 20));		
		titleRight.add(Box.createHorizontalGlue());
		titleRight.add(cboNV);
		titleRight.add(Box.createHorizontalGlue());
		
		panelCenterSouthRight.setBorder(borderTitle("Nhân viên"));
		panelCenterSouthRight.add(titleRight);
		panelCenterSouthRight.add(Box.createVerticalStrut(10));
		
		boxNhanVien = loadNhanVien(1);
		panelCenterSouthRight.add(boxNhanVien);
		
		panelBox.add(panelCenterSouth);
		
		panelCenter.add(panelBox);		
		
		myPanel.add(panelCenter, BorderLayout.NORTH);

		panelEast = new JPanel();
		panelEast.setPreferredSize(new Dimension(170, 700));
		
		Box box = Box.createVerticalBox();
		
		JLabel lblCTUsers1, lblCTUsers2, lblCTUsers3, lblCTUsers4;
		
		box.setBorder(borderTitle("Về tôi"));
		
		box.add(lblCTUsers1 = new JLabel("<html><center><span style='font-size:18px'>" + format.format(hoadon_dao.totalHoaDon1NV(nvlogin.getMaNhanVien())) 
        + "</span><br><span style='font-size:12px'>hóa đơn</span></center></html>"));
		lblCTUsers1.setBackground(Color.decode("#DDDDDD"));
		lblCTUsers1.setOpaque(true);
		lblCTUsers1.setHorizontalAlignment(JLabel.CENTER);
		lblCTUsers1.setPreferredSize(new Dimension(170, 200));
		box.add(Box.createVerticalStrut(10));
		
		box.add(lblCTUsers4 = new JLabel("<html><center><span style='font-size:18px'>" 
		+ catruc_dao.getCaTrucTheoMaCT(nhanvien_dao.getNhanVienTheoMaNV(nvlogin.getMaNhanVien()).getCaTruc().getMaCaTruc()).getTenCaTruc().toUpperCase()
        + "</span><br><span style='font-size:12px'>ca trực</span></center></html>"));
		lblCTUsers4.setBackground(Color.decode("#DDDDDD"));
		lblCTUsers4.setOpaque(true);
		lblCTUsers4.setHorizontalAlignment(JLabel.CENTER);
		lblCTUsers4.setPreferredSize(new Dimension(170, 200));
		box.add(Box.createVerticalStrut(10));
		
		box.add(lblCTUsers2 = new JLabel("<html><center><span style='font-size:18px'>" + format.format(nvlogin.getTienLuong()) 
        + "₫</span><br><span style='font-size:12px'>tiền lương</span></center></html>"));
		lblCTUsers2.setBackground(Color.decode("#DDDDDD"));
		lblCTUsers2.setOpaque(true);
		lblCTUsers2.setHorizontalAlignment(JLabel.CENTER);
		lblCTUsers2.setPreferredSize(new Dimension(170, 200));
		box.add(Box.createVerticalStrut(10));
		
		box.add(lblCTUsers3 = new JLabel("<html><center><span style='font-size:18px'>" 
				+ format.format(nhanvien_dao.getDoanhSo(nvlogin.getMaNhanVien())) 
				+ "₫</span><br><span style='font-size:12px'>doanh số</span></center></html>"));
		lblCTUsers3.setBackground(Color.decode("#DDDDDD"));
		lblCTUsers3.setOpaque(true);
		lblCTUsers3.setHorizontalAlignment(JLabel.CENTER);
		lblCTUsers3.setPreferredSize(new Dimension(170, 200));
		box.add(Box.createVerticalStrut(10));
		
		panelEast.add(box);
		
		cboKH.setEnabled(false);
		cboNV.setEnabled(false);
		
		cboKH.addActionListener(this);
		cboNV.addActionListener(this);
		
		myPanel.add(panelEast, BorderLayout.EAST);
		
		return myPanel;
	}
	
	public Box loadNhanVien(int type) {
		Box myBox = Box.createVerticalBox();
		
		if(type==1) {
			String[][] dsNhanVien = nhanvien_dao.dsNhanVientheoDoanhSo();
			for(int i = 0; i<11; i++) {
		
				NhanVien nv = nhanvien_dao.getNhanVienTheoMaNV(dsNhanVien[i][0]);
				int stt = i+1;
				String count = dsNhanVien[i][1];
				
				JLabel myLBL;
				if(count!=null) {
					myBox.add(myLBL = new JLabel("<html><p style='font-size:9px;'>"+
							stt+". " + nv.getHoNhanVien() + " " + nv.getTenNhanVien() + "</p></html>"));
					myBox.add(new JLabel("<html><p style='font-size:8px; margin-left:10px'> Doanh số: " +count+"</p></html>"));
					myBox.add(Box.createVerticalStrut(5));
				}else {
					myBox.add(myLBL = new JLabel("<html><p style='font-size:9px;'>"+
							stt+". " + "Chưa rõ" + "</p></html>"));
					myBox.add(new JLabel("<html><p style='font-size:8px; margin-left:10px'> Doanh số: 0</p></html>"));
					myBox.add(Box.createVerticalStrut(5));
				}
			}
		}
		
		return myBox;
	}
	
	public Box loadKhachHang(int type) {
		Box myBox = Box.createVerticalBox();
		
		if(type==3) {
			String[][] dsKhachHang = khachhang_dao.dsKhachHangtheoLuotMua();
			for(int i = 0; i<11; i++) {
		
				KhachHang kh = khachhang_dao.getKhachHangTheoMaKH(dsKhachHang[i][0]);
				int stt = i+1;
				String count = dsKhachHang[i][1];
				
				JLabel myLBL;
				if(kh!=null) {
					myBox.add(myLBL = new JLabel("<html><p style='font-size:9px;'>"+
							stt+". " + kh.getHoKhachHang() + " " + kh.getTenKhachHang() + "</p></html>"));
					myBox.add(new JLabel("<html><p style='font-size:8px; margin-left:10px'> Lượt mua: " +count+"</p></html>"));
					myBox.add(Box.createVerticalStrut(5));
				}else {
					myBox.add(myLBL = new JLabel("<html><p style='font-size:9px;'>"+
							stt+". " + "Chưa rõ" + "</p></html>"));
					myBox.add(new JLabel("<html><p style='font-size:8px; margin-left:10px'> Lượt mua: 0</p></html>"));
					myBox.add(Box.createVerticalStrut(5));
				}
			}
		}
		
		if(type==1) {
			String[][] dsKhachHang = khachhang_dao.dsKhachHangTheoTongGia();
			for(int i = 0; i<11; i++) {
		
				KhachHang kh = khachhang_dao.getKhachHangTheoMaKH(dsKhachHang[i][0]);
				int stt = i+1;
				String count = dsKhachHang[i][1];
				
				JLabel myLBL;
				if(kh!=null) {
					myBox.add(myLBL = new JLabel("<html><p style='font-size:9px;'>"+
							stt+". " + kh.getHoKhachHang() + " " + kh.getTenKhachHang() + "</p></html>"));
					myBox.add(new JLabel("<html><p style='font-size:8px; margin-left:10px'> Tổng giá: " +count+"</p></html>"));
					myBox.add(Box.createVerticalStrut(5));
				}else {
					myBox.add(myLBL = new JLabel("<html><p style='font-size:9px;'>"+
							stt+". " + "Chưa rõ" + "</p></html>"));
					myBox.add(new JLabel("<html><p style='font-size:8px; margin-left:10px'> Tổng giá: 0</p></html>"));
					myBox.add(Box.createVerticalStrut(5));
				}
			}
		}
		
		return myBox;
	}
	
	public Border borderTitle(String title) {
		Border border;
		border = BorderFactory.createTitledBorder(
			    BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1), title);
		((TitledBorder) border).setTitleFont(new Font("Time New Roman", Font.ITALIC, 10));
		return border;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(cboKH)) {
			
			String cboStr = (String) cboKH.getSelectedItem();
			boxKhachHang.removeAll();
			
			if(cboStr.equals("Theo tổng giá")) {
				boxKhachHang = loadKhachHang(2);
			}else if(cboStr.equals("Theo số lượng")) {
				boxKhachHang = loadKhachHang(2);
			}else if(cboStr.equals("Theo lượt mua")) {
				boxKhachHang = loadKhachHang(3);
			}
			
		    boxKhachHang.revalidate();
		    boxKhachHang.repaint();
		    panelCenterSouthLeft.revalidate();
		    panelCenterSouthLeft.repaint();
			
		}
	}

}
