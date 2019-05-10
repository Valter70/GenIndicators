import org.apache.poi.hssf.usermodel.HSSFWorkbook
import java.time.LocalDate

class CriminalCase(wbIn: HSSFWorkbook, currentRowIndex: Int) {
    private val currentRow = wbIn.getSheetAt(0).getRow(currentRowIndex)
    val number: String
        get() = getNumberValue()
    val gravity: Gravity
        get() = getGravityValue()
    val decision: Decision
        get() = getDecisionValue()
    val decisionDate: LocalDate?
        get() = getDecisionDateValue()
    val isCombined: Boolean
        get() = getCombinedValue()
    val suspicionDate: LocalDate?
        get() = getSuspicionDateValue()
    val isCurrentYear: Boolean = number.substring(1..4).toInt() == CURRENT_YEAR

    private fun getNumberValue() : String {
        var numKP = ""
        if (currentRow.getCell(0).numericCellValue == 0.0)
            return "00000"
        for(i in 0..2) {
            val partOfNumber = currentRow.getCell(i).numericCellValue.toInt().toString()
            numKP += partOfNumber
        }
        numKP += createFullNumber(currentRow.getCell(3).numericCellValue.toInt().toString())
        return numKP
    }

    private fun createFullNumber(number: String) : String {
        var result = ""
        for(i in 1..(7 - number.length))
            result += "0"
        return result + number
    }

    private fun getDecisionValue() : Decision {
        val cellIndex = titleList.indexOf("Ф11 18.Рiшення")
        val cellValue = currentRow.getCell(cellIndex).stringCellValue
        return if (cellValue.isNotEmpty()) getCodeDecision(cellValue) else Decision.NO_R
    }

    private fun getGravityValue() : Gravity {
        val gravityCell = titleList.indexOf("Ф1 14.Квал.злоч.-тяжкiсть")
        return getCodeGravity(currentRow.getCell(gravityCell).stringCellValue)
    }

    private fun getCombinedValue() : Boolean {
        val cellIndex = titleList.indexOf("Ф3 7.Обєднана/видiлена")
        var result = currentRow.getCell(cellIndex).stringCellValue.isNotEmpty()
        var rowIndex = currentRow.rowNum + 1

        while (isRowMarge(rowIndex) && !result) {
            result = currentRow.sheet.getRow(rowIndex).getCell(cellIndex).stringCellValue.isNotEmpty()
            rowIndex++
        }

        return result
    }

    private fun getSuspicionDateValue() : LocalDate? {
        val cellIndex = titleList.indexOf("19.Дата повiдомлення про пiдозру")

        var result: LocalDate? = null
        val cellValue = currentRow.getCell(cellIndex).dateCellValue
        if (cellValue != null)
            result = java.sql.Date(cellValue.time).toLocalDate()

        var rowIndex = currentRow.rowNum + 1

        while (isRowMarge(rowIndex)) {
            var currentDate: LocalDate? = null
            if (currentRow.sheet.getRow(rowIndex).getCell(cellIndex).dateCellValue != null)
                currentDate = java.sql.Date(currentRow.sheet.getRow(rowIndex).getCell(cellIndex).dateCellValue.time).toLocalDate()
            when {
                result == null && currentDate != null -> result = currentDate
                result != null && currentDate != null && result > currentDate -> result = currentDate
            }
            rowIndex++
        }
        return result
    }

    private fun isRowMarge(rowIndex: Int) : Boolean =
        rowIndex <= currentRow.sheet.lastRowNum && currentRow.sheet.getRow(rowIndex).getCell(0).numericCellValue == 0.0

    private fun getDecisionDateValue() : LocalDate? {
        val cellIndex = titleList.indexOf("Ф11 ЄРДР Дата введення")
        val cellValue = currentRow.getCell(cellIndex).dateCellValue
        return if (cellValue != null) java.sql.Date(cellValue.time).toLocalDate() else null
    }

    override fun toString() = "$number - $decision - $decisionDate - $isCombined - $suspicionDate"

}