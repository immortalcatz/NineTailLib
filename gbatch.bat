@ECHO OFF
COLOR 0E
GOTO:INIT

:INIT
ECHO.
ECHO    .------..------..------..------..------..------.
ECHO    ^|G.--. ^|^|B.--. ^|^|A.--. ^|^|T.--. ^|^|C.--. ^|^|H.--. ^|
ECHO    ^| :/\: ^|^| :(): ^|^| (\/) ^|^| :/\: ^|^| :/\: ^|^| :/\: ^|
ECHO    ^| :\/: ^|^| ()() ^|^| :\/: ^|^| (__) ^|^| :\/: ^|^| (__) ^|
ECHO    ^| '--'G^|^| '--'B^|^| '--'A^|^| '--'T^|^| '--'C^|^| '--'H^|
ECHO    `------'`------'`------'`------'`------'`------'
ECHO.
ECHO Please choose an option from below
ECHO and enter the number of the option
ECHO you want to use.
ECHO.
ECHO 1 Setup a development workspace.
ECHO 2 Refresh dependencies.
ECHO 3 Run the client.
ECHO 4 Run the server.
ECHO 5 Clean the workspace.
ECHO 6 Exit GBatch.
ECHO.
SET /P INPUT="Enter your option:"
IF %INPUT%==1 (
	GOTO:SETUPWORKSPACE
) ELSE (
	IF %INPUT%==2 (
		GOTO:REFRESHDEPS
	) ELSE (
		IF %INPUT%==3 (
			GOTO:RUNCLIENT
		) ELSE (
			IF %INPUT%==4 (
				GOTO:RUNSERVER
			) ELSE (
				IF %INPUT%==5 (
					GOTO:CLEANPROMPT
				) ELSE (
					IF %INPUT%==6 (
						EXIT
					) ELSE (
						GOTO:INPUTERROR
					)
				)
			)
		)
	)
)

:SETUPWORKSPACE
ECHO.
ECHO Please select what IDE you use below.
ECHO 1 Eclipse
ECHO 2 Intellij IDEA
ECHO.
SET /P INPUT="Enter your option:"
IF %INPUT%==1 (
	GOTO:SETUPECLIPSE
) ELSE (
	IF %INPUT%==2 (
		GOTO:SETUPIDEA
	) ELSE (
		GOTO:INPUTERROR
	)
)

:SETUPECLIPSE
ECHO Please wait while Gradle is setting
ECHO up your workspace...
ECHO.
START gradlew setupDecompWorkspace eclipse
ECHO.
ECHO Task successfull !
PAUSE
CLS
GOTO:INIT

:SETUPIDEA
ECHO Please wait while Gradle is setting
ECHO up your workspace...
ECHO.
START gradlew setupDecompWorkspace idea
ECHO.
ECHO Task successfull !
PAUSE
CLS
GOTO:INIT

:REFRESHDEPS
ECHO Please wait while Gradle is refreshing
ECHO your workspace dependencies...
ECHO.
START gradlew --refresh-dependencies
ECHO.
ECHO Task successfull !
PAUSE
CLS
GOTO:INIT

:RUNCLIENT
ECHO Please wait while Gradle is starting
ECHO your Minecraft client...
ECHO.
START gradlew runClient
ECHO.
ECHO Task successfull !
PAUSE
CLS
GOTO:INIT

:RUNSERVER
ECHO Please wait while Gradle is starting
ECHO your Minecraft server...
ECHO.
START gradlew runServer
ECHO.
ECHO Task successfull !
PAUSE
CLS
GOTO:INIT

:CLEANPROMPT
ECHO.
ECHO Are you sure you want to clean your 
ECHO whole workspace ?
ECHO.
ECHO Enter 'yes' below to confirm your option
ECHO or enter 'no' to go back to the main menu.
ECHO.
SET /P INPUT="Enter your option:"
IF %INPUT%=="yes" (
	GOTO:CLEANWORKSPACE
) ELSE (
	GOTO:OPERATIONCANCELED
)

:CLEANWORKSPACE
ECHO Please wait while Gradle is cleaning
ECHO your workspace...
ECHO.
START gradlew clean
ECHO.
ECHO Task successfull !
PAUSE
CLS
GOTO:INIT

:INPUTERROR
ECHO.
ECHO %INPUT% is not a valid option !
ECHO.
PAUSE
CLS
GOTO:INIT

:OPERATIONCANCELED
ECHO.
ECHO Operation canceled !
ECHO.
PAUSE
CLS
GOTO:INIT