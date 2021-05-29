# Documentació Tècnica App Desktop
Les classes estàn organitzades per:
- Classe principal (App.java)
- Windows
- Extra

## App.java
### main()
Mètode que executa el programa
### start()
En aquest mètode s'inicialitzen totes les `Windows` i s'afegeixen dins del `GUI`, també s'inicialitza l'aplicació amb la finestra `MainWindow` i es verifiquen les actualitzacions de `dpor`.

# Windows
## MainWindow.java
Classe de la pàgina principal
### Constructor
El constructor rep el GUIInteraction que serveix per recarregar la finestra, inicialitzar els butons tencar/minimitzar/enrera i canviar entre finestres.
### getPane()
El mètode retorna el `Pane` construit amb tots els objectes, el `ScrollPane` horitzontal que es construeix a partir de `DispatcherListBuilder.java`, també posa el botó de configuració que te uns events que obren el `DispatcherConfig.java` o el tenquen.
## LoginWindow.java
Classe de login
### Constructor
El constructor rep el GUIInteraction que serveix per recarregar la finestra, inicialitzar els butons tencar/minimitzar/enrera i canviar entre finestres. També rep el path que apunta al archiu `uploader.properties` on estan definits els credencials de login a portasigma
### getPane()
Construeix el `Pane` de la finestra, els camps de user i password. Té uns events que detecten la tecla enter i canvien el focus dels camps i intenten la verificació del `ApiConnect.java` si ApiConnect retorna un `True` sobreescriu els valors user i password del `uploader.properties` i canvia a la finestra `MainFoldersWindow.java` sinó et surt un text que demana que ho tornis a intentar.
## MainFoldersWindow.java
Classe de les carpetes principals Inbox,ok,reject i signed
### Constructor
El constructor rep el GUIInteraction que serveix per recarregar la finestra, inicialitzar els butons tencar/minimitzar/enrera i canviar entre finestres. També rep el path que ens serà util per la gestió dels fitxers i saber on estan aquestes carpetes que estan definides al `uploader.properies`.
### getPane()
Construeix el `Pane` de la finestra, les 4 carpetes i els events que fan que canviem de finestra a `SubFoldersWindow.java`
## SubFoldersWindow.java
Classe de les subcarpetes del dispatcher
### Constructor
El constructor rep el GUIInteraction que serveix per recarregar la finestra, inicialitzar els butons tencar/minimitzar/enrera i canviar entre finestres. També rep el path que ens serà util per la gestió dels fitxers.
### getPane()
Construeix el `Pane`, pero abans d'això verifica que el directori sigui una carpeta de subcarpetes o una carpeta final. Un dispatcher pot tenir 1 sol `.dpor` per a tots els tipus de documents o 1 `.dpor` per a cada tipus de document, es a dir, que dins de inbox/ok/reject/signed pot haver-hi 1 `.dpor` amb tots els documents o multiples carpetes cada una amb el seu `.dpor` i els seus documents. Això ho fa amb el mètode `checkIfFinalFolder(path)`, sino és una carpeta final continua construint el `Pane` que està format per un `ScrollPane` vertical amb un `GridPane` al seu interior que gestiona les carpetes amb files de 3. Aquest `GridPane` es construeix amb el mètode `buildList(path,interiorScroll)`. També es construeix el botó d'afegir directoris el qual obre un `TextInputDialog` recull el valor escrit i crea el directori.
### checkIfInalFolder(String dirPath)
Llegeix tots els fitxers del path passat per paràmetre, si algun d'ells no és un directori vol dir que és una carpeta final i canviarà a la finestra `FinalFolderWindow.java`, sinó no farà res.
### buildList(String dirPath,GridPane root)
Per a cada directori que troba crea un objecte dins del `GridPane` a partir del mètode `addItem()`
### addItem(int x,int y,String name,Gridpane interiorScroll)
Col·loca un `Pane` a les coordenades especificades per paràmetre dins del GridPane passat per paràmetre i amb el nom també passat per paràmetre. Afegeix els events de "Drag and drop" i de canvi de finestra a `FinalFolderWindow.java`.
## FinalFolderWindow.java
Classe dels fitxers .pdf d'un directori 
### Constructor
El constructor rep el GUIInteraction que serveix per recarregar la finestra, inicialitzar els butons tencar/minimitzar/enrera i canviar entre finestres. També rep el path que ens serà util per la gestió dels fitxers.
### getPane()
Construeix el `Pane`  que està format per un `ScrollPane` vertical amb un `GridPane` al seu interior que gestiona els `.pdf` amb files de 5. Aquest `GridPane` es construeix amb el mètode `buildList(path,interiorScroll)`.
### buildList(String dirPath,GridPane root)
Per a cada `.pdf` que troba crea un objecte dins del `GridPane` a partir del mètode `addItem()`
### addItem(int x,int y,String name,Gridpane interiorScroll)
Col·loca un `Pane` a les coordenades especificades per paràmetre dins del GridPane passat per paràmetre i amb el nom també passat per paràmetre.També crea el event que al fer click s'obri el document amb el programa predefinit del equip.
# Extra
## ApiConnect.java
BasicAuthLogin amb portasigma
### getLoginAuth(String user,String password)
A partir del user i password passats per paràmetre fa una BasicAuth amb l'api de portasigma i retorna true o false depenent del exit del login.
## CheckDporUpdates.java
Verificació d'actualitzacións de dpor i descarga d'aquests.
### getDispatchersNames()
Crida a la funció `getPathsAndName()` per obtenir els paths de tots els dispatchers i amb el `DispatcherManager.java` conseguim els noms de les subcarpetes. Per a cada una d'aquestes subcarpetes verifiquem que el seu `.dpor` estigui al dia amb el mètode `check()`.
### getPathsAndName():
Obre el fitxer `dispatchers.txt` i col·loca a un `ArrayList` un `HashMap` amb les claus name i path i els seus valors.
### check(String user, int i)
Ataca a la api django a la url /api/getDpor/[user] aquesta retorna les carpetes que necessiten una actualització de `.dpor` per a cada una que retorni la petició actualitzara els nous `.dpor` mitjançant el mètode `downloadNewDpors()`.
### downloadNewDpors(String string, int i,String user)
Aquest mètode ataca a la URL /api/getFile/[user]/[dir] descargant el fitxer nou en el lloc que li correspon mitjançant el mètode `downloadUsingStream()`, després mitjançant el mètode `swapStatus()` canvia a la api el valor isUpdated a `True`.
### downloadUsingStream(String urlStr,String file)
Descarga el `.dpor` i copia el contingut en un nou `File` en el directori que li toca.
### swapStatus(String string)
Ataca a la URL /api/swapStatus/[user]/[dir] i canvia el seu estat a `False`.
## DispatcherConfig
Classe que gestiona la finestra de configuració de dispatchers.
### Constructor
El constructor rep el GUIInteraction que serveix per recarregar la finestra, inicialitzar els butons tencar/minimitzar/enrera i canviar entre finestres. També rep el path que ens serà util per la gestió dels fitxers.
### init()
Inicia la finestra, el `ScrollPane` vertical amb un `VBox` a dins amb els dispatchers que es construeix amb el mètode `readConfig()`.També afegeix els botons de finestra i el botó d'afegir dispatcher que crida al mètode `addDispatcher()`
### readConfig()
Llegeix el document `dispatchers.txt` i crea un objecte dins del `VBox` per a cada un amb el mètode `createAndAddData()`
### createAndAddData(Pane panel,Text dispatcherName,Text dispatcherPath, Button deleteButton)
Crea un `Pane` amb el nom i path del dispatcher i afegeix el botó de borrar que crida al mètode `deleteDispatcher()` i l'afegeix al `VBox`.
### deleteDispatcher(String dispatcher)
Borra les files corresponents al nom de dispatcher que rep per paràmetre dins del fitxer `dispatchers.txt` i crida al mètode `readConfig()` per recargar el `ScrollPane`
### addDispatcher()
Obre un `DirectoryChooser` un cop triat el directori obre un `TextInputDialog` per escriure el nom que tindrà el dispatcher i mitjançant el mètode `addDispatcherToFile()` s'afegeix el nom i el path al fitxer `dispatchers.txt`
### addDispatcherToFile(String dispatcherPath, String dispatcherName)
Obre el fitxer `dispatchers.txt` i afegeix dos linies, el nom i el path del nou dispatcher.
### getStage
Getter del stage de la segona finestra.
## DispatcherListBuilder
Classe encarregada de construir el `HBox` del `ScrollPane` de `MainWindow.java`
### Constructor
El constructor rep el GUIInteraction que serveix per recarregar la finestra, inicialitzar els butons tencar/minimitzar/enrera i canviar entre finestres. També rep el path que ens serà util per la gestió dels fitxers. També inicialitza el Array de valors de dispatcher.
### buildList(HBox generalPanel)
Crida al mètode `getPathsAndName()` que agafa els valors path i name. Un cop te tots els paths obté per a cada dispatcher el número de fitxers que hi ha en cada una de les carpetes principals cridant al mètode `getFileNumber()` i genera un bloc dins del `HBox` mitjançant el mètode `generateBlock()`
### getFileNumber(String dirPath)
És una funció recursiva que mira dins d'un directori i retorna el numero de `.pdf` que hi ha a dins, si un dels fitxers és un directori crida a la mateixa funció.
### generateBlock(int i,HBox generalPanel)
Verifica que el dispatcher sigui un dispatcher comprobant que existeix el fitxer `uploader.properties` i escriu el numero de fitxers que hi ha a cada una de les carpetes principals, sinó mostra un text d'advertència dient que alló no es un dispatcher. També te un event que al fer click a un bloc comproba amb `ApiConnect.java` que els credencials d'aquell dispatcher són correctes. Si no ho són redirigeix a `LoginWindow.java` si estàn bé redirigeix a `MainFolderWindow.java`. Els blocs que no siguin dispatchers no tindran el event.
## DispatcherManager.java
Classe encarregada de llegir el `uploader.properties` i posar-lo a un `HashMap` per utilitzar-lo amb facilitat.
### loadProperties(String dispatcherPath)
Llegeix el `uploader.properties` del path passat per paràmetre i posa els valors dins d'un `HashMap`. Si no troba el fitxer retorna false, sinó true.
### getValue(String key)
Busca dins del HashMap una propietat i retorna el seu valor.
### modifyProperties(String dispatcherPath,String property, String newValue)
Sobreescriu el valor d'una propietat del `uploader.properties` a partir dels valors passats per paràmetre (el path, la propietat i el nou valor).
## GUIInteraction.java
Classe encarregada de gestionar les finestres del projecte.
### Constructor 
Rep el `Stage` de la pàgina principal.
### swapWindow(Pane root)
A partir del `Pane` rebut per paràmetre el canvia al `Stage`
i crida al mètode `addWindowInteraction()`
### addWindowInteraction
Afegeix els butons de Minimitzar i tencar , també fa que el `Stage` es pugui moure de posició
### Setters i getters
Un setter i getter per a cada finestra.
## TextCat
Constants dels textos en Català, més endevant s'obtindràn tots els textos d'aqui i s'afegiràn les classes `TextES.java` i `TextEN.java`





