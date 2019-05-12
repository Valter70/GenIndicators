import org.apache.poi.hssf.usermodel.*
import java.io.*

fun genIndicator() {

    val wbIn = HSSFWorkbook(FileInputStream(INPUT_TAB_FILE_NAME))
    wbIn.forceFormulaRecalculation = true
    val sheetIn = wbIn.getSheetAt(0)

    val currentRow = sheetIn.getRow(LAST_HEADER_ROW + CURRENT_MONTH)

    writeIndicators(currentRow)

    saveWorkbookToFile(wbIn, OUTPUT_TAB_FILE_NAME)
}

