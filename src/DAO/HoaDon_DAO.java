package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.CT_HoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;

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
				totalRows = rs.getInt(1);				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return totalRows;
		
	}
	
	public ArrayList<HoaDon> getPagesHoaDon(int pages) {
		
		int x = pages-1;
		int start = limit*x;
		ArrayList<HoaDon> dshoadon = new ArrayList<HoaDon>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		
		try {
			
			String sql = "SELECT * FROM HoaDon ORDER BY maHoaDon OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, start);
			stmt.setInt(2, limit);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				String maHoaDon = rs.getString(1);
				String maKhachHang = rs.getString(2);
				String maNhanVien = rs.getString(3);
				boolean dangHoaDon = rs.getBoolean(4);
				boolean thanhToan = rs.getBoolean(5);
				Date ngayMua = rs.getDate(6);
				double tienKhach = rs.getDouble(7);
				
				KhachHang kh = new KhachHang(maKhachHang);
				NhanVien nv = new NhanVien(maNhanVien);
				
				HoaDon hoadon = new HoaDon(maHoaDon, ngayMua, kh, nv, dangHoaDon, thanhToan, tienKhach);
				dshoadon.add(hoadon);
				
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
		
		return dshoadon;
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
				double tienKhach = rs.getDouble(7);
				
				HoaDon hd = new HoaDon(maHoaDon, ngayMua, new KhachHang(maKhachHang), new NhanVien(maNhanVien), dangHoaDon, thanhToan, tienKhach);
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
	
	public boolean kiemTraTonTai(String maHoaDon) {
		
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		int count = 0;
		try {
			
			String sql = "SELECT * FROM HoaDon WHERE maHoaDon = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, maHoaDon);
			
			boolean result = statement.execute();
			
			if(result) {
				
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
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		return count>0;		
	}
	
	
	public boolean updateThanhToan(HoaDon hd) {
		
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int count = 0;
		try {
			
			String sql = "UPDATE HoaDon SET thanhToan = ?, tienKhach = ? WHERE maHoaDon = ?";
			stmt = con.prepareStatement(sql);
			stmt.setBoolean(1, hd.getThanhToan());
			stmt.setDouble(2, hd.getTienKhach());
			stmt.setString(3, hd.getMaHoaDon());
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				count++;
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			try {
				stmt.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		return count>0;
		
	}
	
	public boolean create(HoaDon hd, ArrayList<CT_HoaDon> cthd) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt1 = null, stmt2 = null;
		int n1 = 0, n2 = 0;
		try {
			
			stmt1 = con.prepareStatement("INSERT INTO HoaDon VALUES"
					+ "(?, ?, ?, ?, ?, ?, ?)");
			stmt1.setString(1, hd.getMaHoaDon());
			stmt1.setString(2, hd.getKhachHang().getMaKhachHang());
			stmt1.setString(3, hd.getNhanVien().getMaNhanVien());
			stmt1.setBoolean(4, hd.isDangHoaDon());
			stmt1.setBoolean(5, hd.getThanhToan());
			java.util.Date date = hd.getNgayMua();
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			stmt1.setDate(6, sqlDate);
			stmt1.setDouble(7, hd.getTienKhach());
			n1 = stmt1.executeUpdate();
			
			for(CT_HoaDon ct : cthd) {
				
				stmt2 = con.prepareStatement("INSERT INTO CT_HoaDon VALUES"
						+"(?, ?, ?)");
				stmt2.setString(1, ct.getHoaDon().getMaHoaDon());
				stmt2.setString(2, ct.getThuocCT().getMaThuoc());
				stmt2.setInt(3, ct.getSoLuong());
				n2 += stmt2.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			try {
		        if (stmt1 != null) {
		            stmt1.close();
		        }
		        if (stmt2 != null) {
		            stmt2.close();
		        }
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		return (n1>0 && n2>0);
		
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
			
			if (rs.next()) {
		        maHienTai = rs.getString(1);
		    } else {
		        maHienTai = "HD1000"; // hoặc giá trị khác tùy ý
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
