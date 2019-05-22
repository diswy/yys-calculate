package xiaofu.lib.base.timer

interface ITimer {
    fun onTime(second: Int)
    fun onTimeEnd()
}