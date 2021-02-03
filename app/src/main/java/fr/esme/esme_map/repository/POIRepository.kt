package fr.esme.esme_map.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import fr.esme.esme_map.dao.AppDatabase
import fr.esme.esme_map.model.POI
import fr.esme.esme_map.repository.POIRepository.Singleton.POIList
import fr.esme.esme_map.repository.POIRepository.Singleton.databasePOIRef
import kotlin.concurrent.thread

class POIRepository {

    //permet d'accéder à ces deux valeurs dans toute l'application sans la rafraichir
    object Singleton{
        // se connecter a la reference "plants"
        val databasePOIRef = FirebaseDatabase.getInstance("https://esme-map-default-rtdb.firebaseio.com/").getReference("POI")

        //créer une liste qui contient nos plantes
        val POIList = arrayListOf<POI>()
    }

    // fonction de renvoi de tout les POIs
    fun getPOi(db: AppDatabase) {

        databasePOIRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //retirer les anciennes
                //POIList.clear()

                //récolter la liste
                for (ds in snapshot.children) {
                    //var poi = ds.getValue(POI::class.java)
                   var poi = Gson().fromJson(ds.value.toString(),POI::class.java)
                    poi?.let {
                        thread {
                            db.poiDao().createPOI(it)
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }



    //mise à jour d'un POI
    fun updatePOI(poi: POI){
        databasePOIRef.child(poi.uid.toString()).setValue(poi)
    }

    //ajout d'un POI
    fun addPOI(poi: POI){
        //System.out.println(poi)
        databasePOIRef.child(poi.uid.toString()).setValue(poi)
    }

}