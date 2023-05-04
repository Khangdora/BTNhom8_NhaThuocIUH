package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import DAO.CTHoaDon_DAO;
import DAO.HoaDon_DAO;
import DAO.KhachHang_DAO;
import DAO.NhaCungCap_DAO;
import DAO.NhanVien_DAO;
import DAO.Thuoc_DAO;
import connectDB.ConnectDB;
import entity.NhanVien;

public class ThongKe extends JFrame {
	
	public static Thuoc_DAO thuoc_dao;
	public static NhaCungCap_DAO ncc_dao;
	public static NhanVien_DAO nhanvien_dao;
	public static HoaDon_DAO hoadon_dao;
	public static KhachHang_DAO khachhang_dao;
	public static CTHoaDon_DAO cthoadon_dao;
	
	private JPanel panelEast, panelCenter;
	
	private NhanVien nvlogin;
	
	private DefaultTableModel modelLeft, modelRight;
	private JTable tableLeft, tableRight;
	private Box panelCenterSouthLeft, panelCenterSouthRight;
	
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
		
		this.nvlogin = nvlogin;
		
		add(panelThongKe(),BorderLayout.CENTER);
	}
	
	public JPanel panelThongKe() {
		JPanel myPanel = new JPanel();
		myPanel.setPreferredSize(new Dimension(900, 600));
		
		DecimalFormat format = new DecimalFormat("#.###");
		
		panelCenter = new JPanel();
		panelCenter.setPreferredSize(new Dimension(700, 700));
		
		Box panelBox = Box.createVerticalBox();
		
		Box panelCenterNorth = Box.createHorizontalBox();
		panelCenterNorth.setPreferredSize(new Dimension(650, 50));
		
		JLabel lblCN1, lblCN2, lblCN3, lblCN4;
		
		panelCenterNorth.add(lblCN1 = new JLabel("<html><center><span style='font-size:18px'>" + format.format(thuoc_dao.totalThuoc()) 
        + "</span><br><span style='font-size:12px'>nhân viên</span></center></html>"));
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
		
		panelCenterCenter.add(lblPrevMonth = new JLabel("<html><center><span style='font-size:12px'>tháng trước</span><br><span style='font-size:18px'>" + format.format(khachhang_dao.totalKhachHang()) 
        + "</span></center></html>"));
		lblPrevMonth.setBackground(Color.decode("#FF3366"));
		lblPrevMonth.setOpaque(true);
		lblPrevMonth.setPreferredSize(new Dimension(150, 100));
		lblPrevMonth.setHorizontalAlignment(JLabel.CENTER);
		panelCenterCenter.add(Box.createHorizontalStrut(10));
		
		panelCenterCenter.add(lblMonth = new JLabel("<html><center><span style='font-size:12px'>tháng này</span><br><span style='font-size:18px'>" + format.format(khachhang_dao.totalKhachHang()) 
        + "</span></center></html>"));
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
		
		panelCenterSouthLeft.setPreferredSize(new Dimension(300, 400));
		panelCenterSouthRight.setPreferredSize(new Dimension(300, 400));
		
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
		
		
		
		panelCenterSouthLeft.add(titleLeft);
		
		//DSNV
		Box titleRight = Box.createHorizontalBox();
		titleRight.add(lblNV = new JLabel("Nhân viên:"));
		titleRight.add(Box.createHorizontalStrut(10));
		lblNV.setHorizontalAlignment(JLabel.LEFT);
		cboNV = new JComboBox<String>(new String[] {"Theo doanh số", "Theo số đơn", "Theo tiền lương"});
		cboNV.setPreferredSize(new Dimension(200, 20));
		titleRight.add(cboNV);
		
		panelCenterSouthRight.add(titleRight);
		
		panelBox.add(panelCenterSouth);
		
		panelCenter.add(panelBox);		
		
		myPanel.add(panelCenter, BorderLayout.NORTH);

		panelEast = new JPanel();
		panelEast.setPreferredSize(new Dimension(170, 600));
		
		Box box = Box.createVerticalBox();
		
		JLabel lblCTUsers0, lblCTUsers1, lblCTUsers2;
		
		box.add(lblCTUsers0 = new JLabel("<html><center><span style='font-size:13px'>Của tôi</span></center></html>"));
		lblCTUsers0.setHorizontalAlignment(JLabel.CENTER);
		box.add(Box.createVerticalStrut(10));
		
		box.add(lblCTUsers1 = new JLabel("<html><center><span style='font-size:18px'>" + format.format(hoadon_dao.totalHoaDon1NV(nvlogin.getMaNhanVien())) 
        + "</span><br><span style='font-size:12px'>hóa đơn</span></center></html>"));
		lblCTUsers1.setBackground(Color.decode("#DDDDDD"));
		lblCTUsers1.setOpaque(true);
		lblCTUsers1.setHorizontalAlignment(JLabel.CENTER);
		lblCTUsers1.setPreferredSize(new Dimension(170, 200));
		box.add(Box.createVerticalStrut(10));
		
		box.add(lblCTUsers2 = new JLabel("<html><center><span style='font-size:18px'>" + format.format(nvlogin.getTienLuong()) 
        + "</span><br><span style='font-size:12px'>tiền lương</span></center></html>"));
		lblCTUsers2.setBackground(Color.decode("#DDDDDD"));
		lblCTUsers2.setOpaque(true);
		lblCTUsers2.setHorizontalAlignment(JLabel.CENTER);
		lblCTUsers2.setPreferredSize(new Dimension(170, 200));
		box.add(Box.createVerticalStrut(10));
		
		panelEast.add(box);
		
		myPanel.add(panelEast, BorderLayout.EAST);
		
		return myPanel;
	}

}
