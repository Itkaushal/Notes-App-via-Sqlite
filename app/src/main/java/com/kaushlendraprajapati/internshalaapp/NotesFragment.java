package com.kaushlendraprajapati.internshalaapp;

import static com.kaushlendraprajapati.internshalaapp.DatabaseClass.Col_1;
import static com.kaushlendraprajapati.internshalaapp.DatabaseClass.Col_2;
import static com.kaushlendraprajapati.internshalaapp.DatabaseClass.Col_3;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import com.kaushlendraprajapati.internshalaapp.databinding.FragmentNotesBinding;

import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment {

    RecyclerView recyclerView;
    AdapterClass notesAdapter;
    List<ModalClass> notes = new ArrayList<>();
    FragmentNotesBinding notesBinding;

    DatabaseClass database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        notesBinding = FragmentNotesBinding.inflate(inflater, container, false);
        return notesBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = notesBinding.recyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize database here
        database = new DatabaseClass(requireContext());

        // Create adapter after initializing the database
        notesAdapter = new AdapterClass(notes, database, requireContext());
        recyclerView.setAdapter(notesAdapter);

        notesBinding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = notesBinding.etTitle.getText().toString();
                String content = notesBinding.etContent.getText().toString();
                if (!title.isEmpty() && !content.isEmpty()) {
                    boolean mydata = database.isInsert(title, content);
                    if (mydata) {
                        Toast.makeText(requireContext(), "Notes Added Successfully", Toast.LENGTH_SHORT).show();
                        // Clear EditText fields after adding note
                        notesBinding.etTitle.setText("");
                        notesBinding.etContent.setText("");
                        // Refresh notes list
                        notesFetch();
                    } else {
                        Toast.makeText(requireContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Enter Notes!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // data fetching from database
        notesFetch();
    }

    private void notesFetch() {
        Cursor cursor = database.readData();
        if (cursor.getCount() == 0) {
            Toast.makeText(requireContext(), "Data null", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(Col_1);
                int index2 = cursor.getColumnIndex(Col_2);
                int index3 = cursor.getColumnIndex(Col_3);
                int id = cursor.getInt(index);
                String title = cursor.getString(index2);
                String content = cursor.getString(index3);

                ModalClass modalClass = new ModalClass(id, title, content);
                notes.add(modalClass);
            }
        }
        cursor.close();
    }
}
