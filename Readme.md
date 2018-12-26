# .settings/

.settings folder - stores eclipse configs

.settings/org.eclipse.jdt.core.prefs

```
eclipse.preferences.version=1
org.eclipse.jdt.core.compiler.codegen.inlineJsrBytecode=enabled
org.eclipse.jdt.core.compiler.codegen.targetPlatform=1.8
org.eclipse.jdt.core.compiler.codegen.unusedLocal=preserve
org.eclipse.jdt.core.compiler.compliance=1.8
org.eclipse.jdt.core.compiler.debug.lineNumber=generate
org.eclipse.jdt.core.compiler.debug.localVariable=generate
org.eclipse.jdt.core.compiler.debug.sourceFile=generate
org.eclipse.jdt.core.compiler.problem.assertIdentifier=error
org.eclipse.jdt.core.compiler.problem.enumIdentifier=error
org.eclipse.jdt.core.compiler.source=1.8
```

# .classpath

.classpath file: stores classpath.
My classpath:

```
<?xml version="1.0" encoding="UTF-8"?>
<classpath>
        <classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-1.8"/>
        <classpathentry kind="src" path="src"/>
        <classpathentry kind="lib" path="res"/>
        <classpathentry kind="lib" path="/home/riddle/eclipse-workspace/libs/jars/slick.jar"/>
        <classpathentry kind="lib" path="/home/riddle/eclipse-workspace/libs/jars/lwjgl.jar">
                <attributes>
                        <attribute name="org.eclipse.jdt.launching.CLASSPATH_ATTR_LIBRARY_PATH_ENTRY" value="/home/riddle/eclipse-workspace/libs/lwjgl/native/linux"/>
                </attributes>
        </classpathentry>
        <classpathentry kind="lib" path="/home/riddle/eclipse-workspace/libs/jars/jorbis-0.0.15.jar"/>
        <classpathentry kind="lib" path="/home/riddle/eclipse-workspace/libs/jars/jogg-0.0.7.jar"/>
        <classpathentry kind="lib" path="/home/riddle/eclipse-workspace/libs/jars/jinput.jar"/>
        <classpathentry kind="output" path="bin"/>
</classpath>
```
