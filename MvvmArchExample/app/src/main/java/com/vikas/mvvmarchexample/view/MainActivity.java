package com.vikas.mvvmarchexample.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vikas.mvvmarchexample.R;
import com.vikas.mvvmarchexample.adapter.ContactListAdapter;
import com.vikas.mvvmarchexample.model.Contact;
import com.vikas.mvvmarchexample.viewmodel.ContactViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private final String TITLE_ADD = "Add";
    private final String TITLE_EDIT = "Update";
    private final String ACTION_SAVE = "Save";
    private final String ACTION_CANCEL = "Cancel";

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
        mBtnFilteredContacts = findViewById(R.id.button_show_filtered);

        mBtnShowAllContacts.setChecked(true);

        mContactViewModel = new ViewModelProvider(this, viewModelFactory).get(ContactViewModel.class);
        mContactViewModel.getAllContacts().observe(this, contacts -> contactListAdapter.submitList(contacts));



        mBtnShowAllContacts.setOnClickListener(view -> {
            mBtnFilteredContacts.setChecked(false);
            contactListAdapter.submitList(mContactViewModel.getAllContacts().getValue());
        });

        mBtnFilteredContacts.setOnClickListener(view -> {
            mBtnShowAllContacts.setChecked(false);

            List<Contact> list = new ArrayList<>();
            contactListAdapter.submitList(list);
            /*mContactViewModel.fetchMatchingContacts(null, null).observe(this, contacts -> {

            });*/
        });

        FloatingActionButton floatingActionButton = findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(view -> showDialog(TITLE_ADD, new Contact.ContactBuilder().buildContact()));

       new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
           @Override
           public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
               return false;
           }

           @Override
           public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
               mContactViewModel.delete(contactListAdapter.getContactAt(viewHolder.getAdapterPosition()));
           }
       }).attachToRecyclerView(mRecyclerView);

       contactListAdapter.setOnClickListener(new ContactListAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(Contact contact) {
               showDialog(TITLE_EDIT, new Contact.ContactBuilder()
                       .setId(contact.getId())
                       .setName(contact.getName())
                       .setNumber(contact.getNumber())
                       .buildContact());
           }
       });
    }

    private void showDialog(String title, Contact contact){
        final View view = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        final EditText editTextName = view.findViewById(R.id.edit_text_name);
        final EditText editTextNumber = view.findViewById(R.id.edit_text_number);
        final TextView textViewError = view.findViewById(R.id.text_view_error);
        textViewError.setVisibility(View.GONE);
        if(contact.getName()!=null && contact.getNumber() != -1){
            editTextName.setText(contact.getName());
            editTextNumber.setText(String.valueOf(contact.getNumber()));
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(ACTION_SAVE, (dialogInterface, i) -> {
        });
        alertDialogBuilder.setNegativeButton(ACTION_CANCEL, (dialogInterface, i) -> {

        });

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view1 -> {
            if(editTextName.getText().toString().trim().isEmpty() || editTextNumber.getText().toString().trim().isEmpty()){
                textViewError.setVisibility(View.VISIBLE);
            } else{
                Contact.ContactBuilder contactBuilder = new Contact.ContactBuilder();
                contactBuilder.setName(editTextName.getText().toString());
                contactBuilder.setNumber(Long.parseLong(editTextNumber.getText().toString()));
                if(contact.getId() != -1)
                    contactBuilder.setId(contact.getId());

                if(title.equalsIgnoreCase(TITLE_ADD))
                    saveContact(contactBuilder.buildContact());
                else {
                    if(validateValuesModified(contact, contactBuilder.buildContact()))
                        updateContact(contactBuilder.buildContact());
                    else
                        showErrorMessage();
                }
                alertDialog.dismiss();
            }
        });
    }

    private boolean validateValuesModified(Contact contact1, Contact contact2){
        return !contact1.getName().equalsIgnoreCase(contact2.getName()) || contact1.getNumber() != contact2.getNumber();
    }

    private void saveContact(Contact contact){
        Date currentDate =  Calendar.getInstance().getTime();
        contact.setCreatedDate(currentDate);
        mContactViewModel.insert(contact);
    }

    private void updateContact(Contact contact){
        Date currentDate =  Calendar.getInstance().getTime();
        contact.setCreatedDate(currentDate);
        mContactViewModel.update(contact);
    }

    private void showErrorMessage(){
        Toast.makeText(this, "Can not update.Reason could be data is not changed!", Toast.LENGTH_SHORT).show();
    }
}