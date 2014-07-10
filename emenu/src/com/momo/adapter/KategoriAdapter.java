package com.momo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.momo.emenu.R;
import com.momo.emenu.R.id;

import com.momo.model.kategori;
import com.momo.util.DrawableManager;
import com.momo.xmlparser.constant;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class KategoriAdapter extends ArrayAdapter<kategori>{
	private LayoutInflater mInflater;
	private ArrayList<kategori> mList;
    private Context mCon;
    private int mViewResourceId;
    private DrawableManager manager=new DrawableManager();
	public KategoriAdapter(Context context, int textViewResourceId,ArrayList<kategori> list) {
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
    public kategori getItem(int position) {
            return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
            return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
    	convertView = mInflater.inflate(mViewResourceId, null);
    	TextView nama=(TextView)convertView.findViewById(R.id.textkategori);
    	nama.setText(mList.get(position).getNama_kategori());
    	
    	//ImageView icon=(ImageView)convertView.findViewById(R.id.iconkategori);
    	//manager.fetchDrawableOnThread(constant.IMG_URL+mList.get(position).getIcon(), icon);
    	return convertView;
    }

}
