package hogent.be.lunchers.utils

import hogent.be.lunchers.enums.FilterEnum
import hogent.be.lunchers.models.Lunch
import hogent.be.lunchers.models.LunchIngredient
import hogent.be.lunchers.models.LunchTag

class SearchUtil{
    fun searchLunch(searchString:String, allLunches:List<Lunch>): List<Lunch>{
        val filteredlunches = mutableListOf<Lunch>()

        for (lunch: Lunch in allLunches){
            var naamFound = filterLunchNaam(searchString,lunch)
            var beschrijvingFound = filterBeschrijving(searchString,lunch)
            var tagFound = filterTags(searchString,lunch)
            var ingredientFound = filterIngredienten(searchString,lunch)
            var handelsnaamFound = filterHandelsNaam(searchString,lunch)

            if(naamFound || beschrijvingFound || tagFound || ingredientFound || handelsnaamFound){
                filteredlunches.add(lunch)
            }
        }
        return filteredlunches.toList()
    }

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

    private fun filterLunchNaam(searchString: String, lunch:Lunch): Boolean{
        if(lunch.name.contains(searchString, ignoreCase = true)){
            return true
        }
        return false
    }

    private fun filterHandelsNaam(searchString: String, lunch:Lunch): Boolean{
        if(lunch.merchant.companyName.contains(searchString, ignoreCase = true)){
            return true
        }
        return false
    }

    private fun filterBeschrijving(searchString: String, lunch:Lunch): Boolean{
        if(lunch.description.contains(searchString, ignoreCase = true)){
            return true
        }
        return false
    }

    private fun filterTags(searchString: String, lunch:Lunch): Boolean{
        for(tag: LunchTag in lunch.lunchTags){
            if(tag.tag.name.contains(searchString, ignoreCase = true)){
                return true
            }
        }
        return false
    }

    private fun filterIngredienten(searchString: String, lunch:Lunch): Boolean{
        for(ingredient: LunchIngredient in lunch.lunchIngredienten){
            if(ingredient.ingredient.name.contains(searchString,ignoreCase = true)){
                return true
            }
        }
        return false
    }
}