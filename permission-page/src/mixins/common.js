import fa from "element-ui/src/locale/lang/fa";

export default {
    data() {
        return {
            //表格数据
            list: [],
            //总记录数
            total: 0,
            //表格查询参数
            params: {
                page: 1,
                size: 10,
                //模糊查询的文本
                text: '',
                //模糊查询的字段
                sort: [],
                //根据什么排序
                asc: true
            },
            //是否为添加的弹窗
            isAdd: true,
            request: {
                add: () => {
                },
                edit: () => {
                },
                delete: () => {
                },
                page: () => {
                },
                getById: () => {
                }
            },
            //表格Loading
            loadingTable: false,
            //提交表单按钮Loading
            loadingSubmit: false,
            //编辑数据Loading
            loadingEdit: false,
            //增加删除的表单
            form: {},
            //弹窗是否隐藏
            showDialog: false,
            //按钮权限
            permission: [],
        }

    },
    methods: {
        havePermission(permission) {
            return this.permission.indexOf(permission) !== -1;
        },
        haveAnyPermission(permission) {
            for (let i = 0; i < permission.length; i++) {
                if (this.havePermission(permission[i])) {
                    return true
                }
            }
            return false;
        },
        //显示添加模态框
        showAddDialog(form) {
            this.isAdd = true;
            this.form = form;
            this.showDialog = true;
        },
        //删除数据
        removeOne(id) {
            this.$confirm('此操作将永久删除此纪录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(res => {
                this.request.delete(id).then(res => {
                    if (res.success) {
                        this.$notify.success(res.msg)
                        this.queryPage()
                    }
                })
            })
        },
        //编辑数据
        showEditDialog(id) {
            this.isAdd = false;
            this.request.getById(id).then(res => {
                this.form = res.data;
                this.showDialog = true;
            })
        },
        //分页查询
        queryPage() {
            this.loadingTable = true;
            this.request.page(this.params).then(res => {
                if (res.success) {
                    this.list = res.data.list;
                    this.total = res.data.total;
                } else {
                    this.$notify.error(res.msg)
                }
                this.loadingTable = false;
            })
        },
        //提交表单
        submitForm() {
            this.$refs.form.validate((valid) => {
                if (valid) {
                    this.loadingSubmit = true;
                    if (this.isAdd) {
                        this.request.add(this.form).then(res => {
                            this.loadingSubmit = false;
                            if (res.success) {
                                this.showDialog = false;
                                this.queryPage()
                                this.$notify.success(res.msg);
                            }
                        })
                    } else {
                        this.request.edit(this.form).then(res => {
                            this.loadingSubmit = false;
                            if (res.success) {
                                this.showDialog = false;
                                this.$notify.success(res.msg);
                                this.queryPage();
                            }
                        })
                    }
                }
            });
        }
    },
    created() {
        this.queryPage()
        if (this.$route.meta.permission) {
            this.permission = this.$route.meta.permission
        }
    }
}
