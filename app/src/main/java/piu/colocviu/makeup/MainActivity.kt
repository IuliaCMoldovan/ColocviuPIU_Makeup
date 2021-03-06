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
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.iterator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.product_list.*
import piu.colocviu.makeup.adapter.ProductAdapter
import piu.colocviu.makeup.model.Product


/* implementeaza interfata deoarece e nevoie sa se schimbe lista de elemente afisate, cand se
    schimba elementul selectat */
class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var currentCategory = "none"
    val productList: ArrayList<Product> = ArrayList()
    var favoriteList: ArrayList<Product> = ArrayList()
    var almostList: ArrayList<Product> = ArrayList()
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

        productList.add(Product("Eyeliner 1", "Brand 1", "Descriere 1", "12.1 lei", "eyeliner", R.drawable.eyeliner_1, android.R.drawable.star_big_on, android.R.drawable.ic_dialog_alert))
        productList.add(Product("Eyeliner 2", "Brand 2", "Descriere 2", "12.1 lei", "eyeliner", R.drawable.eyeliner_2, android.R.drawable.star_big_on, android.R.drawable.ic_dialog_alert))
        productList.add(Product("Blush 1", "Brand 3", "Descriere 3", "12.1 lei", "blush", R.drawable.blush_1, android.R.drawable.star_big_on, android.R.drawable.ic_dialog_alert))
        productList.add(Product("Blush 2", "Brand 4", "Descriere 4", "12.1 lei", "blush", R.drawable.blush_2, android.R.drawable.star_big_on, android.R.drawable.ic_dialog_alert))
        productList.add(Product("Blush 3", "Brand 5", "Descriere 5", "12.1 lei", "blush", R.drawable.blush_3, android.R.drawable.star_big_on, android.R.drawable.ic_dialog_alert))
        productList.add(Product("Blush 4", "Brand 6", "Descriere 6", "12.1 lei", "blush", R.drawable.blush_4, android.R.drawable.star_big_on, android.R.drawable.ic_dialog_alert))
        productList.add(Product("Lipstick 1", "Brand 7", "Descriere 7", "12.1 lei", "lipstick", R.drawable.lipstick_1, android.R.drawable.star_big_on, android.R.drawable.ic_dialog_alert))
        productList.add(Product("Lipstick 2", "Brand 8", "Descriere 8", "12.1 lei", "lipstick", R.drawable.lipstick_2, android.R.drawable.star_big_on, android.R.drawable.ic_dialog_alert))
        productList.add(Product("Lipstick 3", "Brand 9", "Descriere 9", "12.1 lei", "lipstick", R.drawable.lipstick_3, android.R.drawable.star_big_on, android.R.drawable.ic_dialog_alert))
        productList.add(Product("Lipstick 4", "Brand 10", "Descriere 10", "12.1 lei", "lipstick", R.drawable.lipstick_4, android.R.drawable.star_big_on, android.R.drawable.ic_dialog_alert))
        productList.add(Product("Foundation 1", "Brand 11", "Descriere 11", "12.1 lei", "foundation", R.drawable.foundation_1, android.R.drawable.star_big_on, android.R.drawable.ic_dialog_alert))
        productList.add(Product("Foundation 2", "Brand 12", "Descriere 12", "12.1 lei", "foundation", R.drawable.foundation_2, android.R.drawable.star_big_on, android.R.drawable.ic_dialog_alert))
        productList.add(Product("Foundation 3", "Brand 13", "Descriere 13", "12.1 lei", "foundation", R.drawable.foundation_3, android.R.drawable.star_big_on, android.R.drawable.ic_dialog_alert))
        productList.add(Product("Foundation 4", "Brand 14", "Descriere 14", "12.1 lei", "foundation", R.drawable.foundation_4, android.R.drawable.star_big_on, android.R.drawable.ic_dialog_alert))
        productList.add(Product("Eyeliner 3", "Brand 15", "Descriere 15", "12.1 lei", "eyeliner", R.drawable.eyeliner_3, android.R.drawable.star_big_on, android.R.drawable.ic_dialog_alert))
        productList.add(Product("Eyeliner 4", "Brand 16", "Descriere 16", "12.1 lei", "eyeliner", R.drawable.eyeliner_4, android.R.drawable.star_big_on, android.R.drawable.ic_dialog_alert))

        productAdapter = ProductAdapter(applicationContext, listedProducts, favoriteList, almostList)
        listView.adapter = productAdapter

        // functionalitatea de long press, ca sa stergem un element
        /*listView.onItemLongClickListener =
            OnItemLongClickListener { parent, view, position, id ->
                AlertDialog.Builder(this@MainActivity)
                    .setIcon(android.R.drawable.ic_delete)
                    .setTitle("Sigur?")
                    .setMessage("Vrei ")
                    .setPositiveButton("DA"
                    ) { dialog, which ->
                        productList.removeAt(position)
                        productAdapter.notifyDataSetChanged()
                    }
                    .setNegativeButton("NU", null)
                    .show()
                true
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
        val menuInfo: AdapterView.AdapterContextMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo

         when (item.itemId) {
             // ADD LA FAVORITE
            R.id.add_to_fav -> {
                AlertDialog.Builder(this)
                    .setTitle("Adauga favorit")
                    .setMessage("Doriti sa adaugati la favorite?")
                    .setPositiveButton(
                        "DA"
                    ) { dialog, which ->
                        // adaug la final in lista de favorite, elementul care in lista afisata se gaseste la pozitia meuInfo.position

                        if (favoriteList.contains(listedProducts[menuInfo.position])) {
                            Toast.makeText(this, "Exista deja la favorite", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            favoriteList.add(favoriteList.lastIndex + 1, listedProducts[menuInfo.position])
                            Toast.makeText(this, "Adaugat la favorite", Toast.LENGTH_SHORT).show()
                        }

                        //listView.getChildAt(menuInfo.position).setBackgroundColor(ContextCompat.getColor(this, R.color.add_fav))
                        productAdapter.notifyDataSetChanged()
                    }
                    .setNegativeButton(
                        "NU"
                    ) { dialog, which ->
                        Toast.makeText(this, "Renuntat la add fav", Toast.LENGTH_SHORT).show()
                    }
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()
            }

             // REMOVE DE LA FAVORITE
            R.id.del_from_fav -> {
                AlertDialog.Builder(this)
                    .setTitle("Sterge favorit")
                    .setMessage("Doriti sa stergeti de la favorite?")
                    .setPositiveButton(
                        "DA"
                    ) { dialog, which ->

                        // daca nu e deja in lista de favorite, nu il pot sterge
                        if (!favoriteList.contains(listedProducts[menuInfo.position])) {
                            Toast.makeText(this, "Nu e favorit ca sa il poti sterge", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            // daca e deja in lista de favorite, doar atunci il pot sterge

                            // sterg din lista de favorite, elementul care in lista afisata se gaseste la pozitia meuInfo.position
                            favoriteList.remove(listedProducts[menuInfo.position])

                            // daca era si in cea cu rosu, si de aici il sterg
                            if (almostList.contains(listedProducts[menuInfo.position])) {
                                almostList.remove(listedProducts[menuInfo.position])
                            }
                            productAdapter.notifyDataSetChanged()
                            Toast.makeText(this, "Sters de la favorite", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .setNegativeButton(
                        "NU"
                    ) { dialog, which ->
                         // daca nu e deja in lista de favorite, nu pot face actiunea asta
                        if (!favoriteList.contains(listedProducts[menuInfo.position])) {
                            Toast.makeText(this, "Nu e favorit ca sa il poti face asta", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            if (!almostList.contains(listedProducts[menuInfo.position])) {
                                almostList.add(almostList.lastIndex+1, listedProducts[menuInfo.position])
                                Toast.makeText(this, "Renuntat la delete fav", Toast.LENGTH_SHORT).show()
                            }
                            else {
                                Toast.makeText(this, "E deja cu rosu", Toast.LENGTH_SHORT).show()
                            }
                        }
                        productAdapter.notifyDataSetChanged()
                    }
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()
            }

            // REMOVE DIN LISTA DE PRODUSE
            R.id.del_item -> {
                AlertDialog.Builder(this)
                    .setTitle("Sterge item")
                    .setMessage("Doriti sa stergeti itemul?")
                    .setPositiveButton(
                        "DA"
                    ) { dialog, which ->
                        if (favoriteList.contains(listedProducts[menuInfo.position])) {
                            favoriteList.remove(listedProducts[menuInfo.position])
                        }
                        if (almostList.contains(listedProducts[menuInfo.position])) {
                            almostList.remove(listedProducts[menuInfo.position])
                        }
                        listedProducts.removeAt(menuInfo.position)
                        productAdapter.notifyDataSetChanged()
                        Toast.makeText(this, "Sters item", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton(
                        "NU"
                    ) { dialog, which ->
                        Toast.makeText(this, "Renuntat la sters item", Toast.LENGTH_SHORT).show()
                    }
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()
            }
        }
        return super.onContextItemSelected(item)
    }

    // meniul pentru categorii
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_foundation -> {
                Toast.makeText(this, "Foundations", Toast.LENGTH_SHORT).show()
                currentCategory = "foundation"
            }
            R.id.nav_lipstick -> {
                Toast.makeText(this, "Lipsticks", Toast.LENGTH_SHORT).show()
                currentCategory = "lipstick"
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