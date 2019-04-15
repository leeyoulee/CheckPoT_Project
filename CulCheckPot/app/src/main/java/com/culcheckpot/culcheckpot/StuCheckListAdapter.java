package com.culcheckpot.culcheckpot;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


public class StuCheckListAdapter extends BaseAdapter {

    private Context context;
    private List<StuCheckList> StuCheckList; //리스트에 StuCourseList 클래스가 담기게 해줌
    private String userID = StuMain.userID;
    private AppCompatActivity parent;


    public StuCheckListAdapter(Context context, List<StuCheckList> StuCheckList, AppCompatActivity parent) {
        this.context = context;
        this.StuCheckList = StuCheckList;
        this.parent = parent;
    }

    @Override
    public int getCount() {
        return StuCheckList.size();
    }

    @Override
    public Object getItem(int i) {
        return StuCheckList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.stuchecklist, null);

        final TextView courseDate = (TextView) v.findViewById(R.id.courseDate);
        final TextView attendText = (TextView) v.findViewById(R.id.attendText);

        courseDate.setText(StuCheckList.get(i).getStuDate()); //클래스에 리스트값 중 타이틀값을 가져옴
        attendText.setText(StuCheckList.get(i).getAttendText());

        return v;
    }
}

