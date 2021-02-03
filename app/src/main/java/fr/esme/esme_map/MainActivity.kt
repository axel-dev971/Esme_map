package fr.esme.esme_map

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.room.Room
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import fr.esme.esme_map.dao.AppDatabase
import fr.esme.esme_map.interfaces.UserClickInterface
import fr.esme.esme_map.model.POI
import fr.esme.esme_map.model.Position
import fr.esme.esme_map.model.User
import fr.esme.esme_map.repository.POIRepository
import fr.esme.esme_map.ui.main.ConnexionFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_connexion)

        //injecter le fragment dans notre boite (fragment_container)
        val transaction = supportFragmentManager.beginTransaction() //instantiation de la classe
        transaction.replace(R.id.fragement_connexion, ConnexionFragment(this)) // on lui indique par quoi on veut remplacer le fragment container
        transaction.addToBackStack(null) // nulle car on ne veut pas de retour
        transaction.commit()

        //charger notre plante repository pour la connection à la base de donnée
        //val repo = UserRepository()

        //mettre à jour la liste de plantes
        //repo.updateData{
            //injecter le fragment dans notre boite (fragment_container)

        //}



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