class Indicators {
    val suspicionAll: Double
        get() = getSuspicionAllValue()
    val suspicionCurrent: Double
        get() = getSuspicionCurrentValue()
    val suspicionGravity: Double
        get() = getSuspicionGravityValue()
    val toCourtCase: Double
        get() = getToCourtCaseValue()
    val toCourtEpisode: Double
        get() = getToCourtEpisodeValue()
    val closedAll: Double
        get() = getClosedAllValue()
    val closedCurrent: Double
        get() = getClosedCurrentValue()

    private fun getSuspicionAllValue() : Double  = crimeList.filter { it.suspicionDate?.monthValue == CURRENT_MONTH }.size.toDouble()

    private fun getSuspicionCurrentValue() : Double  = crimeList.filter { it.suspicionDate?.monthValue == CURRENT_MONTH && it.isCurrentYear }.size.toDouble()

    private fun getSuspicionGravityValue() : Double  = crimeList.filter { it.suspicionDate?.monthValue == CURRENT_MONTH && it.gravity == Gravity.T3 }.size.toDouble()

    private fun getToCourtCaseValue() : Double = crimeList.filter { it.decision in sentToCourtCase && !it.isCombined && it.decisionDate?.monthValue == CURRENT_MONTH}.size.toDouble()

    private fun getToCourtEpisodeValue() : Double = crimeList.filter { it.decision in sentToCourtCase && it.decisionDate?.monthValue == CURRENT_MONTH}.size.toDouble()

    private fun getClosedAllValue() : Double = crimeList.filter { it.decision in closeCase}.size.toDouble()

    private fun getClosedCurrentValue() : Double = crimeList.filter { it.decision in closeCase && it.isCurrentYear}.size.toDouble()

}
