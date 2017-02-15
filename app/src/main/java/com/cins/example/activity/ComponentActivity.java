package com.cins.example.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cins.example.R;
import com.cins.example.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eric on 2017/2/15.
 */

public class ComponentActivity extends BaseActivity {

    private Toolbar mToolbar;
    private View thirdLayout;

    private SwitchCompat switchCompat;

    private CheckBox checkBox;
    private RadioButton radiobutton;
    private SeekBar seekBar;
    Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.fragment_component);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("其他组件");

        thirdLayout = findViewById(R.id.componentLayout);

        switchCompat = (SwitchCompat) findViewById(R.id.switchCompat);

        checkBox = (CheckBox) findViewById(R.id.checkbox);
        radiobutton = (RadioButton) findViewById(R.id.radiobutton);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        findViewById(R.id.bottomNavigation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, BottomNavigationActivity.class));
            }
        });
        findViewById(R.id.snackbars).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(thirdLayout, "Snackbar Test", Snackbar.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.bottomSheetDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });
        findViewById(R.id.dialogs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogs();
            }
        });

        findViewById(R.id.switchCompatLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchCompat.setChecked(!switchCompat.isChecked());
            }
        });

        findViewById(R.id.checkboxLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setChecked(!checkBox.isChecked());
            }
        });
        findViewById(R.id.radiobuttonLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radiobutton.setChecked(!radiobutton.isChecked());
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Snackbar.make(thirdLayout, "progress=" + progress, Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    public void initView() {




    }

    private BottomSheetDialog mBottomSheetDialog;

    public void showBottomSheetDialog() {
        View sheetDialogView = View.inflate(mActivity, R.layout.reading_actions_sheet, null);
        mBottomSheetDialog = new BottomSheetDialog(mActivity);
        mBottomSheetDialog.setContentView(sheetDialogView);
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });
        mBottomSheetDialog.show();
    }

    public void showDialogs() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("Title");
        builder.setMessage("Message");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}
