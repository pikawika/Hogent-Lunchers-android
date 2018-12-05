package hogent.be.lunchers.viewmodels

import android.arch.lifecycle.MutableLiveData
import hogent.be.lunchers.bases.InjectedViewModel
import hogent.be.lunchers.networks.LunchersApi
import hogent.be.lunchers.models.Lunch
import hogent.be.lunchers.utils.MessageUtil
import hogent.be.lunchers.utils.PreferenceUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Een [InjectedViewModel] klasse die alle lunches bevat.
 */
class ReservationViewModel : InjectedViewModel() {

    @Inject
    lateinit var lunchersApi: LunchersApi

    fun reserveer(lunchid:Int, aantal:Int){
        MessageUtil.showToast("Reserveer ${aantal} keer de lunch met id ${lunchid}")
    }
}