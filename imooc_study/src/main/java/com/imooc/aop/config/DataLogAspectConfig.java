package com.imooc.aop.config;

import com.imooc.aop.dao.ActionDAO;
import com.imooc.aop.domain.Action;
import com.imooc.aop.domain.ActionType;
import com.imooc.aop.domain.ChangeItem;
import com.imooc.aop.util.DiffUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 记录产品修改记录属于非功能性需求，适合使用AOP实现
 *
 * 见探秘Spring AOP的44~53.png
 */
@Aspect
@Component
public class DataLogAspectConfig {
    private static final Logger logger = LoggerFactory.getLogger(DataLogAspectConfig.class);

    @Autowired
    ActionDAO actionDAO;

    //save包括新增和更新2种操作
    @Pointcut("execution(public * com.imooc.aop.dao.*.save*(..))")
    public void save() {}

    @Pointcut("execution(public * com.imooc.aop.dao.*.delete*(..))")
    public void delete() {}

    /**
     * 1 判断操作类型，新增/删除/更新
     *  新增/更新 save(ShopProduct)，通过ShopProduct.id字段区分是新增还是更新
     *  删除 delete(id)
     * 2 获取ChangeItem
     *  2.1 新增操作，before获取记录，after记录新增的id
     *  2.2 更新操作，before获取操作前的记录，after获取操作后的记录，然后diff
     *  2.3 删除操作，before根据id获取记录
     * 3 保存操作记录
     *  actionType
     *  objectId
     *  objectClass
     */
    @Around("save() || delete()")
    public Object addDataLog(ProceedingJoinPoint pjp) throws Throwable {
        Object returnObj = null;

        //获取方法名称
        String method = pjp.getSignature().getName();
        ActionType actionType = null;
        Action action = new Action();
        Long id = null;
        Object oldObj = null;

        try {
            /*
                调用目标方法之前
            */
            //判断操作类型
            if ("save".equals(method)) {
                //获取ShopProduct的id字段
                Object obj = pjp.getArgs()[0];
                try {
                    //通过反射获取id
                    id = Long.valueOf(PropertyUtils.getProperty(obj, "id").toString());
                } catch (Exception e) {}

                //通过id区分新增/更新
                if (id == null) {
                    actionType = ActionType.INSERT;
                    List<ChangeItem> changeItems = DiffUtil.getInsertChangeItems(obj);
                    action.getChanges().addAll(changeItems);
                    action.setObjectClass(obj.getClass().getName());
                } else {
                    actionType = ActionType.UPDATE;
                    action.setObjectId(id);
                    oldObj = DiffUtil.getObjectById(pjp.getTarget(), id);
                    action.setObjectClass(oldObj.getClass().getName());
                }
            } else if ("delete".equals(method)) {
                id = Long.valueOf(pjp.getArgs()[0].toString());
                actionType = ActionType.DELETE;
                oldObj = DiffUtil.getObjectById(pjp.getTarget(), id);
                ChangeItem changeItem = DiffUtil.getDeleteChangeItem(oldObj);
                //保存操作记录
                action.getChanges().add(changeItem);
                action.setObjectId(Long.valueOf(pjp.getArgs()[0].toString()));
                action.setObjectClass(oldObj.getClass().getName());
            }

            //调用目标方法，returnObj为目标方法返回值
            returnObj = pjp.proceed(pjp.getArgs());

            /*
                调用目标方法之后
             */
            action.setActionType(actionType);
            if (ActionType.INSERT == actionType) {
                //通过反射获取新增id
                Object newId = PropertyUtils.getProperty(returnObj, "id");
                action.setObjectId(Long.valueOf(newId.toString()));
            } else if (ActionType.UPDATE == actionType) {
                //调用findById查询DB获取更新对象
                Object newObj = DiffUtil.getObjectById(pjp.getTarget(), id);
                List<ChangeItem> changeItems = DiffUtil.getChangeItems(oldObj, newObj);
                action.getChanges().addAll(changeItems);
            }

            //从ThreadLocal/Session动态获取用户名称
            action.setOperator("admin");
            action.setOperateTime(new Date());
            actionDAO.save(action);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }

        return returnObj;
    }
}
