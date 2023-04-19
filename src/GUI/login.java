package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import DAO.NhanVien_DAO;
import connectDB.ConnectDB;
import entity.NhanVien;
import others.RoundBorderWithPadding;
import others.RoundedBorder;

public class login extends JFrame implements ActionListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel panelCenter, panelWest;
	JTextField txtMaNV;
	JPasswordField txtMatKhau;
	JLabel lblMaNV, lblMatKhau, lblThongbao;
	JButton btnLogin;
	
	private NhanVien_DAO nhanvien_dao;

	public login() {
		
		// ========================
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		nhanvien_dao = new NhanVien_DAO();
		
		// =========================
		
		setTitle("Đăng nhập hệ thống Nhà thuốc IUH");
		setSize(700,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		ImageIcon icon = new ImageIcon(getClass().getResource("/img/logo.jpg"));
		setIconImage(icon.getImage());
		
		createWest();
		add(panelWest, BorderLayout.WEST);
		
		createLogin();
		add(panelCenter, BorderLayout.CENTER);
		
	}
	
	private void createWest() {
		panelWest = new JPanel();
		panelWest.setPreferredSize(new Dimension(400, 400));
		panelWest.setLayout(new BorderLayout());
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(400, 400));
		
		JLabel lblBackground = new JLabel(new ImageIcon(getClass().getResource("/img/background.jpg")));
		lblBackground.setBorder(null);
		lblBackground.setBounds(0, 0, 400, 400);
		layeredPane.add(lblBackground, 1);

		JLabel lblLogo = new JLabel(new ImageIcon(getClass().getResource("/img/logoiuh.png")));
		lblLogo.setBorder(null);
		lblLogo.setBounds(45, 100, 300, 100);
		layeredPane.add(lblLogo, 0);
		
		JLabel lblSlogan = new JLabel("Vì sức khỏe cộng đồng");
		lblSlogan.setFont(new Font("Serif", Font.ITALIC, 18));
		lblSlogan.setForeground(Color.WHITE);
		lblSlogan.setBorder(null);
		lblSlogan.setBounds(120, 175, 300, 50);
		layeredPane.add(lblSlogan, 0);
		
		panelWest.add(layeredPane, BorderLayout.CENTER);
		
	}
	
	private void createLogin() {
		
		panelCenter = new JPanel();
		panelCenter.setLayout(null);
		panelCenter.setBackground(Color.decode("#ffffff"));
		panelCenter.add(lblMaNV = new JLabel("Mã nhân viên"));
		lblMaNV.setBounds(40, 60, 200, 30);
		panelCenter.add(txtMaNV = new JTextField());
		txtMaNV.setBounds(40, 90, 200, 30);
		txtMaNV.setBorder(new RoundBorderWithPadding(Color.GRAY, 10, 10, 10, 10, 20));
		panelCenter.add(lblMatKhau = new JLabel("Mật khẩu"));
		lblMatKhau.setBounds(40, 120, 200, 30);
		panelCenter.add(txtMatKhau = new JPasswordField());
		txtMatKhau.setBounds(40, 150, 200, 30);
		txtMatKhau.setBorder(new RoundBorderWithPadding(Color.GRAY, 10, 10, 10, 10, 20));
		panelCenter.add(lblThongbao = new JLabel());
		lblThongbao.setBounds(45,180,200,30);
		lblThongbao.setFont(new Font("Arial", Font.ITALIC, 12));
		lblThongbao.setForeground(Color.RED);
		panelCenter.add(btnLogin = new JButton("Đăng nhập"));
		btnLogin.setBounds(40, 210, 200, 30);
		btnLogin.setBackground(Color.decode("#003399"));
		btnLogin.setForeground(Color.decode("#ffffff"));
		btnLogin.setBorder(new RoundedBorder(new Color(0, 0, 0, 0), 10));
		btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		btnLogin.addActionListener(this);
		txtMaNV.addKeyListener(this);
		txtMatKhau.addKeyListener(this);
		
	}
	
	public boolean isEmpty() {
		String maNV = txtMaNV.getText().trim();
		char[] passwordChars = txtMatKhau.getPassword();
		String passwordString = new String(passwordChars).trim();
		
		if(!(maNV.length()>0)) {
			lblThongbao.setText("Mã nhân viên không được rỗng.");
			return false;
		}
		
		if(!(passwordString.length()>0)) {
			lblThongbao.setText("Mật khẩu không được rỗng.");
			return false;
		}
		
		return true;		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		Object o = e.getSource();
		if(o.equals(btnLogin)) {
			
			if(isEmpty()) {
				
				String maNV = txtMaNV.getText().trim();
				char[] passwordChars = txtMatKhau.getPassword();
				String passwordString = new String(passwordChars).trim();
				
				NhanVien nv = new NhanVien(maNV);
				nv.setMatKhau(passwordString);
				
				if(nhanvien_dao.login(nv)) {
					lblThongbao.setText("Đăng nhập thành công.");
					new main(nv).setVisible(true);
					dispose();
				}else {
					lblThongbao.setText("Mã NV hoặc mật khẩu không đúng.");
				}
				
			}
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        	btnLogin.doClick();
        }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
