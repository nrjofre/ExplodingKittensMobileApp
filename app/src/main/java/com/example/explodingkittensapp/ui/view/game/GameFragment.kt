package com.example.explodingkittensapp.ui.view.game

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.explodingkittensapp.APImodels.Bodies.APIGameParticipants
import com.example.explodingkittensapp.APImodels.Bodies.APIMyturn
import com.example.explodingkittensapp.APImodels.Bodies.APIPlay
import com.example.explodingkittensapp.APImodels.Bodies.APIRejectInvite
import com.example.explodingkittensapp.R
import com.example.explodingkittensapp.activities.MainActivity
import com.example.explodingkittensapp.activities.OnClickListener
import com.example.explodingkittensapp.model.CardModel
import com.example.explodingkittensapp.ui.viewmodel.GameViewModel
import com.example.explodingkittensapp.ui.viewmodel.MyGamesViewModel
import com.example.explodingkittensapp.ui.viewmodel.UserViewModel


class GameFragment : Fragment(), OnClickListener {

    lateinit var gameAdapter: PlayerDeckRecyclerViewAdapter
    lateinit var otherPlayersadapter: OtherPlayersRecyclerViewAdapter
    lateinit var recyclerView1: RecyclerView
    lateinit var recyclerView2: RecyclerView
    private val cardsGameViewModel: GameViewModel by activityViewModels()
    private val playersGameViewModel: GameViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private val gameViewModel: MyGamesViewModel by activityViewModels()

    var handler: Handler = Handler()
    var runnable: Runnable? = null
    var delay = 4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cardsGameViewModel.setNavigator(activity as MainActivity)
        val aux = gameViewModel.chosenMyGames.value?.let { APIMyturn(it.gamename, userViewModel.uname) }

        if (aux != null) {
            cardsGameViewModel.getMyTurn(aux)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val uname = userViewModel.uname
        val gamename = gameViewModel.gamename
        val lastcard = gameViewModel.lastcard
        //val turn = gameViewModel.chosenMyGames.value?.turn

        val view = inflater.inflate(R.layout.fragment_game, container, false)

        val drawbtn : Button = view.findViewById(R.id.drawbtn)

        val textturn = view.findViewById<TextView>(R.id.gameName)
        // actualizo si el botton de draw card se puede usar
        if ((gameViewModel.turn % (playersGameViewModel.players.size + 1)).toString() == cardsGameViewModel.myturn){
            drawbtn?.isClickable = true
            textturn.text = "your turn"

        }else{
            drawbtn?.isClickable = false
            textturn.text = "wait for your turn"
        }

        cardsGameViewModel.cardsAPI(uname)

        //cards recyclerview
        recyclerView1 = view.findViewById(R.id.playerDeckRecyclerView)
        gameAdapter = PlayerDeckRecyclerViewAdapter(this)
        gameAdapter.isClickable = false
        recyclerView1.adapter = gameAdapter
        recyclerView1.layoutManager = GridLayoutManager(activity,1, LinearLayoutManager.HORIZONTAL,false)

        cardsGameViewModel.gameLiveData.observe(viewLifecycleOwner, Observer {
            gameAdapter.set(it)
        })

        //participants recycler view
        val newAPIGameParticipants = APIGameParticipants(uname, gamename)
        playersGameViewModel.playersAPI(newAPIGameParticipants)
        recyclerView2 = view.findViewById(R.id.otherPlayersRecyclerView)
        otherPlayersadapter = OtherPlayersRecyclerViewAdapter(this)
        recyclerView2.adapter = otherPlayersadapter
        recyclerView2.layoutManager = GridLayoutManager(activity,1, LinearLayoutManager.HORIZONTAL,false)

        playersGameViewModel.playersLiveData.observe(viewLifecycleOwner, Observer {
            otherPlayersadapter.set(it)
        })

        if (!drawbtn.isClickable){
            drawbtn.setOnClickListener{
                cardsGameViewModel.drawCard(uname, gamename, activity, view)
            }
        }

        return view
    }

    override fun onClickItem(item: Any) {
        if (item is CardModel){
            //Jugar una carta
            val play = APIPlay(item.id, userViewModel.uname, gameViewModel.gamename)
            cardsGameViewModel.playCard(play)

            cardsGameViewModel.cards.remove(item)
            cardsGameViewModel.gameLiveData.value = cardsGameViewModel.cards

        }
        else{
            TODO("Not yet implemented")
        }

    }

    // para que corra cada 5 segundos
    override fun onResume() {
        handler.postDelayed(Runnable {
            handler.postDelayed(runnable!!, delay.toLong())
            //updatea los participantes
            var apipart = APIGameParticipants(userViewModel.uname, gameViewModel.gamename)
            playersGameViewModel.playersAPI(apipart) //actualiza playerslivedata
            //updatea las cartas de la mano                         y la postea a la api
            cardsGameViewModel.cardsAPI(userViewModel.uname) //actualiza gamelivedata
            //updatea el juego que se esta jugando para ver el turno en el que esta y la ultima carta
            if (playersGameViewModel.players.size != 0){
                gameViewModel.myGamesAPI(userViewModel.uname) //actualiza los games para el jugador
                gameViewModel.updateMyGames(gameViewModel.gamename) //actualiza turn y lastcard del game actual
                cardsGameViewModel.getMyTurn(APIMyturn(gameViewModel.gamename,userViewModel.uname))
            }

            val view = view
            // actualizo la ultima carta utilizada
            val drawbtn = view?.findViewById<Button>(R.id.drawbtn)
            val lastcardimageview = view?.findViewById<ImageView>(R.id.lastCardImageView)


            // agregar cada caso de cartas a medida que se van implementando mas cartas
            if (gameViewModel.lastcard == "5GKopmheJVBVJwcEMWtI") { // kitten
                lastcardimageview?.setImageResource(R.drawable.kitten1)
            }
            else if (gameViewModel.lastcard == "PHKvNq2afTrKUHYznI1W"){ // skip
                lastcardimageview?.setImageResource(R.drawable.skip)
            }
            else if (gameViewModel.lastcard == "5VYvZ4k72Y2fbfEmGdiV"){ // defuse
                lastcardimageview?.setImageResource(R.drawable.defuse)
            }
            else if (gameViewModel.lastcard == ""){ // none
                lastcardimageview?.setImageResource(R.drawable.backcard)
            }
            else if (gameViewModel.lastcard == "vvqEBQusOvfprXO7eB7S"){ // attack
                lastcardimageview?.setImageResource(R.drawable.attack)
            }
            else if (gameViewModel.lastcard == "xwFAlcipGg6wXHryomoG"){ // shuffle
                lastcardimageview?.setImageResource(R.drawable.shuffle)
            }

            val textturn = view?.findViewById<TextView>(R.id.gameName)
            // actualizo si el botton de draw card se puede usar
            if ((gameViewModel.turn % (playersGameViewModel.players.size + 1)).toString() == cardsGameViewModel.myturn){
                drawbtn?.isClickable = true
                if (textturn != null) {
                    textturn.text = "Is Your Turn"
                }
            }else{
                drawbtn?.isClickable = false
                if (textturn != null) {
                    textturn.text = "Wait For Your Turn"
                }
            }

            if ((gameViewModel.turn % (playersGameViewModel.players.size + 1)).toString() == cardsGameViewModel.myturn){
                gameAdapter.isClickable = true
            }else{
                gameAdapter.isClickable = false
            }

            if (playersGameViewModel.players.size == 0){
                Toast.makeText(activity, "Congratulations, You Won", Toast.LENGTH_LONG).show()
                if (view != null) {
                    Navigation.findNavController(view).popBackStack()
                    Navigation.findNavController(view).popBackStack()
                    cardsGameViewModel.wingame(userViewModel.uname)
                }
            }


        }.also { runnable = it }, delay.toLong())
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable!!)
    }


}