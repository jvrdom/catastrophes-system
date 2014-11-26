package com.example.catastrophes_system_android;

import java.util.List;

import junit.framework.Assert;
import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class AdaptadorCatastrofeList implements ListAdapter {

	private List<Catastrofe> catastrofes;
	private Activity context;

	public AdaptadorCatastrofeList(Activity context, List<Catastrofe> lista_catastrofes) {
		this.context = context;
		this.catastrofes = lista_catastrofes;
	}

	static class ViewHolderCatastrofe {
	    TextView codigo;
	    TextView nombre;
	    ImageView imagen;
	}
	
	@Override public View getView(int position, View convertView, ViewGroup parent) {
	
		View rowView = convertView;
		ViewHolderCatastrofe holder;
	    if(rowView == null){
	        LayoutInflater inflater = (LayoutInflater) context.getLayoutInflater();
	        rowView = inflater.inflate(R.layout.linea_catastrofe, null);	        
	        holder = new ViewHolderCatastrofe();	
	        holder.imagen = (ImageView) rowView.findViewById(R.id.icon);	
	        holder.nombre = (TextView) rowView.findViewById(R.id.GeneroNombre);	                
	        rowView.setTag(holder);
	    } 
	    else  holder = (ViewHolderCatastrofe) rowView.getTag();
		
		holder.nombre.setText(catastrofes.get(position).getNombre().toString());	
		int idImagen = getDrawable(context, catastrofes.get(position).getLogo());
		if(idImagen==0) idImagen = getDrawable(context, "incendio");		
		holder.imagen.setImageResource(idImagen); 
		return rowView;
	}

	public static int getDrawable(Context context, String name)
    {
        Assert.assertNotNull(context);
        Assert.assertNotNull(name);
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }
	
	@Override public int getCount() { return catastrofes.size(); }

	@Override public Object getItem(int position) { return catastrofes.get(position); }

	@Override public long getItemId(int position) {	return 0; }

	@Override public int getItemViewType(int position) { return R.layout.linea_catastrofe; }

	@Override public int getViewTypeCount() { return 1; }

	@Override public boolean hasStableIds() { return false; }

	@Override public boolean isEmpty() { return false; }

	@Override public void registerDataSetObserver(DataSetObserver observer) { }

	@Override public void unregisterDataSetObserver(DataSetObserver observer) { }

	@Override public boolean areAllItemsEnabled() { return true; }

	@Override public boolean isEnabled(int position) { return true; }
}