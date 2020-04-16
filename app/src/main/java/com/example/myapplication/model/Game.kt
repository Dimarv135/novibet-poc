package com.example.myapplication.model

class Game : ArrayList<GameItem>()

data class GameItem(
    val betViews: List<GameBetView>,
    val caption: String,
    val hasHighlights: Boolean,
    val marketViewKey: String,
    val marketViewType: String,
    val modelType: String,
    val totalCount: Number
)

data class GameBetView(
    val betViewKey: String,
    val competitionContextCaption: String,
    val competitions: List<Competition>,
    val marketCaptions: List<MarketCaption>,
    val modelType: String,
    val totalCount: Number
)

data class Competition(
    val betContextId: Number,
    val caption: String,
    val events: List<Event>,
    val regionCaption: String
)

data class MarketCaption(
    val betCaptions: Any,
    val betTypeSysname: String,
    val marketCaption: String
)

data class Event(
    val additionalCaptions: AdditionalCaptions,
    val betContextId: Number,
    val hasBetContextInfo: Boolean,
    val isHighlighted: Boolean,
    val liveData: GameLiveData,
    val markets: List<Market>,
    val path: String
)

data class AdditionalCaptions(
    val competitor1: String,
    val competitor1ImageId: Number,
    val competitor2: String,
    val competitor2ImageId: Number,
    val type: Number
)

data class GameLiveData(
    val adjustTimeMillis: Number,
    val awayCorners: Number,
    val awayGoals: Number,
    val awayPenaltyKicks: Number,
    val awayRedCards: Number,
    val awayYellowCards: Number,
    val duration: Any,
    val durationSeconds: Any,
    val elapsed: String,
    val elapsedSeconds: Double,
    val homeCorners: Number,
    val homeGoals: Number,
    val homePenaltyKicks: Number,
    val homeRedCards: Number,
    val homeYellowCards: Number,
    val isInPlay: Boolean,
    val isInPlayPaused: Boolean,
    val isNumbererrupted: Boolean,
    val isLive: Boolean,
    val liveStreamingCountries: Any,
    val phaseCaption: String,
    val phaseCaptionLong: String,
    val phaseSysname: String,
    val referenceTime: String,
    val referenceTimeUnix: Number,
    val sportradarMatchId: Number,
    val supportsAchievements: Boolean,
    val supportsActions: Boolean,
    val timeToNextPhase: Any,
    val timeToNextPhaseSeconds: Any,
    val timeline: Any
)

data class Market(
    val betItems: List<GameBetItem>,
    val betTypeSysname: String,
    val marketId: Number
)

data class GameBetItem(
    val caption: String,
    val code: String,
    val id: Number,
    val instanceCaption: Any,
    val isAvailable: Boolean,
    val oddsText: String,
    val price: Double
)