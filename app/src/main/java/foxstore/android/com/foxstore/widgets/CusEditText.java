package foxstore.android.com.foxstore.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

/**
 * Created by yuzhongrong on 2018/6/29.
 */

public class CusEditText extends android.support.v7.widget.AppCompatEditText {
    public CusEditText(Context context) {
        this(context,null);
    }

    public CusEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);

        String hint = null;
        if(focused){
            hint =getHint().toString();
            setTag(hint);
            setHint("");
        }else{
            hint = getTag().toString();
            setHint(hint);
        }
    }
}
