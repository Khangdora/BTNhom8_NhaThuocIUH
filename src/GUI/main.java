package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import DAO.NhanVien_DAO;
import connectDB.ConnectDB;
import entity.NhanVien;

public class main extends JFrame implements ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private NhanVien nvlogin;
	JPanel panelTrangChu, panelHoaDon, panelKhoThuoc, panelKhachHang, panelLichSu, panelNhanVien, panelThongKe;
	JPanel panelNorth, createCenter, panelCenter;
	JLabel timeNow, timeLeft, lblName, lblDangxuat;
	JTabbedPane tabbedPane;
	JButton btnTab1, btnTab2, btnTab3, btnTab4, btnTab5, btnTab6;
	
	ChildTab child_tab = new ChildTab();
	
	private NhanVien_DAO nhanvien_dao;
	
	public main(NhanVien nv) {
		
		// ========================
		
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		nhanvien_dao = new NhanVien_DAO();
		nvlogin = nhanvien_dao.getNhanVienTheoMaNV(nv.getMaNhanVien());
		
		// ========================
		
		setTitle("Hệ thống Nhà thuốc IUH");
		setSize(1000,700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		ImageIcon icon = new ImageIcon(getClass().getResource("/img/logo.jpg"));
		setIconImage(icon.getImage());
		
		createNorth();
		add(panelNorth, BorderLayout.NORTH);
		
		createCenter();
		add(createCenter, BorderLayout.CENTER);
		
	
	}
	
	private void createNorth() {
		
		panelNorth = new JPanel() {
			
			private static final long serialVersionUID = 1L;
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/img/header.jpg"));
		        Image image = imageIcon.getImage();
		        g.drawImage(image, 0, 0, 1000, 60, this);
		    }
		    
		};
		panelNorth.setPreferredSize(new Dimension(1000, 60));
		panelNorth.setBorder(null);
		
		// === Panel Brand 
		JPanel brandPanel = new JPanel();
		brandPanel.setPreferredSize(new Dimension(200, 50));
		brandPanel.setLayout(null);
		brandPanel.setOpaque(false);
		
		JLabel lblLogo = new JLabel(new ImageIcon(getClass().getResource("/img/logoiuh_mini.png")));
		lblLogo.setBorder(null);
		lblLogo.setBounds(0,0,150,40);
		lblLogo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		brandPanel.add(lblLogo);
		panelNorth.add(brandPanel, BorderLayout.WEST);
		
		// === Panel Trung tâm
		JPanel centerPanel = new JPanel();
		centerPanel.setPreferredSize(new Dimension(560, 50));
		centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		centerPanel.setOpaque(false);
		
		Box box = Box.createVerticalBox();
		box.add(timeNow = new JLabel());
		timeNow.setForeground(Color.WHITE);
		box.add(Box.createVerticalStrut(5));
		box.add(timeLeft = new JLabel());
		timeLeft.setForeground(Color.WHITE);

		centerPanel.add(box);
		
		updateTime();
		
		panelNorth.add(centerPanel, BorderLayout.CENTER);
		
		// === Panel Info
		JPanel infoPanel = new JPanel();
		infoPanel.setPreferredSize(new Dimension(200, 50));
		infoPanel.setOpaque(false);
		infoPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		Box box2 = Box.createVerticalBox();
		box2.add(lblName = new JLabel("<html>Xin chào <font color='#FFCC00'>"+nvlogin.getHoNhanVien()+" "+nvlogin.getTenNhanVien()+"</font></html>"));
		lblName.setForeground(Color.WHITE);
		lblName.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblName.setName("infoUser");
		box2.add(Box.createVerticalStrut(5));
		box2.add(lblDangxuat = new JLabel("Đăng xuất"));
		lblDangxuat.setForeground(Color.WHITE);
		lblDangxuat.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblDangxuat.setName("dangxuat");
		
		infoPanel.add(box2);
		
		panelNorth.add(infoPanel, BorderLayout.EAST);
			
		Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
		
		timer.start();
		lblName.addMouseListener(this);
		lblDangxuat.addMouseListener(this);
		
	}
	
	private void createCenter() {
		createCenter = new JPanel();
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		
		panelHoaDon = child_tab.panelHoaDon();
		panelKhachHang = child_tab.panelKhachHang();
		panelKhoThuoc = child_tab.panelKhoThuoc();
		panelLichSu = child_tab.panelLichSu();
		panelNhanVien = child_tab.panelNhanVien();
		panelThongKe = child_tab.panelThongKe();
		panelTrangChu = child_tab.panelTrangChu();
		
		btnTab1 = new JButton("Hóa đơn");
		
		
		tabbedPane.addTab("Trang chủ", panelTrangChu);
		tabbedPane.addTab("Hóa đơn", panelHoaDon);
		tabbedPane.addTab("Khách hàng", panelKhachHang);
		tabbedPane.addTab("Kho thuốc", panelKhoThuoc);
		tabbedPane.addTab("Lịch sử", panelLichSu);
		tabbedPane.addTab("Nhân viên", panelNhanVien);
		tabbedPane.addTab("Thống kê", panelThongKe);
		
		tabbedPane.removeTabAt(0);
		tabbedPane.setTabComponentAt(0, btnTab1);
		
		
		//removeTab
//		tabbedPane.removeTabAt(0);
		
		
//		tabbedPane.removeTabAt(1);
//		btnTab1 = new JButton("Hóa đơn");
//		btnTab1.setOpaque(false);
//		
//		tabbedPane.set
		
//		Tab1
//		btnTab1 = new JButton("Trang chủ");
//		btnTab1.setOpaque(false);
//		tabbedPane.removeTabAt(0);
//		tabbedPane.insertTab("", null, new JPanel(), null, 0);
//		tabbedPane.setTabComponentAt(0, btnTab1);
		
////		//Tab2
//		btnTab2 = new JButton("Khách hàng");
//		btnTab2.setOpaque(false);
//		tabbedPane.removeTabAt(1);
//		tabbedPane.insertTab("", null, new JPanel(), null, 1);
//		tabbedPane.setTabComponentAt(1, btnTab2);
		
		tabbedPane.setBorder(null);
		
		createCenter.add(tabbedPane);
	}
	
	private void updateTime() {
		
		//Cài đặt thời gian hiện tại
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		String formattedDateTime = now.format(formatter) + " ngày " + now.getDayOfMonth() + " tháng " + now.getMonthValue() + " năm " + now.getYear();
		timeNow.setText(formattedDateTime);
		
		//Cài đặt thời gian theo ca
		if(nvlogin.getCaTruc().getMaCaTruc().equals("CA01")) {
			
			LocalDateTime deadline = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 14, 0);
			Duration duration = Duration.between(now, deadline);
			long hours = duration.toHours();
			long minutes = duration.toMinutes() % 60;
			long seconds = duration.getSeconds() % 60;
			
			String timeLeftString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
			
			timeLeft.setText("Thời gian trực ca còn " + timeLeftString);
			
			//Nếu qua ngày hôm sau
			if(duration.toMinutes()<0) {
				deadline = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth()+1, 14, 0);
				duration = Duration.between(now, deadline);
				hours = duration.toHours();
				minutes = duration.toMinutes() % 60;
				seconds = duration.getSeconds() % 60;
				timeLeftString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
				timeLeft.setText("Thời gian tới ca tiếp theo còn " + timeLeftString);
			}
			
		}else {
			
			LocalDateTime deadline = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 22, 0);
			Duration duration = Duration.between(now, deadline);
			long hours = duration.toHours();
			long minutes = duration.toMinutes() % 60;
			long seconds = duration.getSeconds() % 60;
			
			String timeLeftString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
			
			timeLeft.setText("Thời gian trực ca còn " + timeLeftString);
			
			//Nếu qua ngày hôm sau
			if(duration.toMinutes()<0) {
				deadline = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth()+1, 14, 0);
				duration = Duration.between(now, deadline);
				hours = duration.toHours();
				minutes = duration.toMinutes() % 60;
				seconds = duration.getSeconds() % 60;
				timeLeftString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
				timeLeft.setText("Thời gian tới ca tiếp theo còn " + timeLeftString);
			
			}
		}
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
	
	public static void main(String[] args) {
		NhanVien nv = new NhanVien("NV100");
		nv.setMatKhau("#Dx123#Dx123");
		new main(nv).setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		String name = e.getComponent().getName();
		
		if(name.trim().equals("infoUser")) {
			new infouser(nvlogin).setVisible(true);
		}
		
		if(name.trim().equals("dangxuat")) {
			new login().setVisible(true);
			dispose();
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
