package com.onetoall.yjt;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onetoall.yjt.utils.GlideDisplay;

import java.util.List;

public class GoodAdapter extends BaseAdapter
{
    List<Good> list;
    Context context;

    public GoodAdapter (List<Good> list , Context context)
    {
        this.list = list ;
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return list == null ? 0 :list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = null;

        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_good_layout, null);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivCart     = (ImageView) convertView.findViewById(R.id.iv_cart);
        viewHolder.ivGood     = (ImageView) convertView.findViewById(R.id.iv_good);
        viewHolder.tvPrice    = (TextView)  convertView.findViewById(R.id.tv_price);
        viewHolder.tvOldPrice = (TextView)  convertView.findViewById(R.id.tv_old_price);
        viewHolder.tvGood     = (TextView)  convertView.findViewById(R.id.tv_good);

        GlideDisplay glideDisplay = new GlideDisplay(context);
        glideDisplay.displayImage(list.get(position).getFlowerImage(),viewHolder.ivGood);
        viewHolder.tvGood.setText(list.get(position).getFlowerName());
        viewHolder.tvPrice.setText(list.get(position).getNowPrice());
        viewHolder.tvOldPrice.setText(list.get(position).getOldPrice());
        viewHolder.tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        return null;
    }
    class ViewHolder
    {
        ImageView ivGood;
        TextView textView;
        TextView tvPrice;
        TextView tvGood;
        TextView tvOldPrice;
        ImageView ivCart;


    }
}
