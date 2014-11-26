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

public class AdaptadorPersonaList implements ListAdapter {

	private List<Persona> personas;
	private Activity context;

	public AdaptadorPersonaList(Activity context, List<Persona> lista_personas) {
		this.context = context;
		this.personas = lista_personas;
	}

	static class ViewHolderPersona {
	    TextView codigo;
	    TextView nombre;
	    ImageView imagen;
	}
	
	@Override public View getView(int position, View convertView, ViewGroup parent) {
	
		View rowView = convertView;
		ViewHolderPersona holder;
	    if(rowView == null){
	        LayoutInflater inflater = (LayoutInflater) context.getLayoutInflater();
	        rowView = inflater.inflate(R.layout.linea_persona_desaparecida, null);	        
	        holder = new ViewHolderPersona();	
	        holder.imagen = (ImageView) rowView.findViewById(R.id.icon);	
	        holder.nombre = (TextView) rowView.findViewById(R.id.GeneroNombre);	                
	        rowView.setTag(holder);
	    } 
	    else  holder = (ViewHolderPersona) rowView.getTag();
		
		holder.nombre.setText(personas.get(position).getNombre().toString());
		int idImagen = getDrawable(context, personas.get(position).getImagen());
		if(idImagen==0) idImagen = getDrawable(context, "ic_launcher");		
		holder.imagen.setImageResource(idImagen); 
		return rowView;
	}

	public static int getDrawable(Context context, String name)
    {
        Assert.assertNotNull(context);
        Assert.assertNotNull(name);
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }
	
	@Override public int getCount() { return personas.size(); }

	@Override public Object getItem(int position) { return personas.get(position); }

	@Override public long getItemId(int position) {	return 0; }

	@Override public int getItemViewType(int position) { return R.layout.linea_persona_desaparecida; }

	@Override public int getViewTypeCount() { return 1; }

	@Override public boolean hasStableIds() { return false; }

	@Override public boolean isEmpty() { return false; }

	@Override public void registerDataSetObserver(DataSetObserver observer) { }

	@Override public void unregisterDataSetObserver(DataSetObserver observer) { }

	@Override public boolean areAllItemsEnabled() { return true; }

	@Override public boolean isEnabled(int position) { return true; }
}