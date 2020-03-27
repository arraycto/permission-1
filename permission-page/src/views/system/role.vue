<template>
    <div style="height: 100%;width: 100%;">

        <el-dialog :title="isAdd?'新增':'编辑'" :visible.sync="showDialog" width="600px">
            <el-form :model="form" :inline="true" ref="form" :rules="rule" style="font-weight: bold;" size="small"
                     label-position="right" label-width="80px">
                <el-form-item prop="name" label="角色名称">
                    <el-input v-model="form.name" size="mini" placeholder="角色名称"/>
                </el-form-item>
                <el-form-item prop="nameZh" label="中文名称">
                    <el-input v-model="form.nameZh" size="mini" placeholder="中文名称"/>
                </el-form-item>
                <el-form-item prop="introduce" label="角色介绍">
                    <el-input v-model="form.introduce" size="mini" placeholder="角色介绍"/>
                </el-form-item>
                <el-form-item label="启用" prop="enabled">
                    <el-radio-group size="mini" v-model="form.enabled" fill="#009688">
                        <el-radio-button size="mini" :label="true">启用</el-radio-button>
                        <el-radio-button size="mini" :label="false">禁用</el-radio-button>
                    </el-radio-group>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button size="mini" @click="showDialog = false">取 消</el-button>
                <el-button size="mini" type="primary" @click="submitForm()" :loading="loadingSubmit">
                    {{isAdd?'新增':'更新'}}
                </el-button>
            </div>
        </el-dialog>
        <el-dialog title="角色授权" :visible.sync="menuDialog" width="250px">
            <el-tree
                    accordion
                    ref="menu"
                    :data="menuTree"
                    :default-checked-keys="roleMenus"
                    show-checkbox
                    node-key="id">
            </el-tree>
            <div slot="footer" class="dialog-footer">
                <el-button size="mini" @click="menuDialog = false">取 消</el-button>
                <el-button size="mini" type="primary" @click="updateRoleMenus()" :loading="loadingSubmit">提交
                </el-button>
            </div>
        </el-dialog>
        <div class="header">
            <el-form :inline="true">
                <el-form-item>
                    <el-select multiple filterable allow-create size="mini" v-model="params.sort"
                               placeholder="排序字段">
                        <el-option label="角色名称" value="name"></el-option>
                        <el-option label="角色介绍" value="introduce"></el-option>
                        <el-option label="创建时间" value="createTime"></el-option>
                        <el-option label="是否启用" value="enabled"></el-option>
                        <el-option label="中文名称" value="nameZh"></el-option>
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
                <el-table-column align="center" label="角色名称" prop="name"></el-table-column>
                <el-table-column align="center" label="中文名称" prop="nameZh"></el-table-column>
                <el-table-column align="center" label="角色介绍" prop="introduce"></el-table-column>
                <el-table-column align="center" label="创建时间" prop="createTime"></el-table-column>
                <el-table-column align="center" label="是否启用">
                    <template slot-scope="scope">
                        <el-switch v-model="scope.row.enabled" disabled/>
                    </template>
                </el-table-column>
                <el-table-column align="center" label="操作" min-width="120" fixed="right"
                                 v-if="haveAnyPermission(['edit','del'])">
                    <template slot-scope="scope">
                        <el-button @click="showEditDialog(scope.row.id)" size="mini" type="success" icon="el-icon-edit"
                                   :loading="loadingEdit" v-if="havePermission('edit')" />
                        <el-button @click="showMenuDialog(scope.row.id)" size="mini" type="warning"
                                   icon="el-icon-user-solid"
                                   :loading="loadingEdit" v-if="havePermission('edit')"/>
                        <el-button @click="removeOne(scope.row.id)" size="mini" type="danger" icon="el-icon-delete"
                                   v-if="havePermission('del')&&scope.row.id!=='1'"/>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="footer" style="margin-top: 5px;">
            <el-pagination :current-page="params.page" :page-size="params.size" @current-change="queryPage()"
                           layout="total,prev, pager, next" :total="total" small background/>
        </div>
    </div>
</template>

<script>
    import request from '../../api/role'
    import common from "../../mixins/common";
    import menuApi from "../../api/menu";

    export default {
        name: "admin",
        mixins: [common],
        data() {
            return {
                rule: {
                    nameZh:[{required:true,message:"角色中文名必须填写",trigger:'blur'}],
                    name: [{required: true, message: '角色名必须填写', trigger: 'blur'}],
                },
                request: request,
                //所有菜单的权限树
                menuTree: [],
                menuDialog: false,
                //角色所拥有的菜单
                roleMenus: [],
                currentRoleId: '',
            }
        },
        methods: {
            showMenuDialog(id) {
                request.getRoleMenus(id).then(res => {
                    this.roleMenus = res.data;
                    this.menuDialog = true;
                    this.currentRoleId = id;
                })
            },
            loadTree() {
                menuApi.getTree().then(res => {
                    this.menuTree = res.data;
                })
            },
            updateRoleMenus() {
                this.loadingSubmit=true;
                let keys = this.$refs.menu.getCheckedKeys();
                let halfKeys = this.$refs.menu.getHalfCheckedKeys();
                const menuIds = halfKeys.concat(keys);
                request.updateRoleMenus({roleId: this.currentRoleId, menuIds}).then(res => {
                    if (res.success) {
                        this.menuDialog = false;
                        this.loadingSubmit=false;
                        this.$message.success(res.msg)
                    }

                })
            }
        },
        created() {
            this.loadTree();
        }
    }
</script>

<style>

</style>
