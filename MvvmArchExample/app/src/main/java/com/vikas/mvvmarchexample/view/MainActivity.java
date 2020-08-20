package com.vikas.mvvmarchexample.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vikas.mvvmarchexample.R;
import com.vikas.mvvmarchexample.adapter.ContactListAdapter;
import com.vikas.mvvmarchexample.model.Contact;
import com.vikas.mvvmarchexample.repository.ContactRepository;
import com.vikas.mvvmarchexample.viewmodel.ContactViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ContactViewModel mContactViewModel;
    private RecyclerView mRecyclerView;
    private RadioButton mBtnShowAllContacts, mBtnFilteredContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        final ContactListAdapter contactListAdapter = new ContactListAdapter();
        mRecyclerView.setAdapter(contactListAdapter);

        mBtnShowAllContacts = findViewById(R.id.button_show_all);
        mBtnShowAllContacts.setOnClickListener(view -> {

        });

        mBtnShowAllContacts.setChecked(true);

        mBtnFilteredContacts = findViewById(R.id.button_show_filtered);
        mBtnFilteredContacts.setOnClickListener(view -> {

        });

        mContactViewModel = new ViewModelProvider(this, viewModelFactory).get(ContactViewModel.class);
        mContactViewModel.getAllContacts().observe(this, contacts -> contactListAdapter.submitList(contacts));

        mContactViewModel.fetchMatchingContacts(null, null).observe(this, contacts -> {

        });

        FloatingActionButton floatingActionButton = findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(view -> showDialog("Add", null, null));
    }

    private void showDialog(String title, String name, String number){
        final View view = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        final EditText editTextName = view.findViewById(R.id.edit_text_name);
        final EditText editTextNumber = view.findViewById(R.id.edit_text_number);
        final TextView textViewError = view.findViewById(R.id.text_view_error);
        textViewError.setVisibility(View.GONE);
        if(name!=null && number!=null){
            editTextName.setText(name);
            editTextNumber.setText(number);
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Save", (dialogInterface, i) -> {
        });
        alertDialogBuilder.setNegativeButton("Cancel", (dialogInterface, i) -> {

        });

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view1 -> {
            if(editTextName.getText().toString().trim().isEmpty() || editTextNumber.getText().toString().trim().isEmpty()){
                textViewError.setVisibility(View.VISIBLE);
            } else{
                saveContact(editTextName.getText().toString(), editTextNumber.getText().toString());
                alertDialog.dismiss();
            }
        });
    }

    private void saveContact(String name, String number){
        Contact contact = new Contact(name, Long.parseLong(number));
        Date currentDate =  Calendar.getInstance().getTime();
        contact.setCreatedDate(currentDate);
        mContactViewModel.insert(contact);
    }

    private void showToast(){
        //Toast.makeText(MainActivity.this, "Please enter name & number!", Toast.LENGTH_SHORT).setGravity().show();
    }
}