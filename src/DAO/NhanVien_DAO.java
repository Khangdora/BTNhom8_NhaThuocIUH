package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.CaTruc;
import entity.NhanVien;

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
	
	

}
