<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>部门管理</title>
    <jsp:include page="/common/backend_common.jsp"/>
</head>
<body>

<body class="no-skin" youdao="bind" style="background: white">
<input id="gritter-light" checked="" type="checkbox" class="ace ace-switch ace-switch-5"/>

<div class="page-header">
    <h1>
        用户管理
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            维护部门与用户关系
        </small>
    </h1>
</div>
<div class="main-content-inner">
    <div class="col-sm-3">
        <div class="table-header">
            部门列表&nbsp;&nbsp;
            <a class="green" href="#">
                <i class="ace-icon fa fa-plus-circle orange bigger-130 dept-add"></i>
            </a>
        </div>
        <div id="deptList">
            <%--1.填充部门列表的地方--%>
        </div>
    </div>
    <div class="col-sm-9">
        <div class="col-xs-12">
            <div class="table-header">
                用户列表&nbsp;&nbsp;
                <a class="green" href="#">
                    <i class="ace-icon fa fa-plus-circle orange bigger-130 user-add"></i>
                </a>
            </div>
            <div>
                <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                    <div class="row">
                        <div class="col-xs-6">
                            <div class="dataTables_length" id="dynamic-table_length"><label>
                                展示
                                <select id="pageSize" name="dynamic-table_length" aria-controls="dynamic-table" class="form-control input-sm">
                                    <option value="10">10</option>
                                    <option value="25">25</option>
                                    <option value="50">50</option>
                                    <option value="100">100</option>
                                </select> 条记录 </label>
                            </div>
                        </div>
                    </div>
                    <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid"
                           aria-describedby="dynamic-table_info" style="font-size:14px">
                        <thead>
                        <tr role="row">
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                姓名
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                所属部门
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                邮箱
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                电话
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                状态
                            </th>
                            <th class="sorting_disabled" rowspan="1" colspan="1" aria-label=""></th>
                        </tr>
                        </thead>
                        <tbody id="userList">
                        <%--2.填充用户列表的地方--%>
                        </tbody>
                    </table>
                    <div class="row" id="userPage">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%--3.一个弹出框，用于部门的新增和修改--%>
<div id="dialog-dept-form" style="display: none;">
    <form id="deptForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer" role="grid">
            <tr>
                <td style="width: 80px;"><label for="parentId">上级部门</label></td>
                <td>
                    <select id="parentId" name="parentId" data-placeholder="选择部门" style="width: 200px;"></select>
                    <input type="hidden" name="id" id="deptId"/>
                </td>
            </tr>
            <tr>
                <td><label for="deptName">名称</label></td>
                <td><input type="text" name="name" id="deptName" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="deptSeq">顺序</label></td>
                <td><input type="text" name="seq" id="deptSeq" value="1" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="deptRemark">备注</label></td>
                <td><textarea name="remark" id="deptRemark" class="text ui-widget-content ui-corner-all" rows="3" cols="25"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<%--3.一个弹出框，用于用户的新增和修改--%>
<div id="dialog-user-form" style="display: none;">
    <form id="userForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer" role="grid">
            <tr>
                <td style="width: 80px;"><label for="parentId">所在部门</label></td>
                <td>
                    <select id="deptSelectId" name="deptId" data-placeholder="选择部门" style="width: 200px;"></select>
                </td>
            </tr>
            <tr>
                <td><label for="userName">名称</label></td>
                <input type="hidden" name="id" id="userId"/>
                <td><input type="text" name="username" id="userName" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="userMail">邮箱</label></td>
                <td><input type="text" name="mail" id="userMail" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="userTelephone">电话</label></td>
                <td><input type="text" name="telephone" id="userTelephone" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="userStatus">状态</label></td>
                <td>
                    <select id="userStatus" name="status" data-placeholder="选择状态" style="width: 150px;">
                        <option value="1">有效</option>
                        <option value="0">无效</option>
                        <option value="2">删除</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="userRemark">备注</label></td>
                <td><textarea name="remark" id="userRemark" class="text ui-widget-content ui-corner-all" rows="3" cols="25"></textarea></td>
            </tr>
        </table>
    </form>
</div>

<script id="deptListTemplate" type="x-tmpl-mustache">
<ol class="dd-list">
    {{#deptList}}
        <li class="dd-item dd2-item dept-name" id="dept_{{id}}" href="javascript:void(0)" data-id="{{id}}">
            <div class="dd2-content" style="cursor:pointer;">
            {{name}}
            <span style="float:right;">
                <a class="green dept-edit" href="#" data-id="{{id}}" >
                    <i class="ace-icon fa fa-pencil bigger-100"></i>
                </a>
                &nbsp;
                <a class="red dept-delete" href="#" data-id="{{id}}" data-name="{{name}}">
                    <i class="ace-icon fa fa-trash-o bigger-100"></i>
                </a>
            </span>
            </div>
        </li>
    {{/deptList}}
</ol>
</script>
<script type="application/javascript">

    $(function () {

        var deptList;//存储树形部门列表
        var deptMap = {};//存储map格式的部门信息
        var optionStr = "";//编辑、新增部门的时候用于辅助展示部门层级列表
        var lastClickDeptId = -1;//上一次被点击的部门的id

        <!--1.获取要渲染的模板-->
        var deptListTemplate = $('#deptListTemplate').html();
        <!--2.先用mustache预处理一下-->
        Mustache.parse(deptListTemplate);
        <!--3.加载部门树-->
        loadDeptTree();

        //加载部门树
        function loadDeptTree() {
            <!--4.ajax获取后台的数据-->
            $.ajax({
                url: "/sys/dept/tree.json" ,
                success: function (result) {
                    if(result.ret){
                        deptList = result.data;
                        <!--5.拿到数据并渲染数据-->
                        var rendered = Mustache.render(deptListTemplate,{deptList: result.data});
                        <!--6.渲染后的数据放到对应的html地方进行展示，注意此时只能渲染第一层-->
                        $("#deptList").html(rendered);
                        <!--7.递归渲染下面层级的部门-->
                        recursiveRenderDept(result.data);
                        <!--8.为部门列表绑定事件-->
                        bindDeptClick();
                    } else{
                        showMessage("加载部门列表失败",result.msg,false);
                    }
                }
            })
        }

        //递归渲染部门列表
        function recursiveRenderDept(deptList) {
            if(deptList && deptList.length > 0){
                $(deptList).each(function (i,dept) {
                    deptMap[dept.id] = dept;
                    if(dept.sysDeptList && dept.sysDeptList.length > 0){
                        var rendered = Mustache.render(deptListTemplate,{deptList: dept.sysDeptList});
                        $("#dept_" + dept.id).append(rendered);
                        recursiveRenderDept(dept.sysDeptList);
                    }
                })
            }
        }

        //绑定部门点击事件
        function bindDeptClick() {

            //1,点击部门编辑按钮
            $(".dept-edit").click(function(e) {
                e.preventDefault();
                e.stopPropagation();
                //获取部门id
                var deptId = $(this).attr("data-id");
                //弹出模态框
                $("#dialog-dept-form").dialog({
                    model: true,
                    title: "编辑部门",
                    //显示模态框前就会触发
                    open: function (event,ui) {
                        //隐藏原始的隐藏模态框按钮
                        $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                        //部门列表下拉框默认显示“-”
                        optionStr = "<option value=\"0\">-</option>";
                        //递归展示所有的部门
                        recursiveRenderDeptSelect(deptList, 1);
                        $("#deptForm")[0].reset();
                        $("#parentId").html(optionStr);
                        $("#deptId").val(deptId);
                        //根据部门id获取部门的信息
                        var targetDept = deptMap[deptId];
                        if (targetDept) {
                            $("#parentId").val(targetDept.parentId);
                            $("#deptName").val(targetDept.name);
                            $("#deptSeq").val(targetDept.seq);
                            $("#deptRemark").val(targetDept.remark);
                        }
                    },
                    //自定义的按钮，主要就是更新按钮和取消按钮
                    buttons : {
                        "更新": function(e) {
                            e.preventDefault();
                            //执行更新操作，第一个参数表示不是新增操作，第二个和第三个分别是处理成功和失败后的处理
                            updateDept(false, function (data) {
                                $("#dialog-dept-form").dialog("close");
                            }, function (data) {
                                showMessage("更新部门", data.msg, false);
                            })
                        },
                        "取消": function () {
                            $("#dialog-dept-form").dialog("close");
                        }
                    }
                });
            });

            //2.删除部门
            $(".dept-delete").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var deptId = $(this).attr("data-id");
                var deptName = $(this).attr("data-name");
                //TODO 后端还没有实现，因为这涉及用户，后面再具体实现
                if (confirm("确定要删除部门[" + deptName + "]吗?")) {
                    $.ajax({
                        url: "/sys/dept/delete.json",
                        data: {
                            id: deptId
                        },
                        success: function (result) {
                            if (result.ret) {
                                showMessage("删除部门[" + deptName + "]", "操作成功", true);
                                loadDeptTree();
                            } else {
                                showMessage("删除部门[" + deptName + "]", result.msg, false);
                            }
                        }
                    });
                }
            });

            //3.点击部门名称的时候加载这个部门下的所有用户
            $(".dept-name").click(function(e) {
                e.preventDefault();
                e.stopPropagation();
                var deptId = $(this).attr("data-id");
                //鼠标放在部门上有一定的效果
                handleDepSelected(deptId);
            });

        }

        //处理鼠标点击部门名称的事件
        function handleDepSelected(deptId) {
            //点击了其他有效的部门，那么原来选中的部门的选中样式要去除
            if (lastClickDeptId != -1) {
                var lastDept = $("#dept_" + lastClickDeptId + " .dd2-content:first");
                lastDept.removeClass("btn-yellow");
                lastDept.removeClass("no-hover");
            }
            //给当前新选中的部门加上相应的选中样式
            var currentDept = $("#dept_" + deptId + " .dd2-content:first");
            currentDept.addClass("btn-yellow");
            currentDept.addClass("no-hover");
            lastClickDeptId = deptId;
            loadUserList(deptId);
        }

        //点击部门之后，应该在右边加载对应的用户列表，后面再实现
        //TODO
        function loadUserList(deptId) {
            console.log("加载用户列表，部门的id为："+deptId);
        }

        //新增部门
        $(".dept-add").click(function() {
            $("#dialog-dept-form").dialog({
                model: true,
                title: "新增部门",
                open: function (event,ui) {
                    //隐藏原始的隐藏模态框按钮
                    $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                    //部门列表下拉框默认显示“-”
                    optionStr = "<option value=\"0\">-</option>";
                    //递归展示所有的部门
                    recursiveRenderDeptSelect(deptList, 1);
                    $("#deptForm")[0].reset();
                    $("#parentId").html(optionStr);
                },
                buttons : {
                    "新增": function(e) {
                        e.preventDefault();
                        //执行更新操作，第一个参数表示不是新增操作，第二个和第三个分别是处理成功和失败后的处理
                        updateDept(true, function (data) {
                            $("#dialog-dept-form").dialog("close");
                        }, function (data) {
                            showMessage("新增部门", data.msg, false);
                        })
                    },
                    "取消": function () {
                        $("#dialog-dept-form").dialog("close");
                    }
                }
            })
        });

        //下拉选择框递归展示所有的部门
        function recursiveRenderDeptSelect(deptList, level) {
            level = level | 0;
            if(deptList && deptList.length > 0){
                $(deptList).each(function (i, dept) {
                    deptMap[dept.id] = dept;
                    var blank = "";
                    if(level > 1){
                        for(var j = 2; j <= level; j++) {
                            blank += "..";
                        }
                        blank += "◥";
                    }
                    optionStr += Mustache.render("<option value='{{id}}'>{{name}}</option>", {id: dept.id, name: blank + dept.name});
                    if (dept.sysDeptList && dept.sysDeptList.length > 0) {
                        recursiveRenderDeptSelect(dept.sysDeptList, level + 1);
                    }
                })
            }
        };

        //更新部门
        function updateDept(isCreate, successCallback, failCallback) {
            $.ajax({
                url: isCreate ? "/sys/dept/save.json" : "/sys/dept/update.json",
                data: $("#deptForm").serializeArray(),
                type: 'POST',
                success: function(result) {
                    if (result.ret) {
                        loadDeptTree();
                        if (successCallback) {
                            successCallback(result);
                        }
                    } else {
                        if (failCallback) {
                            failCallback(result);
                        }
                    }
                }
            })
        };

    })

</script>




</body>
</html>
