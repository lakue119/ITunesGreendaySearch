package com.lakue.itunesgreendaysearch

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.lakue.itunesgreendaysearch.repository.FavoriteTrackRepository
import com.lakue.itunesgreendaysearch.repository.ITunesRepository
import com.lakue.itunesgreendaysearch.ui.bottomnavagation.home.HomeViewModel
import com.lakue.itunesgreendaysearch.utils.NetworkHelper
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.manipulation.Ordering
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class HomeViewModelTest {

    @get: Rule
    var rule: TestRule = InstantTaskExecutorRule()

    lateinit  var viewModel : HomeViewModel

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var db: FavoriteTrackRepository

    @Mock
    lateinit var repository: ITunesRepository

    @Mock
    lateinit var lifeCycleOwner: LifecycleOwner

    lateinit var lifeCycle: LifecycleRegistry


    @Before
    fun setUp() {
//        MockitoAnnotations.initMocks(this)
        prepareViewModel()

        lifeCycle = LifecycleRegistry(lifeCycleOwner)
        Mockito.`when`(lifeCycleOwner.lifecycle).thenReturn(lifeCycle)
        lifeCycle.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    @Test
    fun fetchSearch(){
        viewModel.fetchiTunesMusic()
    }

    @Test
    fun liveDataEqual(){
//        Assert.assertEquals(viewModel.getLiveTest().resultCount, 50)
    }

    private fun prepareViewModel(){
        viewModel = HomeViewModel(NetworkHelper(context), db, repository)
    }
}