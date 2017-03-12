package com.fileop.app.fileop;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText edt = null;
    private final String strName = "data.txt";
    private Button btnSave = null;
    private Button btnCancel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String strContent = Load();
        edt = (EditText) findViewById(R.id.editText);
        edt.setText(strContent);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strContent = edt.getText().toString();
                save(strContent);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private String Load() {
        StringBuilder content = new StringBuilder();
        FileInputStream in = null;
        BufferedReader reader = null;
        try {
            in = openFileInput(strName);
            reader = new BufferedReader(new InputStreamReader(in));
            String strLine = "";
            while ((strLine = reader.readLine()) != null) {
                content.append(strLine);
                content.append("\r\n");
            }
            if (content.length() >= 2)
                content.delete(content.length()-2,content.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return content.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String strContent = edt.getText().toString();
        save(strContent);
    }

    private void save(String strContent){
        TextUtils.replace(strContent, new String[]{"\n"}, new String[]{"\n\r"});
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput(strName, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(strContent);
            Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "保存失败！", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "保存失败！", Toast.LENGTH_SHORT).show();
        } finally {
            if (writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
