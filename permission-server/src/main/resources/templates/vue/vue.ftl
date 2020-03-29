<template>
    <div style="height: 100%;width: 100%;">
        <el-dialog :title="isAdd?'新增':'编辑'"  :visible.sync="showDialog" width="600px">
            <el-form status-icon  :model="form" :inline="true" ref="form" :rules="rule" style="font-weight: bold;" size="small" label-position="right" label-width="80px" >
                <#list table.fields as field>
                    <el-form-item prop="${field.propertyName}" label="${field.comment}">
                        <el-input v-model="form.${field.propertyName}" size="mini" placeholder="${field.comment}"/>
                    </el-form-item>
                </#list>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button size="mini" @click="showDialog = false">取 消</el-button>
                <el-button size="mini" type="primary" @click="submitForm()" :loading="loadingSubmit">{{isAdd?'新增':'更新'}}</el-button>
            </div>
        </el-dialog>
        <div class="header">
            <el-form :inline="true">
                <el-form-item>
                    <el-select multiple filterable allow-create size="mini" v-model="params.sort"
                               placeholder="排序字段">
                        <#list table.fields as field>
                        <el-option  label="${field.comment}" value="${field.propertyName}"></el-option>
                        </#list>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-select size="mini" v-model="params.asc" placeholder="排序方式" style="width: 80px">
                        <el-option label="正序" :value="true"/>
                        <el-option label="逆序" :value="false"/>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-input v-model="params.text" prefix-icon="el-icon-search" size="mini"
                              @keyup.enter.native="queryPage()" placeholder="请输入查询内容"/>
                </el-form-item>
                <el-form-item>
                    <el-button size="mini" type="success" @click="queryPage()" icon="el-icon-search">查询</el-button>
                    <el-button size="mini" type="primary" @click="showAddDialog({})" icon="el-icon-plus" v-if="havePermission('add')">新增</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div class="center">
            <span class="divider-line"/>
            <!--表格-->
            <el-table v-loading="loadingTable" :data="list" highlight-current-row element-loading-text="加载中..." border fit>
                <#list table.fields as field>
                <el-table-column align="center" label="${field.comment}" prop="${field.propertyName}"></el-table-column>
                </#list>
                <el-table-column align="center" label="操作"  min-width="120" fixed="right"  v-if="haveAnyPermission(['edit','del'])">
                    <template slot-scope="scope">
                        <el-button @click="showEditDialog(scope.row.id)" size="mini" type="success" icon="el-icon-edit" :loading="loadingEdit"  v-if="havePermission('edit')" />
                        <el-button @click="removeOne(scope.row.id)" size="mini" type="danger" icon="el-icon-delete" v-if="havePermission('del')"/>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="footer" style="margin-top: 5px;">
            <el-pagination :current-page="params.page" :page-size="params.size" @current-change="queryPage" layout="total,prev, pager, next" :total="total" small background/>
        </div>
    </div>
</template>

<script>
    import request from '../../api/${entity?lower_case}'
    import common from "../../mixins/common";

    export default {
        name: "admin",
        mixins: [common],
        data() {
            return {
                rule:{
                    <#list table.fields as field>
                    ${field.propertyName}:[{required:true,message:"${field.comment}必须填写",trigger:'blur'}],
                    </#list>
                },
                request: request,
            }
        },
        methods: {},
        created() {
        }
    }
</script>

<style>

</style>
