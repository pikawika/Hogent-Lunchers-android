package hogent.be.lunchers.injection.components

import hogent.be.lunchers.injection.modules.NetworkModule
import hogent.be.lunchers.viewmodels.LunchViewModel
import dagger.Component
import hogent.be.lunchers.viewmodels.AccountViewModel
import javax.inject.Singleton

/**
 * Deze [NetworkComponent] dient als tussenlaag tussen de [NetworkModule] en de effectieve [LunchViewModel]
 *
 * Momenteel compatibel met: [LunchViewModel]
 */
@Singleton
/**
 * We hebben de netwerkmodule nodig voor het ophalen van de data
 */
@Component(modules = [NetworkModule::class])
interface NetworkComponent {

    /**
     * Doet dependency injection op de meegegeven LunchViewModel
     *
     * @param lunchViewModel De [LunchViewModel] dat je wilt voorzien van dependency injection. Verplicht van type [LunchViewModel].
     */
    fun inject(lunchViewModel: LunchViewModel)

    fun inject(accountViewModel: AccountViewModel)

    //soortgelijke functies aanmaken voor alle andere injecties van models
}