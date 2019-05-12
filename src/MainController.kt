import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.stage.Stage
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.FileDialog
import org.eclipse.swt.widgets.MessageBox
import org.eclipse.swt.widgets.Shell
import java.io.*
import java.time.LocalDate

class MainController {

    @FXML
    lateinit var btnOK: Button

    @FXML
    lateinit var btnCancel: Button

    @FXML
    lateinit var crimeFileDialog: Button

    @FXML
    lateinit var outputFileDialog: Button

    @FXML
    lateinit var inLabel: Label

    @FXML
    lateinit var monthLabel: Label

    @FXML
    lateinit var outLabel: Label

    @FXML
    lateinit var crimeFileName: TextField

    @FXML
    lateinit var tableFileName: TextField

    @FXML
    lateinit var months: ComboBox<String>

    @FXML
    lateinit var checkOut: CheckBox

    @FXML
    private fun getCrimeXLSFileName() {
        val dlgOpen = FileDialog(Shell(), SWT.OPEN)
        dlgOpen.filterNames = arrayOf("Excel")
        dlgOpen.filterExtensions = arrayOf("*.xls")
        val fName = dlgOpen.open()
        if (fName != null)
            crimeFileName.text = fName
    }

    @FXML
    private fun getTabFileName() {
        val dlgSave = FileDialog(Shell(), SWT.SAVE)
        dlgSave.filterNames = arrayOf("Excel")
        dlgSave.filterExtensions = arrayOf("*.xls")
        val fName = dlgSave.open()
        if (fName != null)
            tableFileName.text = fName
    }

    @FXML
    private fun actCancel() {
        closeDialog(btnCancel)
        System.exit(1)
    }

    @FXML
    private fun actOK() {
        CRIME_FILE_NAME = crimeFileName.text
        INPUT_TAB_FILE_NAME = tableFileName.text
        CURRENT_MONTH = months.selectionModel.selectedIndex + 1

        setOutputTableFileName()

        if(CRIME_FILE_NAME == "" || INPUT_TAB_FILE_NAME == "") {
            val style = SWT.ICON_ERROR
            val mBox = MessageBox(Shell(SWT.APPLICATION_MODAL), style)
            mBox.text = "Помилка!"
            mBox.message = "Не вказані імена файлів!"
            mBox.open()
        } else {
            closeDialog(btnOK)
            writeToResourceFile()
        }
    }

    private fun closeDialog(btn: Button) {
        val stage = btn.scene.window as Stage
        stage.close()
    }

    fun initialize() {
        setMonthsValue()
        readFromResourceFile()
        checkOut.isSelected = true
    }

    private fun setMonthsValue() {
        months.items.addAll("Січень", "Лютий", "Березень", "Квітень", "Травень", "Червень", "Липень", "Серпень", "Вересень", "Жовтень", "Листопад", "Грудень")
        months.selectionModel.select(LocalDate.now().monthValue - 1)
    }

    private fun readFromResourceFile() {
        val rsFile = File(RESOURCE_FILE_NAME)
        if (rsFile.exists()) {
            val filesName = rsFile.readLines()
            crimeFileName.text = filesName[0]
            tableFileName.text = filesName[1]
            checkOut.isSelected = filesName[2].toBoolean()
            months.selectionModel.select(filesName[3].toInt())
        }
    }

    private fun writeToResourceFile() {
        val fileWriter = FileWriter(RESOURCE_FILE_NAME, false)
        fileWriter.write(CRIME_FILE_NAME + "\n")
        fileWriter.write(INPUT_TAB_FILE_NAME + "\n")
        fileWriter.write(checkOut.isSelected.toString() + "\n")
        fileWriter.write(months.selectionModel.selectedIndex.toString() + "\n")
        fileWriter.close()
    }

    private fun setOutputTableFileName() {
        OUTPUT_TAB_FILE_NAME = if (checkOut.isSelected && INPUT_TAB_FILE_NAME != "") {
            INPUT_TAB_FILE_NAME.substringBefore(".") + "-out." + INPUT_TAB_FILE_NAME.substringAfter(".")
        } else {
            INPUT_TAB_FILE_NAME
        }
    }
}