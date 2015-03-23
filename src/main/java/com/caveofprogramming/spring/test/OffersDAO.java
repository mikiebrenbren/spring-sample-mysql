package com.caveofprogramming.spring.test;

import com.caveofprogramming.spring.test.model.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by michael.brennan on 3/23/15.
 */
@Component("offersdao")
public class OffersDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource jdbcTemplate){
        this.jdbcTemplate = new JdbcTemplate(jdbcTemplate);
    }

//    This returns a list of offers
    public List<Offer> getOffers(){
        //I think that this rowmapper creates adds an offer to the list ie it adds a row to the list
        //for every row that is in the query
        return jdbcTemplate.query("select * from offers", new RowMapper<Offer>() {
            @Override
            //maps a jdbc result set to a single java object
            public Offer mapRow(ResultSet resultSet, int i) throws SQLException {
                Offer offer = new Offer();
                offer.setId(resultSet.getInt("id"));
                offer.setName(resultSet.getString("name"));
                offer.setEmail(resultSet.getString("email"));
                offer.setText(resultSet.getString("text"));
                return offer;
            }
        });
    }
}
