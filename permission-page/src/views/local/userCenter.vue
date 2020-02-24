<template>
    <div>
        <el-row :gutter="20">
            <el-col :span="6">
                <el-card class="box-card">
                    <div slot="header">
                        <span style="font-size: 15px;font-weight:bold;">个人信息</span>
                        <el-popover placement="bottom-start" width="260" v-model="infoVisible">
                            <el-form :rules="infoRules" status-icon ref="infoForm" :model="infoForm" :inline="true"
                                     style="width:260px;font-weight: bold" label-position="right" label-width="65px">
                                <el-form-item prop="nickname" label="昵称">
                                    <el-input v-model="infoForm.nickname" size="mini" type="text"
                                              placeholder="昵称"></el-input>
                                </el-form-item>
                                <el-form-item prop="phone" label="Tel">
                                    <el-input v-model="infoForm.phone" size="mini" type="text"
                                              placeholder="电话号码"></el-input>
                                </el-form-item>
                                <el-form-item prop="email" label="Email">
                                    <el-input v-model="infoForm.email" size="mini" type="text"
                                              placeholder="邮箱"></el-input>
                                </el-form-item>
                            </el-form>
                            <div style="text-align: right; margin: 0">
                                <el-button size="mini" @click="infoVisible = false,$refs.infoForm.resetFields()">取消
                                </el-button>
                                <el-button type="primary" size="mini" @click="updateInfo()">确认修改</el-button>
                            </div>
                            <span slot="reference"
                                  style="color: #67C23A;font-size: 13px;font-weight: normal;cursor: pointer"
                                  type="text"> 修改</span>
                        </el-popover>
                        <el-popover placement="bottom-end" width="180" v-model="visible">
                            <el-form :rules="rules" status-icon ref="pwdForm" :model="pwdForm" :inline="true"
                                     style="width:230px">
                                <el-form-item prop="oldPwd">
                                    <el-input v-model="pwdForm.oldPwd" size="mini" type="password"
                                              placeholder="原密码"></el-input>
                                </el-form-item>
                                <el-form-item prop="newPwd">
                                    <el-input v-model="pwdForm.newPwd" size="mini" type="password"
                                              placeholder="新密码"></el-input>
                                </el-form-item>
                                <el-form-item prop="newPwdTwo">
                                    <el-input v-model="pwdForm.newPwdTwo" size="mini" type="password"
                                              placeholder="再次输入新密码"></el-input>
                                </el-form-item>
                            </el-form>
                            <div style="text-align: right; margin: 0">
                                <el-button size="mini" @click="visible = false,$refs.pwdForm.resetFields()">取消
                                </el-button>
                                <el-button type="primary" size="mini" @click="updatePwd()">确认修改</el-button>
                            </div>
                            <el-button slot="reference" style="float: right; padding: 3px 0" type="text">修改密码
                            </el-button>
                        </el-popover>
                    </div>
                    <el-divider content-position="left"><span style="font-weight: bold">账号：</span>{{adminInfo.username}}
                    </el-divider>
                    <el-divider content-position="left"><span style="font-weight: bold">昵称：</span>{{adminInfo.nickname}}
                    </el-divider>
                    <el-divider content-position="left"><span style="font-weight: bold">Tel：</span>{{adminInfo.email}}
                    </el-divider>
                    <el-divider content-position="left"><span style="font-weight: bold">Email：</span>{{adminInfo.email}}
                    </el-divider>
                    <el-divider/>
                    <el-divider content-position="left">
                        <span style="font-weight: bold">拥有角色：</span>
                        <el-tag v-for="(item,index) in adminInfo.roles" :key="index">
                            {{item.nameZh}}
                        </el-tag>
                    </el-divider>
                </el-card>
            </el-col>
            <el-col :span="18">
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <span>操作日志</span>
                        <el-button style="float: right; padding: 3px 0" type="text" v-if="this.$store.getters.admin.id==='1'" @click="clearLog()">清空日志</el-button>
                    </div>
                    <!--表格-->
                    <el-table v-loading="loadingTable" :data="tableData" highlight-current-row
                              element-loading-text="加载中..."
                              border fit>
                        <el-table-column align="center" label="操作名称" prop="opName"></el-table-column>
                        <el-table-column align="center" label="操作者IP" prop="ip"></el-table-column>
                        <el-table-column align="center" label="创建时间" prop="createTime"></el-table-column>
                        <el-table-column align="center" label="操作者用户名" prop="username"></el-table-column>
                        <el-table-column align="center" label="耗时" prop="spendTime">
                            <template slot-scope="scope">
                                <el-tag type="success" v-if="scope.row.spendTime<300" v-text="scope.row.spendTime+'ms'"/>
                                <el-tag type="success" v-else-if="scope.row.spendTime<1000"
                                        v-text="scope.row.spendTime+'ms'"/>
                                <el-tag type="success" v-else v-text="scope.row.spendTime+'ms'"/>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="footer" style="margin-top: 5px;">
                        <el-pagination :current-page="params.page" :page-size="params.size"
                                       @current-change="loadLogs()" layout="total,prev, pager, next" :total="total"
                                       small background/>
                    </div>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import adminApi from "../../api/admin";
    import logApi from "../../api/log";

    export default {
        name: "userCenter",
        data() {
            let validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    if (this.pwdForm.newPwdTwo !== '') {
                        this.$refs.pwdForm.validateField('newPwdTwo');
                    }
                    callback();
                }
            };
            let validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.pwdForm.newPwd) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                rules: {
                    oldPwd: [{required: true, message: '原密码不能为空', trigger: 'blur'}],
                    newPwd: [{validator: validatePass, trigger: 'blur'}],
                    newPwdTwo: [{validator: validatePass2, trigger: 'blur'}],
                },
                infoRules: {
                    email: [{required: true, message: '请填写邮箱', trigger: 'blur'}],
                    nickname: [{required: true, message: '请填写昵称', trigger: 'blur'}],
                    phone: [{required: true, message: '请填写手机号', trigger: 'blur'}]
                },
                pwdForm: {
                    oldPwd: '',
                    newPwd: '',
                    newPwdTwo: ''
                },
                loadingTable: false,
                //资料修改弹窗
                infoVisible: false,
                visible: false,
                infoForm: {...this.$store.getters.admin},
                tableData: [],
                params: {
                    page: 1,
                    size: 10
                },
                total: 0,
            }
        },
        computed: {
            adminInfo() {
                return this.$store.getters.admin
            }
        },
        methods: {
            updatePwd() {
                this.$refs.pwdForm.validate((valid) => {
                    if (valid) {
                        this.visible = false;
                        console.log(this.pwdForm);
                        adminApi.updatePwd(this.pwdForm).then(res => {
                            adminApi.logout();
                            window.sessionStorage.removeItem("admin");
                            this.$store.commit("setAdmin", {});
                            this.$store.commit("setRoutes", []);
                            this.$store.commit("setRemoteRoutes", []);
                            this.$router.replace("/login");
                        })
                    }
                });
            },
            updateInfo() {
                this.$refs.infoForm.validate((valid) => {
                    if (valid) {
                        console.log(this.infoForm);
                        adminApi.updateInfo(this.infoForm).then(res => {
                            if (res.success) {
                                this.$store.commit("setAdmin", this.infoForm)
                                window.sessionStorage.setItem("admin", JSON.stringify(this.infoForm))
                                this.infoVisible = false;
                                this.$message.success(res.msg)
                            }
                        })
                    }
                })
            },
            loadLogs() {
                logApi.page(this.params).then(res => {
                    this.tableData = res.data.list;
                    this.total = res.data.total;
                })
            },
            clearLog(){
              this.$confirm('确定清空日志吗?', '提示', {
                  confirmButtonText: '确定',
                  cancelButtonText: '取消',
                  type: 'warning'
              }).then(()=>{
                 logApi.clearLogs().then(res=>{
                     this.loadLogs()
                     this.$message.success(res.msg)
                 })
              });
            }
        },
        created() {
            this.loadLogs();
        }
    }
</script>

<style scoped>

</style>
