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
import entity.Thuoc;

public class Thuoc_DAO {
	
	int limit = 25;
	
	public int totalThuoc() {
		int totalRows = 0;
		
		try {
			
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "SELECT COUNT(*) AS total FROM Thuoc";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				totalRows = rs.getInt("total");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return totalRows;
	}
	
	public ArrayList<Thuoc> getallThuoc() {
		
		ArrayList<Thuoc> dsthuoc = new ArrayList<Thuoc>();
		try {
			
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "Select * from Thuoc";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				
				String maThuoc = rs.getString(1);
				String tenThuoc = rs.getString(2);
				String donViBan = rs.getString(3);
				int soLuong = rs.getInt(4);
				double donGia = rs.getDouble(5);
				String thanhPhan = rs.getString(6);
				String xuatXu = rs.getString(7);
				String congDung = rs.getString(8);
				String dangBaoChe = rs.getString(9);
				Date ngayNhapThuoc = rs.getDate(10);
				Date hanSuDung = rs.getDate(11);
				String thumbnail = rs.getString(12);
				
				Thuoc thuoc = new Thuoc(maThuoc, tenThuoc, donViBan, soLuong, donGia, thanhPhan, xuatXu, congDung, dangBaoChe, ngayNhapThuoc, hanSuDung, thumbnail);
				dsthuoc.add(thuoc);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dsthuoc;
	}
	
	public Thuoc getThuocTheoMaThuoc(String maThuoc) {
		Thuoc thuoc = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			
			String sql = "SELECT * FROM Thuoc WHERE maThuoc = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, maThuoc);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				
				String maThuocStr = rs.getString(1);
				String tenThuoc = rs.getString(2);
				String donViBan = rs.getString(3);
				int soLuong = rs.getInt(4);
				double donGia = rs.getDouble(5);
				String thanhPhan = rs.getString(6);
				String xuatXu = rs.getString(7);
				String congDung = rs.getString(8);
				String dangBaoChe = rs.getString(9);
				Date ngayNhapThuoc = rs.getDate(10);
				Date hanSuDung = rs.getDate(11);
				String thumbnail = rs.getString(12);
				
				thuoc = new Thuoc(maThuocStr, tenThuoc, donViBan, soLuong, donGia, thanhPhan, xuatXu, congDung, dangBaoChe, ngayNhapThuoc, hanSuDung, thumbnail);
				
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
		
		return thuoc;
	}
	
	
	public ArrayList<Thuoc> filterNangCao(String donViBan, String xuatXu, String dangBaoChe, String sort) {
		
		ArrayList<Thuoc> dsThuoc = new ArrayList<Thuoc>();
		
		try {
			
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "SELECT * FROM Thuoc ";
			
			donViBan = donViBan.replace("Tất cả", "");
			xuatXu = xuatXu.replace("Tất cả", "");
			Pattern pattern = Pattern.compile("^[\\p{L}\\s]+");
			Matcher matcher = pattern.matcher(xuatXu);
			if(matcher.find())
				xuatXu = matcher.group(0).trim();
			
			dangBaoChe = dangBaoChe.replace("Tất cả", "");
			
			if (!donViBan.isBlank()) {
			    sql += " WHERE donViBan = N'" + donViBan.trim() + "'";
			    if (!xuatXu.isBlank()) {
			        sql += " AND xuatXu = N'" + xuatXu.trim() + "'";
			        if (!dangBaoChe.isBlank()) 
			            sql += " AND dangBaoChe = N'" + dangBaoChe.trim() + "'";
			    } else if (!dangBaoChe.isBlank()) {
			        sql += " AND dangBaoChe = N'" + dangBaoChe.trim() + "'";
			    } else {
			        sql += " AND 1=1";
			    }
			} else if (!xuatXu.isBlank()) {
			    sql += " WHERE xuatXu = N'" + xuatXu.trim() + "'";
			    if (!dangBaoChe.isBlank()) {
			        sql += " AND dangBaoChe = N'" + dangBaoChe.trim() + "'";
			    } else {
			        sql += " AND 1=1";
			    }
			} else if (!dangBaoChe.isBlank()) {
			    sql += " WHERE dangBaoChe = N'" + dangBaoChe.trim() + "'";
			} else {
			    sql += " WHERE 1=1";
			}

			if (!sort.isBlank()) {
			    sql += sort.trim().equals("Mới-Cũ") ? " ORDER BY maThuoc DESC" : " ORDER BY maThuoc ASC";
			}
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				
				String maThuoc = rs.getString(1);
				String tenThuoc = rs.getString(2);
				String donViBanStr = rs.getString(3);
				int soLuong = rs.getInt(4);
				double donGia = rs.getDouble(5);
				String thanhPhan = rs.getString(6);
				String xuatXuStr = rs.getString(7);
				String congDung = rs.getString(8);
				String dangBaoCheStr = rs.getString(9);
				Date ngayNhapThuoc = rs.getDate(10);
				Date hanSuDung = rs.getDate(11);
				String thumbnail = rs.getString(12);
				
				Thuoc thuoc = new Thuoc(maThuoc, tenThuoc, donViBanStr, soLuong, donGia, thanhPhan, xuatXuStr, congDung, dangBaoCheStr, ngayNhapThuoc, hanSuDung, thumbnail);
				dsThuoc.add(thuoc);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dsThuoc;
		
	}
	
	public ArrayList<Thuoc> filterThuoc(String regex) {
		
		ArrayList<Thuoc> dsThuoc = new ArrayList<Thuoc>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		
		try {
			
			String sql = "SELECT TOP 50 * FROM Thuoc WHERE tenThuoc LIKE ? OR maThuoc LIKE ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, "%"+regex+"%");
			stmt.setString(2, "%"+regex+"%");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				String maThuoc = rs.getString(1);
				String tenThuoc = rs.getString(2);
				String donViBan = rs.getString(3);
				int soLuong = rs.getInt(4);
				double donGia = rs.getDouble(5);
				String thanhPhan = rs.getString(6);
				String xuatXu = rs.getString(7);
				String congDung = rs.getString(8);
				String dangBaoChe = rs.getString(9);
				Date ngayNhapThuoc = rs.getDate(10);
				Date hanSuDung = rs.getDate(11);
				String thumbnail = rs.getString(12);
				
				Thuoc thuoc = new Thuoc(maThuoc, tenThuoc, donViBan, soLuong, donGia, thanhPhan, xuatXu, congDung, dangBaoChe, ngayNhapThuoc, hanSuDung, thumbnail);
				dsThuoc.add(thuoc);
				
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
		
		return dsThuoc;
		
	}
	
	public ArrayList<Thuoc> getPagesThuoc(int pages) {
		
		int x = pages-1;
		int start = limit*x+1;
		ArrayList<Thuoc> dsthuoc = new ArrayList<Thuoc>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		
		try {
			
			String sql = "SELECT * FROM Thuoc ORDER BY maThuoc OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, start);
			stmt.setInt(2, limit);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				String maThuoc = rs.getString(1);
				String tenThuoc = rs.getString(2);
				String donViBan = rs.getString(3);
				int soLuong = rs.getInt(4);
				double donGia = rs.getDouble(5);
				String thanhPhan = rs.getString(6);
				String xuatXu = rs.getString(7);
				String congDung = rs.getString(8);
				String dangBaoChe = rs.getString(9);
				Date ngayNhapThuoc = rs.getDate(10);
				Date hanSuDung = rs.getDate(11);
				String thumbnail = rs.getString(12);
				
				Thuoc thuoc = new Thuoc(maThuoc, tenThuoc, donViBan, soLuong, donGia, thanhPhan, xuatXu, congDung, dangBaoChe, ngayNhapThuoc, hanSuDung, thumbnail);
				dsthuoc.add(thuoc);
				
			}
			
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		finally {
			try {
				stmt.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		return dsthuoc;
	}
	
	public ArrayList<String> getXuatXu() {
		
		ArrayList<String> dsthuoc = new ArrayList<String>();
		try {
			
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "SELECT xuatXu, COUNT(*) AS soLuong FROM Thuoc GROUP BY xuatXu;";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				
				String xuatXu = rs.getString(1);
				int soLuong = rs.getInt(2);
				
				String str = xuatXu + " (" + soLuong + ")";
				dsthuoc.add(str);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dsthuoc;
		
	}
	
	public String maThuocAuto() {
		String maMoi = null;
		String maHienTai = null;
		
		try {
			
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();	
			String sql = "SELECT TOP 1 maThuoc FROM Thuoc ORDER BY maThuoc DESC";
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
		
		maMoi = "SP" + kyTuMoi;
		return maMoi;
	}

}
