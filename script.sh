rm -rf html-parser &&
git clone https://kimheonseung@github.com/kimheonseung/html-parser.git ./html-parser &&
cd html-parser &&
chmod +x gradlew &&
./gradlew clean build -i &&
cd build/libs/ &&
java -jar html-parser-v1.jar
