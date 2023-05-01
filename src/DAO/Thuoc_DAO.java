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
import entity.NhaCungCap;
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
	
	public boolean checkExist(int maNV) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("select * from NhanVien where maNV = ?");
			stmt.setInt(1, maNV);
			rs = stmt.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
			rs.close();
			stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean update(Thuoc thuoc) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("update Thuoc set tenThuoc = ?, donViBan = ?, soLuong = ?"
					+ ", donGia = ?, thanhPhan = ?, xuatXu = ?, congDung = ?, dangBaoChe = ?, ngayNhap = ?, hanSuDung = ?,"
					+ "maNCC = ? where maThuoc = ?");
			
			stmt.setString(1, thuoc.getTenThuoc());
			stmt.setString(2, thuoc.getDonViBan());
			stmt.setInt(3, thuoc.getSoLuong());
			stmt.setDouble(4, thuoc.getDonGia());
			stmt.setString(5, thuoc.getThanhPhan());
			stmt.setString(6, thuoc.getXuatXu());
			stmt.setString(7, thuoc.getCongDung());
			stmt.setString(8, thuoc.getDangBaoChe());
			java.sql.Date sqlDateNgayNhap = new java.sql.Date(thuoc.getNgayNhapThuoc().getTime());
			stmt.setDate(9, sqlDateNgayNhap);
			java.sql.Date sqlDateHSD = new java.sql.Date(thuoc.getHanSuDung().getTime());
			stmt.setDate(10, sqlDateHSD);
			stmt.setString(11, thuoc.getNcc().getMaNCC());
			stmt.setString(12, thuoc.getMaThuoc());
//			stmt.setString(12, thuoc.getThumbnail());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return n > 0;
	}
	
	public boolean delete(Thuoc thuoc) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("delete from Thuoc where maThuoc = ?");
			stmt.setString(1, thuoc.getMaThuoc());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return n > 0;
	}
	
	public boolean create(Thuoc thuoc) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("insert into Thuoc values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, (SELECT maNCC FROM NhaCungCap WHERE tenNCC = ?))");
			stmt.setString(1, thuoc.getMaThuoc());
			stmt.setString(2, thuoc.getTenThuoc());
			stmt.setString(3, thuoc.getDonViBan());
			stmt.setInt(4, thuoc.getSoLuong());
			stmt.setDouble(5, thuoc.getDonGia());
			stmt.setString(6, thuoc.getThanhPhan());
			stmt.setString(7, thuoc.getXuatXu());
			stmt.setString(8, thuoc.getCongDung());
			stmt.setString(9, thuoc.getDangBaoChe());
			java.sql.Date sqlDateNgayNhap = new java.sql.Date(thuoc.getNgayNhapThuoc().getTime());
			stmt.setDate(10, sqlDateNgayNhap);
			java.sql.Date sqlDateHSD = new java.sql.Date(thuoc.getHanSuDung().getTime());
			stmt.setDate(11, sqlDateHSD);
			stmt.setString(12, "");
			stmt.setString(13, thuoc.getNcc().getTenNCC());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return n > 0;
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
			
			String sql = "SELECT t.*, n.* \r\n"
					+ "FROM Thuoc t \r\n"
					+ "INNER JOIN NhaCungCap n ON t.maNCC = n.maNCC\r\n"
					+ "WHERE t.maThuoc = ?";
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
				String maNCC = rs.getString(14);
				String tenNCC = rs.getString(15);
				String diaChi = rs.getString(16);
				String sdt = rs.getString(17);
				String email = rs.getString(18);
				NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC, diaChi, sdt, email);
				
				thuoc = new Thuoc(maThuocStr, tenThuoc, donViBan, soLuong, donGia, thanhPhan, xuatXu, congDung, dangBaoChe, ngayNhapThuoc, hanSuDung, thumbnail, ncc);
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
		int start = limit*x;
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
			if (rs.next()) 
				maHienTai = "SP1000";
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String kyTuCuoi = maHienTai.replaceAll("[^0-9]+", "");
		String kyTuMoi = Integer.toString(Integer.parseInt(kyTuCuoi) + 1);
		
		maMoi = "SP" + kyTuMoi;
		return maMoi;
	}
}
