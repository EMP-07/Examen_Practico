package com.isengard.fruegas

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //vincula los elementos de la parte gráfica
        val nombre = findViewById<EditText>(R.id.nombreUh)
        val rol = findViewById<Spinner>(R.id.TipUd)
        val equipo = findViewById<RadioGroup>(R.id.cajaOpc)
        val armadura = findViewById<RadioButton>(R.id.opc1)
        val escudo = findViewById<RadioButton>(R.id.opc2)
        val antorcha = findViewById<CheckBox>(R.id.cajaCheck)
        val envio = findViewById<ImageButton>(R.id.envioTp)

        Log.d("FraguasIsengard", "onCreate: Las fraguas de Isengard se han forjado en la memoris")

        //pasa los datos del spinner desde strings.xml
        val adaptador = ArrayAdapter.createFromResource(
            this,
            R.array.tiposUnidad,
            android.R.layout.simple_spinner_item
        )
        //diseño del menú desplegable
        adaptador.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        rol.adapter = adaptador

        //foco inicial al nombre/EditText
        nombre.requestFocus()

        //comprobar si el usuario sale del foco del campo nombre
        nombre.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && nombre.text.toString().trim().isEmpty()) {
                nombre.error = "El ejército no acepta soldados anónimos"
            }
        }

        //lo que está dentro de las llaves se ejecutará cuando pulsen el botón
        envio.setOnClickListener {
            //leemos el texto que escribió el usuario
            val textoEnvio = nombre.text.toString().trim()

            //comprueba si está vacío. Si está vacío, detenemos el botón
            if (textoEnvio.isEmpty()) {
                nombre.error = "No se acepta soldados anónimos"
                nombre.requestFocus()
                return@setOnClickListener
            }

            val llevarAntorcha = antorcha.isChecked
            if (!antorcha.isChecked) {
                Log.e("error", "¡Peligro! Unidad enviada sin fuego")
            }
                //si el nombre es válido, lee los demás componentes
                val tipoUnidad = rol.selectedItem.toString()

                //lee si seleccionó armadura, escudo o antorcha:
                val opcEquipoSelec = equipo.checkedRadioButtonId

                val tieneArmadura = (opcEquipoSelec == R.id.opc1)
                val tieneEscudo = (opcEquipoSelec == R.id.opc2)

                var equipamiento = when {
                    tieneArmadura -> "Armadura"
                    tieneEscudo -> "Escudo"
                    else -> "Ninguno"
                }

                if (llevarAntorcha) {
                    equipamiento += " y Antorcha"
                }

                //muestra el toast de larga duración con un mensaje
                Toast.makeText(
                    this,
                    "¡Unidad " + textoEnvio + " enviada al Abismo de Helm!",
                    Toast.LENGTH_LONG
                ).show();
            }
        }

        override fun onStart() { //cuando la pantalla empieza a ser visble para el usuario
            super.onStart()
            Log.d("FraguasIsengard", "onStart: Las frasguas se encienden")
        }

         override fun onResume() { //cuando ya se puede interactuar con la pantalla
            super.onResume()
            Log.d("FraguasIsengard", "onResume: Las fraguas están listas para comenzar")
        }

        override fun onPause() { //cuando en la pantalla principal aparece otra por encima, temporal o permanentemente
            super.onPause()
            Log.d("FraguasIsengard", "onPause: Saruman detiene la producción temporalmente")
        }

        override fun onStop() { //cuando la pantalla deja de ser visible para el usuario
            super.onStop()
            Log.d("FraguasIsengard", "onStop: Saruman detiene la producción definitivamente")
        }

        override fun onDestroy() { //cuando la pantalla desaparece definitivamente, como al cerrar el programa
            super.onDestroy()
            Log.d("FraguasIsengard", "onDestroy: las fraguas son destruidas")
        }

        //para guardar los datos al girar la pantalla
        override fun onSaveInstanceState(outState: Bundle) {
            super.onSaveInstanceState(outState)
            val CampoNombre =findViewById<EditText>(R.id.nombreUh)
            if (CampoNombre.error !=null) {
                outState.putString("errorP", CampoNombre.error.toString())
            }
        }
}

