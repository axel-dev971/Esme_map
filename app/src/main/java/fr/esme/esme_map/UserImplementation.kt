package fr.esme.esme_map

import fr.esme.esme_map.dao.AppDatabase
import fr.esme.esme_map.interfaces.UserInterface
import fr.esme.esme_map.model.*
import fr.esme.esme_map.repository.POIRepository

class UserImplementation(val user: User, val appDatabase: AppDatabase) : UserInterface {

    //Récupération et implémantation de la liste des POI
    /*override fun getPOIs(): List<POI> {
        return appDatabase.poiDao().getPOIs()
    }*/

    //Récupération et implémantation de la liste des POIs d'un utilisateur
    /*override fun getUserPOIs(username: String): List<POI> {
        return POIRepository().getUserPOi(username)
    }*/

    //Récupération et implémantation de la liste des POIs pour la création d'une course
    /* override fun getRuns(): List<Run> {
        //TODO call network  ===> DB distance ===> DB ====> Code
        return listOf(
            Run(
                user,
                "Footing Paris",
                5,
                listOf(Position(0.0, 0.0)),
                Category("Sport", 12)
            ),
            Run(
                user,
                "Footing Versaille",
                5,
                listOf(Position(0.0, 0.0)),
                Category("Sport", 12)
            )
        )
    }*/

    /*override fun getMyPosition(): Position {
        //TODO ask GPS TD
        return Position(5.3522219, 76.856614)
    }*/


    //override fun getUsers(): List<User> {
     /*   var riri = User("RIRI")
        var fifi = User("FIFI")
        var loulou = User("LOULOU")

        riri.imageUrl = "https://www.nautiljon.com/images/people/01/65/riri_chanteuse_83056.jpg"
        fifi.imageUrl = "https://i.imgur.com/DvpvklR.png"

        return listOf(
            riri,
            fifi,
            loulou
        )*/
   // }

}