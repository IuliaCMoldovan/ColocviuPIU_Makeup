package piu.colocviu.makeup

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.iterator
import kotlinx.android.synthetic.main.activity_main.*
import piu.colocviu.makeup.adapter.ProductAdapter
import piu.colocviu.makeup.model.Product
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule


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

        listView.visibility = View.GONE
        progressBar.visibility = View.GONE

        val listView = findViewById<ListView>(R.id.listView)

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

        //val intent = Intent(this, ProductActivity::class.java)

        /*
        // disable-uiesc toate butoanele si orice din pagina, cat timp se incarca produsele (cat timp apare ProgressBar-ul pe ecran)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        );*/

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

            //startActivity(intent);
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


    /*val language = arrayOf<String>("C","C++","Java",".Net","Kotlin","Ruby","Rails","Python","Java Script","Php","Ajax","Perl","Hadoop")
    val description = arrayOf<String>(
        "C programming is considered as the base for other programming languages",
        "C++ is an object-oriented programming language.",
        "Java is a programming language and a platform.",
        ".NET is a framework which is used to develop software applications.",
        "Kotlin is a open-source programming language, used to develop Android apps and much more.",
        "Ruby is an open-source and fully object-oriented programming language.",
        "Ruby on Rails is a server-side web application development framework written in Ruby language.",
        "Python is interpreted scripting  and object-oriented programming language.",
        "JavaScript is an object-based scripting language.",
        "PHP is an interpreted language, i.e., there is no need for compilation.",
        "AJAX allows you to send and receive data asynchronously without reloading the web page.",
        "Perl is a cross-platform environment used to create network and server-side applications.",
        "Hadoop is an open source framework from Apache written in Java."
    )

    val imageId = arrayOf<Int>(
        R.drawable.c_image,R.drawable.cpp_image,R.drawable.java_image,
        R.drawable.net_image,R.drawable.kotlin_image,R.drawable.ruby_image,
        R.drawable.rails_image,R.drawable.python_image,R.drawable.js_image,
        R.drawable.php_image,R.drawable.ajax_image,R.drawable.python_image,
        R.drawable.hadoop_image
    )*/

}