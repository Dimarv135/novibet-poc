package com.example.myapplication.model

class Game : ArrayList<GameItem>()

data class GameItem(
    val betViews: List<GameBetView>,
    val caption: String,
    val hasHighlights: Boolean,
    val marketViewKey: String,
    val marketViewType: String,
    val modelType: String,
    val totalCount: Int
)

data class GameBetView(
    val betViewKey: String,
    val competitionContextCaption: String,
    val competitions: List<Competition>,
    val marketCaptions: List<MarketCaption>,
    val modelType: String,
    val totalCount: Int
)

data class Competition(
    val betContextId: Int,
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
    val betContextId: Int,
    val hasBetContextInfo: Boolean,
    val isHighlighted: Boolean,
    val liveData: GameLiveData,
    val markets: List<Market>,
    val path: String
)

data class AdditionalCaptions(
    val competitor1: String,
    val competitor1ImageId: Int,
    val competitor2: String,
    val competitor2ImageId: Int,
    val type: Int
)

data class GameLiveData(
    val adjustTimeMillis: Int,
    val awayCorners: Int,
    val awayGoals: Int,
    val awayPenaltyKicks: Int,
    val awayRedCards: Int,
    val awayYellowCards: Int,
    val duration: Any,
    val durationSeconds: Any,
    val elapsed: String,
    val elapsedSeconds: Double,
    val homeCorners: Int,
    val homeGoals: Int,
    val homePenaltyKicks: Int,
    val homeRedCards: Int,
    val homeYellowCards: Int,
    val isInPlay: Boolean,
    val isInPlayPaused: Boolean,
    val isInterrupted: Boolean,
    val isLive: Boolean,
    val liveStreamingCountries: Any,
    val phaseCaption: String,
    val phaseCaptionLong: String,
    val phaseSysname: String,
    val referenceTime: String,
    val referenceTimeUnix: Int,
    val sportradarMatchId: Int,
    val supportsAchievements: Boolean,
    val supportsActions: Boolean,
    val timeToNextPhase: Any,
    val timeToNextPhaseSeconds: Any,
    val timeline: Any
)

data class Market(
    val betItems: List<GameBetItem>,
    val betTypeSysname: String,
    val marketId: Int
)

data class GameBetItem(
    val caption: String,
    val code: String,
    val id: Int,
    val instanceCaption: Any,
    val isAvailable: Boolean,
    val oddsText: String,
    val price: Double
)