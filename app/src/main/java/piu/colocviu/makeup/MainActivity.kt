package piu.colocviu.makeup

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.iterator
import kotlinx.android.synthetic.main.activity_main.*
import piu.colocviu.makeup.adapter.ProductAdapter
import piu.colocviu.makeup.model.Product


/* implementeaza interfata deoarece e nevoie sa se schimbe lista de elemente afisate, cand se
    schimba elementul selectat */
class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var currentCategory = "none"
    val productList: ArrayList<Product> = ArrayList()
    val listedProducts: ArrayList<Product> = ArrayList()
    lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // inregistram acest view ca fiind pentru meniu contextual
        registerForContextMenu(listView)

        listView.visibility = View.GONE
        progressBar.visibility = View.GONE

        val listView = findViewById<ListView>(R.id.listView)

        productList.add(Product("Eyeliner 1", "Brand 1", "Descriere 1", "12.1 lei", "eyeliner", R.drawable.eyeliner_1))
        productList.add(Product("Eyeliner 2", "Brand 2", "Descriere 2", "12.1 lei", "eyeliner", R.drawable.eyeliner_2))
        productList.add(Product("Blush 1", "Brand 3", "Descriere 3", "12.1 lei", "blush", R.drawable.blush_1))
        productList.add(Product("Blush 1", "Brand 4", "Descriere 4", "12.1 lei", "blush", R.drawable.blush_2))

        productAdapter = ProductAdapter(applicationContext, listedProducts)
        listView.adapter = productAdapter
        
        /* val myListAdapter = ProductAdapter(this,language,description,imageId)
         listView.adapter = myListAdapter

         listView.setOnItemClickListener(){adapterView, view, position, id ->
             val itemAtPos = adapterView.getItemAtPosition(position)
             val itemIdAtPos = adapterView.getItemIdAtPosition(position)
             Toast.makeText(this, "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_LONG).show()
         }*/
    }

    // la meniu asta contextual, ii dau layout-u pe care l-am creat in operations_menu
    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View?,
        menuInfo: ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        //menu.setHeaderTitle("Puteti efectua: ")
        menuInflater.inflate(R.menu.operations_menu, menu)
    }

    // cand e selectat un buton din meniul contextual, o sa fac cateva operatii
    override fun onContextItemSelected(item: MenuItem): Boolean {

         when (item.itemId) {
            R.id.add_to_fav -> {
                Toast.makeText(this, "Adaugat la favorite", Toast.LENGTH_SHORT).show()

                return true
            }
            R.id.del_from_fav -> {
                Toast.makeText(this, "Sters de la favorite", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.del_item -> {
                Toast.makeText(this, "Sters", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_parfum -> {
                Toast.makeText(this, "Parfumuri", Toast.LENGTH_SHORT).show()
                currentCategory = "parfum"
            }
            R.id.nav_ruj -> {
                Toast.makeText(this, "Rujuri", Toast.LENGTH_SHORT).show()
                currentCategory = "ruj"
            }
            R.id.nav_eyeliner -> {
                Toast.makeText(this, "Eyeline-uri", Toast.LENGTH_SHORT).show()
                currentCategory = "eyeliner"
            }
            R.id.nav_blush -> {
                Toast.makeText(this, "Blush-uri", Toast.LENGTH_SHORT).show()
                currentCategory = "blush"
            }
        }

        emptyPage.visibility = View.GONE
        invalidateOptionsMenu()
        showProducts(currentCategory)

        return super.onOptionsItemSelected(item)
    }

    private fun showProducts(category: String) {
        progressBar.visibility = View.VISIBLE;

        // las sa se invarta atata timp ProgressBar-u pana sa il fac invizibil
        Handler(mainLooper).postDelayed({
            progressBar.visibility = View.GONE;
            // trebuie facuta vizibila, dupa acea prima data cand nu se afiseaza nimic
            listView.visibility = View.VISIBLE

            // acum filtram dupa produsele din categoria selectata
            listedProducts.clear()
            for (p in productList) {
                if (p.categorie.compareTo(category) == 0) {
                    listedProducts.add(p)
                }
            }

            productAdapter.notifyDataSetChanged()
            listView.adapter = productAdapter
        }, 3000)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        var itemsList = menu.iterator()

        // le fac pe toate neafisate, just in case
        for (i in itemsList) {
            i.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        }

        itemsList = menu.iterator()
        // acuma caut itemu cu titlu respectiv selectat, sa il pun ca si curent
        for (i in itemsList) {
            if (i.title == currentCategory) {
                i.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            }
        }

        return super.onPrepareOptionsMenu(menu)
    }

    //
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

    // o sa apara un alert daca vrea sa paraseasca de tot aplicatia
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Iesiti din aplicatie?")
            .setPositiveButton("DA", DialogInterface.OnClickListener { dialog, id ->
                // termina toate procesele parinte si procesul curent, si deci se iese din aplicatie
                finishAffinity();
            })
            .setNegativeButton(
                "NU",
                DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        builder.create().show()
    }

    private fun doOperation() {
        // if adauga la favorite
        Toast.makeText(applicationContext, "Adaugat la favorite!", Toast.LENGTH_SHORT).show();

        // if sterge de la favorite
        Toast.makeText(applicationContext, "Sters de la favorite!", Toast.LENGTH_SHORT).show();

        // if sterge element din lista
        Toast.makeText(applicationContext, "Element sters!", Toast.LENGTH_SHORT).show();
    }

    // daca se apasa "DA", atunci doar ulterior verific ce operatie se doreste sa se efectueze
    val daBtnClick = { dialog: DialogInterface, which: Int ->
        doOperation()
    }
    val nuBtnClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(applicationContext, "Operatie anulata", Toast.LENGTH_SHORT).show()
        dialog.cancel()
    }

    fun basicAlert(view: View) {
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Efectuare operatie")
            setMessage("Sunteti sigur?")
            setPositiveButton("DA", DialogInterface.OnClickListener(function = daBtnClick))
            setNegativeButton("NU", DialogInterface.OnClickListener(function = nuBtnClick))
            show()
        }
    }
}