object SumOfMultiples {

    fun sum(factors: Set<Int>, limit: Int): Int {
        //TODO("Implement this function to complete the task")

        var result = 0
        for (i in 1 until limit) {
            for (j in factors) {
                if (j == 0) {
                    break
                }
                if (i.rem(j) == 0) {
                    result += i
                    break
                }
            }
        }
        return result
    }
}
