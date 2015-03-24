package com.caveofprogramming.spring.test;

import com.caveofprogramming.spring.test.model.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

    private NamedParameterJdbcTemplate jdbcTemplate;
    private MapSqlParameterSource mapSqlParameterSource;

    /*
    I think that this autowires the DataSource into the method
     */
    @Autowired
    public void setDataSource(DataSource jdbcTemplate){
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

//    This returns a list of offers
    public List<Offer> getOffers(){

         mapSqlParameterSource = new MapSqlParameterSource();

        //I think that this rowmapper creates adds an offer to the list ie it adds a row to the list
        //for every row that is in the query
        return jdbcTemplate.query("select * from offers", mapSqlParameterSource, new RowMapper<Offer>() {
            //maps a jdbc result set to a single java object
            @Override
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



    public Offer getOffer(int id){

        mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);

        //I think that this rowmapper creates adds an offer to the list ie it adds a row to the list
        //for every row that is in the query, you can query for multiple things
        return jdbcTemplate.queryForObject("select * from offers where id = :id", mapSqlParameterSource, new RowMapper<Offer>() {
            //maps a jdbc result set to a single java object
            @Override
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

    /* ROWMAPPER CLASS
    An interface used by JdbcTemplate for mapping rows of a ResultSet on a per-row basis. Implementations of this interface perform the actual work of mapping each row to a
    result object, but don't need to worry about exception handling. SQLExceptions will be caught and handled by the calling JdbcTemplate.

    Typically used either for JdbcTemplate's query methods or for out parameters of stored procedures. RowMapper objects are typically stateless and thus reusable;
    they are an ideal choice for implementing row-mapping logic in a single place.

    Alternatively, consider subclassing MappingSqlQuery from the jdbc.object package: Instead of working with separate JdbcTemplate and RowMapper objects, you can build
    executable query objects (containing row-mapping logic) in that style.
     */

    public boolean delete(int id){
        mapSqlParameterSource = new MapSqlParameterSource("id", id);
        //mind the spaces in the sql statements
       return jdbcTemplate.update("delete from offers where id =:id", mapSqlParameterSource) == 1;
    }


    //creating a row in the database
    public boolean create(Offer offer){
        BeanPropertySqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(offer);
        return jdbcTemplate.update("insert into offers (name, text, email) values (:name, :text, :email)", beanPropertySqlParameterSource) == 1;

    }

    //creating a row in the database
    public boolean update(Offer offer){
        BeanPropertySqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(offer);
        return jdbcTemplate.update("update offers set name=:name, text=:text, email=:email where id=:id", beanPropertySqlParameterSource) == 1;

    }
}
