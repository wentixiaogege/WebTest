package util;

import util.BaseElement;

public class BaseElement implements Cloneable{
	public String config;
	public float value;


    public Object clone() {  
    	BaseElement copy = null;  
        
        try {  
        	copy = (BaseElement) super.clone();  
        } catch (CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return copy;  
    }  
}
