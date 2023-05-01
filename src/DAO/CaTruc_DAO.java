package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.CaTruc;

public class CaTruc_DAO {
	
	public ArrayList<CaTruc> getAllCaTruc() {
		
		ArrayList<CaTruc> dsCaTruc = new ArrayList<CaTruc>();
		
		try {
			
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "SELECT * FROM CaTruc";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				
				String maCaTruc = rs.getString(1);
				String tenCaTruc = rs.getString(2);
				String ghiChu = rs.getString(3);
				
				CaTruc caTruc = new CaTruc(maCaTruc, tenCaTruc, ghiChu);
				dsCaTruc.add(caTruc);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dsCaTruc;
			
	}
	
	public CaTruc getCaTrucTheoMaCT(String maCT) {
		CaTruc ct = new CaTruc();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		
		try {
			
			String sql = "Select * from CaTruc where macatruc = ?";
			statement=con.prepareStatement(sql);
			statement.setString(1, maCT);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				ct.setMaCaTruc(rs.getString(1));
				ct.setTenCaTruc(rs.getString(2));
				ct.setGhiChu(rs.getString(3));
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
		
		return ct;
		
	}
	
}
