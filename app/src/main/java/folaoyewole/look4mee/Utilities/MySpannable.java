package folaoyewole.look4mee.Utilities;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by sp_developer on 2/2/17.
 */
public class MySpannable extends ClickableSpan {

    private boolean isUnderline = true;

    public MySpannable(boolean isUnderline){
        this.isUnderline = isUnderline;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);

        ds.setUnderlineText(isUnderline);
    }

}
