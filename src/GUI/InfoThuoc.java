package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.SQLException;


import java.util.Date;



import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JTextArea;
import javax.swing.JTextField;


import DAO.Thuoc_DAO;
import connectDB.ConnectDB;
import entity.NhaCungCap;
import entity.Thuoc;

public class InfoThuoc extends KhoThuoc{
	
	private static final long serialVersionUID = 1L;
	private JButton btnSua;

	
	
	public InfoThuoc(Thuoc thuoc) {
		// TODO Auto-generated constructor stub		
		
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		thuoc_dao = new Thuoc_DAO();
		
		setTitle("Thông tin sản phẩm có mã " + thuoc.getMaThuoc());
		setSize(700, 550);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new BorderLayout());
		
		
		
		JPanel panelNorth = new JPanel();
		JLabel lblTitle = new JLabel("Thông tin chi tiết thuốc".toUpperCase());
		lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
		panelNorth.add(lblTitle);
		this.add(panelNorth, BorderLayout.NORTH);
		
		int widthlbl = 100, widthtxt = 230, height = 30, x1 = 10, x2 = 100, x3 = 350, x4 = 440, y = 10;
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(null);
		JLabel lblMaSP = new JLabel("Mã thuốc: ");
		txtMaSP = new JTextField(thuoc_dao.maThuocAuto(), 5);
		txtMaSP.setEditable(false);
		lblMaSP.setBounds(x1, y, widthlbl, height);
		txtMaSP.setBounds(x2, y, widthtxt, height);
		panelCenter.add(lblMaSP);
		panelCenter.add(txtMaSP);
		
		JLabel lblTenSP = new JLabel("Tên thuốc: ");
		txtTenSP = new JTextField();
		lblTenSP.setBounds(x3, y, widthlbl, height);
		txtTenSP.setBounds(x4, y, widthtxt, height);
		panelCenter.add(lblTenSP);
		panelCenter.add(txtTenSP);
		
		lblTBTenSP = new JLabel();
		lblTBTenSP.setBounds(x4, y + height + 2, widthtxt, 15);
		panelCenter.add(lblTBTenSP);
		lblTBTenSP.setForeground(Color.RED);
		lblTBTenSP.setFont(lblTBTenSP.getFont().deriveFont(12.0f));
		
		JLabel lblDVB = new JLabel("Đơn vị bán: ");
		comboBoxSelectDVB = new JComboBox<>(donViBanStr);
		lblDVB.setBounds(x1, y+height+20, widthlbl, height);
		comboBoxSelectDVB.setBounds(x2, y+height+20, widthtxt, height);
		panelCenter.add(lblDVB);
		panelCenter.add(comboBoxSelectDVB);
		
		JLabel lblSoLuong = new JLabel("Số lượng: ");
		txtSoLuong = new JTextField();
		lblSoLuong.setBounds(x3, y+height+20, widthlbl, height);
		txtSoLuong.setBounds(x4, y+height+20, widthtxt, height);
		panelCenter.add(lblSoLuong);
		panelCenter.add(txtSoLuong);
		
		lblTBSoLuong = new JLabel();
		lblTBSoLuong.setBounds(x4, 110, widthtxt, 15);
		panelCenter.add(lblTBSoLuong);
		lblTBSoLuong.setForeground(Color.RED);
		lblTBSoLuong.setFont(lblTBSoLuong.getFont().deriveFont(12.0f));
		
		JLabel lblDBC = new JLabel("Dạng bào chế: ");
		comboBoxSelectDBC = new JComboBox<String>(dangBaoCheStr);
		lblDBC.setBounds(x1, y+height*2+20*2, widthlbl, height);
		comboBoxSelectDBC.setBounds(x2, y+height*2+20*2, widthtxt, height);
		panelCenter.add(lblDBC);
		panelCenter.add(comboBoxSelectDBC);
		
		JLabel lblXuatXu = new JLabel("Xuất xứ: ");
		comboBoxSelectXX = new JComboBox<String>(xuatXuStr);
		lblXuatXu.setBounds(x3, y+height*2+20*2, widthlbl, height);
		comboBoxSelectXX.setBounds(x4, y+height*2+20*2, widthtxt, height);
		panelCenter.add(lblXuatXu);
		panelCenter.add(comboBoxSelectXX);
		
		
		
		JLabel lblNgayNhap = new JLabel("Ngày nhập: ");
		snNgayNhap = ngayNhap();
		lblNgayNhap.setBounds(x1, y+height*3+20*3, widthlbl, height);
		snNgayNhap.setBounds(x2, y+height*3+20*3, widthtxt, height);
		panelCenter.add(lblNgayNhap);
		panelCenter.add(snNgayNhap);
		JLabel lblHSD = new JLabel("Hạn sử dụng: ");
		snHSD = hanSuDung();
		lblHSD.setBounds(x3, y+height*3+20*3, widthlbl, height);
		snHSD.setBounds(x4, y+height*3+20*3, widthtxt, height);
		panelCenter.add(lblHSD);
		panelCenter.add(snHSD);
		
		JLabel lblThanhPhan = new JLabel("Thành phần: ");
		txtThanhPhan = new JTextArea();
		txtThanhPhan.setLineWrap(true);
		txtThanhPhan.setWrapStyleWord(true);
		JScrollPane spThanhPhan = new JScrollPane(txtThanhPhan, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		lblThanhPhan.setBounds(x1, y+240, widthlbl , height + 70);
		spThanhPhan.setBounds(x2, y+240, widthtxt, height+ 70);
		panelCenter.add(lblThanhPhan);
		panelCenter.add(spThanhPhan);
		
		lblTBThanhPhan = new JLabel();
		lblTBThanhPhan.setBounds(x2, 360, widthtxt, 15);
		panelCenter.add(lblTBThanhPhan);
		lblTBThanhPhan.setForeground(Color.RED);
		lblTBThanhPhan.setFont(lblTBThanhPhan.getFont().deriveFont(12.0f));
		
		
		JLabel lblCongDung = new JLabel("Công dụng: ");
		txtCongDung = new JTextArea();
		txtCongDung.setLineWrap(true);
		txtCongDung.setWrapStyleWord(true);
		JScrollPane spCongDung = new JScrollPane(txtCongDung, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		lblCongDung.setBounds(x3, y+240, widthlbl , height + 70);
		spCongDung.setBounds(x4, y+240, widthtxt, height+ 70);
		panelCenter.add(lblCongDung);
		panelCenter.add(spCongDung);
		
		lblTBCongDung = new JLabel();
		lblTBCongDung.setBounds(x4, 360, widthtxt, 15);
		panelCenter.add(lblTBCongDung);
		lblTBCongDung.setForeground(Color.RED);
		lblTBCongDung.setFont(lblTBCongDung.getFont().deriveFont(12.0f));
		
		
		JLabel lblDonGia = new JLabel("Đơn giá: ");
		txtDonGia = new JTextField();
		lblDonGia.setBounds(x1, y+370, widthlbl, height);
		txtDonGia.setBounds(x2, y+370, widthtxt, height);
		panelCenter.add(lblDonGia);
		panelCenter.add(txtDonGia);
		
		
		lblTBDonGia = new JLabel();
		lblTBDonGia.setBounds(x2, 420, widthtxt, 15);
		panelCenter.add(lblTBDonGia);
		lblTBDonGia.setForeground(Color.RED);
		lblTBDonGia.setFont(lblTBDonGia.getFont().deriveFont(12.0f));
		
		JLabel lblNCC = new JLabel("Nhà cung cấp: ");
		comboBoxSelectNCC = new JComboBox<String>();
		for(String item : ncc_dao.getTenNCC()) {
			comboBoxSelectNCC.addItem(item);
		}
		lblNCC.setBounds(x3, y+370, widthlbl, height);
		comboBoxSelectNCC.setBounds(x4, y+370, widthtxt, height);
		panelCenter.add(lblNCC);
		panelCenter.add(comboBoxSelectNCC);
		
		this.add(panelCenter, BorderLayout.CENTER);

		JPanel panelSouth = new JPanel();
		panelSouth.setBackground(Color.decode("#DDDDDD"));
		
		panelSouth.add(btnSua = new JButton("Cập nhật thông tin"));
		this.add(panelSouth, BorderLayout.SOUTH);
		
		readDataIntoTxt(thuoc);
		
		btnSua.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(btnSua)) {
			update();
		}
	}
	
	public void readDataIntoTxt(Thuoc thuoc) {
		txtMaSP.setText(thuoc.getMaThuoc());
		txtTenSP.setText(thuoc.getTenThuoc());
		txtSoLuong.setText(Integer.toString(thuoc.getSoLuong()));
		txtDonGia.setText(Double.toString(thuoc.getDonGia()));
		txtThanhPhan.setText(thuoc.getThanhPhan());
		txtCongDung.setText(thuoc.getCongDung());
		snNgayNhap.setValue(thuoc.getNgayNhapThuoc());
		snHSD.setValue(thuoc.getHanSuDung());
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
	
	public void update() {
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
			Date ngayNhap = (Date) snNgayNhap.getValue();
			Date HSD = (Date) snHSD.getValue();
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
	
	@Override
	protected void reload() {
		// TODO Auto-generated method stub
		super.reload();
	}
	
	@Override
	public void removeTextlblTB() {
		// TODO Auto-generated method stub
		super.removeTextlblTB();
	}
	
	@Override
	public boolean validData() {
		// TODO Auto-generated method stub
		return super.validData();
	}
	
//	public boolean validData() {
//		int sum = 0;
//		String tenSP = txtTenSP.getText().trim();
//		if(!tenSP.matches("^[a-zA-Z0-9 ]+$")) {
//			lblTBTenSP.setText("Sai tên sản phẩm");
//			sum++;
//		}
//		
//		String soLuong = txtSoLuong.getText().trim();
//		if(!soLuong.matches("^[0-9]+$")) {
//			lblTBSoLuong.setText("Sai số lượng");
//			sum++;
//		}
//		String donGia = txtDonGia.getText().trim();
//		if(!donGia.matches("^[1-9][0-9]{3,}")){
//			lblTBDonGia.setText("Sai đơn giá");
//			sum++;
//		}
//		
//		if(sum > 0) {
//			return false;
//		}
//		
//		return true;
//	}
	
//	public class CountryComboBox extends JComboBox<String> {
//	    
//	    /**
//		 * 
//		 */
//		private static final long serialVersionUID = 1L;
//
//		public CountryComboBox() {
//	        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
//	        Locale[] locales = Locale.getAvailableLocales();
//	        Arrays.sort(locales, Comparator.comparing(Locale::getDisplayCountry));
//	        for (Locale locale : locales) {
//	            String country = locale.getDisplayCountry();
//	            if (!country.isEmpty() && !(model.getIndexOf(country) != -1)) {
//	                model.addElement(country);
//	            }
//	        }
//
//	        setModel(model);
//	    }
	    
//	}
	
//	public JSpinner ngayNhap() {
//		// Lấy ngày hiện tại từ đối tượng Calendar
//		Calendar calendar = Calendar.getInstance();
//		Date currentDate = calendar.getTime();
//
//		SpinnerDateModel dateModel = new SpinnerDateModel(currentDate, null, null, Calendar.DAY_OF_MONTH);
//		JSpinner spinner = new JSpinner(dateModel);
//		
//		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
//		spinner.setEditor(dateEditor);
//		
//		return spinner;
//	}
	
	
//	public JSpinner hanSuDung() {
//		// Lấy ngày hiện tại từ đối tượng Calendar
//		Calendar calendar = Calendar.getInstance();
//		Date currentDate = calendar.getTime();
//
//		// Thêm 7 ngày để tạo ngày trong tương lai
//		calendar.add(Calendar.DAY_OF_MONTH, 7);
//		Date futureDate = calendar.getTime();
//
//		// Tạo SpinnerDateModel với ngày trong tương lai
//		SpinnerDateModel dateModel = new SpinnerDateModel(futureDate, currentDate, null, Calendar.DAY_OF_MONTH);
//		JSpinner spinner = new JSpinner(dateModel);
//		
//		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
//		spinner.setEditor(dateEditor);
//		return spinner;
//	}
	
}
