package entity;

public class CT_HoaDon {
	
	private HoaDon hoaDon;
	private Thuoc thuocCT;
	private int soLuong;
	
	public HoaDon getHoaDon() {
		return hoaDon;
	}
	
	public void setHoaDon(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}
	
	public Thuoc getThuocCT() {
		return thuocCT;
	}
	
	public void setThuocCT(Thuoc thuocCT) {
		this.thuocCT = thuocCT;
	}
	
	public int getSoLuong() {
		return soLuong;
	}
	
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	
	public double getDongia() {
		return this.thuocCT.getDonGia();
	}
	
	public double getTongtien() {
		return this.thuocCT.getDonGia()*this.getSoLuong();
	}
	
	public CT_HoaDon(HoaDon hoaDon, Thuoc thuocCT, int soLuong) {
		super();
		this.hoaDon = hoaDon;
		this.thuocCT = thuocCT;
		this.soLuong = soLuong;
	}
	
	
	
	
}
