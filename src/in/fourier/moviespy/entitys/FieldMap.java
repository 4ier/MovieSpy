/**
 * FieldMap
 * TODO
 * 4ier
 * 2015年8月9日
 */
package in.fourier.moviespy.entitys;

import java.util.LinkedHashMap;

/**
 * @author 4ier
 * 
 */
public class FieldMap<K, V> extends LinkedHashMap<K, V>
{
    private static final long serialVersionUID = -1049378909747064132L;

    @Override
    public String toString()
    {
        String string = "";
        for (V value : values())
        {
            string = string + ", '" + value.toString() + "' ";
        }

        return string.substring(1);
    }
}
