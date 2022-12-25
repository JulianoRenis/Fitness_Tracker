package com.julianorenis.fitness_tracker

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    // private lateinit var btnIMC: LinearLayout
    private lateinit var rvMain: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //1) o layout XML
        //2) A onde a recycleView vai aparecer (Tela principal, tela cheia)
        //3) Lógica - Conectar o xml da célula DENTRO da RecyleView + a sua quatidade de elementos dinâmicos

        val adapter = MainAdapter()
        rvMain = findViewById(R.id.rv_Main)
        rvMain.adapter = adapter
        rvMain.layoutManager = LinearLayoutManager(this)


        // Classe para administrar a recycleView e suas células (os seus layouts de itens)
        //Adapter ->

//        btnIMC = findViewById(R.id.btn_imc)
//
//        btnIMC.setOnClickListener{
//            var i = Intent(this, ImcActivity::class.java)
//            startActivity(i)
//
//        }

    }

    private inner class MainAdapter : RecyclerView.Adapter<MainViewholder>() {

        //1 - Informa qual é o layout XML da célula especifica (item)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewholder {
            val view = layoutInflater.inflate(R.layout.main_item,parent,false)
            Log.i("TesteRecycle","Criando Recycle na tela")
            return MainViewholder(view)
        }

        //2 - Disparado toda ves que houver uma rolagem na tela e for necessario trocar o conteudo
        // da celula
        override fun onBindViewHolder(holder: MainViewholder, position: Int) {
            Log.i("TesteRolagem","Rolagem Funcionando")
        }

        //3- Informar quantas celulas irá usar
        override fun getItemCount(): Int {
            Log.i("TesteItem","Mostrando todos os itens")
            return 50
        }
    }

    // essa é a classe da célula em si!
    private class MainViewholder(view: View) : RecyclerView.ViewHolder(view){

    }
}