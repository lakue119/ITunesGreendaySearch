package com.lakue.itunesgreendaysearch.ui.music

import android.content.Intent
import android.graphics.Typeface
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.lakue.itunesgreendaysearch.R
import com.lakue.itunesgreendaysearch.base.BaseActivity
import com.lakue.itunesgreendaysearch.databinding.ActivityErrorBinding
import com.lakue.itunesgreendaysearch.databinding.ActivityMusicBinding
import com.lakue.itunesgreendaysearch.model.Track
import com.lakue.itunesgreendaysearch.ui.error.ErrorViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MusicActivity : BaseActivity<ActivityMusicBinding, MusicViewModel>(R.layout.activity_music) {

    var isStart = false

    companion object {
        const val EXTRA_TRACK = "EXTRA_TRACK"
    }

    lateinit var mediaPlayer: MediaPlayer


    private val track by lazy { intent.getSerializableExtra(EXTRA_TRACK) as Track }

    override fun init() {
        showLoading()
        CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(Dispatchers.Default).async {
                // background thread
                mediaPlayer = MediaPlayer()
                mediaPlayer.apply{
                    setAudioStreamType(AudioManager.STREAM_MUSIC)
                    setDataSource(track.previewUrl)
                    prepare()
                }
            }.await()
            hideLoading()
            binding.apply{
                music = track
                musicPlayerView.setCoverURL(track.artworkUrl100)
                musicPlayerView.setMax(mediaPlayer.duration/1000)

                musicPlayerView.stop()
                musicPlayerView.start()
                mediaPlayer.start()
                isStart = true
            }
        }
        setEvent()
    }

    fun setEvent(){
        binding.apply {
            musicPlayerView.setOnClickListener {
                isStart = if(isStart){
                    musicPlayerView.stop()
                    mediaPlayer.pause()
                    false
                } else {
                    musicPlayerView.start()
                    mediaPlayer.start()
                    true
                }
            }
        }
    }

    override fun onDestroy() {
        //미디어 플레이어 종료
        mediaPlayer.stop()
        mediaPlayer.release()
        super.onDestroy()
    }


}