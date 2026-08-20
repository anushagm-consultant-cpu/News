package com.example.newspulse.ui.viewmodel

import android.app.Application
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.text.Html
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspulse.data.NewsItem
import com.example.newspulse.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ListenViewModel @Inject constructor(
    application: Application,
    private val repository: NewsRepository
) : AndroidViewModel(application), TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val _progress = MutableStateFlow(0f)
    val progress = _progress.asStateFlow()

    private val _currentSpeed = MutableStateFlow(1.0f)
    val currentSpeed = _currentSpeed.asStateFlow()

    private var isTtsReady = false
    private var lastStartOffset = 0
    private var currentOffsetInSpeech = 0

    private val _currentArticle = MutableStateFlow<NewsItem?>(null)
    val currentArticle = _currentArticle.asStateFlow()

    private val _articles = MutableStateFlow<List<NewsItem>>(emptyList())
    val articles = _articles.asStateFlow()

    private var currentIndex = -1

    init {
        tts = TextToSpeech(application, this)
    }

    fun startRandomPlaylist() {
        if (_articles.value.isNotEmpty()) {
            if (_currentArticle.value == null) {
                currentIndex = 0
                _currentArticle.value = _articles.value[currentIndex]
            }
            return
        }
        
        viewModelScope.launch {
            try {
                val response = repository.fetchLatestNews()
                response?.articles?.let { fetchedArticles ->
                    if (fetchedArticles.isNotEmpty()) {
                        val shuffled = fetchedArticles.shuffled()
                        _articles.value = shuffled
                        if (_currentArticle.value == null) {
                            currentIndex = 0
                            _currentArticle.value = shuffled[currentIndex]
                        } else {
                            syncCurrentIndex()
                        }
                    }
                }
            } catch (e: Exception) {
                // Log error
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.getDefault())
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Handle error
            } else {
                isTtsReady = true
                tts?.setSpeechRate(_currentSpeed.value)
            }

            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    _isPlaying.value = true
                }

                override fun onDone(utteranceId: String?) {
                    _isPlaying.value = false
                    _progress.value = 1f
                    lastStartOffset = 0
                    currentOffsetInSpeech = 0
                }

                override fun onError(utteranceId: String?) {
                    _isPlaying.value = false
                }

                //slider
                override fun onRangeStart(utteranceId: String?, start: Int, end: Int, frame: Int) {
                    currentOffsetInSpeech = start
                    _currentArticle.value?.let { article ->
                        val text = getCleanText(article)
                        if (text.isNotEmpty()) {
                            // progress = (Previous chunks + current character position) / total length
                            val absolutePosition = lastStartOffset + start
                            _progress.value = absolutePosition.toFloat() / text.length.toFloat()
                        }
                    }
                }
            })
        }
    }

    private fun getCleanText(article: NewsItem): String {
        val rawText = article.content ?: article.description ?: article.title
        return if (rawText.isNullOrEmpty()) "" else Html.fromHtml(rawText, Html.FROM_HTML_MODE_LEGACY).toString().trim()
    }

    fun playNext() {
        if (_articles.value.isEmpty()) return
        currentIndex = (currentIndex + 1) % _articles.value.size
        val article = _articles.value[currentIndex]
        _currentArticle.value = article
        speak(article)
    }

    fun playPrevious() {
        if (_articles.value.isEmpty()) return
        currentIndex = if (currentIndex <= 0) _articles.value.size - 1 else currentIndex - 1
        val article = _articles.value[currentIndex]
        _currentArticle.value = article
        speak(article)
    }

    private fun speak(article: NewsItem, offset: Int = 0) {
        if (!isTtsReady) return

        val fullText = getCleanText(article)
        if (fullText.isEmpty()) return

        val textToSpeak = if (offset > 0 && offset < fullText.length) {
            fullText.substring(offset)
        } else {
            lastStartOffset = 0
            fullText
        }

        lastStartOffset = offset
        currentOffsetInSpeech = 0

        val utteranceId = "news_article_${article.url ?: article.title}"
        val params = android.os.Bundle()
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId)
        params.putInt(TextToSpeech.Engine.KEY_PARAM_STREAM, android.media.AudioManager.STREAM_MUSIC)

        tts?.setSpeechRate(_currentSpeed.value)
        tts?.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, params, utteranceId)
        _isPlaying.value = true
    }

    fun togglePlayPause() {
        if (_isPlaying.value) {
            lastStartOffset += currentOffsetInSpeech
            tts?.stop()
            _isPlaying.value = false
        } else {
            _currentArticle.value?.let { speak(it, lastStartOffset) }
        }
    }

    fun stopAudio() {
        tts?.stop()
        _isPlaying.value = false
        lastStartOffset = 0
        currentOffsetInSpeech = 0
        _progress.value = 0f
    }

    fun cycleSpeed() {
        val nextSpeed = when (_currentSpeed.value) {
            1.0f -> 1.5f
            1.5f -> 2.0f
            2.0f -> 0.75f
            else -> 1.0f
        }
        _currentSpeed.value = nextSpeed
        tts?.setSpeechRate(nextSpeed)
        // If playing, restart with new speed for immediate effect
        if (_isPlaying.value) {
            lastStartOffset += currentOffsetInSpeech
            _currentArticle.value?.let { speak(it, lastStartOffset) }
        }
    }

    fun setSpeed(speed: Float) {
        _currentSpeed.value = speed
        tts?.setSpeechRate(speed)
    }

    override fun onCleared() {
        tts?.stop()
        tts?.shutdown()
        super.onCleared()
    }

    fun setCurrentArticleAudio(article: NewsItem) {
        val current = _currentArticle.value
        val isSame = current != null &&
                     ((article.url != null && current.url == article.url) ||
                      (current.title == article.title))

        if (isSame) return

        tts?.stop()
        _currentArticle.value = article
        _isPlaying.value = false
        _progress.value = 0f
        lastStartOffset = 0
        currentOffsetInSpeech = 0

        syncCurrentIndex()
    }

    private fun syncCurrentIndex() {
        val current = _currentArticle.value ?: return
        if (_articles.value.isNotEmpty()) {
            val index = _articles.value.indexOfFirst {
                it.url == current.url || it.title == current.title
            }
            if (index != -1) {
                currentIndex = index
            }
        }
    }




}
