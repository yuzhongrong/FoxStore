package foxstore.android.com.common.widgets;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cjwsc.idcm.base.BaseActivity;

import org.w3c.dom.Text;

import foxstore.android.com.common.R;

public class CommonTopLayout extends RelativeLayout {

    private ImageView back;
    private TextView title;
    private Context context;
    public CommonTopLayout(Context context) {
        super(context);
        this.context=context;
    }

    public CommonTopLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    private void initView() {

        View root = LayoutInflater.from(getContext()).inflate(R.layout.common_top_layout, this, true);
        back=root.findViewById(R.id.back);
        title=root.findViewById(R.id.title);
        back.setOnClickListener(v -> {
            ((BaseActivity) context).finish();
        });
    }


    public void setTitle(String title){
        if(!TextUtils.isEmpty(title)) this.title.setText(title);

    }

}
