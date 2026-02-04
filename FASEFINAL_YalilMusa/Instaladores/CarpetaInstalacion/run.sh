#!/bin/bash
cd "$(dirname "$0")"
java --module-path lib \
     --add-modules javafx.controls,javafx.fxml,javafx.web,javafx.swing,javafx.media,javafx.base,javafx.graphics \
     --add-exports javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED \
     --add-exports javafx.graphics/com.sun.javafx.sg.prism.web=ALL-UNNAMED \
     --add-exports javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED \
     --add-exports javafx.graphics/com.sun.javafx.util=ALL-UNNAMED \
     --add-exports javafx.graphics/com.sun.javafx.tk=ALL-UNNAMED \
     --add-exports javafx.graphics/com.sun.prism=ALL-UNNAMED \
     --add-exports javafx.graphics/com.sun.glass.ui=ALL-UNNAMED \
     --add-exports javafx.graphics/com.sun.glass.utils=ALL-UNNAMED \
     --add-exports javafx.graphics/com.sun.javafx.geom=ALL-UNNAMED \
     --add-exports javafx.graphics/com.sun.javafx.geom.transform=ALL-UNNAMED \
     --add-exports javafx.base/com.sun.javafx.logging=ALL-UNNAMED \
     --add-exports javafx.web/com.sun.webkit=ALL-UNNAMED \
     --add-exports javafx.web/com.sun.javafx.webkit.prism=ALL-UNNAMED \
     --add-exports javafx.web/com.sun.javafx.webkit.theme=ALL-UNNAMED \
     -jar AutoCiclo.jar
