1. clone下来部署时

   ```
   org.apache.ibatis.binding.BindingException: Invalid bound statement (not found): com.egg.manager.persistence.db.mysql.mapper.define.DefinePermissionMapper.findAllPermissionByUserAcccountId
   类似这样的话，原因之一是因为没有在 egg-persistence 中将 resources 标记为  [Resources Root] ，标记后重启项目可解决。
   ```