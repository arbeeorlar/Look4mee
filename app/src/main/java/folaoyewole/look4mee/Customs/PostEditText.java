package folaoyewole.look4mee.Customs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

/**
 * Created by sp_developer on 2/4/17.
 */
public class PostEditText extends EditText {

    public PostEditText(Context context)
    {
        super(context);
    }

    public PostEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public PostEditText(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs)
    {
        InputConnection conn = super.onCreateInputConnection(outAttrs);
        outAttrs.imeOptions &= ~EditorInfo.IME_FLAG_NO_ENTER_ACTION;
        return conn;
    }
}
