    package com.momo.xmlparser;
     
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;
     
public class ParsingXML {
           	//private Context ctx;
	private int status;
	private String raw_url;
	Context mctx;
	public DefaultHandler getMyExampleHandler() {
		return myExampleHandler;
	}
	DefaultHandler myExampleHandler=null;
			
	public ParsingXML(Context ctx , int status){
           	//this.ctx=ctx;
			this.status=status;
			this.mctx=ctx;
			if(status==constant.GET_LIST_MENU){
				raw_url=constant.URL+"listMenu.php";
				Log.d("url",raw_url);
				myExampleHandler = new ListMenuHandler();
	        }else if(status==constant.GET_LIST_KATEGORI){
				raw_url=constant.URL+"listKategori.php";
				Log.d("url",raw_url);
				myExampleHandler = new KategoriHandler();
	        }
	}
	public ParsingXML(Context ctx,int status,String id){
		this.status=status;
		this.mctx=ctx;
		if(status==constant.GET_LIST_MENU){
			raw_url=constant.URL+"listMenu.php?id="+id;
			Log.d("url",raw_url);
			myExampleHandler = new ListMenuHandler();
        }else if(status==constant.GET_LIST_SEARCH){
			raw_url=constant.URL+"search.php?id="+id;
			Log.d("url",raw_url);
			myExampleHandler = new ListMenuHandler();
        }
	}
	public ParsingXML(Context ctx,int status,String id,String sort){
		this.status=status;
		this.mctx=ctx;
		if(status==constant.GET_LIST_MENU){
			raw_url=constant.URL+"listMenu.php?id="+id+"&sortby="+sort;
			Log.d("url",raw_url);
			myExampleHandler = new ListMenuHandler();
        }
	}
	
	public void parse() throws Exception{	
    		StrictMode.ThreadPolicy policy = new StrictMode.
            ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
    		URL url = new URL(raw_url);
            	
        	SAXParserFactory spf = SAXParserFactory.newInstance();
    		SAXParser sp = spf.newSAXParser();
    	    XMLReader xr = sp.getXMLReader();
            xr.setContentHandler(myExampleHandler);
                   
            xr.parse(new InputSource(url.openStream()));
                   
	}
}