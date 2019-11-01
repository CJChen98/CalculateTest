package com.example.calculatetest;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import java.util.Random;

public class MyViewModel extends AndroidViewModel {
    SavedStateHandle handle;
    private String key_hs = String.valueOf(R.string.KEY_HIGH_SCORE),
            key_ssdn = String.valueOf(R.string.SAVE_SHP_DATA_NAME),
            key_ln = String.valueOf(R.string.KEY_LEFT_NUM),
            key_o = String.valueOf(R.string.KEY_OPERATOR),
            key_rn = String.valueOf(R.string.KEY_RIGHT_NUM),
            key_anwser = String.valueOf(R.string.KEY_ANSWER),
            key_cs = String.valueOf(R.string.KEY_CURRENT_SCORE);
    boolean win_flag = false;

    public MyViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        if (!handle.contains(key_hs)) {
            SharedPreferences shp = getApplication().getSharedPreferences(key_ssdn, Context.MODE_PRIVATE);
            handle.set(key_hs, shp.getInt(key_hs, 0));
            handle.set(key_ln, 0);
            handle.set(key_o, "+");
            handle.set(key_rn, 0);
            handle.set(key_anwser, 0);
            handle.set(key_cs, 0);
        }
        this.handle = handle;
    }

    public MutableLiveData<Integer> getLeftNum() {
        return handle.getLiveData(key_ln);
    }

    public MutableLiveData<String> getOperator() {
        return handle.getLiveData(key_o);
    }

    public MutableLiveData<Integer> getRightNum() {
        return handle.getLiveData(key_rn);
    }

    public MutableLiveData<Integer> getHighScore() {
        return handle.getLiveData(key_hs);
    }

    public MutableLiveData<Integer> getCurrentScore() {
        return handle.getLiveData(key_cs);
    }

    MutableLiveData<Integer> getAnswer() {
        return handle.getLiveData(key_anwser);
    }

    void generator() {
        int level = 25;
        Random random = new Random();
        int x, y;
        x = random.nextInt(level) + 1;
        y = random.nextInt(level) + 1;
        if (x % 2 == 0) {
            getOperator().setValue("+");
            if (x > y) {
                getAnswer().setValue(x);
                getLeftNum().setValue(y);
                getRightNum().setValue(x - y);
            } else {
                getAnswer().setValue(y);
                getLeftNum().setValue(x);
                getRightNum().setValue(y - x);
            }
        } else {
            getOperator().setValue("-");
            if (x > y) {
                getAnswer().setValue(x - y);
                getLeftNum().setValue(x);
                getRightNum().setValue(y);
            } else {
                getAnswer().setValue(y - x);
                getLeftNum().setValue(y);
                getRightNum().setValue(x);
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    void save() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(key_ssdn, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(key_ssdn, getHighScore().getValue());
        edit.apply();
    }

    @SuppressWarnings("ConstantConditions")
    void AnswerCorrect() {
        getCurrentScore().setValue(getCurrentScore().getValue() + 1);
        if (getCurrentScore().getValue() > getHighScore().getValue()) {
            getHighScore().setValue(getCurrentScore().getValue());
            win_flag = true;
        }
        generator();
    }
}
