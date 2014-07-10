package com.momo.emenu;

import java.util.ArrayList;

import com.momo.adapter.MenuAdapter;
import com.momo.adapter.MenuEditAdapter;
import com.momo.customview.NumberPicker;
import com.momo.emenu.EmenuActivity.tombol;
import com.momo.model.meja;
import com.momo.model.menu;
import com.momo.model.pesanan;
import com.momo.xmlparser.ExecuteStream;
import com.momo.xmlparser.ListMenuHandler;
import com.momo.xmlparser.MejaHandler;
import com.momo.xmlparser.ParsingXML;
import com.momo.xmlparser.PesananHandler;
import com.momo.xmlparser.constant;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SearchView.OnQueryTextListener;

public class SearchResult extends Activity{
	 public static ArrayList<menu> list=null;Dialog editDialog;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.serachreasult);
	        MenuAdapter adapter=new MenuAdapter(this, R.layout.listadapter, list);
	        GridView gris=(GridView)findViewById(R.id.gridViewsearch);
	        gris.setAdapter(adapter);
	        TextView result=(TextView)findViewById(R.id.textViewresult);
	        result.setText("Ditemukan "+list.size()+" Hasil pencarian");
	    }
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.actions, menu);
	        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
	        searchView.setOnQueryTextListener(searchlistener);
	        return true;
	    }
		@Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
			case R.id.confirm:
				final MejaHandler m=new MejaHandler(SearchResult.this);
				ArrayList<meja> list=m.getAllMeja();
				String hasil="";
				String insertmenu="";
				for(meja me:list){
					hasil=ExecuteStream.execute(constant.URL+"insertpesanan.php?no="+me.getNama()+"&nama="+me.getNomor());
				}
				if(hasil.equals("error")||hasil.equals("")){
					Toast.makeText(SearchResult.this, "gagal", Toast.LENGTH_SHORT).show();	
				}else{
					final PesananHandler p=new PesananHandler(SearchResult.this);
					ArrayList<pesanan> listp=p.getAllContacts();
					int jum=listp.size();
					for(pesanan pe:listp){
						insertmenu=ExecuteStream.execute(constant.URL+"insertdetailpesanan.php?id="+hasil+"&menu="+pe.getIdMenu()+"&qty="+pe.getQty());
						if(insertmenu.equals("sukses")){
							jum--;
						}
					}
					if(jum==0){
						final AlertDialog alertDialog = new AlertDialog.Builder(SearchResult.this).create();
						alertDialog.setTitle("E-Menu Madtari");
						alertDialog.setMessage("Terima Kasih, Pesanan Segera Kami Hidangkan. :)");
						alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
						   public void onClick(DialogInterface dialog, int which) {
						      // here you can add functions
							   p.deleteAllPesanan();
							   m.deleteAllMeja();
							   Intent i=new Intent(SearchResult.this,EmenuActivity.class);
							   startActivity(i);
							   SearchResult.this.finish();
							   alertDialog.dismiss();
						   }
						});
						alertDialog.show();
					}else{
						Toast.makeText(SearchResult.this, "gagal", Toast.LENGTH_SHORT).show();
					}
				}
				break;
			case R.id.change:
				editDialog=new Dialog(this);
				editDialog.setContentView(R.layout.editpesanandialog);
				final ListView listPesan=(ListView)editDialog.findViewById(R.id.listEdit);
				final PesananHandler pesan=new PesananHandler(this);
				final MenuEditAdapter adapter=new MenuEditAdapter(this, R.layout.editpesananadapter, pesan.getAllContacts());
				listPesan.setAdapter(adapter);
				Button cancel=(Button)editDialog.findViewById(R.id.buttonCancelE);
				Button edit=(Button)editDialog.findViewById(R.id.buttonEdit);
				cancel.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						editDialog.dismiss();
					}
				});
				edit.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						editDialog.dismiss();
						for(int i=0;i<listPesan.getChildCount();i++){
							View adap=listPesan.getChildAt(i);
							NumberPicker qty=(NumberPicker)adap.findViewById(R.id.qty);
							pesan.updateContact(adapter.getItem(i).getIdMenu(), qty.getValue()+"");
							Toast.makeText(SearchResult.this, qty.getValue()+"", Toast.LENGTH_SHORT).show();
						}
						//Toast.makeText(MenuActivity.this, listPesan.getChildCount()+"", Toast.LENGTH_SHORT).show();
					}
				});
				editDialog.show();
				break;
			case R.id.cancel:
				showDialog(this,"E-Menu Madtari","Yakin Untuk Menghapus Semua Pesanan?",new DialogInterface.OnClickListener() {
					   public void onClick(DialogInterface dialog, int which) {
						      // here you can add functions
							   PesananHandler p=new PesananHandler(SearchResult.this);
								p.deleteAllPesanan();	
								dialog.dismiss();
								Toast.makeText(SearchResult.this, "Semua Pesanan Telah di Batalkan", Toast.LENGTH_LONG).show();
							   
						   }
						},new DialogInterface.OnClickListener() {
							   public void onClick(DialogInterface dialog, int which) {
								      // here you can add functions
									   dialog.dismiss();
								   }
							});
				
				break;

			default:
				break;
			}
	        return true;
	    }
		public void showDialog(Activity activity, String title, CharSequence message,DialogInterface.OnClickListener yes,DialogInterface.OnClickListener no) {
	        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
	        if (title != null)
	            builder.setTitle(title);
	        builder.setMessage(message);
	        builder.setPositiveButton("OK",yes);
	        builder.setNegativeButton("Cancel", no);
	        builder.show();
	    }
		protected OnQueryTextListener searchlistener=new OnQueryTextListener() {
			
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				ParsingXML parseMenu=new ParsingXML(SearchResult.this, constant.GET_LIST_SEARCH,query);
				try {
					parseMenu.parse();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				SearchResult.list=((ListMenuHandler)parseMenu.getMyExampleHandler()).getParsedData().getList();
				Intent i=new Intent(SearchResult.this, SearchResult.class);
				startActivity(i);
				SearchResult.this.finish();
				return false;
			}
			
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				return false;
			}
		};
}
