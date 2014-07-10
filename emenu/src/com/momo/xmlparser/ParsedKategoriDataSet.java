package com.momo.xmlparser;

import java.util.ArrayList;

import android.util.Log;

import com.momo.model.kategori;
import com.momo.model.menu;

public class ParsedKategoriDataSet {
	private String extractedString = null;
    private int extractedInt = 0;
    private ArrayList<kategori> list=new ArrayList<kategori>();
	private String id_kategori;
	private String nama_kategori;
	private String icon;
	public ArrayList<kategori> getList() {
		return list;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public void setList(ArrayList<kategori> list) {
		this.list = list;
	}
	public String getId_kategori() {
		return id_kategori;
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
	public void addKategori(){
		kategori k=new kategori(id_kategori,nama_kategori,icon);
		Log.d("a", k.getNama_kategori());
    	list.add(k);
    }
}
