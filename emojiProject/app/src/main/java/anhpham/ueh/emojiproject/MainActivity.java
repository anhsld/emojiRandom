package anhpham.ueh.emojiproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    GridView mainGrid;
    int lives = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startGame();
    }

    protected void startGame() {
        mainGrid = findViewById(R.id.mainGrid);
        List<Integer> emoji = Arrays.asList(
                0x1F602, 0x1F603, 0x1F605, 0x1F60D, 0x1F60F,
                0x1F618, 0x1F621, 0x1F625, 0x1F628, 0x1F62D,
                0x1F637, 0x1F61D, 0x1F616, 0x1F609, 0x1F60B,
                0x1F635, 0x1F633, 0x1F624, 0x1F61C, 0x1F60A);
        List data = new ArrayList();
        for (int i = 0; i < emoji.size(); i++) {
            data.add(new String(Character.toChars(emoji.get(i))));
        }
        Collections.shuffle(data);
        ColorActivity adapter = new ColorActivity(getApplicationContext(), R.layout.dgv_system, data);
        mainGrid.setAdapter(adapter);

        List temp = new ArrayList(data);
        final String[] target = {getAndShowRandomEmoji(temp)};
        mainGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView v = (TextView) view;
                String selected = v.getText().toString();
                if(selected.equals(target[0])){
                    temp.remove(target[0]);
                    v.setText("");
                    if(temp.size() <= 0){
                        startActivity(new Intent(getApplicationContext(), WinActivity.class));
                    }
                }
                else{
                    lives--;
                    Toast.makeText(getApplicationContext(),
                            "Wrong! " + lives + " remaining lives", Toast.LENGTH_SHORT).show();
                    if(lives <= 0){
                        startActivity(new Intent(getApplicationContext(), LoseActivity.class));
                    }
                }
                target[0] = getAndShowRandomEmoji(temp);
            }
        });
    }

    protected String getAndShowRandomEmoji(List<String> list) {
        Random rand = new Random();
        String result = list.get(rand.nextInt(list.size()));
        TextView target = findViewById(R.id.target);
        target.setText(result);
        return result;
    }
}