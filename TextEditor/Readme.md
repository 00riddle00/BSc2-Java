
### GTK works with JDK11

### Put in your .xinitrc:
```
export _JAVA_OPTIONS="-Dawt.useSystemAAFontSettings=on -Dswing.aatext=true -Dswing.defaultlaf=com.sun.java.swing.plaf.gtk.GTKLookAndFeel -Dswing.crossplatformlaf=com.sun.java.swing.plaf.gtk.GTKLookAndFeel"
```

### Use this javac compile option:
```
javac --add-exports java.desktop/com.sun.java.swing.plaf.gtk=ALL-UNNAMED
```