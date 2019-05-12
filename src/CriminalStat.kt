import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf .usermodel.HSSFWorkbook
import java.io.FileInputStream
import java.io.FileOutputStream

val titleList = getHeaderNames()
val crimeList = createCrimeListFromXLS()

fun writeIndicators(currentRow: HSSFRow) {
    currentRow.getCell(SUSPICION_COLUMN).setCellValue(Indicators().suspicionAll)
    currentRow.getCell(SUSPICION_COLUMN + 1).setCellValue(Indicators().suspicionCurrent)
    currentRow.getCell(SUSPICION_COLUMN + 2).setCellValue(Indicators().suspicionGravity)

    currentRow.getCell(COURT_COLUMN).setCellValue(Indicators().toCourtCase)
    currentRow.getCell(COURT_COLUMN + 1).setCellValue(Indicators().toCourtEpisode)

    currentRow.getCell(CLOSE_COLUMN).setCellValue(Indicators().closedAll)
    currentRow.getCell(CLOSE_COLUMN + 1).setCellValue(Indicators().closedCurrent)
}

fun saveWorkbookToFile(wb: HSSFWorkbook, outFileName: String) {
    val fileOut = FileOutputStream(outFileName)
    wb.write(fileOut)
    fileOut.close()
}


private fun createCrimeListFromXLS() : List<CriminalCase> {
    val wbStat = HSSFWorkbook(FileInputStream(CRIME_FILE_NAME))
    val sheetStat = wbStat.getSheetAt(0)

    removeAllMergedRegions(wbStat)

    val crimeList: MutableList<CriminalCase> = mutableListOf(CriminalCase(wbStat, FIRST_INDICATOR_ROW))
    var currentRecord = FIRST_INDICATOR_ROW + 1
    while(currentRecord <= sheetStat.lastRowNum) {
        if (sheetStat.getRow(currentRecord).getCell(0).numericCellValue != 0.0)
            crimeList.add(CriminalCase(wbStat, currentRecord))
        currentRecord++
    }

    return crimeList
}

private fun removeAllMergedRegions(wb: HSSFWorkbook) = with(wb.getSheetAt(0)) {
    for(index in 0..numMergedRegions)
        removeMergedRegion(index)
}

private fun getHeaderNames() : List<String> {
    val wbIn = HSSFWorkbook(FileInputStream(CRIME_FILE_NAME))
    var numRow = 3
    val sheet = wbIn.getSheetAt(0)
    val listHeader = mutableListOf("")
    for(i in 0 until sheet.getRow(numRow).lastCellNum) {
        val cellValue = sheet.getRow(numRow).getCell(i).stringCellValue
        if(cellValue == "Форма2") {
            numRow++
            listHeader.add(sheet.getRow(numRow).getCell(i).stringCellValue)
        }
        else
            listHeader.add(cellValue)
    }
    listHeader.removeAt(0)
    return listHeader
}
