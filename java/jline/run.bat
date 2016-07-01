@echo off

call gradle installDist
build\install\jline\bin\jline-sample.bat
