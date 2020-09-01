package com.egg.manager.persistence.pojo.mapstruct.mysql.message.mail;

import com.egg.manager.persistence.pojo.message.mail.MyEmailMsgO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.mail.SimpleMailMessage;

/**
 * @Description:
 * @ClassName: MessageMailMapstruct
 * @Author: zhoucj
 * @Date: 2020/8/7 9:56
 */
@Mapper(componentModel = "spring")
public interface MessageMailMapstruct {
    MessageMailMapstruct INSTANCE = Mappers.getMapper(MessageMailMapstruct.class);

    /**
     * 复制MyEmailMsgO 到 SimpleMailMessage
     *
     * @param emailMsgO
     * @return
     */
    @Mappings({
            @Mapping(source = "receiveEmails", target = "to"),
            @Mapping(source = "fromUser", target = "from"),
            @Mapping(source = "content", target = "text")
    })
    SimpleMailMessage myEmailMsgO_CopyTo_SimpleMailMessage(MyEmailMsgO emailMsgO);


}
