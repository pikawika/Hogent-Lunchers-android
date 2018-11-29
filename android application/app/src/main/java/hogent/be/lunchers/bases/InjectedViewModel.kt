package hogent.be.lunchers.bases

import android.arch.lifecycle.ViewModel
import hogent.be.lunchers.injection.components.DaggerNetworkComponent
import hogent.be.lunchers.injection.components.NetworkComponent
import hogent.be.lunchers.injection.modules.NetworkModule
import hogent.be.lunchers.viewmodels.LunchViewModel

/**
 * Een implementeerbare basis [ViewModel] klasse voor viewmodels die injectie nodig hebben via dagger.
 *
 * Er zal adhv het viewmodel type de juiste injectie voorzien worden
 *
 * momenteel compatibel met: [LunchViewModel]
 *
 * Special thanks to Harm De Weirdt for base code and clear explanation of innerworkings
 * https://github.com/hdeweirdt/metar
 */
abstract class InjectedViewModel : ViewModel() {
    /**
     * Er is een instance nodig van de dagger [NetworkComponent] om de injectie mee uit te voeren
     *
     * Deze injector zal alle viewmodels injecten en moet dus voorzien worden
     */
    private val injector: NetworkComponent = DaggerNetworkComponent
        .builder()
        .networkModule(NetworkModule)
        .build()

    /**
     * Injecteren zodra de viewmodel aangemaakt wordt
     */
    init {
        inject()
    }

    /**
     * Injecteren adhvd de reeds aangemaakte dagger instantie van de klasse die de [InjectedViewModel] overerft.
     *
     * Hier zullen nieuwe viewModels toegevoegd moeten worden alsook in de [NetworkComponent]
     */
    private fun inject() {
        when (this) {
            is LunchViewModel -> injector.inject(this)
        }
    }


}