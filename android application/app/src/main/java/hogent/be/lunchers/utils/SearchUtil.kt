package hogent.be.lunchers.utils

import hogent.be.lunchers.models.Lunch
import hogent.be.lunchers.models.LunchIngredient
import hogent.be.lunchers.models.LunchTag

class SearchUtil{
    fun searchLunch(searchString:String, allLunches:List<Lunch>): List<Lunch>{
        val filteredlunches = mutableListOf<Lunch>()

        for (lunch: Lunch in allLunches){
            var naamFound = filterNaam(searchString,lunch)
            var beschrijvingFound = filterBeschrijving(searchString,lunch)
            var tagFound = filterTags(searchString,lunch)
            var ingredientFound = filterIngredienten(searchString,lunch)

            if(naamFound || beschrijvingFound || tagFound || ingredientFound){
                filteredlunches.add(lunch)
            }
        }
        return filteredlunches.toList()
    }

    private fun filterNaam(searchString: String, lunch:Lunch): Boolean{
        if(lunch.naam.contains(searchString, ignoreCase = true)){
            return true
        }
        return false
    }

    private fun filterBeschrijving(searchString: String, lunch:Lunch): Boolean{
        if(lunch.beschrijving.contains(searchString, ignoreCase = true)){
            return true
        }
        return false
    }

    private fun filterTags(searchString: String, lunch:Lunch): Boolean{
        for(tag: LunchTag in lunch.lunchTags){
            if(tag.tag.naam.contains(searchString, ignoreCase = true)){
                return true
            }
        }
        return false
    }

    private fun filterIngredienten(searchString: String, lunch:Lunch): Boolean{
        for(ingredient: LunchIngredient in lunch.lunchIngredienten){
            if(ingredient.ingredient.naam.contains(searchString,ignoreCase = true)){
                return true
            }
        }
        return false
    }
}