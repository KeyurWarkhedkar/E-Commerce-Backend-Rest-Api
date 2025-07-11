package com.keyur.E_Commerce.Security;

import com.keyur.E_Commerce.Entities.Customer;
import com.keyur.E_Commerce.Entities.Seller;
import com.keyur.E_Commerce.Repositories.CustomerDao;
import com.keyur.E_Commerce.Repositories.SellerDao;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    //fields
    CustomerDao customerDao;
    SellerDao sellerDao;

    //injecting dependency
    CustomUserDetailsService(CustomerDao customerDao, SellerDao sellerDao) {
        this.customerDao = customerDao;
        this.sellerDao = sellerDao;
    }

    //methods
    @Override
    public UserDetails loadUserByUsername(String mobileNo) throws UsernameNotFoundException {
        Optional<Customer> customer = customerDao.findByMobileNo(mobileNo);
        if (customer.isPresent()) {
            return new org.springframework.security.core.userdetails.User(
                    customer.get().getMobileNo(),
                    customer.get().getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"))
            );
        }

        Optional<Seller> seller = sellerDao.findByMobile(mobileNo);
        if (seller.isPresent()) {
            return new org.springframework.security.core.userdetails.User(
                    seller.get().getMobile(),
                    seller.get().getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_SELLER"))
            );
        }

        throw new UsernameNotFoundException("No user found with mobile number: " + mobileNo);
    }
}
