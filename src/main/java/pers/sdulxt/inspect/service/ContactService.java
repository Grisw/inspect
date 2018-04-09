package pers.sdulxt.inspect.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.sdulxt.inspect.mapper.ContactMapper;
import pers.sdulxt.inspect.mapper.UserMapper;
import pers.sdulxt.inspect.model.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContactService {
    private final ContactMapper contactMapper;
    private final UserMapper userMapper;
    private Log log;

    @Autowired
    public ContactService(UserMapper userMapper, ContactMapper contactMapper) {
        this.contactMapper = contactMapper;
        this.userMapper = userMapper;
        if(Constant.DEBUG){
            log = LogFactory.getLog(getClass().getName());
        }
    }

    /**
     * Get contact phone number and name.
     * @param phoneNumber Phone number.
     * @return The Contact phone numbers
     */
    public List<Map<String, String>> getContacts(String phoneNumber){
        List<Map<String, String>> contacts = new ArrayList<>();
        List<String> phones = contactMapper.getContactsByPhoneNumber(phoneNumber);
        for(String phone : phones){
            Map<String, String> contact = new HashMap<>();
            contact.put("phone", phone);
            contact.put("name", userMapper.getUserNameByPhoneNumber(phone));
            contacts.add(contact);
        }
        return contacts;
    }
}
