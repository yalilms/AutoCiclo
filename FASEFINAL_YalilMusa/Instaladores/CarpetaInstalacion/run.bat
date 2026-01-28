@echo off
cd /d "%~dp0"

start "" javaw --module-path "lib" ^
     --add-modules javafx.base,javafx.graphics,javafx.controls,javafx.fxml,javafx.media,javafx.web,javafx.swing ^
     --add-exports javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED ^
     --add-exports javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED ^
     --add-exports javafx.graphics/com.sun.javafx.util=ALL-UNNAMED ^
     --add-exports javafx.graphics/com.sun.javafx.tk=ALL-UNNAMED ^
     --add-exports javafx.graphics/com.sun.prism=ALL-UNNAMED ^
     --add-exports javafx.graphics/com.sun.glass.ui=ALL-UNNAMED ^
     --add-exports javafx.graphics/com.sun.glass.utils=ALL-UNNAMED ^
     --add-exports javafx.graphics/com.sun.javafx.geom=ALL-UNNAMED ^
     --add-exports javafx.graphics/com.sun.javafx.geom.transform=ALL-UNNAMED ^
     --add-exports javafx.base/com.sun.javafx.logging=ALL-UNNAMED ^
     -cp "AutoCiclo.jar;lib/*" ^
     com.autociclo.Main
