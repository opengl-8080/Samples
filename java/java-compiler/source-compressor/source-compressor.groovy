def result = new StringBuilder()
def inMultiLineComment = false
new File(args[0]).eachLine('UTF-8') { line ->
  line = line.trim()
  if (line.isEmpty()) {
    return
  }
  def chars = line.chars
  def index = 0
  def c
  def inStringLiteral = false

  while (index < chars.length) {
    c = chars[index]
    if (c == '/' as char) {
      if (inMultiLineComment) {
        // ignore
      } else if (!inStringLiteral) {
        if (index + 1 < chars.length) {
          if (chars[index + 1] == '/' as char) {
            break // 残りは全部行コメントなので無視
          } else if (chars[index + 1] == '*' as char) {
            inMultiLineComment = true
          } else {
            result += c
          }
        } else {
          result += c
        }
      } else {
        result += c
      }
    } else if (c == '*' as char) {
      if (inStringLiteral) {
        result += c
      } else if (inMultiLineComment) {
        if (index + 1 < chars.length) {
          if (chars[index + 1] == '/' as char) {
            // 複数行コメントの終了
            index++
            inMultiLineComment = false
          }
        }
      } else {
        result += c
      }
    } else if (inMultiLineComment) {
      // ignore
    } else if (c == '"' as char) {
      inStringLiteral = !inStringLiteral
      result += c
    } else if (c == '\\' as char) {
      result += c + chars[++index]
    } else if (c == ' ' as char) {
      if (inStringLiteral) {
        result += c
      } else if (index + 1 < chars.length) {
        if (chars[index + 1] == ' ' as char) {
          // ignore
        } else {
          result += c
        }
      } else {
        result += c
      }
    } else {
      result += c
    }

    index++
  }

  if (!inMultiLineComment
      && c != ';' as char
      && c != ' ' as char
      && !result.toString().isEmpty()) {
    result += ' '
  }
}

println(result)

return

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
    def compressedText = new StringBuilder();
    inPath.toFile().eachLine('UTF-8') { line ->
      compressedText += line.replaceAll(/^[ \t]+/, '')
                          .replaceAll(/[ \t]+$/, '')
                          .replaceAll('(?<=[ ;])//.*$', '')
                          .replaceAll('/\\*.*?\\*/', '')
    }
    compressedText = compressedText.toString().replaceAll('/\\*.*?\\*/', '')
    // def text = new String(Files.readAllBytes(inPath), StandardCharsets.UTF_8)
    // def compressedText = text.replaceAll(/(?m)^[ \t]+/, '')
    //                       .replaceAll(/(?m)[ \t]+$/, '')
    //                       .replaceAll('(?m)(?<=[; \t])//.*$', '')
    //                       .replaceAll(/\r|\n/, ' ')
    //                       .replaceAll('/\\*.*?\\*/', '')

    Files.write(outPath, compressedText.getBytes(StandardCharsets.UTF_8))
  } else {
    if (Files.exists(outPath)) {
      throw new RuntimeException("file already exists. ($outPath)");
    }
    Files.copy(inPath, outPath)
  }
}
