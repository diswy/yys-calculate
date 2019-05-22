package xiaofu.lib.base.timer

interface ITimerEnd : ITimer {
    override fun onTime(second: Int) {
    }
}