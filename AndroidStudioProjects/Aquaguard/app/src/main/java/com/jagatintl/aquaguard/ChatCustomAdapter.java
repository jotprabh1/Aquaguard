package com.jagatintl.aquaguard;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Prabhjot Singh on 19-10-2016.
 */
public class ChatCustomAdapter extends BaseAdapter{
    LayoutInflater inflater=null;
    Context context=null;
    public ChatCustomAdapter(ChatActivity chatActivity)
    {
        context=chatActivity;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return ChatActivity.chatList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=inflater.inflate(R.layout.chat_view,null);
        TextView sender=(TextView)convertView.findViewById(R.id.textViewSender);
        TextView message=(TextView)convertView.findViewById(R.id.textViewMessage);
        String email;
        if(!ChatActivity.chatList.get(position).get(ChatActivity.TAG_EMAIL).equals(Login.eml)) {
            email = ChatActivity.chatList.get(position).get(ChatActivity.TAG_EMAIL) + ":";
            sender.setGravity(Gravity.START);
            message.setGravity(Gravity.START);
        }else
        {
            email="Me:";
            sender.setGravity(Gravity.END);
            message.setGravity(Gravity.END);
        }
        sender.setText(email);
        message.setText(ChatActivity.chatList.get(position).get(ChatActivity.TAG_MESSAGE));

        return convertView;
    }
}
