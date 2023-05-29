package rsb.javareloaded;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/4/27:12:13
 * @since 2022.04.0
 */
public class BaseCustomerService implements CustomerService {

    private final RowMapper<Customer> rowMapper = (rs,i) -> new Customer(rs.getLong("id"),rs.getString("NAME"));

    private final JdbcTemplate jdbcTemplate;

    public BaseCustomerService(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Collection<Customer> save(String... names) {
        List<Customer> customerList = new ArrayList<>();

        for(String name : names) {
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            this.jdbcTemplate.update((connection) -> {
                PreparedStatement ps = connection.prepareStatement("insert into CUSTOMERS(name) values (?)"
                        , Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,name);
                return ps;
            },keyHolder);
            Long keyHolderKey = Objects.requireNonNull(keyHolder.getKey().longValue());
            Customer customer = this.findById(keyHolderKey);
            Assert.notNull(name,"the name given must not be null!");
            customerList.add(customer);
        }
        return customerList;
    }

    @Override
    public Customer findById(Long id) {
        String sql = "select * from CUSTOMERS where id = ?";
        return this.jdbcTemplate.queryForObject(sql,rowMapper,id);
    }

    @Override
    public Collection<Customer> findAll() {
        return this.jdbcTemplate.query("select * from CUSTOMERS",rowMapper);
    }
}
