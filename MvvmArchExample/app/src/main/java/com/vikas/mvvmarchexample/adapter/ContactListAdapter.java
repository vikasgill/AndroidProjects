package com.vikas.mvvmarchexample.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.vikas.mvvmarchexample.R;
import com.vikas.mvvmarchexample.model.Contact;

public class ContactListAdapter extends ListAdapter<Contact, ContactListAdapter.ContactViewHolder> {

    private OnItemClickListener mListener;

    public ContactListAdapter() {
        super(DIFFUTIL_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Contact> DIFFUTIL_CALLBACK = new DiffUtil.ItemCallback<Contact>() {
        @Override
        public boolean areItemsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
            return oldItem.getName().equals(newItem.getName()) && oldItem.getNumber() == newItem.getNumber();
        }
    };

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_view, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = getItem(position);

        holder.mName.setText(contact.getName());
        holder.mContactNumber.setText(String.valueOf(contact.getNumber()));
    }

    class ContactViewHolder extends RecyclerView.ViewHolder{

        private TextView mName, mContactNumber;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.text_name);
            mContactNumber = itemView.findViewById(R.id.text_contact_number);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    mListener.onItemClick(getContactAt(position));
                }
            });
        }
    }

    public Contact getContactAt(int position){
        return getItem(position);
    }

    public interface OnItemClickListener{
        void onItemClick(Contact contact);
    }

    public void setOnClickListener(OnItemClickListener listener){
        mListener = listener;
    }
}
