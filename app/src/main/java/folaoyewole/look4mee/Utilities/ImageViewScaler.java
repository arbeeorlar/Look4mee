package folaoyewole.look4mee.Utilities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by folasheleoyewole on 7/16/16.
 */
public class ImageViewScaler extends ImageView {
    public ImageViewScaler(Context context) {
        super(context);
    }

    public ImageViewScaler(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ImageViewScaler(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable drawable = getDrawable();
        if(drawable != null){
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = width * drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth();

            setMeasuredDimension(width,height);
        }

        else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }
}
