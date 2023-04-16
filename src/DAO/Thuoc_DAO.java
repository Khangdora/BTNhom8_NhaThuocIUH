package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

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
			
			rs.close();
			stmt.close();
			con.close();
			
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
	
	
	public ArrayList<Thuoc> getPagesThuoc(int pages) {
		
		int x = pages-1;
		int start = limit*x+1;
		ArrayList<Thuoc> dsthuoc = new ArrayList<Thuoc>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		
		try {
			
			String sql = "SELECT * FROM Thuoc LIMIT ? OFFSET ?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, limit);
			stmt.setInt(2, start);
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
				
				String str = xuatXu + "(" + soLuong + ")";
				dsthuoc.add(str);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dsthuoc;
		
	}
	

}
