package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};

import com.unclezs.permission.common.response.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unclezs.permission.module.system.model.dto.BaseDto;

/**
 * ${table.comment!}
 *
 * @author ${author}
 * @date ${date}
 */
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    Result list(Page<${entity}> page, BaseDto params);

    Result edit(${entity} ${entity?lower_case});

    Result add(${entity} ${entity?lower_case});

    Result del(String id);

    Result getOne(String id);
}
