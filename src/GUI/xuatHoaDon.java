package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import DAO.HoaDon_DAO;
import DAO.KhachHang_DAO;
import connectDB.ConnectDB;
import entity.CT_HoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;

public class xuatHoaDon extends JFrame implements ActionListener, DocumentListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private NhanVien nvlogin;
	
	private HoaDon_DAO hoadon_dao;
	private KhachHang_DAO khachhang_dao;
	
	private DefaultTableModel model;
	private JTable table;
	
	private JLabel lblTienThuaValue;
	
	private JTextField txtTienKhach;
	
	private HoaDon hoadon;
	private ArrayList<CT_HoaDon> listCTHD;
	
	private DecimalFormat formatter = new DecimalFormat("#,###");
	
	private JButton btnThanhToan;
	
	public xuatHoaDon(NhanVien nv, HoaDon hd, ArrayList<CT_HoaDon> cthd) {
		
		// =================
		
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		hoadon_dao = new HoaDon_DAO();
		khachhang_dao = new KhachHang_DAO();
		
		hoadon = hd;
		listCTHD = cthd;
		
		KhachHang kh = khachhang_dao.getKhachHangTheoMaKH(hd.getKhachHang().getMaKhachHang());
		
		setTitle("Thanh toán cho hóa đơn " + hd.getMaHoaDon());
		setSize(700, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		// panelNorth
		JPanel panelNorth = new JPanel();
		panelNorth.setPreferredSize(new Dimension(700, 40));
		JLabel lblTitle;
		panelNorth.add(lblTitle = new JLabel("Thanh toán".toUpperCase()));
		lblTitle.setFont(new Font("Arial", Font.BOLD, 25));
		
		// panelWest
		JPanel panelWest = new JPanel();
		panelWest.setPreferredSize(new Dimension(380, 400));
		String[] colHeaderCTHD = {"STT","Tên","Đơn giá","Số lượng","Tổng tiền"};
		
		model = new DefaultTableModel(colHeaderCTHD, 0) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		table = new JTable(model);
		table.setRowHeight(20);
		
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(JLabel.CENTER);
		
		table.getColumnModel().getColumn(0).setCellRenderer(center);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setCellRenderer(render);
		table.getColumnModel().getColumn(3).setCellRenderer(center);
		table.getColumnModel().getColumn(4).setCellRenderer(render);
		
		JScrollPane spCTHD = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		spCTHD.setPreferredSize(new Dimension(350, 400));
		
		setDuLieuChiTietHoaDon();
		
		panelWest.add(spCTHD);
		add(panelWest, BorderLayout.WEST);
		
		// panelCenter
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(null);
		
		JLabel lblName, lblNameValue, lblSDT, lblSDTValue, lblEmail, lblEmailValue,
			lblHr, lblTongSP, lblTongSPValue, lblTongTien, lblTongTienValue, 
			lblTienKhach, lblTienThua, lblTinhTrang, lblTinhTrangValue,
			lblKeDon, lblKeDonValue;
		
		panelCenter.add(lblName = new JLabel("Khách hàng: "));
		lblName.setBounds(20, 20, 100, 30);
		
		panelCenter.add(lblNameValue = new JLabel(kh.getHoKhachHang()+" "+kh.getTenKhachHang()));
		lblNameValue.setFont(new Font("Arial", Font.PLAIN, lblName.getFont().getSize()));
		lblNameValue.setBounds(120, 20, 200, 30);
		
		panelCenter.add(lblSDT = new JLabel("Số điện thoại: "));
		lblSDT.setBounds(20, 50, 100, 30);
		
		panelCenter.add(lblSDTValue = new JLabel(kh.getSoDienThoai()));
		lblSDTValue.setFont(new Font("Arial", Font.PLAIN, lblSDTValue.getFont().getSize()));
		lblSDTValue.setBounds(120, 50, 200, 30);
		
		panelCenter.add(lblEmail = new JLabel("Email: "));
		lblEmail.setBounds(20, 80, 100, 30);
		
		panelCenter.add(lblEmailValue = new JLabel(kh.getEmailKhachHang()));
		lblEmailValue.setFont(new Font("Arial", Font.PLAIN, lblEmailValue.getFont().getSize()));
		lblEmailValue.setBounds(120, 80, 200, 30);
		
		panelCenter.add(lblHr = new JLabel("_____________________________________"));
		lblHr.setFont(new Font("Arial", Font.PLAIN, lblHr.getFont().getSize()));
		lblHr.setBounds(20, 110, 300, 30);
		
		panelCenter.add(lblTongSP = new JLabel("Số lượng thuốc: "));
		lblTongSP.setFont(new Font("Arial", Font.PLAIN, lblTongSP.getFont().getSize()));
		lblTongSP.setBounds(20, 140, 100, 30);
		
		panelCenter.add(lblTongSPValue = new JLabel(tongSoLuongThuoc()+""));
		lblTongSPValue.setFont(new Font("Arial", Font.PLAIN, lblTongSPValue.getFont().getSize()));
		lblTongSPValue.setBounds(120, 140, 200, 30);
		
		panelCenter.add(lblTinhTrang = new JLabel("Tình trạng: "));
		lblTinhTrang.setFont(new Font("Arial", Font.PLAIN, lblTinhTrang.getFont().getSize()));
		lblTinhTrang.setBounds(20, 170, 100, 30);
		
		panelCenter.add(lblTinhTrangValue = new JLabel(hd.getThanhToan() ? "Đã thanh toán" : "Chưa thanh toán"));
		lblTinhTrangValue.setFont(new Font("Arial", Font.PLAIN, lblTinhTrangValue.getFont().getSize()));
		lblTinhTrangValue.setBounds(120, 170, 200, 30);
		lblTinhTrangValue.setForeground(hd.getThanhToan() ? Color.decode("#008000") : Color.red);
		
		panelCenter.add(lblKeDon = new JLabel("Tình trạng: "));
		lblKeDon.setFont(new Font("Arial", Font.PLAIN, lblKeDon.getFont().getSize()));
		lblKeDon.setBounds(20, 200, 100, 30);
		
		panelCenter.add(lblKeDonValue = new JLabel(hd.isDangHoaDon() ? "Kê đơn" : "Không kê đơn"));
		lblKeDonValue.setFont(new Font("Arial", Font.PLAIN, lblKeDonValue.getFont().getSize()));
		lblKeDonValue.setBounds(120, 200, 200, 30);
		
		panelCenter.add(lblTongTien = new JLabel("Tổng tiền: "));
		lblTongTien.setFont(new Font("Arial", Font.PLAIN, lblTongTien.getFont().getSize()));
		lblTongTien.setBounds(20, 230, 100, 30);
		
		panelCenter.add(lblTongTienValue = new JLabel(tongTienStr()));
		lblTongTienValue.setFont(new Font("Arial", Font.BOLD, 20));
		lblTongTienValue.setBounds(120, 230, 200, 30);
		
		panelCenter.add(lblTienKhach = new JLabel("Tiền khách đưa: "));
		lblTienKhach.setFont(new Font("Arial", Font.PLAIN, lblTienKhach.getFont().getSize()));
		lblTienKhach.setBounds(20, 260, 100, 30);
		
		lblTienThuaValue = new JLabel();
		if(hd.getThanhToan()) {
			panelCenter.add(lblTienKhach = new JLabel(formatter.format(hd.getTienKhach())+"₫"));
			lblTienKhach.setFont(new Font("Arial", Font.BOLD, 20));
			lblTienKhach.setBounds(120, 260, 100, 30);
			txtTienKhach = new JTextField(hd.getTienKhach()+"");
			lblTienThuaValue.setFont(new Font("Arial", Font.BOLD, 12));
		}else {
			panelCenter.add(txtTienKhach = new JTextField());
			txtTienKhach.setFont(new Font("Arial", Font.BOLD, 20));
			txtTienKhach.setBounds(120, 260, 100, 30);
			lblTienThuaValue.setFont(new Font("Arial", Font.BOLD, 20));
		}

		panelCenter.add(lblTienThua = new JLabel("Tiền thừa: "));
		lblTienThua.setFont(new Font("Arial", Font.PLAIN, lblTienThua.getFont().getSize()));
		lblTienThua.setBounds(20, 290, 200, 30);
		
		panelCenter.add(lblTienThuaValue);
		lblTienThuaValue.setBounds(120, 290, 100, 30);
		lblTienThuaValue.setName("tienthua");
		
		if(hd.getThanhToan()) {
			lblTienThuaValue.setText(tienThuaStr(hd.getTienKhach()));
			btnThanhToan = new JButton("Hủy thanh toán");
		}else {
			btnThanhToan = new JButton("Thanh toán");
		}
		
		panelCenter.add(btnThanhToan);
		btnThanhToan.setBounds(20, 350, 200, 30);
		
		add(panelCenter, BorderLayout.CENTER);
		add(panelNorth, BorderLayout.NORTH);
		
		txtTienKhach.getDocument().putProperty("owner", txtTienKhach);
		txtTienKhach.getDocument().addDocumentListener(this);
		txtTienKhach.addKeyListener(this);
		btnThanhToan.addActionListener(this);
		
	}
	
	public int tongSoLuongThuoc() {
		int count = 0;
		for(CT_HoaDon hd : listCTHD) {
			count+=hd.getSoLuong();
		}
		return count;
	}
	
	public double tienThua(String tienKhach) {
		try {
			return Double.parseDouble(tienKhach.trim())-tongTien();
		} catch (Exception e) {
			return -1;
		}
	}
	
	public String tienThuaStr(double tienKhach) {
		
		double tienThua = tienKhach-tongTien();
		
		if(tienThua<0)
			return "chưa đủ tiền";
		
		return "<html><font color='red'>"+formatter.format(tienThua)+"</font>₫</html>";
		
	}
	
	public double tongTien() {
		Double tongTien = (double) 0;
		for(CT_HoaDon ct : listCTHD) {
			tongTien += ct.getTongtien()*ct.getSoLuong();
		}
		return tongTien;
	}
	
	public double tienKhach() {
		try {
			return Double.parseDouble(txtTienKhach.getText().trim());
		} catch (Exception e) {
			return 0.0;
		}
	}
	
	
	public String tongTienStr() {
		return "<html><font color='red'>"+formatter.format(tongTien())+"</font>₫</html>";
	}
	
	public void setDuLieuChiTietHoaDon() {
		
		DefaultTableModel temp = (DefaultTableModel) table.getModel();
		temp.getDataVector().removeAllElements();
		
		if(listCTHD.isEmpty())
			return;
		
		int i = 1;
		
		for(CT_HoaDon ct : listCTHD) {
						
			model.addRow(new Object[] {i,ct.getThuocCT().getTenThuoc(),formatter.format(ct.getDongia()),
					ct.getSoLuong(),formatter.format(ct.getTongtien())});
			
			i++;
		}
		
	}
	
	DefaultTableCellRenderer render = new DefaultTableCellRenderer() {
		
		private static final long serialVersionUID = 1L;
		@Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        // Lấy đối tượng JLabel của TableCellRenderer
	        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        // Nếu là cột đầu tiên, thiết lập font in đậm
	        if (column == 6) {
	            Font boldFont = new Font(label.getFont().getName(), Font.BOLD, label.getFont().getSize());
	            label.setFont(boldFont);
	            label.setHorizontalAlignment(JLabel.RIGHT);
	        }
	        return label;
	    }
	};

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	
		Object o = e.getSource();
		if(o.equals(btnThanhToan)) {
			
			HoaDon hd = hoadon;
			String str = hoadon.getThanhToan() ? "hủy thanh toán?" : "thanh toán?";

			if(tienThua(txtTienKhach.getText())<0&&hd.getThanhToan()==false) {
				JOptionPane.showMessageDialog(this, "Không đủ tiền để tiến thành thực hiện thanh toán!");
			}else {
			
				if(JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn sẽ " +
						str,"Xác nhận cập nhật",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
					
					hd.setTienKhach(hoadon.getThanhToan() ? 0.0 : tienKhach());
					hd.setThanhToan(hoadon.getThanhToan() ? false : true);
					
					
					
					hd.setTongTien(tongTien());
					hd.setSoLuong(tongSoLuongThuoc());
					
					if(hoadon_dao.kiemTraTonTai(hd.getMaHoaDon())) {
						if(hoadon_dao.updateThanhToan(hd)) {
							new xuatHoaDon(nvlogin, hd, listCTHD).setVisible(true);
							LichSu.setDuLieuLichSu(1);
							dispose();
						}else
							JOptionPane.showMessageDialog(this, "Lỗi cập nhật.");
					}else {
						if(hoadon_dao.create(hd, listCTHD)) {
							new xuatHoaDon(nvlogin, hd, listCTHD).setVisible(true);
							LichSu.setDuLieuLichSu(1);
							dispose();
						}else
							JOptionPane.showMessageDialog(this, "Lỗi cập nhật.");
					}
				
				}
			
			}
			
		}
		
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
		double tienKhach = 0.0;
		
		try {
			tienKhach = Double.parseDouble(txtTienKhach.getText().trim());
		} catch (Exception e2) {
			tienKhach = 0;
		}
		
		lblTienThuaValue.setText(tienThuaStr(tienKhach));
		lblTienThuaValue.setFont(new Font("Arial", Font.BOLD, 20));
		
		if(tienThuaStr(tienKhach).trim().equals("chưa đủ tiền"))
			lblTienThuaValue.setFont(new Font("Arial", Font.BOLD, 12));
		
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			try {
				txtTienKhach.setText(Integer.parseInt(txtTienKhach.getText().trim())*1000+"");
			} catch (Exception e2) {}
		}	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
