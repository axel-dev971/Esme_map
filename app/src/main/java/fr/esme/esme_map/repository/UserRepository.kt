package fr.esme.esme_map.repository

import com.google.firebase.database.*
import com.google.gson.Gson
import fr.esme.esme_map.dao.AppDatabase
import fr.esme.esme_map.model.POI
import fr.esme.esme_map.model.User
import fr.esme.esme_map.repository.UserRepository.Singleton.FriendsList
import fr.esme.esme_map.repository.UserRepository.Singleton.UserList
import fr.esme.esme_map.repository.UserRepository.Singleton.databaseUserRef
import fr.esme.esme_map.ui.main.ConnexionFragment
import javax.security.auth.callback.Callback
import kotlin.concurrent.thread


class UserRepository {


    //permet d'accéder à ces deux valeurs dans toute l'application sans la rafraichir
    object Singleton{
        // se connecter a la reference "User"
        val databaseUserRef = FirebaseDatabase.getInstance("https://esme-map-default-rtdb.firebaseio.com/").getReference(
            "User"
        )

        //créer une liste qui contient nos plantes
        val UserList = arrayListOf<User>()
        val FriendsList = arrayListOf<User>()
    }

    // fonction de renvoi de tout les utiliseateurs
    fun getFriends(callback: () -> Unit) {

        databaseUserRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //retirer les anciennes
                FriendsList.clear()

                //récolter la liste
                for (ds in snapshot.children){
                    //construire un objet Plante

                    if (ConnexionFragment.Singleton.UserCurrent != ds.child("username").value){
                        val user = ds.getValue(User::class.java)

                        //verifier que la plant n'est pas null
                        if (user != null ){
                            //ajouter la plante à notre liste
                            FriendsList.add(user)
                        }
                    }

                }
                callback()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }



    //mise à jour d'un utilisateur
    fun updateUser(user: User){
        databaseUserRef.child(user.username.toString()).setValue(user)
    }

    fun UpdateMyPosition(latitude : Double, longitude : Double){
        databaseUserRef.child(ConnexionFragment.Singleton.UserCurrent).child("myPostion").removeValue()
        databaseUserRef.child(ConnexionFragment.Singleton.UserCurrent).child("myPostion").child("latitude").setValue(latitude)
        databaseUserRef.child(ConnexionFragment.Singleton.UserCurrent).child("myPostion").child("longitude").setValue(longitude)

    }

    //ajout d'un utilisateur
    fun addUser(user: User){
        //System.out.println(poi)
        databaseUserRef.child(user.username.toString()).setValue(user)
    }





}