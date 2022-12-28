package com.julianorenis.fitness_tracker

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.julianorenis.fitness_tracker.model.Calc

class ImcActivity : AppCompatActivity() {

    private lateinit var editWeigth: EditText
    private lateinit var editHeigth: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)

        editWeigth = findViewById(R.id.edtPeso)
        editHeigth = findViewById(R.id.edtAltura)

        val btnSend: Button = findViewById(R.id.btnCalcular)

        btnSend.setOnClickListener {

            if (!validate()) {
                Toast.makeText(this, R.string.fields_messages, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val weight = editWeigth.text.toString().toInt()
            val height = editHeigth.text.toString().toInt()

            val resultForm = calculateImc(weight, height)

            val imcResponseId = imcResponse(resultForm)
            // AlertDialog

            // Codigo mais comum ---------------------------------//
            val diaglog = AlertDialog.Builder(this)
            diaglog.setTitle(getString(R.string.imc_response, resultForm))
            diaglog.setMessage(R.string.calcular)
            diaglog.setPositiveButton(android.R.string.ok,
                object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        //  código roda aqui depois do clique
                    }

                })
            val d = diaglog.create()
            d.show()
            //---------------------------------------------------//


            // Código mais límpo
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.imc_response, resultForm))
                .setMessage(R.string.calcular)
                .setPositiveButton(android.R.string.ok) { dialog, which -> // Usando o lambda
                    //código roda aqui depois do clique
                }
                .setNegativeButton(R.string.save) { dialog, which ->

                    Thread {
                        val app = application as App
                        val dao = app.db.calcDao()
                        dao.insert(Calc(type = "imc", res = resultForm))

                        runOnUiThread{
                            val intent = Intent(this@ImcActivity,ListCalc::class.java)
                            intent.putExtra("data","data")
                            intent.putExtra("type","imc")
                            intent.putExtra("valor","valor")
                            startActivity(intent)
                        }
                    }.start()

                }
                .create()
                .show()

            // chamada de serviços do aparelho -> Fechando teclado ao mostrar alert
            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }

    }

    @StringRes
    private fun imcResponse(imc: Double): Int {
        //Código Clean code
        // substitui o if else (){}
        return when {
            imc < 15.0 -> R.string.imc_severely_low_weight
            imc < 16.0 -> R.string.imc_very_low_weight
            imc < 18.5 -> R.string.imc_low_weight
            imc < 25.0 -> R.string.normal
            imc < 30.0 -> R.string.imc_high_weight
            imc < 35.0 -> R.string.imc_so_high_weight
            imc < 40.0 -> R.string.imc_severely_high_weight
            else -> R.string.imc_extreme_weight
        }
        //Funcional porém dá pra melhorar usando o when do Kotlin
//        if(imc < 15.0){
//            return R.string.imc_severely_low_weight
//        } else if(imc < 16.0){
//            return  R.string.imc_very_low_weight
//        }else if(imc < 18.5){
//            return  R.string.imc_low_weight
//        }else if(imc < 25.0){
//            return  R.string.normal
//        }else if(imc < 30.0){
//            return  R.string.imc_high_weight
//        }else if(imc < 35.0){
//            return  R.string.imc_so_high_weight
//        }else if(imc < 40.0){
//            return  R.string.imc_severely_high_weight
//        } else{
//            return R.string.imc_extreme_weight
//        }
    }

    private fun calculateImc(weight: Int, height: Int): Double {
        // peso / (altura * altura)
        return weight / ((height / 100.0) * (height / 100.0))
    }

    private fun validate(): Boolean {
        //quando o metodo restonar boolean e for necessário fazer
        // varios if e elses posso fazer dessa forma para enchugar o codigo
        return editWeigth.text.toString().isNotEmpty() && editHeigth.text.toString()
            .isNotEmpty() && !editWeigth.text.toString()
            .startsWith("0") && !editHeigth.text.toString().startsWith("0")
    }
}