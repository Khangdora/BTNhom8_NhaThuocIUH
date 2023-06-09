package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import javax.swing.text.DocumentFilter.FilterBypass;

import DAO.NhaCungCap_DAO;
import GUI.KhoThuoc.NumberOnlyFilter;
import connectDB.ConnectDB;
import entity.NhaCungCap;

public class NhaCungCap_GUI extends JFrame implements ActionListener, MouseListener, DocumentListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelNCC;
	private JTable tableNCC;
	private JTextField txtMa;
	private JTextField txtTen;
	private JTextField txtDiaChi;
	private JTextField txtSDT;
	private JTextField txtEmail;
	private JTextField txtSearchName;
	private JButton btnAdd;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnXoaTrang;
	
	private NhaCungCap_DAO ncc_dao;
	private JButton btnCanel;
	private JFrame frameAdd;
	private AbstractButton btnCanelAdd;
	private AbstractButton btnSaveAdd;
	private JButton btnSaveUpdate;
	private JButton btnCanelUpdate;
	private JFrame frameUpdate;
	private JLabel lblTBTen;
	private JLabel lblTBDC;
	private JLabel lblTBSDT;
	private JLabel lblTBEmail;

	public NhaCungCap_GUI() {
		
		try {
			ConnectDB.getInstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ncc_dao = new NhaCungCap_DAO();
		
		setTitle("Nhà cung cấp");
		setSize(800, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		
		createGUI();
	}
	
	public void createGUI() {
		this.setLayout(new BorderLayout());
		JPanel panelNorth = new JPanel();
		panelNorth.setPreferredSize(new Dimension(panelNorth.getPreferredSize().height, 30));
		panelNorth.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
		JLabel lblTitle = new JLabel("DANH SÁCH NHÀ CUNG CẤP");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 15));
		lblTitle.setForeground(Color.BLUE);
		panelNorth.add(lblTitle);
		this.add(panelNorth, BorderLayout.NORTH);
		//-----------------------------------------------
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
		
		JPanel panelSearch = new JPanel();
		panelNorth.setPreferredSize(new Dimension(panelNorth.getPreferredSize().height, 30));
		JLabel lblSearchName = new JLabel("Nhập thông tin cần tìm: ");
		txtSearchName = new JTextField(20);
		txtSearchName.getDocument().addDocumentListener(new DocumentListener() {
		    @Override
		    public void insertUpdate(DocumentEvent e) {
		        searchProduct();
		    }

		    @Override
		    public void removeUpdate(DocumentEvent e) {
		        searchProduct();
		    }

		    @Override
		    public void changedUpdate(DocumentEvent e) {
		        searchProduct();
		    }

		    private void searchProduct() {
		    	String searchText = txtSearchName.getText().trim();
		    	TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableNCC.getModel());
		    	tableNCC.setRowSorter(sorter);
		    	RowFilter<TableModel, Object> filter1 = RowFilter.regexFilter("(?i)" + searchText, 0);
		    	RowFilter<TableModel, Object> filter2 = RowFilter.regexFilter("(?i)" + searchText, 1);
		    	RowFilter<TableModel, Object> filter3 = RowFilter.regexFilter("(?i)" + searchText, 2);
		    	RowFilter<TableModel, Object> filter4 = RowFilter.regexFilter("(?i)" + searchText, 3);
		    	RowFilter<TableModel, Object> filter5 = RowFilter.regexFilter("(?i)" + searchText, 4);
		    	RowFilter<TableModel, Object> filter = RowFilter.orFilter(Arrays.asList(filter1, filter2, filter3, filter4, filter5));
		    	sorter.setRowFilter(filter);
		    }
		});
		panelSearch.add(lblSearchName);
		panelSearch.add(txtSearchName);
		
		panelSearch.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
		panelCenter.add(panelSearch);
		//-----------------------------------------------
		JPanel panelTable = new JPanel();
		String[] colHeader = {"Mã nhà cung cấp","Tên nhà cung cấp","Địa chỉ","Số điện thoại","Email"};
		
		modelNCC = new DefaultTableModel(colHeader, 0) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableNCC = new JTable(modelNCC);
		tableNCC.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableNCC.setRowHeight(20);
		JScrollPane sp = new JScrollPane(tableNCC, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,  JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(782, 250));
		panelTable.add(sp);
		panelCenter.add(panelTable);
		
		DefaultTableCellRenderer right = new DefaultTableCellRenderer();
		right.setHorizontalAlignment(JLabel.RIGHT);
		tableNCC.getColumnModel().getColumn(3).setCellRenderer(right);
		
		this.add(panelCenter, BorderLayout.CENTER);
		
		//------------------------------------------------------
		
		JPanel panelSouth = new JPanel();
		btnAdd = new JButton("Thêm");
		btnUpdate = new JButton("Sửa");
		btnDelete = new JButton("Xóa");
		btnCanel = new JButton("Thoát");
		
		
		panelSouth.add(btnAdd);
		panelSouth.add(btnUpdate);
		panelSouth.add(btnDelete);
		panelSouth.add(btnCanel);
		
		
		this.add(panelSouth, BorderLayout.SOUTH);
		btnAdd.addActionListener(this);
		tableNCC.addMouseListener(this);
		btnDelete.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnCanel.addActionListener(this);
		//---------------------------------------------
		
		setDuLieuNCC();
		
		
	}
	
	public JFrame guiUpdate() {
		frameUpdate = new JFrame();
		frameUpdate.setTitle("Sửa nhà cung cấp");
		frameUpdate.setSize(500, 300);
		frameUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameUpdate.setLocationRelativeTo(null);
		frameUpdate.setResizable(false);
		
		
		JPanel myPanel = new JPanel();
		myPanel.setLayout(null);
		
		JLabel lblMa = new JLabel("Mã nhà cung cấp: ");
		txtMa = new JTextField();
		txtMa.setEditable(false);
		myPanel.add(lblMa);
		myPanel.add(txtMa);	
		
		JLabel lblTen = new JLabel("Tên nhà cung cấp: ");
		txtTen = new JTextField();
		txtTen.setName("thongbao_ten");
		lblTBTen = new JLabel();
		myPanel.add(lblTen);
		myPanel.add(txtTen);
		myPanel.add(lblTBTen);
		
		JLabel lblDiaChi = new JLabel("Địa chỉ: ");
		txtDiaChi = new JTextField();
		txtDiaChi.setName("thongbao_dc");
		lblTBDC = new JLabel();
		myPanel.add(lblDiaChi);
		myPanel.add(txtDiaChi);
		myPanel.add(lblTBDC);
		
		JLabel lblSDT = new JLabel("Số điện thoại: ");
		txtSDT = new JTextField();
		txtSDT.setDocument(new PlainDocument());
		((PlainDocument)txtSDT.getDocument()).setDocumentFilter(new NumberOnlyFilter());
		txtSDT.setName("thongbao_sdt");
		lblTBSDT = new JLabel();
		myPanel.add(lblSDT);
		myPanel.add(txtSDT);
		myPanel.add(lblTBSDT);
		
		JLabel lblEmail = new JLabel("Email: ");
		txtEmail = new JTextField();
		txtEmail.setName("thongbao_email");
		lblTBEmail = new JLabel();
		myPanel.add(lblEmail);
		myPanel.add(txtEmail);
		myPanel.add(lblTBEmail);
		
		btnSaveUpdate = new JButton("Lưu");
		btnXoaTrang = new JButton("Xóa trắng");
		btnCanelUpdate = new JButton("Thoát");
		myPanel.add(btnSaveUpdate);
		myPanel.add(btnXoaTrang);
		myPanel.add(btnCanelUpdate);
		
		int height = 20, width1 = 120, width2 = 360, x = 10, y = 17;
		lblMa.setBounds(x, y, width1, height);
		txtMa.setBounds(width1, y, width2, height);
		
		lblTen.setBounds(x, y*3, width1, height);
		txtTen.setBounds(width1, y*3, width2, height);
		lblTBTen.setBounds(width1, y*4, width2, height);
		
		lblDiaChi.setBounds(x, y*5, width1, height);
		txtDiaChi.setBounds(width1, y*5, width2, height);
		lblTBDC.setBounds(width1, y*6, width2, height);
		
		lblSDT.setBounds(x, y*7, width1, height);
		txtSDT.setBounds(width1, y*7, width2, height);
		lblTBSDT.setBounds(width1, y*8, width2, height);
		
		lblEmail.setBounds(x, y*9, width1, height);
		txtEmail.setBounds(width1, y*9, width2, height);
		lblTBEmail.setBounds(width1, y*10, width2, height);
		
		
//		btnSaveUpdate.setBounds(160, 235, 100, 25);
		btnSaveUpdate.setBounds(270, 235, 100, 25);
		btnCanelUpdate.setBounds(380, 235, 100, 25);
		
		
		
		frameUpdate.add(myPanel);
		
		
		
		//-----------------------------------------------
		
		btnSaveUpdate.addActionListener(this);
//		btnXoaTrang.addActionListener(this);
		btnCanelUpdate.addActionListener(this);
		
		txtTen.getDocument().putProperty("owner", txtTen);
		txtTen.getDocument().addDocumentListener(this);
		
		txtDiaChi.getDocument().putProperty("owner", txtDiaChi);
		txtDiaChi.getDocument().addDocumentListener(this);
		
		txtSDT.getDocument().putProperty("owner", txtSDT);
		txtSDT.getDocument().addDocumentListener(this);
		
		txtEmail.getDocument().putProperty("owner", txtEmail);
		txtEmail.getDocument().addDocumentListener(this);
		
		//----------------------------------
		return frameUpdate;
	}
	
	public JFrame guiAdd() {
		frameAdd = new JFrame();
		frameAdd.setTitle("Thêm nhà cung cấp");
		frameAdd.setSize(500, 300);
		frameAdd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameAdd.setLocationRelativeTo(null);
		frameAdd.setResizable(false);
		
		
		JPanel myPanel = new JPanel();
		myPanel.setLayout(null);
		
		JLabel lblMa = new JLabel("Mã nhà cung cấp: ");
		txtMa = new JTextField(ncc_dao.maNCCAuto());
		txtMa.setEditable(false);
		myPanel.add(lblMa);
		myPanel.add(txtMa);	
		
		JLabel lblTen = new JLabel("Tên nhà cung cấp: ");
		txtTen = new JTextField();
		txtTen.setName("thongbao_ten");
		lblTBTen = new JLabel();
		myPanel.add(lblTen);
		myPanel.add(txtTen);
		myPanel.add(lblTBTen);
		
		JLabel lblDiaChi = new JLabel("Địa chỉ: ");
		txtDiaChi = new JTextField();
		txtDiaChi.setName("thongbao_dc");
		lblTBDC = new JLabel();
		myPanel.add(lblDiaChi);
		myPanel.add(txtDiaChi);
		myPanel.add(lblTBDC);
		
		JLabel lblSDT = new JLabel("Số điện thoại: ");
		txtSDT = new JTextField();
		txtSDT.setDocument(new PlainDocument());
		((PlainDocument)txtSDT.getDocument()).setDocumentFilter(new NumberOnlyFilter());
		txtSDT.setName("thongbao_sdt");
		lblTBSDT = new JLabel();
		myPanel.add(lblSDT);
		myPanel.add(txtSDT);
		myPanel.add(lblTBSDT);
		
		JLabel lblEmail = new JLabel("Email: ");
		txtEmail = new JTextField();
		txtEmail.setName("thongbao_email");
		lblTBEmail = new JLabel();
		myPanel.add(lblEmail);
		myPanel.add(txtEmail);
		myPanel.add(lblTBEmail);

		
		btnSaveAdd = new JButton("Lưu");
		btnXoaTrang = new JButton("Xóa trắng");
		btnCanelAdd = new JButton("Thoát");
		myPanel.add(btnSaveAdd);
		myPanel.add(btnXoaTrang);
		myPanel.add(btnCanelAdd);
		
		int height = 20, width1 = 120, width2 = 360, x = 10, y = 17;
		lblMa.setBounds(x, y, width1, height);
		txtMa.setBounds(width1, y, width2, height);
		
		lblTen.setBounds(x, y*3, width1, height);
		txtTen.setBounds(width1, y*3, width2, height);
		lblTBTen.setBounds(width1, y*4, width2, height);
		
		lblDiaChi.setBounds(x, y*5, width1, height);
		txtDiaChi.setBounds(width1, y*5, width2, height);
		lblTBDC.setBounds(width1, y*6, width2, height);
		
		lblSDT.setBounds(x, y*7, width1, height);
		txtSDT.setBounds(width1, y*7, width2, height);
		lblTBSDT.setBounds(width1, y*8, width2, height);
		
		lblEmail.setBounds(x, y*9, width1, height);
		txtEmail.setBounds(width1, y*9, width2, height);
		lblTBEmail.setBounds(width1, y*10, width2, height);
		
		
		btnSaveAdd.setBounds(160, 235, 100, 25);
		btnXoaTrang.setBounds(270, 235, 100, 25);
		btnCanelAdd.setBounds(380, 235, 100, 25);
		
		btnCanelAdd.addActionListener(this);
		btnXoaTrang.addActionListener(this);
		btnSaveAdd.addActionListener(this);
		frameAdd.add(myPanel);
		
		//-----------------------------------------------
		txtTen.getDocument().putProperty("owner", txtTen);
		txtTen.getDocument().addDocumentListener(this);
		
		txtDiaChi.getDocument().putProperty("owner", txtDiaChi);
		txtDiaChi.getDocument().addDocumentListener(this);
		
		txtSDT.getDocument().putProperty("owner", txtSDT);
		txtSDT.getDocument().addDocumentListener(this);
		
		txtEmail.getDocument().putProperty("owner", txtEmail);
		txtEmail.getDocument().addDocumentListener(this);
		
		return frameAdd;
	}
	
	public void update() {
		if(validData()) {
			String ma = txtMa.getText();
			String ten = txtTen.getText();
			String daiChi = txtDiaChi.getText();
			String sdt = txtSDT.getText();
			String email = txtEmail.getText();
			NhaCungCap ncc = new NhaCungCap(ma, ten, daiChi, sdt, email);
			if(ncc_dao.update(ncc)) {
				setDuLieuNCC();
				JOptionPane.showMessageDialog(this, "Cập nhật thành công");
			}
		}
	}
	
	public void delete() {
		int row = tableNCC.getSelectedRow();
		if(row < 0 || row >= tableNCC.getRowCount()) {
			JOptionPane.showMessageDialog(this, "Bạn chưa chọn nhà cung cấp muốn xóa");
		}
		else {
			String maNCC = tableNCC.getValueAt(row, 0).toString();
			NhaCungCap ncc = ncc_dao.getNCCTheoMaNCC(maNCC);
//			String tenNCC = ncc.getTenNCC();
			if(JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn xóa nhà cung cấp có tên: " + ncc.getTenNCC(), "Thông báo", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				if(ncc_dao.delete(ncc)) {
					setDuLieuNCC();
					KhoThuoc.docDuLieuVaoComboboxNCC();
				}
			}
		}
	}
	
	public void saveAdd() {
		if(validData()) {
			String ma = ncc_dao.maNCCAuto();
			String ten = txtTen.getText();
			String daiChi = txtDiaChi.getText();
			String sdt = txtSDT.getText();
			String email = txtEmail.getText();
			
			NhaCungCap ncc = new NhaCungCap(ma, ten, daiChi, sdt, email);
			
			if(ncc_dao.create(ncc)) {
				modelNCC.addRow(new Object[] {ncc.getMaNCC(), ncc.getTenNCC(), ncc.getDiaChiNCC(), ncc.getSdtNCC(), ncc.getEmailNCC()});
				xoaTrang();
				frameAdd.dispose();
				KhoThuoc.docDuLieuVaoComboboxNCC();
			}
		}
	}
	
	public void lblThongBao(JLabel lbl, int x, String str) {
		lbl.setFont(new Font("Serif", Font.ITALIC, 13));
		lbl.setText(str);
		if(x==0)
			lbl.setForeground(Color.red);
		else if(x==1)
			lbl.setForeground(Color.decode("#008000"));
	}
	
	public void setDuLieuTxt(int row) {
		txtMa.setText(tableNCC.getValueAt(row, 0).toString());
		txtTen.setText(tableNCC.getValueAt(row, 1).toString());
		txtDiaChi.setText(tableNCC.getValueAt(row, 2).toString());
		txtSDT.setText(tableNCC.getValueAt(row, 3).toString());
		txtEmail.setText(tableNCC.getValueAt(row, 4).toString());
	}
	
	public boolean validData() {
		int sum = 0;
		String email = txtEmail.getText().trim();
		if(!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
			lblThongBao(lblTBEmail, 0, "Sai email");
			sum++;
		}
		String sdt = txtSDT.getText().trim();
		if(!sdt.matches("^[0-9 -]{8,}$")) {
			lblThongBao(lblTBSDT, 0, "Sai số điện thoại");
			sum++;
		}
		String diaChi = txtDiaChi.getText().trim();
		if(!diaChi.matches("^[\\p{L}0-9 .,-\\\\]+$")) {
			lblThongBao(lblTBDC, 0, "Sai địa chỉ");
			sum++;
		}String tenSP = txtTen.getText().trim();
		if(!tenSP.matches("^[\\p{L}0-9 .]+$")) {
			lblThongBao(lblTBTen, 0, "Sai tên nhà cung cấp");
			sum++;
		}
		if(sum > 0) {
			return false;
		}
		
		return true;
	}
	
	public void xoaTrang() {
		txtMa.setText(ncc_dao.maNCCAuto());
		txtTen.setText("");
		txtDiaChi.setText("");
		txtSDT.setText("");
		txtEmail.setText("");
		tableNCC.clearSelection();
		lblTBTen.setText("");
		lblTBEmail.setText("");
		lblTBDC.setText("");
		lblTBSDT.setText("");
	}
	
	public void setDuLieuNCC() {
		DefaultTableModel dtm = (DefaultTableModel) tableNCC.getModel();
		dtm.getDataVector().removeAllElements();
		
		ArrayList<NhaCungCap> dsNCC = ncc_dao.getallNCC();
		for(NhaCungCap ncc : dsNCC) {
			modelNCC.addRow(new Object[] {ncc.getMaNCC(), ncc.getTenNCC(), ncc.getDiaChiNCC(), ncc.getSdtNCC(), ncc.getEmailNCC()});
		}
	}
	
	public Border borderTitle(String title) {
		Border border;
		border = BorderFactory.createTitledBorder(
			    BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1), title);
		((TitledBorder) border).setTitleFont(new Font("Time New Roman", Font.ITALIC, 10));
		return border;
	}
	
	public class NumberOnlyFilter extends DocumentFilter {
	    public void insertString(FilterBypass fb, int offset, String string,
	            AttributeSet attr) throws BadLocationException {

	        Document doc = fb.getDocument();
	        StringBuilder sb = new StringBuilder();
	        sb.append(doc.getText(0, doc.getLength()));
	        sb.insert(offset, string);

	        if (isNumber(sb.toString())) {
	            super.insertString(fb, offset, string, attr);
	        }
	    }
	    
	    public void replace(FilterBypass fb, int offset, int length, String text,
	            AttributeSet attrs) throws BadLocationException {

	        Document doc = fb.getDocument();
	        StringBuilder sb = new StringBuilder();
	        sb.append(doc.getText(0, doc.getLength()));
	        sb.replace(offset, offset + length, text);

	        if (isNumber(sb.toString())) {
	            super.replace(fb, offset, length, text, attrs);
	        }
	    }

	    public void remove(FilterBypass fb, int offset, int length)
	            throws BadLocationException {
	        Document doc = fb.getDocument();
	        StringBuilder sb = new StringBuilder();
	        sb.append(doc.getText(0, doc.getLength()));
	        sb.delete(offset + 1, offset + length);

	        if (isNumber(sb.toString())) {
	            super.remove(fb, offset, length);
	        }
	    }
	    
	    private boolean isNumber(String text) {
	        try {
	            Integer.parseInt(text);
	            return true;
	        } catch (NumberFormatException e) {
	            return false;
	        }
	    }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(btnAdd)) {
			guiAdd().setVisible(true);
		}
		if(o.equals(btnCanelAdd)) {
			frameAdd.dispose();
		}
		if(o.equals(btnSaveAdd)) {
			saveAdd();
		}
		if(o.equals(btnXoaTrang)) {
			xoaTrang();
		}
		if(o.equals(btnDelete)) {
			delete();
		}
		if(o.equals(btnUpdate)) {
			int row = tableNCC.getSelectedRow();
			if(row < 0 || row >= tableNCC.getRowCount()) {
				JOptionPane.showMessageDialog(this, "Bạn chưa chọn nhà cung cấp muốn sửa");
			}
			else {
				guiUpdate().setVisible(true);
				setDuLieuTxt(row);
			}
			
		}
		if(o.equals(btnCanelUpdate)) {
			frameUpdate.dispose();
		}
		if(o.equals(btnSaveUpdate)) {
			update();
		}
		if(o.equals(btnCanel)) {
			this.dispose();
		}
	}
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		JTextField textField = (JTextField) e.getDocument().getProperty("owner");
		if(textField != null) {
			if(textField.getName().equals("thongbao_ten")) {
				String tenSP = txtTen.getText().trim();
				if(!tenSP.matches("^[\\p{L}0-9 .]+$")) {
					lblThongBao(lblTBTen, 0, "Sai tên nhà cung cấp");
				}
				else {
					lblThongBao(lblTBTen, 1, "");
				}	
			}
			if(textField.getName().equals("thongbao_dc")) {
				String diaChi = txtDiaChi.getText().trim();
				if(!diaChi.matches("^[\\p{L}0-9 .,-\\\\]+$")) {
					lblThongBao(lblTBDC, 0, "Sai địa chỉ");
				}
				else {
					lblThongBao(lblTBDC, 1, "");
				}	
			}
			if(textField.getName().equals("thongbao_sdt")) {
				String sdt = txtSDT.getText().trim();
				if(!sdt.matches("^[0-9 -]{8,}$")) {
					lblThongBao(lblTBSDT, 0, "Sai số điện thoại");
				}
				else {
					lblThongBao(lblTBSDT, 1, "");
				}	
			}
			if(textField.getName().equals("thongbao_email")) {
				String email = txtEmail.getText().trim();
				if(!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
					lblThongBao(lblTBEmail, 0, "Sai email");
				}
				else {
					lblThongBao(lblTBEmail, 1, "");
				}	
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
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

	

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}
}