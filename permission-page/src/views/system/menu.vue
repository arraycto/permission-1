<template>
    <div style="height: 100%;width: 100%;">
        <el-dialog :title="isAdd?'新增':'编辑'" :visible.sync="showDialog" width="600px">
            <el-form :model="form" :inline="true" ref="form" :rule="rule" style="font-weight: bold;" size="small"
                     label-position="right" label-width="80px">
                <el-form-item prop="type" label="菜单类型">
                    <el-radio-group size="mini" v-model="form.type" fill="#009688">
                        <el-radio-button size="mini" :label="1">菜单</el-radio-button>
                        <el-radio-button size="mini" :label="2">按钮</el-radio-button>
                    </el-radio-group>
                </el-form-item>
                <el-form-item prop="hidden" label="是否隐藏">
                    <el-radio-group size="mini" v-model="form.hidden" fill="#009688">
                        <el-radio-button size="mini" :label="true">是</el-radio-button>
                        <el-radio-button size="mini" :label="false">否</el-radio-button>
                    </el-radio-group>
                </el-form-item>
                <el-form-item prop="name" label="菜单名称">
                    <el-input v-model="form.name" size="mini" placeholder="菜单名称"/>
                </el-form-item>
                <el-form-item prop="pid" label="父菜单">
                    <tree-select v-model="form.pid" :options="menuTree" placeholder="不填则为顶级" style="width: 170px"/>
                </el-form-item>
                <el-form-item prop="component" label="组件名称">
                    <el-input v-model="form.component" size="mini" placeholder="组件名称"/>
                </el-form-item>
                <el-form-item prop="path" label="路由路径">
                    <el-input v-model="form.path" size="mini" placeholder="路由路径"/>
                </el-form-item>

                <el-form-item prop="reqUrl" label="请求URL">
                    <el-input v-model="form.reqUrl" size="mini" placeholder="请求URL，用于权限"/>
                </el-form-item>
                <el-form-item prop="orderBy" label="排序">
                    <el-input-number v-model="form.orderBy" controls-position="right" :min="0" :max="99999" size="mini"
                                     placeholder="排序"/>
                </el-form-item>
                <el-form-item prop="icon" label="图标">
                    <icon-select v-model="form.icon"></icon-select>
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
                    <el-button size="mini" type="primary"
                               @click="showAddDialog({hidden:false,type: 1,orderBy:99,component:'/Home'})"
                               icon="el-icon-plus"
                               v-if="havePermission('add')">新增菜单
                    </el-button>
                </el-form-item>
            </el-form>
        </div>
        <div class="center">
            <span class="divider-line"/>
            <!--表格-->
            <el-table class="table" row-key="name" :tree-props="{children: 'children'}" v-loading="loadingTable"
                      :data="list" element-loading-text="加载中..." border>
                <el-table-column align="left" min-width="100" label="菜单名称" prop="name"></el-table-column>
                <el-table-column align="center" label="组件名称" prop="component"></el-table-column>
                <el-table-column align="center" label="菜单类型" prop="type">
                    <template slot-scope="scope">
                        <el-tag type="success">{{scope.row.type===1?'菜单':'按钮'}}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column align="center" label="路由路径" prop="path"></el-table-column>
                <el-table-column align="center" label="图标" prop="icon">
                    <template slot-scope="scope">
                        <i :class="scope.row.icon"/>
                    </template>
                </el-table-column>
                <el-table-column align="center" label="请求URL" prop="reqUrl"></el-table-column>
                <el-table-column align="center" label="排序" prop="orderBy"></el-table-column>
                <el-table-column align="center" label="是否隐藏" prop="hidden">
                    <template slot-scope="scope">
                        <el-switch :value="scope.row.hidden" disabled/>
                    </template>
                </el-table-column>
                <el-table-column align="center" label="操作" min-width="120" fixed="right"
                                 v-if="haveAnyPermission(['edit','del'])">
                    <template slot-scope="scope">
                        <el-button @click="showEditDialog(scope.row.id)" size="mini" type="success" icon="el-icon-edit"
                                   :loading="loadingEdit" v-if="havePermission('edit')"/>
                        <el-button @click="removeOne(scope.row.id)" size="mini" type="danger" icon="el-icon-delete"
                                   v-if="havePermission('del')"/>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
    import request from '../../api/menu'
    import common from "../../mixins/common"
    import TreeSelect from '@riophae/vue-treeselect'
    import '@riophae/vue-treeselect/dist/vue-treeselect.css'
    import iconSelect from '../../components/iconSelect/index'

    export default {
        name: "admin",
        components: {TreeSelect,iconSelect},
        mixins: [common],
        data() {
            return {
                params: {
                    page: 1,
                    size: 99999
                },
                rule: [],
                request: request,
                menuTree: []
            }
        },
        methods: {
            loadTree() {
                request.getTree(1).then(res => {
                    this.menuTree = res.data;
                })
            },
        },
        created() {
            this.loadTree();
        }
    }
</script>

<style>

</style>
