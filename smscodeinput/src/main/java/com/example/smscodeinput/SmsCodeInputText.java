package com.example.smscodeinput;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.smscodeinput.utils.ClipboardUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SmsCodeInputText extends RelativeLayout {

    private Context context;
    private TextView tv_code1;
    private TextView tv_code2;
    private TextView tv_code3;
    private TextView tv_code4;
    private TextView tv_code5;
    private TextView tv_code6;
    private EditText et_code;
    private List<String> codes = new ArrayList<>();
    private InputMethodManager imm;

    public SmsCodeInputText(Context context) {
        super(context);
        this.context = context;
        loadView();
    }

    public SmsCodeInputText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        loadView();
    }

    private void loadView() {
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = LayoutInflater.from(context).inflate(R.layout.rw_login_phone_code, this);
        initView(view);
        initEvent();
    }

    private void initView(View view) {
        tv_code1 = (TextView) view.findViewById(R.id.tv_code1);
        tv_code2 = (TextView) view.findViewById(R.id.tv_code2);
        tv_code3 = (TextView) view.findViewById(R.id.tv_code3);
        tv_code4 = (TextView) view.findViewById(R.id.tv_code4);
        tv_code5 = (TextView) view.findViewById(R.id.tv_code5);
        tv_code6 = (TextView) view.findViewById(R.id.tv_code6);
        et_code = (EditText) view.findViewById(R.id.et_code);
        et_code.requestFocus();
        et_code.setLongClickable(true);//????????????
    }

    private void initEvent() {
        //???????????????
        et_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null && editable.length() > 0) {
                    et_code.setText("");
                    if (codes.size() < 6) {
                        String data = editable.toString().trim();
                        if (data.length() >= 6) {
                            //???string?????????List
                            List<String> list = Arrays.asList(data.split(""));
                            //Arrays.asList????????????add???remove??????????????????AbstractList????????????list??????java.util.ArrayList???
                            codes = new ArrayList<>(list);
                            if (codes != null && codes.size() > 6) {
                                //??????data.split("")????????????""?????????????????????????????????
                                codes.remove(0);
                            }
                        } else {
                            codes.add(data);
                        }
                        showCode();
                    }
                }
            }
        });
        // ???????????????????????????
        et_code.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (codes != null && codes.size() > 0) {
                    if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent.getAction() == KeyEvent.ACTION_DOWN && codes.size() > 0) {
                        codes.remove(codes.size() - 1);
                        showCode();
                        return true;
                    }
                }
                return false;
            }
        });

        final XPopup.Builder builder = new XPopup.Builder(getContext()).watchView(et_code);
        et_code.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                builder.asAttachList(new String[]{"??????"}, null, new OnSelectListener() {
                    @Override
                    public void onSelect(int selectPostion, String text) {
                        //?????????????????????????????????Html, String,Uri,Intent?????????????????????ClipData.getItemAt(0).getText()??????????????????????????????????????????????????????????????????????????????????????????null
                        String data = ClipboardUtils.getText(context).toString();//????????????
                        if (TextUtils.isEmpty(data)) {
                            return;
                        }
                        if (data.length() >= 6) {
                            //???string?????????List
                            List<String> list = Arrays.asList(data.split(""));
                            //Arrays.asList????????????add???remove??????????????????AbstractList????????????list??????java.util.ArrayList???
                            codes = new ArrayList<>(list);
                            if (codes != null && codes.size() > 6) {
                                //??????data.split("")???????????????????????????????????????????????????
                                codes.remove(0);
                            }
                        } else {
                            codes.add(data);
                        }
                        showCode();
                    }
                }).show();
                return true;
            }
        });
    }

    /**
     * ????????????????????????
     */
    private void showCode() {
        String code1 = "";
        String code2 = "";
        String code3 = "";
        String code4 = "";
        String code5 = "";
        String code6 = "";
        if (codes.size() >= 1) {
            code1 = codes.get(0);
        }
        if (codes.size() >= 2) {
            code2 = codes.get(1);
        }
        if (codes.size() >= 3) {
            code3 = codes.get(2);
        }
        if (codes.size() >= 4) {
            code4 = codes.get(3);
        }
        if (codes.size() >= 5) {
            code5 = codes.get(4);
        }
        if (codes.size() >= 6) {
            code6 = codes.get(5);
        }
        tv_code1.setText(code1);
        tv_code2.setText(code2);
        tv_code3.setText(code3);
        tv_code4.setText(code4);
        tv_code5.setText(code5);
        tv_code6.setText(code6);
        //setColor();//??????????????????
        callBack();//??????
    }

    /**
     * ????????????????????????
     */
    public void showEmptyCode() {
        tv_code1.setText("");
        tv_code2.setText("");
        tv_code3.setText("");
        tv_code4.setText("");
        tv_code5.setText("");
        tv_code6.setText("");
        codes.clear();
        et_code.setText("");
    }

    /**
     * ??????????????????
     */
    /*private void setColor(){
        int color_default = Color.parseColor("#999999");
        int color_focus = Color.parseColor("#3F8EED");
        v1.setBackgroundColor(color_default);
        v2.setBackgroundColor(color_default);
        v3.setBackgroundColor(color_default);
        v4.setBackgroundColor(color_default);
        if(codes.size()==0){
            v1.setBackgroundColor(color_focus);
        }
        if(codes.size()==1){
            v2.setBackgroundColor(color_focus);
        }
        if(codes.size()==2){
            v3.setBackgroundColor(color_focus);
        }
        if(codes.size()>=3){
            v4.setBackgroundColor(color_focus);
        }
    }*/

    /**
     * ??????
     */
    private void callBack() {
        if (onInputListener == null) {
            return;
        }
        if (codes.size() == 6) {
            onInputListener.onSucess(getPhoneCode());
        } else {
            onInputListener.onInput();
        }
    }

    //????????????
    public interface OnInputListener {
        void onSucess(String codes);

        void onInput();
    }

    private OnInputListener onInputListener;

    public void setOnInputListener(OnInputListener onInputListener) {
        this.onInputListener = onInputListener;
    }

    /**
     * ????????????
     */
    public void showSoftInput() {
        //???????????????
        if (imm != null && et_code != null) {
            et_code.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imm.showSoftInput(et_code, 0);
                }
            }, 1500);
        }
    }

    /**
     * ????????????????????????
     *
     * @return ?????????
     */
    public String getPhoneCode() {
        StringBuilder sb = new StringBuilder();
        for (String codes : codes) {
            sb.append(codes);
        }
        return sb.toString();
    }
}

