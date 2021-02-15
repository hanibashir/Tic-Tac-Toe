package tictactoe

import java.util.*

val boardArray = Array(3) { CharArray(3) { ' ' } }
val scanner = Scanner(System.`in`)
var rowNum = 0
var colNum = 0

const val playerX = 'X'
const val playerO = 'O'
var currentPlayer = playerO

fun main() {

    var msg = ""
    printGrid()
    loop@ for (i in boardArray.indices) {
        while (boardArray[i].contains(' ')) {
            currentPlayer = if (currentPlayer == playerO) playerX else playerO
            getInput()
            when {
                winner(playerX) -> {
                    msg = "$playerX wins"
                    break@loop
                }
                winner(playerO) -> {
                    msg = "$playerO wins"
                    break@loop
                }
                else -> msg = "Draw"
            }
        }
    }
    print(msg)
}

fun getInput() {

    print("Enter the coordinates: ")
    try {
        rowNum = scanner.nextInt()
        colNum = scanner.nextInt()

        when {
            rowNum !in 1..3 || colNum !in 1..3 -> {
                println("Coordinates should be from 1 to 3!")
                getInput()
            }
            isNotEmptyCell(rowNum, colNum) -> {
                println("This cell is occupied! Choose another one!")
                getInput()
            }
            else -> {
                boardArray[rowNum - 1][colNum - 1] = currentPlayer
                printGrid()
            }
        }

    } catch (ime: InputMismatchException) {
        println("You should enter numbers!")
        // to avoid problem caused by invalid input (InputMismatchException)
        scanner.nextLine()
        // wrong input go back to the beginning of the method
        getInput()
    }
}

fun printGrid() {
    println("---------")
    for (charArray in boardArray) println("| ${charArray.joinToString(" ")} |")
    println("---------")
}

fun isNotEmptyCell(row: Int, col: Int) = (boardArray[row - 1][col - 1] != ' ')

private fun winner(char: Char): Boolean {
    // check the row
    for (i in 0..2) {
        if (char == boardArray[i][0] && char == boardArray[i][1] && char == boardArray[i][2]) {
            return true
        }
    }

    // check the column
    for (i in 0..2) {
        if (char == boardArray[0][i]
            && char == boardArray[1][i]
            && char == boardArray[2][i]
        ) {
            return true
        }
    }

    // check diagonal or anti diagonal
    for (i in 0..2) {
        if ((
                    // diagonal
                    char == boardArray[0][0]
                            && char == boardArray[1][1]
                            && char == boardArray[2][2])
            ||
            // anti-diagonal
            (char == boardArray[0][2]
                    && char == boardArray[1][1]
                    && char == boardArray[2][0])
        ) {
            return true
        }
    }
    return false
}