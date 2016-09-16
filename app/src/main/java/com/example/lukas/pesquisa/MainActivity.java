package com.example.lukas.pesquisa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {

    JSONObject json = null;
    JSONArray jArray;

    Conectar conectar = new Conectar();
    Button btnEntrar;
    Button btnProximo;
    ViewGroup radiogroup;
    TextView txtQuestao;
    LinearLayout linearLayout;
    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnEntrar = (Button) findViewById(R.id.btnIniciar);


        conectar.execute("https://raw.githubusercontent.com/lukasepramos/Pesquisa/master/jsonQuestionario.txt");



        btnEntrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                try {
                    json = conectar.get(5000, TimeUnit.MILLISECONDS);
                    jArray = json.getJSONArray("questao");
                    questao(jArray,0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



    }

    private void questao(final JSONArray jArray,final int i) throws JSONException {

        JSONArray jArrayOpc;





        if(i < jArray.length())
        {
            jArrayOpc = jArray.getJSONObject(i).getJSONArray("alternativas");
            if(jArray.getJSONObject(i).getString("type").equals("radio")){
                setContentView(R.layout.pesquisaradio);

                radiogroup = (ViewGroup) findViewById(R.id.radiogroup);
                for(int j = 0;j< jArrayOpc.length();j++){
                    RadioButton rdbtn = new RadioButton(this);
                    rdbtn.setId(j);
                    rdbtn.setText(jArrayOpc.get(j).toString());
                    radiogroup.addView(rdbtn);
                }
            }
            else{
                setContentView(R.layout.pesquisacheck);
                linearLayout = (LinearLayout) findViewById(R.id.linearCheck);

                for(int j = 0;j < jArrayOpc.length();j++){

                    checkBox = new CheckBox(this);
                    checkBox.setId(j);
                    checkBox.setText(jArrayOpc.get(j).toString());
                    linearLayout.addView(checkBox);
                }




            }

            btnProximo =  (Button) findViewById(R.id.btnProx);
            txtQuestao = (TextView) findViewById(R.id.txtQuestao);
            txtQuestao.setText(jArray.getJSONObject(i).getString("enunciado"));

            btnProximo.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        questao(jArray,i+1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        }else{
            setContentView(R.layout.fim);
        }


    }


}