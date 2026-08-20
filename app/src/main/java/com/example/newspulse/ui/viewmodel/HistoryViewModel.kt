package com.example.newspulse.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspulse.data.NewsItem
import com.example.newspulse.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

enum class HistoryFilter(val label: String) {
    ALL("All"),
    TODAY("Today"),
    YESTERDAY("Yesterday"),
    DAYS_2("2 Days ago"),
    DAYS_3("3 Days ago"),
    DAYS_4("4 Days ago"),
    DAYS_5("5 Days ago"),
    DAYS_6("6 Days ago"),
    DAYS_7("7 Days ago"),
    WEEK_1("1 Week ago"),
    WEEK_2("2 Weeks ago"),
    WEEK_3("3 Weeks ago"),
    WEEK_4("4 Weeks ago"),
    WEEK_5("5 Weeks ago"),
    MONTH_1("1 Month ago"),
    MONTH_6("6 Months ago"),
    YEAR_1("1 Year ago")
}

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: NewsRepository
): ViewModel() {

    private val _filter = MutableStateFlow(HistoryFilter.ALL)
    val filter: StateFlow<HistoryFilter> = _filter

    fun setFilter(filter: HistoryFilter) {
        _filter.value = filter
    }

    fun addToHistory(article: NewsItem) {
        viewModelScope.launch {
            repository.addToHistory(article)
        }
    }

    fun removeFromHistory(article: NewsItem) {
        viewModelScope.launch {
            repository.removeFromHistory(article)
        }
    }

    val historyNews: StateFlow<List<NewsItem>> = combine(
        repository.readingHistory,
        _filter
    ) { history, filter ->
        when (filter) {
            HistoryFilter.ALL -> history
            HistoryFilter.TODAY -> filterPeriod(history, Calendar.DAY_OF_YEAR, 0)
            HistoryFilter.YESTERDAY -> filterPeriod(history, Calendar.DAY_OF_YEAR, -1)
            HistoryFilter.DAYS_2 -> filterPeriod(history, Calendar.DAY_OF_YEAR, -2)
            HistoryFilter.DAYS_3 -> filterPeriod(history, Calendar.DAY_OF_YEAR, -3)
            HistoryFilter.DAYS_4 -> filterPeriod(history, Calendar.DAY_OF_YEAR, -4)
            HistoryFilter.DAYS_5 -> filterPeriod(history, Calendar.DAY_OF_YEAR, -5)
            HistoryFilter.DAYS_6 -> filterPeriod(history, Calendar.DAY_OF_YEAR, -6)
            HistoryFilter.DAYS_7 -> filterPeriod(history, Calendar.DAY_OF_YEAR, -7)
            HistoryFilter.WEEK_1 -> filterPeriod(history, Calendar.WEEK_OF_YEAR, -1)
            HistoryFilter.WEEK_2 -> filterPeriod(history, Calendar.WEEK_OF_YEAR, -2)
            HistoryFilter.WEEK_3 -> filterPeriod(history, Calendar.WEEK_OF_YEAR, -3)
            HistoryFilter.WEEK_4 -> filterPeriod(history, Calendar.WEEK_OF_YEAR, -4)
            HistoryFilter.WEEK_5 -> filterPeriod(history, Calendar.WEEK_OF_YEAR, -5)
            HistoryFilter.MONTH_1 -> filterPeriod(history, Calendar.MONTH, -1)
            HistoryFilter.MONTH_6 -> filterPeriod(history, Calendar.MONTH, -6)
            HistoryFilter.YEAR_1 -> filterPeriod(history, Calendar.YEAR, -1)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val readingStreak: StateFlow<Int> = repository.readingHistory
        .map { history ->
            calculateStreak(history)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    private fun calculateStreak(history: List<NewsItem>): Int {
        if (history.isEmpty()) return 0

        val dates = history
            .filter { it.historyAt > 0 }
            .map {
                val cal = Calendar.getInstance()
                cal.timeInMillis = it.historyAt
                cal.set(Calendar.HOUR_OF_DAY, 0)
                cal.set(Calendar.MINUTE, 0)
                cal.set(Calendar.SECOND, 0)
                cal.set(Calendar.MILLISECOND, 0)
                cal.timeInMillis
            }
            .distinct()
            .sortedDescending()

        if (dates.isEmpty()) return 0

        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val yesterday = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        // Streak is broken if the most recent reading was not today or yesterday
        if (dates[0] != today && dates[0] != yesterday) {
            return 0
        }

        var streak = 0
        var currentCheckDate = dates[0]

        for (date in dates) {
            if (date == currentCheckDate) {
                streak++
                // Move to previous day
                val cal = Calendar.getInstance()
                cal.timeInMillis = currentCheckDate
                cal.add(Calendar.DAY_OF_YEAR, -1)
                currentCheckDate = cal.timeInMillis
            } else {
                break
            }
        }

        return streak
    }

    private fun filterPeriod(history: List<NewsItem>, field: Int, amount: Int): List<NewsItem> {
        val calendar = Calendar.getInstance()
        
        // Reset to midnight of today
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val todayStart = calendar.timeInMillis

        // Move to the start of the specific period (e.g., 2 days ago)
        calendar.add(field, amount)
        val start = calendar.timeInMillis
        
        // The end of the period is exactly one day later to capture just that specific date
        val end = if (start == todayStart) {
            // For "Today", show everything from midnight until now
            System.currentTimeMillis()
        } else {
            val endCal = Calendar.getInstance()
            endCal.timeInMillis = start
            endCal.add(Calendar.DAY_OF_YEAR, 1)
            endCal.timeInMillis
        }
        
        return history.filter { it.historyAt in start until end }
    }
}
