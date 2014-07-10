package com.momo.xmlparser;

import java.util.ArrayList;

import com.momo.model.menu;

import android.util.Log;


public class ParsedListMenuDataSet {
        private String extractedString = null;
        private int extractedInt = 0;
        private ArrayList<menu> list=null;
        private String id_menu;
    	private String nama_menu;
    	private String deskripsi;
    	private String stok;
    	private String harga;
    	private String pic;
    	private float jml_rate;
    	private float hasil_rate;
    	private String 	id_kategori;
    	
        
		public String getHarga() {
			return harga;
		}


		public void setHarga(String harga) {
			this.harga = harga;
		}


		public ParsedListMenuDataSet() {
			list=new ArrayList<menu>();
		}


		public ArrayList<menu> getList() {
			return list;
		}


		public void setList(ArrayList<menu> list) {
			this.list = list;
		}


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


		public void setJml_rate(String jmlRate) {
			jml_rate = Float.parseFloat(jmlRate);
		}


		public float getHasil_rate() {
			return hasil_rate;
		}


		public void setHasil_rate(String hasilRate) {
			hasil_rate = Float.parseFloat(hasilRate);
		}


		public String getId_kategori() {
			return id_kategori;
		}


		public void setId_kategori(String idKategori) {
			id_kategori = idKategori;
		}


		public void addMenu(){
			
			menu m=new menu(id_menu, nama_menu, deskripsi, stok, pic,harga, jml_rate, hasil_rate, id_kategori);
			
			list.add(m);
        }
        
}