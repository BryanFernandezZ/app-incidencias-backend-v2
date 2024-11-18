package com.example.app_incidencias_backend_v2.security;

import com.example.app_incidencias_backend_v2.dao.AuthDao;
import com.example.app_incidencias_backend_v2.dto.response.CuentaResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(UserDetailService.class);

    private final AuthDao authDao;

    public UserDetailService(AuthDao authDao) {
        this.authDao = authDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CuentaResponseDto cuentaResponseDto = authDao.buscarCuentaPorNombreUsuario(username).orElse(null);
        if (cuentaResponseDto != null) {
            return new User(cuentaResponseDto.getUsername(), cuentaResponseDto.getPassword(), Collections.emptyList());
        } else {
            throw new UsernameNotFoundException("El usuario no existe");
        }
    }
}
