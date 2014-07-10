package com.momo.model;

public class pesanan {
	private String idMenu;
	private String namaMenu;
	private String qty;
	private String subTotal;
	public String getIdMenu() {
		return idMenu;
	}
	public void setIdMenu(String idMenu) {
		this.idMenu = idMenu;
	}
	public String getNamaMenu() {
		return namaMenu;
	}
	public void setNamaMenu(String namaMenu) {
		this.namaMenu = namaMenu;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}
	public pesanan(String idMenu, String namaMenu, String qty, String subTotal) {
		super();
		this.idMenu = idMenu;
		this.namaMenu = namaMenu;
		this.qty = qty;
		this.subTotal = subTotal;
	}
	public pesanan(){}
	
}
