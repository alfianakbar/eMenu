package com.momo.emenu;

import com.momo.emenu.R;
import com.momo.model.meja;
import com.momo.xmlparser.MejaHandler;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

	

public class EmenuActivity extends Activity {
    
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ((Button)findViewById(R.id.buttonOK)).setOnClickListener(new tombol());
        
    }
    
    class tombol implements OnClickListener{

		public void onClick(View v) {
			MejaHandler m=new MejaHandler(EmenuActivity.this);
			m.deleteAllMeja();
			EditText nomor=(EditText)findViewById(R.id.nomor);
			EditText nama=(EditText)findViewById(R.id.nama);
			if(nama.getText().toString().matches("")||nomor.getText().toString().matches("")){
				final AlertDialog alertDialog = new AlertDialog.Builder(EmenuActivity.this).create();
				alertDialog.setTitle("E-Menu");
				alertDialog.setMessage("Maaf, Mohon Mengisi Semua Form.");
				alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int which) {
				      // here you can add functions
					   alertDialog.dismiss();
				   }
				});
				alertDialog.show();
			}
			else{
				try{
					Integer.parseInt(nomor.getText().toString());
					m.addMeja(new meja(nama.getText().toString(), nomor.getText().toString()));
					startActivity(new Intent(EmenuActivity.this, MenuActivity.class));
					EmenuActivity.this.finish();
				}catch (NumberFormatException e) {
					// TODO: handle exception
					final AlertDialog alertDialog = new AlertDialog.Builder(EmenuActivity.this).create();
					alertDialog.setTitle("E-Menu");
					alertDialog.setMessage("Maaf, Mohon Nomor Meja Diisi dengan Angka");
					alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
					   public void onClick(DialogInterface dialog, int which) {
					      // here you can add functions
						   alertDialog.dismiss();
					   }
					});
					alertDialog.show();
				}
				
			}
			
		}
    	
    }
}

