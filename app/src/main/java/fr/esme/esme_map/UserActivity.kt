package fr.esme.esme_map

import android.content.Intent
import android.graphics.Color
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import fr.esme.esme_map.interfaces.UserClickInterface
import fr.esme.esme_map.model.Position
import fr.esme.esme_map.model.User
import fr.esme.esme_map.repository.POIRepository
import fr.esme.esme_map.repository.UserRepository

class UserActivity : AppCompatActivity(), OnMapReadyCallback {

    private val TAG = MainActivity::class.qualifiedName
    private lateinit var mMap: GoogleMap
    private var hisPosInitial = LatLng(0.0, 0.0)

    private val POI_ACTIVITY = 1
    private lateinit var fusedLocationClient : FusedLocationProviderClient

    //génération de la carte depuis GoogleMap
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val user: User? = Gson().fromJson(intent.getStringExtra("USER"), User::class.java)

        //Initialisation des POIs de l'utilisateur connecté sur la map
        POIRepository.Singleton.databasePOIRef.child(user?.username.toString()).addListenerForSingleValueEvent(
            object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    //recherche item par item dans la base de donnée
                    for (ds in snapshot.children) {

                        val position = ds.child("position")
                        val latitude = position.child("latitude").value.toString()
                        val longitude = position.child("longitude").value.toString()
                        val name = ds.child("name").value.toString()

                        val poiPos = LatLng(latitude.toDouble(), longitude.toDouble())
                        mMap.addMarker(MarkerOptions().position(poiPos).title(name))

                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })

    }

    //
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        //affichage de la page général
        setContentView(R.layout.activity_user)

        //affichage de maniere asynchrone de la carte dans la vue
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Raffraissement et affichage de ma position sur la vue   ###############################  A CHANGER POUR RECUPERER LA POSITION DE L'UTILISATEUR AMIS DANS LA BASE DE DONNE
/*        viewModel.myPositionLiveData.observe(this, { position ->
            showHisPosition(position)
        })*/

    }

    //affichage de ma position par une pastille noir
    //TODO show MyPosition
    fun showHisPosition(position: Position) {

        //mettre une condition sur la position pour éviter le rafraichissement de la page à chaque requète
        val myPos = LatLng(position.latitude, position.longitude)

        if (hisPosInitial != myPos){
            hisPosInitial = myPos
            UserRepository().UpdateMyPosition(position.latitude, position.longitude)

            //création du point de position
            val circleOptions = CircleOptions()
            circleOptions.center(myPos)
            circleOptions.radius(80.0)
            circleOptions.strokeColor(Color.WHITE)
            circleOptions.fillColor(Color.BLUE)
            circleOptions.strokeWidth(6f)

            if(this::hisPositionCircle.isInitialized) {
                hisPositionCircle.remove()
            }

            hisPositionCircle =  mMap.addCircle(circleOptions)

            //zoom et recentrage de la vue sur la position
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPos, 14f))
        }
    }

    lateinit var hisPositionCircle : Circle


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    var myPosition : Location? = null

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
        mMap.clear()
    }




}