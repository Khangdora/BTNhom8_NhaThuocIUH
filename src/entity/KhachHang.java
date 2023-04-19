package entity;

import java.util.Objects;

public class KhachHang {
	
	private String maKhachHang;
	private String hoKhachHang;
	private String tenKhachHang;
	private String soDienThoai;
	private String emailKhachHang;
	private boolean gioiTinh;
	
	public String getMaKhachHang() {
		return maKhachHang;
	}

	public void setMaKhachHang(String maKhachHang) {
		this.maKhachHang = maKhachHang;
	}

	public String getHoKhachHang() {
		return hoKhachHang;
	}

	public void setHoKhachHang(String hoKhachHang) {
		this.hoKhachHang = hoKhachHang;
	}

	public String getTenKhachHang() {
		return tenKhachHang;
	}

	public void setTenKhachHang(String tenKhachHang) {
		this.tenKhachHang = tenKhachHang;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public String getEmailKhachHang() {
		return emailKhachHang;
	}

	public void setEmailKhachHang(String emailKhachHang) {
		this.emailKhachHang = emailKhachHang;
	}

	public boolean isGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maKhachHang);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KhachHang other = (KhachHang) obj;
		return Objects.equals(maKhachHang, other.maKhachHang);
	}

	public KhachHang(String maKhachHang, String hoKhachHang, String tenKhachHang, String soDienThoai,
			String emailKhachHang, boolean gioiTinh) {
		super();
		this.maKhachHang = maKhachHang;
		this.hoKhachHang = hoKhachHang;
		this.tenKhachHang = tenKhachHang;
		this.soDienThoai = soDienThoai;
		this.emailKhachHang = emailKhachHang;
		this.gioiTinh = gioiTinh;
	}

	public KhachHang(String maKhachHang) {
		super();
		this.maKhachHang = maKhachHang;
	}

	public KhachHang() {
		super();
	}
	
	public Object[] getObjectNV() {
		String phai="";
		if(isGioiTinh()) {
			phai = "Nam";
		}else {
			phai = "Nữ";
		}
		Object[] obj = {getMaKhachHang(), getHoKhachHang(), getTenKhachHang(), getSoDienThoai(), getEmailKhachHang(), phai};
		return obj;
	}
	
	@Override
	public String toString() {
		return "khachhang [maKhachHang=" + maKhachHang + ", hoKhachHang=" + hoKhachHang + ", tenKhachHang="
				+ tenKhachHang + ", soDienThoai=" + soDienThoai + ", emailKhachHang=" + emailKhachHang + ", gioiTinh="
				+ gioiTinh + "]";
	}
	
}
