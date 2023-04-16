package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
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
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entity.NhanVien;
import others.BottomBorder;

public class main extends JFrame implements ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private NhanVien nvlogin;
	private JPanel panelTrangChu, panelHoaDon, panelKhoThuoc, panelKhachHang, panelLichSu, panelNhanVien, panelThongKe;
	private JPanel panelNorth, panelCenter, panelWest;
	private JLabel timeNow, timeLeft, lblName, lblDangxuat, lblLogo;
	private JButton btnTab1, btnTab2, btnTab3, btnTab4, btnTab5, btnTab6;
	
	private ArrayList<JPanel> panels;
	private ArrayList<JButton> buttons;
	
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
		setSize(1100,700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		ImageIcon icon = new ImageIcon(getClass().getResource("/img/logo.jpg"));
		setIconImage(icon.getImage());
		
		createNorth();
		add(panelNorth, BorderLayout.NORTH);
		
		createWest();
		add(panelWest, BorderLayout.WEST);
		
		createCenter();
		add(panelCenter, BorderLayout.CENTER);
		
	
	}
	
	private void createNorth() {
		
		panelNorth = new JPanel() {
			
			private static final long serialVersionUID = 1L;
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/img/header.jpg"));
		        Image image = imageIcon.getImage();
		        g.drawImage(image, 0, 0, 1100, 60, this);
		    }
		    
		};
		panelNorth.setPreferredSize(new Dimension(1100, 60));
		panelNorth.setBorder(null);
		
		// === Panel Brand 
		JPanel brandPanel = new JPanel();
		brandPanel.setPreferredSize(new Dimension(300, 50));
		brandPanel.setLayout(null);
		brandPanel.setOpaque(false);
		
		lblLogo = new JLabel(new ImageIcon(getClass().getResource("/img/logoiuh_mini.png")));
		lblLogo.setBorder(null);
		lblLogo.setBounds(0,0,150,40);
		lblLogo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblLogo.setName("home");
		
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
		lblLogo.addMouseListener(this);
		
	}

	
	private void createWest() {
		
		panelWest = new JPanel();
		panelWest.setLayout(new GridLayout(8, 1));
		panelWest.setPreferredSize(new Dimension(170, 500));
		panelWest.setBackground(Color.decode("#AAAAAA"));
		
		buttons = new ArrayList<JButton>();
		
		btnTab1 = new JButton("Hóa đơn");
		setBtn(btnTab1);
		buttons.add(btnTab1);
		
		btnTab2 = new JButton("Khách hàng");
		setBtn(btnTab2);
		buttons.add(btnTab2);
		
		btnTab3 = new JButton("Kho thuốc");
		setBtn(btnTab3);
		buttons.add(btnTab3);
		
		btnTab4 = new JButton("Lịch sử");
		setBtn(btnTab4);
		buttons.add(btnTab4);
		
		btnTab5 = new JButton("Nhân viên");
		setBtn(btnTab5);
		buttons.add(btnTab5);
		
		btnTab6 = new JButton("Thống kê");
		setBtn(btnTab6);
		buttons.add(btnTab6);
		
		panelWest.add(btnTab1);
		panelWest.add(btnTab2);
		panelWest.add(btnTab3);
		panelWest.add(btnTab4);
		panelWest.add(btnTab5);
		panelWest.add(btnTab6);
		panelWest.add(new JLabel());
		
		JLabel copyright;
		panelWest.add(copyright = new JLabel("Group 8 IUH"));
		copyright.setHorizontalAlignment(SwingConstants.CENTER);
		copyright.setFont(new Font("Serif", Font.PLAIN, 13));
		
		btnTab1.addActionListener(this);
		btnTab2.addActionListener(this);
		btnTab3.addActionListener(this);
		btnTab4.addActionListener(this);
		btnTab5.addActionListener(this);
		btnTab6.addActionListener(this);
//		btnTab1.addMouseListener(this);
//		btnTab2.addMouseListener(this);
//		btnTab3.addMouseListener(this);
//		btnTab4.addMouseListener(this);
//		btnTab5.addMouseListener(this);
//		btnTab6.addMouseListener(this);
		
	}
	
	private void createCenter() {
		panelCenter = new JPanel();
		panelCenter.setBorder(null);
		
		panelHoaDon = child_tab.panelHoaDon();
		panelKhachHang = child_tab.panelKhachHang();
		panelKhoThuoc = child_tab.panelKhoThuoc();
		panelLichSu = child_tab.panelLichSu();
		panelNhanVien = child_tab.panelNhanVien();
		panelThongKe = child_tab.panelThongKe();
		panelTrangChu = child_tab.panelTrangChu();
		
		panelCenter.add(panelTrangChu);
		panelCenter.add(panelHoaDon);
		panelCenter.add(panelKhachHang);
		panelCenter.add(panelKhoThuoc);
		panelCenter.add(panelLichSu);
		panelCenter.add(panelNhanVien);
		panelCenter.add(panelThongKe);
		onlyHome();
		
		panels = new ArrayList<JPanel>();
		panels.add(panelTrangChu);
		panels.add(panelHoaDon);
		panels.add(panelKhachHang);
		panels.add(panelKhoThuoc);
		panels.add(panelLichSu);
		panels.add(panelNhanVien);
		panels.add(panelThongKe);
		
	}
	
	private void onlyHome() {
		panelHoaDon.setVisible(true);
		panelHoaDon.setVisible(false);
		panelKhachHang.setVisible(false);
		panelKhoThuoc.setVisible(false);
		panelLichSu.setVisible(false);
		panelNhanVien.setVisible(false);
		panelThongKe.setVisible(false);
	}
	
	private void setBtn(JButton jbtn) {
		
//		GradientPaint gradient = new GradientPaint(0, 0, Color.RED, 0, jbtn.getHeight(), Color.BLUE);
		
//		Color midColor = new Color((Color.RED.getRed() + Color.BLUE.getRed()) / 2,
//                (Color.RED.getGreen() + Color.BLUE.getGreen()) / 2,
//                (Color.RED.getBlue() + Color.BLUE.getBlue()) / 2);
			
		jbtn.setBorder(null);
		jbtn.setBackground(Color.decode("#003366"));
//		jbtn.setBackground(midColor);
		jbtn.setFont(new Font("Arial", Font.BOLD, 14));
		jbtn.setForeground(Color.WHITE);
		jbtn.setFocusable(false);
		jbtn.setText(jbtn.getText().toUpperCase());
		jbtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jbtn.setBorder(BorderFactory.createCompoundBorder(jbtn.getBorder(), new BottomBorder()));
	}
	
	private void entBtn(JButton jbtn) {
		jbtn.setBackground(Color.decode("#3366CC"));
	}
	
	private void outBtn(JButton jbtn) {
		jbtn.setBackground(Color.decode("#003399"));
	}
	
	private void setBgBtn() {
		for (JButton btn : buttons) {
			btn.setBackground(Color.decode("#003366"));
			btn.setForeground(Color.white);
		}
	}
	
	private void setBgBtn(JButton x) {
		for (JButton btn : buttons) {
			if(btn == x) {
				btn.setBackground(Color.decode("#EEEEEE"));
				btn.setForeground(Color.black);
			} else {
				btn.setBackground(Color.decode("#003366"));
				btn.setForeground(Color.white);
			}
		}
	}
	
	private void updateTime() {
		
		//========================================= LÀM LẠI
		
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
		
		Object o = e.getSource();
		if(o.equals(btnTab1)) {			
            for (JPanel panel : panels) {
                if (panel == panelHoaDon) 
                    panel.setVisible(true);
                 else 
                    panel.setVisible(false);
             }
            setBgBtn(btnTab1);
		}
		
		if(o.equals(btnTab2)) {			
            for (JPanel panel : panels) {
                if (panel == panelKhachHang) 
                    panel.setVisible(true);
                else 
                   panel.setVisible(false);
             }
             setBgBtn(btnTab2);
		}
		
		if(o.equals(btnTab3)) {			
            for (JPanel panel : panels) {
                if (panel == panelKhoThuoc) {
                    panel.setVisible(true);
                } else {
                   panel.setVisible(false);
                }
             }
            setBgBtn(btnTab3);
		}
		
		if(o.equals(btnTab4)) {			
            for (JPanel panel : panels) {
                if (panel == panelLichSu) {
                    panel.setVisible(true);
                } else {
                   panel.setVisible(false);
                }
             }
            setBgBtn(btnTab4);
		}
		
		if(o.equals(btnTab5)) {			
            for (JPanel panel : panels) {
                if (panel == panelNhanVien) {
                    panel.setVisible(true);
                } else {
                   panel.setVisible(false);
                }
             }
            setBgBtn(btnTab5);
		}
		
		if(o.equals(btnTab6)) {			
            for (JPanel panel : panels) {
                if (panel == panelThongKe) {
                    panel.setVisible(true);
                } else {
                   panel.setVisible(false);
                }
             }
            setBgBtn(btnTab6);
		}
		

	}
	
	public static void main(String[] args) {
		NhanVien nv = new NhanVien("NV100");
		nv.setMatKhau("#Dx123#Dx123");
		new main(nv).setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		String name = e.getComponent().getName().trim();
					
		if(name.equals("home")) {
           for (JPanel panel : panels) {
                if (panel == panelTrangChu)
                   panel.setVisible(true);
                else 
                   panel.setVisible(false);
            }
           setBgBtn();
		}
			
		if(name.equals("infoUser")) {
			new infouser(nvlogin).setVisible(true);
		}
			
		if(name.equals("dangxuat")) {
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
//		Object o = e.getSource();
//		if(o.equals(btnTab1)) 
//			entBtn(btnTab1);
//		if(o.equals(btnTab2)) 
//			entBtn(btnTab2);
//		if(o.equals(btnTab3)) 
//			entBtn(btnTab3);
//		if(o.equals(btnTab4)) 
//			entBtn(btnTab4);
//		if(o.equals(btnTab5)) 
//			entBtn(btnTab5);
//		if(o.equals(btnTab6)) 
//			entBtn(btnTab6);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
//		Object o = e.getSource();
//		if(o.equals(btnTab1)) 
//			outBtn(btnTab1);
//		if(o.equals(btnTab2)) 
//			outBtn(btnTab2);
//		if(o.equals(btnTab3)) 
//			outBtn(btnTab3);
//		if(o.equals(btnTab4)) 
//			outBtn(btnTab4);
//		if(o.equals(btnTab5)) 
//			outBtn(btnTab5);
//		if(o.equals(btnTab6)) 
//			outBtn(btnTab6);
	}
	
	

}
