package com.momo.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.momo.customview.NumberPicker;
import com.momo.emenu.R;
import com.momo.model.kategori;
import com.momo.model.pesanan;
import com.momo.util.DrawableManager;
import com.momo.xmlparser.PesananHandler;

public class MenuEditAdapter extends ArrayAdapter<pesanan>{
	private LayoutInflater mInflater;
	private ArrayList<pesanan> mList;
    private Context mCon;
    private int mViewResourceId;
    private DrawableManager manager=new DrawableManager();
	public MenuEditAdapter(Context context, int textViewResourceId,ArrayList<pesanan> list) {
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
    	NumberPicker qty=(NumberPicker)convertView.findViewById(R.id.qty);
    	qty.setValue(Integer.parseInt(mList.get(position).getQty()));
    	nama.setText(mList.get(position).getNamaMenu().toUpperCase());
    	String sub=Integer.parseInt(mList.get(position).getSubTotal())*qty.getValue()+"";
    	harga.setText("Rp."+sub+".00");
    	qty.setHarga(mList.get(position).getSubTotal());
    	qty.setEdit(harga);
    	ImageButton delete=(ImageButton)convertView.findViewById(R.id.imageButtonDelete);
    	delete.setTag(position);
    	delete.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mList.remove(Integer.parseInt(v.getTag().toString()));
				MenuEditAdapter.this.notifyDataSetChanged();
			}
		});
    	return convertView;
    }

}
