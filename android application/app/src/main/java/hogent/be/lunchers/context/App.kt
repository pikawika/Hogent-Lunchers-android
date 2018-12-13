package hogent.be.lunchers.context

import android.app.Application
import hogent.be.lunchers.injection.components.DaggerNetworkComponent
import hogent.be.lunchers.injection.components.NetworkComponent
import hogent.be.lunchers.injection.modules.NetworkModule

class App: Application() {

    /**
     * Er is een instance nodig van de dagger [NetworkComponent] om de injectie mee uit te voeren
     *
     * Deze injector zal alle viewmodels injecten en moet dus voorzien worden
     */
    companion object {
        lateinit var injector: NetworkComponent
    }

    override fun onCreate() {
        super.onCreate()
        injector = DaggerNetworkComponent.
            builder().
            networkModule(NetworkModule(this)).
            build()
    }
}