cd dist*
cd Applet*
keytool -genkey -alias javagames -keystore Javagames < ..\..\keystore.conf
jarsigner -keystore JavaGames -storepass killzpwned -keypass killzpwned data.jar javagames
jarsigner -keystore JavaGames -storepass killzpwned -keypass killzpwned code.jar javagames
PAUSE