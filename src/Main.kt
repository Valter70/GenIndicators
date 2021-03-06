﻿import javafx.application.Application
import javafx.fxml.FXMLLoader.load
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

@Suppress("JAVA_CLASS_ON_COMPANION")
class Main : Application() {

    private val layout = "main.fxml"

    override fun start(primaryStage: Stage?) {
        //System.setProperty("prism.lcdtext", "false") // for beautiful fonts on linux
        primaryStage?.title = "Вхідні данні"
        primaryStage?.scene = Scene(load<Parent?>(Main.javaClass.getResource(layout)))
        primaryStage?.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main::class.java)
            genIndicator()
        }
    }
}
