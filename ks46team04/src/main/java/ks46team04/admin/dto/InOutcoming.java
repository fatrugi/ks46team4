package ks46team04.admin.dto;

public class InOutcoming {
	
	private String inOutcomingCode;
	private String goodsCode;
	private String inOutcomingQuantity;
	private String inOutcomingType;
	private String inOutcomingDate;
	private String inOutcomingRegId;
	private String inOutcomingRegDate;
	private String inOutcomingUpdateId;
	private String inOutcomingUpdateDate;
	public String getInOutcomingCode() {
		return inOutcomingCode;
	}
	public void setInOutcomingCode(String inOutcomingCode) {
		this.inOutcomingCode = inOutcomingCode;
	}
	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public String getInOutcomingQuantity() {
		return inOutcomingQuantity;
	}
	public void setInOutcomingQuantity(String inOutcomingQuantity) {
		this.inOutcomingQuantity = inOutcomingQuantity;
	}
	public String getInOutcomingType() {
		return inOutcomingType;
	}
	public void setInOutcomingType(String inOutcomingType) {
		this.inOutcomingType = inOutcomingType;
	}
	public String getInOutcomingDate() {
		return inOutcomingDate;
	}
	public void setInOutcomingDate(String inOutcomingDate) {
		this.inOutcomingDate = inOutcomingDate;
	}
	public String getInOutcomingRegId() {
		return inOutcomingRegId;
	}
	public void setInOutcomingRegId(String inOutcomingRegId) {
		this.inOutcomingRegId = inOutcomingRegId;
	}
	public String getInOutcomingRegDate() {
		return inOutcomingRegDate;
	}
	public void setInOutcomingRegDate(String inOutcomingRegDate) {
		this.inOutcomingRegDate = inOutcomingRegDate;
	}
	public String getInOutcomingUpdateId() {
		return inOutcomingUpdateId;
	}
	public void setInOutcomingUpdateId(String inOutcomingUpdateId) {
		this.inOutcomingUpdateId = inOutcomingUpdateId;
	}
	public String getInOutcomingUpdateDate() {
		return inOutcomingUpdateDate;
	}
	public void setInOutcomingUpdateDate(String inOutcomingUpdateDate) {
		this.inOutcomingUpdateDate = inOutcomingUpdateDate;
	}
	@Override
	public String toString() {
		return "InOutcoming [inOutcomingCode=" + inOutcomingCode + ", goodsCode=" + goodsCode + ", inOutcomingQuantity="
				+ inOutcomingQuantity + ", inOutcomingType=" + inOutcomingType + ", inOutcomingDate=" + inOutcomingDate
				+ ", inOutcomingRegId=" + inOutcomingRegId + ", inOutcomingRegDate=" + inOutcomingRegDate
				+ ", inOutcomingUpdateId=" + inOutcomingUpdateId + ", inOutcomingUpdateDate=" + inOutcomingUpdateDate
				+ "]";
	}
	
}