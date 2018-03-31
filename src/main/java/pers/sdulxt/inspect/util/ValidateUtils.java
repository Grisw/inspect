package pers.sdulxt.inspect.util;

public class ValidateUtils {

    /**
     * Check if any values of the objects are null.
     * @param objects The objects to check.
     * @return True if one or more objects are null. False otherwise.
     */
    public static boolean checkNull(Object... objects){
        for(Object object : objects){
            if(object == null)
                return true;
        }
        return false;
    }
}
