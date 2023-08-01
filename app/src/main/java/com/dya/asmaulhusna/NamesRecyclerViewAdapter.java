package com.dya.asmaulhusna;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class NamesRecyclerViewAdapter extends RecyclerView.Adapter<NamesRecyclerViewAdapter.NamesViewHolder> implements Filterable {

    List<NamesItem> AllahName;
    List<NamesItem> searchText;
    Context context;

    public NamesRecyclerViewAdapter( Context context,List<NamesItem> allahName) {
        AllahName = allahName;
        this.context = context;
        this.searchText = new ArrayList<>(allahName);
    }

    @NonNull
    @Override
    public NamesRecyclerViewAdapter.NamesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.names_row,parent,false);
        return new NamesViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NamesRecyclerViewAdapter.NamesViewHolder holder, int position) {

        NamesItem  itemData = AllahName.get(position);
        holder.NameEnTv.setText(itemData.getName_en());
        holder.NameTv.setText(itemData.getName());
        //DatabaseConnect databaseConnectClass = new DatabaseConnect(context);

       /**
        String iSurat=itemData.getName();
        String iId=itemData.getId();
        String iLink = itemData.getaLink();
        mydbClass.readAllAyahData(iId);
        holder.constraintLayout.setOnClickListener(v -> {

        Intent intent =new Intent(context,SuratView.class);
        intent.putExtra("sura",iSurat);
        intent.putExtra("id",iId);
        intent.putExtra("link",iLink);
        intent.putExtra("scrollPosition", "0");
        context.startActivity(intent);
        }); */

        holder.NameRowLayout.setOnClickListener(v -> {
        BottomSheetDialog dialog = new BottomSheetDialog(v.getContext(),R.style.BottomSheetStyle);
        dialog.setContentView(R.layout.bottom_sheet_dialog_layout);
        ImageButton btnCopyAll = dialog.findViewById(R.id.btnCopyAll);
        TextView Name_ar= dialog.findViewById(R.id.Name_ar);
        TextView kurdish= dialog.findViewById(R.id.kurdish);
        TextView english= dialog.findViewById(R.id.english);
        TextView persian= dialog.findViewById(R.id.persian);
        TextView turkish= dialog.findViewById(R.id.turkish);
        TextView spanish= dialog.findViewById(R.id.spanish);
        TextView french = dialog.findViewById(R.id.french);
        TextView chinese= dialog.findViewById(R.id.chinese);
        TextView japanese= dialog.findViewById(R.id.japanese);
        TextView korean= dialog.findViewById(R.id.korean);
        TextView hindi= dialog.findViewById(R.id.hindi);
        TextView russian= dialog.findViewById(R.id.russian);

             Name_ar.setText(itemData.getName());
             kurdish.setText(itemData.getKurdish());
             english.setText(itemData.getEnglish());
             persian.setText(itemData.getPersian());
             turkish.setText(itemData.getTurkish());
             spanish.setText(itemData.getSpanish());
             french.setText(itemData.getFrench());
             chinese.setText(itemData.getChinese());
             japanese.setText(itemData.getJapanese());
             korean.setText(itemData.getKorean());
             hindi.setText(itemData.getHindi());
             russian.setText(itemData.getRussian());


             btnCopyAll.setOnClickListener(v1 -> {

                 ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(
                         Context.CLIPBOARD_SERVICE
                 );

                 String textClip ="Arabic\n"+  Name_ar.getText()+"\n"+"\n"+
                         "Kurdish\n"+ kurdish.getText()+"\n"+"\n"+
                         "English\n"+  english.getText()+"\n"+"\n"+
                         "Persian\n"+persian.getText()+"\n"+"\n"+
                         "Turkish\n"+turkish.getText()+"\n"+"\n"+
                         "Spanish\n"+spanish.getText()+"\n"+"\n"+
                         "French\n"+french.getText()+"\n"+"\n"+
                         "Chinese\n"+chinese.getText()+"\n"+"\n"+
                         "Japanese\n"+japanese.getText()+"\n"+"\n"+
                         "Korean\n"+korean.getText()+"\n"+"\n"+
                         "Indian\n"+hindi.getText()+"\n"+"\n"+
                         "Russian\n"+ russian.getText()+"\n"+"\n";

                 ClipData clipData = ClipData.newPlainText("text", textClip +"\n"+"\n"+"#Asmaul_Husna_application"+"\n"+"https://play.google.com/store/apps/details?id=com.dya.asmaulhusna");
                 clipboardManager.setPrimaryClip(clipData);
                 Toast.makeText(context, "Copy to Clipboard", Toast.LENGTH_SHORT).show();

             });


            dialog.show();



        });

    }

    @Override
    public int getItemCount() {
        return AllahName.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List <NamesItem> filterList = new ArrayList<>();

            if (charSequence.toString().isEmpty()){
                filterList.addAll(searchText);
            }else {

                for (NamesItem text : searchText ){
                    if (text.getName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filterList.add(text);
                    } else if (text.getName_en().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filterList.add(text);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;

            return filterResults;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            AllahName.clear();
            AllahName.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };


    public static class NamesViewHolder extends RecyclerView.ViewHolder {

        TextView NameEnTv , NameTv;
        LinearLayout NameRowLayout;

        public NamesViewHolder(@NonNull View itemView) {
            super(itemView);

            NameEnTv = itemView.findViewById(R.id.NameEn);
            NameTv = itemView.findViewById(R.id.name);
            NameRowLayout = itemView.findViewById(R.id.NameRowLayout);

        }
    }
}
