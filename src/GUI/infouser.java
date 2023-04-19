package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import DAO.NhanVien_DAO;
import connectDB.ConnectDB;
import entity.NhanVien;

public class infouser extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private NhanVien nvlogin;
	
	private NhanVien_DAO nhanvien_dao;
	
	public infouser(NhanVien nv) {
		
		// ===========================
		
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		nhanvien_dao = new NhanVien_DAO();
		nvlogin = nv;
		
		// ===========================
		
		setTitle("Thông tin nhân viên " + nv.getHoNhanVien() + " " + nv.getTenNhanVien());
		setSize(700, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		JPanel tab1 = new JPanel();
		
		Box box = Box.createVerticalBox();
		
		box.add(new JLabel(nv.getHoNhanVien() + " " + nv.getTenNhanVien()));
		box.add(new JLabel(nv.getChucVu()));
		
		tab1.add(box);
		
		add(tab1, BorderLayout.CENTER);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
