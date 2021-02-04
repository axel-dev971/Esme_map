package fr.esme.esme_map.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import fr.esme.esme_map.MainActivity
import fr.esme.esme_map.MapActivity
import fr.esme.esme_map.R
import fr.esme.esme_map.model.User
import fr.esme.esme_map.repository.UserRepository
import kotlin.concurrent.thread

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


            UserRepository.Singleton.databaseUserRef.addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    //récolter la liste
                    for (ds in snapshot.children) {

                        System.out.println(ds.child("username").value)
                        //Vérification de l'user dans la base de donnée
                        if (UserText?.text.toString() == ds.child("username").value ){

                            //redirection vers la map
                            val myIntent = Intent(context, MapActivity::class.java)
                            startActivityForResult(myIntent, MAP_ACTIVITY)
                        }

                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })




            System.out.println(UserText?.text.toString())



        }

    }

}