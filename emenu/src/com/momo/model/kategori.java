package com.momo.model;

public class kategori {
	private String id_kategori;
	private String nama_kategori;
	private String icon;
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getId_kategori() {
		return id_kategori;
	}
	public kategori(String idKategori, String namaKategori, String icon) {
		super();
		id_kategori = idKategori;
		nama_kategori = namaKategori;
		this.icon = icon;
	}
	public void setId_kategori(String idKategori) {
		id_kategori = idKategori;
	}
	public String getNama_kategori() {
		return nama_kategori;
	}
	public void setNama_kategori(String namaKategori) {
		nama_kategori = namaKategori;
	}
	
}
