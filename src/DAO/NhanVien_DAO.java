package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import connectDB.ConnectDB;
import entity.CT_HoaDon;
import entity.CaTruc;
import entity.HoaDon;
import entity.NhanVien;
import entity.Thuoc;

public class NhanVien_DAO {
	
	public ArrayList<NhanVien> getallNhanVien() {
		
		ArrayList<NhanVien> dsnv = new ArrayList<NhanVien>();
		try {
			
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "Select * from nhanvien";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				
				String maNhanVien = rs.getString(1);
				String hoNhanVien = rs.getString(2);
				String tenNhanVien = rs.getString(3);
				String soDienThoai = rs.getString(4);
				String emailNhanVien = rs.getString(5);
				CaTruc caTruc = new CaTruc(rs.getString(6));
				boolean gioiTinh = rs.getBoolean(7);
				String chucVu = rs.getString(8);
				double tienLuong = rs.getDouble(9);
				String matKhau = rs.getString(10);
				
				NhanVien nv = new NhanVien(maNhanVien, hoNhanVien, tenNhanVien, soDienThoai, emailNhanVien, caTruc, gioiTinh, chucVu, tienLuong, matKhau);
				dsnv.add(nv);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dsnv;		
	}
	
	public NhanVien getNhanVienTheoMaNV(String maNV) {
		NhanVien nv = new NhanVien();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		
		try {
			
			String sql = "Select * from NhanVien where maNhanVien = ?";
			statement=con.prepareStatement(sql);
			statement.setString(1, maNV);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				
				nv.setMaNhanVien(rs.getString(1));
				nv.setHoNhanVien(rs.getString(2));
				nv.setTenNhanVien(rs.getString(3));
				nv.setSoDienThoai(rs.getString(4));
				nv.setEmailNhanVien(rs.getString(5));
				nv.setCaTruc(new CaTruc(rs.getString(6)));
				nv.setGioiTinh(rs.getBoolean(7));
				nv.setChucVu(rs.getString(8));
				nv.setTienLuong(rs.getDouble(9));
				nv.setMatKhau(rs.getString(10));
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			try {
				statement.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		return nv;
		
	}
	
	public boolean login(NhanVien nv) {
		
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		int count = 0;
		try {
			
			String sql = "Select * from NhanVien where maNhanVien = ? and matkhau = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, nv.getMaNhanVien());
			statement.setString(2, nv.getMatKhau());
			
			boolean result = statement.execute();
			
			if (result) {
			    ResultSet rs = statement.getResultSet();
			    while (rs.next()) {
			        count++;
			    }
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			try {
				statement.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return count>0; 
		
	}
	
	public ArrayList<String> getChucVu() {
		
		ArrayList<String> dsChucVu = new ArrayList<String>();
		
		try {
			
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "SELECT chucVu FROM NhanVien GROUP BY chucVu";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String ChucVu = rs.getString(1);
				dsChucVu.add(ChucVu);
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dsChucVu;
		
	}
	
	public String maNVAuto() {
		String maMoi = null;
		String maHienTai = null;
		
		try {
			
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();	
			String sql = "SELECT TOP 1 maNhanVien FROM NhanVien ORDER BY maNhanVien DESC";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				maHienTai = rs.getString(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String kyTuCuoi = maHienTai.replaceAll("[^0-9]+", "");
		String kyTuMoi = Integer.toString(Integer.parseInt(kyTuCuoi) + 1);
		
		maMoi = "NV" + kyTuMoi;
		return maMoi;
	}

public ArrayList<NhanVien> filterNangCao(String gioiTinh, String caTruc, String chucVu) {
		
		ArrayList<NhanVien> dsNhanVien = new ArrayList<NhanVien>();
		
		try {
			
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "SELECT * FROM NhanVien ";
			
			gioiTinh = gioiTinh.replace("Tất cả", "");
			gioiTinh = gioiTinh.replace("Nam", "True");
			gioiTinh = gioiTinh.replace("Nữ", "False");
			caTruc = caTruc.replace("Tất cả", "");
			caTruc = caTruc.replace("Ca sáng", "CA01");
			caTruc = caTruc.replace("Ca chiều", "CA02");
			chucVu = chucVu.replace("Tất cả", "");
			
			if (!gioiTinh.isBlank()) {
			    sql += " WHERE gioiTinh = N'" + gioiTinh.trim() + "'";
			    if (!caTruc.isBlank()) {
			        sql += " AND macatruc = N'" + caTruc.trim() + "'";
			        if (!chucVu.isBlank()) 
			            sql += " AND chucvu = N'" + chucVu.trim() + "'";
			    } else 
			    	if (!chucVu.isBlank()) 
			            sql += " AND chucvu = N'" + chucVu.trim() + "'";
			     else {
			        sql += " AND 1=1";
			    } 
			} else 
				if (!caTruc.isBlank()) {
				        sql += " Where macatruc = N'" + caTruc.trim() + "'";
				        if (!chucVu.isBlank()) 
				            sql += " AND chucvu = N'" + chucVu.trim() + "'";
				        else 
				        	if (!chucVu.isBlank()) 
				        		sql += " AND chucvu = N'" + chucVu.trim() + "'";
				        	else {
				        		sql += " AND 1=1";
				        	}
				} else if (!chucVu.isBlank()) 
						sql += " Where chucvu = N'" + chucVu.trim() + "'";
		        	else
		        		sql += " Where 1=1";
				
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				
				String maNhanVien = rs.getString(1);
				String ho = rs.getString(2);
				String ten = rs.getString(3);
				String sdt = rs.getString(4);
				String email = rs.getString(5);
				String maCaTruc = rs.getString(6);
				boolean phai  = rs.getBoolean(7);
				String chuc = rs.getString(8);
				float tienLuong = rs.getFloat(9);
				String mk = rs.getString(10);
				
				CaTruc ct = new CaTruc(maCaTruc);
				
				NhanVien nv = new NhanVien(maNhanVien,ho,ten,sdt,email,ct,phai,
						chuc,tienLuong,mk);
				dsNhanVien.add(nv);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dsNhanVien;
		
	}

	public boolean insertNhanVien(NhanVien nv) {
		
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		
		try {
			stmt = con.prepareStatement("INSERT INTO NhanVien VALUES"
					+ "(?, ?, ?, ?, ?, ?, ?,?,?,?)");
			stmt.setString(1, nv.getMaNhanVien());
			stmt.setString(2, nv.getHoNhanVien());
			stmt.setString(3, nv.getTenNhanVien());
			stmt.setString(4, nv.getSoDienThoai());
			stmt.setString(5, nv.getEmailNhanVien());
			stmt.setString(6, nv.getCaTruc().getMaCaTruc());
			stmt.setBoolean(7, nv.isGioiTinh());
			stmt.setString(8, nv.getChucVu());
			stmt.setDouble(9, nv.getTienLuong());
			stmt.setString(10, "123");
			n = stmt.executeUpdate();	
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		finally {
			try {
				stmt.close();
				
			} catch (SQLException e2) {
				e2.printStackTrace();
				
			}
		}
		return n>0;
	}
	public boolean xoaNhanVien(String ma) {
		
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		
		try {
			stmt = con.prepareStatement("DELETE  FROM NhanVien WHERE maNhanVien = ?");
			stmt.setString(1, ma);
			n = stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		
		finally {
			try {
				stmt.close();
				
			} catch (SQLException e2) {
				e2.printStackTrace();
				
			}
		}
		return n>0;
	}

	public boolean updateNhanVien(NhanVien nv) {
		
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		
		try {
			stmt = con.prepareStatement("UPDATE NhanVien SET ho = ?, ten = ?, sodienthoai = ?, email = ?, macatruc =?, gioitinh = ?, chucvu = ?, tienluong=? WHERE maNhanVien = ?");
			stmt.setString(1, nv.getHoNhanVien());
			stmt.setString(2, nv.getTenNhanVien());
			stmt.setString(3, nv.getSoDienThoai());
			stmt.setString(4, nv.getEmailNhanVien());
			stmt.setString(5, nv.getCaTruc().getMaCaTruc());
			stmt.setBoolean(6, nv.isGioiTinh());
			stmt.setString(7, nv.getChucVu());
			stmt.setDouble(8, nv.getTienLuong());
			stmt.setString(9, nv.getMaNhanVien());
			n = stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		
		finally {
			try {
				stmt.close();
				
			} catch (SQLException e2) {
				e2.printStackTrace();
				
			}
		}
		return n>0;
	}
	
	public ArrayList<NhanVien> filterNhanVien(String regex) {
		
		ArrayList<NhanVien> dsnv = new ArrayList<NhanVien>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		
		try {
			
			String sql = "SELECT TOP 50 * FROM NhanVien WHERE ten LIKE ? OR maNhanVien LIKE ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, "%"+regex+"%");
			stmt.setString(2, "%"+regex+"%");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				String maNhanVien = rs.getString(1);
				String hoNhanVien = rs.getString(2);
				String tenNhanVien = rs.getString(3);
				String soDienThoai = rs.getString(4);
				String emailNhanVien = rs.getString(5);
				CaTruc caTruc = new CaTruc(rs.getString(6));
				boolean gioiTinh = rs.getBoolean(7);
				String chucVu = rs.getString(8);
				double tienLuong = rs.getDouble(9);
				String matKhau = rs.getString(10);
				
				NhanVien nv = new NhanVien(maNhanVien, hoNhanVien, tenNhanVien, soDienThoai, emailNhanVien, caTruc, gioiTinh, chucVu, tienLuong, matKhau);
				dsnv.add(nv);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			try {
				stmt.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		return dsnv;
		
	}
	
}

