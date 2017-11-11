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
import android.widget.TextView;

import com.example.eltur.parkinsonbp.HttpClient.HttpClient;
import com.example.eltur.parkinsonbp.ServerClass.HabitUpdate;
import com.example.eltur.parkinsonbp.ServerClass.SubMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Eltur on 12/06/2017.
 */

public class Habit extends AppCompatActivity {
    Button btnSave;
    private HabitUpdate[] ha;
    private static ArrayList<String> Hergelim;
    String userid ="";
    private CheckBox[] cb;
    private  CheckBox[] cb1;
    private int Jindex=0;
    private int JindexForMassege =0;
    ArrayList<String> subMenuCoffe = new ArrayList<String>();
    ArrayList<String> subMenuTea = new ArrayList<String>();
    ArrayList<String> subMenuCola = new ArrayList<String>();
    ArrayList<String> subMenuCigarettes = new ArrayList<String>();
    private static ArrayList<String> consumption;
    private static ArrayList<String> finalconsumption;
    ArrayList<SubMenu> subMenuItems = new ArrayList<SubMenu>();
    ArrayList<String> subMenuClearItems = new ArrayList<>();
    private List<List<SubMenu>> subMenuList1 = new ArrayList<List<SubMenu>>();
    private  ArrayList<Long> subMenuGroupId = new ArrayList<Long>();
    ArrayList<SubMenu> subMenuItemsLowermenu = new ArrayList<SubMenu>();
    private List<List<SubMenu>> subMenuList2 = new ArrayList<List<SubMenu>>();
    Object[] subMenuForUpdate= new Object[100];
    Object[] subMenuForUpdate2= new Object[100];
    private boolean IsCheckedFromSubMenuUpper = false;
    private boolean IsCheckedFromSubMenuLower = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hergelim);
        btnSave = (Button) findViewById(R.id.btnSaveHergelim);
        Hergelim = new ArrayList<String>();
        consumption = new ArrayList<String>();
        finalconsumption =  new ArrayList<String>();
        AddChkBox();
       // ha = new HabitUpdate[Hergelim.size()];

        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ArrayList<String> userchoice = new ArrayList<String>();
               // userchoice.clear();
                String subString = "";
                boolean ischkbox = false;
                int objectSizeUpperSubMenu = 0;
                int objectSizeLowerSubMenu = 0;
                int sumObjectSize = 0;

                for (int i = 0; i < cb.length; i++) {
                    if (cb[i].isChecked()) {
                        objectSizeUpperSubMenu++;
                    }
                }

                for (int i = 0; i < cb1.length; i++) {
                    if (cb1[i].isChecked()) {
                        objectSizeLowerSubMenu++;
                    }
                }
                sumObjectSize = objectSizeUpperSubMenu + objectSizeLowerSubMenu;
                ha = new HabitUpdate[sumObjectSize];

                int j =0;
                for (int i = 0; i < cb.length; i++) {
                    if (cb[i].isChecked()) {
                        ha[j] = new HabitUpdate();
                        ha[j].setHabitName(consumption.get(i).toString());
                        ha[j].setHabitDescription(subMenuForUpdate[i].toString());
                        j++;
                        ischkbox = true;
                    }
                }

                int getindex = j;
                for (int i = 0; i < finalconsumption.size(); i++) {
                    if (cb1[i].isChecked()) {
                        ha[getindex] = new HabitUpdate();
                        ha[getindex].setHabitName(finalconsumption.get(i).toString());
                        ha[getindex].setHabitDescription(subMenuForUpdate2[i].toString());
                        getindex += 1;
                        ischkbox = true;
                    }
                }

                if(!ischkbox)
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(Habit.this).create();
                    alertDialog.setTitle("הודעת מערכת");
                    alertDialog.setMessage("נא לבחור הרגלים");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    // Intent i = new Intent(moodAndAction.this, firstpage.class);
                                    // startActivity(i);
                                    // finish();
                                }
                            });
                    alertDialog.show();
                    return;
                }



                connectToDB addDataToDB= new connectToDB();

                userid = getIntent().getStringExtra("EXTRA_SESSION_ID");
               // String returnVal = addactivities.AddDataToDB(userid,ac,null,null,null,null,null);
                String returnVal = addDataToDB.AddDataToDB(userid,null,ha,null,null,null);


                if(returnVal == "Success")
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(Habit.this).create();
                    alertDialog.setTitle("הודעת מערכת");
                    alertDialog.setMessage("הרגלים נשמרו בהצלחה");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(getBaseContext(), firstpage.class);
                                    intent.putExtra("EXTRA_SESSION_ID", userid);

                                    startActivity(intent);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                        finishAffinity();
                                    }
                                   // finish();
                                }
                            });
                    alertDialog.show();
                }

                else if(returnVal == "Faild")
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(Habit.this).create();
                    alertDialog.setTitle("הודעת מערכת");
                    alertDialog.setMessage("לא ניתן לעדכן הרגלים אנא נסה מאוחר יותר");
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
        final LinearLayout MyFramelaoyoutHabit = (LinearLayout )findViewById(R.id.LinearlayoutHabit);
        connectToDB conn= new connectToDB();
        Hergelim = conn.getAllHergelim();
        subMenuList1 = conn.getSubMenuListHabit();
        subMenuGroupId = HttpClient.getSubMenuListHabitMenuGroupId();


        for (int i = 0; i < Hergelim.size(); i++) {
            if(subMenuGroupId.get(i) != null) {
                if (subMenuGroupId.get(i).toString().equals("0")) {   //upper menu
                    consumption.add(Hergelim.get(i).toString());
                }

                if (subMenuGroupId.get(i).toString().equals("1")) {
                    //lower menu
                    finalconsumption.add(Hergelim.get(i).toString());
                }
            }
        }

        cb = new CheckBox[consumption.size()];

        for (int i = 0; i < consumption.size(); i++) {
                //region CheckBox Style
                cb[i] = new CheckBox(this);
                cb[i].setText(consumption.get(i).toString());
                cb[i].setTextSize(22);
                cb[i].setId(i + 6);
                cb[i].setTextColor(Color.BLACK);
                int states[][] = {{android.R.attr.state_checked}, {}};
                int colors[] = {Color.BLACK, Color.parseColor("#00793c")};
                CompoundButtonCompat.setButtonTintList(cb[i], new ColorStateList(states, colors));
                if (i % 2 != 0) {
                    cb[i].setBackgroundColor(Color.parseColor("#E8F5E9"));
                } else {
                    cb[i].setBackgroundColor(Color.parseColor("#E0E0E0"));

                }
                //endregion
            //region OnClickListener CheckBox
            final int finalI = i;
                cb[i].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        subMenuForUpdate[finalI] = new String("");
                       // for (int j = 0; j < consumption.size(); j++) {
                            if (cb[finalI].isChecked()) {
                                if (!subMenuList1.get(finalI).isEmpty()) {
                                    subMenuItems.addAll(subMenuList1.get(finalI));
                                    if (subMenuItems.isEmpty()) {
                                    } else {
                                        getPopUpSubMenu(cb, finalI);
                                    }
                                    subMenuItems.clear();
                                }
                            }

                        if(!cb[finalI].isChecked()) {
                                cb[finalI].setText(consumption.get(finalI).toString());
                                if (!subMenuForUpdate[finalI].toString().isEmpty() || subMenuForUpdate[finalI] == "")
                                    subMenuForUpdate[finalI] = "";
                                    subMenuClearItems.set(finalI,"");

                            }

                       // }
                    }
                });

                MyFramelaoyoutHabit.addView(cb[i]);

        }

    //    finalI++;
        TextView tv = new TextView(this);
        tv.setText("האם צרכת היום?");
        tv.setTextSize(18);
       // @color/common_google_signin_btn_text_light_default
        tv.setTextColor(Color.GRAY);
        MyFramelaoyoutHabit.addView(tv);
        cb1 = new CheckBox[finalconsumption.size()];

        for (int i = 0; i < finalconsumption.size(); i++) {
            //region CheckBox Style
            cb1[i] = new CheckBox(this);
            cb1[i].setText(finalconsumption.get(i).toString());
            cb1[i].setTextSize(22);
            cb1[i].setId(i + 6);
            cb1[i].setTextColor(Color.BLACK);
            int states[][] = {{android.R.attr.state_checked}, {}};
            int colors[] = {Color.BLACK, Color.parseColor("#00793c")};
            CompoundButtonCompat.setButtonTintList(cb1[i], new ColorStateList(states, colors));
            if (i % 2 != 0) {
                cb1[i].setBackgroundColor(Color.parseColor("#E8F5E9"));
            } else {
                cb1[i].setBackgroundColor(Color.parseColor("#E0E0E0"));

            }
            final int finalIS = i;
            cb1[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                   // for (int j = 0; j < finalconsumption.size(); j++) {
                    subMenuForUpdate2[finalIS] = new String("");
                        if (cb1[finalIS].isChecked()) {
                            if(!subMenuList1.get(consumption.size()+ finalIS).isEmpty()) {
                                subMenuItemsLowermenu.addAll(subMenuList1.get(consumption.size()+ finalIS));
                                if (subMenuItemsLowermenu.isEmpty()) {
                                }
                                else {
                                    getPopUpSubMenuLower(cb1, finalIS);
                                }
                                subMenuItemsLowermenu.clear();
                            }
                        }
                        if(!cb1[finalIS].isChecked()) {
                            {
                                cb1[finalIS].setText(finalconsumption.get(finalIS).toString());
                                if (!subMenuForUpdate2[finalIS].toString().isEmpty() || subMenuForUpdate2[finalIS] == "") {
                                    subMenuForUpdate2[finalIS] = "";
                                    subMenuClearItems.set(finalIS, "");
                                }
                            }
                        }
                   // }
                }
            });

            MyFramelaoyoutHabit.addView(cb1[i]);
        }

    }

    private void getPopUpSubMenuLower(CheckBox[]cbx, int index)
    {
        final int ind = index;
        final PopupMenu popup = new PopupMenu(Habit.this, cbx[index]);
        popup.getMenuInflater()
                .inflate(R.menu.popup_menu_activities_general, popup.getMenu());
       // subMenuList2.clear();
       // subMenuList2.addAll(subMenuList1.get(ind));
       // subMenuList2 = HttpClient.getSubMenuListActivities();
        subMenuItemsLowermenu.clear();
        subMenuItemsLowermenu.addAll(subMenuList1.get(consumption.size() + ind));
        getClearSubMenuList(subMenuItemsLowermenu);
        popup.dismiss();
        //Add Dynamic submenu items
        for(int i = 0; i < subMenuClearItems.size() ;i++) {
            popup.getMenu().add(1, i ,i, subMenuClearItems.get(i).toString());
            //groupId,ItemId,order,title
        }
        IsCheckedFromSubMenuLower =false;
        popup.getMenu().setGroupCheckable(1,true,true);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                item.setChecked(true);
                cb1[ind].setText(finalconsumption.get(ind).toString() + " " + "[" + item.getTitle().toString() + "]");
                subMenuForUpdate2[ind] = item.getTitle();
                IsCheckedFromSubMenuLower = true;
                return true;
            }
        });

        popup.show();
        if(!IsCheckedFromSubMenuLower) {
            getClearSubMenuList(subMenuItemsLowermenu);
            cb1[ind].setText(finalconsumption.get(ind).toString()+ " " + "["+subMenuClearItems.get(0).toString()+"]");
            subMenuForUpdate2[ind] = subMenuClearItems.get(0).toString();
        }

    }

    private void getPopUpSubMenu(CheckBox[]cbx, int index)
    {

        final int ind = index;
        final PopupMenu popup = new PopupMenu(Habit.this, cbx[index]);
        popup.getMenuInflater()
                .inflate(R.menu.popup_menu_activities_general, popup.getMenu());

     //   subMenuList1 = HttpClient.getSubMenuListActivities();
        subMenuItems.clear();
        subMenuItems.addAll(subMenuList1.get(ind));
        getClearSubMenuList(subMenuItems);
        popup.dismiss();
        //Add Dynamic submenu items
        for(int i = 0; i < subMenuClearItems.size() ;i++) {
            popup.getMenu().add(1, i ,i, subMenuClearItems.get(i).toString());
            //groupId,ItemId,order,title
        }
        IsCheckedFromSubMenuUpper =false;
        popup.getMenu().setGroupCheckable(1,true,true);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                item.setChecked(true);
                cb[ind].setText(consumption.get(ind).toString() + " " + "[" + item.getTitle().toString() + "]");
                subMenuForUpdate[ind] = item.getTitle();
                IsCheckedFromSubMenuUpper = true;
                return true;
            }
        });

        popup.show();
        if(!IsCheckedFromSubMenuUpper) {
            getClearSubMenuList(subMenuItems);
            cb[ind].setText(consumption.get(ind).toString()+ " " + "["+subMenuClearItems.get(0).toString()+"]");
            subMenuForUpdate[ind] = subMenuClearItems.get(0).toString();
        }

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(getBaseContext(), firstpage.class);
        intent.putExtra("EXTRA_SESSION_ID", getIntent().getStringExtra("EXTRA_SESSION_ID"));
        startActivity(intent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }
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
