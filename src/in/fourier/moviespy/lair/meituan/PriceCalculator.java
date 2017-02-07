/**
 * PriceCalculator
 * TODO
 * 4ier
 * 2017年1月4日
 */
package in.fourier.moviespy.lair.meituan;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 4ier <br>
 * @charset "UTF-8";<br>
 *          .m3>i{overflow:hidden;display:inline-block;font-style:normal;font-family:arial;text-decoration
 *          :inherit;vertical-align:top;} .true1>i:nth-child(1){text-indent:-0.0em;width:0.55em;}
 *          .true2>i:nth-child(1){text-indent:-1.1em;width:1.1em;}
 *          .true3>i:nth-child(2){text-indent:-1.1em;width:1.1em;}
 *          .true3>i:nth-child(3){text-indent:-1.6500000000000001em;width:0.55em;}
 *          .true4>i:nth-child(1){text-indent:-1.1em;width:0.55em;}
 *          .true4>i:nth-child(2){text-indent:-0.55em;width:1.1em;}
 *          .true4>i:nth-child(3){text-indent:-0.55em;width:0.55em;}
 *          .true5>i:nth-child(1){text-indent:-0.55em;width:1.1em;}
 *          .true5>i:nth-child(2){text-indent:-0.55em;width:0.55em;}
 *          .true5>i:nth-child(5){text-indent:-0.55em;width:0.55em;}
 *          .true5>i:nth-child(6){text-indent:-1.1em;width:0.55em;}
 *          .true6>i:nth-child(1){text-indent:-0.0em;width:1.1em;}
 *          .true6>i:nth-child(2){text-indent:-1.1em;width:1.1em;}
 *          .true6>i:nth-child(3){text-indent:-0.55em;width:0.55em;}
 *          .true6>i:nth-child(4){text-indent:-1.6500000000000001em;width:0.55em;}
 *          .true7>i:nth-child(1){text-indent:-0.55em;width:0.55em;}
 *          .true7>i:nth-child(2){text-indent:-0.55em;width:1.1em;}
 *          .true7>i:nth-child(3){text-indent:-0.0em;width:0.55em;}
 *          .true7>i:nth-child(4){text-indent:-0.55em;width:1.1em;}
 *          .true7>i:nth-child(5){text-indent:-0.0em;width:0.55em;}
 *          .true8>i:nth-child(1){text-indent:-1.1em;width:1.1em;}
 *          .true8>i:nth-child(2){text-indent:-0.55em;width:0.55em;}
 *          .true8>i:nth-child(3){text-indent:-0.0em;width:1.1em;}
 *          .true8>i:nth-child(4){text-indent:-1.1em;width:0.55em;}
 *          .true8>i:nth-child(6){text-indent:-0.0em;width:1.1em;}
 *          .true9>i:nth-child(1){text-indent:-0.0em;width:1.1em;}
 *          .true9>i:nth-child(2){text-indent:-0.55em;width:1.1em;}
 *          .true9>i:nth-child(3){text-indent:-0.55em;width:1.1em;}
 *          .true9>i:nth-child(4){text-indent:-0.0em;width:1.1em;}
 *          .true9>i:nth-child(5){text-indent:-1.1em;width:0.55em;}
 *          .true10>i:nth-child(1){text-indent:-0.0em;width:0.55em;}
 *          .true10>i:nth-child(2){text-indent:-0.0em;width:0.55em;}
 *          .true10>i:nth-child(4){text-indent:-0.55em;width:0.55em;}
 *          .true10>i:nth-child(5){text-indent:-1.6500000000000001em;width:0.55em;}
 *          .true10>i:nth-child(6){text-indent:-1.1em;width:1.1em;}
 *          .true10>i:nth-child(7){text-indent:-1.1em;width:0.55em;}
 *          .true10>i:nth-child(8){text-indent:-1.6500000000000001em;width:0.55em;}
 *          .true10>i:nth-child(9){text-indent:-1.1em;width:1.1em;}
 */
public class PriceCalculator
{
    private static PriceCalculator instance = new PriceCalculator();
    public static final String PRICE_REX = "(?<=(>))[(0-9)|\\.]{1,}(?=(<))";

    class Factor
    {
        int begin = 0;
        int end = 0;

        Factor(int begin, int sublength)
        {
            this.begin = begin;
            this.end = this.begin + sublength;
        }

        public int getBegin()
        {
            return begin;
        }

        public int getEnd()
        {
            return end;
        }

    }

    Map<String, HashMap<Integer, Factor>> factorMap = new HashMap<String, HashMap<Integer, Factor>>();
    HashMap<Integer, Factor> tempMap = new HashMap<Integer, Factor>();

    public PriceCalculator()
    {
        tempMap.put(1, new Factor(0, 1));
        factorMap.put("true1", tempMap);
        tempMap = new HashMap<Integer, Factor>();
        tempMap.put(1, new Factor(2, 2));
        factorMap.put("true2", tempMap);
        tempMap = new HashMap<Integer, Factor>();
        tempMap.put(2, new Factor(2, 2));
        tempMap.put(3, new Factor(3, 1));
        factorMap.put("true3", tempMap);
        tempMap = new HashMap<Integer, Factor>();
        tempMap.put(1, new Factor(2, 1));
        tempMap.put(2, new Factor(1, 2));
        tempMap.put(3, new Factor(1, 1));
        factorMap.put("true4", tempMap);
        tempMap = new HashMap<Integer, Factor>();
        tempMap.put(1, new Factor(1, 2));
        tempMap.put(2, new Factor(1, 1));
        tempMap.put(5, new Factor(1, 1));
        tempMap.put(6, new Factor(2, 1));
        factorMap.put("true5", tempMap);
        tempMap = new HashMap<Integer, Factor>();
        tempMap.put(1, new Factor(0, 2));
        tempMap.put(2, new Factor(2, 2));
        tempMap.put(3, new Factor(1, 1));
        tempMap.put(4, new Factor(3, 1));
        factorMap.put("true6", tempMap);
        tempMap = new HashMap<Integer, Factor>();
        tempMap.put(1, new Factor(1, 1));
        tempMap.put(2, new Factor(1, 2));
        tempMap.put(3, new Factor(0, 1));
        tempMap.put(4, new Factor(1, 2));
        tempMap.put(5, new Factor(0, 1));
        factorMap.put("true7", tempMap);
        tempMap = new HashMap<Integer, Factor>();
        tempMap.put(1, new Factor(2, 2));
        tempMap.put(2, new Factor(1, 1));
        tempMap.put(3, new Factor(0, 2));
        tempMap.put(4, new Factor(2, 1));
        tempMap.put(6, new Factor(0, 2));
        factorMap.put("true8", tempMap);
        tempMap = new HashMap<Integer, Factor>();
        tempMap.put(1, new Factor(0, 2));
        tempMap.put(2, new Factor(1, 2));
        tempMap.put(3, new Factor(1, 2));
        tempMap.put(4, new Factor(0, 2));
        tempMap.put(5, new Factor(2, 1));
        factorMap.put("true9", tempMap);
        tempMap = new HashMap<Integer, Factor>();
        tempMap.put(1, new Factor(0, 1));
        tempMap.put(2, new Factor(0, 1));
        tempMap.put(4, new Factor(1, 1));
        tempMap.put(5, new Factor(3, 1));
        tempMap.put(6, new Factor(2, 2));
        tempMap.put(7, new Factor(2, 1));
        tempMap.put(8, new Factor(3, 1));
        tempMap.put(9, new Factor(2, 2));
        factorMap.put("true10", tempMap);
    }

    public static PriceCalculator getInstance()
    {
        return instance;
    }

    public String getPrice(String oriPrice)
    {
        String price = "";
        Pattern p = Pattern.compile(PRICE_REX);
        Matcher matcher = p.matcher(oriPrice);
        String trueS = oriPrice.substring(oriPrice.indexOf("true"), oriPrice.indexOf("true") + 5);
        int index = 0;
        String tempS = "";
        while (matcher.find())
        {
            index++;
            tempS = matcher.group();
            try
            {
                price = price
                        + tempS.substring(instance.factorMap.get(trueS).get(index).getBegin(),
                                instance.factorMap.get(trueS).get(index).getEnd());
            } catch (NullPointerException e)
            {
                continue;
            }
        }
        return price;
    }
}
