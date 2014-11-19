# ExecuteAnonymous
================

Ant task to execute anonymous Apex code on a Salesforce environment

## Build
It is assumed that you 
- have a working ant
- salesforce jar is somewhere in the classpath
- `JAVA_HOME` points to a recent JDK

The file `ExecuteAnonymous.jar` will be created in the dist subdirectory when you run `ant`.

## Running
Place the `ExecuteAnonymous.jar` in the classpath (I have copied it into directory containing ant-salesforce.jar).
The `build.xml` in `test` subdirectory gives you some hints on running this new ant target.
Either use `fileName` or `code` attribute to the `anonExec` target. The `fileName` will be used if set both.
