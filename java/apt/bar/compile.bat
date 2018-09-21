@echo off

mkdir build
javac -d build\classes src\bar\MyProcessor.java
mkdir build\bar\bar
copy build\classes\bar\MyProcessor.class build\bar\bar
mkdir build\bar\META-INF\services
copy resources\META-INF\services\javax.annotation.processing.Processor build\bar\META-INF\services

jar -c -f .\build\bar.jar -C build\bar .
