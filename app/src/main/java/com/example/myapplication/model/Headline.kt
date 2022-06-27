package com.example.myapplication.model

class Headline : ArrayList<HeadlineItem>()

data class HeadlineItem(
    val betViews: List<BetView?>,
    val caption: String,
    val marketViewKey: String,
    val marketViewType: String,
    val modelType: String
)

data class BetView(
    val betContextId: Number,
    val betItems: List<BetItem>,
    val betViewKey: String,
    val competitor1Caption: String,
    val competitor2Caption: String,
    val displayFormat: String,
    val imageId: Number,
    val liveData: LiveData,
    val marketTags: List<Any>,
    val marketViewGroupId: Number,
    val marketViewId: Number,
    val modelType: String,
    val path: String,
    val rootMarketViewGroupId: Number,
    val startTime: String,
    val text: String,
    val url: Any
)

data class BetItem(
    val caption: String,
    val code: String,
    val id: Number,
    val instanceCaption: String,
    val isAvailable: Boolean,
    val oddsText: String,
    val price: Number
)

data class LiveData(
    val adjustTimeMillis: Number,
    val awayPoNumbers: Number,
    val duration: String,
    val durationSeconds: Number,
    val elapsed: String,
    val elapsedSeconds: Double,
    val homePoNumbers: Number,
    val homePossession: Boolean,
    val isInPlay: Boolean,
    val isInPlayPaused: Boolean,
    val isNumbererrupted: Boolean,
    val isLive: Boolean,
    val liveStreamingCountries: String,
    val phaseCaption: String,
    val phaseCaptionLong: String,
    val phaseSysname: String,
    val quarterScores: List<QuarterScore>,
    val referenceTime: String,
    val referenceTimeUnix: Number,
    val remaining: String,
    val remainingSeconds: Double,
    val sportradarMatchId: Number,
    val supportsAchievements: Boolean,
    val supportsActions: Boolean,
    val timeToNextPhase: Any,
    val timeToNextPhaseSeconds: Any,
    val timeline: Any
)

data class QuarterScore(
    val awayScore: Number,
    val caption: String,
    val homeScore: Number
)