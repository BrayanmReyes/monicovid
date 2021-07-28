package pe.akiramenai.project.unasam.spring.util;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class  mathResources{
	    public double sum (List<Double> a){
	        if (a.size() > 0) {
	        	double sum = 0;
	 
	            for (double i : a) {
	                sum += i;
	            }
	            return sum;
	        }
	        return 0;
	    }
	    public double mean (List<Double> a){
	        double sum = sum(a);
	        double mean = 0;
	        mean = sum / (a.size() * 1.0);
	        return mean;
	    }
	    public double median (List<Double> a){
	        int middle = a.size()/2;
	 
	        if (a.size() % 2 == 1) {
	            return a.get(middle);
	        } else {
	           return (a.get(middle-1) + a.get(middle)) / 2.0;
	        }
	    }
	    public double sd (List<Double> a){
	        double sum = 0;
	        double mean = mean(a);
	 
	        for (Double i : a)
	            sum += Math.pow((i - mean), 2);
	        return Math.sqrt( sum / ( a.size() - 1 ) ); // sample
	    }
	
}
