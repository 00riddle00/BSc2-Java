
# Eclipse

[1] Import project via git (using "new project" wizard) or "Open Projects from File System".
Eclipse will create .settings/, .bin/ folders, .classpath, .project files.

[2] Download Slick2D library. You could create lib/ folder inside eclipse workspace folder and put Slick2D/ dir inside. The latter dir will contain unzipped slick library contents. Add Slick2D library to Eclipse project (Java Build Path), indicate the native library location of Slick to the folder Slick2D/, which contains unzipped Slick library contents.

Google installing Slick2D with Eclipse

[3] Add resources. Properties -> Java Build Path -> Add Class Folder -> res/

[4] Run. If you get the "testdata/alphamap.png" error from Slick library, choose to "Run as" -> "Java Application".

# NetBeans

[1] New Project -> Java Project with Existing Sources

[2] Add Slick2D library (the lwjgl natives were inside Slick2D lib folder in my case).

Google installing Slick2D with NetBeans

[3] Add res/ folder

[4] Run


# Intellij IDEA
	

