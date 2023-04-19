package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.HoaDon;

public class HoaDon_DAO {
	
	int limit = 25;
	
	public int totalHoaDon() {
		int totalRows = 0;
		
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "SELECT COUNT(*) AS total FROM HoaDon";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				totalRows = rs.getInt(totalRows);				
			}
			
			rs.close();
			stmt.close();
			con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return totalRows;
		
	}
	
	public ArrayList<HoaDon> getallHoaDon() {
		
		ArrayList<HoaDon> dshoadon = new ArrayList<HoaDon>();
		try {
			
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "Select * from HoaDon";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				
				String maHoaDon = rs.getString(1);
				String maKhachHang = rs.getString(2);
				String maNhanVien = rs.getString(3);
				boolean dangHoaDon = rs.getBoolean(4);
				boolean thanhToan = rs.getBoolean(5);
				Date ngayMua = rs.getDate(6);
				
				HoaDon hd = new HoaDon(maHoaDon, ngayMua, maKhachHang, maNhanVien, dangHoaDon, thanhToan);
				dshoadon.add(hd);
				
			}
			
			rs.close();
			stmt.close();
			con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dshoadon;
	}
	
	public boolean create(HoaDon hd) {
		
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		
		try {
			
			stmt = con.prepareStatement("INSERT INTO" +
		" HoaDon VALUES(?, ?, ?, ?, ?, ?)");
			stmt.setString(1, hd.getMaHoaDon());
			stmt.setString(2, hd.getMaKhachHang());
			stmt.setString(3, hd.getMaNhanVien());
			stmt.setBoolean(4, hd.isDangHoaDon());
			stmt.setBoolean(5, hd.getThanhToan());
			stmt.setDate(6, hd.getNgayMua());
			n = stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			try {
				stmt.close();
				con.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		return n>0;
		
	}

	public String maHoaDonAuto() {
		String maMoi = null;
		String maHienTai = null;
		
		try {
			
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();	
			String sql = "SELECT TOP 1 maHoaDon FROM HoaDon ORDER BY maHoaDon DESC";
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
		
		maMoi = "HD" + kyTuMoi;
		return maMoi;
	}
	

}
