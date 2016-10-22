package com.example.shadow.foldermanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends Activity {


    Button btnThem,btnXoa,btnSua,btnXem;
    File root;
    private ArrayList<File> fileList = new ArrayList<File>();
    String path;
    String to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ánh xạ nút trên màn hình main
        btnThem = (Button) findViewById(R.id.btnThem);
        btnXoa = (Button)findViewById(R.id.btnXoa);
        btnSua = (Button)findViewById(R.id.btnSua);
        btnXem = (Button) findViewById(R.id.btnXem);
        //lấy nút gốc
        root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        //lấy danh sách các file trong nút gốc
        getfile(root);
        //sự kiện nút thêm
        addFolder();
        //sự kiện nút xóa
        removeFolder();
        //sự kiện nút sửa
        modifyFolder();
        //sự kiện nút xem
        watch();

    }

    private void watch() {
        btnXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển qua màn hình list view
                Intent intent = new Intent(MainActivity.this,listView.class);
                startActivity(intent);
            }
        });
    }

    private void modifyFolder() {
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = (LayoutInflater.from(MainActivity.this)).inflate(R.layout.user_modify,null);
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                alertBuilder.setView(view);
                final EditText userInput = (EditText)view.findViewById(R.id.filefrom);
                final EditText userInput2 = (EditText) view.findViewById(R.id.fileto);
                alertBuilder.setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        path = userInput.getText().toString();
                        to = userInput2.getText().toString();
                        if(path.equals("")||to.equals("")){
                            Toast.makeText(getApplicationContext(),"Không được nhập rỗng",Toast.LENGTH_LONG).show();
                            return;
                        }
                        File folder = new File(Environment.getExternalStorageDirectory().toString());
                        File from      = new File(folder, path);
                        File to        = new File(folder, userInput2.getText().toString() );
                        if(from.renameTo(to))
                        {
                            Toast.makeText(getApplicationContext(),"Thành công",Toast.LENGTH_LONG).show();
                        }
                        else Toast.makeText(getApplicationContext(),"Thất bại",Toast.LENGTH_LONG).show();

                    }
                });
                Dialog dialog = alertBuilder.create();
                dialog.show();

            }
        });
    }

    /*public ArrayList<String> GetFiles(String DirectoryPath) {
        ArrayList<String> MyFiles = new ArrayList<String>();
        File f = new File(DirectoryPath);

        f.mkdirs();
        File[] files = f.listFiles();
        if (files.length == 0)
            return null;
        else {
            for (int i=0; i<files.length; i++)
                MyFiles.add(files[i].getName());
        }

        return MyFiles;
    }*/

    private void removeFolder() {
    btnXoa.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View view = (LayoutInflater.from(MainActivity.this)).inflate(R.layout.user_input,null);
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
            alertBuilder.setView(view);
            final EditText userInput = (EditText)view.findViewById(R.id.userinput);
            alertBuilder.setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
             @Override
                public void onClick(DialogInterface dialog, int which) {
                    path = userInput.getText().toString();
                    if(path.equals("")){
                        Toast.makeText(getApplicationContext(),"Không được nhập rỗng",Toast.LENGTH_LONG).show();
                        return;
                    }
                    for(int i=0; i<fileList.size();i++)
                    {
                        if(path.equalsIgnoreCase(fileList.get(i).getName()))
                        {
                            File folder = new File(Environment.getExternalStorageDirectory().toString()+"/"+path);
                            folder.delete();

                            if(!folder.exists()){
                                Toast.makeText(getApplicationContext(),"Xóa thành công",Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                    }


                     Toast.makeText(getApplicationContext(),"Thất bại",Toast.LENGTH_LONG).show();


                }
            });
            Dialog dialog = alertBuilder.create();
            dialog.show();
        }
    });
}
private void addFolder()
{
    btnThem.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View view = (LayoutInflater.from(MainActivity.this)).inflate(R.layout.user_input,null);
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
            alertBuilder.setView(view);
            final EditText userInput = (EditText)view.findViewById(R.id.userinput);
            alertBuilder.setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    path = userInput.getText().toString();
                    if(path.equals("")){
                        Toast.makeText(getApplicationContext(),"Không được nhập rỗng",Toast.LENGTH_LONG).show();
                        return;
                    }
                    File folder = new File(Environment.getExternalStorageDirectory().toString()+"/"+path);
                    folder.mkdirs();
                    fileList.add(folder);
                    if(folder.exists()){
                        Toast.makeText(getApplicationContext(),"Thêm thành công",Toast.LENGTH_LONG).show();
                    }else{

                        Toast.makeText(getApplicationContext(),"Thất bại, bị trùng khóa.",Toast.LENGTH_LONG).show();
                    }
                }
            });
            Dialog dialog = alertBuilder.create();
            dialog.show();

        }
    });
}

private ArrayList<File> getfile(File dir) {
    File listfile[] = dir.listFiles();
    if(listfile != null && listfile.length>0)
    {
        for(int i=0; i<listfile.length; i++)
        {
            if(listfile[i].isDirectory())
            {
                fileList.add(listfile[i]);
                getfile(listfile[i]);
            }
            else
            {
                if (    listfile[i].getName().endsWith(".png")
                        || listfile[i].getName().endsWith(".jpg")
                        || listfile[i].getName().endsWith(".jpeg")
                        || listfile[i].getName().endsWith(".gif"))

                {
                    fileList.add(listfile[i]);
                }
            }
        }
    }
    return fileList;
}


}
