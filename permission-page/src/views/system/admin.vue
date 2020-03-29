<template>
    <div style="height: 100%;width: 100%;">
        <el-dialog :title="isAdd?'新增':'编辑'" :visible.sync="showDialog" width="600px">
            <el-form :model="form" :inline="true" ref="form" :rules="rule" style="font-weight: bold;" size="small"
                     label-position="right"
                     label-width="80px">
                <el-form-item label="用户名" prop="username" v-if="isAdd">
                    <el-input size="mini" v-model="form.username" autocomplete="off" placeholder="请输入用户名"/>
                </el-form-item>
                <el-form-item label="密码" v-if="isAdd" prop="password">
                    <el-input size="mini" v-model="form.password" autocomplete="off"/>
                </el-form-item>
                <el-form-item label="昵称" prop="nickname">
                    <el-input size="mini" v-model="form.nickname" autocomplete="off"/>
                </el-form-item>
                <el-form-item label="手机号" prop="phone">
                    <el-input size="mini" v-model="form.phone" autocomplete="off"/>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input size="mini" v-model="form.email" autocomplete="off"/>
                </el-form-item>
                <el-form-item label="是否启用" prop="enabled">
                    <el-radio-group size="mini" v-model="form.enabled" fill="#009688">
                        <el-radio-button size="mini" :label="true">启用</el-radio-button>
                        <el-radio-button size="mini" :label="false">禁用</el-radio-button>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="拥有角色" prop="roles">
                    <el-select size="mini" v-model="form.roleIds" placeholder="拥有角色" style="width:450px" multiple
                               filterable allow-create>
                        <el-option :label="item.nameZh" :value="item.id" v-for="item in roles" :key="item.id"/>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button size="mini" @click="showDialog = false">取 消</el-button>
                <el-button size="mini" type="primary" @click="submitForm()" :loading="loadingSubmit">
                    {{isAdd?'新增':'更新'}}
                </el-button>
            </div>
        </el-dialog>
        <div class="header">
            <el-form :inline="true">
                <el-form-item>
                    <el-select multiple filterable allow-create size="mini" v-model="params.sort"
                               placeholder="排序字段">
                        <el-option label="昵称" value="nickname"/>
                        <el-option label="用户名" value="username"/>
                        <el-option label="手机号" value="phone"/>
                        <el-option label="邮箱" value="email"/>
                        <el-option label="是否启用" value="enabled"/>
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
                    <el-button size="mini" type="primary" @click="showAddDialog({enabled:true})" icon="el-icon-plus"
                               v-if="havePermission('add')">新增
                    </el-button>
                </el-form-item>
            </el-form>
        </div>
        <div class="center">
            <span class="divider-line"/>
            <!--表格-->
            <el-table v-loading="loadingTable" :data="list" highlight-current-row element-loading-text="加载中..." border
                      fit>
                <el-table-column align="center" prop="username" label="用户名"/>
                <el-table-column align="center" prop="nickname" label="昵称"/>
                <el-table-column align="center" prop="phone" label="手机号"/>
                <el-table-column align="center" prop="email" label="邮箱"/>
                <el-table-column align="center" label="是否启用">
                    <template slot-scope="scope">
                        <el-switch v-model="scope.row.enabled" disabled/>
                    </template>
                </el-table-column>
                <el-table-column align="center" label="操作" min-width="120" fixed="right"
                                 v-if="haveAnyPermission(['edit','del'])">
                    <template slot-scope="scope">
                        <el-button @click="showEditDialog(scope.row.id)" size="mini" type="success"
                                   v-if="havePermission('edit')&&scope.row.id!=='1'"
                                   icon="el-icon-edit" :loading="loadingEdit"/>
                        <el-button @click="removeOne(scope.row.id)" size="mini" type="danger" icon="el-icon-delete"
                                   v-if="havePermission('del')&&scope.row.id!=='1'"/>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="footer" style="margin-top: 5px;">
            <el-pagination :current-page="params.page" :page-size="params.size" @current-change="queryPage"
                           layout="total,prev, pager, next" :total="total" small background/>
        </div>
    </div>
</template>

<script>
    import request from '../../api/admin'
    import roleApi from '../../api/role'
    import common from "../../mixins/common";

    export default {
        name: "admin",
        mixins: [common],
        data() {
            return {
                roles: [],
                rule: {
                    username: [{required: true, message: "用户名必须填写", trigger: 'blur'}],
                    password: [{required: true, message: '密码必须填写', trigger: 'blur'}],
                    nickname: [{required: true, message: '请填写昵称', trigger: 'blur'}],
                    phone: [{required: true, message: '请填写手机号', trigger: 'blur'}],
                    email: [{required: true, message: '请填写邮箱', trigger: 'blur'}],
                },
                request: request,
            }
        },
        methods: {
            loadRoles() {
                roleApi.page({page: 1, size: 1000}).then(res => {
                    this.roles = res.data.list;
                })
            }
        },
        created() {
            this.loadRoles();
        }
    }
</script>

<style>
</style>
