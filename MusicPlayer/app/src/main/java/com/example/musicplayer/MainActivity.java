package com.example.musicplayer;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private String songNames[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.listView);

        final ArrayList<File> songs = readSongs(Environment.getExternalStorageDirectory());

        songNames=new String[songs.size()];

        for(int i=0;i<songs.size();i++){
            songNames[i] = songs.get(i).getName().toString().replace(".mp3","");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.songs_layout,R.id.textView,songNames);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startActivity(new Intent(MainActivity.this,AudioPlayer.class).putExtra("position",position).putExtra("list",songs));

            }
        });
    }

    private ArrayList<File> readSongs(File root){
      ArrayList<File> arrayList=new ArrayList<File>();
      File files[]=root.listFiles();
      for(File file:files){
          if(file.isDirectory()){
              arrayList.addAll(readSongs(file));
          }else{
              if(file.getName().endsWith(".mp3")){
                  arrayList.add(file);
              }
          }
      }
      return arrayList;
    }

}
