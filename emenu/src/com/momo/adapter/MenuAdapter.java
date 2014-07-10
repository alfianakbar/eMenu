package com.momo.adapter;

import java.util.ArrayList;


import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.method.DialerKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.momo.customview.ActionItem;
import com.momo.customview.NumberPicker;
import com.momo.customview.QuickAction;
import com.momo.emenu.R;
import com.momo.emenu.R.id;
import com.momo.model.kategori;
import com.momo.model.meja;
import com.momo.model.menu;
import com.momo.model.pesanan;
import com.momo.util.DrawableManager;
import com.momo.xmlparser.PesananHandler;
import com.momo.xmlparser.constant;
public class MenuAdapter extends ArrayAdapter<menu>{
	private static final int ID_ADD = 1;
	private static final int ID_DETAIL = 2;
	private static final int ID_RATE = 3;
	
	private LayoutInflater mInflater;
	private ArrayList<menu> mList;
    private Context mCon;
    private int mViewResourceId;
    Dialog detail,pesan,ratedialog;
    String id="";
    static int selected;
    private DrawableManager manager=new DrawableManager();
	public MenuAdapter(Context context, int textViewResourceId,ArrayList<menu> list) {
		super(context, textViewResourceId);
		mInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mList=list;
		mViewResourceId=textViewResourceId;
		mCon=context;
		this.manager=new DrawableManager();
		// TODO Auto-generated constructor stub
	}
	@Override
    public int getCount() {
            return mList.size();
    }
	@Override
	public boolean areAllItemsEnabled() {
	    return true;
	}

	@Override
	public boolean isEnabled(int position) {
	    return true;
	}

    @Override
    public menu getItem(int position) {
            return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
            return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
    	convertView = mInflater.inflate(mViewResourceId, null);
    	TextView nama=(TextView)convertView.findViewById(R.id.textNama);
    	ImageView foto=(ImageView)convertView.findViewById(R.id.foto);
    	final RatingBar rate=(RatingBar)convertView.findViewById(R.id.ratingBar);
    	TextView harga=(TextView)convertView.findViewById(R.id.textHarga);
    	nama.setText(mList.get(position).getNama_menu().toUpperCase());
    	harga.setText("Rp."+mList.get(position).getHarga()+",00");
    	foto.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ActionItem addItem 		= new ActionItem(ID_ADD, "Pesan", mCon.getResources().getDrawable(R.drawable.ic_add));
				ActionItem acceptItem 	= new ActionItem(ID_DETAIL, "Detail", mCon.getResources().getDrawable(R.drawable.ic_accept));
		        ActionItem uploadItem 	= new ActionItem(ID_RATE, "Rate", mCon.getResources().getDrawable(R.drawable.ic_up));
				
				final QuickAction mQuickAction 	= new QuickAction(mCon);
				
				mQuickAction.addActionItem(addItem);
				mQuickAction.addActionItem(acceptItem);
				mQuickAction.addActionItem(uploadItem);
				MenuAdapter.selected=position;
				mQuickAction.show(v);
				mQuickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
					//
					public void onItemClick(QuickAction quickAction, int pos, int actionId) {
						ActionItem actionItem = quickAction.getActionItem(pos);
						
						switch (actionId) {
						case ID_ADD:
							pesan=new Dialog(mCon);
							pesan.setContentView(R.layout.pesandialog);
							TextView namaP=(TextView)pesan.findViewById(R.id.textNamaP);
							TextView hargaP=(TextView)pesan.findViewById(R.id.textHargaP);
							namaP.setText(mList.get(selected).getNama_menu().toUpperCase());
							hargaP.setText("Rp."+mList.get(selected).getHarga()+".00");
							NumberPicker qty=(NumberPicker)pesan.findViewById(R.id.Picker1);
							qty.setEdit(hargaP);
							qty.setHarga(mList.get(selected).getHarga());
							Button cancelP=(Button)pesan.findViewById(R.id.buttonCancelP);
							Button okP=(Button)pesan.findViewById(R.id.buttonOKP);
							cancelP.setOnClickListener(listener);
							okP.setOnClickListener(listener);
							pesan.show();
							break;
						case ID_DETAIL:
							detail=new Dialog(mCon);
							detail.setContentView(R.layout.detaildialog);
							ImageView image=(ImageView)detail.findViewById(R.id.imageViewD);
							TextView nama=(TextView)detail.findViewById(R.id.textNamaD);
							TextView harga=(TextView)detail.findViewById(R.id.textHargaD);
							TextView des=(TextView)detail.findViewById(R.id.textDeskripsi);
							((Button)detail.findViewById(R.id.buttonCancel)).setOnClickListener(listener);
							nama.setText(mList.get(position).getNama_menu().toUpperCase());
					    	harga.setText("Rp."+mList.get(position).getHarga()+",00");
					    	des.setText(mList.get(position).getDeskripsi());
							manager.fetchDrawableOnThread(constant.IMG_URL+mList.get(position).getPic(), image);
							detail.show();
							break;
						case ID_RATE:
							ratedialog=new Dialog(mCon);
							ratedialog.setContentView(R.layout.ratedialog);
							Button cancel=(Button)ratedialog.findViewById(R.id.rate);
							Button ok=(Button)ratedialog.findViewById(R.id.cancel);
							ok.setOnClickListener(listener);
							cancel.setOnClickListener(listener);
							ratedialog.show();
							break;
						default:
							break;
						}
					}
				});
			}
		});
    	manager.fetchDrawableOnThread(constant.IMG_URL+mList.get(position).getPic(), foto);
    	rate.setRating(mList.get(position).getHasil_rate());
    	rate.setEnabled(false);
    	return convertView;
    }
    
    protected OnClickListener listener=new OnClickListener() {
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.buttonCancel:
				detail.dismiss();
				break;
			case R.id.rate:
				RatingBar rating=(RatingBar)ratedialog.findViewById(R.id.rating);
				new InputRate(mList.get(selected).getId_menu(), rating.getRating()).execute();
				ratedialog.dismiss();
				break;
			case R.id.cancel:
				ratedialog.dismiss();
				break;
			case R.id.buttonCancelP:
				pesan.dismiss();
				break;
			case R.id.buttonOKP:
				PesananHandler p=new PesananHandler(mCon);
				TextView namaP=(TextView)pesan.findViewById(R.id.textNamaP);
				TextView hargaP=(TextView)pesan.findViewById(R.id.textHargaP);
				NumberPicker qty=(NumberPicker)pesan.findViewById(R.id.Picker1);
				String idm=mList.get(selected).getId_menu();
				String nama=mList.get(selected).getNama_menu();
				String qtyt=qty.getValue()+"";
				String harga=mList.get(selected).getHarga();
				p.addContact(new pesanan(idm,nama ,qtyt ,harga ));
				pesan.dismiss();
				break;
			default:
				break;
			}
		}
	};
	public class InputRate extends AsyncTask<Void, Void, Void>{
		Toast a;
		float rate;
		String user,title,review,hasil,id;
		public InputRate(String id,float rate){
			this.rate=rate;
			this.id=id;
		}
		@Override
		protected void onPreExecute() {
				a=Toast.makeText(mCon, "Uploading Rate...",130);
				a.show();
		}
		@Override
		protected Void doInBackground(Void... arg0) {
			String url=constant.URL+"insertRate.php?id="+id+"&rate="+this.rate;
			hasil=com.momo.xmlparser.ExecuteStream.execute(url);
			return null;
		}
		@Override
		protected void onPostExecute(Void unused) {
			a.cancel();
			if(hasil.equals("sukses")){
				a=Toast.makeText(mCon, "Rate Uploaded ",10);
			}
			else{
				a=Toast.makeText(mCon, "Rate Upload Eror",10);
			}
			a.show();
		}
	  }
}