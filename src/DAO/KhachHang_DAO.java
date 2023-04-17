package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connectDB.ConnectDB;
import entity.KhachHang;

public class KhachHang_DAO {
	
	public KhachHang getKhachHangTheoSDT(String sdt) {
		KhachHang kh = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			
			String sql = "SELECT TOP 1 * FROM KhachHang WHERE sodienthoai = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, sdt);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				
				kh = new KhachHang();
				kh.setMaKhachHang(rs.getString(1));
				kh.setHoKhachHang(rs.getString(2));
				kh.setTenKhachHang(rs.getString(3));
				kh.setSoDienThoai(rs.getString(4));
				kh.setEmailKhachHang(rs.getString(5));
				kh.setGioiTinh(rs.getBoolean(6));
				
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
		
		return kh;
		
	}

}
