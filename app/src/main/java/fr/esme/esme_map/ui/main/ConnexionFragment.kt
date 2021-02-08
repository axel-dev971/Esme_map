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
import fr.esme.esme_map.MainActivity
import fr.esme.esme_map.MapActivity
import fr.esme.esme_map.R
import fr.esme.esme_map.repository.UserRepository
import fr.esme.esme_map.ui.main.ConnexionFragment.Singleton.UserCurrent


class ConnexionFragment(context: MainActivity) :  Fragment(){
    val MAP_ACTIVITY = 3

    constructor() : this(MainActivity()) {}



        object Singleton {

            //créer une liste qui contient nos plantes
            lateinit var UserCurrent: String
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater?.inflate(R.layout.connexion_fragment, container, false)
            return view
        }


        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)

            val UserText = activity?.findViewById<EditText>(R.id.name_connexion)

            activity?.findViewById<Button>(R.id.button_connexion)?.setOnClickListener {


                UserRepository.Singleton.databaseUserRef.addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        //recherche item par item dans la base de donnée
                        for (ds in snapshot.children) {
                            //Vérification de l'user dans la base de donnée
                            if (UserText?.text.toString() == ds.child("username").value) {

                                //enregistrement du pseudo de l'utilisateur dans une variable
                                UserCurrent = UserText?.text.toString()

                                //redirection vers la map
                                val myIntent = Intent(context, MapActivity::class.java)
                                startActivityForResult(myIntent, MAP_ACTIVITY)


                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
            }
        }


}