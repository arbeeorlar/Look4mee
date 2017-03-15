package folaoyewole.look4mee.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import folaoyewole.look4mee.R;


/**
 * Created by sp_developer on 10/10/16.
 */
public class AdditionalSkills_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private String title;
    private List<String> list = new ArrayList<>();
    private onEditTextClickListener listener;

    public interface onEditTextClickListener {
        void onClick(int position);
    }

    public AdditionalSkills_Adapter(Context c) {

        context = c;
        //this.title = title;
    }


    public class AdditionalSkillViewHoler extends RecyclerView.ViewHolder{

        EditText mEditText;
        ImageButton remove_skill_btn;
        View v;

        public AdditionalSkillViewHoler(View itemView) {
            super(itemView);

            v = itemView;
            mEditText = (EditText)itemView.findViewById(R.id.additionalskill_category);
            mEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int pos = getAdapterPosition();

                    notifyItemInserted(pos);

                    Binder(listener, pos);

                }
            });

            remove_skill_btn = (ImageButton)itemView.findViewById(R.id.remove_skill);
            remove_skill_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                }
            });


        }

        public void Binder(onEditTextClickListener listener, int position){
            listener.onClick(position);

            System.out.println("Inside edittext");
        }

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.additionalskills_row_item, parent, false);

        return new AdditionalSkillViewHoler(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        AdditionalSkillViewHoler additionalSkillViewHoler = (AdditionalSkillViewHoler)holder;

    }

    @Override
    public int getItemCount() {
        return 0;
    }



}
