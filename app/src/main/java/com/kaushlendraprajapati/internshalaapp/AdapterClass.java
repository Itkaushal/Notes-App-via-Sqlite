package com.kaushlendraprajapati.internshalaapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.Viewholder> {

   private List<ModalClass> notes;
   private Context context;
   DatabaseClass database;

    public AdapterClass(List<ModalClass> notes,DatabaseClass database,Context context) {
        this.notes = notes;
        this.database=database;
        this.context=context;
    }

    @NonNull
    @Override
    public AdapterClass.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_layout,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClass.Viewholder holder, @SuppressLint("RecyclerView") int position) {

        holder.title.setText(notes.get(position).getTitle());
        holder.content.setText(notes.get(position).getContent());

        // edit image button click
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (database != null) {
                    int noteId = notes.get(position).getId();
                    String newTitle = holder.title.getText().toString();
                    String newContent = holder.content.getText().toString();
                    boolean isUpdated = database.isUpdate(String.valueOf(noteId), newTitle, newContent);
                    if (isUpdated) {
                        notes.get(position).setTitle(newTitle);
                        notes.get(position).setContent(newContent);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Notes Updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Failed to Update Notes!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle the case when database is null
                    Toast.makeText(context, "Database is null", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // delete image button click

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (database!=null){
                    int noteId= notes.get(position).getId();
                    boolean deleteNote=database.deleteNote(String.valueOf(noteId));
                    if (deleteNote){
                       notes.remove(position);
                       notifyDataSetChanged();
                        Toast.makeText(context, "Note Deleted Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context, "note not deleted!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(context, "database is null!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView title;
        TextView content;
        ImageView deleteButton,editButton;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.tvTitle);
            content=itemView.findViewById(R.id.tvContent);
            deleteButton=itemView.findViewById(R.id.deleteButton);
            editButton=itemView.findViewById(R.id.editButton);
        }
    }
}
