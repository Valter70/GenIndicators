// Решения по КП
enum class Decision(val statName: String) {
    NO_R("РІШЕННЯ НЕ ПРИЙНЯТО"),
    ZAK1("ЗАКРИТО СТ.284 Ч.1 П.1 КПК"),
    ZAK2("ЗАКРИТО СТ.284 Ч.1 П.2 КПК"),
    ZAK5("ЗАКРИТО СТ.284 Ч.1 П.5 КПК"),
    ZAK7("ЗАКРИТО СТ.284 Ч.1 П.7 КПК"),
    ZAK14("ЗАКРИТО СТ.284 Ч.1 АБЗАЦ 14 КПК"),
    ZUP1("ЗУПИНЕНО СТ.280 Ч.1.П.1 КПК"),
    ZUP2("ЗУПИНЕНО СТ.280 Ч.1.П.2 КПК"),
    SUD("З ОБВИНУВАЛЬНИМ АКТОМ"),
    UG_P("У Т.Ч. З УГОДОЮ ПРО ПРИМИРЕННЯ")
}

val closeCase = setOf(Decision.ZAK1, Decision.ZAK2, Decision.ZAK14)
val sentToCourtCase = setOf(Decision.SUD, Decision.UG_P, Decision.ZAK5, Decision.ZAK7)

fun getCodeDecision(strDecision: String) =
    when(strDecision){
        "ЗАКРИТО СТ.284 Ч.1 П.1 КПК" -> Decision.ZAK1
        "ЗАКРИТО СТ.284 Ч.1 П.2 КПК" -> Decision.ZAK2
        "ЗАКРИТО СТ.284 Ч.1 П.5 КПК" -> Decision.ZAK5
        "ЗАКРИТО СТ.284 Ч.1 П.7 КПК" -> Decision.ZAK7
        "ЗАКРИТО СТ.284 Ч.1 АБЗАЦ 14 КПК" -> Decision.ZAK14
        "ЗУПИНЕНО СТ.280 Ч.1.П.1 КПК" -> Decision.ZUP1
        "ЗУПИНЕНО СТ.280 Ч.1.П.2 КПК" -> Decision.ZUP2
        "З ОБВИНУВАЛЬНИМ АКТОМ" -> Decision.SUD
        "У Т.Ч. З УГОДОЮ ПРО ПРИМИРЕННЯ" -> Decision.UG_P
        else -> throw IndexOutOfBoundsException("Невірна назва тяжкості: $strDecision")
    }

