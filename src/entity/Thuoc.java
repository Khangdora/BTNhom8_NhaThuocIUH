package entity;

import java.sql.Date;
import java.util.Objects;

public class Thuoc {
	
	private String maThuoc;
	private String tenThuoc;
	private String donViBan;
	private int soLuong;
	private double donGia;
	private String thanhPhan;
	private String xuatXu;
	private String congDung;
	private String dangBaoChe;
	private Date ngayNhapThuoc;
	private Date hanSuDung;
	
	public String getMaThuoc() {
		return maThuoc;
	}
	
	public void setMaThuoc(String maThuoc) {
		this.maThuoc = maThuoc;
	}
	
	public String getTenThuoc() {
		return tenThuoc;
	}
	
	public void setTenThuoc(String tenThuoc) {
		this.tenThuoc = tenThuoc;
	}
	
	public String getDonViBan() {
		return donViBan;
	}
	
	public void setDonViBan(String donViBan) {
		this.donViBan = donViBan;
	}
	
	public int getSoLuong() {
		return soLuong;
	}
	
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	
	public double getDonGia() {
		return donGia;
	}
	
	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}
	
	public String getThanhPhan() {
		return thanhPhan;
	}
	
	public void setThanhPhan(String thanhPhan) {
		this.thanhPhan = thanhPhan;
	}
	
	public String getXuatXu() {
		return xuatXu;
	}
	
	public void setXuatXu(String xuatXu) {
		this.xuatXu = xuatXu;
	}
	
	public String getCongDung() {
		return congDung;
	}
	
	public void setCongDung(String congDung) {
		this.congDung = congDung;
	}
	
	public String getDangBaoChe() {
		return dangBaoChe;
	}
	
	public void setDangBaoChe(String dangBaoChe) {
		this.dangBaoChe = dangBaoChe;
	}
	
	public Date getNgayNhapThuoc() {
		return ngayNhapThuoc;
	}
	
	public void setNgayNhapThuoc(Date ngayNhapThuoc) {
		this.ngayNhapThuoc = ngayNhapThuoc;
	}
	
	public Date getHanSuDung() {
		return hanSuDung;
	}
	
	public void setHanSuDung(Date hanSuDung) {
		this.hanSuDung = hanSuDung;
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(maThuoc);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Thuoc other = (Thuoc) obj;
		return Objects.equals(maThuoc, other.maThuoc);
	}

	public Thuoc(String maThuoc, String tenThuoc, String donViBan, int soLuong, double donGia, String thanhPhan,
			String xuatXu, String congDung, String dangBaoChe, Date ngayNhapThuoc, Date hanSuDung) {
		super();
		this.maThuoc = maThuoc;
		this.tenThuoc = tenThuoc;
		this.donViBan = donViBan;
		this.soLuong = soLuong;
		this.donGia = donGia;
		this.thanhPhan = thanhPhan;
		this.xuatXu = xuatXu;
		this.congDung = congDung;
		this.dangBaoChe = dangBaoChe;
		this.ngayNhapThuoc = ngayNhapThuoc;
		this.hanSuDung = hanSuDung;
	}

	public Thuoc(String maThuoc) {
		super();
		this.maThuoc = maThuoc;
	}

	public Thuoc() {
		super();
	}

	@Override
	public String toString() {
		return "thuoc [maThuoc=" + maThuoc + ", tenThuoc=" + tenThuoc + ", donViBan=" + donViBan + ", soLuong="
				+ soLuong + ", donGia=" + donGia + ", thanhPhan=" + thanhPhan + ", xuatXu=" + xuatXu + ", congDung="
				+ congDung + ", dangBaoChe=" + dangBaoChe + ", ngayNhapThuoc=" + ngayNhapThuoc + ", hanSuDung="
				+ hanSuDung + "]";
	}

}
