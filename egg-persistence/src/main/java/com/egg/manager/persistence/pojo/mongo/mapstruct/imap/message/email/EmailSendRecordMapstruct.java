package com.egg.manager.persistence.pojo.mongo.mapstruct.imap.message.email;

import com.egg.manager.persistence.pojo.mongo.mapstruct.conversion.message.email.EmailSendRecordConversion;
import com.egg.manager.persistence.pojo.mongo.mvo.message.email.EmailSendRecordMVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.mail.SimpleMailMessage;

/**
 * @Description:
 * @ClassName: EmailSendRecordMapstruct
 * @Author: zhoucj
 * @Date: 2020/8/7 9:56
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = EmailSendRecordConversion.class
)
public interface EmailSendRecordMapstruct {
    EmailSendRecordMapstruct INSTANCE = Mappers.getMapper(EmailSendRecordMapstruct.class);










    /**
     * 复制-EmailSendRecordMVO->SimpleMailMessage
     * @param emailSendRecordMVO
     * @return
     */
    @Mappings({
            @Mapping(source = "receiveUserInfoList", target = "to",qualifiedByName = "handleGetReceiveUserAddressList"),
            @Mapping(source = "fromUserInfo", target = "from",qualifiedByName = "handleGetFromUserEmailAddress"),
            @Mapping(source = "content", target = "text"),
            @Mapping(target = "replyTo", ignore = true),
            @Mapping(target = "cc", ignore = true),
            @Mapping(target = "bcc", ignore = true)
    })
    SimpleMailMessage emailSendRecordMVO_CopyTo_SimpleMailMessage(EmailSendRecordMVO emailSendRecordMVO);


}
