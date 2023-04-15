package entity;

import java.util.Objects;

public class CaTruc {
	
	private String maCaTruc;
	private String tenCaTruc;
	private String ghiChu;
	
	public String getMaCaTruc() {
		return maCaTruc;
	}
	
	public void setMaCaTruc(String maCaTruc) {
		this.maCaTruc = maCaTruc;
	}
	
	public String getTenCaTruc() {
		return tenCaTruc;
	}
	
	public void setTenCaTruc(String tenCaTruc) {
		this.tenCaTruc = tenCaTruc;
	}
	
	public String getGhiChu() {
		return ghiChu;
	}
	
	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ghiChu, maCaTruc, tenCaTruc);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CaTruc other = (CaTruc) obj;
		return Objects.equals(ghiChu, other.ghiChu) && Objects.equals(maCaTruc, other.maCaTruc)
				&& Objects.equals(tenCaTruc, other.tenCaTruc);
	}

	public CaTruc(String maCaTruc, String tenCaTruc, String ghiChu) {
		super();
		this.maCaTruc = maCaTruc;
		this.tenCaTruc = tenCaTruc;
		this.ghiChu = ghiChu;
	}

	public CaTruc(String maCaTruc) {
		super();
		this.maCaTruc = maCaTruc;
	}

	public CaTruc() {
		super();
	}
	
}
