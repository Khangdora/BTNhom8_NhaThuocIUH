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
import entity.Thuoc;

public class CTHoaDon_DAO {
	
		public ArrayList<CT_HoaDon> getAllCTHoaDon(String maHoaDon) {
			
			ArrayList<CT_HoaDon> dsHoaDon = new ArrayList<CT_HoaDon>();
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			PreparedStatement stmt = null;
			
			try {
				
				String sql = "SELECT * FROM CT_HoaDon WHERE maHoaDon = ?";
				stmt = con.prepareStatement(sql);
				stmt.setString(1, maHoaDon);
				
				ResultSet rs = stmt.executeQuery();
				
				while (rs.next()) {
					
					String maThuoc = rs.getString(2);
					int soLuong = rs.getInt(3);
					
					Thuoc thuoc = new Thuoc(maThuoc);
					HoaDon hoadon = new HoaDon(maHoaDon);
					
					CT_HoaDon ct = new CT_HoaDon(hoadon, thuoc, soLuong);
					dsHoaDon.add(ct);
					
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return dsHoaDon;
		}
	
	
		public int totalSoLuong(String maHoaDon) {
			int totalRows = 0;
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			PreparedStatement stmt = null;
			
			try {
				
				String sql = "SELECT COUNT(*) AS total FROM CT_HoaDon WHERE maHoaDon = ?";
				stmt = con.prepareStatement(sql);
				stmt.setString(1, maHoaDon);
				ResultSet rs = stmt.executeQuery();
				
				while(rs.next()) { 
					totalRows = rs.getInt(1);
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

			return totalRows;
				
		}

}
