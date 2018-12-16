package hogent.be.lunchers.utils

import hogent.be.lunchers.enums.FilterEnum
import hogent.be.lunchers.models.Lunch
import hogent.be.lunchers.models.LunchIngredient
import hogent.be.lunchers.models.LunchTag

object SearchUtil{
    /**
     * Zoekt een lunches die overeenkomen aan de hand van de meegeven zoekstring
     *
     * Zoekt op:
     * - lunchnaam
     * - tag
     * - ingredient
     * - handelsnaam
     * - gemeente
     *
     * @param searchString de zoekterm
     * @param allLunches de lijst waarin gezocht moet worden
     */
    @JvmStatic
    fun searchLunch(searchString:String, allLunches:List<Lunch>): List<Lunch>{
        val filteredlunches = mutableListOf<Lunch>()

        for (lunch: Lunch in allLunches){
            var found = filterLunchNaam(searchString,lunch)
            if (!found)
                found = filterHandelsNaam(searchString,lunch)
            if (!found)
                found = hasCity(searchString,lunch)
            if (!found)
                found = filterTags(searchString,lunch)
            if (!found)
                found = filterIngredienten(searchString,lunch)

            if(found){
                filteredlunches.add(lunch)
            }
        }
        return filteredlunches.toList()
    }

    @JvmStatic
    fun filterLunch(filterEnum: FilterEnum, allLunches:List<Lunch>): List<Lunch>{
        var filteredlunches: List<Lunch> = emptyList()

        if (filterEnum == FilterEnum.RECENT)
            filteredlunches = allLunches.sortedWith(compareBy { it.lunchId }).reversed()

        if (filterEnum == FilterEnum.PRICE_HIGHEST)
            filteredlunches = allLunches.sortedWith(compareBy { it.price }).reversed()

        if (filterEnum == FilterEnum.PRICE_LOWEST)
            filteredlunches = allLunches.sortedWith(compareBy { it.price })

        //filter niet gevonden gewoon houden
        if (!filteredlunches.any())
            filteredlunches = allLunches

        return filteredlunches.toList()
    }

    @JvmStatic
    private fun filterLunchNaam(searchString: String, lunch:Lunch): Boolean{
        if(lunch.name.contains(searchString, ignoreCase = true)){
            return true
        }
        return false
    }

    @JvmStatic
    private fun filterHandelsNaam(searchString: String, lunch:Lunch): Boolean{
        if(lunch.merchant.companyName.contains(searchString, ignoreCase = true)){
            return true
        }
        return false
    }

    @JvmStatic
    private fun filterTags(searchString: String, lunch:Lunch): Boolean{
        for(tag: LunchTag in lunch.lunchTags){
            if(tag.tag.name.contains(searchString, ignoreCase = true)){
                return true
            }
        }
        return false
    }

    @JvmStatic
    private fun filterIngredienten(searchString: String, lunch:Lunch): Boolean{
        for(ingredient: LunchIngredient in lunch.lunchIngredienten){
            if(ingredient.ingredient.name.contains(searchString,ignoreCase = true)){
                return true
            }
        }
        return false
    }

    @JvmStatic
    private fun hasCity(searchString: String, lunch:Lunch): Boolean{
        return lunch.merchant.location.city.startsWith(searchString, ignoreCase = true)
    }
}