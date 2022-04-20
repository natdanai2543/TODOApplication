package th.ac.kku.cis.lab.todoapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {


    private List<Data_activity> dataActivityList;
    private Activity context;
    private DataBase database;

    AlertDialog.Builder builder;

    public MainAdapter(List<Data_activity> dataActivityList, Activity context) {
        this.dataActivityList = dataActivityList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {//int main data
        Data_activity dataActivity = dataActivityList.get(position);
        database= DataBase.getInstance(context);
        holder.textView.setText(dataActivity.getText());

        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data_activity d= dataActivityList.get(holder.getAdapterPosition());
                int sID = d.getID();
                String sText = d.getText();

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_update);

                int width= WindowManager.LayoutParams.MATCH_PARENT;
                int height=WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.getWindow().setLayout(width,height);
                dialog.show();

                EditText editText=dialog.findViewById(R.id.edit_text);
                Button btUpdate=dialog.findViewById(R.id.bt_update);

                editText.setText(sText);

                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                        String uText=editText.getText().toString().trim();

                        database.mainDao().upate(sID,uText);

                        dataActivityList.clear();
                        dataActivityList.addAll(database.mainDao().getAll());
                        notifyDataSetChanged();


                    }
                });




            }
        });

        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder= new AlertDialog.Builder(v.getContext());
                builder.setMessage("คุณต้องการลบใช่ไหม ??")
                        .setCancelable(false)
                        .setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Data_activity d= dataActivityList.get(holder.getAdapterPosition());

                                database.mainDao().delete(d);

                                int position = holder.getAdapterPosition();
                                dataActivityList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, dataActivityList.size());
                            }
                        })
                        .setNegativeButton("ไม่", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("DeleteConfirmation");
                alert.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataActivityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView btEdit,btDelete;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.text_view);
            btEdit=itemView.findViewById(R.id.bt_edit);
            btDelete=itemView.findViewById(R.id.bt_delete);


        }
    }
}
