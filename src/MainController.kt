import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.stage.Stage
import java.io.*
import java.time.LocalDate
import javax.swing.JFileChooser
import javax.swing.JOptionPane
import javax.swing.UIManager
import javax.swing.filechooser.FileNameExtensionFilter

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
        val fileOpen = JFileChooser(".")
        fileOpen.fileFilter = FileNameExtensionFilter("Excel", "xls", "xlsx")
        val ret = fileOpen.showDialog(null, "Открыть файл")
        if (ret == JFileChooser.APPROVE_OPTION) {
            crimeFileName.text = fileOpen.selectedFile.absolutePath
        }
    }

    @FXML
    private fun getTabFileName() {
        val fileSave = JFileChooser(".")
        fileSave.fileFilter = FileNameExtensionFilter("Excel", "xls", "xlsx")
        val ret = fileSave.showDialog(null, "Сохранить в файл")
        if (ret == JFileChooser.APPROVE_OPTION) {
            tableFileName.text = fileSave.selectedFile.absolutePath
        }
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

        if(!isValidFileName()) {
            JOptionPane.showMessageDialog(null, "Не коректні імена файлів!\n" + "Необхідні файли *.xls!   ", "Помилка!", JOptionPane.ERROR_MESSAGE)
        } else {
            closeDialog(btnOK)
            writeToResourceFile()
        }
    }

    private fun isValidFileName() : Boolean =
        CRIME_FILE_NAME != "" && INPUT_TAB_FILE_NAME != "" &&
        CRIME_FILE_NAME.substringAfter(".") == "xls" && INPUT_TAB_FILE_NAME.substringAfter(".") == "xls"


    private fun closeDialog(btn: Button) {
        val stage = btn.scene.window as Stage
        stage.close()
    }

    fun initialize() {
        setMonthsValue()
        readFromResourceFile()
        checkOut.isSelected = true
        localizeDialog()
    }

    private fun localizeDialog() {
        UIManager.put("FileChooser.saveButtonText", "Сохранить");
        UIManager.put("FileChooser.saveButtonToolTipText", "Сохранить выбранный файл");
        UIManager.put("FileChooser.openButtonText", "Открыть");
        UIManager.put("FileChooser.openButtonToolTipText", "Открыть выбранный файл");
        UIManager.put("FileChooser.cancelButtonText", "Отмена");
        UIManager.put("FileChooser.cancelButtonToolTipText", "Отменить открытие файла");
        UIManager.put("FileChooser.fileNameLabelText", "Имя файла");
        UIManager.put("FileChooser.filesOfTypeLabelText", "Типы файлов");
        UIManager.put("FileChooser.lookInLabelText", "Директория");
        UIManager.put("FileChooser.saveInLabelText", "Сохранить в директории");
        UIManager.put("FileChooser.folderNameLabelText", "Путь директории");
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