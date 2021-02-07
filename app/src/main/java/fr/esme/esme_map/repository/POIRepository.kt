package fr.esme.esme_map.repository

import android.content.Intent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import fr.esme.esme_map.dao.AppDatabase
import fr.esme.esme_map.model.POI
import fr.esme.esme_map.repository.POIRepository.Singleton.UserPOIList
import fr.esme.esme_map.repository.POIRepository.Singleton.databasePOIRef
import kotlin.concurrent.thread

class POIRepository {

    //permet d'accéder à ces deux valeurs dans toute l'application sans la rafraichir
    object Singleton{
        // se connecter a la reference "plants"
        val databasePOIRef = FirebaseDatabase.getInstance("https://esme-map-default-rtdb.firebaseio.com/").getReference("POI")

        //créer une liste qui contient nos plantes
        var UserPOIList = arrayListOf<POI>()
    }

    // fonction de renvoi de tout les POIs de tout les utilisateurs
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

    // fonction de renvoi de tout les POIs d'un utilisateur
    fun getUserPOi(username : String,callback: () -> Unit)  {
        var listePOI = arrayListOf<POI>()
        databasePOIRef.child(username).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                //UserPOIList.clear()
                //recherche item par item dans la base de donnée
                for (ds in snapshot.children) {

                        var poi = Gson().fromJson(ds.value.toString(),POI::class.java)

                        if (poi != null ){
                            //System.out.println("User : " + UserPOIList)
                            UserPOIList.add(poi)
                        }

                    //Construction d'un objet POI
                    //var poi = ds.getValue(POI::class.java)
                }
                callback()
            }
            override fun onCancelled(error: DatabaseError) {}
        })
        System.out.println("Liste : "+UserPOIList)
    }


    //mise à jour d'un POI
    fun updatePOI(poi: POI){
        databasePOIRef.child(poi.uid.toString()).setValue(poi)
    }

    //ajout d'un POI
    fun addPOI(username : String ,poi: POI){
        //System.out.println(poi)
        databasePOIRef.child(username).child(poi.uid.toString()).setValue(poi)
    }

}