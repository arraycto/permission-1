package ${package.Controller};


import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unclezs.permission.common.response.Result;
import com.unclezs.permission.module.system.model.dto.BaseDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import lombok.AllArgsConstructor;

/**
* ${table.comment!}
*
* @author ${author}
* @date ${date}
*/
@RestController
@RequestMapping("/${entity?lower_case}")
@Api(tags = "${table.comment!} ")
@AllArgsConstructor
public class ${table.controllerName} {
    private ${entity}Service ${entity?lower_case}Service;

    @PostMapping("query/list/{page}/{size}")
    @ApiOperation("分页查询")
    public Result page${entity}(@PathVariable Integer page, @PathVariable Integer size, @RequestBody BaseDto params) {
        return ${entity?lower_case}Service.list(new Page<>(page, size), params);
    }

    @PutMapping("edit")
    @ApiOperation("编辑${table.comment!}")
    @Log("编辑${table.comment!}")
    public Result edit${entity}(@Validated@RequestBody ${entity} ${entity?lower_case}) {
        return ${entity?lower_case}Service.edit(${entity?lower_case});
    }

    @PostMapping("add")
    @ApiOperation("添加")
    @Log("添加${table.comment!}")
    public Result save${entity}(@Validated @RequestBody ${entity} ${entity?lower_case}) {
        return ${entity?lower_case}Service.add(${entity?lower_case});
    }

    @DeleteMapping("del/{id}")
    @ApiOperation("删除")
    @Log("删除${table.comment!}")
    public Result remove${entity}(@PathVariable String id) {
        return ${entity?lower_case}Service.del(id);
    }

    @GetMapping("query/one/{id}")
    @ApiOperation("根据ID查询一个")
    public Result query${entity}ById(@PathVariable String id) {
        return ${entity?lower_case}Service.getOne(id);
    }
}

