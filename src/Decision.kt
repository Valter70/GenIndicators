// Решения по КП
enum class Decision(val statName: String) {
    NO_R("РІШЕННЯ НЕ ПРИЙНЯТО"),
    ZAK1("ЗАКРИТО СТ.284 Ч.1 П.1 КПК"),
    ZAK2("ЗАКРИТО СТ.284 Ч.1 П.2 КПК"),
    ZAK4("ЗАКРИТО СТ.284 Ч.1 П.4 КПК"),
    ZAK5("ЗАКРИТО СТ.284 Ч.1 П.5 КПК"),
    ZAK7("ЗАКРИТО СТ.284 Ч.1 П.7 КПК"),
    ZAK14("ЗАКРИТО СТ.284 Ч.1 АБЗАЦ 14 КПК"),
    ZN("ПЕРЕДАНО ЗІ ЗНЯТТЯМ З ОБЛІКУ"),
    ZUP1("ЗУПИНЕНО СТ.280 Ч.1.П.1 КПК"),
    ZUP2("ЗУПИНЕНО СТ.280 Ч.1.П.2 КПК"),
    SUD("З ОБВИНУВАЛЬНИМ АКТОМ"),
    UG_P("У Т.Ч. З УГОДОЮ ПРО ПРИМИРЕННЯ"),
    UG_V("У Т.Ч. З УГОДОЮ ПРО ВИЗНАННЯ ВИНУВАТОСТІ"),
    PR_94("СТ.94 ПРО ЗАСТОС.ПРИМУСОВИХ ЗАХОДІВ МЕД.ХАР-РУ"),
    KL_49("З КЛОПОТАННЯМ ПРО ЗВІЛЬНЕННЯ ВІД КРИМ.ВІДП. СТ.49"),
    KL_44("З КЛОПОТАННЯМ ПРО ЗВІЛЬНЕННЯ ВІД КРИМ.ВІДП. СТ.44"),
    KL_45("З КЛОПОТАННЯМ ПРО ЗВІЛЬНЕННЯ ВІД КРИМ.ВІДП. СТ.45"),
    KL_46("З КЛОПОТАННЯМ ПРО ЗВІЛЬНЕННЯ ВІД КРИМ.ВІДП. СТ.46")
}

val closeCase = setOf(Decision.ZAK1, Decision.ZAK2, Decision.ZAK14, Decision.ZAK4)
val sentToCourtCase = setOf(Decision.SUD, Decision.UG_P, Decision.UG_V, Decision.ZAK5, Decision.ZAK7, Decision.KL_49, Decision.KL_44, Decision.KL_45, Decision.KL_46, Decision.PR_94)

fun getCodeDecision(strDecision: String) = Decision.values().find { it.statName == strDecision }!!
