package piu.colocviu.makeup.adapter

import android.content.Context
import android.graphics.drawable.Icon
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import piu.colocviu.makeup.R
import piu.colocviu.makeup.model.Product

class ProductAdapter(var context: Context, var products: ArrayList<Product>, var favorites: ArrayList<Product>) : BaseAdapter() {

    private class ViewHolder(row: View?) {
        var nume: TextView = row?.findViewById(R.id.nume) as TextView
        var brand: TextView = row?.findViewById(R.id.brand) as TextView
        var descriere: TextView = row?.findViewById(R.id.descriere) as TextView
        var pret: TextView = row?.findViewById(R.id.pret) as TextView
        var categorie: TextView = row?.findViewById(R.id.categorie) as TextView
        var imagine: ImageView = row?.findViewById(R.id.imagine) as ImageView
        var iconita_fav: ImageView = row?.findViewById(R.id.iconita_fav) as ImageView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val viewHolder: ViewHolder

        val produs: Product = getItem(position) as Product

        // daca inca nu am pentru view-ul asta, atunci creez unu pe baza la produsele existente
        if (convertView == null) {
            val layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.product_list, parent, false)

            if (favorites.contains(produs)) {
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.add_fav))
            }

            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            // daca am deja, atunci doar fac corespondenta intre tag
            view = convertView
            if (favorites.contains(produs)) {
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.add_fav))
            }
            viewHolder = view.tag as ViewHolder
        }

        // imi atribui dinamic valorile la produs
        viewHolder.nume.text = produs.nume
        viewHolder.brand.text = produs.brand
        viewHolder.descriere.text = produs.descriere
        viewHolder.pret.text = produs.pret
        viewHolder.categorie.text = produs.categorie
        viewHolder.imagine.setImageResource(produs.imagine)
        viewHolder.iconita_fav.setImageResource(produs.iconita_fav)

        if (favorites.contains(produs)) {
            viewHolder.iconita_fav.visibility = View.VISIBLE
        }
        else {
            viewHolder.iconita_fav.visibility = View.GONE
        }

        return view as View
    }

    override fun getItem(position: Int): Any {
        return products[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return products.count()
    }
}