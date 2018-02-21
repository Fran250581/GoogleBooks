package com.example.fran.googlebooks

import android.app.ProgressDialog
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import java.net.URL
import java.net.URLEncoder
import org.xml.sax.InputSource
import javax.xml.parsers.SAXParserFactory


class MainActivity : AppCompatActivity() {

    private var entrada: EditText? = null
    private var salida: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        entrada = findViewById<View>(R.id.EditText01) as EditText?
        salida = findViewById<View>(R.id.TextView01) as TextView
    }

    fun buscar(view: View) {
        val palabras = entrada!!.getText().toString()
        salida!!.append(palabras + "--")
        BuscarGoogle().execute(palabras)
    }

    private fun resultadosSW(palabras: String): String {
        val url = URL("http://books.google.com/books/feeds/volumes?q=\""
                + URLEncoder.encode(palabras, "UTF-8") + "\"")
        val factory = SAXParserFactory.newInstance()
        val parser = factory.newSAXParser()
        val reader = parser.getXMLReader()
        val manejadorXML = ManejadorXML()
        reader.setContentHandler(manejadorXML)
        reader.parse(InputSource(url.openStream()))
        return manejadorXML.getTotalResults()
    }

    inner class BuscarGoogle : AsyncTask<String, Void, String>() {

        private var progreso: ProgressDialog? = null

        override fun onPreExecute() {
            progreso = ProgressDialog(this@MainActivity)
            progreso!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            progreso!!.setMessage("Accediendo a Google...")
            progreso!!.setCancelable(false)
            progreso!!.show()
        }

        override fun doInBackground(vararg palabras: String): String? {
            try {
                return resultadosSW(palabras[0])
            }
            catch (e: Exception) {
                cancel(true)
                Log.e("HTTP", e.message, e)
                return null
            }
        }

        override fun onPostExecute(res: String) {
            progreso!!.dismiss()
            salida!!.append(res + "\n")
        }

        override fun onCancelled() {
            progreso!!.dismiss()
            salida!!.append("Error al conectar\n")
        }
    }

}

