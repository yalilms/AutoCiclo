#!/bin/bash
cd "$(dirname "$0")"
java --module-path lib --add-modules javafx.controls,javafx.fxml,javafx.web,javafx.swing,javafx.media -jar AutoCiclo.jar
