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
        var view: View?
        var viewHolder: ViewHolder

        if (convertView == null) {
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.product_list, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var produs: Product = getItem(position) as Product
        viewHolder.nume.text = produs.nume
        viewHolder.brand.text = produs.brand
        viewHolder.descriere.text = produs.descriere
        viewHolder.pret.text = produs.pret
        viewHolder.categorie.text = produs.categorie
        viewHolder.imagine.setImageResource(produs.imagine)


        return view as View
        /*val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.product_list, null, true)

        val titleText = rowView.findViewById(R.id.title) as TextView
        val imageView = rowView.findViewById(R.id.icon) as ImageView
        val subtitleText = rowView.findViewById(R.id.description) as TextView

        titleText.text = title[position]
        imageView.setImageResource(imgid[position])
        subtitleText.text = description[position]

        return rowView*/
    }

    override fun getItem(position: Int): Any {
        return products.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return products.count()
    }
}