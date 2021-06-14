package services.security;

import domain.Administrator;
import domain.Client;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import repository.AdministratorRepository;
import repository.ClientRepository;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final ClientRepository clientRepository = new ClientRepository();
    private final AdministratorRepository administratorRepository = new AdministratorRepository();

    public MyUserDetailsService() {
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findOne(username);
        Administrator administrator = administratorRepository.findOne(username);

        if (client != null)
            return new User(client.getNumeUtilizator(), client.getParola(), new ArrayList<>());
        else
            return new User(administrator.getNumeUtilizator(), administrator.getParola(), new ArrayList<>());
    }

    public UserDetails loadUserByEmail(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findOne(username);
        Administrator administrator = administratorRepository.findOne(username);

        if (client != null)
            return new User(client.getNumeUtilizator(), client.getEmail(), new ArrayList<>());
        else
            return new User(administrator.getNumeUtilizator(), administrator.getEmail(), new ArrayList<>());
    }
}