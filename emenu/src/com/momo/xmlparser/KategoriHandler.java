package com.momo.xmlparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class KategoriHandler extends DefaultHandler{
	 
    // ===========================================================
    // Fields
    // ===========================================================
   
    private boolean in_data = false;
    private boolean in_barang=false;
    private boolean id_kategori=false,nama_kategori=false,icon=false;
    private StringBuffer buuff=null;
   
    private ParsedKategoriDataSet myParsedExampleDataSet = new ParsedKategoriDataSet();

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public ParsedKategoriDataSet getParsedData() {
            return this.myParsedExampleDataSet;
    }

    // ===========================================================
    // Methods
    // ===========================================================
    @Override
    public void startDocument() throws SAXException {
            this.myParsedExampleDataSet = new ParsedKategoriDataSet();
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
            }else if (localName.equals("kategori")) {
                    this.setIn_barang(true);
            }else if (localName.equals("id_kategori")) {
                    this.id_kategori = true;
                    setBuuff(new StringBuffer(""));
            }else if (localName.equals("nama_kategori")) {
            		this.nama_kategori=true;
            		setBuuff(new StringBuffer(""));
            }else if (localName.equals("icon")) {
        		this.icon=true;
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
    	}else if (localName.equals("kategori")) {
    		myParsedExampleDataSet.addKategori() ;
            this.setIn_barang(false);
    	}else if (localName.equals("id_kategori")) {
            this.id_kategori = false; 
    	}else if (localName.equals("nama_kategori")) {
    		this.nama_kategori=false;
    	}else if (localName.equals("icon")) {
    		this.icon=false; 
    	}
    }
   
    /** Gets be called on the following structure:
     * <tag>characters</tag> */
    @Override
public void characters(char ch[], int start, int length) {
            if(this.id_kategori){
            	myParsedExampleDataSet.setId_kategori (new String(ch, start, length));
            }
            if(this.nama_kategori){
                myParsedExampleDataSet.setNama_kategori(new String(ch, start, length));
            }
            if(this.icon){
                myParsedExampleDataSet.setIcon(new String(ch, start, length));
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
