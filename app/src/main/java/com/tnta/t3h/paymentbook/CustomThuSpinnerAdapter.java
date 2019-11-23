package com.tnta.t3h.paymentbook;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tnta.t3h.paymentbook.model.ThuKind;

import java.util.ArrayList;

/**
 * Created by edunetjsc on 7/9/17.
 */

public class CustomThuSpinnerAdapter extends ArrayAdapter<ThuKind> {

    Context context;
    int layout;
    ArrayList<ThuKind> arrayList;
    int[] img_array;

    public CustomThuSpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<ThuKind> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layout = resource;
        this.arrayList = objects;
        img_array = new int[]{R.drawable.luong, R.drawable.tietkiem, R.drawable.thu_khac};
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Nullable
    @Override
    public ThuKind getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayList.get(position).getId();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ThuKind thu = getItem(position);
        final int pos=position;
        TextView textView_spinner;
        ImageView imageView_spinner;
        View v = convertView;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        v = mInflater.inflate(layout, null);

        textView_spinner = (TextView) v.findViewById(R.id.textView_spinner);
        imageView_spinner = (ImageView) v.findViewById(R.id.imageView_spinner);

        textView_spinner.setText(thu.getName());
        imageView_spinner.setImageResource(img_array[thu.getId()-1]);

        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ThuKind thu = getItem(position);
        final int pos=position;
        TextView textView_spinner;
        ImageView imageView_spinner;
        View v = convertView;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        v = mInflater.inflate(layout, null);

        textView_spinner = (TextView) v.findViewById(R.id.textView_spinner);
        imageView_spinner = (ImageView) v.findViewById(R.id.imageView_spinner);

        textView_spinner.setText(thu.getName());
        imageView_spinner.setImageResource(img_array[thu.getId()-1]);

        return v;
    }
}
