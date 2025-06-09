
package com.natanconstrutora.repository;

import com.natanconstrutora.model.User;
import com.natanconstrutora.model.Regiao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'ROLE_PRESTADOR' AND u.regiao = :regiao")
    List<User> findPrestadoresByRegiao(@Param("regiao") Regiao regiao);
}
package com.natanconstrutora.repository;

import com.natanconstrutora.model.User;
import com.natanconstrutora.model.Regiao;
import com.natanconstrutora.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
    List<User> findByRoleName(@Param("roleName") RoleName roleName);
    
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName AND u.regiao = :regiao")
    List<User> findPrestadoresByRegiao(@Param("roleName") RoleName roleName, @Param("regiao") Regiao regiao);
    
    List<User> findByRegiao(Regiao regiao);
}
