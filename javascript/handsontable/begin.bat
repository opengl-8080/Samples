@echo off

set THIS_DIR=%~dp0

cd /d %THIS_DIR%

start cmd

start http

set CHROME_EXE="%CHROME_HOME%\chrome.exe"

%CHROME_EXE% http://localhost/index.html
