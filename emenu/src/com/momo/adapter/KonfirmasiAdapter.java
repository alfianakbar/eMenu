package com.momo.adapter;

import java.util.ArrayList;

import com.momo.customview.NumberPicker;
import com.momo.emenu.R;
import com.momo.model.pesanan;
import com.momo.util.DrawableManager;
import com.momo.xmlparser.PesananHandler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class KonfirmasiAdapter extends ArrayAdapter<pesanan>{
	private LayoutInflater mInflater;
	private ArrayList<pesanan> mList;
    private Context mCon;
    private int mViewResourceId;
    private DrawableManager manager=new DrawableManager();
	public KonfirmasiAdapter(Context context, int textViewResourceId,ArrayList<pesanan> list) {
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
    public pesanan getItem(int position) {
            return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
            return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
    	convertView = mInflater.inflate(mViewResourceId, null);
    	
    	TextView nama=(TextView)convertView.findViewById(R.id.textNamaE);
    	TextView harga=(TextView)convertView.findViewById(R.id.textHargaE);
    	TextView qty=(TextView)convertView.findViewById(R.id.qty);
    	qty.setText(mList.get(position).getQty());
    	nama.setText(mList.get(position).getNamaMenu().toUpperCase());
    	String sub=Integer.parseInt(mList.get(position).getSubTotal())*Integer.parseInt(mList.get(position).getQty())+"";
    	harga.setText("Rp."+sub+".00");
    	
    	return convertView;
    }

}
