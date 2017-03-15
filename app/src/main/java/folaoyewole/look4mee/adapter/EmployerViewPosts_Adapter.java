package folaoyewole.look4mee.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;

import folaoyewole.look4mee.R;
import folaoyewole.look4mee.Model.PostEmployer;
import folaoyewole.look4mee.Utilities.MySpannable;

/**
 * Created by sp_developer on 2/6/17.
 */
public class EmployerViewPosts_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<PostEmployer> Items;
    ColorGenerator generator = ColorGenerator.MATERIAL; // for generating random colors


    private static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
                else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
                else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }


    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {

            ssb.setSpan(new MySpannable(false){
                @Override
                public void onClick(View widget) {

                    tv.setLayoutParams(tv.getLayoutParams());
                    tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                    tv.invalidate();

                    /*

                     //... To allow Read More and Read Less ...//

                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, -2, "..Read Less", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, 4, "..Read More", true);
                    }
                     */

                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }

    public EmployerViewPosts_Adapter(Context context, ArrayList<PostEmployer> items) {
        this.context = context;
        this.Items = items;
    }

    public class EmployerViewPostsViewHolder extends RecyclerView.ViewHolder {

        TextView Desc;
        TextView uploadTime;
        ImageView imageLetter;
        TextView jobTitle;

        //TextView readmore;
        View v;

        public EmployerViewPostsViewHolder(View itemView) {
            super(itemView);

            v = itemView;
            Desc = (TextView) itemView.findViewById(R.id.job_desc);
            uploadTime = (TextView)itemView.findViewById(R.id.upload_time);
            imageLetter = (ImageView)itemView.findViewById(R.id.item_letter);
            jobTitle = (TextView)itemView.findViewById(R.id.jobTitle);

           // makeTextViewResizable(Desc, 4, "..Read More", true);

        }


    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_posts_employer_row, parent, false);

        return new EmployerViewPostsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof EmployerViewPostsViewHolder){

            final EmployerViewPostsViewHolder employerViewPostsViewHolder =  (EmployerViewPostsViewHolder)holder;
          //  makeTextViewResizable(employerViewPostsViewHolder.Desc, 4, "..Read More", true);


            String letter = String.valueOf(Items.get(position).getDescription().charAt(0));

            TextDrawable drawable = TextDrawable.builder().buildRound(letter, generator.getRandomColor());
            employerViewPostsViewHolder.imageLetter.setImageDrawable(drawable);
            employerViewPostsViewHolder.jobTitle.setText(Items.get(position).getJobTitle());
            employerViewPostsViewHolder.Desc.setText(Items.get(position).getDescription());
            employerViewPostsViewHolder.uploadTime.setText(Items.get(position).getUploadtime());
//            employerViewPostsViewHolder.readmore.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    System.out.println("position clicked = "+position);
//
//                    employerViewPostsViewHolder.Desc.setMaxLines(50);
//                    employerViewPostsViewHolder.readmore.setVisibility(View.GONE);
//                }
//            });



        }

    }

    @Override
    public int getItemCount() {
        return Items.size();
    }
}
