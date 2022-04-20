package th.ac.kku.cis.lab.todoapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button btAdd;
    RecyclerView recyclerView;

    List<Data_activity> dataActivityList =new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    DataBase database;

    MainAdapter mainAdapter;

    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText=findViewById(R.id.edit_text);
        btAdd=findViewById(R.id.bt_add);
        recyclerView=findViewById(R.id.recycler_view);



        database= DataBase.getInstance(this);

        dataActivityList =database.mainDao().getAll();

        linearLayoutManager =new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);

        mainAdapter=new MainAdapter(dataActivityList,MainActivity.this);

        recyclerView.setAdapter(mainAdapter);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sText=editText.getText().toString().trim();
                if(!sText.equals("")){

                    Data_activity dataActivity =new Data_activity();

                    dataActivity.setText(sText);

                    database.mainDao().insert(dataActivity);

                    editText.setText("");

                    dataActivityList.clear();
                    Toast.makeText(MainActivity.this,"เพิ่มข้อมูลสำเร็จแล้ว!!",Toast.LENGTH_LONG).show();

                    dataActivityList.addAll(database.mainDao().getAll());
                    mainAdapter.notifyDataSetChanged();

                }else{
                    builder= new AlertDialog.Builder(MainActivity.this);

                    builder.setMessage("กรุณากรอกข้อมูล")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.setTitle("InvalidActionAlert");
                    alert.show();
                }
            }
        });

    }
}