@echo off

mkdir build
javac -cp ..\bar\build\bar.jar -d build\classes src\foo\MyAnnotation.java src\foo\MyInterface.java src\foo\Foo.java src\foo\ConcreteClass.java

