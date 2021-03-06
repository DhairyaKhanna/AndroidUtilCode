package com.blankj.utilcode.pkg.feature.keyboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.lib.base.BaseBackActivity;
import com.blankj.utilcode.pkg.R;
import com.blankj.utilcode.pkg.helper.DialogHelper;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SpanUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/27
 *     desc  : demo about KeyboardUtils
 * </pre>
 */
public class KeyboardActivity extends BaseBackActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, KeyboardActivity.class);
        context.startActivity(starter);
    }

    TextView tvAboutKeyboard;
    EditText etInput;

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_keyboard;
    }

    @Override
    public void initView(Bundle savedInstanceState, @NonNull View contentView) {
        KeyboardUtils.fixAndroidBug5497(this);
        etInput = findViewById(R.id.et_input);
        findViewById(R.id.btn_hide_soft_input).setOnClickListener(this);
        findViewById(R.id.btn_show_soft_input).setOnClickListener(this);
        findViewById(R.id.btn_toggle_soft_input).setOnClickListener(this);
        findViewById(R.id.btn_keyboard_in_fragment).setOnClickListener(this);
        tvAboutKeyboard = findViewById(R.id.tv_about_keyboard);

        KeyboardUtils.registerSoftInputChangedListener(this,
                new KeyboardUtils.OnSoftInputChangedListener() {
                    @Override
                    public void onSoftInputChanged(int height) {
                        tvAboutKeyboard.setText(new SpanUtils()
                                .appendLine("isSoftInputVisible: " + KeyboardUtils.isSoftInputVisible(KeyboardActivity.this))
                                .append("height: " + height)
                                .create()
                        );
                    }
                });
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(@NonNull View view) {
        int i = view.getId();
        if (i == R.id.btn_hide_soft_input) {
            KeyboardUtils.hideSoftInput(this);

        } else if (i == R.id.btn_show_soft_input) {
            KeyboardUtils.showSoftInput(this);

        } else if (i == R.id.btn_toggle_soft_input) {
            KeyboardUtils.toggleSoftInput();

        } else if (i == R.id.btn_keyboard_in_fragment) {
            DialogHelper.showKeyboardDialog();
            KeyboardUtils.showSoftInput(this);

        }
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            View v = getCurrentFocus();
//            if (isShouldHideKeyboard(v, ev)) {
//                InputMethodManager imm =
//                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (imm == null) return super.dispatchTouchEvent(ev);
//                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }
//
//    // 根据 EditText 所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
//    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
//        if (v != null && (v instanceof EditText)) {
//            int[] l = {0, 0};
//            v.getLocationInWindow(l);
//            int left = l[0],
//                    top = l[1],
//                    bottom = top + v.getHeight(),
//                    right = left + v.getWidth();
//            return !(event.getX() > left && event.getX() < right
//                    && event.getY() > top && event.getY() < bottom);
//        }
//        return false;
//    }

    @Override
    protected void onDestroy() {
        KeyboardUtils.unregisterSoftInputChangedListener(this);
        super.onDestroy();
    }
}
