package com.culcheckpot.culcheckpot;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.TextView;

public class StuSchedule {

    private String monday[] = new String[9];
    private String tuesday[] = new String[9];
    private String wednesday[] = new String[9];
    private String thursday[] = new String[9];
    private String friday[] = new String[9];

    public StuSchedule() {
        for(int i = 0; i < 9; i++)
        {
            monday[i] = "";
            tuesday[i] = "";
            wednesday[i] = "";
            thursday[i] = "";
            friday[i] = "";
        }
    }

    public void addSchedule(String scheduleText){
        int temp;
        if((temp = scheduleText.indexOf("월")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']')
                {
                    endPoint = i;
                    monday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = "수업";
                }
            }
        }
        if((temp = scheduleText.indexOf("화")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']')
                {
                    endPoint = i;
                    tuesday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = "수업";
                }
            }
        }
        if((temp = scheduleText.indexOf("수")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']')
                {
                    endPoint = i;
                    wednesday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = "수업";
                }
            }
        }
        if((temp = scheduleText.indexOf("목")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']')
                {
                    endPoint = i;
                    thursday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = "수업";
                }
            }
        }
        if((temp = scheduleText.indexOf("금")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']')
                {
                    endPoint = i;
                    friday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = "수업";
                }
            }
        }
    }

    public boolean validate(String scheduleText) {
        if (scheduleText.equals(""))
        {
            return true;
        }
        int temp;

        if((temp = scheduleText.indexOf("월")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']')
                {
                    endPoint = i;
                    if(!monday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))].equals(""))
                    {
                        return false;
                    }
                }
            }
        }

        if((temp = scheduleText.indexOf("화")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']')
                {
                    endPoint = i;
                    if(!tuesday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))].equals(""))
                    {
                        return false;
                    }
                }
            }
        }

        if((temp = scheduleText.indexOf("수")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']')
                {
                    endPoint = i;
                    if(!wednesday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))].equals(""))
                    {
                        return false;
                    }
                }
            }
        }

        if((temp = scheduleText.indexOf("목")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']')
                {
                    endPoint = i;
                    if(!thursday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))].equals(""))
                    {
                        return false;
                    }
                }
            }
        }

        if((temp = scheduleText.indexOf("금")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']')
                {
                    endPoint = i;
                    if(!friday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))].equals(""))
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void addSchedule(String scheduleText, String courseTitle, String courseProfessor){
        int temp;
        if((temp = scheduleText.indexOf("월")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']')
                {
                    endPoint = i;
                    monday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = courseTitle + courseProfessor;
                }
            }
        }
        if((temp = scheduleText.indexOf("화")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']')
                {
                    endPoint = i;
                    tuesday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = courseTitle + courseProfessor;
                }
            }
        }
        if((temp = scheduleText.indexOf("수")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']')
                {
                    endPoint = i;
                    wednesday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = courseTitle + courseProfessor;
                }
            }
        }
        if((temp = scheduleText.indexOf("목")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']')
                {
                    endPoint = i;
                    thursday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = courseTitle + courseProfessor;
                }
            }
        }
        if((temp = scheduleText.indexOf("금")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++) {
                if (scheduleText.charAt(i) == '[') {
                    startPoint = i;
                }
                if (scheduleText.charAt(i) == ']') {
                    endPoint = i;
                    friday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = courseTitle + courseProfessor;
                }
            }
        }
    }

    public void setting(TextView[] monday, TextView[] tuesday, TextView[] wednesday, TextView[] thursday, TextView[] friday, Context context)
    {
        int maxLength = 0;
        String maxString = "";
        for(int i=0; i<9; i++)
        {
            if(this.monday[i].length() > maxLength)
            {
                maxLength = this.monday[i].length();
                maxString = this.monday[i];
            }
            if(this.tuesday[i].length() > maxLength)
            {
                maxLength = this.tuesday[i].length();
                maxString = this.tuesday[i];
            }
            if(this.wednesday[i].length() > maxLength)
            {
                maxLength = this.wednesday[i].length();
                maxString = this.wednesday[i];
            }
            if(this.thursday[i].length() > maxLength)
            {
                maxLength = this.thursday[i].length();
                maxString = this.thursday[i];
            }
            if(this.friday[i].length() > maxLength)
            {
                maxLength = this.friday[i].length();
                maxString = this.friday[i];
            }

        }
        for(int i=0; i < 9; i++)
        {
            if (!this.monday[i].equals(""))
            {
                monday[i].setText(this.monday[i]);
                monday[i].setBackgroundColor(context.getResources().getColor(R.color.colorMainPoint1));
            }
            else
            {
                monday[i].setText(maxString);
            }
            if (!this.tuesday[i].equals(""))
            {
                tuesday[i].setText(this.tuesday[i]);
                tuesday[i].setBackgroundColor(context.getResources().getColor(R.color.colorMainPoint1));
            }
            else
            {
                tuesday[i].setText(maxString);
            }
            if (!this.wednesday[i].equals(""))
            {
                wednesday[i].setText(this.wednesday[i]);
                wednesday[i].setBackgroundColor(context.getResources().getColor(R.color.colorMainPoint1));
            }
            else
            {
                wednesday[i].setText(maxString);
            }
            if (!this.thursday[i].equals(""))
            {
                thursday[i].setText(this.thursday[i]);
                thursday[i].setBackgroundColor(context.getResources().getColor(R.color.colorMainPoint1));
            }
            else
            {
                thursday[i].setText(maxString);
            }
            if (!this.friday[i].equals(""))
            {
                friday[i].setText(this.friday[i]);
                friday[i].setBackgroundColor(context.getResources().getColor(R.color.colorMainPoint1));
            }
            else
            {
                friday[i].setText(maxString);
            }
        }
    }
}
