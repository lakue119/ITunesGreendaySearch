package com.lakue.itunesgreendaysearch.ui.music

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import com.lakue.itunesgreendaysearch.R
import com.lakue.itunesgreendaysearch.base.BaseActivity
import com.lakue.itunesgreendaysearch.databinding.ActivityMusicBinding
import com.lakue.itunesgreendaysearch.model.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MusicActivity : BaseActivity<ActivityMusicBinding, MusicViewModel>(R.layout.activity_music) {

    var isStart = false

    companion object {
        const val EXTRA_TRACK = "EXTRA_TRACK"
        const val EXTRA_POSITION = "EXTRA_POSITION"
    }

    lateinit var mediaPlayer: MediaPlayer


    private val tracks by lazy { intent.getSerializableExtra(EXTRA_TRACK) as ArrayList<Track> }
    private val pos by lazy { intent.getIntExtra(EXTRA_POSITION,0) }
    var nowPosition = 0

    @RequiresApi(Build.VERSION_CODES.N)
    override fun init() {
        binding.activity = this@MusicActivity
        showLoading()
        nowPosition = pos - Integer.max(0, pos - 20)

        mediaPlayer = MediaPlayer()

        musicStart()
        setEvent()
    }

    fun musicStart(){
        mediaPlayer.reset()
        showLoading()
        CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(Dispatchers.Default).async {
                // background thread
                mediaPlayer.apply{
                    setAudioStreamType(AudioManager.STREAM_MUSIC)
                    setDataSource(tracks[nowPosition].previewUrl)
                    prepare()
                }
            }.await()
            hideLoading()
            binding.apply{
                music = tracks[nowPosition]
                musicPlayerView.setCoverURL(tracks[nowPosition].artworkUrl100)
                musicPlayerView.setMax(mediaPlayer.duration/1000)

                musicPlayerView.stop()
                musicPlayerView.start()
                mediaPlayer.start()
                isStart = true
            }
        }
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

        mediaPlayer.apply{
            setOnCompletionListener {
                onNextMusic()
            }
        }
    }

    override fun onDestroy() {
        //미디어 플레이어 종료
        mediaPlayer.stop()
        mediaPlayer.release()
        super.onDestroy()
    }

    fun onNextMusic(){
        if(nowPosition >= tracks.size-1){
            nowPosition = 0
        } else {
            nowPosition++
        }
        musicStart()
    }

    fun onBeforeMusic(){
        if(nowPosition <= 0){
            nowPosition = tracks.size-1
        } else {
            nowPosition--
        }
        musicStart()
    }


}