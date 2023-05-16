package fr.ucaolan.xmen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity
{
    EditText nameEdit, aliasEdit, pouvoirEdit, descriptionEdit;
    //XMen xMen;
    XMenApplication application;
    Resources res;

    static List<XMen> list;
    TypedArray images;
    int position;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        res = getResources();
        images = res.obtainTypedArray(R.array.idimages);

        nameEdit = (EditText)findViewById(R.id.noms);
        aliasEdit = (EditText)findViewById(R.id.aliass);
        pouvoirEdit = (EditText)findViewById(R.id.pouvoirs);
        descriptionEdit = (EditText)findViewById(R.id.descriptions);

        //recuperation de la position renvoyer par le main
        position = getIntent().getExtras().getInt("position");


        application = (XMenApplication) getApplication();
        list = application.getListe();

        if(position != -1)
        {
            //recupération des information de la position
            list.get(position).getName();
            nameEdit.setText(""+list.get(position).getName());
            aliasEdit.setText(""+list.get(position).getAlias());
            pouvoirEdit.setText(""+list.get(position).getPouvoir());
            descriptionEdit.setText(""+list.get(position).getDescription());
        }
        //new XMen(name, alias, desc, pouvoir, images.getResourceId(0, R.drawable.undef));






    }

    //ajout de l'icon du menu accept
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return  super.onCreateOptionsMenu(menu);
    }

    //methode pour gerer le click sur l'icone accept'
    public void onAccept(MenuItem item)
    {
        //recuperation des donné saisi

        String nom = nameEdit.getText().toString();
        String alias = aliasEdit.getText().toString();
        String pouvoir = pouvoirEdit.getText().toString();
        String desc = descriptionEdit.getText().toString();
        
        XMen xMen = new XMen(nom, alias, desc, pouvoir, images.getResourceId(0, R.drawable.undef));


        //si la position est egala a -1 c_a_d ajouter un xmen
        if(position == -1)
        {
            xMen.setName(nom);
            xMen.setAlias(alias);
            xMen.setDescription(desc);
            xMen.setPouvoir(pouvoir);
            list.add(xMen);


            setResult(RESULT_OK);
            finish();
        }
        else
        {
            xMen.setName(nom);
            xMen.setAlias(alias);
            xMen.setDescription(desc);
            xMen.setPouvoir(pouvoir);
            list.set(position, xMen);

            setResult(RESULT_OK);
            finish();
        }


    }

}