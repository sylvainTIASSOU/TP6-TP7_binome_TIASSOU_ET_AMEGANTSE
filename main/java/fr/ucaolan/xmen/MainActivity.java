package fr.ucaolan.xmen;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectedItem
{
    private ActivityResultLauncher<Intent> editActivityLauncher;
    RecyclerView recyclerView;
    //List<XMen> list = new ArrayList<>();
    List<XMen> list1;
    Application application;
    XmenAdapter xmenAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialisation de la variable editeActivityLaucher
        editActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), this::onEditActivityFinished);


        application = getApplication();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        XMenApplication application1 = (XMenApplication) getApplication();
        list1 = application1.getListe();
        /*Resources res = getResources();

        String[] noms = res.getStringArray(R.array.noms);
        String[] alias = res.getStringArray(R.array.alias);
        String[] desc = res.getStringArray(R.array.descriptions);
        String[] pouvoir = res.getStringArray(R.array.pouvoirs);
        TypedArray images = res.obtainTypedArray(R.array.idimages);

        for (int i=0; i<noms.length; i++)
        {
            XMen xMen = new XMen(noms[i],alias[i], desc[i], pouvoir[i], images.getResourceId(i, R.drawable.undef));

            list.add(xMen);
        }
        images.recycle();*/

        xmenAdapter = new XmenAdapter(this, list1, this);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(xmenAdapter);
    }

    @Override
    public void itemClicked(XMen xmen)
    {
        String name = xmen.getName();
        String alias = xmen.getAlias();
        String pouvoir = xmen.getPouvoir();
        String desc = xmen.getDescription();
        xmen.setIdImage(R.drawable.undef);
        Toast.makeText(this, name, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), EditActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("alias", alias);
        intent.putExtra("pouvoir", pouvoir);
        intent.putExtra("desc", desc);
        startActivity(intent);


    }


    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return  super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {

        int idItem = item.getItemId();

        if(idItem == R.id.reinit)
        {
            //on initialise la liste
            //list.clear();

            return  true;
        }
        if (idItem == R.id.create)
        {
            this.onEdit(-1);
            return true;

        }
        return  super.onOptionsItemSelected(item);
    }

    //methode appele quand on revient dans la l'activiter
    @SuppressLint("NotifyDataSetChanged")
    private  void onEditActivityFinished(ActivityResult result)
    {
        if(result.getResultCode() == RESULT_OK)
        {
            xmenAdapter.notifyDataSetChanged();
        }
    }

    //METHODE D'EDITION DES LISTE
    private void onEdit(int position)
    {
        Intent intent = new Intent(getApplicationContext(), EditActivity.class);
        intent.putExtra("position", position);
        editActivityLauncher.launch(intent);
    }

    //gestion des click sur les menu contextuelle
    public boolean onContextItemSelected(MenuItem item)
    {
        int position = item.getOrder();

        switch (item.getItemId())
        {
            case XMenHolder.MENU_EDIT:
                onEdit(position);
                return  true;
            case XMenHolder.MENU_DELETE:
                onDelete(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    //methode de supression
    private void onReallyDelete(int position)
    {
        list1.remove(position);
        xmenAdapter.notifyItemRemoved(position);
    }

    private void onDelete(int position)
    {
        XMen xMen = list1.get(position);

        new AlertDialog.Builder(this)
                .setTitle(xMen.getName())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Vous confirmez la suppression?")
                .setPositiveButton(android.R.string.ok, (dialog, idbtn)->onReallyDelete(position))
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }
}