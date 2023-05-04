package GUI;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

import entity.Thuoc;

public class SuaThuoc extends KhoThuoc{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("exports")
	public JFrame guiUpdate(Thuoc thuoc) {
		frameUpdate = new JFrame();
		frameUpdate.setTitle("Sửa thông tin thuốc");
		frameUpdate.setSize(800, 400);
		frameUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameUpdate.setLocationRelativeTo(null);
		frameUpdate.setResizable(false);
		frameUpdate.setLayout(null);
		ImageIcon icon = new ImageIcon(getClass().getResource("/img/logo.jpg"));
		frameUpdate.setIconImage(icon.getImage());
		JPanel myPanel = new JPanel();
		myPanel.setLayout(null);
		myPanel.setSize(800, 310);
		myPanel.setBackground(Color.decode("#DDDDDD"));
		myPanel.setBorder(borderTitle("Thông tin sản phẩm"));
		
		int x = 10 , y = 20, width1 = 100, width2 = 280, height = 25;
		
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
		myPanel.add(lblTBTenSP);
		
		
		JLabel lblSoLuong = new JLabel("Số lượng: ");
		txtSoLuong = new JTextField();
		txtSoLuong.setDocument(new PlainDocument());
		((PlainDocument)txtSoLuong.getDocument()).setDocumentFilter(new NumberOnlyFilter());
		lblSoLuong.setBounds(x, y*5, width1, height);
		txtSoLuong.setBounds(width1, y*5, width2, height);
		txtSoLuong.setName("tbSoLuong");
		lblTBSoLuong = new JLabel();
		lblTBSoLuong.setBounds(width1, y*6, width2, height);
		myPanel.add(lblTBSoLuong);
		
		JLabel lblDonGia = new JLabel("Đơn giá: ");
		txtDonGia = new JTextField();
		txtDonGia.setDocument(new PlainDocument());
		((PlainDocument)txtDonGia.getDocument()).setDocumentFilter(new NumberOnlyFilter());
		lblDonGia.setBounds(x, y*7, width1, height);
		txtDonGia.setBounds(width1, y*7, width2, height);
		txtDonGia.setName("tbDonGia");
		lblTBDonGia = new JLabel();
		lblTBDonGia.setBounds(width1, y*8, width2, height);
		myPanel.add(lblTBDonGia);
		
		JLabel lblNgayNhap = new JLabel("Ngày nhập: ");
		txtNgayNhap = new JTextField();
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
		dateHSD.setTextRefernce(txtHSD);
		txtHSD.setName("tbHSD");
		lblHSD.setBounds(x + width1 + width2, y*9, width1, height);
		txtHSD.setBounds(x + width1*2 + width2, y*9, width2, height);
		lblTBHSD = new JLabel();
		lblTBHSD.setBounds(x + width1*2 + width2, y*10, width2, height);
		
		
		JLabel lblThanhPhan = new JLabel("Thành phần: ");
		txtThanhPhan = new JTextArea();
		txtThanhPhan.setLineWrap(true);
		txtThanhPhan.setWrapStyleWord(true);
		txtThanhPhan.setName("tbThanhPhan");
		JScrollPane spThanhPhan = new JScrollPane(txtThanhPhan, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		lblThanhPhan.setBounds(x, y*11, width1, height*3);
		spThanhPhan.setBounds(width1, y*11, width2, height*3);
		
		lblTBThanhPhan = new JLabel();
		lblTBThanhPhan.setBounds(x*2 + width1*3 + width2*2, y*5, width2, height);
		myPanel.add(lblTBThanhPhan);
		
		JLabel lblCongDung = new JLabel("Công dụng: ");
		txtCongDung = new JTextArea();
		txtCongDung.setLineWrap(true);
		txtCongDung.setWrapStyleWord(true);
		txtCongDung.setName("tbCongDung");
		JScrollPane spCongDung = new JScrollPane(txtCongDung, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		lblCongDung.setBounds(x + width1 + width2, y*11, width1, height*3);
		spCongDung.setBounds(x + width1*2 + width2, y*11, width2, height*3);
		
		lblTBCongDung = new JLabel();
		lblTBCongDung.setBounds(x*2 + width1*3 + width2*2, y*11, width2, height);
		myPanel.add(lblTBCongDung);
		
		
		myPanel.add(lblMaSP);
		myPanel.add(txtMaSP);
		myPanel.add(lblTenSP);
		myPanel.add(txtTenSP);
		myPanel.add(lblDVB);
		myPanel.add(comboBoxSelectDVB);
		myPanel.add(lblSoLuong);
		myPanel.add(txtSoLuong);
		myPanel.add(lblDonGia);
		myPanel.add(txtDonGia);
		myPanel.add(lblXuatXu);
		myPanel.add(comboBoxSelectXX);
		myPanel.add(lblDBC);
		myPanel.add(comboBoxSelectDBC);
		myPanel.add(lblTenNCC);
		myPanel.add(comboBoxSelectNCC);
		myPanel.add(lblThanhPhan);
		myPanel.add(spThanhPhan);
		myPanel.add(lblCongDung);
		myPanel.add(spCongDung);
		myPanel.add(lblNgayNhap);
		myPanel.add(txtNgayNhap);
		myPanel.add(lblHSD);
		myPanel.add(txtHSD);
		myPanel.add(lblTBHSD);
		
		frameUpdate.add(myPanel);
		
		
		JPanel panelChucNang = new JPanel();
		panelChucNang.setLayout(null);
		panelChucNang.setBounds(0, 315, 800, 49);
		panelChucNang.setBackground(Color.decode("#DDDDDD"));
		panelChucNang.setBorder(borderTitle("Chức năng"));
		
		btnSave = new JButton("Lưu");
		btnExit = new JButton("Thoát");
		
		btnSave.setBounds(560, 12, 100, 25);
		btnExit.setBounds(680, 12, 100, 25);
		
		panelChucNang.add(btnSave);
		panelChucNang.add(btnExit);
		
		btnSave.addActionListener(this);
		btnExit.addActionListener(this);
		
		frameUpdate.add(panelChucNang);
		
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
		
		readDataIntoTxt(thuoc);
		return frameUpdate;
	}
}
