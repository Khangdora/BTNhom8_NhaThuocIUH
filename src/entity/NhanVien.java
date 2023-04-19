package entity;

import java.util.Objects;

public class NhanVien {
	
	private String maNhanVien;
	private String tenNhanVien;
	private String hoNhanVien;
	private String soDienThoai;
	private String emailNhanVien;
	private CaTruc caTruc;
	private boolean gioiTinh;
	private String chucVu;
	private double tienLuong; 
	private String matKhau;
	
	public String getMaNhanVien() {
		return maNhanVien;
	}
	
	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}
	
	public String getTenNhanVien() {
		return tenNhanVien;
	}
	
	public void setTenNhanVien(String tenNhanVien) {
		this.tenNhanVien = tenNhanVien;
	}
	
	public String getHoNhanVien() {
		return hoNhanVien;
	}
	
	public void setHoNhanVien(String hoNhanVien) {
		this.hoNhanVien = hoNhanVien;
	}
	
	public String getSoDienThoai() {
		return soDienThoai;
	}
	
	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}
	
	public String getEmailNhanVien() {
		return emailNhanVien;
	}
	
	public void setEmailNhanVien(String emailNhanVien) {
		this.emailNhanVien = emailNhanVien;
	}
	
	public CaTruc getCaTruc() {
		return caTruc;
	}
	
	public void setCaTruc(CaTruc caTruc) {
		this.caTruc = caTruc;
	}
	
	public boolean isGioiTinh() {
		return gioiTinh;
	}
	
	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	
	public String getChucVu() {
		return chucVu;
	}
	
	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public double getTienLuong() {
		return tienLuong;
	}

	public void setTienLuong(double tienLuong) {
		this.tienLuong = tienLuong;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maNhanVien);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NhanVien other = (NhanVien) obj;
		return Objects.equals(maNhanVien, other.maNhanVien);
	}

	public NhanVien(String maNhanVien,  String hoNhanVien, String tenNhanVien, String soDienThoai, String emailNhanVien,
			CaTruc caTruc, boolean gioiTinh, String chucVu, double tienLuong, String matKhau) {
		super();
		this.maNhanVien = maNhanVien;
		this.hoNhanVien = hoNhanVien;
		this.tenNhanVien = tenNhanVien;
		this.soDienThoai = soDienThoai;
		this.emailNhanVien = emailNhanVien;
		this.caTruc = caTruc;
		this.gioiTinh = gioiTinh;
		this.chucVu = chucVu;
		this.tienLuong = tienLuong;
		this.matKhau = matKhau;
	}

	public NhanVien(String maNhanVien) {
		super();
		this.maNhanVien = maNhanVien;
	}

	public NhanVien() {
		super();
	}

}
