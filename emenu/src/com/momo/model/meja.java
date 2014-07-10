package com.momo.model;

import java.util.ArrayList;

public class meja {
	private String nama;
	private String nomor;
	private ArrayList<pesanan> listPesanan=new ArrayList<pesanan>();
	public ArrayList<pesanan> getListPesanan() {
		return listPesanan;
	}
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	public String getNomor() {
		return nomor;
	}
	public void setNomor(String nomor) {
		this.nomor = nomor;
	}
	public meja(String nama, String nomor) {
		super();
		this.nama = nama;
		this.nomor = nomor;
	}
	public void addPesanan(pesanan p){
		listPesanan.add(p);
	}
}
