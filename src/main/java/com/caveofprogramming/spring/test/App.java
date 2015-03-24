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

        //getting multiple offers from the database
        try{
            List<Offer> offers = offersDAO.getOffers();

            for(Offer offer : offers){
                System.out.println(offer);
            }
        }catch(DataAccessException e){
            System.out.println(e.getMessage());
        }

        //getting a single offer from the database
        Offer offer = offersDAO.getOffer(2);
        System.out.println(offer);

        //deleting an offer from the database
        boolean offer1 = offersDAO.delete(4);
        System.out.println(offer);

        //creating a new row in the database
        boolean offer2 = offersDAO.create(new Offer("Mike", "mike@joe.com", "I code cool stuff in java"));
        if(offer2) {
            System.out.println("Created an offer with Mike");
        }

        //updating a current row in the database
        Offer offer3 = new Offer(5, "NotMike", "notmike@email.com", "I dont do cool things in java");
        boolean offer4 = offersDAO.update(offer3);
        if(offer4){
            System.out.println("Offers updated");
        }


                ((ClassPathXmlApplicationContext) context).close();
	}

}
