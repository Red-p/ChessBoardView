package androidx.cap.app.chessboardpersdchessreferee


import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cap.app.chessboardpersdchessreferee.controller.ChessBoardAdapter
import androidx.cap.app.chessboardpersdchessreferee.controller.ChessBoardController
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    private lateinit var chessBoard: GridView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chessBoard = findViewById(R.id.chessBoard)
        val controller=ChessBoardController()


        val editText=findViewById<EditText>(R.id.mossa)
        val button= findViewById<Button>(R.id.move)

        val adapter = ChessBoardAdapter(this, controller.get_board_as_List())
        chessBoard.adapter = adapter
        button.setOnClickListener {


            controller.move(editText.text.toString())

            adapter.notifyDataSetChanged()
            controller.switchPlayer()

        }


        controller.checkLiveData().observe(this, androidx.lifecycle.Observer {
            if(it=="CHECKMATE") {

                val toneGen1 = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
                toneGen1.startTone(ToneGenerator.TONE_SUP_PIP, 150)
                AlertDialog.Builder(this).setMessage("CHECKMATE !!").show()
            }
            else {
                val toneGen1 = ToneGenerator(AudioManager.STREAM_MUSIC, 50)
                toneGen1.startTone(ToneGenerator.TONE_PROP_BEEP2, 150)
                Toast.makeText(this, "CHECK!!", Toast.LENGTH_SHORT).show()

            }
        })


    }


}