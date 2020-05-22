
/*
    2020-05-16
 */
import java.util.*
import kotlin.concurrent.schedule

fun example() {
    var board = Board(listOf(0,0,0,0,0,0,0,0,0)) //new clean board
    val reader = Scanner(System.`in`)

    println(board)
    while(!board.isGameOver()) {
        board = board.bestMove()
        println(board)

        print("Your Turn - Enter a valid number (1-9): ")
        val input:Int = reader.nextInt()
        board = board.makeMove(input - 1)
        println(board)

    }
    println(board)
}

fun main() {
    var b = Board()
    println(b)
    b = b.bestMove()
    println(b)
}