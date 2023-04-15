package entity;

public class ThuocMua {
	
	private String maHoaDon;
	private String maThuoc;
	private int soLuong;
	
	public String getMaHoaDon() {
		return maHoaDon;
	}
	
	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}
	
	public String getMaThuoc() {
		return maThuoc;
	}
	
	public void setMaThuoc(String maThuoc) {
		this.maThuoc = maThuoc;
	}
	
	public int getSoLuong() {
		return soLuong;
	}
	
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public ThuocMua(String maHoaDon, String maThuoc, int soLuong) {
		super();
		this.maHoaDon = maHoaDon;
		this.maThuoc = maThuoc;
		this.soLuong = soLuong;
	}
		
}
