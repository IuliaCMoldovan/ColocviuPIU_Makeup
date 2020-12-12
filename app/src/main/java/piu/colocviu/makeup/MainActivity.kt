package piu.colocviu.makeup

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.iterator
import kotlinx.android.synthetic.main.activity_main.*


/* implementeaza interfata deoarece e nevoie sa se schimbe lista de elemente afisate, cand se
    schimba elementul selectat */
class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var currentCategory = "none"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_parfum -> {
                Toast.makeText(this, "Parfumuri", Toast.LENGTH_SHORT).show()
                currentCategory = "Parfum"
            }
            R.id.nav_ruj -> {
                Toast.makeText(this, "Rujuri", Toast.LENGTH_SHORT).show()
                currentCategory = "Ruj"
            }
            R.id.nav_eyeliner -> {
                Toast.makeText(this, "Eyeline-uri", Toast.LENGTH_SHORT).show()
                currentCategory = "Eyeliner"
            }
            R.id.nav_blush -> {
                Toast.makeText(this, "Blush-uri", Toast.LENGTH_SHORT).show()
                currentCategory = "Blush"
            }

        }

        emptyPage.visibility = View.GONE
        invalidateOptionsMenu()
        showProducts(currentCategory)

        return super.onOptionsItemSelected(item)
    }

    private fun showProducts(category: String) {

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