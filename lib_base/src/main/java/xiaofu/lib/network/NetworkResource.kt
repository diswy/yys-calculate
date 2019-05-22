package xiaofu.lib.network

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 暴露网络状态，适用于不需要缓存的请求
 * Created by @author xiaofu on 2019/1/9.
 */
abstract class NetworkResource<RequestType>
@MainThread constructor() {
    private val result = MediatorLiveData<Resource<RequestType>>()

    init {
        result.value = Resource.loading(null)// 初始化空数据，开始loading
        fetchFromNetwork()
    }


    @MainThread
    private fun setValue(newValue: Resource<RequestType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork() {
        val apiResponse = createCall()
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            when (response) {
                is ApiSuccessResponse -> {
                    Observable.just(response)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe { newData ->
                                saveResult(newData.body)
                                setValue(Resource.success(newData.body))
                            }
                }
                is ApiEmptyResponse -> {
                    Observable.just(response)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                setValue(Resource.success(null))
                            }
                }
                is ApiErrorResponse -> {
                    onFetchFailed()
                    setValue(Resource.error(response.throwable, null))
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<RequestType>>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    @WorkerThread
    protected open fun saveResult(item: RequestType) {
    }
}