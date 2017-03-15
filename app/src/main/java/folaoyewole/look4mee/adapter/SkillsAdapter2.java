package folaoyewole.look4mee.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import folaoyewole.look4mee.R;
import folaoyewole.look4mee.Model.Skill;

/**
 * Created by sp_developer on 11/2/16.
 */
public class SkillsAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    LayoutInflater inflater;
    static ArrayList<Skill> Item;

    public SkillsAdapter2(Context context, ArrayList<Skill> Item) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.Item = Item;

    }


    public class SkillsViewHolder extends RecyclerView.ViewHolder{

        View view;
        TextView skill_name;
        ImageButton remove_image_btn;

        public SkillsViewHolder(View itemView) {
            super(itemView);

            view = itemView;
            skill_name = (TextView)itemView.findViewById(R.id.skill_name_txt);
            remove_image_btn = (ImageButton)itemView.findViewById(R.id.remove_skill);
            remove_image_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();

                    try{

                        Item.remove(pos);
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
        View view = LayoutInflater.from(this.context).inflate(R.layout.skills_row_item, parent, false);
        return new SkillsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof SkillsViewHolder){
            SkillsViewHolder skillsViewHolder =  (SkillsViewHolder)holder;

            skillsViewHolder.skill_name.setText(Item.get(position).getSkillName());
        }

    }

    @Override
    public int getItemCount() {
        return Item.size();
    }
}

