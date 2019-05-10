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

    currentRow.getCell(SUSPISION_COLUMN).setCellValue(Indicators().suspicionAll)
    currentRow.getCell(SUSPISION_COLUMN + 1).setCellValue(Indicators().suspicionCurrent)
    currentRow.getCell(SUSPISION_COLUMN + 2).setCellValue(Indicators().suspisionGravity)

    currentRow.getCell(COURT_COLUMN).setCellValue(Indicators().toCourtCase)
    currentRow.getCell(COURT_COLUMN + 1).setCellValue(Indicators().toCourtEpisode)

    currentRow.getCell(CLOSE_COLUMN).setCellValue(Indicators().closedAll)
    currentRow.getCell(CLOSE_COLUMN + 1).setCellValue(Indicators().closedCurrent)

    println(crimeList.size)
    val tmpList = crimeList.filter { it.decision in sentToCourtCase && it.isCombined && it.decisionDate?.monthValue == CURRENT_MONTH}
    for (case in tmpList)
        println(case)

    saveWorkbookToFile(wbIn, OUTPUT_FILE_NAME)
}

fun saveWorkbookToFile(wb: HSSFWorkbook, outFileName: String) {
    val fileOut = FileOutputStream(outFileName)
    wb.write(fileOut)
    fileOut.close()
}
