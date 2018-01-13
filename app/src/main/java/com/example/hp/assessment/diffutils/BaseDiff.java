package com.example.hp.assessment.diffutils;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by Md. Sifat-Ul Haque on 12/01/2018.
 */
public abstract class BaseDiff<Model> extends DiffUtil.Callback {

    protected List<Model> mOldList, mNewList;

    public BaseDiff(List<Model> oldList, List<Model> newList) {
        mOldList = oldList;
        mNewList = newList;
    }

    public void setNewList(List<Model> newList) {
        this.mNewList = newList;
    }

    public void setOldList(List<Model> oldList) {
        this.mOldList = oldList;
    }

    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }


}
