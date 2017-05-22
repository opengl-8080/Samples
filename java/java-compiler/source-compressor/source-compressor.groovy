import java.nio.file.*
import java.nio.charset.*
import groovy.io.FileType

def inDirPath = new File(args[0]).toPath()

def outDirPath = Paths.get("./out", inDirPath.fileName.toString())
Files.createDirectories(outDirPath)

inDirPath.toFile().eachFileRecurse(FileType.FILES) { file ->
  def inPath = file.toPath()
  def outPath = outDirPath.resolve(inDirPath.relativize(inPath))

  Files.createDirectories(outPath.parent)

  if (file.name.endsWith(".java")) {
    def text = new String(Files.readAllBytes(inPath), StandardCharsets.UTF_8)
    def compressedText = text.replaceAll(/(?m)^[ \t]+/, '')
                          .replaceAll(/(?m)[ \t]+$/, '')
                          .replaceAll('(?m)//.*$', '')
                          .replaceAll(/\r|\n/, ' ')
                          .replaceAll('/\\*.*?\\*/', '')

    Files.write(outPath, compressedText.getBytes(StandardCharsets.UTF_8))
  } else {
    if (Files.exists(outPath)) {
      throw new RuntimeException("file already exists. ($outPath)");
    }
    Files.copy(inPath, outPath)
  }
}
