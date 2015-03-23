package com.caveofprogramming.spring.test;

import com.caveofprogramming.spring.test.model.Offer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class App {

	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        OffersDAO offersDAO = (OffersDAO) context.getBean("offersdao");

        List<Offer> offers = offersDAO.getOffers();

        for(Offer offer : offers){
            System.out.println(offer);
        }

                ((ClassPathXmlApplicationContext) context).close();
	}

}
