package cn.org.faster.cms.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 审计字段自动填充。
 */
@Component
public class AuditMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        strictInsertFill(metaObject, "createDate", LocalDateTime.class, now);
        strictInsertFill(metaObject, "updateDate", LocalDateTime.class, now);
        strictInsertFill(metaObject, "sort", Integer.class, 0);
        Long operator = CurrentUser.id();
        if (operator != null) {
            strictInsertFill(metaObject, "createBy", Long.class, operator);
            strictInsertFill(metaObject, "updateBy", Long.class, operator);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        strictUpdateFill(metaObject, "updateDate", LocalDateTime.class, LocalDateTime.now());
        Long operator = CurrentUser.id();
        if (operator != null) {
            strictUpdateFill(metaObject, "updateBy", Long.class, operator);
        }
    }
}
