package pers.sdulxt.inspect.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.sdulxt.inspect.entity.AccountEntity;
import pers.sdulxt.inspect.mapper.AccountMapper;
import pers.sdulxt.inspect.model.Constant;
import pers.sdulxt.inspect.model.Token;
import pers.sdulxt.inspect.util.MD5Utils;

import java.util.*;

@Service
public class TokenService {
    private final AccountMapper accountMapper;
    private Log log;
    private Map<String, Token> tokens;

    @Autowired
    public TokenService(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
        tokens = new HashMap<>();
        if(Constant.DEBUG){
            log = LogFactory.getLog(getClass().getName());
        }
    }

    /**
     * Perform login action.
     * @param phoneNumber Phone number.
     * @param password Password.
     * @return The generated token, null if login failed.
     */
    public String login(String phoneNumber, String password){
        // return if login failed.
        if(!checkAccount(phoneNumber, password)) return null;

        // generate token.
        String tokenString = generateToken(phoneNumber, password);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, Constant.TOKEN_EXPIRE);
        Token token = new Token(tokenString, calendar.getTime());
        // put token into map.
        tokens.put(phoneNumber, token);

        log.info("Login: " + phoneNumber + "|" + tokenString);
        return tokenString;
    }

    /**
     * Check if a token is valid.
     * @param phoneNumber Phone number.
     * @param tokenString Token.
     * @return True if the token is valid. False otherwise.
     */
    public boolean checkValidity(String phoneNumber, String tokenString){
        if(Constant.DEBUG){
            if(phoneNumber.equals("0") && tokenString.equals("0")){
                log.info("Use super TOKEN!");
                return true;
            }
        }
        Token token = tokens.get(phoneNumber);
        if(token != null && token.getToken().equals(tokenString) && token.getExpire().after(new Date())){
            return true;
        }else{
            tokens.remove(phoneNumber);
            return false;
        }
    }

    /**
     * Check if the phoneNumber and password are correct.
     * @param phoneNumber Phone number.
     * @param password Password.
     * @return True if success. False otherwise.
     */
    private boolean checkAccount(String phoneNumber, String password){
        AccountEntity user = accountMapper.getAccountByPhoneNumber(phoneNumber);
        return user != null && user.getPassword().equals(password);
    }

    /**
     * Generate a token for the user.
     * @param phoneNumber Phone number.
     * @param password Password.
     * @return Token.
     */
    private String generateToken(String phoneNumber, String password){
        Random random = new Random();
        long randomValue = random.nextLong();
        String rawToken = phoneNumber + "/" + password + "/" + randomValue;
        return MD5Utils.getMD5(rawToken);
    }
}
