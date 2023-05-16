package fr.ucaolan.xmen;

import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class XMenHolder extends RecyclerView.ViewHolder
{
    public TextView name, alias, description, pouvoir;
    public ImageView image;
    public RelativeLayout view;
    public static final int MENU_EDIT = 1;
    public static final int MENU_DELETE= 2;

    public XMenHolder(@NonNull View itemView)
    {
        super(itemView);

        name = itemView.findViewById(R.id.nom);
        alias = itemView.findViewById(R.id.alias);
        description = itemView.findViewById(R.id.description);
        pouvoir = itemView.findViewById(R.id.pouvoir);
        image = itemView.findViewById(R.id.image);
        view  = itemView.findViewById(R.id.xmenLayout);
        view.setOnCreateContextMenuListener(this::onCreateContexMenu);

    }

    public  void onCreateContexMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        int position = getAdapterPosition();

        menu.setHeaderTitle(name.getText());

        menu.add(0, MENU_EDIT, position, "EDIT");
        menu.add(0, MENU_DELETE, position, "DELETE");
    }
}
