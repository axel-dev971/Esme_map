package fr.esme.esme_map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.esme.esme_map.repository.UserRepository
import fr.esme.esme_map.ui.main.ConnexionFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_connexion)


        //injecter le fragment dans notre boite (fragment_container)
        val transaction = supportFragmentManager.beginTransaction() //instantiation de la classe
        transaction.replace(
            R.id.fragement_connexion,
            ConnexionFragment(this)
        ) // on lui indique par quoi on veut remplacer le fragment container
        transaction.addToBackStack(null) // nulle car on ne veut pas de retour
        transaction.commit()





    }


    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val enterText = activity?.findViewById<EditText>(R.id.POINameEditText)

        activity?.findViewById<Button>(R.id.validateButton)?.setOnClickListener {


            //TODO REcuperer depuis le formulaire

            var poi: POI = POI(
                //################# peut etre à changer par un identifiant plus simple ######################################################################
                Any().hashCode(),
                //User("JP"),
                editiText?.text.toString(),
                5,
                Position(viewModel.latlng.latitude, viewModel.latlng.longitude),
                // Category("Culture", Color())
            )

            System.out.println(poi.uid)
            System.out.println("\n\n")

            var string = Gson().toJson(poi)
            System.out.println(string)

            val intent = Intent().putExtra("poi", string)

            POIRepository().addPOI( poi)

            activity?.setResult(1, intent)

            //Close activity
            activity?.finish()

        }
    }*/

}