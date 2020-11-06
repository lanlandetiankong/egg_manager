package com.egg.manager.persistence.em.message.pojo.mapstruct.imap.email;

import com.egg.manager.persistence.em.message.db.mongo.mo.email.EmailSendRecordMgo;
import com.egg.manager.persistence.em.message.pojo.mapstruct.conversion.email.EmailSendRecordConversion;
import com.egg.manager.persistence.exchange.pojo.mongo.mapstruct.imap.fundamental.MyBaseMongoMapstruct;
import com.egg.manager.persistence.em.message.pojo.mvo.email.EmailSendRecordMgvo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/21
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = EmailSendRecordConversion.class
)
public interface EmailSendRecordMapstruct extends MyBaseMongoMapstruct<EmailSendRecordMgo, EmailSendRecordMgvo> {
    EmailSendRecordMapstruct INSTANCE = Mappers.getMapper(EmailSendRecordMapstruct.class);

    /**
     * mgvo转mgo
     * @param mgvo
     * @return
     */
    @Mappings({})
    EmailSendRecordMgo translateMgvoToMgo(EmailSendRecordMgvo mgvo);


    /**
     * 复制-EmailSendRecordMgvo->SimpleMailMessage
     * @param emailSendRecordMgvo
     * @return
     */
    @Mappings({
            @Mapping(source = "receiveUserInfoList", target = "to", qualifiedByName = "handleGetReceiveUserAddressList"),
            @Mapping(source = "fromUserInfo", target = "from", qualifiedByName = "handleGetFromUserEmailAddress"),
            @Mapping(source = "content", target = "text"),
            @Mapping(target = "replyTo", ignore = true),
            @Mapping(target = "cc", ignore = true),
            @Mapping(target = "bcc", ignore = true)
    })
    SimpleMailMessage emailSendRecordMVO_CopyTo_SimpleMailMessage(EmailSendRecordMgvo emailSendRecordMgvo);


}
