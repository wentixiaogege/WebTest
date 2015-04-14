package factory;

import source.DeltaModel;
import source.Model;
import source.WyleModel;

public class MakeFactory {
	public static <T> Model makeModel(String name){
		WyleModel wyleModel = new WyleModel();
		DeltaModel deltaModel = new DeltaModel();
		if("wyleModel" == name)
		{
			return wyleModel;
		}
		else if("deltaModel" == name){
			
			return deltaModel;
		}
		else{
			return null;
		}
	}
}
