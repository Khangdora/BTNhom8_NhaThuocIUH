package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.plaf.DimensionUIResource;

import DAO.CaTruc_DAO;
import DAO.NhanVien_DAO;
import connectDB.ConnectDB;
import entity.CaTruc;
import entity.NhanVien;

public class InfoUser extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private NhanVien nvlogin, nv;
	
	private NhanVien_DAO nhanvien_dao;
	private CaTruc_DAO catruc_dao;
	
	private JButton btnCapNhat, btnMatKhau;
	
	private DecimalFormat formatter = new DecimalFormat("#,###");
	
	public InfoUser(NhanVien nvLogin, NhanVien nvMain) {
		
		// ===========================
		
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		nhanvien_dao = new NhanVien_DAO();
		catruc_dao = new CaTruc_DAO();
		
		nvlogin = nvLogin;
		nv = nvMain;
		
		// ===========================
		
		setTitle("Thông tin nhân viên " + nvLogin.getHoNhanVien() + " " + nvLogin.getTenNhanVien());
		setSize(700, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		JPanel panelNorth = new JPanel();
		add(panelNorth, BorderLayout.NORTH);
		
		JLabel lblTitle;
		panelNorth.add(lblTitle = new JLabel("Thông tin nhân viên".toUpperCase()));
		lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
		
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(null);
		panelCenter.setPreferredSize(new DimensionUIResource(400, 400));
		
		JLabel lblHo, lblTen, lblSoDienThoai, lblEmail, lblCaTruc, lblGioiTinh, lblChucVu, lblTienLuong, lblTest;
		JTextField txtHo, txtTen, txtSoDienThoai, txtEmail, txtCaTruc, txtGioiTinh, txtChucVu, txtTienLuong;
		JComboBox<String> cbCaTruc, cbGioiTinh, cbChucVu;
		
		int x = 10, y = 20, width = 100, height = 30;
		panelCenter.add(lblHo = new JLabel("Họ:"));
		lblHo.setBounds(x, y+height*0, width, height);
		panelCenter.add(lblTest = new JLabel("Tên: "));
		lblTest.setBounds(x, y+height*1, width, height);
		panelCenter.add(lblTen = new JLabel("Tên: "));
		lblHo.setBounds(x, y+height*2, width, height);
		panelCenter.add(lblSoDienThoai = new JLabel("Số điện thoại: "));
		lblSoDienThoai.setBounds(x, y+height*3, width, height);
		panelCenter.add(lblEmail = new JLabel("Email: "));
		lblEmail.setBounds(x, y+height*4, width, height);
		panelCenter.add(lblCaTruc = new JLabel("Ca trực: "));
		lblCaTruc.setBounds(x, y+height*5, width, height);
		panelCenter.add(lblGioiTinh = new JLabel("Giới tính: "));
		lblGioiTinh.setBounds(x, y+height*6, width, height);
		panelCenter.add(lblChucVu = new JLabel("Chức vụ: "));
		lblChucVu.setBounds(x, y+height*7, width, height);
		panelCenter.add(lblTienLuong = new JLabel("Tiền lương: "));
		lblTienLuong.setBounds(x, y+height*8, width, height);
		
		x = 110; y = 20; width = 200; height = 30;
		panelCenter.add(txtHo = new JTextField(nv.getHoNhanVien()));
		txtHo.setBounds(x, y+height*1, width, height);
		panelCenter.add(txtTen = new JTextField(nv.getTenNhanVien()));
		txtTen.setBounds(x, y+height*2, width, height);
		panelCenter.add(txtSoDienThoai = new JTextField(nv.getSoDienThoai()));
		txtSoDienThoai.setBounds(x, y+height*3, width, height);
		panelCenter.add(txtEmail = new JTextField(nv.getEmailNhanVien()));
		txtEmail.setBounds(x, y+height*4, width, height);
		ArrayList<CaTruc> dsCaTruc = catruc_dao.getAllCaTruc();
		panelCenter.add(cbCaTruc = new JComboBox<String>());
		for(CaTruc ct : dsCaTruc) {
			cbCaTruc.addItem(ct.getMaCaTruc());
		}
		cbCaTruc.setSelectedItem(nv.getCaTruc().getMaCaTruc());
		cbCaTruc.setBounds(x, y+height*5, width, height);
		panelCenter.add(cbGioiTinh = new JComboBox<String>(new String[] {"Nam","Nữ"}));
		cbGioiTinh.setBounds(x, y+height*6, width, height);
		cbGioiTinh.setSelectedItem(nv.isGioiTinh()?"Nam":"Nữ");
		panelCenter.add(cbChucVu = new JComboBox<String>());
		cbChucVu.setBounds(x, y+height*7, width, height);
		ArrayList<String> dsChucVu = nhanvien_dao.getChucVu();
		for(String str : dsChucVu) {
			cbChucVu.addItem(str);
		}
		cbChucVu.setSelectedItem(nv.getChucVu());
		panelCenter.add(txtTienLuong = new JTextField(formatter.format(nv.getTienLuong())));
		txtTienLuong.setBounds(x, y+height*8, width, height);
		panelCenter.add(btnCapNhat = new JButton("Cập nhật thông tin"));
		btnCapNhat.setBounds(80,300,150,30);
		
		add(panelCenter, BorderLayout.CENTER);
		
		JPanel panelEast = new JPanel();
		panelEast.setLayout(null);
		panelEast.setPreferredSize(new Dimension(350, 400));
		
		int x2 = 10, y2 = 20, width2 = 150, height2 = 30;
		JLabel lblPass1, lblPass2, lblPass3;
		JPasswordField txtPass1, txtPass2, txtPass3;
		
		panelEast.add(lblPass1 = new JLabel("Mật khẩu cũ: "));
		lblPass1.setBounds(x2, y2+height2*1, width2, height2);
		panelEast.add(lblPass2 = new JLabel("Mật khẩu mới: "));
		lblPass2.setBounds(x2, y2+height2*2, width2, height2);
		panelEast.add(lblPass3 = new JLabel("Xác nhận mật khẩu: "));
		lblPass3.setBounds(x2, y2+height2*3, width2, height2);
		
		x2 = 130; y2 = 20; width2 = 200; height2 = 30;
		panelEast.add(txtPass1 = new JPasswordField());
		txtPass1.setBounds(x2, y2+height2*1, width2, height2);
		panelEast.add(txtPass2 = new JPasswordField());
		txtPass2.setBounds(x2, y2+height2*2, width2, height2);
		panelEast.add(txtPass3 = new JPasswordField());
		txtPass3.setBounds(x2, y2+height2*3, width2, height2);
		
		panelEast.add(btnMatKhau = new JButton("Thay đổi mật khẩu"));
		btnMatKhau.setBounds(90,150,150,30);
		
		add(panelEast, BorderLayout.EAST);
		btnCapNhat.addActionListener(this);
		btnMatKhau.addActionListener(this);
		
		if(nvlogin.getChucVu().trim().equals("Nhân viên")) {
			if(nvlogin.getMaNhanVien().equals(nv.getMaNhanVien())) {
				cbChucVu.setEditable(false);
				cbChucVu.setEnabled(false);
				cbCaTruc.setEditable(false);
				cbCaTruc.setEnabled(false);
				txtTienLuong.setEditable(false);
			}else {
				txtHo.setEditable(false);
				txtTen.setEditable(false);
				txtEmail.setEditable(false);
				txtSoDienThoai.setEditable(false);
				txtTienLuong.setEditable(false);
				cbChucVu.setEditable(false);
				cbChucVu.setEnabled(false);
				cbGioiTinh.setEditable(false);
				cbGioiTinh.setEnabled(false);
				cbCaTruc.setEditable(false);
				cbCaTruc.setEnabled(false);
			}
		}else {
			
			
			
			
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
