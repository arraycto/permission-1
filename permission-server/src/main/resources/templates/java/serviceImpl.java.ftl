package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import ${package.Mapper}.${table.mapperName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unclezs.permission.common.response.ResponseResult;
import com.unclezs.permission.common.response.Result;
import com.unclezs.permission.module.system.model.dto.BaseDto;
import com.unclezs.permission.common.util.SqlUtil;

/**
 * <p>
 * ${table.comment!} 
 * </p>
 *
 * @author ${author}
 * @date ${date}
 */
@Service
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    /**
    * 分页查询
    *
    * @param page   /
    * @param params /
    * @return /
    */
    @Override
    public Result list(Page<${entity}> page, BaseDto params) {
       Page<${entity}> list = SqlUtil
       .likeAll(${entity}.class, query(), params.getText())
       .orderBy(params.getSort().size() > 0, params.isAsc(), params.getSort().stream().map(StrUtil::toUnderlineCase).toArray(String[]::new))
       .page(page);
       return ResponseResult.okList(list);
    }

    /**
    * 编辑
    *
    * @param  ${entity?lower_case} /
    * @return /
    */
    @Override
    public Result edit(${entity}  ${entity?lower_case}) {
       ${entity} old${entity} = super.getById(${entity?lower_case}.getId());
       BeanUtil.copyProperties(${entity?lower_case}, old${entity}, CopyOptions.create().ignoreNullValue());
       super.updateById(old${entity});
       return ResponseResult.ok("更新${table.comment!}成功");
    }

    /**
    * 添加
    *
    * @param ${entity?lower_case} /
    * @return /
    */
    @Override
    public Result add(${entity} ${entity?lower_case}) {
        super.save(${entity?lower_case});
        return ResponseResult.ok("添加${table.comment!}成功");
    }

    /**
    * 删除
    *
    * @param id /
    * @return /
    */
    @Override
    public Result del(String id) {
        super.removeById(id);
        return ResponseResult.ok("删除${table.comment!}成功");
    }

    /**
    * 根据ID查询一个
    *
    * @param id /
    * @return /
    */
    @Override
    public Result getOne(String id) {
        return ResponseResult.ok(super.getById(id));
    }
}

