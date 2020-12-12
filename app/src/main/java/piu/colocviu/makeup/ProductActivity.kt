package piu.colocviu.makeup

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import piu.colocviu.makeup.adapter.ProductAdapter
import piu.colocviu.makeup.model.Product

class ProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listView)
        val productList: ArrayList<Product> = ArrayList()

        productList.add(
            Product(
                "Eyeliner 1",
                "Brand 1",
                "Descriere 1",
                "12.1 lei",
                "eyeliner",
                R.drawable.eyeliner_1
            )
        )
        productList.add(
            Product(
                "Eyeliner 2",
                "Brand 2",
                "Descriere 2",
                "12.1 lei",
                "eyeliner",
                R.drawable.eyeliner_2
            )
        )
        productList.add(
            Product(
                "Blush 1",
                "Brand 3",
                "Descriere 3",
                "12.1 lei",
                "blush",
                R.drawable.blush_1
            )
        )
        productList.add(
            Product(
                "Blush 1",
                "Brand 4",
                "Descriere 4",
                "12.1 lei",
                "blush",
                R.drawable.blush_2
            )
        )

        listView.adapter = ProductAdapter(applicationContext, productList)
    }
}