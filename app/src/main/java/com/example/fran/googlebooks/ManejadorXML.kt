package com.example.fran.googlebooks

import org.xml.sax.Attributes
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler
/**
 * Created by Fran on 21/02/2018.
 */
class ManejadorXML : DefaultHandler() {

    private var totalResults: String? = null
    private val cadena = StringBuilder()

    fun getTotalResults(): String {
        return this.totalResults!!
    }

    @Throws(SAXException::class)
    override fun startElement(uri: String, nombreLocal: String, nombreCualif: String, atributos: Attributes) {
        cadena.setLength(0)
    }

    override fun characters(ch: CharArray, comienzo: Int, lon: Int) {
        cadena.append(ch, comienzo, lon)
    }

    @Throws(SAXException::class)
    override fun endElement(uri: String, nombreLocal: String, nombreCualif: String) {
        if (nombreLocal == "totalResults") {
            totalResults = cadena.toString()
        }
    }

}
