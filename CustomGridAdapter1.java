package com.example.lenovo.pecs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomGridAdapter1  extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Recipe> ImageList;
//    List ImageList= new ArrayList();

    public CustomGridAdapter1(Context aContext, int layout,ArrayList<Recipe> ImageList) {
        this.context = aContext;
        this.ImageList = ImageList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return ImageList.size();
    }

    @Override
    public Object getItem(int position) {
        return ImageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(Recipe general) {
        ImageList.add(general);
    }

    private class ViewHolder {
        TextView Thing;
        ImageView ThingImage;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
//            convertView = layoutInflater.inflate(R.layout.gridgeneral, null);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.image_general, null);
            holder.ThingImage = (ImageView) convertView.findViewById(R.id.imageView_flag1);
            holder.Thing = (TextView) convertView.findViewById(R.id.thing1);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        General general = ImageList.get(position);
        Recipe general=(Recipe) this.getItem(position);

        holder.Thing.setText(general.getName());
        byte[] thingName = general.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(thingName, 0, thingName.length);
        holder.ThingImage.setImageBitmap(bitmap);

        return convertView;
    }

}
