package com.stream.dto;

public class ProductSales {

	private String seller;

	private String productCode;

	private Long salesCount;

	public ProductSales() {
		super();
	}

	public ProductSales(String seller, String productCode, Long salesCount) {
		super();
		this.seller = seller;
		this.productCode = productCode;
		this.salesCount = salesCount;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Long getSalesCount() {
		return salesCount;
	}

	public void setSalesCount(Long salesCount) {
		this.salesCount = salesCount;
	}

	@Override
	public String toString() {
		return "ProductSales [seller=" + seller + ", productCode=" + productCode + ", salesCount=" + salesCount + "]";
	}

}
