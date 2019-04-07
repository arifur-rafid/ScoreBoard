package com.example.scoreboard;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<GameData> allTime;
    private List<GameData> thisweek;
    private int flag = 0;
    private TextView firstTV, secondTV, thirdTV;
    private ImageView firstIV, secondIV, thirdIV;
    private Button thisWeekBtn, allTimeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // initialize textViews of LeaderBoard
        firstTV =findViewById(R.id.firsttv);
        secondTV =findViewById(R.id.secondtv);
        thirdTV =findViewById(R.id.thirdtv);

        // initialize ImageViews of LeaderBoard
        firstIV =findViewById(R.id.firstiv);
        secondIV =findViewById(R.id.secondiv);
        thirdIV =findViewById(R.id.thirdiv);

        // This function insert some random data in to the playerlist
        insertDataToList();

        adapter = new RankListAdapter(thisweek, this); // create a adapter for the recyclerView
        recyclerView.setAdapter(adapter); // set adapter on the recyclerView
        setLeaderBoard(); // set the leader board (profile image and winning amount) accordingly (this week or all time)

        OnClickAllTimeButtonListener();  // This function represents the actions when user press All Time button
        OnClickThisWeekButtonListener(); // This function represents the actions when user press This Week button

    }

    public void OnClickThisWeekButtonListener() {
        thisWeekBtn = findViewById(R.id.this_week_btn);
        thisWeekBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag = 0; // This flag variable indicates which button is pressed, flag=0 (This Week -> flag =0 and All Time -> flag =1)
                        recyclerViewChange(); // This function set the player score(this week/ all time) into the recyclerView list depending on which button is pressed
                        buttonBackgroundChangeAfterClick(); // This function change button background depending on which button is selected
                    }
                }
        );
    }

    public void OnClickAllTimeButtonListener() {
        allTimeBtn = findViewById(R.id.all_time_btn);
        allTimeBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag = 1;
                        recyclerViewChange();
                        buttonBackgroundChangeAfterClick();
                    }
                }
        );
    }

    void insertDataToList() {
        allTime = new ArrayList<>();    // initialize alltime ArratList
        thisweek = new ArrayList<>();  // initialize thisweek ArratList
        Random rand = new Random();   // generate random number

        /********* Insert player data randomly Start********/

        /** NOTE here I am inserting player date (April-1 to April-20) using loop. In real world scenario which will be player's system current date. If you test this app after april you need to modify the dates for your desired output  */
        for (int i = 1; i <= 10; i++) {
            GameData gameData = new GameData("player " + i,i + "-04-2019", rand.nextInt(5000)+ "", R.drawable.ic_list_place_holder_); //rand.nextInt(5000) means it generate a random number between 0 to 5000
            allTime.add(gameData);
        }
        for (int i = 10; i <= 20; i++) {
            GameData gameData = new GameData("player " + i,i + "-04-2019", rand.nextInt(5000)+ "", R.drawable.ic_2nd_and_3rd_place_holder);
            allTime.add(gameData);
        }
        /********* Insert player data randomly End********/

        sort(); // Sort all player list(allTime ArrayList) in descending order

        /***** Create a player list on this week basis Start ******/

        for (int i = 0; i< allTime.size(); i++)
        {
            GameData gameData= allTime.get(i);
            if(gameData.is_same_week())  // if player date and system date are on the same week
            {
                thisweek.add(gameData);  //then data (from sorted allTime ArrayList) is insert into the another ArrayList(thisweek)
            }
        }
        /***** Create a player list on this week basis End ******/
    }


    public void recyclerViewChange() {
        setLeaderBoard();
        if (flag == 0) { /* flag=0 means (This Week) button is pressed. so thisweek ArrayList is set on the recyclerView */
            adapter = new RankListAdapter(thisweek, this);
            recyclerView.setAdapter(adapter);
        } else if (flag == 1) {  /* flag=1 means (All Time) button is pressed. so allTIme ArrayList is set on the recyclerView*/
            adapter = new RankListAdapter(allTime, this);
            recyclerView.setAdapter(adapter);
        }
        recyclerView.setHasFixedSize(true);
    }

    // This function sort all player list(allTime ArrayList) according to player winnings (Descending order)
    public void sort() {
        Collections.sort(allTime, new Comparator<GameData>() {
            public int compare(GameData obj1, GameData obj2) {
                return Integer.valueOf(Integer.parseInt(obj2.getPlayer_amount())).compareTo(Integer.valueOf(Integer.parseInt(obj1.getPlayer_amount()))); // To compare integer values
            }
        });
    }

    // This function change button background depending on which button is selected
    public void buttonBackgroundChangeAfterClick()
    {
        if(flag==0)
        {
            thisWeekBtn.setBackgroundResource(R.drawable.ic_this_week_button_selected);
            thisWeekBtn.setTextColor(Color.parseColor("#FFFFFF"));
            allTimeBtn.setBackgroundResource(R.drawable.ic_this_week_unselected);
            allTimeBtn.setTextColor(Color.parseColor("#ed1e79"));
        }
        else{
            allTimeBtn.setBackgroundResource(R.drawable.ic_this_week_button_selected);
            allTimeBtn.setTextColor(Color.parseColor("#FFFFFF"));
            thisWeekBtn.setBackgroundResource(R.drawable.ic_this_week_unselected);
            thisWeekBtn.setTextColor(Color.parseColor("#ed1e79"));
        }
    }

    // Set the leader board (profile image and winning amount) accordingly (this week or all time)
    public void setLeaderBoard()
    {
        if(flag==0)
        {
            firstTV.setText(Html.fromHtml("BDT <b>"+thisweek.get(0).getPlayer_amount()+"</b>"));
            secondTV.setText(Html.fromHtml("BDT <b>"+thisweek.get(1).getPlayer_amount()+"</b>"));
            thirdTV.setText(Html.fromHtml("BDT <b>"+thisweek.get(2).getPlayer_amount()+"</b>"));

            firstIV.setImageResource(thisweek.get(0).getProfile_pic_name_id());
            secondIV.setImageResource(thisweek.get(1).getProfile_pic_name_id());
            thirdIV.setImageResource(thisweek.get(2).getProfile_pic_name_id());
        }
        else
        {
            firstTV.setText(Html.fromHtml("BDT <b>"+ allTime.get(0).getPlayer_amount()+"</b>"));
            secondTV.setText(Html.fromHtml("BDT <b>"+ allTime.get(1).getPlayer_amount()+"</b>"));
            thirdTV.setText(Html.fromHtml("BDT <b>"+ allTime.get(2).getPlayer_amount()+"</b>"));

            firstIV.setImageResource(allTime.get(0).getProfile_pic_name_id());
            secondIV.setImageResource(allTime.get(1).getProfile_pic_name_id());
            thirdIV.setImageResource(allTime.get(2).getProfile_pic_name_id());
        }
    }
}