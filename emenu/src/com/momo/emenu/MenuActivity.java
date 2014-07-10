package com.momo.emenu;

import java.util.ArrayList;
import java.util.List;

import com.momo.model.kategori;
import com.momo.model.meja;
import com.momo.model.menu;
import com.momo.model.pesanan;
import com.momo.xmlparser.ExecuteStream;
import com.momo.xmlparser.KategoriHandler;
import com.momo.xmlparser.ListMenuHandler;
import com.momo.xmlparser.MejaHandler;
import com.momo.xmlparser.ParsedKategoriDataSet;
import com.momo.xmlparser.ParsingXML;
import com.momo.xmlparser.PesananHandler;
import com.momo.xmlparser.constant;

import com.momo.adapter.KategoriAdapter;
import com.momo.adapter.KonfirmasiAdapter;
import com.momo.adapter.MenuAdapter;
import com.momo.adapter.MenuEditAdapter;
import com.momo.customview.ActionItem;
import com.momo.customview.NumberPicker;
import com.momo.customview.QuickAction;
import com.momo.emenu.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.MailTo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;

public class MenuActivity extends Activity{
	private ArrayList<kategori> listkategori;
	private ArrayList<menu> listMenu;
	private String curentCategory="";
	private static final int ID_ADD = 1;
	private static final int ID_DETAIL = 2;
	private static final int ID_RATE = 3;
	Dialog editDialog,konfirmDialog;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		RadioGroup group=(RadioGroup)findViewById(R.id.radioGroup1);
		group.setOnCheckedChangeListener(ToggleListener);
		GridView list=(GridView)findViewById(R.id.listMenu);
		new GetData().execute();  
	}
	@Override
	public void onBackPressed () {
		DialogInterface.OnClickListener yes=new DialogInterface.OnClickListener() {
			   public void onClick(DialogInterface dialog, int which) {
				      // here you can add functions
					   MejaHandler m=new MejaHandler(MenuActivity.this);
					   PesananHandler p=new PesananHandler(MenuActivity.this);
					   p.deleteAllPesanan();
					   m.deleteAllMeja();
					   Intent i=new Intent(MenuActivity.this,EmenuActivity.class);
					   startActivity(i);
					   MenuActivity.this.finish();
					   dialog.dismiss();
				   }
		};
		DialogInterface.OnClickListener no=new DialogInterface.OnClickListener() {
			   public void onClick(DialogInterface dialog, int which) {
				      // here you can add functions
				
				   dialog.dismiss();
				   }
		};
		showDialog(this, "E-Menu", "Semua Pesanan akan Dibatalkan.\nYakin Ingin untuk Keluar ?", yes, no);
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
			
			konfirmDialog=new Dialog(this);
			konfirmDialog.setContentView(R.layout.konfdialog);
			final ListView listPesanKo=(ListView)konfirmDialog.findViewById(R.id.listEdit);
			final PesananHandler pesanKo=new PesananHandler(this);
			final KonfirmasiAdapter adapterKo=new KonfirmasiAdapter(this, R.layout.konfirmasiadapter, pesanKo.getAllContacts());
			listPesanKo.setAdapter(adapterKo);
			Button cancel=(Button)konfirmDialog.findViewById(R.id.buttonCancelE);
			Button edit=(Button)konfirmDialog.findViewById(R.id.buttonEdit);
			int total=0;
			for(pesanan p:pesanKo.getAllContacts()){
				int sub=Integer.parseInt(p.getSubTotal())*Integer.parseInt(p.getQty());
		    	
				total+=sub;
			}
			TextView to=(TextView)konfirmDialog.findViewById(R.id.total);
			to.setText(total+"");
			cancel.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					konfirmDialog.dismiss();
				}
			});
			edit.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					konfirmDialog.dismiss();
					final MejaHandler m=new MejaHandler(MenuActivity.this);
					ArrayList<meja> list=m.getAllMeja();
					String hasil="";
					String insertmenu="";
					for(meja me:list){
						hasil=ExecuteStream.execute(constant.URL+"insertpesanan.php?no="+me.getNama()+"&nama="+me.getNomor());
					}
					if(hasil.equals("error")||hasil.equals("")){
						Toast.makeText(MenuActivity.this, "gagal", Toast.LENGTH_SHORT).show();	
					}else{
						final PesananHandler p=new PesananHandler(MenuActivity.this);
						ArrayList<pesanan> listp=p.getAllContacts();
						int jum=listp.size();
						for(pesanan pe:listp){
							insertmenu=ExecuteStream.execute(constant.URL+"insertdetailpesanan.php?id="+hasil+"&menu="+pe.getIdMenu()+"&qty="+pe.getQty());
							if(insertmenu.equals("sukses")){
								jum--;
							}
						}
						if(jum==0){
							final AlertDialog alertDialog = new AlertDialog.Builder(MenuActivity.this).create();
							alertDialog.setTitle("E-Menu Madtari");
							alertDialog.setMessage("Terima Kasih, Pesanan Segera Kami Hidangkan. :)");
							alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
							   public void onClick(DialogInterface dialog, int which) {
							      // here you can add functions
								   p.deleteAllPesanan();
								   m.deleteAllMeja();
								   Intent i=new Intent(MenuActivity.this,EmenuActivity.class);
								   startActivity(i);
								   MenuActivity.this.finish();
								   alertDialog.dismiss();
							   }
							});
							alertDialog.show();
						}else{
							Toast.makeText(MenuActivity.this, "gagal", Toast.LENGTH_SHORT).show();
						}
					}
					//Toast.makeText(MenuActivity.this, listPesan.getChildCount()+"", Toast.LENGTH_SHORT).show();
				}
			});
			konfirmDialog.show();
			
			break;
		case R.id.change:
			editDialog=new Dialog(this);
			editDialog.setContentView(R.layout.editpesanandialog);
			final ListView listPesan=(ListView)editDialog.findViewById(R.id.listEdit);
			final PesananHandler pesan=new PesananHandler(this);
			final MenuEditAdapter adapter=new MenuEditAdapter(this, R.layout.editpesananadapter, pesan.getAllContacts());
			listPesan.setAdapter(adapter);
			cancel=(Button)editDialog.findViewById(R.id.buttonCancelE);
			edit=(Button)editDialog.findViewById(R.id.buttonEdit);
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
					ArrayList<pesanan> temp=new ArrayList<pesanan>();
					for(int i=0;i<listPesan.getChildCount();i++){
						View adap=listPesan.getChildAt(i);
						NumberPicker qty=(NumberPicker)adap.findViewById(R.id.qty);
						pesan.updateContact(adapter.getItem(i).getIdMenu(), qty.getValue()+"");
						temp.add(adapter.getItem(i));
						Toast.makeText(MenuActivity.this, qty.getValue()+"", Toast.LENGTH_SHORT).show();
					}
					PesananHandler pesDb=new PesananHandler(MenuActivity.this);
					ArrayList<pesanan> db=pesDb.getAllContacts();
					for(pesanan p:db){
						boolean ada=false;
						for(pesanan ptemp:temp){
							if(ptemp.getIdMenu().equals(p.getIdMenu())){
								ada=true;
							}
						}
						if(!ada){
							pesDb.deleteContact(p);
						}
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
						   PesananHandler p=new PesananHandler(MenuActivity.this);
							p.deleteAllPesanan();	
							dialog.dismiss();
							Toast.makeText(MenuActivity.this, "Semua Pesanan Telah di Batalkan", Toast.LENGTH_LONG).show();
						   
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
	public class GetData extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			ParsingXML parseKategori=new ParsingXML(MenuActivity.this, constant.GET_LIST_KATEGORI);
			try {
				parseKategori.parse();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			listkategori=((KategoriHandler)parseKategori.getMyExampleHandler()).getParsedData().getList();
			listkategori.add(0, new kategori("", "All Menu", ""));
			
			ParsingXML parseMenu=new ParsingXML(MenuActivity.this, constant.GET_LIST_MENU,"all","sell");
			try {
				parseMenu.parse();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			listMenu=((ListMenuHandler)parseMenu.getMyExampleHandler()).getParsedData().getList();
			return null;
		}
		protected void onPostExecute(Void unused) {
			KategoriAdapter kAdapter=new KategoriAdapter(MenuActivity.this, R.layout.kategoriadapter, listkategori);
			ListView kategori=(ListView)findViewById(R.id.listkategori);
			kategori.setAdapter(kAdapter);
			kategori.setOnItemClickListener(itemclick);
			MenuAdapter mAdapter=new MenuAdapter(MenuActivity.this, R.layout.listadapter, listMenu);
			GridView menu=(GridView)findViewById(R.id.listMenu);
			menu.setAdapter(mAdapter);
			onToggle(findViewById(R.id.toggleBestSeller));
		}
		
	}
	
	protected OnItemClickListener itemclick=new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			ToggleButton defaultB=(ToggleButton)findViewById(R.id.toggleBestSeller);
			((RadioGroup)defaultB.getParent()).check(R.id.toggleBestSeller);
			curentCategory=listkategori.get(arg2).getId_kategori();
			ParsingXML parseMenu;
			if(listkategori.get(arg2).getId_kategori().equals("")){
				parseMenu=new ParsingXML(MenuActivity.this, constant.GET_LIST_MENU,"all","sell");
			}else{
				parseMenu=new ParsingXML(MenuActivity.this, constant.GET_LIST_MENU,curentCategory,"sell");
			}
			try {
				parseMenu.parse();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			listMenu=((ListMenuHandler)parseMenu.getMyExampleHandler()).getParsedData().getList();
			MenuAdapter mAdapter=new MenuAdapter(MenuActivity.this, R.layout.listadapter, listMenu);
			GridView menu=(GridView)findViewById(R.id.listMenu);
			menu.setAdapter(mAdapter);
		}
	};
	static final RadioGroup.OnCheckedChangeListener ToggleListener = new RadioGroup.OnCheckedChangeListener() {

		public void onCheckedChanged(RadioGroup arg0, int arg1) {
			// TODO Auto-generated method stub
			for (int j = 0; j < arg0.getChildCount(); j++) {
                final ToggleButton view = (ToggleButton) arg0.getChildAt(j);
                	view.setChecked(view.getId() == arg1);
                	if(view.getId() == arg1){
                		view.setEnabled(false);
                		
                	}else{
                		view.setEnabled(true);
                	}
            }
		}
        
    };
    public void onToggle(View view) {
        ((RadioGroup)view.getParent()).check(view.getId());
        ParsingXML parseMenu;
        switch (view.getId()) {
		case R.id.toggleBestSeller:
			if(curentCategory.equals("")){
				parseMenu=new ParsingXML(MenuActivity.this, constant.GET_LIST_MENU,"all","sell");
			}else{
				parseMenu=new ParsingXML(MenuActivity.this, constant.GET_LIST_MENU,curentCategory,"sell");
			}
			try {
				parseMenu.parse();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			listMenu=((ListMenuHandler)parseMenu.getMyExampleHandler()).getParsedData().getList();
			MenuAdapter mAdapter=new MenuAdapter(MenuActivity.this, R.layout.listadapter, listMenu);
			GridView menu=(GridView)findViewById(R.id.listMenu);
			menu.setAdapter(mAdapter);
			break;
		case R.id.toggleCheap:
			if(curentCategory.equals("")){
				parseMenu=new ParsingXML(MenuActivity.this, constant.GET_LIST_MENU,"all","harga");
			}else{
				parseMenu=new ParsingXML(MenuActivity.this, constant.GET_LIST_MENU,curentCategory,"harga");
			}try {
				parseMenu.parse();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			listMenu=((ListMenuHandler)parseMenu.getMyExampleHandler()).getParsedData().getList();
			mAdapter=new MenuAdapter(MenuActivity.this, R.layout.listadapter, listMenu);
			menu=(GridView)findViewById(R.id.listMenu);
			menu.setAdapter(mAdapter);
			break;
		case R.id.toggleTopRated:
			if(curentCategory.equals("")){
				parseMenu=new ParsingXML(MenuActivity.this, constant.GET_LIST_MENU,"all","hasil_rate");
			}else{
				parseMenu=new ParsingXML(MenuActivity.this, constant.GET_LIST_MENU,curentCategory,"hasil_rate");
			}try {
				parseMenu.parse();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			listMenu=((ListMenuHandler)parseMenu.getMyExampleHandler()).getParsedData().getList();
			mAdapter=new MenuAdapter(MenuActivity.this, R.layout.listadapter, listMenu);
			menu=(GridView)findViewById(R.id.listMenu);
			menu.setAdapter(mAdapter);
			break;
		default:
			break;
		}
		
    }
    protected OnQueryTextListener searchlistener=new OnQueryTextListener() {
		
		public boolean onQueryTextSubmit(String query) {
			// TODO Auto-generated method stub
			ParsingXML parseMenu=new ParsingXML(MenuActivity.this, constant.GET_LIST_SEARCH,query);
			try {
				parseMenu.parse();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SearchResult.list=((ListMenuHandler)parseMenu.getMyExampleHandler()).getParsedData().getList();
			Intent i=new Intent(MenuActivity.this, SearchResult.class);
			startActivity(i);
			return false;
		}
		
		public boolean onQueryTextChange(String newText) {
			// TODO Auto-generated method stub
			return false;
		}
	};

}
