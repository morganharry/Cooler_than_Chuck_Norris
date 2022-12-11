package chucknorris

import kotlin.math.pow

//encode
fun encode() {
    println("Input string:")
    val str = readln().toString()
    val strBin = strToBin(str)
    val strChucksCode = strToChucksCode(strBin)
    println("Encoded string:")
    println(strChucksCode)
}

fun strToBin(str: String): String {
    var strBin = ""
    for (i in 0..str.length - 1) {
        val b = Integer.toBinaryString(str[i].code)
        val c = String.format("%07d", b.toInt())
        strBin += c
    }
    return strBin
}

fun strToChucksCode(strBin: String): String {
    var strChucksCode = ""
    var strNumber = ""
    var type = ' '
    for (n in 0..strBin.length - 1) {
        when {
            strBin[n] != type -> {
                type = strBin[n]
                if (n == 0) {
                    when (strBin[n]) {
                        '1' -> strChucksCode += "0 0"
                        '0' -> strChucksCode += "00 0"
                    }
                } else {
                    when (strBin[n]) {
                        '1' -> strChucksCode += " 0 0"
                        '0' -> strChucksCode += " 00 0"
                    }
                }
            }

            strBin[n] == type -> strChucksCode += "0"
        }
    }
    return strChucksCode
}

//decode .
fun decode() {
    println("Input encoded string:")
    try {
        val inputCode = readln()
        checkTo0(inputCode)
        val listCode = inputCode.split(" ").toMutableList()
        val listCodeBinary = toBinary(listCode)
        val listInteger = toInteger(listCodeBinary)
        val listChars = toChars(listInteger)
        println("Decoded string:")
        println(listChars.joinToString(""))
    } catch (e: Exception) {
        println("Encoded string is not valid.")
    }
}

fun checkTo0(inputCode: String) {
    for (i in 0..inputCode.length - 1) {
        if (inputCode[i] != '0' && inputCode[i] != ' ') {
            throw Exception()
        }
    }
    return
}

fun checkTo0Numbers(listType: MutableList<String>) {
    for (i in 0..listType.size - 1) {
        if (listType[i] != "0" && listType[i] != "00") {
            throw Exception()
        }
    }
    return
}

fun checkToEqualsTypeNumber(listType: MutableList<String>, listNumber: MutableList<String>) {
    if (listType.size != listNumber.size) {
        throw Exception()
    }
    return
}

fun toBinary(listCode: MutableList<String>): MutableList<String> {
    val listType = mutableListOf<String>()
    val listNumber = mutableListOf<String>()
    for (i in 0..listCode.size - 1 step 2) {
        listType.add(listCode[i])
    }
    checkTo0Numbers(listType)
    for (n in 1..listCode.size - 1 step 2) {
        listNumber.add(listCode[n])
    }
    checkToEqualsTypeNumber(listType, listNumber)
    val listCodeBinary = mutableListOf<String>()
    var str = ""
    for (i in 0..listNumber.size - 1) {
        val a = listNumber[i].length
        repeat(a) {
            if (listType[i] == "00")
                str += "0"
            else
                str += "1"
        }
    }
    for (k in 0..str.length - 1 step 7) {
        listCodeBinary.add(str.substring(k, k + 7))
    }
    return listCodeBinary
}

fun toInteger(listCodeBinary: MutableList<String>): MutableList<Int> {
    val listInteger = mutableListOf<Int>()
    for (i in 0..listCodeBinary.size - 1) {
        listInteger.add(binaryToDecimal(listCodeBinary[i]))
    }
    return listInteger
}

fun binaryToDecimal(binaryStr: String): Int {
    var decimalStr = 0
    for (i in 0..binaryStr.length - 1) {
        if (binaryStr[i] != '0') {
            val n = -i + 6
            decimalStr += ((2.0).pow(n)).toInt()
        }
    }
    return decimalStr
}

fun toChars(listInteger: MutableList<Int>): MutableList<Char> {
    val listChars = mutableListOf<Char>()
    for (i in 0..listInteger.size - 1) {
        listChars.add(listInteger[i].toChar())
    }
    return listChars
}

fun main() {
    do {
        println("Please input operation (encode/decode/exit):")
        var input = readln()
        when (input) {
            "encode" -> encode()
            "decode" -> decode()
            "exit" -> break
            else -> println("There is no '${input}' operation")
        }
    } while (input != "exit")
    println("Bye!")
}
