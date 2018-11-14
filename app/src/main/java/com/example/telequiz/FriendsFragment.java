package com.example.telequiz;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class FriendsFragment extends Fragment {

    List<String> friendsName = new ArrayList<String>();
    List<String> friendsPhoneNumber = new ArrayList<String>();
    List<String> friendsDpImage = new ArrayList<String>();

    View rootView;
    ImageView profilePicImageView;

    Cursor contactCursor;

    public FriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        contactCursor = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        getContacts();

        ArrayAdapter adapter  = new ArrayAdapter<String>(getActivity(), R.layout.friend_list, R.id.title, friendsName);

        ListView listView = (ListView) rootView.findViewById(R.id.friend_data_list);
        listView.setAdapter(adapter);

        return rootView;
    }

    private void getContacts() {
        while (contactCursor.moveToNext())
        {
            String name=contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            friendsName.add(name);
            friendsPhoneNumber.add(phoneNumber);
        }
        contactCursor.close();
        //textView.setText(allContacts);
    }

    public void zoomProfilePic() {

    }
}


