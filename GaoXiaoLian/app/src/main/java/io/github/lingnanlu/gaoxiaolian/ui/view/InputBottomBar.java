package io.github.lingnanlu.gaoxiaolian.ui.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import io.github.lingnanlu.gaoxiaolian.event.InputBottomBarEvent;
import io.github.lingnanlu.gaoxiaolian.event.InputBottomBarTextEvent;
import io.github.lingnanlu.gaoxiaolian.R;

/**
 * Created by rico on 4/27/2016.
 */
public class InputBottomBar extends LinearLayout {

    private Button btSend;
    private EditText etContent;

    public InputBottomBar(Context context) {
        super(context);
        initView(context);
    }

    public InputBottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {

        //这句是什么含义
        View.inflate(context, R.layout.input_bottom_bar, this);

        btSend = (Button) findViewById(R.id.bt_send);
        etContent = (EditText) findViewById(R.id.et_content);

        btSend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etContent.getText().toString();

                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(getContext(), "消息不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                etContent.setText("");

                EventBus.getDefault().post(
                        new InputBottomBarTextEvent(InputBottomBarEvent
                                .INPUTBOTTOMBAR_SEND_TEXT_ACTION, content, getTag())
                );


            }
        });

    }
}
