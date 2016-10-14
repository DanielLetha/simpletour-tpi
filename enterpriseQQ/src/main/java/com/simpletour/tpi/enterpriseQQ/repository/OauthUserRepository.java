package com.tmy.repository;

import com.simpletour.tpi.enterpriseQQ.entity.OAuthUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OauthUserRepository extends JpaRepository<OAuthUser, Integer> {

    OAuthUser findByOAuthTypeAndOAuthId(String oAuthType, String oAuthId);

}
