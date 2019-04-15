package com.culcheckpot.culcheckpot;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.apache.poi.poifs.property.Parent;
import org.json.JSONObject;

import java.util.List;


public class ProManageCourseListAdapter extends BaseAdapter {

    private Context context;
    private List<ProManageCourseList> ProManageCourseList; //리스트에 StuCourseList 클래스가 담기게 해줌
    private String userID = ProMain.userID;
    private Fragment parent;
    private int courseID;

    public ProManageCourseListAdapter(Context context, List<ProManageCourseList> ProManageCourseList, Fragment parent) {
        this.context = context;
        this.ProManageCourseList = ProManageCourseList;
        this.parent = parent;
    }

    public interface onTimePickerSetListener {
        void onTimePickerSet(int courseID);
    }

    @Override
    public int getCount() {
        return ProManageCourseList.size();
    }

    @Override
    public Object getItem(int i) {
        return ProManageCourseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.promanagecourselist, null);

        TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        TextView courseTime = (TextView) v.findViewById(R.id.courseTime);
        TextView courseRoom = (TextView) v.findViewById(R.id.courseRoom);
        TextView courseGrade = (TextView) v.findViewById(R.id.courseGrade);
        TextView courseArea = (TextView) v.findViewById(R.id.courseArea);
        TextView courseCredit = (TextView) v.findViewById(R.id.courseCredit);
        TextView courseProfessor = (TextView) v.findViewById(R.id.courseProfessor);

        courseTitle.setText(ProManageCourseList.get(i).getCourseTitle()); //클래스에 리스트값 중 타이틀값을 가져옴
        courseTime.setText(ProManageCourseList.get(i).getCourseTime());
        courseRoom.setText(ProManageCourseList.get(i).getCourseRoom());
        courseGrade.setText(ProManageCourseList.get(i).getCourseGrade());
        courseArea.setText(ProManageCourseList.get(i).getCourseArea());
        courseCredit.setText(ProManageCourseList.get(i).getCourseCredit());
        courseProfessor.setText(ProManageCourseList.get(i).getCourseProfessor());

        v.setTag(ProManageCourseList.get(i).getCourseID()); //현재리스트에서 강의아이디값을 태그로 함

        Button deleteBtn = (Button) v.findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                                AlertDialog dialog = builder.setMessage("강의를 삭제하였습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                                AlertDialog dialog = builder.setMessage("강의삭제에 실패하였습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                ProDeleteRequest ProDeleteRequest = new ProDeleteRequest(userID, ProManageCourseList.get(i).getCourseID() + "", responseListener);
                RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                queue.add(ProDeleteRequest);

            }
        });

        Button reviseBtn = (Button) v.findViewById(R.id.reviseBtn);
        reviseBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                courseID = ProManageCourseList.get(i).getCourseID();
                String courseTitle = ProManageCourseList.get(i).getCourseTitle();
                Intent registerIntent = new Intent(parent.getContext(), ProRevise.class);
                parent.getContext().startActivity(registerIntent);
                ProRevise.sendCourseID(courseID);
                ProRevise.sendCourseTitle(courseTitle);
            }
        });

        return v;
    }
}

