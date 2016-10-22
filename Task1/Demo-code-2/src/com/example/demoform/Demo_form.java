package com.example.demoform;

import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Demo_form extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo_form);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.demo_form, menu);
		// Tạo một Preferences để lưu dữ liệu
		ref = this.getSharedPreferences("com.demoform", Context.MODE_PRIVATE);
		ref.edit().clear().commit();
		addEvent();
		return true;
	}
	// Khai báo các biến
	SharedPreferences ref;
	Button btnAdd; 
	Button btnPrint;
	EditText txtName;
	EditText txtId;
	// Tao sự kiện
	private void addEvent() {
	txtName = (EditText) this.findViewById(R.id.txtName);
	txtId = (EditText) this.findViewById(R.id.txtId);
	btnAdd = (Button) this.findViewById(R.id.btnAdd);
	btnPrint = (Button) this.findViewById(R.id.Print);
	// Thêm dữ liệu 
	btnAdd.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			ref.edit().putString(txtId.getText().toString(), txtName.getText().toString()).commit();
			txtId.setText("");
			txtName.setText("");
		}
	});
	// In dữ liệu ra ngoài random ngẫu nhiên 
	btnPrint.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// Lấy số lượng dữ liệu được lưu
			int maxRandom = ref.getAll().size();
			// Kiểm tra dữ liệu
			if(maxRandom==0)
			{
				Toast.makeText(arg0.getContext(),"Ko co du lieu", Toast.LENGTH_LONG).show();
				return;
			}
			Random r = new Random();
			int term=-1;
			String result="";
			while(true)
			{
				// lấy giá trị random từ [0,maxRandom)
				int a = r.nextInt(maxRandom);
				if(term==a)
				{
					continue;
				}
				// ref.getAll() lấy map của tất cả record đang lưu trữ ra
				// ketSet() lấy danh sách của key ra
				// toArray chuyển danh sách đó qua array để  lấy vị trí a của nó
				String keym = ref.getAll().keySet().toArray()[a].toString();
				result += "ID: "+ keym + "; Name: "+ ref.getString(keym, "NULL");
				result += "\r\n";
				if(term!=-1)
					break;
				term = a;
			}
			// Hiện dữ liệu
			Toast.makeText(arg0.getContext(),result, Toast.LENGTH_LONG).show();
		}
	});
	}

}
