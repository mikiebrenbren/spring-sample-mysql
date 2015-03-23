package com.caveofprogramming.spring.test;

import com.caveofprogramming.spring.test.model.Offer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;

import java.util.List;

public class App {

	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        OffersDAO offersDAO = (OffersDAO) context.getBean("offersdao");

        try{
            List<Offer> offers = offersDAO.getOffers();

            for(Offer offer : offers){
                System.out.println(offer);
            }
        }catch(DataAccessException e){
            System.out.println(e.getMessage());
        }

        Offer offer = offersDAO.getOffer(2);
        System.out.println(offer);

        boolean offer1 = offersDAO.delete(4);
        System.out.println(offer);


                ((ClassPathXmlApplicationContext) context).close();
	}

}
