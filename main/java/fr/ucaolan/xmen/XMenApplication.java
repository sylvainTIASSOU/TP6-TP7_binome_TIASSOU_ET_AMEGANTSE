package fr.ucaolan.xmen;

import android.app.Application;
import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.List;

public class XMenApplication extends Application
{
    private final List<XMen> list = new ArrayList<>();
    public  void onCreate()
    {
        super.onCreate();

        Resources res = getResources();

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
        images.recycle();
    }


    public List<XMen> getListe()
    {
        return this.list;
    }
}
