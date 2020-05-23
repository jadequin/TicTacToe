import kotlin.math.*

/*
         Board is a 1D-Array with switching turns (+1 for Player X' Turn, -1 for Player O's Turn)
         0 = empty  ' '         -1 = Player 'O'             1 = Player 'X'
 */

class Board(private val fields: List<Int> = listOf(0,0,0,0,0,0,0,0,0), private val turn: Int = +1, private val history: List<Board> = listOf()) {

    companion object {
        private val hashBoard = hashMapOf<Board, Int>()
        init { Board().fillMapWithNegaMax() }
    }

    fun makeMove(pos: Int) = Board(fields.take(pos) + listOf(turn) + fields.takeLast(fields.size - pos - 1), -turn, history.plus(this))

    fun undoMove() = history.last()

    fun bestMove() = nextMoves()
        .reduce {
            acc, move ->
            if(hashBoard[move]!! > hashBoard[acc]!!)
                move
            else
                acc
        }//TODO: Filter alle besten Züge heraus und wähle einen zufälligen Zug

    //Returns 1 if playerX won, -1 if playerY won and 0 if it's a tie or the game is not over
    fun result(): Int = if(threeInARow().isNullOrEmpty()) 0 else -turn

    fun isGameOver() = outOfMoves() || result() != 0

    fun isLegalMove(pos: Int) = fields[pos] == 0

    fun isPlayerXTurn() = turn == 1

    private fun outOfMoves() = nextMoves().isEmpty()

    private fun nextMoves(): List<Board> = fields.mapIndexed { index, i -> if(i == 0) makeMove(index) else null }.filterNotNull()

    //Returns: List with the winning row if there is one otherwise null
    private fun threeInARow(): List<Int>? {
        val rows = listOf(listOf(0,1,2), listOf(3,4,5), listOf(6,7,8), listOf(0,3,6), listOf(1,4,7), listOf(2,5,8), listOf(0,4,8), listOf(2,4,6))
        rows.forEach { if(it.sumBy { i -> fields.elementAt(i)}.absoluteValue == 3) return it }
        return null
    }

    private fun fillMapWithNegaMax(): Int {
        if(hashBoard[this] != null)
            return hashBoard[this]!!

        if(isGameOver()) {
            hashBoard[this] = result() * -turn
            return result() * -turn
        }
        val worst = nextMoves().fold(Int.MAX_VALUE) {worst, move -> min(worst, -move.fillMapWithNegaMax())}
        hashBoard[this] = worst
        return worst
    }

    override fun toString(): String {
        var cnt = 1
        return fields.joinToString(separator = "|", prefix = "-------\n|", postfix = "-------",
                transform = { (if(it == -1) "O" else if(it == 1) "X" else " ") + (if(cnt++ % 3 == 0) "|\n" else "") })
    }

    override fun hashCode(): Int {
        //duplicates from every rotation or mirroring
        val duplicates = listOf(listOf(0,1,2,3,4,5,6,7,8), listOf(6,3,0,7,4,1,8,5,2), listOf(8,7,6,5,4,3,2,1,0), listOf(2,5,8,1,4,7,0,3,6), //<--- Bug des Jahres!! 1 1/2 Tage Dauer statt 2,5,8 <- 2,5,6
                                listOf(6,7,8,3,4,5,0,1,2), listOf(8,5,2,7,4,1,6,3,0), listOf(2,1,0,5,4,3,8,7,6), listOf(0,3,6,1,4,7,2,5,8))
        val hashVal = duplicates.fold(Int.MAX_VALUE) {
            best, i ->
            min(best, i.foldIndexed(0) {
                index, acc, j ->
                acc + (if(fields[index] == -1) 2 else fields[index]) * (3.0.pow(j).toInt())
            })
        }
        return hashVal * turn
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return other.hashCode() == this.hashCode()
    }
}