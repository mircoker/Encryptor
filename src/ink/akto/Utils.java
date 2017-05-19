package ink.akto;

import java.lang.reflect.Array;

/**
 * Created by Ruben on 08.05.2017.
 */
public class Utils
{
    public static String arrayToStringLn(Object[] array)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object object : array)
        {
            stringBuilder.append(object.toString());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
