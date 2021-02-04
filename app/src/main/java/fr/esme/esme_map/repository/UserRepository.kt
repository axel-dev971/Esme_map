package fr.esme.esme_map.repository

import com.google.firebase.database.*
import com.google.gson.Gson
import fr.esme.esme_map.dao.AppDatabase
import fr.esme.esme_map.model.User
import fr.esme.esme_map.repository.UserRepository.Singleton.databaseUserRef
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
    }

    // fonction de renvoi de tout les utiliseateurs
    fun getUser(db: AppDatabase) {

        databaseUserRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                //récolter la liste
                for (ds in snapshot.children) {
                    var user = Gson().fromJson(ds.value.toString(), User::class.java)
                    user?.let {
                        thread {
                            db.userDao().createUser(it)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }



    //mise à jour d'un utilisateur
    fun updateUser(user: User){
        databaseUserRef.child(user.username.toString()).setValue(user)
    }

    //ajout d'un utilisateur
    fun addUser(user: User){
        //System.out.println(poi)
        databaseUserRef.child(user.username.toString()).setValue(user)
    }





}