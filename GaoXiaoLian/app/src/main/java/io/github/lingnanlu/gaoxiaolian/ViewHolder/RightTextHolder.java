package io.github.lingnanlu.gaoxiaolian.ViewHolder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;

import butterknife.Bind;
import io.github.lingnanlu.gaoxiaolian.R;

/**
 * Created by Administrator on 2016/4/28.
 */
public class RightTextHolder extends CommonViewHolder{

    @Bind(R.id.chat_right_text_tv_content)
    protected TextView content;

    @Bind(R.id.chat_right_text_tv_name)
    protected TextView name;

//    @Bind(R.id.chat_right_text_layout_status)
//    protected FrameLayout statusView;
//
//    @Bind(R.id.chat_right_text_progressbar)
//    protected ProgressBar loadingBar;
//
//    @Bind(R.id.chat_right_text_tv_error)
//    protected ImageView errorView;


    private AVIMMessage message;
    public RightTextHolder(Context context, ViewGroup root) {
        super(context, root, R.layout.chat_right_text_view);
    }

    @Override
    public void bindData(Object o) {
        message = (AVIMMessage) o;

        String content = "";
        if (message instanceof AVIMTextMessage) {
            content = ((AVIMTextMessage)message).getText();
        }

        this.content.setText(content);
        this.name.setText(message.getFrom());
    }
}
