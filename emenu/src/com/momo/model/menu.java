package com.momo.model;

public class menu {
	private String id_menu;
	private String nama_menu;
	private String deskripsi;
	private String stok;
	private String pic;
	private String harga;
	public menu(String idMenu, String namaMenu, String deskripsi, String stok,
			String pic, String harga, float jmlRate, float hasilRate,
			String idKategori) {
		super();
		id_menu = idMenu;
		nama_menu = namaMenu;
		this.deskripsi = deskripsi;
		this.stok = stok;
		this.pic = pic;
		this.harga = harga;
		jml_rate = jmlRate;
		hasil_rate = hasilRate;
		id_kategori = idKategori;
	}
	public String getHarga() {
		return harga;
	}
	public void setHarga(String harga) {
		this.harga = harga;
	}
	private float jml_rate;
	private float hasil_rate;
	private String 	id_kategori;
	public String getId_menu() {
		return id_menu;
	}
	public void setId_menu(String idMenu) {
		id_menu = idMenu;
	}
	public String getNama_menu() {
		return nama_menu;
	}
	public void setNama_menu(String namaMenu) {
		nama_menu = namaMenu;
	}
	public String getDeskripsi() {
		return deskripsi;
	}
	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}
	public String getStok() {
		return stok;
	}
	public void setStok(String stok) {
		this.stok = stok;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public float getJml_rate() {
		return jml_rate;
	}
	public void setJml_rate(float jmlRate) {
		jml_rate = jmlRate;
	}
	public float getHasil_rate() {
		return hasil_rate;
	}
	public void setHasil_rate(float hasilRate) {
		hasil_rate = hasilRate;
	}
	public String getId_kategori() {
		return id_kategori;
	}
	public void setId_kategori(String idKategori) {
		id_kategori = idKategori;
	}
}
