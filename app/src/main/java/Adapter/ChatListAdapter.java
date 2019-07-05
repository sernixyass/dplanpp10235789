package Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.carpoolingappv1.HomeFragment;
import com.example.carpoolingappv1.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;
import Model.InstantMessage;

public class ChatListAdapter  extends BaseAdapter {
    private Activity mActivity ;
    private DatabaseReference mDatabaseReference ;
    private String mDisplayName ;
    private ArrayList <DataSnapshot> mDataSnapshots ;
    //private LinearLayout llayout = mActivity.findViewById(R.id.singleMessageContainer);
    //singleMessageContainer
    private ChildEventListener mListener = new ChildEventListener() {

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            mDataSnapshots.add(dataSnapshot);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    };

    public ChatListAdapter(Activity activity, DatabaseReference ref, String name) {

        mActivity = activity;
        mDisplayName = name;
        mDatabaseReference = ref;
        mDatabaseReference.addChildEventListener(mListener);

        mDataSnapshots = new ArrayList<>();
    }

    private static class ViewHolder{
        TextView authorName;
        TextView body;
        LinearLayout.LayoutParams params;
        TextView mTime ;
    }

    @Override
    public int getCount() {
        return mDataSnapshots.size();
    }

    @Override
    public InstantMessage getItem(int position) {

        DataSnapshot snapshot = mDataSnapshots.get(position);
        return snapshot.getValue(InstantMessage.class);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //convertView=  inflater.inflate(R.layout.fragment_chat,parent,false) ;
            convertView = inflater.inflate(R.layout.chat_msg_row, parent, false);

            final ViewHolder holder = new ViewHolder();
            holder.authorName = convertView.findViewById(R.id.author);
            holder.body = convertView.findViewById(R.id.message);
            holder.mTime = convertView.findViewById(R.id.textView_Time);
            holder.params = (LinearLayout.LayoutParams) holder.authorName.getLayoutParams();
            convertView.setTag(holder);

        }

        final InstantMessage message = getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();

        //holder.params.gravity = Gravity.CENTER;


        //String author = message.getAuthor() ;
        holder.authorName.setText(message.getAuthor());

        //String msg =message.getMessage() ;
        holder.body.setText(message.getMessage());

        holder.mTime.setText(message.getTime());


        boolean isMe = message.getAuthor().equals(mDisplayName);
        setChatRowAppearance(isMe, holder);

        return convertView;
    }

    private void setChatRowAppearance(boolean isItMe, ViewHolder holder) {

        if (isItMe) {
            holder.authorName.setTextColor(Color.GREEN);
            holder.params.gravity = Gravity.END;
//            holder.body.setBackgroundResource(R.drawable.right_msg);

        } else {

            holder.authorName.setTextColor(Color.BLUE);
            holder.params.gravity = Gravity.START;
//            holder.body.setBackgroundResource(R.drawable.left_msg);
            //llayout.setBackgroundResource(R.drawable.left_msg);

        }


        holder.authorName.setLayoutParams(holder.params);
        holder.body.setLayoutParams(holder.params);
        holder.mTime.setLayoutParams(holder.params);

    }


    public void cleanup() {
        mDatabaseReference.removeEventListener(mListener);
    }
}
