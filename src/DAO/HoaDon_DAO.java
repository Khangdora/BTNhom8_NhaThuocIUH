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
	
	public int totalHoaDon1NV(String maNhanVien) {
		
		int total = 0;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		
		try {
			String sql = "SELECT COUNT(*) AS total FROM HoaDon WHERE maNhanVien = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, maNhanVien);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				total = rs.getInt(1);
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
		
		return total;
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
				int soLuong = rs.getInt(8);
				double tongGia = rs.getDouble(9);
				
				KhachHang kh = new KhachHang(maKhachHang);
				NhanVien nv = new NhanVien(maNhanVien);
				
				HoaDon hoadon = new HoaDon(maHoaDon, ngayMua, kh, nv, dangHoaDon, thanhToan, tienKhach);
				hoadon.setSoLuong(soLuong);
				hoadon.setTongTien(tongGia);
				
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

			count = stmt.executeUpdate();
			
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
		PreparedStatement stmt1 = null, stmt2 = null, stmt3 = null;
		int n1 = 0, n2 = 0, n3 = 0;
		try {
			
			stmt1 = con.prepareStatement("INSERT INTO HoaDon VALUES"
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?)");
			stmt1.setString(1, hd.getMaHoaDon());
			stmt1.setString(2, hd.getKhachHang().getMaKhachHang());
			stmt1.setString(3, hd.getNhanVien().getMaNhanVien());
			stmt1.setBoolean(4, hd.isDangHoaDon());
			stmt1.setBoolean(5, hd.getThanhToan());
			java.util.Date date = hd.getNgayMua();
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			stmt1.setDate(6, sqlDate);
			stmt1.setDouble(7, hd.getTienKhach());
			stmt1.setInt(8, hd.getSoLuong());
			stmt1.setDouble(9, hd.getTongTien());
			n1 = stmt1.executeUpdate();
			
			for(CT_HoaDon ct : cthd) {
				
				stmt2 = con.prepareStatement("INSERT INTO CT_HoaDon VALUES"
						+"(?, ?, ?)");
				stmt2.setString(1, ct.getHoaDon().getMaHoaDon());
				stmt2.setString(2, ct.getThuocCT().getMaThuoc());
				stmt2.setInt(3, ct.getSoLuong());
				n2 += stmt2.executeUpdate();
				
				stmt3 = con.prepareStatement("UPDATE Thuoc SET soLuong = soluong-? WHERE maThuoc = ?");
				stmt3.setInt(1, ct.getSoLuong());
				stmt3.setString(2, ct.getThuocCT().getMaThuoc());
				n3 += stmt3.executeUpdate();
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
		        if (stmt3 != null) {
		        	stmt3.close();
		        }
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		return (n1>0 && n2>0 && n3>0);
		
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
	
	public ArrayList<HoaDon> filterHoaDon(String regex) {
		
		ArrayList<HoaDon> dsHoadon = new ArrayList<HoaDon>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		
		if(regex.trim().equals("")) {
			dsHoadon = this.getPagesHoaDon(1);
			return dsHoadon;
		}
		
		try {
			
			String sql = "SELECT TOP 50 * FROM HoaDon h \r\n"+
				"INNER JOIN KhachHang k ON h.maKhachHang = k.maKH \r\n"+
				"WHERE h.maHoaDon LIKE ? OR k.sodienthoai LIKE ? \r\n"+
				"OR (k.ho + ' ' + k.ten) LIKE ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, "%"+regex+"%");
			stmt.setString(2, "%"+regex+"%");
			stmt.setString(3, "%"+regex+"%");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				String maHoaDon = rs.getString(1);
				String maKhachHang = rs.getString(2);
				String maNhanVien = rs.getString(3);
				boolean dangHoaDon = rs.getBoolean(4);
				boolean thanhToan = rs.getBoolean(5);
				Date ngayMua = rs.getDate(6);
				double tienKhach = rs.getDouble(7);
				
				String ho = rs.getString(9);
				String ten = rs.getString(10);
				String sdt = rs.getString(11);
				String email = rs.getString(12);
				boolean gioitinh = rs.getBoolean(13);
				
				KhachHang kh = new KhachHang(maKhachHang, ho, ten, sdt, email, gioitinh);
				NhanVien nv = new NhanVien(maNhanVien);
				
				HoaDon hd = new HoaDon(maHoaDon, ngayMua, kh, nv, dangHoaDon, thanhToan, tienKhach);
				dsHoadon.add(hd);
				
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
		
		return dsHoadon;
		
	}
	
	public ArrayList<HoaDon> filterNangCao(String keDon, String nhanVien, String sort, String AZ) {
		
		ArrayList<HoaDon> dsHoaDon = new ArrayList<HoaDon>();
		
		try {
			
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "SELECT * FROM HoaDon ";
			
			nhanVien = nhanVien.replace("Tất cả nhân viên", "");
			keDon = keDon.replace("Tất cả", "");
			
			if(!keDon.isBlank()) {
				int keDonInt = keDon.trim().equals("Kê đơn")?1:0;
				sql += " WHERE dangHoaDon = " + keDonInt + " ";
				if(!nhanVien.isBlank()) {
					sql += " AND maNhanVien = N'" + nhanVien.trim() + "' ";
				}
			}else if(!nhanVien.isBlank()) 
				sql += " WHERE maNhanVien = N'" + nhanVien.trim() + "' ";
			else 
				sql += " WHERE 1=1";
			
			
			if(sort.trim().equals("Theo thứ tự"))
				sql += " ORDER BY maHoaDon ";
			
			if(sort.trim().equals("Theo số lượng"))
				sql += " ORDER BY soLuong ";
			
			if(sort.trim().equals("Theo giá thành"))
				sql += " ORDER BY tongGia ";
			
			if(AZ.trim().equals("Sắp xếp A-Z"))
				sql += " ASC";
			
			if(AZ.trim().equals("Sắp xếp Z-A"))
				sql += " DESC";
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				String maHoaDon = rs.getString(1);
				String maKhachHang = rs.getString(2);
				String maNhanVien = rs.getString(3);
				boolean dangHoaDon = rs.getBoolean(4);
				boolean thanhToan = rs.getBoolean(5);
				Date ngayMua = rs.getDate(6);
				double tienKhach = rs.getDouble(7);
				int soLuong = rs.getInt(8);
				double tongGia = rs.getDouble(9);
				
				KhachHang kh = new KhachHang(maKhachHang);
				NhanVien nv = new NhanVien(maNhanVien);
				
				HoaDon hoadon = new HoaDon(maHoaDon, ngayMua, kh, nv, dangHoaDon, thanhToan, tienKhach);
				hoadon.setSoLuong(soLuong);
				hoadon.setTongTien(tongGia);
				
				dsHoaDon.add(hoadon);
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dsHoaDon;
		
	}
	
	public double getDoanhThuTheoThang(int month, int year) {
		int total = 0;
		
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		
		try {
			String sql = "SELECT SUM(tongGia) as total FROM HoaDon WHERE MONTH(ngayMua) = ? AND YEAR (ngayMua) = ?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, month);
			stmt.setInt(2, year);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				total = rs.getInt(1);
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
		
		return total;
	}
	

}
