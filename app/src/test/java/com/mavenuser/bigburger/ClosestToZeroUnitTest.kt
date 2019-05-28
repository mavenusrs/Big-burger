package com.mavenuser.bigburger

import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ClosestToZeroUnitTest {


    @Test
    fun testCase1(){
        val items: Array<Double> = arrayOf(7.0,-10.0,13.0,8.0,4.0,-7.2,-12.0,-3.7,3.5,9.6,6.5,-1.7,-6.2,7.0)
        val expectedValue =   -1.7

        assert(expectedValue == closestToZero(items))
    }

    @Test
    fun testCase3(){
        val items: Array<Double> = arrayOf(7.0,1.7,5.0,13.0,8.0,4.0,-7.2,-12.0,-3.7,3.5,9.6,6.5,-1.7,-6.2,7.0)
        val expectedValue =   1.7

        assert(expectedValue == closestToZero(items))
    }


    @Test
    fun testCase2(){
        val items: Array<Double> = arrayOf(7.0,-10.0,13.0,8.0,4.0,-7.2,-12.0,-3.7,3.5,9.6,6.5,-1.7,-6.2,7.0)
        val expectedValue =   -1.7

        assert(expectedValue == closestToZero2(items))
    }

    fun closestToZero(items: Array<Double>): Double{
        if (items.isEmpty())
            throw Exception("Empty list")
        if (items.size == 1)
            return items[0]

        var minValue = items[0]

        for(i in 1..items.size-1){
            if (Math.abs(items[i]) < Math.abs(minValue)){
                minValue = items[i]
            }else if (Math.abs(items[i]) == Math.abs(minValue)){
                if (items[i] > minValue)
                    minValue = items[i]
            }
        }


        return minValue
    }


    fun closestToZero2(items: Array<Double> ): Double{
        var minValue = items[0]

        for(i in 1..items.size-1){
            val diff = Math.abs(minValue) - Math.abs(items[i])

            if ((diff == 0.0 && items[i] > minValue)
                || diff > 0){
                minValue = items[i]
            }

        }

        return minValue
    }
}
