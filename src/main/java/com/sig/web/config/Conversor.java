package com.sig.web.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class Conversor implements Converter<String, String>{

	@Override
	public String convert(String source) {
		source = source.trim();
		if(source.length()>0) {
			source = source.replace(".0","").replace(",", ".");
			return source;
		}
		return "0";
	}
	
  public static String dateFormat(String dados){
        String newDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", new Locale(getCountry()));
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dados);
            newDate = dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            newDate = dados;
        }
        return newDate;
    }

    public static String getCountry(){
        Locale locale = Locale.getDefault();
        String country = String.valueOf(locale.getCountry());
        return country.toLowerCase();
    }

}
