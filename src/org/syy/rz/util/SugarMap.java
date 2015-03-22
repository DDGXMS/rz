package org.syy.rz.util;

import java.util.HashMap;

/** 
 * Map语法糖
 *
 * @author syy
 * @date 2014-11-21 上午12:40:59 
 * @version 1.0
 */
public class SugarMap extends HashMap<Object, Object>{

    private static final long serialVersionUID = -5000518916410735302L;

    public SugarMap(Object...objects) {
        super(objects.length/2);
        for (int i=1; i<objects.length; i=i+2) {
            this.put(objects[i-1], objects[i]);
        }
    }
    
    public static HashMap<String, String> stringMap(String...strings) {
        HashMap<String, String> params = new HashMap<String, String>();
        for (int i=1; i<strings.length; i=i+2) {
            params.put(strings[i-1], strings[i]);
        }
        
        return params;
    }
}
