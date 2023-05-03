package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.NhaCungCap;
import entity.Thuoc;

public class NhaCungCap_DAO {
	public NhaCungCap_DAO() {

	}

	public ArrayList<NhaCungCap> getallNCC(){
		ArrayList<NhaCungCap> dsNCC = new ArrayList<NhaCungCap>();

		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from NhaCungCap";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()) {
				String maNCC = rs.getString(1);
				String tenNCC = rs.getString(2);
				String diaChi = rs.getString(3);
				String SDT = rs.getString(4);
				String email = rs.getString(5);

				NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC, diaChi, SDT, email);
				dsNCC.add(ncc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dsNCC;
	}

	public boolean create(NhaCungCap ncc) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("insert into NhaCungCap values(? , ? , ? , ? ,?)");
			stmt.setString(1, ncc.getMaNCC());
			stmt.setString(2, ncc.getTenNCC());
			stmt.setString(3, ncc.getDiaChiNCC());
			stmt.setString(4, ncc.getSdtNCC());
			stmt.setString(5, ncc.getEmailNCC());
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

	public boolean delete(NhaCungCap ncc) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("delete from NhaCungCap where maNCC = ?");
			stmt.setString(1, ncc.getMaNCC());
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

	public ArrayList<String> getTenNCC(){
		ArrayList<String> dsTenNCC = new ArrayList<String>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select tenNCC from NhaCungCap";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()) {
				String tenNCC= rs.getString(1);

				dsTenNCC.add(tenNCC);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsTenNCC;
	}

	public NhaCungCap getNCCTheoMaNCC(String maNCC) {
		NhaCungCap ncc = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement("select * from NhaCungCap where maNCC = ?");
			stmt.setString(1, maNCC);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String tenNCC = rs.getString(2);
				String diaChi = rs.getString(3);
				String SDT = rs.getString(4);
				String email = rs.getString(5);

				ncc = new NhaCungCap(maNCC, tenNCC, diaChi, SDT, email);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


		return ncc;
	}

	public NhaCungCap getNCCTheoTenNCC(String tenNCC) {
		NhaCungCap ncc = null;
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement("select * from NhaCungCap where tenNCC = ?");
			stmt.setString(1, tenNCC);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String maNCC = rs.getString(1);
				String diaChi = rs.getString(3);
				String SDT = rs.getString(4);
				String email = rs.getString(5);

				ncc = new NhaCungCap(maNCC, tenNCC, diaChi, SDT, email);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ncc;
	}

	public boolean update(NhaCungCap ncc) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;

		try {
			stmt = con.prepareStatement("update NhaCungCap set tenNCC = ? , diaChi = ? , soDienThoai = ?, email = ? where maNCC = ?");
			stmt.setString(1, ncc.getTenNCC());
			stmt.setString(2, ncc.getDiaChiNCC());
			stmt.setString(3, ncc.getSdtNCC());
			stmt.setString(4, ncc.getEmailNCC());
			stmt.setString(5, ncc.getMaNCC());
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

	public String maNCCAuto() {
		String maMoi = null;
		String maHienTai = null;

		try {

			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();	
			String sql = "SELECT TOP 1 maNCC FROM NhaCungCap ORDER BY maNCC DESC";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				maHienTai = rs.getString(1);
			}
			if (rs.next()) 
				maHienTai = "NCC1000";

		} catch (SQLException e) {
			e.printStackTrace();
		}

		String kyTuCuoi = maHienTai.replaceAll("[^0-9]+", "");
		String kyTuMoi = Integer.toString(Integer.parseInt(kyTuCuoi) + 1);

		maMoi = "NCC" + kyTuMoi;
		return maMoi;
	}
}