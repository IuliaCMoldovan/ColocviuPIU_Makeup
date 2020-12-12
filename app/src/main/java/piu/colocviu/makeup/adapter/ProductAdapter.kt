package piu.colocviu.makeup.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import piu.colocviu.makeup.R
import piu.colocviu.makeup.model.Product

class ProductAdapter(var context: Context, var products: ArrayList<Product>) : BaseAdapter() {

    private class ViewHolder(row: View?) {
        var nume: TextView = row?.findViewById(R.id.nume) as TextView
        var brand: TextView = row?.findViewById(R.id.brand) as TextView
        var descriere: TextView = row?.findViewById(R.id.descriere) as TextView
        var pret: TextView = row?.findViewById(R.id.pret) as TextView
        var categorie: TextView = row?.findViewById(R.id.categorie) as TextView
        var imagine: ImageView = row?.findViewById(R.id.imagine) as ImageView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val viewHolder: ViewHolder

        // daca inca nu am pentru view-ul asta, atunci creez unu pe baza la produsele existente
        if (convertView == null) {
            val layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.product_list, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            // daca am deja, atunci doar fac corespondenta intre tag
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        // imi atribui dinamic valorile la produs
        val produs: Product = getItem(position) as Product
        viewHolder.nume.text = produs.nume
        viewHolder.brand.text = produs.brand
        viewHolder.descriere.text = produs.descriere
        viewHolder.pret.text = produs.pret
        viewHolder.categorie.text = produs.categorie
        viewHolder.imagine.setImageResource(produs.imagine)

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