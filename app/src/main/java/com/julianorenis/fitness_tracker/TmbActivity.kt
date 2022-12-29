package com.julianorenis.fitness_tracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.julianorenis.fitness_tracker.model.Calc

class TmbActivity : AppCompatActivity() {

    private lateinit var editWeigth: EditText
    private lateinit var editHeigth: EditText
    private lateinit var editAge: EditText

    private lateinit var lifestyle: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tmb)

        editWeigth = findViewById(R.id.edt_tmb_Peso)
        editHeigth = findViewById(R.id.edt_tmb_Altura)
        editAge    = findViewById(R.id.edt_tmb_Age)

        lifestyle = findViewById(R.id.auto_lifeStyle)
        val items = resources.getStringArray(R.array.tmb_lifeStyle)
        lifestyle.setText(items.first())
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        lifestyle.setAdapter(adapter)

        val btnSend: Button = findViewById(R.id.btn_tmb_Calcular)

        btnSend.setOnClickListener{
            if (!validate()) {
                Toast.makeText(this, R.string.fields_messages, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val weight = editWeigth.text.toString().toInt()
            val height = editHeigth.text.toString().toInt()
            val age = editAge.text.toString().toInt()
            
            val result = calculateTmb(weight,height,age)
            val response = tmbRequest(result)

            // Código mais límpo
            AlertDialog.Builder(this)
                .setMessage(getString(R.string.imc_response, response))
                .setPositiveButton(android.R.string.ok) { dialog, which -> // Usando o lambda
                    //código roda aqui depois do clique
                }
                .setNegativeButton(R.string.save) { dialog, which ->

                    Thread {
                        val app = application as App
                        val dao = app.db.calcDao()
                        dao.insert(Calc(type = "tmb", res = response))

                        runOnUiThread {
                            openListActivity()
                        }
                    }.start()

                }
                .create()
                .show()



        }
    }

    private fun tmbRequest(tbm: Double): Double{
        val items = resources.getStringArray(R.array.tmb_lifeStyle)
        return when {

            // pode ser feito com if e else
            lifestyle.text.toString()==items[0] -> tbm * 1.2
            lifestyle.text.toString()==items[1] -> tbm * 1.375
            lifestyle.text.toString()==items[2] -> tbm * 1.55
            lifestyle.text.toString()==items[3] -> tbm * 1.725
            lifestyle.text.toString()==items[4] -> tbm * 1.9
            else -> 0.0
        }
    }

    private fun calculateTmb(weight: Int, height: Int, age: Int): Double {
        return 66 + (13.8 * weight)+ (5 * height) - (6.8 * age)

    }

    private fun validate(): Boolean {
        //quando o metodo restonar boolean e for necessário fazer
        // varios if e elses posso fazer dessa forma para enchugar o codigo
        return     editWeigth.text.toString().isNotEmpty()
                && editHeigth.text.toString().isNotEmpty()
                && editAge.text.toString().isNotEmpty()
                && !editWeigth.text.toString().startsWith("0")
                && !editHeigth.text.toString().startsWith("0")
                && !editAge.text.toString().startsWith("0")
    }
    private fun openListActivity() {
        val intent = Intent(this, ListCalc::class.java)
        intent.putExtra("type", "tmb")
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.buscar_id){
            finish()
            openListActivity()
        }
        return super.onOptionsItemSelected(item)
    }



}