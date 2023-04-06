# IFT1025-TP2-server
### Quelques consignes sur comment démarrer le projet:
1. Veuillez garder les fichiers .jar dans le même répertoire (out/artifacts), surtout server.jar, ainsi que les fichiers texte dans out/artifacts/server/src/main/java/server/data
2. J'ai changé les VM options pour le fichier GUI.java pour que JavaFX marche, et je ne sais pas si ce changement sera transféré automatiquement, alors sinon vous pouvez ajouter --module-path ${PATH_TO_FX} --add-modules javafx.fxml,javafx.controls,javafx.graphics aux VM options, où ${PATH_TO_FX} est le répertoire "lib" du SDK javafx dans votre machine.
3. J'ai dû manuellement ajouter le répertoire "lib" dans les libraries dans "project structure", alors si ça marche encore pas vous pouvez essayer ça.
4. Si les fichiers .java dans le fichier .zip ne marche pas, veuillez cloner le projet de Github.
5. Le fichier ClientApp.java est seulement là pour que le build du .jar du client graphique marche, parce que sinon ça ne marchait juste pas.