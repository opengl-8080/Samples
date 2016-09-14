object FizzBuzz {

  def main(args: Array[String]): Unit = {
    // A に 100 をセット
    A     DC  100
    // B に 200 をセット
    B     DC  200
    // ANS という領域を確保
    ANS   DS  1

    // GR1 に A の値をセット
    LD.   GR1 A

    // GR1 と B の値を加算して、結果を GR1 に代入
    ADDR. GR1 B

    // GR1 の内容を ANS に書き出す
    ST. GR1 ANS

    println(Memory.ANS)
  }
}

class StGr1 {
  def ANS(): Unit = {
    Memory.ANS = Memory.GR1
  }
}

object ST {
  def GR1(): StGr1 = {
    new StGr1
  }
}

class Gr1 {
  def A(): Unit = {
    Memory.GR1 = Memory.A
  }
}

object Memory {
  var A = 0
  var B = 0
  var GR1 = 0
  var ANS = 0
}

class AddrGr1 {
  def B(): Unit = {
    Memory.GR1 = Memory.GR1 + Memory.B
  }
}

object ADDR {
  def GR1(): AddrGr1 = {
    new AddrGr1
  }
}

object LD {
  def GR1(): Gr1 = {
    new Gr1
  }
}

object A {
  def DC(i: Int): Unit = {
    Memory.A = i
  }
}

object B {
  def DC(i: Int): Unit = {
    Memory.B = i
  }
}

object ANS {
  def DS(i: Int): Unit = {

  }
}