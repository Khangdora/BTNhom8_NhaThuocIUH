package DAO;

import java.sql.Connection;
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

}
