package entity;

import java.util.Date;

public class HoaDon {
	
	private String maHoaDon;
	private Date ngayMua;
	private KhachHang khachHang;
	private NhanVien nhanVien;
	private boolean dangHoaDon;
	private boolean thanhToan;
	private double tienKhach;
	
	public double getTienKhach() {
		return tienKhach;
	}
	
	public void setTienKhach(double tienKhach) {
		this.tienKhach = tienKhach;
	}
	
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
	public KhachHang getKhachHang() {
		return khachHang;
	}
	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}
	public NhanVien getNhanVien() {
		return nhanVien;
	}
	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
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

	public HoaDon(String maHoaDon, Date ngayMua, KhachHang khachHang, NhanVien nhanVien, boolean dangHoaDon,
			boolean thanhToan, double tienKhach) {
		super();
		this.maHoaDon = maHoaDon;
		this.ngayMua = ngayMua;
		this.khachHang = khachHang;
		this.nhanVien = nhanVien;
		this.dangHoaDon = dangHoaDon;
		this.thanhToan = thanhToan;
		this.tienKhach = tienKhach;
	}

	public HoaDon(String maHoaDon) {
		super();
		this.maHoaDon = maHoaDon;
	}
	
	public HoaDon() {
		super();
	}
	
}
