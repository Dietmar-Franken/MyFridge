package xyz.marshallaf.myfridge;

/**
 * Created by Andrew Marshall on 1/24/2017.
 */

public final class Utils {
    // not instantiable
    private Utils() {}

    // volume conversions
    private static double ML_IN_L = 1000.0;
    private static double ML_IN_FLOZ = 29.5735295625;
    private static double ML_IN_PINT = 473.176473;
    private static double ML_IN_QUART = 946.352946;
    private static double ML_IN_GALLON = 3785.411784;
    private static double ML_IN_TSP = 4.92892159375;
    private static double ML_IN_TBSP = 14.78676478125;
    private static double ML_IN_CUP = 236.5882365;

    // mass conversions
    private static double G_IN_MG = 0.001;
    private static double G_IN_KG = 1000.0;
    private static double G_IN_OZ = 28.349523125;
    private static double G_IN_POUND = 453.59237;

//    // convert units
//    public static double convert(double amount, int units, boolean forStorage) {
//        double conversionFactor = 1;
//        if (units == 0) {
//            // items
//            return amount;
//        }
//        else if (units >= 200 && units < 300) {
//            // volume, starting with ml
//            switch (units) {
//                case FoodContract.FoodEntry.UNIT_ML:
//                    break;
//                case FoodContract.FoodEntry.UNIT_L:
//                    conversionFactor = ML_IN_L;
//                    break;
//                case FoodContract.FoodEntry.UNIT_FLOZ:
//                    conversionFactor = ML_IN_FLOZ;
//                    break;
//                case FoodContract.FoodEntry.UNIT_PINT:
//                    conversionFactor = ML_IN_PINT;
//                    break;
//                case FoodContract.FoodEntry.UNIT_QUART:
//                    conversionFactor = ML_IN_QUART;
//                    break;
//                case FoodContract.FoodEntry.UNIT_GALLON:
//                    conversionFactor = ML_IN_GALLON;
//                    break;
//                case FoodContract.FoodEntry.UNIT_TSP:
//                    conversionFactor = ML_IN_TSP;
//                    break;
//                case FoodContract.FoodEntry.UNIT_TBSP:
//                    conversionFactor = ML_IN_TBSP;
//                    break;
//                case FoodContract.FoodEntry.UNIT_CUP:
//                    conversionFactor = ML_IN_CUP;
//                    break;
//            }
//        } else if (units >= 300 && units < 400) {
//            // mass, starting with g
//            switch (units) {
//                case FoodContract.FoodEntry.UNIT_GRAM:
//                    return amount;
//                case FoodContract.FoodEntry.UNIT_KG:
//                    conversionFactor = G_IN_KG;
//                    break;
//                case FoodContract.FoodEntry.UNIT_MG:
//                    conversionFactor = G_IN_MG;
//                    break;
//                case FoodContract.FoodEntry.UNIT_OZ:
//                    conversionFactor = G_IN_OZ;
//                    break;
//                case FoodContract.FoodEntry.UNIT_POUND:
//                    conversionFactor = G_IN_POUND;
//                    break;
//            }
//        }
//        if (forStorage) {
//            return amount * conversionFactor;
//        } else {
//            return amount / conversionFactor;
//        }
//    }
//
//    public static int unitToString(int unitCode) {
//        switch (unitCode) {
//            case FoodContract.FoodEntry.UNIT_ITEM:
//                return R.string.item;
//            case FoodContract.FoodEntry.UNIT_ML:
//                return R.string.milliliter;
//            case FoodContract.FoodEntry.UNIT_L:
//                return R.string.liter;
//            case FoodContract.FoodEntry.UNIT_FLOZ:
//                return R.string.fluid_oz;
//            case FoodContract.FoodEntry.UNIT_PINT:
//                return R.string.pint;
//            case FoodContract.FoodEntry.UNIT_QUART:
//                return R.string.quart;
//            case FoodContract.FoodEntry.UNIT_GALLON:
//                return R.string.gallon;
//            case FoodContract.FoodEntry.UNIT_TSP:
//                return R.string.teaspoon;
//            case FoodContract.FoodEntry.UNIT_TBSP:
//                return R.string.tablespoon;
//            case FoodContract.FoodEntry.UNIT_CUP:
//                return R.string.cup;
//            case FoodContract.FoodEntry.UNIT_GRAM:
//                return R.string.gram;
//            case FoodContract.FoodEntry.UNIT_KG:
//                return R.string.kilogram;
//            case FoodContract.FoodEntry.UNIT_MG:
//                return R.string.milligram;
//            case FoodContract.FoodEntry.UNIT_OZ:
//                return R.string.ounce;
//            case FoodContract.FoodEntry.UNIT_POUND:
//                return R.string.pound;
//            default:
//                return R.string.item;
//        }
//    }
}
