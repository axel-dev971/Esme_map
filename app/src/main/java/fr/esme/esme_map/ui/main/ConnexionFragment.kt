package fr.esme.esme_map.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import fr.esme.esme_map.MainActivity
import fr.esme.esme_map.MapActivity
import fr.esme.esme_map.R

class ConnexionFragment(private val context: MainActivity) : Fragment() {

    private val MAP_ACTIVITY = 3

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater?.inflate(R.layout.connexion_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val UserText = activity?.findViewById<EditText>(R.id.name_connexion)

        activity?.findViewById<Button>(R.id.button_connexion)?.setOnClickListener {

            //redirection vers la map
            val myIntent = Intent(context, MapActivity::class.java)
            startActivityForResult(myIntent, MAP_ACTIVITY)

            //Vérification de l'user dans la base de donnée
            if (UserText?.text.toString() == "" ){

            }

            System.out.println(UserText?.text.toString())



        }

    }

}