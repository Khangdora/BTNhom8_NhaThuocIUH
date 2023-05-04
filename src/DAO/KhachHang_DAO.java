package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import connectDB.ConnectDB;
import entity.HoaDon;
import entity.KhachHang;
import entity.Thuoc;
import GUI.ChildTab;

public class KhachHang_DAO {

	public int totalKhachHang() {
		int totalRows = 0;
		
		try {
			
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "SELECT COUNT(*) AS total FROM KhachHang";
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
	
	public KhachHang getKhachHangTheoMaKH(String mkh) {
		KhachHang kh = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			
			String sql = "SELECT TOP 1 * FROM KhachHang WHERE maKH = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, mkh);
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
	
	
	public ArrayList<KhachHang> filterTuKhoaKH_Tang_Nam(String regex) {
		
		ArrayList<KhachHang> dsKh = new ArrayList<KhachHang>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		
		try {
			String sql = "SELECT * FROM KhachHang WHERE maKH LIKE ? OR sodienthoai LIKE ? OR ho LIKE ? and gioitinh = 1 Order by ten";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, "%"+regex+"%"+"%");
			stmt.setString(2, "%"+regex+"%"+"%");
			stmt.setString(3, "%"+regex+"%"+"%");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				String ma = rs.getString(1);
				String ho = rs.getString(2);
				String ten = rs.getString(3);
				String sdt = rs.getString(4);
				String email = rs.getString(5);
				Boolean gioitinh = rs.getBoolean(6);
				
				KhachHang kh = new KhachHang(ma, ho, ten, sdt, email, gioitinh);
				dsKh.add(kh);
				
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
		
		return dsKh;
		
	}
	
public ArrayList<KhachHang> filterTuKhoaKH_Giam_Nam(String regex) {
		
		ArrayList<KhachHang> dsKh = new ArrayList<KhachHang>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		
		try {
			
			String sql = "SELECT * FROM KhachHang WHERE maKH LIKE ? OR sodienthoai LIKE ? OR ho LIKE ? and gioitinh = 1 Order by ten DESC";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, "%"+regex+"%"+"%");
			stmt.setString(2, "%"+regex+"%"+"%");
			stmt.setString(3, "%"+regex+"%"+"%");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				String ma = rs.getString(1);
				String ho = rs.getString(2);
				String ten = rs.getString(3);
				String sdt = rs.getString(4);
				String email = rs.getString(5);
				Boolean gioitinh = rs.getBoolean(6);
				
				KhachHang kh = new KhachHang(ma, ho, ten, sdt, email, gioitinh);
				dsKh.add(kh);
				
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
		
		return dsKh;
		
	}

public ArrayList<KhachHang> filterTuKhoaKH_Giam_Nu(String regex) {
	
	ArrayList<KhachHang> dsKh = new ArrayList<KhachHang>();
	ConnectDB.getInstance();
	Connection con = ConnectDB.getConnection();
	PreparedStatement stmt = null;
	
	try {
		
		String sql = "SELECT * FROM KhachHang WHERE maKH LIKE ? OR sodienthoai LIKE ? OR ho LIKE ? and gioitinh = 0 Order by ten DESC";
		stmt = con.prepareStatement(sql);
		stmt.setString(1, "%"+regex+"%"+"%");
		stmt.setString(2, "%"+regex+"%"+"%");
		stmt.setString(3, "%"+regex+"%"+"%");
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			
			String ma = rs.getString(1);
			String ho = rs.getString(2);
			String ten = rs.getString(3);
			String sdt = rs.getString(4);
			String email = rs.getString(5);
			Boolean gioitinh = rs.getBoolean(6);
			
			KhachHang kh = new KhachHang(ma, ho, ten, sdt, email, gioitinh);
			dsKh.add(kh);
			
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
	
	return dsKh;
	
	}
	
	public ArrayList<KhachHang> filterTuKhoaKH_Tang_Nu(String regex) {
		
		ArrayList<KhachHang> dsKh = new ArrayList<KhachHang>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		
		try {
			
			String sql = "SELECT * FROM KhachHang WHERE maKH LIKE ? OR sodienthoai LIKE ? OR ho LIKE ? and gioitinh = 0 Order by ten";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, "%"+regex+"%"+"%");
			stmt.setString(2, "%"+regex+"%"+"%");
			stmt.setString(3, "%"+regex+"%"+"%");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				String ma = rs.getString(1);
				String ho = rs.getString(2);
				String ten = rs.getString(3);
				String sdt = rs.getString(4);
				String email = rs.getString(5);
				Boolean gioitinh = rs.getBoolean(6);
				
				KhachHang kh = new KhachHang(ma, ho, ten, sdt, email, gioitinh);
				dsKh.add(kh);
				
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
		
		return dsKh;
		
	}
	
	public String maKHAuto() {
		String maMoi = null;
		String maHienTai = null;
		
		try {
			
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();	
			String sql = "SELECT TOP 1 maKH FROM KhachHang ORDER BY maKH DESC";
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
		
		maMoi = "KH" + kyTuMoi;
		return maMoi;
	}
	
public boolean XoaKhachHang(String ma) {
		
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		
		try {
			stmt = con.prepareStatement("DELETE  FROM KhachHang WHERE maKH = ?");
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

	public boolean updateKhachHang(KhachHang kh) {
		
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		
		try {
			stmt = con.prepareStatement("UPDATE KhachHang SET ho = ?, ten = ?, sodienthoai = ?, email = ?, gioitinh = ? WHERE maKH = ?");
			stmt.setString(1, kh.getHoKhachHang());
			stmt.setString(2, kh.getTenKhachHang());
			stmt.setString(3, kh.getSoDienThoai());
			stmt.setString(4, kh.getEmailKhachHang());
			stmt.setBoolean(5, kh.isGioiTinh());
			stmt.setString(6, kh.getMaKhachHang());
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
	
	public String[][] dsKhachHangtheoLuotMua() {		
		String[][] dsKhachHangs = new String[20][2];
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		
		try {
			String sql = "SELECT TOP 20 h.maKhachHang, k.ho, k.ten, COUNT(*) as total \r\n"
					+ "FROM HoaDon h INNER JOIN KhachHang k \r\n"
					+ "ON h.maKhachHang = k.maKH GROUP BY h.maKhachHang, k.ho, k.ten";
			statement = con.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			int i = 0;
			
			while (rs.next()) {
				String ma = rs.getString(1);
				String total = rs.getString(4);
				
				dsKhachHangs[i][0] = ma;
				dsKhachHangs[i][1] = total;
				
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
		
		return dsKhachHangs;
		
	}
	
}