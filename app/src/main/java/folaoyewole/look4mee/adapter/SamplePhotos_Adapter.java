package folaoyewole.look4mee.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

import folaoyewole.look4mee.R;

/**
 * Created by sp_developer on 10/8/16.
 */
public class SamplePhotos_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Bitmap> photoItems = new ArrayList<>();

    public SamplePhotos_Adapter(Context context, ArrayList<Bitmap> photos) {
        this.context = context;
        this.photoItems = photos;
    }


    private class SamplePhotosViewHolder extends RecyclerView.ViewHolder{

        ImageView photo;
        ImageButton remove_photo;

        public SamplePhotosViewHolder(View itemView) {
            super(itemView);

            photo = (ImageView)itemView.findViewById(R.id.editskills_sample_photo);
            remove_photo = (ImageButton)itemView.findViewById(R.id.editskills_remove_sample_photo);
            remove_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int pos = getAdapterPosition();

                    try{
                        photoItems.remove(pos);
                        notifyItemRemoved(pos);

                    }catch (ArrayIndexOutOfBoundsException e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.context).inflate(R.layout.sample_jobs_photo_row, parent, false);

        return new SamplePhotosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof SamplePhotosViewHolder){
            SamplePhotosViewHolder samplePhotosViewHolder = (SamplePhotosViewHolder)holder;

            samplePhotosViewHolder.photo.setImageBitmap(photoItems.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return photoItems.size();
    }
}
