import org.apache.poi.hssf.usermodel.*
import java.io.*

fun main(args: Array<String>) {

    CRIME_FILE_NAME = args[0]
    INPUT_FILE_NAME = args[1]
    OUTPUT_FILE_NAME = args[2]
    val wbIn = HSSFWorkbook(FileInputStream(INPUT_FILE_NAME))
    wbIn.forceFormulaRecalculation = true
    val sheetIn = wbIn.getSheetAt(0)

    val currentRow = sheetIn.getRow(LAST_HEADER_ROW + CURRENT_MONTH)

    writeIndicators(currentRow)

    saveWorkbookToFile(wbIn, OUTPUT_FILE_NAME)
}

fun saveWorkbookToFile(wb: HSSFWorkbook, outFileName: String) {
    val fileOut = FileOutputStream(outFileName)
    wb.write(fileOut)
    fileOut.close()
}
