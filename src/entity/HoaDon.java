package entity;

import java.sql.Date;
import java.util.ArrayList;

public class HoaDon {
	
	private String maHoaDon;
	private Date ngayMua;
	private String maKhachHang;
	private String maNhanVien;
	private boolean dangHoaDon;
	private boolean thanhToan;
	
	public String getMaHoaDon() {
		return maHoaDon;
	}
	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}
	public Date getNgayMua() {
		return ngayMua;
	}
	public void setNgayMua(Date ngayMua) {
		this.ngayMua = ngayMua;
	}
	public String getMaKhachHang() {
		return maKhachHang;
	}
	public void setMaKhachHang(String maKhachHang) {
		this.maKhachHang = maKhachHang;
	}
	public String getMaNhanVien() {
		return maNhanVien;
	}
	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}
	public boolean isDangHoaDon() {
		return dangHoaDon;
	}
	public void setDangHoaDon(boolean dangHoaDon) {
		this.dangHoaDon = dangHoaDon;
	}
	public boolean getThanhToan() {
		return thanhToan;
	}
	public void setThanhToan(boolean thanhToan) {
		this.thanhToan = thanhToan;
	}
	
	public HoaDon(String maHoaDon, Date ngayMua, String maKhachHang, String maNhanVien, boolean dangHoaDon,
			boolean thanhToan) {
		super();
		this.maHoaDon = maHoaDon;
		this.ngayMua = ngayMua;
		this.maKhachHang = maKhachHang;
		this.maNhanVien = maNhanVien;
		this.dangHoaDon = dangHoaDon;
		this.thanhToan = thanhToan;
	}
	
	public HoaDon(String maHoaDon) {
		super();
		this.maHoaDon = maHoaDon;
	}
	
	public HoaDon() {
		super();
	}
	
}
