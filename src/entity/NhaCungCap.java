package entity;

import java.util.Objects;

public class NhaCungCap {
	private String maNCC;
	private String tenNCC;
	private String diaChiNCC;
	private String sdtNCC;
	private String emailNCC;
	public NhaCungCap() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NhaCungCap(String maNCC) {
		this.maNCC = maNCC;
	}
	public NhaCungCap(String maNCC, String tenNCC, String diaChiNCC, String sdtNCC, String emailNCC) {
		super();
		this.maNCC = maNCC;
		this.tenNCC = tenNCC;
		this.diaChiNCC = diaChiNCC;
		this.sdtNCC = sdtNCC;
		this.emailNCC = emailNCC;
	}
	public String getMaNCC() {
		return maNCC;
	}
	public void setMaNCC(String maNCC) {
		this.maNCC = maNCC;
	}
	public String getTenNCC() {
		return tenNCC;
	}
	public void setTenNCC(String tenNCC) {
		this.tenNCC = tenNCC;
	}
	public String getDiaChiNCC() {
		return diaChiNCC;
	}
	public void setDiaChiNCC(String diaChiNCC) {
		this.diaChiNCC = diaChiNCC;
	}
	public String getSdtNCC() {
		return sdtNCC;
	}
	public void setSdtNCC(String sdtNCC) {
		this.sdtNCC = sdtNCC;
	}
	public String getEmailNCC() {
		return emailNCC;
	}
	public void setEmailNCC(String emailNCC) {
		this.emailNCC = emailNCC;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maNCC);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NhaCungCap other = (NhaCungCap) obj;
		return Objects.equals(maNCC, other.maNCC);
	}
	@Override
	public String toString() {
		return "NhaCungCap [maNCC=" + maNCC + ", tenNCC=" + tenNCC + ", diaChiNCC=" + diaChiNCC + ", sdtNCC=" + sdtNCC
				+ ", emailNCC=" + emailNCC + "]";
	}


}