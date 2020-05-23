
/*
    2020-05-16
 */
import java.lang.NumberFormatException
import java.util.*

fun playGame() {
    val reader = Scanner(System.`in`)
    var b = Board()

    println("\n".repeat(5)) //setup console display

    while(true) {
        println(if(b.isPlayerXTurn()) "Player X Turn" else "Player O Turn")
        println(b)
        println("[ENTER] = AI makes a best move\t" +
                " [1-9]  = make your own move\t" +
                "  [b]   = undo move\t" +
                "  [r]   = reset game\t" +
                " [q/e]  = quit game\t")

        val input = reader.nextLine()

        if(input.isEmpty() && !b.isGameOver())
            b = b.bestMove()
        else if(input.startsWith("q") || input.startsWith("e"))
            return
        else if(input.startsWith("b"))
            if(b == Board())
                println("Can't undo the origin state")
            else
                b = b.undoMove()
        else if(input.startsWith("r"))
            b = Board()
        else if(!b.isGameOver()){
            val num: Int
            try {
                num = input.toInt()
                if(b.isLegalMove(num - 1))
                    b = b.makeMove(num - 1)
                else
                    println("Try a valid move!")
            } catch(nfe: NumberFormatException) {
                println("Try again and enter a valid keystroke")
            }
        }
        if(b.isGameOver())
            println(if(b.result() == 1) "Player X won the game" else if(b.result() == -1) "Player O won the game" else "Tie")

        println("\n".repeat(2))
    }
}



fun main() {
    playGame()
}