package com.example.eltur.parkinsonbp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.example.eltur.parkinsonbp.HttpClient.HttpClient;
import com.example.eltur.parkinsonbp.ServerClass.ActivityUpdate;
import com.example.eltur.parkinsonbp.ServerClass.SubMenu;
import java.util.ArrayList;
import java.util.List;


public class Activities extends AppCompatActivity {

    boolean ischkbox = false;
    ActivityUpdate[] ac1;
    private int JindexForMassege =0;
    boolean IsCheckedFromSubMenu = false;
    boolean IsCheckedFlagOther = false;
    boolean IsScreenClickedFlagOther = false;
    ArrayList<SubMenu> subMenuItems = new ArrayList<SubMenu>();
    ArrayList<String> subMenuClearItems = new ArrayList<>();
    Object[] subMenuForUpdate =  new Object[10];
    private List<List<SubMenu>> subMenuList1 = new ArrayList<List<SubMenu>>();
    private LinearLayout  MyFramelaoyoutActivity;
    private String m_Text = "";
    public ArrayList<String> getActivities() {
        return Activities;
    }
    String userid ="";

    public void setActivities(ArrayList<String> activities) {
        Activities = activities;
    }
   private CheckBox[] cb;
    Button btnClickMe;
    private static ArrayList<String> Activities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        btnClickMe = (Button)findViewById(R.id.btnSaveActivity);
        Activities = new ArrayList<String>();
        MyFramelaoyoutActivity = (LinearLayout)findViewById(R.id.LinearlayoutActivity);
        AddChkBox();

        btnClickMe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int objectSize = 0;
                for (int i = 0; i < cb.length; i++) {
                    if (cb[i].isChecked()) {
                        objectSize++;
                    }
                }

                ac1 = new ActivityUpdate[objectSize];
                int j =0;
                for (int i = 0; i < cb.length; i++) {
                    if (cb[i].isChecked()) {
                          ac1[j] = new ActivityUpdate();
                          ac1[j].setActivityName(Activities.get(i).toString());
                          ac1[j].setActivityDescription(subMenuForUpdate[i].toString());
                        j++;
                        ischkbox = true;
                    }
                }

                if(!ischkbox)
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(com.example.eltur.parkinsonbp.Activities.this).create();
                    alertDialog.setTitle("הודעת מערכת");
                    alertDialog.setMessage("נא לבחור פעילויות");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    return;
                }



                    connectToDB addactivities= new connectToDB();
                    userid = getIntent().getStringExtra("EXTRA_SESSION_ID");

                    String returnVal = addactivities.AddDataToDB(userid,ac1,null,null,null,null);

                    if(returnVal=="Success")
                    {
                        AlertDialog alertDialog = new AlertDialog.Builder(com.example.eltur.parkinsonbp.Activities.this).create();
                        alertDialog.setTitle("הודעת מערכת");
                        alertDialog.setMessage( "הפעילויות נשמרו בהצלחה");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(getBaseContext(), firstpage.class);
                                        intent.putExtra("EXTRA_SESSION_ID", userid);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                        alertDialog.show();
                    }
                    else if(returnVal == "Faild")
                    {
                        AlertDialog alertDialog = new AlertDialog.Builder(com.example.eltur.parkinsonbp.Activities.this).create();
                        alertDialog.setTitle("הודעת מערכת");
                        alertDialog.setMessage("לא ניתן לעדכן פעילויות");
                       // alertDialog.setMessage(ac[5].getActivityName().toString()+ac[5].getActivityDescription().toString());
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(getBaseContext(), firstpage.class);
                                        intent.putExtra("EXTRA_SESSION_ID", userid);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                        alertDialog.show();
                    }

                }

        });

    }

    public void AddChkBox()
    {

        final LinearLayout laoyoutActivity = (LinearLayout )findViewById(R.id.LinearlayoutActivity);
        connectToDB dd = new connectToDB();
        Activities = dd.getAllActivies();
        cb = new CheckBox[Activities.size()];
        subMenuList1 = HttpClient.getSubMenuListActivities();
        for (int i = 0; i < Activities.size(); i++) {
            //region CheckBoxStyle
            cb[i] = new CheckBox(this);
            cb[i].setText(Activities.get(i).toString());
            cb[i].setTextSize(22);
            cb[i].setTextColor(Color.BLACK);
            cb[i].setId(i + 6);
            int states[][] = {{android.R.attr.state_checked}, {}};
            int colors[] = {Color.BLACK, Color.parseColor("#00793c")};
            CompoundButtonCompat.setButtonTintList(cb[i], new ColorStateList(states, colors));
             if(i%2 !=0) {
                cb[i].setBackgroundColor(Color.parseColor("#E8F5E9"));
            }
            else
            {
                cb[i].setBackgroundColor(Color.parseColor("#E0E0E0"));

            }
            //endregion

            //region OnClickListener CheckBox
            final int finalI = i;
            cb[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    subMenuForUpdate[finalI] = new String("");
                        if (cb[finalI].isChecked()) {
                            if(!subMenuList1.get(finalI).isEmpty()) {
                                subMenuItems.addAll(subMenuList1.get(finalI));
                                if (subMenuItems.isEmpty()) {
                                }
                                else {
                                    getPopUpSubMenu(cb, finalI);
                                }
                                subMenuItems.clear();
                            }

                        }
                       if(!cb[finalI].isChecked()) {
                           cb[finalI].setText(Activities.get(finalI).toString());
                               if (!subMenuForUpdate[finalI].toString().isEmpty() || subMenuForUpdate[finalI] == "" ) {
                                   subMenuForUpdate[finalI] = "";
                                   subMenuClearItems.set(finalI,"");

                               }


                        }

                        //region OtherActivities
                        if (cb[finalI].isChecked() && Activities.get(finalI).toString().equals("פעילות גופנית אחרת") && !IsCheckedFlagOther )
                        {
                            IsScreenClickedFlagOther = false;
                            IsCheckedFlagOther = true;
                            JindexForMassege = 0;
                            JindexForMassege = finalI;
                            m_Text = "";
                                final EditText input = new EditText(com.example.eltur.parkinsonbp.Activities.this);
                                AlertDialog alertDialog = new AlertDialog.Builder(com.example.eltur.parkinsonbp.Activities.this).create();
                                alertDialog.setTitle("איזו פעילות גופנית ביצעת?");
                                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                                alertDialog.setView(input);
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "אישור",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                IsScreenClickedFlagOther = true;
                                                m_Text = input.getText().toString();
                                                if(m_Text.isEmpty())
                                                {
                                                    m_Text = "none";
                                                    subMenuForUpdate[JindexForMassege] = "";
                                                    cb[JindexForMassege].setChecked(false);
                                                    cb[JindexForMassege].setText("פעילות גופנית אחרת");
                                                    IsCheckedFlagOther = false;
                                                }
                                                else
                                                {
                                                   // m_Text = "לא צוינה פעילות";
                                                    cb[JindexForMassege].setText("פעילות גופנית אחרת" + " " + "["+m_Text+"]");
                                                    subMenuForUpdate[JindexForMassege] = m_Text;
                                                }
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "ביטול",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                m_Text = "none" ;
                                                cb[JindexForMassege].setText("פעילות גופנית אחרת");
                                                subMenuForUpdate[JindexForMassege] = "";
                                                cb[JindexForMassege].setChecked(false);
                                                dialog.dismiss();
                                                IsCheckedFlagOther = false;
                                                IsScreenClickedFlagOther = true;
                                                return;
                                            }
                                        });
                                alertDialog.show();
                            if(m_Text.isEmpty())
                            {

                                m_Text = "לא צוינה פעילות";
                                subMenuForUpdate[JindexForMassege] = m_Text;
                                cb[JindexForMassege].setText(cb[JindexForMassege].getText().toString() + " " + "["+m_Text+"]");
                            }
                        }
                        if(!cb[finalI].isChecked() && Activities.get(finalI).toString().equals("פעילות גופנית אחרת"))
                        {

                            IsCheckedFlagOther = false;
                            m_Text = "";
                            subMenuForUpdate[JindexForMassege] = m_Text;
                            cb[finalI].setText("פעילות גופנית אחרת");

                        }
                        //endregion


                }
            });
      //endregion
            laoyoutActivity.addView(cb[i]);

        }
    }

    private void getPopUpSubMenu(CheckBox[]cbx, int index)
    {

        final int ind = index;
        final PopupMenu popup = new PopupMenu(Activities.this, cbx[index]);
        popup.getMenuInflater()
                .inflate(R.menu.popup_menu_activities_general, popup.getMenu());

        subMenuList1 = HttpClient.getSubMenuListActivities();
        subMenuItems.clear();
        subMenuItems.addAll(subMenuList1.get(ind));
        getClearSubMenuList(subMenuItems);
        popup.dismiss();
        //Add Dynamic submenu items
        for(int i = 0; i < subMenuClearItems.size() ;i++) {
            popup.getMenu().add(1, i ,i, subMenuClearItems.get(i).toString());
            //groupId,ItemId,order,title
        }
        IsCheckedFromSubMenu =false;
        popup.getMenu().setGroupCheckable(1,true,true);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                item.setChecked(true);
                        cb[ind].setText(Activities.get(ind).toString() + " " + "[" + item.getTitle().toString() + "]");
                subMenuForUpdate[ind] = item.getTitle().toString();
                        IsCheckedFromSubMenu = true;
                return true;
            }
        });

        popup.show();
                if(!IsCheckedFromSubMenu) {
                    getClearSubMenuList(subMenuItems);
                    cb[ind].setText(Activities.get(ind).toString()+ " " + "["+subMenuClearItems.get(0).toString()+"]");
                    subMenuForUpdate[ind] = subMenuClearItems.get(0).toString();
                }

    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(getBaseContext(), firstpage.class);
        intent.putExtra("EXTRA_SESSION_ID", getIntent().getStringExtra("EXTRA_SESSION_ID"));
        startActivity(intent);
        //finish();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }
    }

    public ArrayList<String> getActivityList() {
        return this.Activities;
    }


private void getClearSubMenuList(ArrayList<SubMenu> SuBmenuList)
{
    subMenuClearItems.clear();
    for(int i =0;i<SuBmenuList.size();i++)
    {
        String temp = SuBmenuList.get(i).toString().substring(9,SuBmenuList.get(i).toString().length()-1);
        String subMenuItemsForScreen = temp.replaceAll("^\"|\"$", "");
        subMenuClearItems.add(subMenuItemsForScreen);
    }
}
}