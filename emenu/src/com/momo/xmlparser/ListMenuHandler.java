package com.momo.xmlparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;
 
 
public class ListMenuHandler extends DefaultHandler{
 
        // ===========================================================
        // Fields
        // ===========================================================
       
        private boolean in_data = false;
        private boolean in_barang=false;
        private boolean id=false,nama=false,deskripsi=false,stok=false,pict=false,ketgori=false,harga=false,jml_rate=false,hasilRate=false;
        private StringBuffer buuff=null;
       
        private ParsedListMenuDataSet myParsedExampleDataSet = new ParsedListMenuDataSet();
 
        // ===========================================================
        // Getter & Setter
        // ===========================================================
 
        public ParsedListMenuDataSet getParsedData() {
                return this.myParsedExampleDataSet;
        }
 
        // ===========================================================
        // Methods
        // ===========================================================
        @Override
        public void startDocument() throws SAXException {
                this.myParsedExampleDataSet = new ParsedListMenuDataSet();
        }
 
        @Override
        public void endDocument() throws SAXException {
                // Nothing to do
        }
 
        /** Gets be called on opening tags like:
         * <tag>
         * Can provide attribute(s), when xml was like:
         * <tag attribute="attributeValue">*/
        @Override
        public void startElement(String namespaceURI, String localName,
                        String qName, Attributes atts) throws SAXException {
                if (localName.equals("data")) {
                        this.setIn_data(true);
                }else if (localName.equals("menu")) {
                        this.setIn_barang(true);
                }else if (localName.equals("id_menu")) {
                        this.id = true;
                        setBuuff(new StringBuffer(""));
                }else if (localName.equals("nama_menu")) {
                		this.nama=true;
                		setBuuff(new StringBuffer(""));
                }else if (localName.equals("deskripsi")) {
                		this.deskripsi=true;
                		setBuuff(new StringBuffer(""));
                }else if (localName.equals("stok")) {
            		this.stok=true;
            		setBuuff(new StringBuffer(""));
                }else if (localName.equals("pic")) {
            		this.pict=true;
            		setBuuff(new StringBuffer(""));
                }else if (localName.equals("harga")) {
            		this.harga=true;
            		setBuuff(new StringBuffer(""));
                } else if (localName.equals("jml_rate")) {
            		this.jml_rate=true;
            		setBuuff(new StringBuffer(""));
                }else if (localName.equals("hasil_rate")) {
            		this.hasilRate=true;
            		setBuuff(new StringBuffer(""));
                }else if (localName.equals("id_kategori")) {
            		this.ketgori=true;
            		setBuuff(new StringBuffer(""));
                }   
                
        }
       
        /** Gets be called on closing tags like:
         * </tag> */
        @Override
        public void endElement(String namespaceURI, String localName, String qName)
                        throws SAXException {
        	if (localName.equals("data")) {
                this.setIn_data(false);
        	}else if (localName.equals("menu")) {
        		myParsedExampleDataSet.addMenu() ;
                this.setIn_barang(false);
        	}else if (localName.equals("id_menu")) {
                this.id = false; 
        	}else if (localName.equals("nama_menu")) {
        		this.nama=false; 
        	}else if (localName.equals("deskripsi")) {
        		this.deskripsi=false; 
        	}else if (localName.equals("stok")) {
        		this.stok=false; 
        	}else if (localName.equals("pic")) {
        		this.pict=false; 
        	}else if (localName.equals("jml_rate")) {
        		this.jml_rate=false; 
        	}else if (localName.equals("hasil_rate")) {
        		this.hasilRate=false; 
        	}else if (localName.equals("id_kategori")) {
        		this.ketgori=false; 
        	}else if (localName.equals("harga")) {
        		this.harga=false; 
        	}
        }
       
        /** Gets be called on the following structure:
         * <tag>characters</tag> */
        @Override
    public void characters(char ch[], int start, int length) {
                if(this.id){
                	myParsedExampleDataSet.setId_menu(new String(ch, start, length));
                }
                if(this.nama){
                    myParsedExampleDataSet.setNama_menu (new String(ch, start, length));
                }
                if(this.deskripsi){
                    myParsedExampleDataSet.setDeskripsi(new String(ch, start, length));
                }
                if(this.stok){
                    myParsedExampleDataSet.setStok(new String(ch, start, length));
                }
                if(this.pict){
                    myParsedExampleDataSet.setPic(new String(ch, start, length));
                    Log.d("name", myParsedExampleDataSet.getPic());
                }
                if(this.jml_rate){
                    myParsedExampleDataSet.setJml_rate(new String(ch, start, length));
                }
                if(this.hasilRate){
                	myParsedExampleDataSet.setHasil_rate(new String(ch, start, length));
                }
                if(this.harga){
                    myParsedExampleDataSet.setHarga(new String(ch, start, length));
                }                
                
    }
		public void setIn_data(boolean in_data) {
			this.in_data = in_data;
		}

		public boolean isIn_data() {
			return in_data;
		}

		public void setIn_barang(boolean in_barang) {
			this.in_barang = in_barang;
		}

		public boolean isIn_barang() {
			return in_barang;
		}

		public void setBuuff(StringBuffer buuff) {
			this.buuff = buuff;
		}

		public StringBuffer getBuuff() {
			return buuff;
		}
}

