package com.example.listplus;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class DetailForm extends Activity {
	EditText name = null;
	EditText address = null;
	EditText notes = null;
	RadioGroup types = null;
	RestaurantSQLiteHelper helper = null;
	String restaurantId = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_form);
		
		helper = new RestaurantSQLiteHelper(this);
		
		name = (EditText)findViewById(R.id.name);
		address = (EditText)findViewById(R.id.addr);
		notes = (EditText)findViewById(R.id.notes);
		types = (RadioGroup)findViewById(R.id.types);
		
		Button save = (Button)findViewById(R.id.save);
		save.setOnClickListener(onSave);
		System.out.println("out load");
		restaurantId = getIntent().getStringExtra(MainActivity.ID_EXTRA);
		if (restaurantId != null) {
			load();
		}
	}
	
	public void onDestroy() {
		super.onDestroy();
		helper.close();
	}
	
	private void load() {
		Cursor c = helper.getById(restaurantId);
		
		c.moveToFirst();
		name.setText(helper.getName(c));
		address.setText(helper.getAddress(c));
		notes.setText(helper.getNotes(c));
		
		if (helper.getType(c).equals("sit_down")) {
			types.check(R.id.sit_down);
		}
		else if (helper.getType(c).equals("take_out")) {
			types.check(R.id.take_out);
		}
		else {
			types.check(R.id.delivery);
		}
		c.close();
	}
	
private View.OnClickListener onSave = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String type = null;
			
			switch (types.getCheckedRadioButtonId())
			{
			case R.id.sit_down:
				type = "sit_down";
				break;
			case R.id.take_out:
				type = "take_out";
				break;
			case R.id.delivery:
				type = "delivery";
				break;
			}
			if (restaurantId == null) {
				helper.insert(name.getText().toString(),address.getText().toString(), type,notes.getText().toString());
				System.out.println("Insert");
			}
			else {
				helper.update(restaurantId, name.getText().toString(), address.getText().toString(), type, notes.getText().toString());
				System.out.println("Update");
			}
			Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show(); 
			finish();
		}
	};
}
