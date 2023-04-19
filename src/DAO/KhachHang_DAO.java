package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import connectDB.ConnectDB;
import entity.HoaDon;
import entity.KhachHang;
import entity.Thuoc;
import GUI.ChildTab;

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
	
	public KhachHang getKhachHangTheoMa(String ma) {
		KhachHang kh = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			
			String sql = "SELECT TOP 1 * FROM KhachHang WHERE maKH = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, ma);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				
				kh = new KhachHang();
				kh.setMaKhachHang(rs.getString(1));
				kh.setHoKhachHang(rs.getString(2));
				kh.setTenKhachHang(rs.getString(3));
				kh.setSoDienThoai(rs.getString(4));
				kh.setEmailKhachHang(rs.getString(5));
				kh.setGioiTinh(rs.getBoolean(6));
				
				return kh;
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

	public ArrayList<KhachHang> sortTang_Nam() {
		ArrayList<KhachHang> dskh = new ArrayList<KhachHang>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			
			String sql = "SELECT * FROM KhachHang where gioitinh = 1 ORDER BY ten";
			statement = con.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				
				String ma = rs.getString(1);
				String ho = rs.getString(2);
				String ten = rs.getString(3);
				String sdt = rs.getString(4);
				String email = rs.getString(5);
				Boolean gioitinh = rs.getBoolean(6);
				
				KhachHang kh = new KhachHang(ma, ho, ten, sdt, email, gioitinh);
				dskh.add(kh);
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
		
		return dskh;
		
	}
	
	public ArrayList<KhachHang> sortGiam_Nam() {
		ArrayList<KhachHang> dskh = new ArrayList<KhachHang>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			
			String sql = "SELECT * FROM KhachHang where gioitinh = 1 ORDER BY ten DESC";
			statement = con.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				
				String ma = rs.getString(1);
				String ho = rs.getString(2);
				String ten = rs.getString(3);
				String sdt = rs.getString(4);
				String email = rs.getString(5);
				Boolean gioitinh = rs.getBoolean(6);
				
				KhachHang kh = new KhachHang(ma, ho, ten, sdt, email, gioitinh);
				dskh.add(kh);
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
		
		return dskh;
		
	}
	
	
	public ArrayList<KhachHang> sortTang_Nu() {
		ArrayList<KhachHang> dskh = new ArrayList<KhachHang>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			
			String sql = "SELECT * FROM KhachHang where gioitinh = 0 ORDER BY ten";
			statement = con.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				
				String ma = rs.getString(1);
				String ho = rs.getString(2);
				String ten = rs.getString(3);
				String sdt = rs.getString(4);
				String email = rs.getString(5);
				Boolean gioitinh = rs.getBoolean(6);
				
				KhachHang kh = new KhachHang(ma, ho, ten, sdt, email, gioitinh);
				dskh.add(kh);
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
		
		return dskh;
		
	}
	
	public ArrayList<KhachHang> sortGiam_Nu() {
		ArrayList<KhachHang> dskh = new ArrayList<KhachHang>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			
			String sql = "SELECT * FROM KhachHang where gioitinh = 0 ORDER BY ten DESC";
			statement = con.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				
				String ma = rs.getString(1);
				String ho = rs.getString(2);
				String ten = rs.getString(3);
				String sdt = rs.getString(4);
				String email = rs.getString(5);
				Boolean gioitinh = rs.getBoolean(6);
				
				KhachHang kh = new KhachHang(ma, ho, ten, sdt, email, gioitinh);
				dskh.add(kh);
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
		
		return dskh;
		
	}
	
	public ArrayList<KhachHang> getAllKhachHang() {
			
			ArrayList<KhachHang> dskh = new ArrayList<KhachHang>();
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			PreparedStatement statement = null;
			
			try {
				String sql = "SELECT * FROM KhachHang";
				statement = con.prepareStatement(sql);
	//			statement.setString(1, sdt);
				ResultSet rs = statement.executeQuery();
				
				while (rs.next()) {
					String ma = rs.getString(1);
					String ho = rs.getString(2);
					String ten = rs.getString(3);
					String sdt = rs.getString(4);
					String email = rs.getString(5);
					Boolean gioitinh = rs.getBoolean(6);
					
					KhachHang kh = new KhachHang(ma, ho, ten, sdt, email, gioitinh);
					dskh.add(kh);
				}
				
				
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			finally {
				try {
					statement.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
			
			return dskh;
		}
	
	
		
//	public ArrayList<KhachHang> RefreshAllKhachHang() {
//		
//		ArrayList<KhachHang> dskh = new ArrayList<KhachHang>();
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement statement = null;
//		
//		try {
//			String sql = "SELECT * FROM KhachHang";
//			statement = con.prepareStatement(sql);
//			ResultSet rs = statement.executeQuery();
//			
//			while (rs.next()) {
//				String ma = rs.getString(1);
//				String ho = rs.getString(2);
//				String ten = rs.getString(3);
//				String sdt = rs.getString(4);
//				String email = rs.getString(5);
//				Boolean gioitinh = rs.getBoolean(6);
//				
//				KhachHang kh = new KhachHang(ma, ho, ten, sdt, email, gioitinh);
//				dskh.add(kh);
//				modelKH
//			}
//			
//			
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		
//		finally {
//			try {
//				statement.close();
//			} catch (SQLException e2) {
//				e2.printStackTrace();
//			}
//		}
//		
//		return dskh;
//	}
	
	public boolean insertKhachHang(KhachHang kh) {
		
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		
		try {
			
			stmt = con.prepareStatement("INSERT INTO" +" KhachHang VALUES(?, ?, ?, ?, ?, ?)");
			stmt.setString(1, kh.getMaKhachHang());
			stmt.setString(2, kh.getHoKhachHang());
			stmt.setString(3, kh.getTenKhachHang());
			stmt.setString(4, kh.getSoDienThoai());
			stmt.setString(5, kh.getEmailKhachHang());
			stmt.setBoolean(6, kh.isGioiTinh());
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

}
